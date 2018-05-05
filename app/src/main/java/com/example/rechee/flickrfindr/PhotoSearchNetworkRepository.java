package com.example.rechee.flickrfindr;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.rechee.flickrfindr.model.PhotoSearchResult;
import com.example.rechee.flickrfindr.network.FlickrPhotoService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotoSearchNetworkRepository implements PhotoSearchRepository {

    private final FlickrPhotoService flickrPhotoService;

    @Inject
    public PhotoSearchNetworkRepository(FlickrPhotoService flickrPhotoService) {
        this.flickrPhotoService = flickrPhotoService;
    }

    @Override
    public LiveData<PhotoSearchResult> getSearchResult(String query, int perPage) {
        final MutableLiveData<PhotoSearchResult> data = new MutableLiveData<>();
        this.flickrPhotoService.getPhotoSearch(query, perPage).enqueue(new Callback<PhotoSearchResult>() {
            @Override
            public void onResponse(Call<PhotoSearchResult> call, Response<PhotoSearchResult> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PhotoSearchResult> call, Throwable t) {

            }
        });
        return data;
    }
}
