package org.result4j;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Result<V, E> {
    public static <V, E> Result<V, E> success(V value) {
        return new Success<>(value);
    }
    public static <V, E> Result<V, E> failure(E error) {
        return new Failure<>(error);
    }

    public abstract <U> Result<U, E> map(Function<? super V, ? extends U> mapper);
    public abstract <U> Result<U, E> flatMap(Function<? super V, Result<U, E>> mapper);
    public abstract V orElseGet(Supplier<? extends V> other);
    
    public abstract boolean isSuccess();
    public abstract V get();

    private static class Success<V, E> extends Result<V, E> {
        private final V value;
        Success(V value) { this.value = value; }
        public <U> Result<U, E> map(Function<? super V, ? extends U> mapper) {
            return new Success<>(mapper.apply(value));
        }
        public <U> Result<U, E> flatMap(Function<? super V, Result<U, E>> mapper) {
            return mapper.apply(value);
        }
        public V orElseGet(Supplier<? extends V> other) { return value; }
        public boolean isSuccess() { return true; }
        public V get() { return value; }
    }

    private static class Failure<V, E> extends Result<V, E> {
        private final E error;
        Failure(E error) { this.error = error; }
        public <U> Result<U, E> map(Function<? super V, ? extends U> mapper) {
            return new Failure<>(error);
        }
        public <U> Result<U, E> flatMap(Function<? super V, Result<U, E>> mapper) {
            return new Failure<>(error);
        }
        public V orElseGet(Supplier<? extends V> other) { return other.get(); }
        public boolean isSuccess() { return false; }
        public V get() { throw new RuntimeException("No value present"); }
    }
}
