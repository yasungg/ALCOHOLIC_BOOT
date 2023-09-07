package ALCOHOLIC_BOOT.server.config;

import ALCOHOLIC_BOOT.server.constant.Authority;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean
    private void sharedSecurityConfig(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .securityMatcher("/auth/**", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**", "/sign-api/exception")
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().permitAll())
                .securityMatcher("/user/**")
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().hasRole(Authority.ROLE_USER.name()))
                .securityMatcher("/admin")
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().hasRole(Authority.ROLE_ADMIN.name()))
                ;
    }

    @Bean
    public SecurityFilterChain formLogin(HttpSecurity http) throws Exception {
        sharedSecurityConfig(http);
        http
                .formLogin(formLogin -> {
                    formLogin
                            .loginPage("/auth/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .successHandler(
                                    new AuthenticationSuccessHandler() {
                                        @Override
                                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                            response.sendRedirect("http://localhost:3000/");
                                        }
                                    }
                            ).failureHandler(
                                    new AuthenticationFailureHandler() {
                                        @Override
                                        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                                            response.setHeader("message", "로그인 실패!");
                                            response.sendRedirect("http://localhost:3000/login");
                                        }
                                    }
                            );
                });
        return http.build();
    }
    @Bean
    private CorsConfigurationSource corsSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.addAllowedHeader("*");
        cors.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "PATCH", "DELETE"));
        cors.addAllowedOrigin("http://localhost:3000");
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", cors);
        return corsSource;
    }

}
