package com.example.rechee.flickrfindr;

import android.app.Activity;
import android.app.Application;

import com.example.rechee.flickrfindr.dagger.ContextModule;
import com.example.rechee.flickrfindr.dagger.DaggerSingletonComponent;
import com.example.rechee.flickrfindr.dagger.SingletonComponent;

public class FlickrFindrApplication extends Application {

    private SingletonComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        this.component = DaggerSingletonComponent.builder()
                .contextModule(new ContextModule(this)).build();
    }

    public static FlickrFindrApplication getApplication(Activity activity){
        return (FlickrFindrApplication) activity.getApplicationContext();
    }

    public SingletonComponent getComponent() {
        return component;
    }
}
