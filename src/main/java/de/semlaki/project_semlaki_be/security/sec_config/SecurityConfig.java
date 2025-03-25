package de.semlaki.project_semlaki_be.security.sec_config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Этот метод отключает защиту CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // Этот метод служит для настройки сессий.
                // При текущей настройке мы сессии отключили,
                // то есть сервер будет просить логин/пароль при каждом запросе
                .sessionManagement(x -> x
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Включаем базовую авторизацию (при помощи логина и пароля)
                .httpBasic(Customizer.withDefaults())
                // При помощи этого метода мы конфигурируем доступ к разному функционалу
                // приложения для разных ролей пользователей
                .authorizeHttpRequests(x -> x
                        .requestMatchers(HttpMethod.GET, "/products/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
                        .anyRequest().authenticated()
                        .requestMatchers("/auth/**").permitAll()
                ).build();
    }
}
