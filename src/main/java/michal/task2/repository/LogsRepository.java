package michal.task2.repository;

import michal.task2.model.LogsGenerator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogsRepository extends JpaRepository<LogsGenerator, Integer> {
}
