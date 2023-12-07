package com.spring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

    //config role with authorities() method
//    @Bean
//    public UserDetailsService userDetailsService(){
//        var manager = new InMemoryUserDetailsManager();
//        var user1 = User.withUsername("john").password("12345").authorities("ROLE_ADMIN").build();
//        var user2 = User.withUsername("jane").password("12345").authorities("ROLE_MANAGER").build();
//        manager.createUser(user1);
//        manager.createUser(user2);
//        return manager;
//    }

    //config role with role() method
    @Bean
    public UserDetailsService userDetailsService(){
        var manager = new InMemoryUserDetailsManager();
        var user1 = User.withUsername("john").password("12345").roles("ADMIN").build();
        var user2 = User.withUsername("jane").password("12345").roles("MANAGER").build();
        manager.createUser(user1);
        manager.createUser(user2);
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        // config access with hasRole() method
        //http.authorizeRequests().anyRequest().hasRole("ADMIN");

        // config access after time with access() method()
        http.authorizeRequests().anyRequest().access("T(java.time.LocalTime).now().isAfter(T(java.time.LocalTime).of(10, 45))");
    }
}
