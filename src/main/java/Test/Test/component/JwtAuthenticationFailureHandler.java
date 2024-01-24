package Test.Test.component;

import Test.Test.dto.Token.ResponseFailLoginDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ResponseFailLoginDTO fail = new ResponseFailLoginDTO(false, "Unauthorized");
        response.getWriter().print("Status: " + fail.getStatus() +"\n" + "message: " + fail.getMessage());
        response.getWriter().flush();
    }
}
