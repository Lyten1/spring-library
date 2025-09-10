package mate.academy.springlibrary.repository;

import mate.academy.springlibrary.model.Book;

import java.util.List;

public interface BookRepository {

    Book save(Book book);

    List findAll();
}
