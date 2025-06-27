package pl.ppiekarski.livescoreboard;

import java.util.function.Supplier;

/**
 * simple kotlin.Result alike monad
 */
public sealed interface Result<T> {

    static <T> Result<T> runCatching(Supplier<T> supplier) {
        try {
            return success(supplier.get());
        } catch (Throwable exception) {
            return failure(exception);
        }
    }

    boolean isSuccess();

    boolean isFailure();

    T getOrNull();

    Throwable exceptionOrNull();

    static <T> Result<T> success(T value) {
        return new Success<>(value);
    }

    static <T> Result<T> failure(Throwable exception) {
        return new Failure<>(exception);
    }

    final class Success<T> implements Result<T> {
        private final T value;

        public Success(T value) {
            this.value = value;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public boolean isFailure() {
            return false;
        }

        @Override
        public T getOrNull() {
            return value;
        }

        @Override
        public Throwable exceptionOrNull() {
            return null;
        }

        @Override
        public String toString() {
            return "Success[" + value + "]";
        }
    }

    final class Failure<T> implements Result<T> {
        private final Throwable exception;

        public Failure(Throwable exception) {
            this.exception = exception;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isFailure() {
            return true;
        }

        @Override
        public T getOrNull() {
            return null;
        }

        @Override
        public Throwable exceptionOrNull() {
            return exception;
        }

        @Override
        public String toString() {
            return "Failure[" + exception + "]";
        }
    }
}