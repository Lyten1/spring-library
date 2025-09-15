package mate.academy.springlibrary.mapper;

import mate.academy.springlibrary.config.MapperConfig;
import mate.academy.springlibrary.dto.BookDto;
import mate.academy.springlibrary.dto.CreateBookRequestDto;
import mate.academy.springlibrary.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {

    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
