package com.caruski.eatoutlog;

import android.app.Application;

import com.caruski.eatoutlog.dagger.ApplicationComponent;
import com.caruski.eatoutlog.dagger.ApplicationModule;
import com.caruski.eatoutlog.dagger.DaggerApplicationComponent;

/**
 * This is our application class, that allows Dagger dependency injection to get started.
 */
public class EatOutLogApplication extends Application {

    private static EatOutLogApplication eatOutLogApplication;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        eatOutLogApplication = this;
        // Trigger the ApplicationComponent injection
        applicationComponent = DaggerApplicationComponent.
                builder()
                // Pass it the applicationContext for the app.
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
    }

    /**
     * Provide static access to the application component built in the onCreate so that Dagger can
     * inject dependencies into various View classes.
     *
     * @return our application component in order to inject things.
     */
    public static ApplicationComponent injector() {
        return eatOutLogApplication.applicationComponent;
    }

}
