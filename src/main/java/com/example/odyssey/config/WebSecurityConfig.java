package com.example.odyssey.config;

import com.example.odyssey.security.RestAuthenticationEntryPoint;
import com.example.odyssey.security.TokenAuthenticationFilter;
import com.example.odyssey.services.CustomUserDetailsService;
import com.example.odyssey.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Bean
    public WebMvcConfigurer CORSConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                        .allowCredentials(false);
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Autowired
    private TokenUtil tokenUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.securityContext((securityContext) -> securityContext
                .securityContextRepository(new RequestAttributeSecurityContextRepository()));

        http.cors(cors -> CORSConfigurer());
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(restAuthenticationEntryPoint));

        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/error").permitAll()
                .requestMatchers("/ws").permitAll()
                .requestMatchers("/ws/*").permitAll()
                .requestMatchers("/ws/**").permitAll()
                .requestMatchers(HttpMethod.DELETE,
                        "api/v1/reviews/accommodation/*").permitAll()
                .requestMatchers(HttpMethod.PUT,
                        "/api/v1/reviews/accommodation/report/*").permitAll()
                .requestMatchers(HttpMethod.POST,
                        "/api/v1/users/login",
                        "/api/v1/users/register",
                        "/api/v1/users/confirmEmail/*",
                        "/api/v1/reviews/accommodation"

                ).permitAll()
                .requestMatchers(HttpMethod.GET,
                        "/api/v1/users/image/*",
                        "/api/v1/accommodations",
                        "api/v1/accommodations/stats/period",
                        "/api/v1/accommodations/*",
                        "/api/v1/accommodations/*/images",
                        "/api/v1/accommodations/*/images/*",
                        "/api/v1/accommodations/*/totalPrice",
                        "/api/v1/accommodations/stats/*",
                        "/api/v1/accommodationRequests/*/images",
                        "/api/v1/reviews/*",
                        "/api/v1/reviews/accommodation/host/*",
                        "/api/v1/accommodationRequests/*/images/*",
                        "/api/v1/reviews/accommodation/report/*",
                        "/api/v1/reviews/accommodation/*",
                        "/api/v1/reviews/host/rating/*",
                        "/api/v1/reviews/accommodation/rating/*"
                ).permitAll()
                .anyRequest().authenticated());
        http.addFilterBefore(new TokenAuthenticationFilter(tokenUtil, userDetailsService()), UsernamePasswordAuthenticationFilter.class);

        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(HttpMethod.GET, "/", "/webjars/*", "/*.html", "favicon.ico", "/*/*.html", "/*/*.css", "/*/*.js");
    }
}
