package info.tepp.osgi.manifest.parser;

public interface BiFunction<T,U,R> {
    R apply(T t, U u);
}