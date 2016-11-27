package edu.uno.carter_mariah.recipebook;

import java.io.Serializable;

/**
 * Created by carter on 11/26/16.
 */
public class Item extends Object implements Serializable {
    String name;
    int quantity;
    String measurement;

    public Item(String name, int quantity, String measurement) {
        this.name = name;
        this.quantity = quantity;
        this.measurement = measurement;
    }

    public String toString() {
        return String.format("%d %s of %s", quantity, measurement, name);
    }
}

