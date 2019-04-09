package com.diasonti.descriptiontinder.config.security;

import com.diasonti.descriptiontinder.config.security.jwt.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and().csrf().disable()
                .authorizeRequests()
                    .antMatchers("/api/auth/**", "/api/registration/**").permitAll()
                    .antMatchers("/api/**").authenticated()
                    .antMatchers("/js/**", "/css/**", "/img/**", "/favicon.ico", "/test/**").permitAll()
                    .antMatchers("/**").permitAll()
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), customUserDetailsService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
