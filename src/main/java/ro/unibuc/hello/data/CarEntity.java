package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;


import java.util.Objects;


public class CarEntity {
    @Id
    public String carId; //id of the car in the database

    public String carMaker;//Volvo, BMW, Renault, etc

    public String carType; //Sedan, SUV, StationWagon

    public Integer carYear; //year the car is produced

    public String carEuro; //if Euro6,5,etc

    public Integer carPrice; // price of the car

    public CarEntity(){
    }

    public CarEntity(String carId, String carMaker, String carType, Integer carYear, String carEuro, Integer carPrice) {
        this.carId= carId; //id of the car in the database
        this.carMaker= carMaker;//Volvo, BMW, Renault, etc
        this.carType= carType; //sedan, SUV, StationWagen
        this.carYear= carYear; //year the car is produced
        this.carEuro= carEuro; //if Euro6,5,etc
        this.carPrice= carPrice;
    }

    public String getCarId() {return carId;}
    public void setCarId(String carId) {this.carId= carId;}

    public String getCarMaker() { return carMaker;}
    public void setCarMaker(String carMaker) { this.carMaker= carMaker;}

    public String getCarType() { return carType;}
    public void setCarType(String carType) { this.carType= carType;}

    public Integer getCarYear() { return carYear;}
    public void setCarYear(Integer carYear) { this.carYear= carYear;}

    public String getCarEuro() { return carEuro;}
    public void setCarEuro(String carEuro) { this.carEuro= carEuro;}

    public Integer getCarPrice() { return carPrice;}
    public void setCarPrice(Integer carPrice) { this.carPrice= carPrice;}

    @Override
    public String toString() {
        return "CarEntity{" +
                "Id='" + carId + '\'' +
                ", Maker='" + carMaker + '\'' +
                ", Type='" + carType + '\'' +
                ", Year='" + carYear + '\'' +
                ", Euro=" + carEuro +
                ", Price=" + carPrice + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarEntity that = (CarEntity) o;
        return Objects.equals(carId, that.carId) && Objects.equals(carMaker, that.carMaker) && Objects.equals(carType, that.carType) && Objects.equals(carYear, that.carYear) && Objects.equals(carEuro, that.carEuro) && Objects.equals(carPrice, that.carPrice);
    }


}
