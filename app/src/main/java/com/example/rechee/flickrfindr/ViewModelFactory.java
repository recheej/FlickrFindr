package com.example.rechee.flickrfindr;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final MainScreenViewModel mainScreenViewModel;

    @Inject
    public ViewModelFactory(MainScreenViewModel mainScreenViewModel) {
        this.mainScreenViewModel = mainScreenViewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == MainScreenViewModel.class){
            return (T) mainScreenViewModel;
        }

        throw new RuntimeException("could not get view model: " + modelClass.getCanonicalName());
    }
}
