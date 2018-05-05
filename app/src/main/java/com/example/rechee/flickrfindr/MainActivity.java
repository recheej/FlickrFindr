package com.example.rechee.flickrfindr;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.rechee.flickrfindr.model.Photo;
import com.example.rechee.flickrfindr.model.PhotoSearchResult;
import com.example.rechee.flickrfindr.network.FlickrPhotoService;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    @Inject
    ViewModelFactory viewModelFactory;

    private View progressBar;
    private MainScreenViewModel viewModel;

    private PhotoListAdapter photoListAdapter;
    private List<Photo> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlickrFindrApplication.getApplication(this).getComponent().inject(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.progressBar = findViewById(R.id.view_progressBar);

        final RecyclerView recyclerView = findViewById(R.id.view_recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        this.viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MainScreenViewModel.class);

        this.viewModel.getPhotoSearchResult().observe(this, new Observer<PhotoSearchResult>() {

            @Override
            public void onChanged(@Nullable PhotoSearchResult photoSearchResult) {
                progressBar.setVisibility(View.GONE);
                if(photoSearchResult != null){
                    List<Photo> newPhotos = photoSearchResult.getPhotos().getPhoto();
                    if(photoListAdapter == null){
                        photos = newPhotos;
                        photoListAdapter = new PhotoListAdapter(photos);
                        recyclerView.setAdapter(photoListAdapter);
                    }
                    else{
                        photos.clear();
                        photos.addAll(newPhotos);
                        photoListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.menuItem_Search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);

                viewModel.searchForPhotos(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}
