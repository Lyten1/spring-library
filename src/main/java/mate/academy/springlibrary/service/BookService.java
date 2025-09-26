package mate.academy.springlibrary.service;

import java.util.List;
import mate.academy.springlibrary.dto.BookDto;
import mate.academy.springlibrary.dto.CreateBookRequestDto;

public interface BookService {

    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll();

    BookDto findById(Long id);
}
