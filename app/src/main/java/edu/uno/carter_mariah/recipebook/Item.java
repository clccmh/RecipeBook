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
        Map<String, Float> oz = new HashMap<>();
        oz.put("cups", 0.8f);
        oz.put("gallons", 0.128f);

        Map<String, Float> gal = new HashMap<>();
        gal.put("quarts", 4f);
        gal.put("pints", 8f);
        gal.put("cups", 16f);
        gal.put("ounces", 128f);
        gal.put("liters", 3.8f);

        Map<String, Float> quart = new HashMap<>();
        quart.put("pints", 2f);
        quart.put("cups", 4f);
        quart.put("ounces", 32f);

        Map<String, Float> pint = new HashMap<>();
        pint.put("cups", 2f);
        pint.put("ounces", 16f);
        pint.put("milliliters", 480f);

        Map<String, Float> cup = new HashMap<>();
        cup.put("ounces", 8f);
        cup.put("milliliters", 240f);
        cup.put("tablespoons", 16f);
        cup.put("teaspoons", 48f);

        Map<String, Float> tablespoons = new HashMap<>();
        tablespoons.put("cup", 0.16f);
        tablespoons.put("teaspoons", 3f);

        Map<String, Float> teaspoons = new HashMap<>();
        teaspoons.put("cup", 0.48f);
        teaspoons.put("tablespoons", 0.3f);

        Map<String, Map> conversions = new HashMap<>();
        conversions.put("ounces", oz);
        conversions.put("gallons", gal);
        conversions.put("quarts", quart);
        conversions.put("pints", pint);
        conversions.put("cups", cup);
        conversions.put("tablespoons", tablespoons);
        conversions.put("teaspoons", teaspoons);

        if (conversions.containsKey(measurement) && conversions.get(measurement).containsKey(newMeasurement)) {
            quantity *= ((Float) conversions.get(measurement).get(newMeasurement)).floatValue();
            measurement = newMeasurement;
            return true;
        } else {
            if (conversions.containsKey(measurement))
                Log.d("Measurement", "Contains key");

            if (conversions.get(measurement).containsKey(newMeasurement))
                Log.d("Measurement", "Contains new measurement key");
            return false;
        }
    }

}

