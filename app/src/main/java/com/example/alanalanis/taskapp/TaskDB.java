package com.example.alanalanis.taskapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Task.class}, version = 1)
public abstract class TaskDB extends RoomDatabase {

    public abstract TaskDAO taskDAO();
    private static TaskDB INSTANCE;

    public static TaskDB getTaskDB(
            //                                              //Gets a new instance

            //                                              //Context
            final Context context
    ) {
        if (//
                //                                          //Create a new instance if it is not created already
                INSTANCE == null
                ) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    TaskDB.class, "task-database").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(
            //                                              //Reset the instance
    ) {
        INSTANCE = null;
    }

}
