package Test.Test.dto.Users;

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
public class UserDataDetailDTO {
    private Long id;
    private String username;
    private DetailUserDTO detail;
    private List<JobDTO> jobs;
    private Long created_by;
    private LocalDateTime created_at;
    private Long update_by;
    private LocalDateTime update_at;
    private Long delete_by;
    private LocalDateTime delete_at;

    public UserDataDetailDTO(Long id, String username, Long created_by, LocalDateTime created_at, Long update_by, LocalDateTime update_at, Long delete_by, LocalDateTime delete_at) {
        this.id = id;
        this.username = username;
        this.created_by = created_by;
        this.created_at = created_at;
        this.update_by = update_by;
        this.update_at = update_at;
        this.delete_by = delete_by;
        this.delete_at = delete_at;
    }
}
