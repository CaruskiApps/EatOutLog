package com.caruski.eatoutlog.dagger;

import com.caruski.eatoutlog.activity.MainActivity;
import com.caruski.eatoutlog.activity.NewDishActivity;
import com.caruski.eatoutlog.activity.NewRestActivity;
import com.caruski.eatoutlog.activity.ViewDishesActivity;
import com.caruski.eatoutlog.repository.DishRepository;
import com.caruski.eatoutlog.repository.ImageRepository;
import com.caruski.eatoutlog.repository.RepositoryModule;
import com.caruski.eatoutlog.repository.RestaurantRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RepositoryModule.class})
public interface ApplicationComponent {

    // The various views that we expect Dagger to be able to inject things into.
    void inject(MainActivity activity);

    void inject(NewDishActivity activity);

    void inject(NewRestActivity activity);

    void inject(ViewDishesActivity activity);

    // Our items from the repository module.
    RestaurantRepository restaurantRepository();

    DishRepository dishRepository();

    // nothing uses this yet, which is why it says it's unused.
    ImageRepository imageRepository();
}


