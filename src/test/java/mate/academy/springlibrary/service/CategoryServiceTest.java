package mate.academy.springlibrary.service;

import mate.academy.springlibrary.dto.book.BookDto;
import mate.academy.springlibrary.dto.book.CreateBookRequestDto;
import mate.academy.springlibrary.dto.category.CategoryRequestDto;
import mate.academy.springlibrary.dto.category.CategoryResponseDto;
import mate.academy.springlibrary.mapper.CategoryMapper;
import mate.academy.springlibrary.model.Book;
import mate.academy.springlibrary.model.Category;
import mate.academy.springlibrary.repository.category.CategoryRepository;
import mate.academy.springlibrary.service.category.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void save_ValidCategory_ShouldSaveCategory() {

        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName("Fantasy");

        CategoryResponseDto expected = new CategoryResponseDto();
        expected.setName("Fantasy");

        Category category = new Category();
        category.setId(1L);
        category.setName("Fantasy");


        when(categoryMapper.toEntity(requestDto)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(expected);
        when(categoryRepository.save(category)).thenReturn(category);

        //When
        CategoryResponseDto savedBook = categoryService.save(requestDto);

        assertThat(savedBook).isEqualTo(expected);
        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(categoryMapper, times(1)).toDto(any(Category.class));
        verify(categoryMapper, times(1)).toEntity(any(CategoryRequestDto.class));
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    public void findAll_ValidCategory_ShouldSaveCategory() {

        List<CategoryResponseDto> expected = new ArrayList<>();
        CategoryResponseDto firstExpectedCategory = new CategoryResponseDto();
        firstExpectedCategory.setName("Fantasy");
        CategoryResponseDto secondExpectedCategory = new CategoryResponseDto();
        firstExpectedCategory.setName("Horror");
        expected.add(firstExpectedCategory);
        expected.add(secondExpectedCategory);

        List<Category> categories = new ArrayList<>();
        Category firstCategory = new Category();
        firstCategory.setId(1L);
        firstCategory.setName("Fantasy");
        Category secondCategory = new Category();
        secondCategory.setId(1L);
        secondCategory.setName("Fantasy");
        categories.add(firstCategory);
        categories.add(secondCategory);

        when(categoryMapper.toDto(firstCategory)).thenReturn(firstExpectedCategory);
        when(categoryMapper.toDto(secondCategory)).thenReturn(secondExpectedCategory);
        when(categoryRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(categories));

        Pageable pageable = PageRequest.of(0, 10);
        //When
        Page<CategoryResponseDto> actual = categoryService.findAll(pageable);

        assertEquals(new PageImpl<>(expected), actual);
        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
        verify(categoryMapper, times(2)).toDto(any(Category.class));
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

}
