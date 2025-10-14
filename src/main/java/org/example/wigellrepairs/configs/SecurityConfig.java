package org.example.wigellrepairs.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception {
        https
            .csrf(c->c.disable())
            .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/wigellrepairs/services").hasAnyRole("ADMIN", "USER")
                .requestMatchers(
                    "/wigellrepairs/listcanceled",
                    "/wigellrepairs/listpast",
                    "/wigellrepairs/listupcoming",
                    "/wigellrepairs/addservice",
                    "/wigellrepairs/updateservice",
                    "/wigellrepairs/remservice/*",
                    "/wigellrepairs/addtechnician",
                    "/wigellrepairs/technicians").hasRole("ADMIN")
                .requestMatchers(
                    "/wigellrepairs/bookservice",
                    "/wigellrepairs/cancelbooking",
                    "/wigellrepairs/mybookings").hasRole("USER")
                .anyRequest().authenticated()
            );
        return https.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
            User.withUsername("admin")
                .password("{noop}admin")
                .roles("ADMIN")
                .build(),
            User.withUsername("anna.andersson@mail.se")
                .password("{noop}1234")
                .roles("USER")
                .build(),
            User.withUsername("erik.eriksson@mail.se")
                .password("{noop}5678")
                .roles("USER")
                .build(),
            User.withUsername("maria.malm@mail.se")
                .password("{noop}9101")
                .roles("USER")
                .build()
        );
    }
}
