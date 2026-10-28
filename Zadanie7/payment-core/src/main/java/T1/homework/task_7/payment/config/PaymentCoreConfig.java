package T1.homework.task_7.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import T1.homework.task_7.payment.service.PaymentService;

@Configuration
public class PaymentCoreConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PaymentService paymentService(RestTemplate restTemplate, ApplicationPaymentConfig config) {
        return new PaymentService(restTemplate, config.getUrl());
    }
}