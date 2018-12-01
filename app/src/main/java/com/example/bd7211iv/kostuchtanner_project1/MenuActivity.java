package com.example.bd7211iv.kostuchtanner_project1;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent=getIntent();
        Player player= (Player) intent.getExtras().getSerializable("player");
        Bundle bundle = new Bundle();
        bundle.putSerializable("player",player);
        if(findViewById(R.id.menu_layout_default)!=null){
            MenuPortraitFragment menu= new MenuPortraitFragment();
            menu.setArguments(bundle);
            FragmentManager manager= this.getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.fragment_container,
                            menu)
                    .commit();
        }
        if(findViewById(R.id.menu_layout_land)!=null){
            MenuLandscapeFragment menu= new MenuLandscapeFragment();
            menu.setArguments(bundle);
            FragmentManager manager= this.getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.fragment_container,
                            menu)
                    .commit();
        }
    }
}
