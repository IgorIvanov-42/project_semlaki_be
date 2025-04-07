package de.semlaki.project_semlaki_be.security.sec_config;


import de.semlaki.project_semlaki_be.security.exception.CustomAccessDeniedHandler;
import de.semlaki.project_semlaki_be.security.exception.CustomAuthenticationEntryPoint;
import de.semlaki.project_semlaki_be.security.sec_filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";

    private final TokenFilter tokenFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(TokenFilter tokenFilter,
                          CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                          CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.tokenFilter = tokenFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    /**
     * Бин для шифрования паролей с использованием BCrypt.
     *
     * @return экземпляр BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Конфигурация цепочки фильтров безопасности.
     * <p>
     * Отключает CSRF, устанавливает Stateless сессии, настраивает маршруты доступа, а также подключает кастомные обработчики исключений.
     * </p>
     *
     * @param http объект HttpSecurity для конфигурации безопасности
     * @return настроенная цепочка фильтров SecurityFilterChain
     * @throws Exception в случае ошибки конфигурации
     */
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
                .httpBasic(AbstractHttpConfigurer::disable)

                // При помощи этого метода мы конфигурируем доступ к разному функционалу
                // приложения для разных ролей пользователей
                .authorizeHttpRequests(x -> x
                        .requestMatchers(HttpMethod.GET, "/products/all").permitAll()

                        .requestMatchers(HttpMethod.GET, "/auth/health").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories").permitAll()

                        .requestMatchers(HttpMethod.GET, "/services").permitAll()
                        .requestMatchers(HttpMethod.GET, "/services/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/services/category/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/profile").authenticated()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/refresh").permitAll()


                        // Разрешаем доступ к Swagger UI
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        .anyRequest().authenticated()
                )

                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )

                .addFilterAfter(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
