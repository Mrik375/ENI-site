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

	/**
	 * Déclaration des utilisateurs de l'application avec leur mot de passe chiffré
	 * et leurs rôles
	 */
	//@Bean
	//InMemoryUserDetailsManager userDetailsManager() {
	//PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	//String pwd_chiffre = encoder.encode("Pa$$w0rd");
	//logger.info(pwd_chiffre);
	//UserDetails COACH = User.builder().username("coachAdmin").password(pwd_chiffre)
	//	.roles("ADMIN").build();
				//	UserDetails Tata = User.builder().username("coachTata").password(pwd_chiffre)
	//	.roles("USER").build();
	//UserDetails Titi = User.builder().username("coachTiti").password(pwd_chiffre)
	//		.roles("USER").build();
	//UserDetails Toto = User.builder().username("coachToto").password(pwd_chiffre).roles("USER")
	//		.build();
	//return new InMemoryUserDetailsManager(COACH, Tata, Titi, Toto);
	//}
	
	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT pseudo, password, 1 FROM utilisateurs WHERE pseudo=?");
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT pseudo, role FROM roles WHERE pseudo=?");
		
		return jdbcUserDetailsManager;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(auth -> {
	
			auth
//checker les noms des pages (utilisateur/ventes en cours/achats) !!!!!!!!!!!!!!
				.requestMatchers(HttpMethod.GET, "/Acceuil/S'inscrire/Se connecter/Se deconnecter").hasAnyRole("ADMIN", "USER")
				.requestMatchers(HttpMethod.GET, "/achats/mes ventes/nouvelle vente/ajouter une photo/détail d'une vente").hasAnyRole("ADMIN", "USER")
				.requestMatchers(HttpMethod.POST, "/Mon profil/Modifier mon mot de passe").hasAnyRole("ADMIN","USER")
				.requestMatchers(HttpMethod.GET, "/Utilisateurs/achats/ventes en cours").hasAnyRole("ADMIN", "USER")
				.requestMatchers(HttpMethod.POST, "/Vendre un article").hasRole("USER");
			
//DEFINIR PRECISEMENT LE ROLE D'ADMIN et le rajouter !!!!!!!
			
			//Permettre à toutb le monde d'accéder à l'URL racine
			auth.requestMatchers("/*").permitAll();
			//permettre à tous les utilisateurs d'afficher correctement les images et la CSS 
			auth.requestMatchers("/css/*").permitAll();
			auth.requestMatchers("/images/*").permitAll();
			//toutes les autres url et méthodes HTTP ne sont pas permises 
				
			auth.anyRequest().denyAll();
		});
		http.formLogin(Customizer.withDefaults());
		return http.build();

	}
	
 }
