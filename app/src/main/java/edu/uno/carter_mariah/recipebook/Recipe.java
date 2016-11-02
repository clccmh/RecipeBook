package edu.uno.carter_mariah.recipebook;

/**
 * Created by carter on 10/11/16.
 */
public class Recipe extends Object{
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
    int[] quantities;
    String[] items;
    String[] measurements;

    /**
     *
     * @param name
     * @param steps
     * @param quantities
     * @param items
     * @param measurements
     */
    public Recipe (String name, String[] steps, int[] quantities, String[] items, String[] measurements) {
        this(name, Category.ALL, steps, quantities, items, measurements);
    }

    /**
     *
     * @param name
     * @param category
     * @param steps
     * @param quantities
     * @param items
     * @param measurements
     */
    public Recipe (String name, Category category, String[] steps, int[] quantities, String[] items, String[] measurements) {
        this.name = name;
        this.category = category;
        this.steps = steps;
        this.quantities = quantities;
        this.items = items;
        this.measurements = measurements;
    }

    /**
     *
     * @param i
     * @return A string representing the item with quantity and measurement
     */
    public String getItem(int i) {
        return this.quantities[i] + " " + this.measurements[i] + " of " + this.items[i];
    }
}
