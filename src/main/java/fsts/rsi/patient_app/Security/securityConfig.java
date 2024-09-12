package fsts.rsi.patient_app.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class securityConfig {
@Bean
public JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
    return new JdbcUserDetailsManager(dataSource);
}
    //@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
        String encodedPasswordUser = passwordEncoder.encode("2002");
        String encodedPasswordAdmin = passwordEncoder.encode("0000");
        return new InMemoryUserDetailsManager(
                User.withUsername("Oussama").password(encodedPasswordUser).roles("USER").build(),
                User.withUsername("admin").password(encodedPasswordAdmin).roles("USER", "ADMIN").build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(Customizer.withDefaults()) // Configuration par défaut du formulaire de connexion
                .authorizeRequests(auth -> auth
                        .requestMatchers("/delete").hasRole("ADMIN") // Seuls les utilisateurs avec le rôle ADMIN peuvent accéder à /delete
                        .anyRequest().authenticated() // Toute autre requête nécessite une authentification
                )
                .build();
    }
}
