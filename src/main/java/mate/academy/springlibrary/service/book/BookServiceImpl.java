package mate.academy.springlibrary.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.dto.book.BookDto;
import mate.academy.springlibrary.dto.book.BookSearchParametersDto;
import mate.academy.springlibrary.dto.book.CreateBookRequestDto;
import mate.academy.springlibrary.exeption.EntityNotFoundException;
import mate.academy.springlibrary.mapper.BookMapper;
import mate.academy.springlibrary.model.Book;
import mate.academy.springlibrary.model.Category;
import mate.academy.springlibrary.repository.books.BookRepository;
import mate.academy.springlibrary.repository.books.BookSpecificationBuilder;
import mate.academy.springlibrary.repository.category.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        Set<Category> uniqueCategories = categoryRepository
                .findDistinctById(requestDto.getCategoryIds());
        book.setCategories(uniqueCategories);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book with id " + id + " not found")));
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto bookDto) {
        Book bookFromDb = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Updating bookDto with id " + id + " not found"));
        bookMapper.updateBookFromDto(bookDto, bookFromDb);
        List<Category> categories = categoryRepository.findAllById(bookDto.getCategoryIds());
        Set<Category> uniqueCategories = categories.stream().collect(Collectors.toSet());
        bookFromDb.setCategories(uniqueCategories);
        return bookMapper.toDto(bookRepository.save(bookFromDb));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookDto> search(BookSearchParametersDto bookSearchParametersDto) {
        Specification<Book> bookSpecification = bookSpecificationBuilder
                .build(bookSearchParametersDto);
        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
