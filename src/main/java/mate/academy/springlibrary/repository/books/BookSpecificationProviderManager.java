package mate.academy.springlibrary.repository.books;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.model.Book;
import mate.academy.springlibrary.repository.SpecificationProvider;
import mate.academy.springlibrary.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {

    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find correct provider"));

    }
}
