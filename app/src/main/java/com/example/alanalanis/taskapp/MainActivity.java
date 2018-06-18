package com.example.alanalanis.taskapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static String tag = "AIAA";

    BroadcastReceiver showTaskReceiver = new ShowTaskReceiver();
    BroadcastReceiver updateTaskCountReceiver = new UpdateTaskCountReceiver();

    @Override
    protected void onCreate(
            //                                              //Este metodo se ejecuta al iniciar la actividad y carga la vista

            //                                              //Bundle
            Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart(
            //                                              //Este metodo se ejecuta al iniciar la actividad
    ){
        super.onStart();
        IntentFilter intentTaskReady = new IntentFilter("com.LGF.CUSTOM_INTENT.TasksReady");
        this.registerReceiver(this.showTaskReceiver, intentTaskReady);
        IntentFilter intentTaskCountReady = new IntentFilter("com.LGF.CUSTOM_INTENT.TasksCountReady");
        this.registerReceiver(updateTaskCountReceiver, intentTaskCountReady);
    }

    @Override
    protected void onResume(){
        super.onResume();
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());

        DBUtil.DBGetAllTask(taskDBInstance,getApplicationContext());

        DBUtil.DBTaskCount(taskDBInstance,getApplicationContext());
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

    public void ShowAll(
            //                                              //Abre la actividad TaskListActivity

            //                                              //Vista
            View view
    )
    {

        Intent intent = new Intent(
                getApplicationContext(), TaskListActivity.class
        );
        startActivity(intent);

    }


    public void FinishApp(
            //                                              //Cierra la aplicacion

            //                                              //vista
            View view){
        finish();
    }

    //------------------------------------------------------------------------------------------------------------------
    private class ShowTaskReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            List<Task> listOfTask = DBUtil.getTasks();
            for (Task task: listOfTask){
                Log.d("AIAA - Tasks ", task.getShortDescription() + ", " +
                        String.valueOf(task.getPercentage()));
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private class UpdateTaskCountReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            int intToDoTask = DBUtil.getToDoTaskCount();
            TextView textViewTaskToDo = findViewById(R.id.TextViewTaskToDoCount);
            textViewTaskToDo.setText(String.valueOf(intToDoTask) + " Task To Do");

            int intDoingTask = DBUtil.getDoingTaskCount();
            TextView textViewTaskDoing = findViewById(R.id.TextViewTaskDoingCount);
            textViewTaskDoing.setText(String.valueOf(intDoingTask) + " Task Doing");

            int intDoneTask = DBUtil.getDoneTaskCount();
            TextView textViewTaskDone = findViewById(R.id.TextViewTaskDoneCount);
            textViewTaskDone.setText(String.valueOf(intDoneTask) + " Task Done ");
        }
    }
    //------------------------------------------------------------------------------------------------------------------

}
