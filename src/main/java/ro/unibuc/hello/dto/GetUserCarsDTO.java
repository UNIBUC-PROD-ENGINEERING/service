package ro.unibuc.hello.dto;

public class GetUserCarsDTO {
    private String UserId;
    private String CarId;
    private String CarMaker;
    private int CarYear;
    private int CarPrice;

    public GetUserCarsDTO() {
    }

    public GetUserCarsDTO(String userId, String carId, String carMaker, int carYear, int carPrice) {
        UserId = userId;
        CarId = carId;
        CarMaker = carMaker;
        CarYear = carYear;
        CarPrice = carPrice;
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

    public String getCarMaker() {
        return CarMaker;
    }

    public void setCarMaker(String carMaker) {
        CarMaker = carMaker;
    }

    public int getCarYear() {
        return CarYear;
    }

    public void setCarYear(int carYear) {
        CarYear = carYear;
    }

    public int getCarPrice() {
        return CarPrice;
    }

    public void setCarPrice(int carPrice) {
        CarPrice = carPrice;
    }
}
