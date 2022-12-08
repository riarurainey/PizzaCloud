package pizzas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authenticationProvider(authenticationProvider())
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/ingredients").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/pizzas", "/api/orders/**")
//                .permitAll()
//                .antMatchers(HttpMethod.PATCH, "/api/ingredients").permitAll()
//                .antMatchers("/orders/**").permitAll()
//                .antMatchers("/**").access("permitAll")
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .and()
//                .httpBasic()
//                .realmName("Pizza Cloud")
//                .and()
//                .logout()
//                .logoutSuccessUrl("/")
//                .and()
//                .csrf()
//                .disable()
//                .headers()
//                .frameOptions()
//                .sameOrigin()
//                .and()
//                .build();
//
//    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

}
