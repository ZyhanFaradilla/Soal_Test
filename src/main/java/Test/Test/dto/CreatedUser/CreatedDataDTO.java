package Test.Test.dto.CreatedUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatedDataDTO {
    private String entity;
    private Long pk;
    private Long created_by;
    private LocalDateTime created_at;
}
