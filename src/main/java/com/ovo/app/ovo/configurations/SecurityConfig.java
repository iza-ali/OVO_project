package com.ovo.app.ovo.configurations;


import com.ovo.app.ovo.enums.PlayerTypeEnum;
import com.ovo.app.ovo.services.PlayerDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
//                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/*").permitAll()
                                .requestMatchers("/signup").permitAll()
                                .requestMatchers("/dashboard", "update-password").authenticated()
                                .requestMatchers("/logout").authenticated()
                                .requestMatchers("/account").authenticated()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/game").permitAll()
                                .requestMatchers("/game/start").permitAll()
                                .requestMatchers("/tictactoe").authenticated()
                                .requestMatchers("/leaderboard").authenticated()
                                .requestMatchers("../static/assets").permitAll()
                                .requestMatchers("/gameManagement").hasAuthority(String.valueOf(PlayerTypeEnum.ROLE_ADMIN))
                                .requestMatchers("/js/*.js", "/css/*.css").permitAll()


                ).formLogin(formLogin ->
                        formLogin.loginPage("/login")
                                .defaultSuccessUrl("/dashboard", true)
                ).logout(config -> config.logoutUrl("/logout")
                        .logoutSuccessUrl("/login")).build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}