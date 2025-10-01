package mate.academy.springlibrary.repository.books.spec;

import mate.academy.springlibrary.model.Book;
import mate.academy.springlibrary.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {

    @Override
    public String getKey() {
        return "title";
    }

    public Specification<Book> getSpecification(String[] partTitle) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                criteriaBuilder.lower(root.get("title")),
                "%" + partTitle[0].toLowerCase() + "%");
    }
}
