package com.example.bd7211iv.kostuchtanner_project1;

import java.io.Serializable;

public class Monster extends Creature implements Serializable{
    private String weakness;
    private String resistance;
    private String strength;

    Monster(int tHealth, int tAtkDmg, String tName, String tWeakness, String tStrength, String tResistance){
        super(tHealth,tAtkDmg,tName);
        weakness=tWeakness;
        strength=tStrength;
        resistance=tResistance;


    }

    public String getWeakness() {
        return weakness;
    }

    public void setWeakness(String weakness) {
        this.weakness = weakness;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getResistance() {
        return resistance;
    }

    public void setResistance(String resistance) {
        this.resistance = resistance;
    }
}
