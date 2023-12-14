package com.example.odyssey.sacurity;

import com.example.odyssey.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private TokenUtil tokenUtil;
    private UserDetailsService userDetailsService;
    protected final Log LOGGER = LogFactory.getLog(getClass());
    public TokenAuthenticationFilter(TokenUtil tokenHelper, UserDetailsService userDetailsService) {
        this.tokenUtil = tokenHelper;
        this.userDetailsService = userDetailsService;
    }
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String username;
        String authToken = tokenUtil.getToken(request);
        try {
            if (authToken != null) {
                username = tokenUtil.getUsernameFromToken(authToken);
                if (username != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (tokenUtil.validateToken(authToken, userDetails)) {
                        TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                        authentication.setToken(authToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }

        } catch (ExpiredJwtException ex) {
            LOGGER.debug("Token expired!");
        }

        chain.doFilter(request, response);
    }
}
