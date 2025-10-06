package mate.academy.springlibrary.service;

import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.dto.user.UserRegistrationRequestDto;
import mate.academy.springlibrary.dto.user.UserResponseDto;
import mate.academy.springlibrary.mapper.UserMapper;
import mate.academy.springlibrary.model.User;
import mate.academy.springlibrary.repository.users.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setShippingAddress(userDto.getShippingAddress());
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }

}
