package Test.Test.dto.Token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestTokenDTO {
    private Long id;
    private String username;
    private String password;
    private String subject;
    private String secretKey;
    private String audience;
}
