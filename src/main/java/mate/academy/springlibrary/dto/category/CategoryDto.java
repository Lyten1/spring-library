package mate.academy.springlibrary.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
