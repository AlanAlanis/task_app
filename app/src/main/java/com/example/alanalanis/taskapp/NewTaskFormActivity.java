package com.example.alanalanis.taskapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class NewTaskFormActivity extends AppCompatActivity {

    BroadcastReceiver showTaskReceiver = new ShowTaskReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task_form);

        initiateTheSeekbarPercentage();
        addListenerToTheSeekbarPercentage();

        initiateTheSwitchDone();
        addListenerToTheSwitchDone();

    }

    @Override
    protected  void onStart(){
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("com.LGF.CUSTOM_INTENT.TasksReady");
        this.registerReceiver(this.showTaskReceiver, intentFilter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetAllTask(taskDBInstance,getApplicationContext());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        TaskDB.destroyInstance();
    }
    @Override
    public void onPause(){
        super.onPause();
        this.unregisterReceiver(this.showTaskReceiver);
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
            //                                              //Crate a new task.

            //                                              //View
            View view
    ) {

        Task task = new Task();

        TextView editTextShortDescription = findViewById(R.id.EditTextShortDescription);
        String strShortDescription = editTextShortDescription.getText().toString();
        task.setShortDescription(strShortDescription);

        TextView editTextLongDescription = findViewById(R.id.EditTextLongDescription);
        String strTextLongDescription = editTextLongDescription.getText().toString();
        task.setLongDescription(strTextLongDescription);

        Switch switchDone = findViewById(R.id.SwitchDone);
        if (switchDone.isChecked())
            task.setPercentage(100);
        else{
            SeekBar seekbarPercentage = findViewById(R.id.SeekbarPercentage);
            task.setPercentage(seekbarPercentage.getProgress());
        }

        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBSaveNewTask(taskDBInstance, task);

        Log.d("New Task",task.getShortDescription() + ", " + task.getLongDescription() + ", " + task.getPercentage());

        finish();
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

    private class ShowTaskReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<Task> listOfTask = DBUtil.getTasks();
            for (Task task: listOfTask){
                Log.d( "***InDataBase - Tasks:**** ", "ShortDescription: " +task.getShortDescription() + ", Percentage: " +
                        String.valueOf(task.getPercentage()));
            }
        }
    }

    public  void BackToMainActivity(View view){
        finish();
    }
}

