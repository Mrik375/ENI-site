package fr.eni.site.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Optional;

public class SecurityUtils {

	public static Optional<String> getCurrentUserLogin() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return Optional.empty();
		}

		String userName = null;
		if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
			userName = springSecurityUser.getUsername();
		} else if (authentication.getPrincipal() instanceof String) {
			userName = (String) authentication.getPrincipal();
		}

		return Optional.ofNullable(userName);
	}
}

