package com.daemawiki.internal.common.event;

public interface EventFailureHandler<T> {

    void handleFailure(T event);

}
