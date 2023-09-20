import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusDatabaseManager {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/buses";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "root";

    public void addBus(Bus bus) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO buses (driver_name, bus_number, route_number, brand, year_of_manufacture, mileage) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, bus.getDriverName());
                preparedStatement.setString(2, bus.getBusNumber());
                preparedStatement.setString(3, bus.getRouteNumber());
                preparedStatement.setString(4, bus.getBrand());
                preparedStatement.setInt(5, bus.getYearOfManufacture());
                preparedStatement.setInt(6, bus.getMileage());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBus(Bus bus) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE buses SET driver_name=?, bus_number=?, route_number=?, brand=?, year_of_manufacture=?, mileage=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, bus.getDriverName());
                preparedStatement.setString(2, bus.getBusNumber());
                preparedStatement.setString(3, bus.getRouteNumber());
                preparedStatement.setString(4, bus.getBrand());
                preparedStatement.setInt(5, bus.getYearOfManufacture());
                preparedStatement.setInt(6, bus.getMileage());
                preparedStatement.setInt(7, bus.getId()); // Предполагается, что у вас есть поле id в таблице buses
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBus(Bus bus) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM buses WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, bus.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Bus> getAllBuses() {
        List<Bus> buses = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM buses";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Bus bus = new Bus(
                        resultSet.getString("driver_name"),
                        resultSet.getString("bus_number"),
                        resultSet.getString("route_number"),
                        resultSet.getString("brand"),
                        resultSet.getInt("year_of_manufacture"),
                        resultSet.getInt("mileage")
                    );
                    bus.setId(resultSet.getInt("id"));
                    buses.add(bus);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return buses;
    }
    
    public List<Bus> getQuestMileageBuses(){
	List<Bus> buses = new ArrayList<>();
	try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM buses WHERE (mileage > 100000)";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Bus bus = new Bus(
                        resultSet.getString("driver_name"),
                        resultSet.getString("bus_number"),
                        resultSet.getString("route_number"),
                        resultSet.getString("brand"),
                        resultSet.getInt("year_of_manufacture"),
                        resultSet.getInt("mileage")
                    );
                    bus.setId(resultSet.getInt("id"));
                    buses.add(bus);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	return buses;
    }
    
    public List<Bus> getQuestRouteBuses(int route){
	List<Bus> buses = new ArrayList<>();
	try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM buses WHERE (route_number = \"Route ";
	    sql += route;
	    sql += "\")";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Bus bus = new Bus(
                        resultSet.getString("driver_name"),
                        resultSet.getString("bus_number"),
                        resultSet.getString("route_number"),
                        resultSet.getString("brand"),
                        resultSet.getInt("year_of_manufacture"),
                        resultSet.getInt("mileage")
                    );
                    bus.setId(resultSet.getInt("id"));
                    buses.add(bus);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	return buses;
    }
    
    public List<Bus> getQuestYearsBuses(){
	List<Bus> buses = new ArrayList<>();
	try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM buses WHERE ((year(curdate()) - year_of_manufacture) > 10)";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Bus bus = new Bus(
                        resultSet.getString("driver_name"),
                        resultSet.getString("bus_number"),
                        resultSet.getString("route_number"),
                        resultSet.getString("brand"),
                        resultSet.getInt("year_of_manufacture"),
                        resultSet.getInt("mileage")
                    );
                    bus.setId(resultSet.getInt("id"));
                    buses.add(bus);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	return buses;
    }
}
