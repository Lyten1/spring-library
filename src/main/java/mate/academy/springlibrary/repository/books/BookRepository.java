package mate.academy.springlibrary.repository.books;

import java.util.List;
import mate.academy.springlibrary.model.Book;
import mate.academy.springlibrary.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    List<Book> findAllByCategoriesContaining(Category category);

}
