package org.but.flight.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Arrays;
import java.util.SimpleTimeZone;

public class ExtendedFlightView {
    private LongProperty id = new SimpleLongProperty();
    private StringProperty departureTime = new SimpleStringProperty();
    private StringProperty arrivalTime = new SimpleStringProperty();
    private StringProperty departureDate = new SimpleStringProperty();
    private StringProperty arrivalDate = new SimpleStringProperty();
    private StringProperty departurePlace = new SimpleStringProperty();
    private StringProperty destinationPlace = new SimpleStringProperty();
    private LongProperty countOfLayover = new SimpleLongProperty();
    private LongProperty modelNumber = new SimpleLongProperty();
    private StringProperty name = new SimpleStringProperty();
    private LongProperty numberOfCrew = new SimpleLongProperty();

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

    public String getDeparturePlace() {
        return departurePlaceProperty().get();
    }

    public void setDeparturePlace(String departurePlace) {
        this.departurePlaceProperty().setValue(departurePlace);
    }

    public String getDestinationPlace() {
        return destinationPlaceProperty().get();
    }

    public void setDestinationPlace(String destinationPlace) { this.destinationPlaceProperty().setValue(destinationPlace); }

    public Long getCountOfLayover() {
        return countOfLayoverProperty().get();
    }

    public void setCountOfLayover(Long countOfLayover) {
        this.countOfLayoverProperty().setValue(countOfLayover);
    }

    public Long getModelNumber() {return modelNumberProperty().get(); }

    public void setModelNumber (Long modelNumber) { this.modelNumberProperty().setValue(modelNumber); }

    public String getName() {return nameProperty().get(); }

    public void setName (String name) { this.nameProperty().setValue(name); }

    public Long getNumberOfCrew() {return numberOfCrewProperty().get();}

    public void setNumberOfCrew(Long numberOfCrew) {this.numberOfCrewProperty().setValue(numberOfCrew);}

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

    public StringProperty departurePlaceProperty() {
        return departurePlace;
    }

    public StringProperty destinationPlaceProperty() { return destinationPlace;  }

    public LongProperty countOfLayoverProperty() {
        return countOfLayover;
    }
    public LongProperty modelNumberProperty() {
        return modelNumber;
    }
    public StringProperty nameProperty() {
        return name;
    }
    public LongProperty numberOfCrewProperty() {
        return numberOfCrew;
    }
}
