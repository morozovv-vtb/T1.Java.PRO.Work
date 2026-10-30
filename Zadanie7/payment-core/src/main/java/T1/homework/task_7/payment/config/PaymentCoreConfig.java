package T1.homework.task_7.payment.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import T1.homework.task_7.payment.service.PaymentService;

@Configuration
@EnableConfigurationProperties(ApplicationPaymentConfig.class)
public class PaymentCoreConfig {

    @Bean
    public RestTemplate restTemplate(ApplicationPaymentConfig config) {
        SimpleClientHttpRequestFactory proxy = new SimpleClientHttpRequestFactory();

        proxy.setConnectTimeout(config.getConnectTimeout());
        proxy.setReadTimeout(config.getReadTimeout());

        return new RestTemplate(proxy);
    }

    @Bean
    public PaymentService paymentService(RestTemplate restTemplate, ApplicationPaymentConfig config) {
        return new PaymentService(restTemplate, config);
    }
}