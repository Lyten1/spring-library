package mate.academy.springlibrary.repository.books;

import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.dto.book.BookSearchParametersDto;
import mate.academy.springlibrary.model.Book;
import mate.academy.springlibrary.repository.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {

    private final BookSpecificationProviderManager bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto bookSearchParametersDto) {
        Specification<Book> spec = (root, query, cb) -> cb.conjunction();
        if (bookSearchParametersDto.authors() != null
                && bookSearchParametersDto.authors().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                     .getSpecificationProvider("author")
                     .getSpecification(bookSearchParametersDto.authors()));
        }
        if (bookSearchParametersDto.partTitle() != null) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(new String[] {bookSearchParametersDto.partTitle()}));
        }
        return spec;
    }
}
