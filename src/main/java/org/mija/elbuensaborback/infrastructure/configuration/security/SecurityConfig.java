package org.mija.elbuensaborback.infrastructure.configuration.security;

/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorizeRequests ->{
                            //authorizeRequests.requestMatchers(HttpMethod.GET,"auth/sing-in").permitAll();
                            //authorizeRequests.requestMatchers(HttpMethod.GET,"auth/sing-up").permitAll();
                            authorizeRequests.requestMatchers("auth/**").permitAll();
                            authorizeRequests.requestMatchers("admin/**").hasRole("ADMIN");
                            authorizeRequests.requestMatchers("empleado/**").hasRole("EMPLEADO");
                            authorizeRequests.requestMatchers("cliente/**").authenticated();
                            authorizeRequests.anyRequest().denyAll();
                        }
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(null);
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /*
    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> userList = new ArrayList<>();

        UserDetails user1 = User
                .withUsername("pedro")
                .password("123")
                .roles("ADMIN").build();

        UserDetails user2 = User
                .withUsername("juan")
                .password("123")
                .roles("EMPLEADO").build();

        UserDetails user3 = User
                .withUsername("isa")
                .password("123")
                .build();

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        return new InMemoryUserDetailsManager(userList);
    }

}
*/
public class SecurityConfig {

}
