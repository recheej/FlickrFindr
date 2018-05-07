package com.example.rechee.flickrfindr;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.example.rechee.flickrfindr.model.Photo;
import com.example.rechee.flickrfindr.model.PhotoSearchResult;
import com.example.rechee.flickrfindr.model.Photos;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class MainScreenViewModelTest {

    @Rule
    public InstantTaskExecutorRule  instantExecutorRule = new InstantTaskExecutorRule();

    private PhotoSearchRepository photoSearchRepository = mock(PhotoSearchRepository.class);

    private MainScreenViewModel viewModel = new MainScreenViewModel(photoSearchRepository);

    @Test
    public void testPhotoSearchResultBasic() {

        //a basic test that makes sure view model returns expected data from repository

        final PhotoSearchResult testResult = getTestPhotoResult();

        MutableLiveData<PhotoSearchResult> testLiveData = new MutableLiveData<>();
        testLiveData.setValue(testResult);

        when(photoSearchRepository.getSearchResult("shark", 25)).thenReturn(testLiveData);

        //Observer observer = mock(Observer<PhotoSearchResult>)
        viewModel.getPhotoSearchResult().observeForever(new Observer<PhotoSearchResult>() {
            @Override
            public void onChanged(@Nullable PhotoSearchResult photoSearchResult) {
                assertNotNull(photoSearchResult);
                final Photos photos = photoSearchResult.getPhotos();
                assertNotNull(photos);

                final List<Photo> photo = photos.getPhoto();
                assertNotNull(photo);

                assertTrue(photo.size() > 0);

                Photo testPhoto = testResult.getPhotos().getPhoto().get(0);
                assertEquals(testPhoto.getId(), photo.get(0).getId());
                assertEquals(testPhoto.getTitle(), photo.get(0).getTitle());
                assertEquals(testPhoto.getUrlL(), photo.get(0).getUrlL());
                assertEquals(testPhoto.getUrlN(), photo.get(0).getUrlN());
            }
        });
        viewModel.searchForPhotos("shark");
    }

    /**
     * Makes sure a null url (urlN or urlL) doesn't crash the view model. Also makes sure we return placeholder url
     */
    @Test
    public void testPhotoSearchResultHandlesNullUrls() {

        final PhotoSearchResult testResult = getTestPhotoResult();
        final Photo testPhoto = testResult.getPhotos().getPhoto().get(0);
        doCallRealMethod().when(testPhoto).setUrlN(anyString());
        doCallRealMethod().when(testPhoto).setUrlL(anyString());
        doCallRealMethod().when(testPhoto).getUrlN();
        doCallRealMethod().when(testPhoto).getUrlL();

        testPhoto.setUrlL(null);
        testPhoto.setUrlL(null);

        MutableLiveData<PhotoSearchResult> testLiveData = new MutableLiveData<>();
        testLiveData.setValue(testResult);

        when(photoSearchRepository.getSearchResult("shark", 25)).thenReturn(testLiveData);

        //Observer observer = mock(Observer<PhotoSearchResult>)
        viewModel.getPhotoSearchResult().observeForever(new Observer<PhotoSearchResult>() {
            @Override
            public void onChanged(@Nullable PhotoSearchResult photoSearchResult) {
                Photo resultPhoto = photoSearchResult.getPhotos().getPhoto().get(0);
                assertEquals(MainScreenViewModel.PLACEHOLDER_URL, resultPhoto.getUrlL());
                assertEquals(MainScreenViewModel.PLACEHOLDER_URL, resultPhoto.getUrlN());
            }
        });
        viewModel.searchForPhotos("shark");
    }

    @Test
    public void testPhotoSearchResultHandlesEmptyPhotos() {

        final PhotoSearchResult testResult = getTestPhotoResult();
        List<Photo> emptyPhotos = new ArrayList<>();
        when(testResult.getPhotos().getPhoto()).thenReturn(emptyPhotos);


        MutableLiveData<PhotoSearchResult> testLiveData = new MutableLiveData<>();
        testLiveData.setValue(testResult);

        when(photoSearchRepository.getSearchResult("shark", 25)).thenReturn(testLiveData);

        //Observer observer = mock(Observer<PhotoSearchResult>)
        viewModel.getPhotoSearchResult().observeForever(new Observer<PhotoSearchResult>() {
            @Override
            public void onChanged(@Nullable PhotoSearchResult photoSearchResult) {
                final Photos photosContainer = photoSearchResult.getPhotos();
                assertNotNull(photosContainer);

                List<Photo> resultPhotos = photosContainer.getPhoto();
                assertNotNull(resultPhotos);
                assertEquals(0, resultPhotos.size());
            }
        });
        viewModel.searchForPhotos("shark");
    }

    private static PhotoSearchResult getTestPhotoResult() {
        Photo testPhotoOne = mock(Photo.class);
        when(testPhotoOne.getTitle()).thenReturn("test photo title");
        when(testPhotoOne.getId()).thenReturn("1");
        when(testPhotoOne.getUrlL()).thenReturn("https:\\/\\/farm1.staticflickr.com\\/969\\/40128536750_fb4791d55d_n.jpg");
        when(testPhotoOne.getUrlN()).thenReturn("https:\\/\\/farm1.staticflickr.com\\/969\\/40128536750_fb4791d55d_b.jpg");

        List<Photo> photoList = new ArrayList<>();
        photoList.add(testPhotoOne);

        Photos testPhotos = mock(Photos.class);
        when(testPhotos.getPerpage()).thenReturn(25);
        when(testPhotos.getPage()).thenReturn(1);
        when(testPhotos.getPhoto()).thenReturn(photoList);

        PhotoSearchResult result = mock(PhotoSearchResult.class);
        when(result.getPhotos()).thenReturn(testPhotos);

        return result;
    }
}
