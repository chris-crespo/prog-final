package utils;

public interface ResultSupplier<T> {
    T get() throws Exception;
}
