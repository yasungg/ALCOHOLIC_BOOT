package startline.server.config;

import startline.server.filter.ExceptionFilter;
import startline.server.filter.JwtFilter;
import startline.server.token.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;
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
                .addFilterBefore(new ExceptionFilter(new ObjectMapper()), ExceptionTranslationFilter.class)
                .addFilterBefore(new JwtFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
                .securityMatchers(matchers -> matchers
                        .requestMatchers(
                                antMatcher("/sign/**"),
                                antMatcher("/login"),
                                antMatcher("/forall/**"),
                                antMatcher("/v3/**"),
                                antMatcher("/swagger-resources/**"),
                                antMatcher("/swagger-ui/**"),
                                antMatcher("/webjars/**"),
                                antMatcher("/swagger/**"),
                                antMatcher("/sign-api/exception"),
                                antMatcher("/user/**"),
                                antMatcher("/admin/**")
                        ))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/sign/**", "/login", "/forall/**", "/v3/**", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**", "/swagger/**", "/sign-api/exception").permitAll()
                        .requestMatchers("/pre").hasRole("PRE")
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/dr/**").hasRole("DOCTOR")
                        .requestMatchers("/counsel/**").hasRole("COUNSELOR")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
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
