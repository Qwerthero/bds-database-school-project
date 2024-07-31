package org.but.flight.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.but.flight.api.ExtendedFlightView;
import org.but.flight.api.FlightDetailView;

public class AirplaneController {
    @FXML
    public TextField dTime;
    @FXML
    public TextField aTime;
    @FXML
    public TextField dDate;
    @FXML
    public TextField aDate;
    @FXML
    public TextField dPlace;
    @FXML
    public TextField aPlace;
    @FXML
    public TextField layoverCount;
    @FXML
    public TextField mNumber;
    @FXML
    public TextField name;
    @FXML
    public TextField crewNumber;


    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        ExtendedFlightView flightView = (ExtendedFlightView) stage.getUserData();
        dTime.setText(String.valueOf(flightView.getDepartureTime()));
        aTime.setText(String.valueOf(flightView.getArrivalTime()));
        dDate.setText(String.valueOf(flightView.getDepartureDate()));
        aDate.setText(String.valueOf(flightView.getArrivalDate()));
        dPlace.setText(String.valueOf(flightView.getDeparturePlace()));
        aPlace.setText(String.valueOf(flightView.getDestinationPlace()));
        layoverCount.setText(String.valueOf(flightView.getCountOfLayover()));
        mNumber.setText(String.valueOf(flightView.getModelNumber()));
        name.setText(String.valueOf(flightView.getName()));
        crewNumber.setText(String.valueOf(flightView.getNumberOfCrew()));
    }
}