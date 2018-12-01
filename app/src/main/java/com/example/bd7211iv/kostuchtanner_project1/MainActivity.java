package com.example.bd7211iv.kostuchtanner_project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private EditText nameET;
    private RadioGroup classGroup;
    private Spinner raceSpinner;
    private TextView selectedAge;
    private SeekBar seekBarAge;
    private ToggleButton difficultyToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameET= findViewById(R.id.nameET);
        classGroup= findViewById(R.id.classRadioGroup);
        difficultyToggle= findViewById(R.id.hardmodeToggle);
        //Load the race spinner with the string array of races
        raceSpinner=findViewById(R.id.raceSpinner);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.races, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raceSpinner.setAdapter(adapter);

        //Manage the seekbar and the textview to correspond with it.
        selectedAge= findViewById(R.id.selectedAge);
        seekBarAge= findViewById(R.id.seekBarAge);
        seekBarAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedAge.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        }); //listens for changes here.
    }

    public void submitClick(View view) {
        if(checkInputs()) {
            String pName = nameET.getText().toString();

            int selectedID = classGroup.getCheckedRadioButtonId();
            RadioButton selectedBtn = findViewById(selectedID);
            String pClass = selectedBtn.getText().toString();

            String pRace = raceSpinner.getSelectedItem().toString();

            int ageValue = Integer.parseInt(selectedAge.getText().toString());
            boolean difficulty = difficultyToggle.isChecked();

            //Player player = new Player(150, 20, pName, 50, pClass, pRace, ageValue, difficulty, 20);

            Intent intentAdventure= new Intent(this, adventureActivity.class);
            intentAdventure.putExtra("name", pName);
            intentAdventure.putExtra("class", pClass);
            intentAdventure.putExtra("race", pRace);
            intentAdventure.putExtra("age", Integer.toString(ageValue));
            intentAdventure.putExtra("hard", Boolean.toString(difficulty));
            startActivity(intentAdventure);
        }
    }
    public boolean checkInputs(){
        if(nameET.getText().toString().isEmpty()){
            Toast.makeText(this, "No name entered!", Toast.LENGTH_LONG).show();
            return false;
        }
        if(classGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Select a class before proceeding.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
