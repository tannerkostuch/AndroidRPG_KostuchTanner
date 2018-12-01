package com.example.bd7211iv.kostuchtanner_project1;

import java.io.Serializable;

public class Creature implements Serializable{
    private int health;
    private int attackDmg;
    private String name;

    public Creature(int tHealth,int tAtkDmg, String tName){
        name=tName;
        attackDmg=tAtkDmg;
        health=tHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackDmg() {
        return attackDmg;
    }

    public void setAttackDmg(int attackDmg) {
        this.attackDmg = attackDmg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro(){
        String tmp="";
        tmp+="A "+this.getName()+" appears.";
        return tmp;
    }
}
