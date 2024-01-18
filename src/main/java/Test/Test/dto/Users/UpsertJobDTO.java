package Test.Test.dto.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpsertJobDTO {
    private Long id;
    private String name;
    private LocalDate startAt;
    private LocalDate endAt;
}
