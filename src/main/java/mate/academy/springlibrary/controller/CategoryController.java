package mate.academy.springlibrary.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.springlibrary.dto.category.CategoryDto;
import mate.academy.springlibrary.service.CategoryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    //TODO Add Pagination, Sorting, and Swagger to all controllers you have
    //TODO Don't forget to use Liquibase

    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @GetMapping("/all")
    public List<CategoryDto> getAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@RequestParam Long id) {
        return categoryService.getById(id);
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategory(@RequestParam Long id,
                                      @RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.update(id, categoryDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@RequestParam Long id) {
        categoryService.deleteById(id);
    }

    @GetMapping("/{id}/books")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(@RequestParam Long id) {
        return categoryService.getBooksByCategoryId(id);
    }
}
