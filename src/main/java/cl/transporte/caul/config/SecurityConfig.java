package cl.transporte.caul.config;

import cl.transporte.caul.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // ---- RUTAS PÚBLICAS ----
                .requestMatchers(
                        "/login",
                        "/css/**",
                        "/js/**",
                        "/img/**",
                        "/favicon.ico"
                ).permitAll()

                // Endpoint temporal para crear admin inicial (recuerda cerrarlo luego)
                .requestMatchers("/api/usuarios/setup-admin").permitAll()

                // ---- SOLO ADMIN (maestros sensibles / configuración) ----
                .requestMatchers(
                        "/admin/usuarios/**",
                        "/admin/config/**",
                        "/admin/contratos/**"
                ).hasRole("ADMIN")

                // ---- CONDUCTOR: SOLO su vista móvil (IMPORTANTE: antes del /aplicacion/**) ----
                .requestMatchers(
                        "/aplicacion/servicios",
                        "/aplicacion/servicios/**"
                ).hasRole("CONDUCTOR")

                // ---- ADMIN u OPERADOR: panel y maestros operativos ----
                .requestMatchers(
                        "/admin",
                        "/admin/dashboard",
                        "/admin/servicios/**",
                        "/admin/conductores/**",
                        "/admin/vehiculos/**",
                        "/admin/clientes/**",
                        "/admin/rutas/**",
                        "/aplicacion/**"
                ).hasAnyRole("ADMIN", "OPERADOR")

                // ---- CLIENTE CORPORATIVO ----
                .requestMatchers(
                        "/cliente/**",
                        "/api/cliente/**"
                ).hasRole("CLIENTE_CORP")

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(roleBasedSuccessHandler())
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );

        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(customUserDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("""
                ROLE_ADMIN > ROLE_OPERADOR
                ROLE_OPERADOR > ROLE_CONDUCTOR
                ROLE_CONDUCTOR > ROLE_CLIENTE_CORP
                """);
        return hierarchy;
    }

    @Bean
    public AuthenticationSuccessHandler roleBasedSuccessHandler() {
        return (request, response, authentication) -> {
            String targetUrl = resolveTargetUrl(authentication);
            response.sendRedirect(targetUrl);
        };
    }

    private String resolveTargetUrl(Authentication authentication) throws IOException {
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        if (roles.contains("ROLE_ADMIN") || roles.contains("ROLE_OPERADOR")) {
            return "/admin";
        } else if (roles.contains("ROLE_CONDUCTOR")) {
            return "/aplicacion/servicios";
        } else if (roles.contains("ROLE_CLIENTE_CORP")) {
            return "/cliente/servicios";
        }
        return "/";
    }
}