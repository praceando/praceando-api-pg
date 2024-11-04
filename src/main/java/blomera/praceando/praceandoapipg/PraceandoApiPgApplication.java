package blomera.praceando.praceandoapipg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication(scanBasePackages = "blomera.praceando.praceandoapipg")
@EntityScan(basePackages = "blomera.praceando.praceandoapipg.model")
@EnableCaching
public class PraceandoApiPgApplication {

    public static void main(String[] args) {
        SpringApplication.run(PraceandoApiPgApplication.class, args);
    }

}
