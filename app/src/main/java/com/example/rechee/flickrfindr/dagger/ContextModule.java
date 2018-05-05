package com.example.rechee.flickrfindr.dagger;

import android.content.Context;

import com.example.rechee.flickrfindr.R;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context applicationContext;

    public ContextModule(Context applicationContext){
        this.applicationContext = applicationContext;
    }

    @Provides
    @Singleton
    public Context context() {
        return applicationContext;
    }

    @Provides
    @Named("flickr_api_key")
    public String apiKey() {
        return context().getString(R.string.flickr_api_key);
    }

}
