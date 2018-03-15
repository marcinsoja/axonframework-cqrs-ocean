package pl.marcinsoja.cms.ocean.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class QueryApplicationBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(QueryApplicationBootstrap.class, args);
    }

}
