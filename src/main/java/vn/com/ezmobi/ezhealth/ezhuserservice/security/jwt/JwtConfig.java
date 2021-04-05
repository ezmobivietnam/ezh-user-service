package vn.com.ezmobi.ezhealth.ezhuserservice.security.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
@Data
@NoArgsConstructor
public class JwtConfig {

    private String authorizationHeader;
    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationAfterDays;
}
