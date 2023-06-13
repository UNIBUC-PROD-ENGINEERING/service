package ro.unibuc.hello.dto;

import java.util.List;

import org.springframework.core.annotation.Order;

public class OrderDTO {
    private String id;
    private String userID;
    private List<String> listaProduse;

    public OrderDTO(String id, String userID, List<String> listaProduse) {
        this.id = id;
        this.userID = userID;
        this.listaProduse = listaProduse;
    }

    public String getId() {
        return id;
    }
    public List<String> getListaProduse() {
        return listaProduse;
    }
    public String getUserID() {
        return userID;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setListaProduse(List<String> listaProduse) {
        this.listaProduse = listaProduse;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
}
