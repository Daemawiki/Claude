package com.daemawiki.internal.common.event;

public interface EventHandler<T> {

    void handle(T event);

}
