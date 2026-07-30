package davisantos.dev.OrderManager.modules.user.repository;

import davisantos.dev.OrderManager.modules.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
