package com.example.alanalanis.taskapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    final static String tag = "AIAA";

    @Override
    protected void onCreate(
            //                                              //Este metodo se ejecuta al iniciar la actividad y carga la vista

            //                                              //
            Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(tag,"The onCreate() event");
    }

    @Override
    protected void onStart(
            //                                              //Este metodo se ejecuta al iniciar la actividad
    ){
        super.onStart();
        Log.d(tag,"The onStart() event");
    }

    //- - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    protected void onResume(
            //                                              //
    ){
        super.onResume();
        Log.d(tag,"The onResume() event");
    }

    //- - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    protected void onPause(
            //                                              //
    ){
        super.onPause();
        Log.d(tag,"The onPause() event");

    }

    //- - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    protected  void onStop(
            //                                              //
    ){
        super.onStop();
        Log.d(tag,"The onStop() event");
    }

    //- - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    protected  void onDestroy(
            //                                              //
    ){
        super.onDestroy();
        Log.d(tag,"The onDestroy() event");
    }

    public void ShowNewTaskForm(
            //                                              //Abre la actividad NewTaskForm

            //                                              //Vista
            View view
    )
    {

        Intent intent = new Intent(
                getApplicationContext(), NewTaskFormActivity.class
        );
        startActivity(intent);

    }

}
