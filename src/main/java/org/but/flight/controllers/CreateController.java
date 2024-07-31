package org.but.flight.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import org.but.flight.api.AirplaneNames;
import org.but.flight.api.ExtendedFlightView;
import org.but.flight.api.FlightDetailView;
import org.but.flight.api.FlightEditView;
import org.but.flight.data.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CreateController {
    private static final Logger logger = LoggerFactory.getLogger(CreateController.class);
    @FXML
    public TextField dTimeField;
    @FXML
    public TextField aTimeField;
    @FXML
    public DatePicker dDatePicker;
    @FXML
    public DatePicker aDatePicker;
    @FXML
    public TextField dPlace;
    @FXML
    public TextField aPlace;
    @FXML
    public TextField lCount;
    @FXML public MenuItem m1;
    @FXML public MenuItem m2;
    @FXML public MenuItem m3;
    @FXML public MenuItem m4;
    @FXML public MenuItem m5;
    @FXML public MenuItem m6;
    @FXML
    public MenuButton aMenu;
    @FXML
    Label menuLabel;

    public void setStage(Stage stage) {this.stage = stage;}
    public Stage stage;
    public FlightRepository flightRepository;
    public ExtendedFlightView createView;
    public Button createButton;

    @FXML
    private void initialize() {
        flightRepository = new FlightRepository();
        aMenu = new MenuButton();
        createView = new ExtendedFlightView();

        List<AirplaneNames> dataList = flightRepository.getMenuAirplanes();
        //ObservableList<AirplaneNames> observableDataList = FXCollections.observableArrayList(dataList);
        //bAirplane = new ChoiceBox<>(observableDataList);
        //String test = dataList.get().getModelNumber();

        m1.setText(String.valueOf(dataList.get(0).getModelNumber()));
        m2.setText(String.valueOf(dataList.get(1).getModelNumber()));
        m3.setText(String.valueOf(dataList.get(2).getModelNumber()));
        m4.setText(String.valueOf(dataList.get(3).getModelNumber()));
        m5.setText(String.valueOf(dataList.get(4).getModelNumber()));
        m6.setText(String.valueOf(dataList.get(5).getModelNumber()));
    }

    public void handleCreate() {
        createView.setDepartureTime(dTimeField.getText());
        createView.setArrivalTime(aTimeField.getText());

        createView.setDepartureDate(dDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.print(dDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        createView.setArrivalDate(aDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        createView.setDeparturePlace(dPlace.getText());
        createView.setDestinationPlace(aPlace.getText());
        createView.setCountOfLayover(Long.valueOf(lCount.getText()));

        flightRepository.createFlight(createView);

        logger.info("Creation successful");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Creation confirmation");
        alert.setHeaderText("Flight successfully created");
        alert.showAndWait();

        stage.close();
    }
    public void m1 () {
        menuLabel.setText(m1.getText());
        createView.setModelNumber(Long.valueOf(m1.getText()));
    }
    public void m2 () {
        menuLabel.setText(m2.getText());
        createView.setModelNumber(Long.valueOf(m2.getText()));
    }
    public void m3 () {
        menuLabel.setText(m3.getText());
        createView.setModelNumber(Long.valueOf(m3.getText()));
    }
    public void m4 () {
        menuLabel.setText(m4.getText());
        createView.setModelNumber(Long.valueOf(m4.getText()));
    }
    public void m5 () {
        menuLabel.setText(m5.getText());
        createView.setModelNumber(Long.valueOf(m5.getText()));
    }
    public void m6 () {
        menuLabel.setText(m6.getText());
        createView.setModelNumber(Long.valueOf(m6.getText()));
    }
}
