package vn.com.ezmobi.ezhealth.ezhuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;

@SpringBootApplication(exclude = ArtemisAutoConfiguration.class)
public class EzhUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EzhUserServiceApplication.class, args);
    }

}
