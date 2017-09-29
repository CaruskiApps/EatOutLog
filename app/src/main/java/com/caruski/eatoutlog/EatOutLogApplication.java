package com.caruski.eatoutlog;

import android.app.Application;

import com.caruski.eatoutlog.dagger.BasicComponent;
import com.caruski.eatoutlog.dagger.DaggerBasicComponent;
import com.caruski.eatoutlog.dagger.EatOutLogModule;


public class EatOutLogApplication extends Application {

    private static EatOutLogApplication eatOutLogApplication;
    private BasicComponent basicComponent;

    public static EatOutLogApplication app() {
        return eatOutLogApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        eatOutLogApplication = this;
        basicComponent = DaggerBasicComponent.
                builder()
                .eatOutLogModule(new EatOutLogModule(getApplicationContext()))
                .build();

    }

    public BasicComponent basicComponent(){
        return basicComponent;
    }

}
