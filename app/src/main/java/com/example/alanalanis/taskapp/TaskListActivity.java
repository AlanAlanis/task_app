package com.example.alanalanis.taskapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    BroadcastReceiver showTaskReceiver = new ShowTaskReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    protected  void onStart(){
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("com.LGF.CUSTOM_INTENT.TasksReady");
        this.registerReceiver(this.showTaskReceiver, intentFilter);
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    protected void onResume(){
        super.onResume();
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetAllTask(taskDBInstance,getApplicationContext());
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void onDestroy(){
        super.onDestroy();
        TaskDB.destroyInstance();
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void onPause(){
        super.onPause();
        this.unregisterReceiver(this.showTaskReceiver);
    }

    //-----------------------------------------------------------------------------------------------------------------
    private class ShowTaskReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<Task> listOfTask = DBUtil.getTasks();
            for (Task task: listOfTask){
                Log.d( "***InDataBase - Tasks:**** ", "ShortDescription: " +task.getShortDescription() + ", Percentage: " +
                        String.valueOf(task.getPercentage()));
            }

            RecyclerView recyclerView = findViewById(R.id.recycletViewTasks);

            ListTaskAdapter listTaskAdapter = new ListTaskAdapter(listOfTask);
            recyclerView.setAdapter(listTaskAdapter);

            LinearLayoutManager manager = new LinearLayoutManager(
                    getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);
        }
    }

    //--
    public void ShowToDoTask(
            //                                              //

            //                                              //
            View view
    )
    {
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetToDoTask(taskDBInstance, getApplicationContext());
    }

    public void ShowAllTask(
            //                                              //

            //                                              //
            View view
    ){
        TaskDB  taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetAllTask(taskDBInstance,getApplicationContext());
    }


}
