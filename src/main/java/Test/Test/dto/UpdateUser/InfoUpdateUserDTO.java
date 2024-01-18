package Test.Test.dto.UpdateUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoUpdateUserDTO {
    private Boolean status;
    private String message;
    private UpdateDataDTO reference_data;
}
