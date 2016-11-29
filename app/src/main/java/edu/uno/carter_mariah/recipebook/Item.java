package edu.uno.carter_mariah.recipebook;

import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by carter on 11/26/16.
 */
public class Item extends Object implements Serializable {
    String name;
    float quantity;
    String measurement;

    public Item(String name, float quantity, String measurement) {
        this.name = name;
        this.quantity = quantity;
        this.measurement = measurement;
    }

    public String toString() {
        return !measurement.equals("none")
                ? String.format("%.2f %s of %s", quantity, measurement, name)
                : String.format("%.2f %s", quantity, name);
    }

    public String[] possibleConversions = {
        "ounces",
        "gallons",
        "quarts",
        "pints",
        "cups",
        "tablespoons",
        "teaspoons"
    };

    public boolean convert(String newMeasurement) {
        final String OUNCES = "ounces";
        final String GALLONS = "gallons";
        final String QUARTS = "quarts";
        final String PINTS = "pints";
        final String CUPS = "cups";
        final String TABLESPOONS = "tablespoons";
        final String TEASPOONS = "teaspoons";

        Map<String, Float> oz = new HashMap<>();
        oz.put(GALLONS, 0.0078125f);
        oz.put(QUARTS, 0.03125f);
        oz.put(PINTS, 0.0625f);
        oz.put(CUPS, 0.125f);
        oz.put(TABLESPOONS, 2f);
        oz.put(TEASPOONS, 6f);

        Map<String, Float> gal = new HashMap<>();
        gal.put(OUNCES, 128f);
        gal.put(QUARTS, 4f);
        gal.put(PINTS, 8f);
        gal.put(CUPS, 16f);
        gal.put(TABLESPOONS, 256f);
        gal.put(TEASPOONS, 768f);

        Map<String, Float> quart = new HashMap<>();
        quart.put(GALLONS, 0.25f);
        quart.put(OUNCES, 32f);
        quart.put(PINTS, 2f);
        quart.put(CUPS, 4f);
        quart.put(TABLESPOONS, 64f);
        quart.put(TEASPOONS, 192f);

        Map<String, Float> pint = new HashMap<>();
        pint.put(GALLONS, 0.125f);
        pint.put(OUNCES, 16f);
        pint.put(QUARTS, 0.5f);
        pint.put(CUPS, 2f);
        pint.put(TABLESPOONS, 32f);
        pint.put(TEASPOONS, 96f);

        Map<String, Float> cup = new HashMap<>();
        cup.put(GALLONS, 0.0625f);
        cup.put(OUNCES, 8f);
        cup.put(QUARTS, 0.25f);
        cup.put(PINTS, 0.5f);
        cup.put(TABLESPOONS, 16f);
        cup.put(TEASPOONS, 48f);

        Map<String, Float> tablespoons = new HashMap<>();
        tablespoons.put(GALLONS, 0.00390625f);
        tablespoons.put(OUNCES, 0.5f);
        tablespoons.put(QUARTS, 0.015625f);
        tablespoons.put(CUPS, 0.0625f);
        tablespoons.put(PINTS, 0.03125f);
        tablespoons.put(TEASPOONS, 3f);

        Map<String, Float> teaspoons = new HashMap<>();
        teaspoons.put(GALLONS, 0.00130208f);
        teaspoons.put(OUNCES, 0.166667f);
        teaspoons.put(QUARTS, 0.00520833f);
        teaspoons.put(CUPS, 0.0208333f);
        teaspoons.put(PINTS, 0.0104167f);
        teaspoons.put(TABLESPOONS, 0.33333333f);

        Map<String, Map> conversions = new HashMap<>();
        conversions.put(OUNCES, oz);
        conversions.put(GALLONS, gal);
        conversions.put(QUARTS, quart);
        conversions.put(PINTS, pint);
        conversions.put(CUPS, cup);
        conversions.put(TABLESPOONS, tablespoons);
        conversions.put(TEASPOONS, teaspoons);

        if (conversions.containsKey(measurement) && conversions.get(measurement).containsKey(newMeasurement)) {
            quantity *= ((Float) conversions.get(measurement).get(newMeasurement)).floatValue();
            measurement = newMeasurement;
            return true;
        } else {
            if (conversions.containsKey(measurement)){
                Log.d("Measurement", measurement);
                if (conversions.get(measurement).containsKey(newMeasurement)) {
                    Log.d("New Measurement", newMeasurement);
                }
            }
            return false;
        }
    }

}

