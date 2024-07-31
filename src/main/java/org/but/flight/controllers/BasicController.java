package org.but.flight.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.but.flight.App;
import org.but.flight.api.FlightDetailView;
import org.but.flight.api.PassengerBasicView;
import org.but.flight.data.FlightRepository;
import org.but.flight.exceptions.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class BasicController {
    private static final Logger logger = LoggerFactory.getLogger(BasicController.class);
    @FXML
    public TableView<PassengerBasicView> basicView;
    @FXML
    private TableColumn<PassengerBasicView, Long> idColumn;
    @FXML
    public TableColumn<PassengerBasicView, String> nameColumn;
    @FXML
    public TableColumn<PassengerBasicView, String> surnameColumn;
    @FXML
    private TableColumn<PassengerBasicView, String> emailColumn;
    @FXML
    private Button flightButton;
    @FXML
    private Button injectionButton;

    private FlightRepository ListBasicView;

    public BasicController() {
    }

    @FXML
    private void initialize() {
        ListBasicView = new FlightRepository();

        idColumn.setCellValueFactory(new PropertyValueFactory<PassengerBasicView, Long>("passengerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<PassengerBasicView, String>("passengerName"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<PassengerBasicView, String>("passengerSurname"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<PassengerBasicView, String>("email"));

        List<PassengerBasicView> dataList = ListBasicView.getPassengerBasicView();
        ObservableList<PassengerBasicView> observablePassengerList = FXCollections.observableArrayList(dataList);
        basicView.setItems(observablePassengerList);

        logger.info("BasicController initialized");
    }
    public void initializeView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/FlightDetailView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1050, 600);
            Stage stage = new Stage();
            stage.setTitle("Flight System Database Application");
            stage.setScene(scene);

            Stage stageOld = (Stage) flightButton.getScene().getWindow();
            stageOld.close();
            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }
    public void handleShowFlight () { initializeView(); }
    public void showInjection() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/injectionView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1050, 600);
            Stage stage = new Stage();
            stage.setTitle("Injection Attack Training");
            stage.setScene(scene);

            Stage stageOld = (Stage) injectionButton.getScene().getWindow();
            stageOld.close();
            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }
    public void handleInjectionButton () { showInjection(); }
}


