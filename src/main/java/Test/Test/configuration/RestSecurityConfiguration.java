package Test.Test.configuration;

import Test.Test.component.JwtRequestFilter;
import Test.Test.dto.Token.ResponseFailLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class RestSecurityConfiguration {
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.securityMatcher("/api/**").csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/api/account/authenticate", "/api/account/register").permitAll()
                        .anyRequest().authenticated()
                ).sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).cors((request) -> request
                        .configurationSource(corsConfigurationSource())
                ).addFilterBefore(
                        jwtRequestFilter, UsernamePasswordAuthenticationFilter.class
                ).httpBasic((entry) -> entry
                        .authenticationEntryPoint(authenticationEntryPoint())
//                ).exceptionHandling((exception) -> exception
//                        .accessDeniedHandler(accessDeniedHandler())
                );
        return httpSecurity.build();
    }

    private AccessDeniedHandler accessDeniedHandler(){
        return ((request, response, accessDeniedException) -> {
            response.setStatus(403);
            response.getWriter().write("Access denied");
        });
    }

    private AuthenticationEntryPoint authenticationEntryPoint(){
        return ((request, response, accessDeniedException) -> {
            response.setStatus(401);
            response.getStatus();
            ResponseFailLoginDTO fail = new ResponseFailLoginDTO(false, "Unauthorized");
            response.getWriter().print("Status: " + fail.getStatus() +"\n" + "message: " + fail.getMessage());
        });
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH","DELETE"));
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
