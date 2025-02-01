package com.daemawiki.internal.core.domain.model.primitive;

public interface DomainPrimitive<T> {
    T value();

    interface StringDP extends DomainPrimitive<String> {
    }

    interface IntegerDP extends DomainPrimitive<Integer> {
    }

    interface LongDP extends DomainPrimitive<Long> {
    }

}
