package org.but.flight.exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import org.but.flight.controllers.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public static void handleException(Exception ex) {
        logger.error(ex.getMessage(), ex);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setWidth(700);
        alert.setHeight(700);
        alert.setTitle("Error Dialog");

        TextArea area = new TextArea("Error message: " + ex.getMessage() + System.lineSeparator() + System.lineSeparator() + "The full stack trace:" + System.lineSeparator() + getFullStackTrace(ex));
        area.setWrapText(true);
        area.setEditable(false);

        alert.getDialogPane().setContent(area);
        alert.setResizable(true);
//        alert.setHeaderText(ex.getMessage());
//        alert.setContentText(getFullStackTrace(ex));
        alert.showAndWait();
    }

    private static String getFullStackTrace(Exception exception) {
        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw)) {
            exception.printStackTrace(pw);
            String fullStackTrace = sw.toString();
            return fullStackTrace;
        } catch (IOException e) {
            System.out.println("It was not possible to get the stack trace for that exception: ");
        }
        return "It was not possible to get the stack trace for that exception.";
    }


}
