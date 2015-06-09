package com.clemble.casino.server.search.spring;

import com.clemble.casino.server.spring.WebBootSpringConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Anton Oparin (antono@clemble.com)
 */
@Configuration
@Import({ WebBootSpringConfiguration.class, SearchSpringConfiguration.class })
public class SearchApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SearchApplication.class, args);
    }

}
