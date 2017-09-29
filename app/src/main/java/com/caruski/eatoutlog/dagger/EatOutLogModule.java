package com.caruski.eatoutlog.dagger;

import android.content.Context;

import com.caruski.eatoutlog.repository.DishRepository;
import com.caruski.eatoutlog.repository.DishRepositoryImpl;
import com.caruski.eatoutlog.repository.ImageRepository;
import com.caruski.eatoutlog.repository.ImageRepositoryImpl;
import com.caruski.eatoutlog.repository.RestaurantRepository;
import com.caruski.eatoutlog.repository.RestaurantRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EatOutLogModule {

    private Context context;

    public EatOutLogModule(Context context){
        this.context = context;
    }

    @Singleton @Provides
    Context provideContext(){
        return context;
    }

    @Singleton @Provides
    DishRepository provideDishRepository(Context context){
        return new DishRepositoryImpl(context);
    }

    @Singleton @Provides
    ImageRepository provideImageRepository(Context context){
        return new ImageRepositoryImpl(context);
    }

    @Singleton @Provides
    RestaurantRepository provideRestaurantRepository(Context context){
        return new RestaurantRepositoryImpl(context);
    }
}
