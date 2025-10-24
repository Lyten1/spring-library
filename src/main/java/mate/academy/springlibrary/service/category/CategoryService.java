package mate.academy.springlibrary.service.category;

import java.util.List;
import mate.academy.springlibrary.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.springlibrary.dto.category.CategoryRequestDto;
import mate.academy.springlibrary.dto.category.CategoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CategoryRequestDto categoryDto);

    CategoryResponseDto update(Long id, CategoryRequestDto categoryDto);

    void deleteById(Long id);

    List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id);

}
