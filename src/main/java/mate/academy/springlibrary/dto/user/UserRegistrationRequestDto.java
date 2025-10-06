package mate.academy.springlibrary.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import mate.academy.springlibrary.annotation.fieldmatch.FieldMatch;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(first = "password", second = "repeatPassword", message = "Password must match")
public class UserRegistrationRequestDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 8, max = 20)
    private String password;

    @NotBlank
    @Length(min = 8, max = 20)
    private String repeatPassword;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String shippingAddress;
}
