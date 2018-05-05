package com.example.rechee.flickrfindr;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.rechee.flickrfindr.model.PhotoSearchResult;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainScreenViewModel extends ViewModel {

    private static final int NUM_PER_PAGE = 25;
    private final PhotoSearchRepository photoSearchRepository;
    private MutableLiveData<String> searchQueryLiveData;
    private LiveData<PhotoSearchResult> photoSearchResultLiveData;

    @Inject
    public MainScreenViewModel(final PhotoSearchRepository photoSearchRepository) {
        this.photoSearchRepository = photoSearchRepository;
        searchQueryLiveData = new MutableLiveData<>();

        photoSearchResultLiveData = Transformations.switchMap(searchQueryLiveData, new Function<String, LiveData<PhotoSearchResult>>() {
            @Override
            public LiveData<PhotoSearchResult> apply(String query) {
                return photoSearchRepository.getSearchResult(query, NUM_PER_PAGE);
            }
        });
    }

    public LiveData<PhotoSearchResult> getPhotoSearchResult() {
        return photoSearchResultLiveData;
    }

    public void searchForPhotos(String query){
        searchQueryLiveData.setValue(query);
    }
}
