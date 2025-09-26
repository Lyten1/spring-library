package mate.academy.springlibrary.repository;

import java.util.List;
import mate.academy.springlibrary.model.Book;

public interface BookRepository {

    Book save(Book book);

    List<Book> findAll();

    Book getById(Long id);
}
