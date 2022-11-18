package pizzas.authorization;

import pizzas.authorization.users.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeRequests(authorizeRequest ->
                        authorizeRequest.anyRequest().authenticated())
                .formLogin()
                .and().build();
    }

    @Bean
    UserDetailsService userDetails(UserRepository userRepository) {
        return userRepository::findByUsername;
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
