package org.but.flight.controllers;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.but.flight.App;
import org.but.flight.data.FlightRepository;
import org.but.flight.exceptions.DataAccessException;
import org.but.flight.exceptions.ExceptionHandler;
import org.but.flight.exceptions.ResourceNotFoundException;
import org.but.flight.services.AuthService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @FXML
    public Label emailLabel;
    @FXML
    public Label passwordLabel;
    @FXML
    private Button signInButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordTextField;

    private FlightRepository flightRepository;
    private AuthService authService;

    private ValidationSupport validation;

    public LoginController() {
    }

    @FXML
    private void initialize() {
        GlyphsDude.setIcon(signInButton, FontAwesomeIcon.SIGN_IN, "1em");
        emailTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleSignIn();
            }
        });
        passwordTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleSignIn();
            }
        });

        initializeServices();
        initializeValidations();

        logger.info("LoginController initialized");
    }

    private void initializeValidations() {
        validation = new ValidationSupport();
        validation.registerValidator(emailTextField, Validator.createEmptyValidator("The email must not be empty."));
        validation.registerValidator(passwordTextField, Validator.createEmptyValidator("The password must not be empty."));
        signInButton.disableProperty().bind(validation.invalidProperty());
    }

    private void initializeServices() {
        flightRepository = new FlightRepository();
        authService = new AuthService(flightRepository);
    }
    public void signInActionHandler(ActionEvent event) {
        handleSignIn();
    }

    private void handleSignIn() {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        try {
            boolean authenticated = authService.authenticate(email, password);
            if (authenticated) {
                showPassengersView();
            } else {
                showInvalidPasswordDialog();
            }
        } catch (ResourceNotFoundException | DataAccessException e) {
            showDataAccessDialog();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private void showPassengersView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/PassengerBasicView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1050, 600);
            Stage stage = new Stage();
            stage.setTitle("Flight System Database Application");
            stage.setScene(scene);

            Stage stageOld = (Stage) signInButton.getScene().getWindow();
            stageOld.close();

            authConfirmDialog();

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    private void showInvalidPasswordDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Unauthenticated");
        alert.setHeaderText("The user is not authenticated");
        alert.setContentText("Please provide a valid email and password");//ww  w . j  a  va2s  .  co  m

        alert.showAndWait();
    }

    private void showDataAccessDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Could not access data");
        alert.showAndWait();
    }


    private void authConfirmDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logging confirmation");
        alert.setHeaderText("You were successfully logged in.");

        Timeline idlestage = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                alert.setResult(ButtonType.CANCEL);
                alert.hide();
            }
        }));
        idlestage.setCycleCount(1);
        idlestage.play();

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.out.println("ok clicked");
        } else if (result.get() == ButtonType.CANCEL) {
            System.out.println("cancel clicked");
        }
    }

    public void handleOnEnterActionPassword(ActionEvent dragEvent) {
        handleSignIn();
    }
}
