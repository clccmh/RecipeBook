package edu.uno.carter_mariah.recipebook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    int id;
    String name;
    Category category;
    int servings;
    List<String> steps;
    List<Item> items;

    public Recipe (int id, String name, Category category, int servings) {
        this(name, category, servings, new ArrayList<String>(), new ArrayList<Item>());
        this.id = id;
    }

    /**
     *
     * @param name
     * @param category
     * @param steps
     * @param items
     */
    public Recipe (String name, Category category, int servings, List<String> steps, List<Item> items) {
        this.name = name;
        this.category = category;
        this.servings = servings;
        this.steps = steps;
        this.items = items;
    }

    public void updateServingSize(int newServings) {
        for (Item item : items) {
            item.quantity = (item.quantity / servings) * newServings;
        }
        servings = newServings;
    }

    public static String[] getCategoriesAsStringArray() {
        Category[] values = Category.values();
        String[] result = new String[values.length-1];
        for (int i = 0; i < result.length; i++) {
            result[i] = values[i+1].toString();
        }
        return result;
    }

    public String toString() {
        return String.format("ID: %d\tName: %s\nItems:%s\nSteps:%s",
                this.id,
                this.name,
                this.items.toString(),
                this.steps.toString()
        );
    }

}
