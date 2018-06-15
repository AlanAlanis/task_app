package com.example.alanalanis.taskapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class NewTaskFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task_form);

        initiateTheSeekbarPercentage();
        addListenerToTheSeekbarPercentage();

        initiateTheSwitchDone();
        addListenerToTheSwitchDone();

    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -- - - - - - - - - - - - - - - - - - - - -- - - - - -
    public void initiateTheSeekbarPercentage(
            //                                              //
    ){
        SeekBar seekbarPercentage = findViewById(R.id.SeekbarPercentage);

        seekbarPercentage.setMax(100);

        int defaultTaskPercentage = 0;
        seekbarPercentage.setProgress(defaultTaskPercentage);

        TextView textViewPercentage = findViewById(R.id.TextViewPercentage);
        textViewPercentage.setText(""+defaultTaskPercentage + "%");

    }

    //- - - - - - -
    public void addListenerToTheSeekbarPercentage(
            //                                              //
    ){
        SeekBar seekbarPercentage = findViewById(R.id.SeekbarPercentage);

        seekbarPercentage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressChangedValue = progress;

            }

            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void onStopTrackingTouch(SeekBar seekBar) {

                Switch switchDone = findViewById(R.id.SwitchDone);
                TextView textViewPercentage = findViewById(R.id.TextViewPercentage);
                if (switchDone.isChecked()){
                    textViewPercentage.setText("100%");
                }
                else{
                    String strTaskPercentage = progressChangedValue + "%";
                    textViewPercentage.setText(strTaskPercentage);
                }
            }
        });

    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public void initiateTheSwitchDone(
            //                                              //
    ){
        Switch switchDone = findViewById(R.id.SwitchDone);

        if (//
            //Si esta activado el switch asignar el 100%
                switchDone.isChecked()
                ){
            //
            TextView textviewPercentage = findViewById(R.id.TextViewPercentage);
            textviewPercentage.setText("100%");
        }

    }

    //- - - - - - - -
    public void addListenerToTheSwitchDone(
            //                                              //Inicializa el texto del porcentaje dependiendo si esta
            //                                              //      activado el Switch
    ){
        Switch switchDone = findViewById(R.id.SwitchDone);

        switchDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView textViewPercentage = findViewById(R.id.TextViewPercentage);
                SeekBar seekbarPercentage = findViewById(R.id.SeekbarPercentage);
                if(isChecked){

                    textViewPercentage.setText("100%");
                    SeekBar seekbar = findViewById(R.id.SeekbarPercentage);
                    seekbar.setProgress(100);
                }
                else {
                    int TaskPercentage = seekbarPercentage.getProgress();
                    String strTaskPercentage = TaskPercentage + "%";
                    textViewPercentage.setText(strTaskPercentage);
                }
            }
        });

    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public void SaveNewTask(
            //                                              //Back to main activity.

            //                                              //View
            View view
    ) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public void CancelNewTask(
            //                                              //Back to main activity.

            //                                              //View
            View view
    ) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }




}
