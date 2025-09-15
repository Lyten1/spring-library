package mate.academy.springlibrary.service;

import java.util.List;
import mate.academy.springlibrary.model.Book;

public interface BookService {

    Book save(Book book);

    List findAll();
}
