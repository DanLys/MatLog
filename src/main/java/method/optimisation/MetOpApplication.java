package method.optimisation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


/**
 * Created by Danil Lyskin at 15:32 25.02.2021
 */
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MetOpApplication {

    public static void main(String[] args) {

        SpringApplication.run(MetOpApplication.class, args);
    }
}
