package T1.homework.task_6.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "product_type", nullable = false)
    private String productType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonManagedReference
    private UserEntity user;

    public ProductEntity() {}

    public ProductEntity(String accountNumber, BigDecimal balance, String productType, UserEntity user) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.productType = productType;
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    @Override
    public String toString() {
        return "ProductEntity{id=" + id + ", accountNumber='" + accountNumber + "', balance=" + balance +
                ", productType='" + productType + "', userId=" + (user != null ? user.getId() : "null") + "}";
    }
}