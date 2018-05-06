package com.example.rechee.flickrfindr;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rechee.flickrfindr.model.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {
    private final List<Photo> photoData;
    private final Picasso picasso;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView photoImageView;
        public final TextView textViewPhotoTitle;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.photoImageView = itemView.findViewById(R.id.view_ImageView);

            this.textViewPhotoTitle = itemView.findViewById(R.id.textView_photoTitle);
        }
    }

    public PhotoListAdapter(List<Photo> photoData) {
        this.photoData = photoData;
        this.picasso = Picasso.get();
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Photo photo = photoData.get(position);

        holder.textViewPhotoTitle.setText(photo.getTitle());

        picasso.load(photo.getUrlN()).fit().centerCrop().into(holder.photoImageView);

        holder.photoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Context context = holder.textViewPhotoTitle.getContext();

                ImageView fullImageView = new ImageView(context);
                fullImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

                picasso.load(photo.getUrlL()).fit().centerCrop().into(fullImageView);

                Dialog settingsDialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                settingsDialog.setContentView(fullImageView);
                settingsDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoData.size();
    }
}
