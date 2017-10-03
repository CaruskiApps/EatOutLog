package com.caruski.eatoutlog.repository;


import dagger.Subcomponent;

/**
 * Dagger sub components allow top level components to access all of their @Provides objects.
 * <p>
 * This allows us to provide repository items only in the repository package, but let them be used
 * throughout the application.
 */
@Subcomponent(modules = {RestaurantRepositoryImpl.class, DishRepositoryImpl.class, ImageRepositoryImpl.class})
public interface RepositoryComponent {

    RestaurantRepository restaurantRepository();

    DishRepository dishRepository();

    // nothing uses this yet, which is why this says it is unused.
    ImageRepository imageRepository();

    /**
     * Sub components must provide a Builder, but I'm not sure what this is used for yet.
     */
    @Subcomponent.Builder
    interface Builder {
        RepositoryComponent build();
    }
}

