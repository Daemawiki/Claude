//package com.daemawiki.daemawiki.application.mail.event.handler;
//
//import com.daemawiki.daemawiki.application.mail.event.model.MailSendEvent;
//import com.daemawiki.daemawiki.common.util.event.EventFailureHandler;
//import com.daemawiki.daemawiki.domain.mail.repository.AuthCodeRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.event.TransactionPhase;
//import org.springframework.transaction.event.TransactionalEventListener;
//import reactor.core.scheduler.Schedulers;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//class UserMailSendEventFailureHandler implements EventFailureHandler<MailSendEvent> {
//    private final AuthCodeRepository authCodeRepository;
//
//    @Override
//    @Transactional()
//    @TransactionalEventListener(classes = {MailSendEvent.class}, phase = TransactionPhase.AFTER_ROLLBACK)
//    public void handleFailure(MailSendEvent event) {
//        log.warn("Failure handler triggered - To: {}, AuthCode: {}", event.to(), event.content());
//        authCodeRepository.deleteByEmail(event.to())
//                .subscribeOn(Schedulers.boundedElastic())
//                .doOnNext(count -> log.error("{}-Mail send failed for {}", count, event.to()))
//                .doOnError(e -> log.error("Redis entity delete failed for {}: {}", event.to(), e.getMessage()))
//                .subscribe();
//    }
//}
