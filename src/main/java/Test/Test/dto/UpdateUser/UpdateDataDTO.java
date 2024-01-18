package Test.Test.dto.UpdateUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDataDTO {
    private String entity;
    private Long pk;
    private Long updated_by;
    private LocalDateTime updated_at;
}
