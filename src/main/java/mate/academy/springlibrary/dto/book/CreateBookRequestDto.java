package mate.academy.springlibrary.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Book's DTO")
public class CreateBookRequestDto {

    @Schema(description = "Book's title")
    private String title;
    @Schema(description = "Book's author")
    private String author;
    @Schema(description = "Book's isbn", example = "123-4567890123")
    private String isbn;
    @Schema(description = "Book's price", example = "19.99")
    private BigDecimal price;
    private String description;
    private String coverImage;
}
