package fr.eni.site.configuration.security;

import org.apache.commons.logging.*;
import org.springframework.context.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class EniSecurityConfig {
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * Déclaration des utilisateurs de l'application avec leur mot de passe chiffré
	 * et leurs rôles
	 */
	@Bean
	InMemoryUserDetailsManager userDetailsManager() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder(); 
		String pwd_chiffre = encoder.encode("Pa$$w0rd");
		logger.info(pwd_chiffre);
		UserDetails COACH = User.builder().username("coachAdmin").password(pwd_chiffre)
				.roles("ADMIN").build();
		UserDetails Tata = User.builder().username("coachTata").password(pwd_chiffre)
				.roles("USER").build();
		UserDetails Titi = User.builder().username("coachTiti").password(pwd_chiffre)
				.roles("USER").build();
		UserDetails Toto = User.builder().username("coachToto").password(pwd_chiffre).roles("USER")
				.build();
		return new InMemoryUserDetailsManager(COACH, Tata, Titi, Toto);
	}
}