package fr.eni.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@PropertySource("classpath:application-secrets.properties")
@EnableTransactionManagement
public class EniSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(EniSiteApplication.class, args);
	}

}
