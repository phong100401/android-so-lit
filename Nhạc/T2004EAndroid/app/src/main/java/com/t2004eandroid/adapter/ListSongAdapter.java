package com.t2004eandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.t2004eandroid.R;
import com.t2004eandroid.activity.SongDetailActivity;
import com.t2004eandroid.entity.Song;

import java.util.ArrayList;
import java.util.List;

public class ListSongAdapter extends RecyclerView.Adapter<ListSongAdapter.ViewHolder> {

    Context currentContext;
    List<Song> songs;

    public ListSongAdapter(Context currentContext, List<Song> songs) {
        this.currentContext = currentContext;
        this.songs = songs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(currentContext).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Song currentSong = songs.get(position);
        Glide.with(currentContext).load(currentSong.getThumbnail()).into(holder.imageView);
        holder.songTxtView.setText(currentSong.getName());
        holder.authorTxtView.setText(currentSong.getAuthor());
        holder.singerTxtView.setText(currentSong.getSinger());
        holder.songWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentContext, SongDetailActivity.class);
                intent.putExtra("songs", (ArrayList<Song>) songs);
                int mLastPosition = holder.getAdapterPosition();
                intent.putExtra("position", mLastPosition);
                currentContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout songWrapper;
        ImageView imageView;
        TextView songTxtView;
        TextView authorTxtView;
        TextView singerTxtView;

        public ViewHolder(View itemView) {
            super(itemView);
            songWrapper = itemView.findViewById(R.id.song_wrapper);
            imageView = itemView.findViewById(R.id.image_thumbnail);
            songTxtView = itemView.findViewById(R.id.song_name);
            authorTxtView = itemView.findViewById(R.id.song_author);
            singerTxtView = itemView.findViewById(R.id.song_singer);
        }
    }
}
