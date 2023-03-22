package ro.unibuc.hello.data;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CarXUserEntity {
    private String UserId;
    private String CarId;

    public CarXUserEntity() {
    }

    public CarXUserEntity(String userId, String carId) {
        UserId = userId;
        CarId = carId;
    }

    public String getCarId() {
        return CarId;
    }

    public void setCarId(String carId) {
        CarId = carId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
