package edu.uno.carter_mariah.recipebook;

import java.io.Serializable;

/**
 * Created by carter on 10/11/16.
 * TODO: Change quantities and measurementUnit to be Tuple
 */
public class Recipe extends Object implements Serializable{
    public enum Category {
        ALL,
        BREAKFAST,
        LUNCH,
        DINNER,
        DESERT,
        DRINKS
    }

    String name;
    Category category;
    String[] steps;
    String[] items;
    int[] quantities;
    String[] measurementUnit;

    /**
     *
     * @param name
     * @param steps
     * @param quantities
     * @param items
     * @param measurementUnit
     */
    public Recipe (String name, String[] steps, int[] quantities, String[] items, String[] measurementUnit) {
        this(name, Category.ALL, steps, quantities, items, measurementUnit);
    }

    /**
     *
     * @param name
     * @param category
     * @param steps
     * @param quantities
     * @param items
     * @param measurementUnit
     */
    public Recipe (String name, Category category, String[] steps, int[] quantities, String[] items, String[] measurementUnit) {
        this.name = name;
        this.category = category;
        this.steps = steps;
        this.quantities = quantities;
        this.items = items;
        this.measurementUnit = measurementUnit;
    }

    /**
     *
     * @param i
     * @return A string representing the item with quantity and measurement
     */
    public String getItem(int i) {
        return this.quantities[i] + " " + this.measurementUnit[i] + " of " + this.items[i];
    }

    public class Tuple<X, Y> {
        public X x;
        public Y y;
        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }
}
