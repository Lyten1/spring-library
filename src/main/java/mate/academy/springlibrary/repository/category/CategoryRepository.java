package mate.academy.springlibrary.repository.category;

import jakarta.validation.constraints.NotEmpty;
import mate.academy.springlibrary.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select distinct c from Category c where c.id IN :ids")
    Set<Category> findDistinctById(@NotEmpty @Param("ids") List<Long> id);
}
