package Test.Test.dto.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpsertUserDetailDTO {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
}
