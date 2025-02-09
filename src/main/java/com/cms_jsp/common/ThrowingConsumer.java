package com.cms_jsp.common;

import java.util.function.Consumer;

/**
 * Stream 처리에서 Exception 처리를 위한 Util
 * - WebClient 사용
 * @author
 *
 * @param <T>
 * @param <E>
 */

@FunctionalInterface
public interface ThrowingConsumer<T, E extends Throwable>  {
	
	void accept(T t) throws E;

    static <T, E extends Throwable> Consumer<T> unchecked(ThrowingConsumer<T, E> f) {
        return t -> {
            try {
                f.accept(t);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }

    static <T, E extends Throwable> Consumer<T> unchecked(ThrowingConsumer<T, E> f, Consumer<Throwable> c) {
        return t -> {
            try {
                f.accept(t);
            } catch (Throwable e) {
                c.accept(e);
            }
        };
    }
}
