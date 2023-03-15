package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.CarEntity;
import java.util.Objects;

public class CarsDTO {


    private String carId; //id of the car in the database

    private String carMaker;//Volvo, BMW, Renault, etc

    private String carType; //sedan, SUV, StationWagon

    private Integer carYear; //year the car is produced

    private String carEuro; //if Euro6,5,etc

    private Integer carPrice; // price of the car

    public CarsDTO(){

    }

    public CarsDTO(CarEntity car){
        this.carId= car.getCarId();
        this.carMaker= car.getCarMaker();
        this.carType= car.getCarType();
        this.carYear= car.getCarYear();
        this.carEuro= car.getCarEuro();
        this.carPrice= car.getCarPrice();
    }


    public String getCarId() {return carId;}
    public void setCarId(String carId) {this.carId= carId;}

    public String getCarMaker() { return carMaker;}
    public void setCarMaker() { this.carMaker= carMaker;}

    public String getCarType() { return carType;}
    public void setCarType() { this.carType= carType;}

    public Integer getCarYear() { return carYear;}
    public void setCarYear() { this.carYear= carYear;}

    public String getCarEuro() { return carEuro;}
    public void setCarEuro() { this.carEuro= carEuro;}

    public Integer getCarPrice() { return carPrice;}
    public void setCarPrice() { this.carPrice= carPrice;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarsDTO carDTO = (CarsDTO) o;
        return carId.equals(carDTO.carId); //&& carMaker.equals(carDTO.carMaker) && carType.equals(carDTO.carType) && carYear.equals(carDTO.carYear) && carEuro.equals(carDTO.carEuro) && carPrice.equals(carDTO.carPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId, carMaker, carType, carYear, carEuro, carPrice);
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "carId='" + carId + '\'' +
                ", carMaker='" + carMaker + '\'' +
                ", carType='" + carType + '\'' +
                ", carYear='" + carYear + '\'' +
                ", carEuro=" + carEuro +
                ", carPrice=" + carPrice +
                '}';
    }
}
