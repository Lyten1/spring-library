package mate.academy.springlibrary.service;

import mate.academy.springlibrary.model.Book;

import java.util.List;

public interface BookService {

    Book save(Book book);

    List findAll();
}
