package Test.Test.dto.DeleteUser;

import Test.Test.dto.UpdateUser.UpdateDataDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoDeleteUserDTO {
    private Boolean status;
    private String message;
    private DeleteDataDTO reference_data;
}
