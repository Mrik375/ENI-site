package fr.eni.site.configuration.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class EniSecurityConfig {
	protected final Log logger = LogFactory.getLog(getClass());


	//Remplacé par l'implémentation de UserDetailsService dans UtilisateursService
	//@Bean
//	UserDetailsManager userDetailsManager(DataSource dataSource) {
//		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//		jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT pseudo, mot_de_passe, 1 FROM UTILISATEURS WHERE pseudo=?");
//		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("""
//				SELECT u.pseudo, r.ROLE
//				FROM UTILISATEURS u
//				JOIN ROLES r ON u.administrateur = r.IS_ADMIN
//				WHERE u.pseudo = ?
//				""");
//		return jdbcUserDetailsManager;
//	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth ->
						auth
								.requestMatchers(HttpMethod.GET, "/", "/accueil", "/creercompte", "/connexion", "/logout").permitAll()
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
