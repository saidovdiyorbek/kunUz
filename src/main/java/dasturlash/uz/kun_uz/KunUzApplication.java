package dasturlash.uz.kun_uz;

import dasturlash.uz.kun_uz.config.SpringSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class KunUzApplication {

    public static void main(String[] args) {
        SpringApplication.run(KunUzApplication.class, args);
    }

}
