package org.gabris.RestApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestRestApiApplication {
	public static void main(String[] args) {
		SpringApplication.from(RestApiApplication::main).with(TestRestApiApplication.class).run(args);
	}

}
