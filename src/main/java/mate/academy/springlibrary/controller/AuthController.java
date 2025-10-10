package mate.academy.springlibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.dto.user.UserLoginRequestDto;
import mate.academy.springlibrary.dto.user.UserLoginResponseDto;
import mate.academy.springlibrary.dto.user.UserRegistrationRequestDto;
import mate.academy.springlibrary.dto.user.UserResponseDto;
import mate.academy.springlibrary.security.AuthenticationService;
import mate.academy.springlibrary.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authorization controller")
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    @Operation(summary = "Register user")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto userDto) {
        return userService.register(userDto);
    }

    @PostMapping("/login")
    @Operation(summary = "Authorize user")
    public UserLoginResponseDto login(UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }

}
