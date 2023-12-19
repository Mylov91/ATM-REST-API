package ru.mylov.ATM.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mylov.ATM.models.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(long id);
}
