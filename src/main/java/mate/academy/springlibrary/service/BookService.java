package mate.academy.springlibrary.service;

import java.util.List;
import mate.academy.springlibrary.dto.BookDto;
import mate.academy.springlibrary.dto.BookSearchParametersDto;
import mate.academy.springlibrary.dto.CreateBookRequestDto;

public interface BookService {

    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto update(Long id, CreateBookRequestDto book);

    void delete(Long id);

    List<BookDto> search(BookSearchParametersDto bookSearchParametersDto);
}
