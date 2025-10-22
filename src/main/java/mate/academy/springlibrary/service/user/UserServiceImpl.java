package mate.academy.springlibrary.service.user;

import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.dto.user.UserRegistrationRequestDto;
import mate.academy.springlibrary.dto.user.UserResponseDto;
import mate.academy.springlibrary.exeption.EntityNotFoundException;
import mate.academy.springlibrary.exeption.RegistrationException;
import mate.academy.springlibrary.mapper.UserMapper;
import mate.academy.springlibrary.model.Role;
import mate.academy.springlibrary.model.RoleName;
import mate.academy.springlibrary.model.User;
import mate.academy.springlibrary.repository.role.RoleRepository;
import mate.academy.springlibrary.repository.users.UserRepository;
import mate.academy.springlibrary.service.shoppingcart.ShoppingCartService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ShoppingCartService shoppingCartService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RegistrationException("User with email: "
                    + userDto.getEmail() + " has already registered");
        }
        User user = userMapper.toModel(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        user.setRoles(Set.of(userRole));
        User savedUser = userRepository.save(user);
        shoppingCartService.create(savedUser);
        return userMapper.toResponseDto(savedUser);
    }

}
