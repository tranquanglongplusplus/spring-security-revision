package com.spring.security.config;

import com.spring.security.Model.User;
import com.spring.security.services.CustomInMemoryUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@AllArgsConstructor
public class ProjectConfiguration {

    private final DataSource dataSource;

    @Bean
    public UserDetailsService customInMemoryUserDetailsService(){
        UserDetails u1 = new User("longtq1", "abc123", "admin");
        UserDetails u2 = new User("longtq2", "abc123", "user");
        List<UserDetails> users = List.of(u1,u2);
        return new CustomInMemoryUserDetailsService(users);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService customJdbcUserDetailsService(DataSource dataSource) {
        String usersByUsernameQuery =
                "select username, password, enabled from security_custom.customer where username = ?";
        String authsByUserQuery =
                "select username, authority from security_custom.customer where username = ?";
        var customJdbcUserDetailsService = new JdbcUserDetailsManager(dataSource);
        customJdbcUserDetailsService.setUsersByUsernameQuery(usersByUsernameQuery);
        customJdbcUserDetailsService.setAuthoritiesByUsernameQuery(authsByUserQuery);
        return customJdbcUserDetailsService;
    }

    @Bean
    public InitializingBean initializingBean() {
        return () -> SecurityContextHolder.setStrategyName(
                SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
//        return () -> SecurityContextHolder.setStrategyName(
//                SecurityContextHolder.MODE_GLOBAL);
//        return () -> SecurityContextHolder.setStrategyName(
//                SecurityContextHolder.MODE_GLOBAL);
    }
}
