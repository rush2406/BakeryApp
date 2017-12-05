package com.example.rusha.bakeryapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rusha on 6/27/2017.
 */

public class Recipe implements Serializable {
    int id;
    String name;
    ArrayList<Ingredients> ingredient = new ArrayList<>();
    ArrayList<Steps> steps = new ArrayList<>();

    public Recipe(int id, String name, ArrayList<Ingredients> ingredient, ArrayList<Steps> steps) {
        this.id = id;
        this.name = name;
        this.ingredient = ingredient;
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public String getName() {

        return name;
    }

    public ArrayList<Ingredients> getIngredient() {

        return ingredient;
    }

    public ArrayList<Steps> getSteps() {

        return steps;
    }

}
