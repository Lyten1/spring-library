package mate.academy.springlibrary.repository.role;

import mate.academy.springlibrary.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
