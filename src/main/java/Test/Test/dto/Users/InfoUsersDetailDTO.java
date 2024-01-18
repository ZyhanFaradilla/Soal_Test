package Test.Test.dto.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoUsersDetailDTO {
    private Boolean status;
    private List<UserDataDetailDTO> data;
}
