package mate.academy.springlibrary.repository;

public interface SpecificationProviderManager<T> {

    SpecificationProvider<T> getSpecificationProvider(String key);
}
