package T1.homework.task_7.payment_core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "payment-core.products-service")
public class ApplicationPaymentConfig {

    private String url;

    // Getter
    public String getUrl() {
        return url;
    }

    // Setter — обязательно для Spring
    public void setUrl(String url) {
        this.url = url;
    }
}