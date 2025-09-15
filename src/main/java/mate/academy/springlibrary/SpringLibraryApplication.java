package mate.academy.springlibrary;

import java.math.BigDecimal;
import mate.academy.springlibrary.model.Book;
import mate.academy.springlibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringLibraryApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(SpringLibraryApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("Red flag");
            book.setAuthor("Jorge Orvel");
            book.setIsbn("qwerty");
            book.setPrice(new BigDecimal(12.99));

            bookService.save(book);

            bookService.findAll().forEach(System.out::println);
        };
    }
}
