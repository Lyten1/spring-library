package mate.academy.springlibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.springlibrary.dto.category.CategoryRequestDto;
import mate.academy.springlibrary.dto.category.CategoryResponseDto;
import mate.academy.springlibrary.service.category.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Tag(name = "Category Controller")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "Create new category")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto createCategory(@RequestBody @Valid CategoryRequestDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all categories",
            description = "Get all categories supporting pagination and sorting")
    @PreAuthorize("hasRole('USER')")
    public Page<CategoryResponseDto> getAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by Id")
    @PreAuthorize("hasRole('USER')")
    public CategoryResponseDto getCategoryById(@RequestParam Long id) {
        return categoryService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing category")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponseDto updateCategory(@RequestParam Long id,
                                              @RequestBody @Valid CategoryRequestDto categoryDto) {
        return categoryService.update(id, categoryDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete existing category")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@RequestParam Long id) {
        categoryService.deleteById(id);
    }

    @GetMapping("/{id}/books")
    @Operation(summary = "Get books by category")
    @PreAuthorize("hasRole('USER')")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(@RequestParam Long id) {
        return categoryService.getBooksByCategoryId(id);
    }
}
