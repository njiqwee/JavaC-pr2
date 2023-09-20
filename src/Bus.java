public class Bus {
    private int id;
    private String driverName;
    private String busNumber;
    private String routeNumber;
    private String brand;
    private int yearOfManufacture;
    private int mileage;

    public Bus(String driverName, String busNumber, String routeNumber, String brand, int yearOfManufacture, int mileage) {
        this.driverName = driverName;
        this.busNumber = busNumber;
        this.routeNumber = routeNumber;
        this.brand = brand;
        this.yearOfManufacture = yearOfManufacture;
        this.mileage = mileage;
    }
    
    public int getId() {
        return id;
    }
    
    public String getDriverName() {
        return driverName;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public String getBrand() {
        return brand;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public int getMileage() {
        return mileage;
    }

    // Сеттеры
    public void setId(int id) {
        this.id = id;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setYearOfManufacture(int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
}