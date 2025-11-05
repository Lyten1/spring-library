package mate.academy.springlibrary.repository;

import mate.academy.springlibrary.model.Book;
import mate.academy.springlibrary.model.Category;
import mate.academy.springlibrary.repository.books.BookRepository;
import mate.academy.springlibrary.repository.category.CategoryRepository;
import org.hibernate.annotations.processing.SQL;
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
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("""
            Find distinct categories by ids
            """)
    void findDistinctById_ValidId_ReturnDistinctCategory() {
        Category category = new Category();
        category.setName("Fantasy");
        Category category1 = new Category();
        category1.setName("Horror");
        Category category2 = new Category();
        category2.setName("Detective");
        categoryRepository.saveAll(List.of(category, category1, category2));

        Set<Category> actual = categoryRepository.findDistinctById(
                List.of(category.getId(), category1.getId()));

        Assertions.assertEquals(2, actual.size());
    }

}
