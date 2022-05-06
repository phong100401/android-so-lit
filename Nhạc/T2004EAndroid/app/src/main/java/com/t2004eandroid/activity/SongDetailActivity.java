package com.t2004eandroid.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.t2004eandroid.R;
import com.t2004eandroid.entity.Song;
import com.t2004eandroid.service.LyricService;
import com.t2004eandroid.service.impl.LyricDotComService;
import com.t2004eandroid.util.MyGlobalClass;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class SongDetailActivity extends AppCompatActivity {

    Context _context;
    Song currentSong;
    List<Song> songs;
    TextView songName, songCurrentTime, songTotalTime;
    SeekBar seekBar;
    ImageView thumbnailImageView;
    ImageView controlImageView;
    ImageView previousImageView;
    ImageView nextImageView;
    TextView lyricTextView;
    int currentPosition;
    int x = 0;
//    Future longRunningTaskFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);
        initView();
        initData();
        initActions();
    }

    private void initView() {
        _context = getApplication();
//        MyGlobalClass globalVariable = (MyGlobalClass) _context;
        songName = findViewById(R.id.song_detail_name);
        songCurrentTime = findViewById(R.id.song_detail_current_time);
        songTotalTime = findViewById(R.id.song_detail_total_time);
        seekBar = findViewById(R.id.song_detail_seekbar);
        thumbnailImageView = findViewById(R.id.song_detail_thumbnail);
        controlImageView = findViewById(R.id.song_detail_control);
        previousImageView = findViewById(R.id.song_detail_previous);
        nextImageView = findViewById(R.id.song_detail_next);
        lyricTextView = findViewById(R.id.song_detail_lyric);
        songName.setHorizontallyScrolling(true);
        songName.setSelected(true);
    }

    private void initData() {
//        currentSong = (Song) getIntent().getSerializableExtra("currentSong");
        songs = (List<Song>) getIntent().getSerializableExtra("songs");
        currentPosition = getIntent().getIntExtra("position", 0);
        currentSong = songs.get(currentPosition);
        songName.setText(currentSong.getName());
        Glide.with(_context).load(currentSong.getThumbnail()).into(thumbnailImageView);
        try {
            if (MyGlobalClass.getMediaPlayer().isPlaying() || MyGlobalClass.getMediaPlayer().getCurrentPosition() != 0) {
                controlImageView.setTag("control_play");
                controlImageView.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                MyGlobalClass.getMediaPlayer().reset();
            }
            MyGlobalClass.getMediaPlayer().setDataSource(currentSong.getLink());
            MyGlobalClass.getMediaPlayer().prepare();
            MyGlobalClass.getMediaPlayer().start();
            songCurrentTime.setText(convertToTime(String.valueOf(MyGlobalClass.getMediaPlayer().getCurrentPosition())));
            songTotalTime.setText(convertToTime(String.valueOf(MyGlobalClass.getMediaPlayer().getDuration())));
            seekBar.setProgress(0);
            seekBar.setMax(MyGlobalClass.getMediaPlayer().getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initActions() {
        // Seekbar OnChange Action
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Runnable longRunningTask = new Runnable() {
            @Override
            public void run() {
                if (MyGlobalClass.getMediaPlayer() != null) {
                    songCurrentTime.setText(convertToTime(String.valueOf(MyGlobalClass.getMediaPlayer().getCurrentPosition())));
                    seekBar.setProgress(MyGlobalClass.getMediaPlayer().getCurrentPosition());
                    if (MyGlobalClass.getMediaPlayer().isPlaying()) {
                        thumbnailImageView.setRotation(x++);
                    } else {
                        thumbnailImageView.setRotation(x);
                    }
                }
                new Handler().postDelayed(this::run, 10);
            }
        };
        runOnUiThread(longRunningTask);

//        longRunningTaskFuture = executorService.submit(longRunningTask);

        // SeekBar User Action
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (MyGlobalClass.getMediaPlayer() != null && fromUser) {
                    if (!MyGlobalClass.getMediaPlayer().isPlaying()) {
                        MyGlobalClass.getMediaPlayer().start();
                    }
                    MyGlobalClass.getMediaPlayer().seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Control Action
        controlImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("IMAGE VIEW CONSTANT: ",  String.valueOf(controlImageView.getDrawable().getConstantState()));
                Log.d("Play Circle CONSTANT: ",  String.valueOf(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_play_circle_filled_24, null).getConstantState()));
                Log.d("Play Circle CONSTANT CONTEXT: ",  String.valueOf(ContextCompat.getDrawable(SongDetailActivity.this, R.drawable.ic_baseline_play_circle_filled_24).getConstantState()));
                Log.d("TEST FUNCTION", String.valueOf(checkImageResource(SongDetailActivity.this, controlImageView, R.drawable.ic_baseline_play_circle_filled_24)));
                Log.d("TEST Tag", String.valueOf(controlImageView.getTag()));

                if (String.valueOf(controlImageView.getTag()).equals("control_play")) {
                    controlImageView.setTag("control_pause");
                    controlImageView.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                    MyGlobalClass.getMediaPlayer().pause();
                } else {
                    controlImageView.setTag("control_play");
                    controlImageView.setImageResource(R.drawable.ic_baseline_stop_circle_24);
                    int currentProgress = MyGlobalClass.getMediaPlayer().getCurrentPosition();
                    MyGlobalClass.getMediaPlayer().start();
                    MyGlobalClass.getMediaPlayer().seekTo(currentProgress);
                }
            }
        });

        // Previous Action
        previousImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPosition == 0) {
                    currentPosition = songs.size() - 1;
                } else {
                    currentPosition--;
                }
                loadSongByPosition(currentPosition);
            }
        });

        // Next Action
        nextImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPosition == songs.size() - 1) {
                    currentPosition = 0;
                } else {
                    currentPosition++;
                }
                loadSongByPosition(currentPosition);
            }
        });

        MyGlobalClass.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                nextImageView.performClick();
            }
        });

        // Get Lyrics
        lyricTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });
    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);

        TextView bottomSheetTitle = bottomSheetDialog.findViewById(R.id.bottom_sheet_title);
        TextView bottomSheetAuthor = bottomSheetDialog.findViewById(R.id.bottom_sheet_author);
        TextView bottomSheetLyric = bottomSheetDialog.findViewById(R.id.bottom_sheet_lyric);
        bottomSheetTitle.setText(currentSong.getName());
        bottomSheetAuthor.setText(currentSong.getAuthor());
        try {
            LyricService lyricService = new LyricDotComService();
            List<String> list = lyricService.searchSongByName(currentSong.getName());
            if(list.size() == 0){
                throw new Exception();
            }
            String result = lyricService.getSongLyricByLink(list.get(0));
            if(result == null){
                throw new Exception();
            }
            bottomSheetLyric.setText(Html.fromHtml(result, Html.FROM_HTML_MODE_COMPACT));
        }catch (Exception e){
            Log.d("Lyric", "Cant get lyric from service " + e.getMessage());
            bottomSheetLyric.setText("Sorry baby.");
        }
        bottomSheetDialog.show();
    }

    private void loadSongByPosition(int position){
        currentSong = songs.get(currentPosition);
        songName.setText(currentSong.getName());
        MyGlobalClass.getMediaPlayer().reset();
        Glide.with(_context).load(currentSong.getThumbnail()).into(thumbnailImageView);
        try {
            MyGlobalClass.getMediaPlayer().setDataSource(currentSong.getLink());
            MyGlobalClass.getMediaPlayer().prepare();
            MyGlobalClass.getMediaPlayer().start();
            songCurrentTime.setText(convertToTime(String.valueOf(MyGlobalClass.getMediaPlayer().getCurrentPosition())));
            songTotalTime.setText(convertToTime(String.valueOf(MyGlobalClass.getMediaPlayer().getDuration())));
            seekBar.setProgress(0);
            seekBar.setMax(MyGlobalClass.getMediaPlayer().getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // di an trom
    public static String convertToTime(String duration){
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static boolean checkImageResource(Context ctx, ImageView imageView,
                                             int imageResource) {
        boolean result = false;

        if (ctx != null && imageView != null && imageView.getDrawable() != null) {
            Drawable.ConstantState constantState;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                constantState = ctx.getResources()
                        .getDrawable(imageResource, ctx.getTheme())
                        .getConstantState();
            } else {
                constantState = ctx.getResources().getDrawable(imageResource)
                        .getConstantState();
            }

            if (imageView.getDrawable().getConstantState() == constantState) {
                result = true;
            }
        }

        return result;
    }
}