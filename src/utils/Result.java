package utils;

import java.util.function.*;

public interface Result<T> {
    public static <T> Result<T> of(ResultSupplier<T> supplier) {
        try {
            return ok(supplier.get());
        }
        catch (Exception e) {
            return error(e);
        }
    }

    public static <T> Result<T> ok(T val) {
        return new Ok<>(val);
    }

    public static <T, E extends Exception> Result<T> error(E exn) {
        return new Error<>(exn);
    }

    public Result<T> ifOk(Consumer<T> consumer);
    public Result<T> ifError(Consumer<Exception> consumer);
}

class Ok<T> implements Result<T> {
    private T val;

    Ok(T val) {
        this.val = val;
    }

    public Result<T> ifOk(Consumer<T> consumer) {
        consumer.accept(val); 
        return this;
    }

    public Result<T> ifError(Consumer<Exception> consumer) {
        return this;
    }
}

class Error<T> implements Result<T> {
    private Exception exn;

    Error(Exception exn) {
        this.exn = exn;
    }

    public Result<T> ifOk(Consumer<T> consumer) { 
        return this;
    }

    public Result<T> ifError(Consumer<Exception> consumer) {
        consumer.accept(exn);
        return this;
    }
}
