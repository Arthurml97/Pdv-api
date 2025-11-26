package br.com.tutum.portfolio.pdvapi.config;

// Injeção de depêndencia e configuração geral do spring

import org.springframework.beans.factory.annotation.Autowired; // Permite usar o @Autowired para injetar o UserDetailsService dentro desta class
import org.springframework.context.annotation.Bean; // Indicar que o método retorna um objeto que o Spring deve gerenciar, como por exemplo criar a ferramenta da senha
import org.springframework.context.annotation.Configuration; // Avisar ao Spring que esta class é de configuração
// Autenticação
import org.springframework.security.authentication.AuthenticationManager; // Interface principal para gerenciar o processo de login, verificar se a senha é valida
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder; // Ferramenta de construção para criar a autenticação personalizada
// Regras de URL
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Objeto principal onde define as regras, como quem pode acessar o url, desligar CSRF e etc
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Habilita a configuração de segurança web do Spring Security
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // Auxiliar usada para desabilitar configurações padroes como por exemplo o CSRF dentro do Spring
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // Aplica níves de segurança diretamente para métodos individuais dentro das classes
import org.springframework.security.web.SecurityFilterChain; // Filtros de segurança, sendo o resultado final da configuração do HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService; // Interface que define algo que busca os usuários
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Implementação específica do algoritmo de Hash BCrypt, sendo o mais utilizado e popular para criptografar senhas
import org.springframework.security.crypto.password.PasswordEncoder; // Inteface genérica de codificador de senhas, podendo trocar o algoritmo no futuro se necessário
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults; // Static import para usar as configurações padrão do Spring usado no .httpBasic(withDefaults()))
// Criptografia e dados de usuarios

@Configuration // Indica que essa classe é uma classe de configuração do Spring
@EnableWebSecurity // Habilita a segurança web do Spring Security
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean // Define um bean gerenciado pelo Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(withDefaults()) // Ativar o CORS no Spring Security
                .csrf(AbstractHttpConfigurer::disable) // Desabilita a proteção CSRF (Cross-Site Request Forgery)
                .authorizeHttpRequests(auth -> auth // Configura as regras de autorização para requisições HTTP
                        .requestMatchers("/h2-console/**").permitAll() // Permite acesso livre às URLs do console H2
                        .requestMatchers("/api/usuarios").permitAll() // Liberar Cadastro
                        // Liberar o Swagger
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/produtos").permitAll() // Liberar o GET para testar sem login
                        .anyRequest().authenticated() // Exige autenticação para qualquer outra requisição
                )
                .httpBasic(withDefaults());

        http.headers(headers -> headers.frameOptions(frame -> frame.disable())); // Permite que o console H2 seja
                                                                                 // carregado em um frame da mesma
                                                                                 // origem
        return http.build(); // Constrói e retorna a cadeia de filtros de segurança configurada
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permite apenas o frontend por segurança
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));

        // Permite os métodos comuns, GET para listar, POST para criar e etc
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Permite quaisquer cabeçalhos
        configuration.setAllowedHeaders(List.of("*"));

        // Autenticação de Cookies e Tokens
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean // Cria a ferramenta de criptografia para usar em qualquer lugar
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return auth.build();
    }
}
