package blomera.praceando.praceandoapipg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(scanBasePackages = "blomera.praceando.praceandoapipg")
@EntityScan(basePackages = "blomera.praceando.praceandoapipg.model")
public class PraceandoApiPgApplication {

    public static void main(String[] args) {
        SpringApplication.run(PraceandoApiPgApplication.class, args);
    }

}
