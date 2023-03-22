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

    public String getCarId() {
        return CarId;
    }

    public String getCarMaker() {
        return CarMaker;
    }

    public int getCarYear() {
        return CarYear;
    }

    public int getCarPrice() {
        return CarPrice;
    }

}
