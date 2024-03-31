package org.gabris.RestApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PageRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Page("/", List.of(HttpVerb.GET), "text/html", "pages/index.html")));
            log.info("Preloading " + repository.save(new Page("/new", List.of(HttpVerb.GET), "text/html", "pages/new.html")));
        };
    }
}
