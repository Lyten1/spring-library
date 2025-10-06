package mate.academy.springlibrary.repository.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import mate.academy.springlibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@NotBlank @Email String email);
}
