package com.example.bd7211iv.kostuchtanner_project1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class adventureActivity extends AppCompatActivity {
    private Player player;

    private TextView textHealth;
    private TextView textMana;

    private CheckBox cbSavageAttack;
    private CheckBox cbLifeTrans;
    private Button btnRun;
    private Button btnMgc;
    private Button btnMelee;
    private Button btnRest;

    private int currentPlayerHealth;
    private int currentPlayerMana;

    private boolean FacingEnemy;

    private Monster currentMonster;
    private int currentMonsterHealth;
    private String currentMonsterAction;
    private String currentMonsterName;
    private int monsterAttackPower;
    private String monsterWeakness;
    private String monsterStrength;
    private String monsterResistance;
    private TextView monsterHealth;
    private TextView monsterName;
    private TextView monsterAction;
    Monster[] monsters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure);
        findAll();
        monsters = getMonsterData();
        if(savedInstanceState!=null){
             player= (Player) savedInstanceState.getSerializable("PLAYER");
            //monsters= (Monster[]) savedInstanceState.getSerializable("MONSTERS");
            //currentMonster= (Monster) savedInstanceState.getSerializable("CURRENTMON");
            monsterAttackPower= savedInstanceState.getInt("MONSTERATTACKPOWER");
            monsterStrength= savedInstanceState.getString("MONSTERSTRENGTH");
            monsterResistance= savedInstanceState.getString("MONSTERRESISTANCE");
            monsterWeakness= savedInstanceState.getString("MONSTERWEAKNESS");
            currentMonsterName=savedInstanceState.getString("CURRENTMONSTERNAME");
            currentMonsterHealth= savedInstanceState.getInt("CURRENTMONHEALTH");
            currentPlayerHealth= savedInstanceState.getInt("CURRENTPLAYERHEALTH");
            currentPlayerMana=savedInstanceState.getInt("CURRENTPLAYERMANA");
            currentMonsterAction= savedInstanceState.getString("CURRENTMONSTERACTION");
            FacingEnemy=savedInstanceState.getBoolean("FACINGENEMY");
//            Toast.makeText(this,  Integer.toString(monsterAttackPower), Toast.LENGTH_LONG).show();
//            Toast.makeText(this,  monsterStrength, Toast.LENGTH_LONG).show();
//            Toast.makeText(this,  monsterWeakness, Toast.LENGTH_LONG).show();
//            Toast.makeText(this,  currentMonsterName, Toast.LENGTH_LONG).show();
//            Toast.makeText(this,  monsterResistance, Toast.LENGTH_LONG).show();
            viewPlayer();
            viewMonster();
        }

        else{
        //Get what the user entered
        Intent mainIntent=getIntent();
        String name = mainIntent.getStringExtra("name");
        String pclass = mainIntent.getStringExtra("class");
        String race = mainIntent.getStringExtra("race");
        Integer age = Integer.valueOf(mainIntent.getStringExtra("age"));
        Boolean diff = Boolean.valueOf(mainIntent.getStringExtra("hard"));

        btnRun.setText("Next Room");
            //Toast.makeText(this, "CREATING NEW PLAYER", Toast.LENGTH_LONG).show();
            player = new Player(150, 10, name, 50, pclass, race, age, diff, 20);

            //Set max health of player
            currentPlayerHealth = player.getHealth();
            currentPlayerMana = player.getMana();
            viewPlayer();
            //Get monsters from json file
            FacingEnemy = false;
            // player.test();
            // System.out.println(textHealth.getText().toString().equals(""));
        }
    }
    public void findAll(){
        textHealth=findViewById(R.id.textHealth);
        textMana=findViewById(R.id.textMana);

        btnMelee=findViewById(R.id.btnMelee);
        btnMgc=findViewById(R.id.btnMgc);
        btnRest=findViewById(R.id.btnRest);
        btnRun=findViewById(R.id.btnRun);
        cbSavageAttack=findViewById(R.id.cbSavageAttack);
        cbLifeTrans=findViewById(R.id.cbLifeTrans);

        monsterHealth=findViewById(R.id.monsterHealth);
        monsterName=findViewById(R.id.monsterName);
        monsterAction=findViewById(R.id.monsterAction);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Toast.makeText(this, "Actually Saved!", Toast.LENGTH_LONG).show();
        //outState.putSerializable("MONSTERS",monsters);
        //outState.putSerializable("CURRENTMON",currentMonster);
        outState.putString("CURRENTMONSTERNAME",currentMonsterName);
        outState.putString("MONSTERWEAKNESS",monsterWeakness);
        outState.putString("MONSTERRESISTANCE",monsterResistance);
        outState.putString("MONSTERSTRENGTH",monsterStrength);
        outState.putInt("MONSTERATTACKPOWER",monsterAttackPower);
        outState.putInt("CURRENTMONHEALTH",currentMonsterHealth);
        outState.putString("CURRENTMONSTERACTION",currentMonsterAction);

        outState.putBoolean("FACINGENEMY",FacingEnemy);

        outState.putSerializable("PLAYER",player);
        outState.putInt("CURRENTPLAYERHEALTH",currentPlayerHealth);
        outState.putInt("CURRENTPLAYERMANA",currentPlayerMana);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Toast.makeText(this, "Actually Restored!", Toast.LENGTH_LONG).show();
    }

    public Monster[] getMonsterData(){
        String jsonData;
        try{
            InputStream is = this.getAssets().open("monsterdata.json");
            int size= is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonData= new String(buffer, "UTF-8");
            JSONObject obj = new JSONObject(jsonData);
            JSONArray monArray = obj.getJSONArray("monsters");

            Monster[] monsters=new Monster[monArray.length()];

            for(int i=0; i<monArray.length(); i++) {
                JSONObject mon = monArray.getJSONObject(i);
                String mname = mon.getString("name");
                Monster tmpMon=new Monster(Integer.valueOf(mon.getString("health")),Integer.valueOf(mon.getString("attackDmg")),mon.getString("name"),
                        mon.getString("weakness"),mon.getString("strength"),mon.getString("resistance"));
                monsters[i]=tmpMon;
            }
            return monsters;

        }
        catch (JSONException e){
            Log.d("DataCollectError", "JSON Problem");
        }
        catch (IOException e){
            Log.d("DataCollectError", "IO Problem");
        }
        // Just incase it doesnt work, itll return an empty array.
        Monster[] monsters=new Monster[0];
        return monsters;
    }

    public void handleMelee(View view) {
        if(FacingEnemy) {
            currentMonsterAction="You hit the monster";
            int calcPower = player.getAttackDmg();
            if (cbSavageAttack.isChecked()) {
                calcPower *= 2;
                currentMonsterAction+=" with no regard for your own well being";
            }
            if (monsterWeakness.equals("Physical")) {
                calcPower *= 2;
                currentMonsterAction+=", it seems really effective!";
            }
            if (monsterResistance.equals("Physical")) {
                calcPower /= 2;
                currentMonsterAction+="... it just looks angrier";
            }

            if (calcPower >= currentMonsterHealth) {
                monsterDeath();
            }
            else {
                currentMonsterHealth -= calcPower;
                currentMonsterAction +=".\n"+ monsterAction();
                viewMonster();
            }
        }
        else{
            currentMonsterAction="You swing into the air... Nothing seems to change.";
            viewMonster();
        }
    }

    public void handleMagic(View view) {
        if(FacingEnemy) {
            currentMonsterAction="You shoot the monster";
            int calcPower = player.getMagicAttackDmg();
            if (cbLifeTrans.isChecked()) {
                if(checkDeath(5,currentPlayerHealth)){
                    die();
                }
                else {
                    currentPlayerHealth-=5;
                    viewPlayer();
                    currentMonsterAction += " with energy from your very being";
                }
            }
            if(!cbLifeTrans.isChecked()){
                if(currentPlayerMana>=10){
                    currentPlayerMana-=10;
                }
                else{
                    currentPlayerMana=0;
                    calcPower=0;
                    currentMonsterAction+=", but your mana is too low and the spell fizzles";
                }
            }

            if (monsterWeakness.equals("Magic")) {
                calcPower *= 2;
                currentMonsterAction+=", it seems really effective!";
            }
            if (monsterResistance.equals("Magic")) {
                calcPower /= 2;
                currentMonsterAction+="... it just looks angrier";
            }
            if (calcPower >= currentMonsterHealth) {
                monsterDeath();
            }
            else {
                currentMonsterHealth -= calcPower;
                currentMonsterAction +=".\n"+ monsterAction();
                viewMonster();
            }

        }
        else{
            currentMonsterAction="You're smart enough not to waste mana.";
            viewMonster();
        }
    }

    public void handleRun(View view) {
        if(FacingEnemy){
            Random ran = new Random();
            int random = ran.nextInt(10 - 0) + 0;
            if(random>5){
                currentMonsterAction="You did not escape, \n"+monsterAction();
                viewMonster();
            }
            else{
                Random r = new Random();
                int randomMonsterIndex = r.nextInt(monsters.length - 0) + 0;
                Monster currentMonster = monsters[randomMonsterIndex];
                monsterWeakness=currentMonster.getWeakness();
                monsterResistance=currentMonster.getResistance();
                monsterStrength=currentMonster.getStrength();
                monsterAttackPower=currentMonster.getAttackDmg();
                currentMonsterHealth=currentMonster.getHealth();
                currentMonsterAction=currentMonster.getIntro();
                currentMonsterName=currentMonster.getName();

                viewMonster();
                FacingEnemy=true;
                btnRun.setText("Run Away");
            }
        }

        else{
            Random r = new Random();
            int randomMonsterIndex = r.nextInt(monsters.length - 0) + 0;
            Monster currentMonster = monsters[randomMonsterIndex];
            monsterWeakness=currentMonster.getWeakness();
            monsterResistance=currentMonster.getResistance();
            monsterStrength=currentMonster.getStrength();
            monsterAttackPower=currentMonster.getAttackDmg();
            currentMonsterHealth=currentMonster.getHealth();
            currentMonsterAction=currentMonster.getIntro();
            currentMonsterName=currentMonster.getName();

            viewMonster();
            FacingEnemy=true;
            btnRun.setText("Run Away");
        }

    }

    public void handleRest(View view) {
        if(FacingEnemy){
            currentMonsterAction="You try to put up your tent when you realize you forgot about the "+ currentMonsterName+".\n";
            currentMonsterAction+=monsterAction();
            viewMonster();
        }
        else{
            currentMonsterAction="You rest peacefully.";
            currentPlayerHealth=player.getHealth();
            currentPlayerMana=player.getMana();
            viewMonster();
            viewPlayer();
        }
    }

    public String monsterAction(){
        currentMonsterAction="The "+currentMonsterName+" attacks!";
        int calcPower=monsterAttackPower;
        if(monsterStrength.equals(player.getPlayerRace())){
            calcPower*=2;
        }
        if(cbSavageAttack.isChecked()){
            calcPower*=2;
        }
        if(!checkDeath(calcPower,currentPlayerHealth)){
            currentPlayerHealth-=calcPower;
        }
        else{
            die();
        }
        viewPlayer();
        return currentMonsterAction;
    }

    public boolean checkDeath(int damage, int health){
        if(damage>=health){
            return true;
        }
        else{
            return false;
        }
    }

    public void die(){
        Intent intentMain= new Intent(this, MainActivity.class);
        startActivity(intentMain);
        Toast.makeText(this, "You see a bright light and hear a voice ask you...", Toast.LENGTH_LONG).show();
    }
    public void monsterDeath(){
        FacingEnemy=false;
        currentMonsterHealth=0;
        currentMonsterAction="The monster falls before you.";
        currentMonsterName="Nothing";
        btnRun.setText("Next Room");
        viewMonster();
    }

    public void viewPlayer(){
        textHealth.setText(Integer.toString(currentPlayerHealth));
        textMana.setText(Integer.toString(currentPlayerMana));
        if(FacingEnemy){
            btnRun.setText("Run Away");
        }
        else{
            btnRun.setText("Next Room");
        }
    }

    public void viewMonster(){
        monsterHealth.setText(Integer.toString(currentMonsterHealth));
        monsterName.setText(currentMonsterName);
        monsterAction.setText(currentMonsterAction);
    }



    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        IsFinish("Want to return to the main menu?");
    }

    public void IsFinish(String alertmessage) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        android.os.Process.killProcess(android.os.Process.myPid());
                        // This above line close correctly
                        //finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(alertmessage)
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }


    public void handleMenu(View view) {
        Intent intentMenu= new Intent(this, MenuActivity.class);
        intentMenu.putExtra("player", player);
        startActivity(intentMenu);
    }
}
