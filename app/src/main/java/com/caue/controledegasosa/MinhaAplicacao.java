package com.caue.controledegasosa;

import android.app.Application;

import io.realm.Realm;

public class MinhaAplicacao extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
