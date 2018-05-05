package com.example.rechee.flickrfindr;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rechee.flickrfindr.model.Photo;

import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {
    private final List<Photo> photoData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView photoImageView;
        public final TextView textViewPhotoTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            this.photoImageView = itemView.findViewById(R.id.view_ImageView);
            this.textViewPhotoTitle = itemView.findViewById(R.id.textView_photoTitle);
        }
    }

    public PhotoListAdapter(List<Photo> photoData) {
        this.photoData = photoData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_photo_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { Photo photo = photoData.get(position);

        holder.textViewPhotoTitle.setText(photo.getTitle());
    }

    @Override
    public int getItemCount() {
        return photoData.size();
    }
}
