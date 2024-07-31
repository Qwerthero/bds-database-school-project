package org.but.flight.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;

public class FlightEditView {
    private LongProperty id = new SimpleLongProperty();
    private StringProperty departureTime = new SimpleStringProperty();
    private StringProperty arrivalTime = new SimpleStringProperty();
    private StringProperty departureDate = new SimpleStringProperty();
    private StringProperty arrivalDate = new SimpleStringProperty();

    public Long getId() {
        return idProperty().get();
    }

    public void setId(Long id) {
        this.idProperty().setValue(id);
    }

    public String getDepartureTime() {
        return departureTimeProperty().get();
    }

    public void setDepartureTime(String DepartureTime) {
        this.departureTimeProperty().setValue(DepartureTime);
    }

    public String getArrivalTime() {
        return arrivalTimeProperty().get();
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTimeProperty().setValue(arrivalTime);
    }

    public String getDepartureDate() {
        return departureDateProperty().get();
    }

    public void setDepartureDate(String departureDate) {
        this.departureDateProperty().setValue(departureDate);
    }

    public String getArrivalDate() {
        return arrivalDateProperty().get();
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDateProperty().set(arrivalDate);
    }

    public LongProperty idProperty() {
        return id;
    }

    public StringProperty departureTimeProperty() {
        return departureTime;
    }

    public StringProperty arrivalTimeProperty() {
        return arrivalTime;
    }

    public StringProperty departureDateProperty() {
        return departureDate;
    }
    public StringProperty arrivalDateProperty() {
        return arrivalDate;
    }
}