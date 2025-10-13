package mate.academy.springlibrary.repository.category;

import java.util.List;
import mate.academy.springlibrary.model.Book;
import mate.academy.springlibrary.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Book> findAllByCategoryId(Long categoryId);
}
