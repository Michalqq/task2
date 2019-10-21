package michal.task2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogsGenerator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String logDescription;
    private LocalDateTime date;

    public LogsGenerator(String logDescription) {
        this.logDescription = logDescription;
        this.date = LocalDateTime.now();
    }

}
