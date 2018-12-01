package com.example.bd7211iv.kostuchtanner_project1;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuPortraitFragment extends Fragment {
    Player player;
    String[] playerInfo;
    ListView playerList;
    String[] monsterInfo;

    public MenuPortraitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        player =(Player) getArguments().getSerializable("player");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_portrait, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            View view= getView();
            Intent intentMenu= new Intent(view.getContext(), MenuActivity.class);
            onViewCreated(view,savedInstanceState);
            //intentMenu.putExtra("player", player);
           // startActivity(intentMenu);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        playerInfo=new String[]{"Player Info","Name: "+player.getName(),"Class: "+player.getPlayerClass(),"Race: "+player.getPlayerRace(),
                "Attack Damage: "+Integer.toString(player.getAttackDmg()),"Magic Damage: "+Integer.toString(player.getMagicAttackDmg())};
        playerList=view.findViewById(R.id.playerListPortrait);
        ArrayAdapter adapter=new ArrayAdapter(view.getContext(),R.layout.player_label,playerInfo);
        playerList.setAdapter(adapter);

        Monster[] monsters= getMonsterData(view);
        monsterInfo=new String[monsters.length];
        for(int i=0;i<monsters.length;i++){
            monsterInfo[i]="Name: "+monsters[i].getName()+"\nHealth: "+monsters[i].getHealth()+"\nAttack Power: "+monsters[i].getAttackDmg() +"\nWeakness: "
                    +monsters[i].getWeakness()+"\nResistance: "+monsters[i].getResistance()+"\nStrength: "+monsters[i].getStrength();
        }
        ArrayAdapter monsterAdapter=new ArrayAdapter(view.getContext(),R.layout.player_label,monsterInfo);
        GridView monsterGrid= view.findViewById(R.id.monsterGridPortrait);
        monsterGrid.setAdapter(monsterAdapter);
    }

    public Monster[] getMonsterData(View view){
        String jsonData;
        try{
            InputStream is = view.getContext().getAssets().open("monsterdata.json");
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

}
