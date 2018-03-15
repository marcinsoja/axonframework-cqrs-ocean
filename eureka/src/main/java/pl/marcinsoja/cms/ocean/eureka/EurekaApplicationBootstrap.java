package pl.marcinsoja.cms.ocean.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplicationBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplicationBootstrap.class, args);
    }
}
