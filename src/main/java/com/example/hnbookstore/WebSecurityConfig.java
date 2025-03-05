package com.example.hnbookstore;

import com.example.hnbookstore.service.BookUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final BookUserDetailsService bookUserDetailsService;

    public WebSecurityConfig(BookUserDetailsService bookUserDetailsService) {
        this.bookUserDetailsService = bookUserDetailsService;
    }

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/css/**").permitAll() 
            .requestMatchers("/h2-console/**").permitAll()  // ✅ Ensure H2 Console is accessible
            .requestMatchers("/login", "/register").permitAll()
            .requestMatchers("/addbook", "/edit/**").hasAnyRole("USER", "ADMIN")  
            .requestMatchers("/delete/**").hasRole("ADMIN")  
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/booklist", true)
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
            .permitAll()
        )
        .csrf(csrf -> csrf.disable())  // ✅ Disable CSRF globally (needed for POST requests)
        .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));  // ✅ Allow H2 Console

    return http.build();
}


    @Bean
    public UserDetailsService userDetailsService() {
        return bookUserDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(bookUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());  
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  
    }
}
