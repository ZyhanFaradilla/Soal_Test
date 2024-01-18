package Test.Test.dto.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoOneUserDetailDTO {
    private Boolean status;
    private UserDataDetailDTO data;
}
