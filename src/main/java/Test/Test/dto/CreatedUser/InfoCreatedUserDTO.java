package Test.Test.dto.CreatedUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoCreatedUserDTO {
    private Boolean status;
    private String message;
    private CreatedDataDTO reference_data;
}
