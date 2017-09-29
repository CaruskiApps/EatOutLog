package com.caruski.eatoutlog.dagger;

import com.caruski.eatoutlog.activity.MainActivity;
import com.caruski.eatoutlog.activity.NewDishActivity;
import com.caruski.eatoutlog.activity.NewRestActivity;
import com.caruski.eatoutlog.activity.ViewDishesActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {EatOutLogModule.class})
public interface BasicComponent {

    void inject(MainActivity activity);
    void inject(NewDishActivity activity);
    void inject(NewRestActivity activity);
    void inject(ViewDishesActivity activity);
}
