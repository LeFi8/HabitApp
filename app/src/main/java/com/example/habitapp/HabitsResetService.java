package com.example.habitapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class HabitsResetService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        DbHelper db = new DbHelper(this);
        db.resetAllHabitsStatus();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
