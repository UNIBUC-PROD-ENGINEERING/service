package ro.unibuc.hello.data;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class OrderEntity {
    @Id
    private String id;

    @DBRef
    private UserEntity user;
    @DBRef
    private List<Produs> listaProduse;

    public OrderEntity(String id, UserEntity user, List<Produs> listaProduse) {
        this.id = id;
        this.user = user;
        this.listaProduse = listaProduse;
    }
    public OrderEntity() {

    }

    public String getId() {
        return id;
    }
    public List<Produs> getListaProduse() {
        return listaProduse;
    }
    public UserEntity getUser() {
        return user;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setListaProduse(List<Produs> listaProduse) {
        this.listaProduse = listaProduse;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }
}
