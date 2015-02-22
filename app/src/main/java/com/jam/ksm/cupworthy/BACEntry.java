package com.jam.ksm.cupworthy;

/**
 * Created by kimberlystrauch on 2/19/15.
 */
public class BACEntry {
    private long id;
    private int drinks;
    private String gender;
    private int weight;
    private double bac_value;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public int getDrinks() {
        return drinks;
    }

    public String getGender() {
        return gender;
    }

    public int getWeight() {
        return weight;
    }
    public double getBAC() {
        return bac_value;
    }

    public void setDrinks(int drinks) {
        this.drinks = drinks;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setWeight(int weight) {
        this.weight = weight;

    }
    public void setBAC(double bac) {
        this.bac_value = bac;
    }

}

