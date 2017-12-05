package com.example.rusha.bakeryapp;

/**
 * Created by rusha on 6/27/2017.
 */

public class Ingredients {
    String quantity;
    String measure;
    String ingredient;

    public Ingredients(String quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getMeasure() {

        return measure;
    }

    public String getQuantity() {

        return quantity;
    }
}

