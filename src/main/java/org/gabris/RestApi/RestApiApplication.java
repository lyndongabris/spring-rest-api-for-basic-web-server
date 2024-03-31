package org.gabris.RestApi;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApiApplication {

	public static void main(String[] args) {
		// TODO: replace with properties
		Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/server",
				"server", "tempPassword").load();
		flyway.migrate();
		SpringApplication.run(RestApiApplication.class, args);
	}

}
