package ro.unibuc.hello.dto;

public class BuyCarDTO {
    private String UserId;
    private String CarId;

    public BuyCarDTO() {
    }

    public BuyCarDTO(String userId, String carId) {
        UserId = userId;
        CarId = carId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCarId() {
        return CarId;
    }

    public void setCarId(String carId) {
        CarId = carId;
    }
}
