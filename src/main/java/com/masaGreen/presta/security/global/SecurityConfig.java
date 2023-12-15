package com.masaGreen.presta.security.global;

import com.masaGreen.presta.security.CustomUserDetailsService;
import com.masaGreen.presta.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // httpSecurity
        //         .authorizeHttpRequests(auth -> {
        //             auth.requestMatchers("/swagger-ui.html","/swagger-ui.html", "/favicon.ico","/swagger/**","/swagger-ui/**").permitAll().anyRequest().authenticated();

        //         })
        //         .csrf(csrf->csrf.disable())
        //         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        //         .cors(cors-> cors.disable())
                
        //         .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        //         .headers(headers->headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

        //          .exceptionHandling(exceptionHandling->exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint));
                
        // return httpSecurity.build();
        http
        .cors(Customizer.withDefaults())
        .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
        .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/**", "/oauth2/**", "auth/forgot-password", "/auth/refreshToken", "/static/**", "/.well-known/acme-challenge/**",
                        "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/auth/login", "login/**", "/auth/validate/**", "/oauth2/**",
                        "/actuator/**", "/users/**", "/styles/**", "/favicon", "/storage/**", "/error/**", "mentee/profile-header/**", "mentee/bio/**", "mentor/bio/**","mentor/profile-header/**",
                        "availability/week/**", "/contact","mentor/expertise/**", "mentee/interests/**", "mentee/get-similar/**", "mentor/experiences/**", "mentee/experiences/**", "stats/mentor/**", "stats/mentee/**", "group-sessions/get/**","group-sessions/get-slug/**", "landing-page/**", "group-sessions/mentor/get-all/**").permitAll()
                .anyRequest().authenticated())
        .exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
        );
http.addFilterBefore(
        jwtFilter,
        UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        return  daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
