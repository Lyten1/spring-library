package mate.academy.springlibrary.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.dto.BookDto;
import mate.academy.springlibrary.dto.CreateBookRequestDto;
import mate.academy.springlibrary.mapper.BookMapper;
import mate.academy.springlibrary.model.Book;
import mate.academy.springlibrary.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.getById(id));
    }
}
