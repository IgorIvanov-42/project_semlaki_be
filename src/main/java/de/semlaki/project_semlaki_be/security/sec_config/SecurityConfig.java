package de.semlaki.project_semlaki_be.security.sec_config;


import de.semlaki.project_semlaki_be.security.sec_filter.TokenFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final String ADMIN_ROLE = "ADMIN";
    private final String USER_ROLE = "USER";
    private final TokenFilter tokenFilter;


    public SecurityConfig(TokenFilter tokenFilter) {
        this.tokenFilter = tokenFilter;

    }
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
                .httpBasic(AbstractHttpConfigurer::disable )

                // При помощи этого метода мы конфигурируем доступ к разному функционалу
                // приложения для разных ролей пользователей
                .authorizeHttpRequests(x -> x
                        .requestMatchers(HttpMethod.GET, "/products/all").permitAll()

                        .requestMatchers(HttpMethod.GET, "/auth/health").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register").permitAll()

                        .requestMatchers(HttpMethod.GET, "/services").permitAll()

                        .requestMatchers("/auth/**")
                        .permitAll()
                        .anyRequest().authenticated()
                ).addFilterAfter(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
