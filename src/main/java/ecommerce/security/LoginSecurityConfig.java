package ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

//Author: Theingi Myint

@Configuration
public class LoginSecurityConfig {

    //Connects to DB for user authentication
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    //API Chain (React Admin)
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/product/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/api/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );
        return http.build();
    }

    // Web Chain (Thymeleaf Customer)
    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(
                                        "/register**", "/", "/category**", "/search**",
                                        "/login",
                                        "/css/**", "/js/**", "/images/**", "/h2-console/**"
                                ).permitAll()
                                .requestMatchers("/admin**", "/api/product/**").hasRole("ADMIN")
                                .requestMatchers( "/order/**", "/checkout", "/payment",
                                        "/cart/**","/process-checkout","/checkout-successful", "/profile**"
                                ).hasRole("CUSTOMER")

                                .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser")
                                .successHandler((request, response, authentication) -> {
                                    var authorities = authentication.getAuthorities();
                                    String redirectURL = request.getContextPath();
                                    boolean isAdmin = authorities.stream()
                                            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
                                    if (isAdmin) {
                                        redirectURL += "/admin";
                                    }else{
                                    	/*INTERGRATION CODE
                                    	 * Initializes the customer and cart objects from @GetMapping("/initialize-customer-cart") in LoginController.  */
                                        redirectURL += "/initialize-customer-cart";
                                    }
                                    response.sendRedirect(redirectURL);
                                })
                                .failureUrl("/login?error")
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex.accessDeniedPage("/accessDeniedPage"));

        return http.build();
    }
}