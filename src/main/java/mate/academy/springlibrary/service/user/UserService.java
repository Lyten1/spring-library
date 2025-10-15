package mate.academy.springlibrary.service.user;

import mate.academy.springlibrary.dto.user.UserRegistrationRequestDto;
import mate.academy.springlibrary.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto user);
}
