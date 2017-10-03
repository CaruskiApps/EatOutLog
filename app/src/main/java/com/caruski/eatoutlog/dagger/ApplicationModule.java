package com.caruski.eatoutlog.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * The primary responsibility of this module is to provide the Context (Android's
 * getApplicationContext()) once for the application.  Any sub components that need the context will
 * have it injected by Dagger.
 */
@Module()
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }
}


