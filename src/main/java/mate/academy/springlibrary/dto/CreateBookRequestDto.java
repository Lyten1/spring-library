package mate.academy.springlibrary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Book's DTO")
public class CreateBookRequestDto {

    @NotNull
    @Schema(description = "Book's title")
    private String title;
    @Schema(description = "Book's author")
    @NotNull
    private String author;
    @Schema(description = "Book's isbn", example = "123-4567890123")
    @NotNull
    private String isbn;
    @Schema(description = "Book's price", example = "19.99")
    @NotNull
    @Min(0)
    private BigDecimal price;

    private String description;
    private String coverImage;
}
