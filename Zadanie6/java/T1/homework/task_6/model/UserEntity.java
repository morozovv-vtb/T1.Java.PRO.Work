package T1.homework.task_6.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<ProductEntity> products;

    public UserEntity() {}

    public UserEntity(String username) {
        this.username = username;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public List<ProductEntity> getProducts() { return products; }
    public void setProducts(List<ProductEntity> products) { this.products = products; }

    @Override
    public String toString() {
        return "UserEntity{id=" + id + ", username='" + username + "'}";
    }
}