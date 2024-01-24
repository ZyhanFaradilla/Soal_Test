package Test.Test.dto.Account;

import Test.Test.validation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private Long id;
    @UniqueUsername(message = "Username sudah ada")
    private String username;
    private String password;
}
