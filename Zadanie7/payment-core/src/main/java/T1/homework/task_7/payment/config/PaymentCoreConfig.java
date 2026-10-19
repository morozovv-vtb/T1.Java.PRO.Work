package T1.homework.task_7.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import T1.homework.task_7.payment.service.PaymentService;

@Configuration
public class PaymentCoreConfig {

    @Value("${payment-core.products-service.url}")
    private String productsServiceUrl;

    // Бин RestTemplate
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Бин PaymentService — передаём зависимости через конструктор
    @Bean
    public PaymentService paymentService(RestTemplate restTemplate) {
        return new PaymentService(restTemplate, productsServiceUrl);
    }
}
