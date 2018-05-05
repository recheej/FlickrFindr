package com.example.rechee.flickrfindr.dagger;

import com.example.rechee.flickrfindr.PhotoSearchNetworkRepository;
import com.example.rechee.flickrfindr.PhotoSearchRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Singleton
    @Provides
    PhotoSearchRepository photoSearchRepository(PhotoSearchNetworkRepository photoSearchNetworkRepository){
        return photoSearchNetworkRepository;
    }
}
