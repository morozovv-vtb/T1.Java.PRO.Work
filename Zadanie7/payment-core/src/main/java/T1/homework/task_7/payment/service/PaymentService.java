package T1.homework.task_7.payment.service;

import T1.homework.task_7.common.dto.ProductDto;
import T1.homework.task_7.payment.exception.PaymentCoreException;
import T1.homework.task_7.payment.exception.ProductNotFoundException;
import T1.homework.task_7.payment.exception.ProductsServiceUnavailableException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {

    private final RestTemplate restTemplate;
    private final String productsServiceUrl;

    public PaymentService(RestTemplate restTemplate, String productsServiceUrl) {
        this.restTemplate = restTemplate;
        this.productsServiceUrl = productsServiceUrl;
    }

    public List<ProductDto> getProductsByUserId(Long userId) {
        String url = productsServiceUrl + "/user/" + userId;
        ResponseEntity<ProductDto[]> response = restTemplate.getForEntity(url, ProductDto[].class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return List.of(response.getBody());
        } else {
            throw new ProductsServiceUnavailableException();
        }
    }

    public ProductDto getProductById(Long productId) {
        String url = productsServiceUrl + "/" + productId;
        ResponseEntity<ProductDto> response = restTemplate.getForEntity(url, ProductDto.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new ProductNotFoundException(productId);
        }
    }

    public boolean executePayment(Long productId, BigDecimal amount) {
        ProductDto product = getProductById(productId);
        if (product.getBalance().compareTo(amount) < 0) {
            throw new PaymentCoreException("Insufficient balance on product " + productId);
        }
        return true;
    }
}