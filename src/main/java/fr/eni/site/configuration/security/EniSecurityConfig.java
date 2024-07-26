package fr.eni.site.configuration.security;

import javax.sql.DataSource;

import org.apache.commons.logging.*;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.*;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class EniSecurityConfig {
	protected final Log logger = LogFactory.getLog(getClass());

	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT pseudo, mot_de_passe, 1 FROM UTILISATEURS WHERE pseudo=?");
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("""
				SELECT u.pseudo, r.ROLE
				FROM UTILISATEURS u
				JOIN ROLES r ON u.administrateur = r.IS_ADMIN
				WHERE u.pseudo = ?
				""");
		
		return jdbcUserDetailsManager;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(auth ->
			auth
					.requestMatchers(HttpMethod.GET, "/", "/accueil", "/creercompte", "/connexion","/logout").permitAll()
					.requestMatchers(HttpMethod.GET, "/profil", "/profil/**").permitAll()
					.requestMatchers(HttpMethod.POST, "/creercompte").permitAll()
					.requestMatchers("/logout").permitAll()
					.requestMatchers("/resources/**", "/css/**", "/js/**", "/images/**", "/scss/**", "favicon.ico").permitAll()
					.anyRequest().permitAll()
		)
				.formLogin(form ->
						form
								.loginPage("/connexion")
								.defaultSuccessUrl("/accueil")
				)
				.logout(form ->
				form
						.logoutUrl("/logout")
						.clearAuthentication(true)
						.deleteCookies("JSESSIONID")
						.invalidateHttpSession(true)
						.logoutSuccessUrl("/")
		);
		http.formLogin(Customizer.withDefaults());
		return http.build();

	}
	
 }
