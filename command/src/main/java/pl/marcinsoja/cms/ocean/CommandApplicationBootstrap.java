package pl.marcinsoja.cms.ocean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class CommandApplicationBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(CommandApplicationBootstrap.class, args);
    }
}
