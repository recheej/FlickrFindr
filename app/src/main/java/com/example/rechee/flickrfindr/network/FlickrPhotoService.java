package com.example.rechee.flickrfindr.network;

import com.example.rechee.flickrfindr.model.PhotoSearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrPhotoService {
    @GET("?method=flickr.photos.search&extras=url_l,url_n")
    Call<PhotoSearchResult> getPhotoSearch(@Query("text") String searchTerm, @Query("per_page") int perPage);
}
