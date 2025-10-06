package mate.academy.springlibrary.security;

import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.dto.user.UserLoginRequestDto;
import mate.academy.springlibrary.model.User;
import mate.academy.springlibrary.repository.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    public boolean login(UserLoginRequestDto userDto) {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        return user.isPresent() && user.get().getPassword().equals(userDto.getPassword());
    }
}
