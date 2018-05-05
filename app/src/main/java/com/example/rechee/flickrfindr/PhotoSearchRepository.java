package com.example.rechee.flickrfindr;

import android.arch.lifecycle.LiveData;

import com.example.rechee.flickrfindr.model.PhotoSearchResult;

public interface PhotoSearchRepository {
    LiveData<PhotoSearchResult> getSearchResult(String query, int perPage);
}
