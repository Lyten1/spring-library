package mate.academy.springlibrary.service;

import java.util.List;
import mate.academy.springlibrary.dto.BookDto;
import mate.academy.springlibrary.dto.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto update(Long id, CreateBookRequestDto book);

    void delete(Long id);
}
