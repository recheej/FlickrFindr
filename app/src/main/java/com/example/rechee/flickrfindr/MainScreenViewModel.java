package com.example.rechee.flickrfindr;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.rechee.flickrfindr.model.Photo;
import com.example.rechee.flickrfindr.model.PhotoSearchResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainScreenViewModel extends ViewModel {

    private static final int NUM_PER_PAGE = 25;
    private final PhotoSearchRepository photoSearchRepository;
    private MutableLiveData<String> searchQueryLiveData;
    private LiveData<PhotoSearchResult> photoSearchResultLiveData;

    public static final String PLACEHOLDER_URL = "http://www.pixedelic.com/themes/geode/demo/wp-content/uploads/sites/4/2014/04/placeholder4.png?w=640";

    @Inject
    public MainScreenViewModel(final PhotoSearchRepository photoSearchRepository) {
        this.photoSearchRepository = photoSearchRepository;
        searchQueryLiveData = new MutableLiveData<>();

        photoSearchResultLiveData = Transformations.switchMap(searchQueryLiveData, new Function<String, LiveData<PhotoSearchResult>>() {
            @Override
            public LiveData<PhotoSearchResult> apply(String query) {
                return Transformations.map(photoSearchRepository.getSearchResult(query, NUM_PER_PAGE), new Function<PhotoSearchResult, PhotoSearchResult>() {
                    @Override
                    public PhotoSearchResult apply(PhotoSearchResult input) {

                        if(input != null){
                            List<Photo> photos = input.getPhotos().getPhoto();
                            if(photos == null){
                                photos = new ArrayList<>();
                            }

                            for (Photo photo : photos) {
                                if(photo.getUrlL() == null){
                                    photo.setUrlL(PLACEHOLDER_URL);
                                }

                                if(photo.getUrlN() == null){
                                    photo.setUrlN(PLACEHOLDER_URL);
                                }
                            }
                        }

                        return input;
                    }
                });
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
