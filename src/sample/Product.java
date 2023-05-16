package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class Product {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    private Timestamp time;
    private int quantity;

    public Product(int id, String name, Double price, int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = quantity;
    }
    public Product(int id, String name, Double price, Timestamp time) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.time = time;
    }

    public Product(int id, String name, Double price) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }

    public Product(int id, String name, Double price, Timestamp time, int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.time = time;
        this.quantity = quantity;
    }

    public int getId()
    {
        return id.get();
    }
    public String getName()
    {
        return name.get();
    }
    public Double getPrice()
    {
        return price.get();
    }

    public Timestamp getTime() {
        return time;
    }

    public int getQuantity() {
        return quantity;
    }
}
