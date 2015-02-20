package info.tepp.osgi.manifest.parser;

public interface Function<T, R> {
    R apply(T input);
}
