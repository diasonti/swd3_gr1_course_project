package com.diasonti.descriptiontinder.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

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
                .and().httpBasic().authenticationEntryPoint(apiBasicAuthenticationEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }

    @Bean
    public BasicAuthenticationEntryPoint apiBasicAuthenticationEntryPoint() {
        final BasicAuthenticationEntryPoint entryPoint = new ApiBasicAuthenticationEntryPoint();
        entryPoint.setRealmName("DTINDER");
        return entryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
