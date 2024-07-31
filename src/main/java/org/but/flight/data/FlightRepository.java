package org.but.flight.data;



import javafx.util.converter.DateTimeStringConverter;
import org.but.flight.api.*;
import org.but.flight.config.DataSourceConfig;
import org.but.flight.exceptions.DataAccessException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FlightRepository {

    public PassengerAuthView findPassengerByEmail(String email) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT email, password" +
                             " FROM bds.login l" +
                             " WHERE l.email = ?")) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPersonAuth(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find passenger by ID failed.", e);
        }
        return null;
    }

    public List<PassengerBasicView> getPassengerBasicView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT passenger_id, email, passenger_name, passenger_surname" +                             "" +
                             " FROM bds.passenger p" +
                             " LEFT JOIN bds.login l ON p.login_id = l.login_id");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<PassengerBasicView> passengerBasicView = new ArrayList<>();
            while (resultSet.next()) {
                passengerBasicView.add(mapToPassengerBasicView(resultSet));
            }
            return passengerBasicView;
        } catch (SQLException e) {
            throw new DataAccessException("Persons basic view could not be loaded.", e);
        }
    }

    public List<FlightDetailView> flightDetailView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     " SELECT f.flight_id, f.departure_time, f.arrival_time, f.departure_date, " +
                     " f.arrival_date, fp.destination_place, fp.departure_place, fp.count_of_layover " +
                     " FROM bds.flight f JOIN bds.flight_places fp " +
                     " ON f.flight_places_id = fp.flight_places_id;")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<FlightDetailView> flightDetailView = new ArrayList<>();
            while (resultSet.next()) {
                flightDetailView.add(mapToFlightView(resultSet));
            }
            return flightDetailView;
        } catch (SQLException e) {
            throw new DataAccessException("Find flight by ID with destinations failed.", e);
        }
    }

    public List<FlightDetailView> findFlight(String destinationPlace) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     " SELECT f.flight_id, f.departure_time, f.arrival_time, f.departure_date, " +
                             " f.arrival_date, fp.departure_place, fp.destination_place, fp.count_of_layover " +
                             " FROM bds.flight f JOIN bds.flight_places fp " +
                             " ON f.flight_places_id = fp.flight_places_id " +
                             " WHERE destination_place = ?")) {
            preparedStatement.setString(1, destinationPlace);
            List<FlightDetailView> flightDetailView = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    flightDetailView.add(mapToFlightView(resultSet));
                }
                return flightDetailView;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find flight by destination place failed.", e);
        }
    }

    public ExtendedFlightView findFlightDetailView(Long flightId) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     " SELECT f.flight_id, f.departure_time, f.arrival_time, f.departure_date," +
                     " f.arrival_date, fp.departure_place, fp.destination_place, fp.count_of_layover," +
                     " a.model_number, a.name, a.number_of_crew" +
                     " FROM bds.flight f JOIN bds.flight_places fp" +
                     " ON f.flight_places_id = fp.flight_places_id" +
                     " JOIN bds.airplane a ON f.airplane_id = a.airplane_id " +
                             " WHERE f.flight_id = ?")) {
            preparedStatement.setLong(1, flightId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToExtendedFlightView(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find flight by ID with destinations and airplanes failed.", e);
        }
        return null;
    }

    public FlightEditView findEditView(Long flightId) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     " SELECT flight_id, departure_time, arrival_time, departure_date," +
                             " arrival_date FROM bds.flight WHERE flight_id = ?")) {
            preparedStatement.setLong(1, flightId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToEditView(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find flight by ID with destinations and airplanes failed.", e);
        }
        return null;
    }

    public void createFlight(ExtendedFlightView createView) {
        String insertFlightSQL = "INSERT INTO bds.flight_places (departure_place, destination_place, count_of_layover)" +
                " VALUES (?, ?, ?); " +
                " INSERT INTO bds.flight (departure_time, arrival_time, departure_date, " +
                " arrival_date, airplane_id, flight_places_id) VALUES (?, ?, ?, ?, " +
                " (SELECT airplane_id FROM bds.airplane WHERE model_number = ?), " +
                " (SELECT flight_places_id FROM bds.flight_places WHERE departure_place = ? AND destination_place = ?) );";
        try (Connection connection = DataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertFlightSQL)) {
            Time dTime = Time.valueOf(createView.getDepartureTime());
            Time aTime = Time.valueOf(createView.getArrivalTime());

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date date1 = format.parse(createView.getDepartureDate());
            java.sql.Date dDate = new java.sql.Date(date1.getTime());

            java.util.Date date2 = format.parse(createView.getArrivalDate());
            java.sql.Date aDate = new java.sql.Date(date2.getTime());

            preparedStatement.setString(1, createView.getDeparturePlace());
            preparedStatement.setString(2, createView.getDestinationPlace());
            preparedStatement.setLong(3, createView.getCountOfLayover());

            preparedStatement.setTime(4, dTime);
            preparedStatement.setTime(5, aTime);
            preparedStatement.setDate(6, dDate);
            preparedStatement.setDate(7, aDate);

            preparedStatement.setLong(8, createView.getModelNumber());

            preparedStatement.setString(9, createView.getDeparturePlace());
            preparedStatement.setString(10, createView.getDestinationPlace());

            try {
                connection.setAutoCommit(false);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new DataAccessException("Creating person failed, no rows affected.");
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating person failed operation on the database failed." + e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public List<AirplaneNames> getMenuAirplanes() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT model_number, airplane_id FROM bds.airplane");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<AirplaneNames> airplaneNames = new ArrayList<>();
            while (resultSet.next()) {
                airplaneNames.add(mapToAirplaneNames(resultSet));
            }
            return airplaneNames;
        } catch (SQLException e) {
            throw new DataAccessException("Airplane names could not be loaded.", e);
        }
    }
    private AirplaneNames mapToAirplaneNames(ResultSet rs) throws SQLException {
        AirplaneNames airplaneNames = new AirplaneNames();
        airplaneNames.setId(rs.getLong("airplane_id"));
        airplaneNames.setModelNumber(rs.getLong("model_number"));
        return airplaneNames;
    }

    public void editFlight(FlightEditView flightEditView) {
        String updateSQL = "UPDATE bds.flight SET departure_time = ?, arrival_time = ?, departure_date = ?, arrival_date = ? " +
                " WHERE flight_id = ?";
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL, Statement.RETURN_GENERATED_KEYS)) {
            Time dTime = Time.valueOf(flightEditView.getDepartureTime());
            Time aTime = Time.valueOf(flightEditView.getArrivalTime());

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date date1 = format.parse(flightEditView.getDepartureDate());
            java.sql.Date dDate = new java.sql.Date(date1.getTime());

            java.util.Date date2 = format.parse(flightEditView.getArrivalDate());
            java.sql.Date aDate = new java.sql.Date(date2.getTime());

            preparedStatement.setTime(1, dTime);
            preparedStatement.setTime(2, aTime);
            preparedStatement.setDate(3, dDate);
            preparedStatement.setDate(4, aDate);
            preparedStatement.setLong(5, flightEditView.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Editing person failed");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFlight (Long flightId) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM bds.flight WHERE flight_id = ?")) {
            preparedStatement.setLong(1, flightId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {}
        } catch (SQLException e) {
            throw new DataAccessException("Deleting flight failed", e);
        }
    }

    private PassengerAuthView mapToPersonAuth(ResultSet rs) throws SQLException {
        PassengerAuthView person = new PassengerAuthView();
        person.setEmail(rs.getString("email"));
        person.setPassword(rs.getString("password"));
        return person;
    }

    private PassengerBasicView mapToPassengerBasicView(ResultSet rs) throws SQLException {
        PassengerBasicView PassengerBasicView = new PassengerBasicView();
        PassengerBasicView.setId(rs.getLong("passenger_id"));
        PassengerBasicView.setEmail(rs.getString("email"));
        PassengerBasicView.setPassengerName(rs.getString("passenger_surname"));
        PassengerBasicView.setPassengerSurname(rs.getString("passenger_name"));
        return PassengerBasicView;
    }

    private FlightDetailView mapToFlightView(ResultSet rs) throws SQLException {
        FlightDetailView flightDetailView = new FlightDetailView();
        flightDetailView.setId(rs.getLong("flight_id"));
        flightDetailView.setDepartureTime(rs.getString("departure_time"));
        flightDetailView.setArrivalTime(rs.getString("arrival_time"));
        flightDetailView.setDepartureDate(rs.getString("departure_date"));
        flightDetailView.setArrivalDate(rs.getString("arrival_date"));
        flightDetailView.setDeparturePlace(rs.getString("departure_place"));
        flightDetailView.setDestinationPlace(rs.getString("destination_place"));
        if (rs.getString("count_of_layover") == null) {
            long zero = 0;
            flightDetailView.setCountOfLayover(zero);
        }
        else {
            flightDetailView.setCountOfLayover(rs.getLong("count_of_layover"));
        }
        return flightDetailView;
    }

    private FlightEditView mapToEditView(ResultSet rs) throws SQLException {
        FlightEditView flightEditView = new FlightEditView();
        flightEditView.setId(rs.getLong("flight_id"));
        flightEditView.setDepartureTime(rs.getString("departure_time"));
        flightEditView.setArrivalTime(rs.getString("arrival_time"));
        flightEditView.setDepartureDate(rs.getString("departure_date"));
        flightEditView.setArrivalDate(rs.getString("arrival_date"));
        return flightEditView;
    }

    private ExtendedFlightView mapToExtendedFlightView(ResultSet rs) throws SQLException {
        ExtendedFlightView flightDetailView = new ExtendedFlightView();
        flightDetailView.setId(rs.getLong("flight_id"));
        flightDetailView.setDepartureTime(rs.getString("departure_time"));
        flightDetailView.setArrivalTime(rs.getString("arrival_time"));
        flightDetailView.setDepartureDate(rs.getString("departure_date"));
        flightDetailView.setArrivalDate(rs.getString("arrival_date"));
        flightDetailView.setDeparturePlace(rs.getString("departure_place"));
        flightDetailView.setDestinationPlace(rs.getString("destination_place"));
        if (rs.getString("count_of_layover") == null) {
            long zero = 0;
            flightDetailView.setCountOfLayover(zero);
        }
        else {
            flightDetailView.setCountOfLayover(rs.getLong("count_of_layover"));
        }
        flightDetailView.setModelNumber(rs.getLong("model_number"));
        flightDetailView.setName(rs.getString("name"));
        flightDetailView.setNumberOfCrew(rs.getLong("number_of_crew"));
        return flightDetailView;
    }

    public static List<InjectionView> getInjectionView(String input) {
        String sqlResult = "SELECT id, name, username FROM bds.injection WHERE id =" + input;
        try (Connection connection = DataSourceConfig.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlResult)) {
            List<InjectionView> sqlInjection = new ArrayList<>();
            while (resultSet.next()) {
                sqlInjection.add(mapToSQLInjection(resultSet));
            }
            return sqlInjection;
        } catch (SQLException e) {
            throw new DataAccessException("SQL Injection failed.", e);
        }
    }
    private static InjectionView mapToSQLInjection(ResultSet resultSet) throws SQLException {
        InjectionView sqlInjection = new InjectionView();
        sqlInjection.setId(resultSet.getLong("id"));
        sqlInjection.setName(resultSet.getString("name"));
        sqlInjection.setSurname(resultSet.getString("username"));
        return sqlInjection;
    }

}
