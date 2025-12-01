package mate.academy.springlibrary.repository;

import mate.academy.springlibrary.model.Book;
import mate.academy.springlibrary.model.Category;
import mate.academy.springlibrary.repository.books.BookRepository;
import mate.academy.springlibrary.repository.category.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("""
            Find all books by category Fantasy
            """)
    void findAllByCategoriesContaining_CategoryFantasy_ReturnTwoBooks() {
        Book book = new Book();
        book.setTitle("Hobit");
        book.setPrice(new BigDecimal(12.45));
        book.setAuthor("D. Arman");
        book.setIsbn("124566543-2134");
        Category category = new Category();
        category.setName("Fantasy");
        categoryRepository.save(category);
        book.setCategories(Set.of(category));
        bookRepository.save(book);

        List<Book> actual = bookRepository.findAllByCategoriesContaining(category);

        Assertions.assertEquals(1, actual.size());
    }

}
