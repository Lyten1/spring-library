package mate.academy.springlibrary.mapper;

import mate.academy.springlibrary.config.MapperConfig;
import mate.academy.springlibrary.dto.category.CategoryDto;
import mate.academy.springlibrary.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto categoryDto);

    void updateCategoryFromDto(CategoryDto dto, @MappingTarget Category category);
}
