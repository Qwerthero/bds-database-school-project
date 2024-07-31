package org.but.flight.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import org.but.flight.api.InjectionView;
import org.but.flight.api.PassengerBasicView;
import org.but.flight.data.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InjectionController {
    private static final Logger logger = LoggerFactory.getLogger(InjectionController.class);
    @FXML
    private TableColumn<InjectionView, Long> id;
    @FXML
    private TableColumn<InjectionView, String> name;
    @FXML
    private TableColumn<InjectionView, String> surname;
    @FXML
    private TableView<InjectionView> InjectionTableView;
    @FXML
    private TextField inputField;

    private FlightRepository InjectionView;
    public Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        InjectionView = new FlightRepository();

        id.setCellValueFactory(new PropertyValueFactory<InjectionView, Long>("id"));
        name.setCellValueFactory(new PropertyValueFactory<InjectionView, String>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<InjectionView, String>("surname"));

        logger.info("InjectionController initialized");
    }
    public void HandleConfirmButton() {
        String userInput = inputField.getText();
        List<InjectionView> dataList = InjectionView.getInjectionView(userInput);
        ObservableList<InjectionView> observablePassengerList = FXCollections.observableArrayList(dataList);
        InjectionTableView.setItems(observablePassengerList);
    }
}
