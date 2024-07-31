package org.but.flight.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PassengerBasicView {
    private LongProperty passengerId = new SimpleLongProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty passengerName = new SimpleStringProperty();
    private StringProperty passengerSurname = new SimpleStringProperty();
    private StringProperty nickname = new SimpleStringProperty();

    public Long getPassengerId() { return PassengerIdProperty().get(); }
    public void setId(Long passengerId) {
        this.PassengerIdProperty().setValue(passengerId);
    }

    public String getEmail() {
        return emailProperty().get();
    }
    public void setEmail(String email) {
        this.emailProperty().setValue(email);
    }

    public String getPassengerName() {
        return PassengerNameProperty().get();
    }
    public void setPassengerName(String passengerName) {
        this.PassengerNameProperty().setValue(passengerName);
    }

    public String getPassengerSurname() { return passengerSurnameProperty().get(); }
    public void setPassengerSurname(String passengerSurname) { this.passengerSurnameProperty().setValue(passengerSurname); }

    public String getNickname() {
        return nicknameProperty().get();
    }
    public void setNickname(String nickname) {
        this.nicknameProperty().set(nickname);
    }


    public LongProperty PassengerIdProperty() {
        return passengerId;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty PassengerNameProperty() {
        return passengerName;
    }

    public StringProperty passengerSurnameProperty() {
        return passengerSurname;
    }

    public StringProperty nicknameProperty() {
        return nickname;
    }

}
