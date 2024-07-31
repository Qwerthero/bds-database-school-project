package org.but.flight.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.but.flight.api.FlightEditView;
import org.but.flight.data.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditController {
    private static final Logger logger = LoggerFactory.getLogger(DetailController.class);
    @FXML
    private Button editButton;
    @FXML
    public TextField dTime;
    @FXML
    public TextField aTime;
    @FXML
    public TextField dDate;
    @FXML
    public TextField aDate;
    @FXML
    public TextField id;
    public Stage stage;

    public FlightRepository flightRepository;
    public FlightEditView editView;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        editView = (FlightEditView) stage.getUserData();
        id.setText(String.valueOf(editView.getId()));
        dTime.setText(String.valueOf(editView.getDepartureTime()));
        aTime.setText(String.valueOf(editView.getArrivalTime()));
        dDate.setText(String.valueOf(editView.getDepartureDate()));
        aDate.setText(String.valueOf(editView.getArrivalDate()));
    }
    @FXML
    public void handleEdit() {
        editView.setDepartureTime(dTime.getText());
        editView.setArrivalTime(aTime.getText());
        editView.setArrivalDate(dDate.getText());
        editView.setDepartureDate(aDate.getText());

        flightRepository = new FlightRepository();
        flightRepository.editFlight(editView);

        logger.info("Edit successful");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Editing confirmation");
        alert.setHeaderText("Flight successfully edited");
        alert.showAndWait();

        stage.close();
    }
}