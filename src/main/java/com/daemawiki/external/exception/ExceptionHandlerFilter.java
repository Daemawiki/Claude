package com.daemawiki.external.exception;

import com.daemawiki.external.exception.custom.CustomException;
import com.daemawiki.external.exception.custom.CustomExceptionFactory;
import com.daemawiki.external.exception.custom.CustomExceptionLogger;
import com.daemawiki.external.exchange.response.ResponseWriterFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
class ExceptionHandlerFilter implements WebFilter {

    private final ResponseWriterFactory responseWriterFactory;

    private static final CustomExceptionLogger LOGGER = CustomExceptionLogger.of(ExceptionHandlerFilter.class);

    @NonNull
    @Override
    public Mono<Void> filter(
            @NonNull final ServerWebExchange exchange,
            final WebFilterChain chain
    ) {
        return chain.filter(exchange)
                .doOnError(error -> resolveError(error, exchange));
    }

    private void resolveError(
            final Throwable error,
            final ServerWebExchange exchange
    ) {
        final var ex = convertToCustomException(error);
        final var exceptionResponse = convertExceptionToResponse(ex);

        LOGGER.log(ex, exceptionResponse, exchange);

        responseWriterFactory.getWriter(exchange)
                .setStatus(ex.getStatus())
                .setBody(exceptionResponse)
                .end();
    }

    private CustomException convertToCustomException(final Throwable throwable) {
        if (throwable instanceof CustomException customException) {
            return customException;
        }

        return CustomExceptionFactory.internalServerError(
                throwable.getMessage(), throwable.getCause()
        );
    }

    private ExceptionResponse convertExceptionToResponse(final CustomException exception) {
        return ExceptionResponse.ofCustomException(exception);
    }

}
