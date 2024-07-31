package org.but.flight.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AirplaneNames {
    private LongProperty id = new SimpleLongProperty();
    private LongProperty modelNumber = new SimpleLongProperty();

    public Long getId() {
        return idProperty().get();
    }
    public void setId(Long id) {
        this.idProperty().setValue(id);
    }

    public Long getModelNumber() {
        return modelNumberProperty().get();
    }
    public void setModelNumber(Long modelNumber) {
        this.modelNumberProperty().setValue(modelNumber);
    }

    public LongProperty idProperty() {
        return id;
    }
    public LongProperty modelNumberProperty() {
        return modelNumber;
    }


}
