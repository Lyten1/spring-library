package mate.academy.springlibrary.repository;

import mate.academy.springlibrary.model.Category;
import mate.academy.springlibrary.repository.category.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
        Category firstCategory = new Category();
        firstCategory.setName("Fantasy");
        Category secondCategory = new Category();
        secondCategory.setName("Horror");
        Category thirdCategory = new Category();
        thirdCategory.setName("Detective");
        categoryRepository.saveAll(List.of(firstCategory, secondCategory, thirdCategory));

        Set<Category> actual = categoryRepository.findDistinctById(
                List.of(firstCategory.getId(), secondCategory.getId()));

        Assertions.assertEquals(2, actual.size());
    }

}
