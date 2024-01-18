package Test.Test.dto.Account;

import Test.Test.dto.DetailUserDTO;
import Test.Test.dto.JobDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDTO {
    private Long id;
    private String username;
    private DetailUserDTO detail;
    private List<JobDTO> jobs;
    private Long created_by;
    private LocalDateTime created_at;
}
