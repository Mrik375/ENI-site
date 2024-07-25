package fr.eni.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-secrets.properties")
public class EniSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(EniSiteApplication.class, args);
	}

}
