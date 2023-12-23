package ru.mylov.ATM.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mylov.ATM.models.Operation;

public interface OperationsRepository extends JpaRepository<Operation, Integer> {
}
