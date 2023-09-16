package ALCOHOLIC_BOOT.server.config;

import ALCOHOLIC_BOOT.server.constant.Authority;
import ALCOHOLIC_BOOT.server.filter.JwtFilter;
import ALCOHOLIC_BOOT.server.token.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final TokenService tokenService;
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/sign/**", "/login", "/forall/**", "/v3/**", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**", "/swagger/**", "/sign-api/exception").permitAll()
                        .requestMatchers("/user/**").hasAuthority(Authority.ROLE_USER.name())
                        .requestMatchers("/admin/**").hasAuthority(Authority.ROLE_ADMIN.name())
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
//                .formLogin(formLogin -> formLogin
//                        .loginProcessingUrl("/sign/in")
//                        .usernameParameter("username")
//                        .passwordParameter("password")
//                        .successHandler(
//                                new AuthenticationSuccessHandler() {
//                                    @Override
//                                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                                        response.sendRedirect("http://localhost:3000/");
//                                    }
//                                })
//                        .failureHandler(
//                                new AuthenticationFailureHandler() {
//                                    @Override
//                                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//                                        response.setHeader("message", "로그인 실패!");
//                                        response.sendRedirect("http://localhost:3000/login");
//                                    }
//                                })
//                        .permitAll())



        ;
        return http.build();
    }
    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.addAllowedHeader("*");
        cors.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "PATCH", "DELETE"));
        cors.addAllowedOrigin("http://localhost:3000");
        cors.addExposedHeader("Authorization");
        cors.addExposedHeader("RefreshToken");
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", cors);
        return corsSource;
    }

}
