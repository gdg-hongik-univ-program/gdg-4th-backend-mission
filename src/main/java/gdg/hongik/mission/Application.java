package gdg.hongik.mission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"com.example", "gdg.hongik.mission"})
@EnableJpaRepositories(basePackages = "com.example.shopbackend.repository")
@EntityScan(basePackages = "com.example.shopbackend.domain")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
