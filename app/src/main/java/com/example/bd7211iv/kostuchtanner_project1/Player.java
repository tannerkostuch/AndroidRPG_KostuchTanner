package com.example.bd7211iv.kostuchtanner_project1;

import java.io.Serializable;

public class Player extends Creature implements Serializable{
    private int mana;
    private String playerClass;
    private String playerRace;
    private int age;
    private boolean difficulty;
    private int magicAttackDmg;



    Player(int tHealth, int tAtkDmg, String tName, int tMana, String tPClass, String tPRace, int tAge, boolean tDiff, int tMgcAtkDmg){
        super(tHealth,tAtkDmg,tName);
        playerClass=tPClass;
        playerRace=tPRace;
        age=tAge;
        mana=tMana+age;
        super.setHealth(tHealth-age);
        difficulty=tDiff;
        if(playerRace.equals("Elf")){
            mana+=20;
            super.setHealth(super.getHealth()-20);
        }
        if(playerRace.equals("Dwarf")){
            mana-=20;
            super.setHealth(super.getHealth()+20);
        }
        if(playerClass.equals("Mage")){
            magicAttackDmg= tMgcAtkDmg*2;
            super.setAttackDmg(tAtkDmg/2);
        }
        if(playerClass.equals("Warrior")) {
            magicAttackDmg= tMgcAtkDmg/2;
            super.setAttackDmg(tAtkDmg*2);
        }

        if(difficulty){
            super.setHealth(super.getHealth()/2);
        }

    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    public String getPlayerRace() {
        return playerRace;
    }

    public void setPlayerRace(String playerRace) {
        this.playerRace = playerRace;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isDifficulty() {
        return difficulty;
    }

    public void setDifficulty(boolean difficulty) {
        this.difficulty = difficulty;
    }

    public int getMagicAttackDmg() {
        return magicAttackDmg;
    }

    public void setMagicAttackDmg(int magicAttackDmg) {
        this.magicAttackDmg = magicAttackDmg;
    }

    public void test(){
        System.out.println("Name: "+super.getName());
        System.out.println("Health: "+super.getHealth());
        System.out.println("Mana: "+mana);
        System.out.println("Magic dmg: "+ magicAttackDmg);
        System.out.println("Atck Dmg: "+ super.getAttackDmg());
    }
}
