package mate.academy.springlibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.dto.BookDto;
import mate.academy.springlibrary.dto.BookSearchParametersDto;
import mate.academy.springlibrary.dto.CreateBookRequestDto;
import mate.academy.springlibrary.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Book controller")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all book")
    public List<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new books")
    public BookDto createBook(@RequestBody CreateBookRequestDto bookDto) {
        return bookService.save(bookDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing book")
    public BookDto updateBook(@PathVariable Long id, @RequestBody CreateBookRequestDto bookDto) {
        return bookService.update(id, bookDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete existing book")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping("/search")
    public List<BookDto> searchBooks(BookSearchParametersDto searchParameters) {
        return bookService.search(searchParameters);
    }
}
