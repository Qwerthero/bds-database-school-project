package org.but.flight.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.but.flight.App;
import org.but.flight.api.*;
import org.but.flight.config.DataSourceConfig;
import org.but.flight.data.FlightRepository;
import org.but.flight.exceptions.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DetailController {
    private static final Logger logger = LoggerFactory.getLogger(DetailController.class);
    @FXML
    public TableView<FlightDetailView> detailView;
    @FXML
    public MenuItem editButton;
    @FXML
    public MenuItem deleteButton;
    @FXML
    public MenuItem detailButton;
    @FXML
    public Button refreshButton;
    @FXML
    public Button allButton;
    @FXML
    public Button findButton;
    @FXML
    public TextField textField;
    @FXML
    public Button createButton;
    @FXML
    private TableColumn<FlightDetailView, Long> flightIdColumn;
    @FXML
    public TableColumn<FlightDetailView, String> departurePlaceColumn;
    @FXML
    public TableColumn<FlightDetailView, String> arrivalPlaceColumn;
    @FXML
    public TableColumn<FlightDetailView, String> departureTimeColumn;
    @FXML
    private TableColumn<FlightDetailView, String> arrivalTimeColumn;
    @FXML
    private TableColumn<FlightDetailView, String> departureDateColumn;
    @FXML
    public TableColumn<FlightDetailView, String> arrivalDateColumn;
    @FXML
    public TableColumn<FlightDetailView, Long> countOfLayoverColumn;

    private FlightRepository ListDetailView;

    public DetailController() {
    }
    @FXML
    private void initialize() {
        ListDetailView = new FlightRepository();

        flightIdColumn.setCellValueFactory(new PropertyValueFactory<FlightDetailView, Long>("id"));
        departurePlaceColumn.setCellValueFactory(new PropertyValueFactory<FlightDetailView, String>("departurePlace"));
        arrivalPlaceColumn.setCellValueFactory(new PropertyValueFactory<FlightDetailView, String>("destinationPlace"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<FlightDetailView, String>("departureTime"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<FlightDetailView, String>("arrivalTime"));
        departureDateColumn.setCellValueFactory(new PropertyValueFactory<FlightDetailView, String>("departureDate"));
        arrivalDateColumn.setCellValueFactory(new PropertyValueFactory<FlightDetailView, String>("arrivalDate"));
        countOfLayoverColumn.setCellValueFactory(new PropertyValueFactory<FlightDetailView, Long>("countOfLayover"));

        List<FlightDetailView> dataList = ListDetailView.flightDetailView();
        ObservableList<FlightDetailView> observablePassengerList = FXCollections.observableArrayList(dataList);
        detailView.setItems(observablePassengerList);

        logger.info("DetailController initialized");
        menuIni();
    }
    public void menuIni() {
        editButton = new MenuItem("Edit flight");
        deleteButton = new MenuItem("Delete flight");
        detailButton = new MenuItem("Detail view");

        ContextMenu menu = new ContextMenu();
        menu.getItems().add(editButton);
        menu.getItems().addAll(detailButton);
        menu.getItems().add(deleteButton);
        detailView.setContextMenu(menu);

        detailButton.setOnAction(event -> {
            FlightDetailView flightDetailView = detailView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/AirplaneDetailView.fxml"));
                Stage stage = new Stage();

                Long flightId = flightDetailView.getId();
                ExtendedFlightView DetailView = ListDetailView.findFlightDetailView(flightId);

                stage.setUserData(DetailView);
                stage.setTitle("Flight System Database Application");

                AirplaneController controller = new AirplaneController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 1050, 600);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });

        editButton.setOnAction(event -> {
            FlightDetailView flightDetailView = detailView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/FlightEditView.fxml"));
                Stage stage = new Stage();

                Long flightId = flightDetailView.getId();
                FlightEditView editView = ListDetailView.findEditView(flightId);

                stage.setUserData(editView);
                stage.setTitle("Flight System Database Application");

                EditController controller = new EditController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 1050, 600);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });

        deleteButton.setOnAction(event -> {
            FlightDetailView flightDetailView = detailView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete flight");
            alert.setHeaderText("Are you sure you want to delete this flight?");
            alert.showAndWait();

            Long flightId = flightDetailView.getId();
            ListDetailView.deleteFlight(flightId);
        });


    }
    public void handleCreate(javafx.event.ActionEvent actionEvent) throws SQLException {
        logger.info("Creating flight...");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/CreateFlight.fxml"));
            Stage stage = new Stage();

            stage.setTitle("Flight System Database Application");

            CreateController controller = new CreateController();
            controller.setStage(stage);
            fxmlLoader.setController(controller);

            Scene scene = new Scene(fxmlLoader.load(), 1050, 600);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }


    public void handleRefresh(javafx.event.ActionEvent actionEvent) throws SQLException {
        logger.info("Database refreshed!");
        DataSourceConfig.getConnection();
        List<FlightDetailView> dataList = ListDetailView.flightDetailView();
        ObservableList<FlightDetailView> observablePassengerList = FXCollections.observableArrayList(dataList);
        detailView.setItems(observablePassengerList);
        detailView.refresh();
        detailView.sort();
    }

    public void handleAll(javafx.event.ActionEvent actionEvent) {
        logger.info("Displaying all rows");
        List<FlightDetailView> dataList = ListDetailView.flightDetailView();
        ObservableList<FlightDetailView> observablePassengerList = FXCollections.observableArrayList(dataList);
        detailView.setItems(observablePassengerList);
    }

    public void handleFind(javafx.event.ActionEvent actionEvent) {
        logger.info("Finding...");
        String userInput = textField.getText();
        List<FlightDetailView> dataList = ListDetailView.findFlight(userInput);
        ObservableList<FlightDetailView> observablePassengerList = FXCollections.observableArrayList(dataList);
        detailView.setItems(observablePassengerList);
    }
}
