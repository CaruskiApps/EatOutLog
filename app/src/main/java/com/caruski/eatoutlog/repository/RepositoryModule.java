package com.caruski.eatoutlog.repository;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * This is the RepositoryModule that new's up all our different repository impl's.
 * <p>
 * Since it lives in the same package as these repository classes, it is allowed to access their
 * package private constructors.  This means we can only get an instance of these repository impls
 * outside of this package if we inject them.
 */
@Module(subcomponents = RepositoryComponent.class)
public class RepositoryModule {

    @Provides
    @Singleton
    RestaurantRepository provideRestaurantRepository(Context context) {
        return new RestaurantRepositoryImpl(context);
    }

    @Provides
    @Singleton
    DishRepository provideDishRepository(Context context) {
        return new DishRepositoryImpl(context);
    }

    @Provides
    @Singleton
    ImageRepository provideImageRepository(Context context) {
        return new ImageRepositoryImpl(context);
    }
}

