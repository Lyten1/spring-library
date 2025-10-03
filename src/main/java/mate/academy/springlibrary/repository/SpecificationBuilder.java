package mate.academy.springlibrary.repository;

import mate.academy.springlibrary.dto.BookSearchParametersDto;
import mate.academy.springlibrary.model.Book;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {

    Specification<Book> build(BookSearchParametersDto bookSearchParametersDto);
}
