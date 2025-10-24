package mate.academy.springlibrary.service.book;

import java.util.List;
import mate.academy.springlibrary.dto.book.BookDto;
import mate.academy.springlibrary.dto.book.BookSearchParametersDto;
import mate.academy.springlibrary.dto.book.CreateBookRequestDto;
import mate.academy.springlibrary.model.Book;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto update(Long id, CreateBookRequestDto book);

    void delete(Long id);

    List<BookDto> search(BookSearchParametersDto bookSearchParametersDto);

    Book getEntityById(Long id);
}
