package com.daemawiki.external.security.paseto;

import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.internal.user.primitive.Token;
import com.daemawiki.internal.user.primitive.personal.Email;
import com.daemawiki.internal.user.repository.UserRepository;
import dev.paseto.jpaseto.Claims;
import dev.paseto.jpaseto.Paseto;
import dev.paseto.jpaseto.PasetoParser;
import dev.paseto.jpaseto.Pasetos;
import dev.paseto.jpaseto.Version;
import dev.paseto.jpaseto.lang.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
class TokenHandler implements Tokenizer, TokenUtils {

    private static final SecretKey SECRET_KEY;

    private static final KeyPair KEY_PAIR;

    static {
        SECRET_KEY = Keys.secretKey();
        KEY_PAIR = Keys.keyPairFor(Version.V2);
    }

    private final UserRepository userRepository;

    @Value("${app.issuer}")
    private String issuer;

    @Override
    public Mono<Token> generate(final String subject) {
        return Mono.fromCallable(() -> {
            final var now = Instant.now();

            final String token = Pasetos.V2.LOCAL.builder()
                    .setSharedSecret(SECRET_KEY)
                    .setIssuer(issuer)
                    .setIssuedAt(now)
                    .setSubject(subject)
                    .setExpiration(now.plus(2, ChronoUnit.HOURS))
                    .setKeyId(UUID.randomUUID().toString())
                    .compact();

            return Token.create(token);
        });
    }

    @Override
    public Mono<Authentication> getAuthentication(String token) {
        return parseClaims(token)
                .flatMap(claims -> createAuthenticatedUserBySubject(claims.getSubject()))
                .map(details -> new UsernamePasswordAuthenticationToken(
                        details, null, details.getAuthorities()));
    }

    private Mono<UserDetails> createAuthenticatedUserBySubject(String subject) {
        return userRepository.findByEmail(Email.create(subject))
                .switchIfEmpty(Mono.error(CustomExceptionFactory.unauthorized("인증에 실패하였습니다.")))
                .flatMap(user -> Mono.justOrEmpty(new User(subject, "", List.of(
                        new SimpleGrantedAuthority("ROLE_" + user.userRole().name())
                ))));
    }

    @Override
    public Mono<Boolean> validateToken(final String authToken) {
        return parseToken(authToken)
                .flatMap(p -> isTokenExpired(authToken))
                .onErrorResume(e -> Mono.just(false));
    }

    @Override
    public Mono<String> extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Mono<Boolean> isTokenExpired(final String token) {
        return extractClaim(token, Claims::getExpiration)
                .map(expiration -> expiration.isBefore(Instant.now()));
    }

    private <T> Mono<T> extractClaim(
            final String token,
            final Function<Claims, T> claimsResolver
    ) {
        return parseClaims(token)
                .map(claimsResolver);
    }

    private Mono<Claims> parseClaims(final String token) {
        return parseToken(token)
                .map(Paseto::getClaims);
    }

    private Mono<Paseto> parseToken(final String token) {
        return Mono.fromCallable(() -> {
            final PasetoParser parser = Pasetos.parserBuilder()
                    .setSharedSecret(SECRET_KEY)
                    .setPublicKey(KEY_PAIR.getPublic())
                    .build();

            return parser.parse(token);
        });
    }

}