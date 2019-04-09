package com.diasonti.descriptiontinder.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.diasonti.descriptiontinder.config.security.jwt.JWTConfig.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    private final UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, UserDetailsService userDetailsService) {
        super(authManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_NAME);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(HEADER_NAME);
        if (token != null) {
            String userName = null;
            try {
                userName = JWT.require(HMAC512(SECRET.getBytes()))
                        .build()
                        .verify(token.replace(TOKEN_PREFIX, ""))
                        .getSubject();
            } catch (JWTVerificationException e) {
                log.warn("Invalid token received. Token: '{}'", token, e);
            }

            if (userName != null) {
                final UserDetails user = userDetailsService.loadUserByUsername(userName);
                return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            }
        }
        return null;
    }

}
