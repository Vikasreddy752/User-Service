package com.intern.userservice.Configuration;

import com.intern.userservice.Service.UserService;
import com.intern.userservice.Token.Authfilter;
import com.intern.userservice.Token.JwtUtil;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public Authfilter authfilter(JwtUtil jwtUtil,UserDetailsService userDetailsService) {
        return new Authfilter(jwtUtil,userDetailsService);
    }
    @Bean
    public BCryptPasswordEncoder bCryptEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider AuthenticationProvider(UserDetailsService userDetailsService,
                                                            BCryptPasswordEncoder bCryptEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain security(HttpSecurity http,Authfilter authfilter) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((requests) ->
            requests.requestMatchers("/signup","/login")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
        )
        .addFilterBefore(authfilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
