package Test.Test.dto.Token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseTokenDTO {
    private Boolean status;
    private String message;
    private String access_token;
    private String refresh_token;
}
