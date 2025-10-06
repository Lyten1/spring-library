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
    String email;

    @NotBlank
    @Length(min = 8, max = 20)
    String password;

    @NotBlank
    @Length(min = 8, max = 20)
    String repeatPassword;

    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    String shippingAddress;
}
