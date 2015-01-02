package info.tepp.osgi.manifest.parser;

public interface Function<F, T> {
    T apply(F input);
}
