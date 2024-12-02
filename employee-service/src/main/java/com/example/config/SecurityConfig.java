package com.example.config;

import com.example.jwt.AuthEntryPointJwt;
import com.example.jwt.JwtFilter;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthEntryPointJwt authEntryPointJwt;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(request ->
                        request.requestMatchers(
                                "/auth/login",
                                        "/employees/hierarchy/{id}",
                                        "/employees/hierarchy",
                                        "/managers")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .cors(cors -> cors.configure(http))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(e ->
                        e.authenticationEntryPoint(authEntryPointJwt))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(employeeService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

//    @Bean
//    public FilterRegistrationBean<CorsFilter> coresFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.addAllowedOriginPattern(ConstantValues.STAR);
//        corsConfiguration.addAllowedHeader(ConstantValues.AUTHORIZATION);
//        corsConfiguration.addAllowedHeader(ConstantValues.CONTENT_TYPE);
//        corsConfiguration.addAllowedHeader(ConstantValues.ACCEPT);
//        corsConfiguration.addAllowedMethod(ConstantValues.GET);
//        corsConfiguration.addAllowedMethod(ConstantValues.POST);
//        corsConfiguration.addAllowedMethod(ConstantValues.DELETE);
//        corsConfiguration.addAllowedMethod(ConstantValues.PUT);
//        corsConfiguration.addAllowedMethod(ConstantValues.OPTIONS);
//        corsConfiguration.setMaxAge(3600L);
//        source.registerCorsConfiguration(ConstantValues.PATTERN, corsConfiguration);
//
//        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
//        bean.setOrder(-110);
//        return bean;
//    }

}
