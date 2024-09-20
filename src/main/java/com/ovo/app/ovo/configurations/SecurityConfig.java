package com.ovo.app.ovo.configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/*").permitAll()
                                .requestMatchers("/signup").permitAll()
                                .requestMatchers("/dashboard", "update-password").authenticated()
                                .requestMatchers("/logout").authenticated()
                                .requestMatchers("/account").authenticated()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/leaderboard").authenticated()
                                .requestMatchers("../static/assets").permitAll()
                                .requestMatchers("/assets/**").permitAll()
                                .requestMatchers("/api/games/**").authenticated()
                                .requestMatchers("/adminDashboard").hasRole("ADMIN")
                                .requestMatchers("/gameManagement").hasRole("ADMIN")
                                .requestMatchers("/reports").hasRole("ADMIN")
                                .requestMatchers("/js/*.js", "/css/*.css").permitAll()
                                .requestMatchers("/game").authenticated()
                                .requestMatchers("/game/**").authenticated()
                                .requestMatchers("/gameplay").authenticated()
                                .requestMatchers("/gameplay/**").authenticated()

                ).formLogin(formLogin ->
                        formLogin.loginPage("/login")
                                .successHandler(authenticationSuccessHandler())
                ).logout(config -> config
                        .logoutSuccessUrl("/login")).build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {

            if (authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                response.sendRedirect("/adminDashboard");
            } else {
                response.sendRedirect("/dashboard");
            }
        };
    }
}