package mate.academy.springlibrary.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.dto.user.UserLoginRequestDto;
import mate.academy.springlibrary.dto.user.UserRegistrationRequestDto;
import mate.academy.springlibrary.dto.user.UserResponseDto;
import mate.academy.springlibrary.exeption.RegistrationException;
import mate.academy.springlibrary.security.AuthenticationService;
import mate.academy.springlibrary.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto userDto) {
        return userService.register(userDto);
    }

    @PostMapping("/login")
    public boolean login(@RequestBody UserLoginRequestDto userDto) {
        return authenticationService.login(userDto);
    }
}
