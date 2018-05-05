package com.example.rechee.flickrfindr.dagger;

import com.example.rechee.flickrfindr.MainActivity;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, NetModule.class, RepositoryModule.class})
public interface SingletonComponent {
    @Named("flickr_api_key") String flickrApiKey();

    void inject(MainActivity mainActivity);
}
