package T1.homework.task_7.common.dto;

import java.math.BigDecimal;

public class ProductDto {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private String productType;
    private Long userId;

    // Конструктор
    public ProductDto(Long id,
                      String accountNumber,
                      BigDecimal balance,
                      String productType,
                      Long userId
    ) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.productType = productType;
        this.userId = userId;
    }

    // Getters и Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
