package mate.academy.springlibrary.service;

import mate.academy.springlibrary.dto.book.BookDto;
import mate.academy.springlibrary.dto.book.CreateBookRequestDto;
import mate.academy.springlibrary.mapper.BookMapper;
import mate.academy.springlibrary.model.Book;
import mate.academy.springlibrary.model.Category;
import mate.academy.springlibrary.repository.books.BookRepository;
import mate.academy.springlibrary.repository.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import mate.academy.springlibrary.service.book.BookServiceImpl;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void save_ValidBook_ShouldSaveBook() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Fantasy");

        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setPrice(BigDecimal.valueOf(12.99));
        requestDto.setTitle("hobit");
        requestDto.setAuthor("D. Lois");
        requestDto.setIsbn("123532421-1234");
        requestDto.setCategoryIds(List.of(1L));

        BookDto expected = new BookDto()
                .setPrice(BigDecimal.valueOf(12.99))
                .setTitle("hobit")
                .setAuthor("D. Lois")
                .setIsbn("12353249871-1234")
                .setCategoryIds(List.of(1L));

        Book book = new Book();
        book.setId(1L);
        book.setPrice(BigDecimal.valueOf(12.99));
        book.setTitle("hobit");
        book.setAuthor("D. Lois");
        book.setIsbn("123532421-1234");
        book.setCategories(Set.of(category));

        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expected);
        when(bookRepository.save(book)).thenReturn(book);
        when(categoryRepository.findDistinctById(List.of(1L))).thenReturn(Set.of(category));

        //When
        BookDto savedBook = bookService.save(requestDto);

        assertThat(savedBook).isEqualTo(expected);
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(bookMapper, times(1)).toDto(any(Book.class));
        verify(bookMapper, times(1)).toModel(any(CreateBookRequestDto.class));
        verify(categoryRepository, times(1)).findDistinctById(any(List.class));
        verifyNoMoreInteractions(bookRepository, bookMapper, categoryRepository);
    }

    @Test
    public void findAll_ValidRequest_ShouldReturnAllBooks() {
        //Given
        List<BookDto> expected = new ArrayList<>();
        expected.add(new BookDto()
                .setId(1L)
                .setPrice(BigDecimal.valueOf(12.99))
                .setTitle("hobit")
                .setAuthor("D. Lois")
                .setIsbn("123532421-1234")
                .setCategoryIds(List.of(1L)));
        expected.add(new BookDto()
                .setId(2L)
                .setPrice(BigDecimal.valueOf(16.89))
                .setTitle("man")
                .setAuthor("D. Peter")
                .setIsbn("54325654-1235")
                .setCategoryIds(List.of(1L)));

        Category category = new Category();
        category.setId(1L);
        category.setName("Fantasy");

        List<Book> books = new ArrayList<>();
        Book firstBook = new Book();
        firstBook.setId(1L);
        firstBook.setPrice(BigDecimal.valueOf(12.99));
        firstBook.setTitle("hobit");
        firstBook.setAuthor("D. Lois");
        firstBook.setIsbn("123532421-1234");
        firstBook.setCategories(Set.of(category));
        Book secondBook = new Book();
        secondBook.setId(2L);
        secondBook.setPrice(BigDecimal.valueOf(16.89));
        secondBook.setTitle("man");
        secondBook.setAuthor("D. Peter");
        secondBook.setIsbn("54325654-1235");
        secondBook.setCategories(Set.of(category));

        books.add(firstBook);
        books.add(secondBook);

        when(bookMapper.toDto(firstBook)).thenReturn(expected.get(0));
        when(bookMapper.toDto(secondBook)).thenReturn(expected.get(1));
        when(bookRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(books));

        Pageable pageable = PageRequest.of(0, 10);
        //When
        List<BookDto> actual = bookService.findAll(pageable);

        //Then
        assertEquals(expected, actual);
        verify(bookRepository).findAll(any(Pageable.class));
        verify(bookMapper, times(2)).toDto(any(Book.class));
    }

    @Test
    public void findById_ValidId_ShouldReturnBookWithCorrectId() {
        //Given

        BookDto expected = new BookDto()
                .setId(1L)
                .setPrice(BigDecimal.valueOf(12.99))
                .setTitle("hobit")
                .setAuthor("D. Lois")
                .setIsbn("123532421-1234")
                .setCategoryIds(List.of(1L));

        Category category = new Category();
        category.setId(1L);
        category.setName("Fantasy");

        Book book = new Book();
        book.setId(1L);
        book.setPrice(BigDecimal.valueOf(12.99));
        book.setTitle("hobit");
        book.setAuthor("D. Lois");
        book.setIsbn("123532421-1234");
        book.setCategories(Set.of(category));

        when(bookMapper.toDto(book)).thenReturn(expected);
        when(bookRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(book));

        //When
        BookDto actual = bookService.findById(1L);

        //Then
        assertEquals(expected, actual);
        verify(bookRepository).findById(any(Long.class));
        verify(bookMapper, times(1)).toDto(any(Book.class));
    }
}
