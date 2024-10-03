package blomera.praceando.praceandoapipg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "blomera.praceando.praceandoapipg")
@EntityScan(basePackages = "blomera.praceando.praceandoapipg.model")
public class PraceandoApiPgApplication {

    public static void main(String[] args) {
        SpringApplication.run(PraceandoApiPgApplication.class, args);
    }

}
