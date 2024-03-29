package com.example.myaudioplayer;

import static com.example.myaudioplayer.MainActivity.musicFiles;
import static com.example.myaudioplayer.MainActivity.shuffleBoolean;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Player extends AppCompatActivity {
    TextView song_name, artist_name, duration_played, duration_total;
    ImageView cover_art, nextBtn, prevBtn, backBtn, shuffleBtn, repeatBtn;
    ImageButton btnShare;
    FloatingActionButton playPauseBtn ;
    SeekBar seekBar ;
    int position =-1;
    static ArrayList<MusicFiles> listSongs = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Thread playThread, prevThread, nextThread;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initViews ();
        getInenMethod();
        song_name.setText(listSongs.get(position).getTitle());
        artist_name.setText(listSongs.get(position).getArtist());
        // Inside your onCreate method, after setting up your other UI elements


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if( mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(1000);



                }

            }
            // Inside your onCreate method, after setting up your other UI elements



            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Player.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() /1000 ;
                    seekBar.setProgress(mCurrentPosition);
                    duration_played.setText(formattedTime(mCurrentPosition));
                }
                handler.postDelayed(this ,1000);

            }
        });
        shuffleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( shuffleBoolean) {
                    shuffleBoolean = false;
                    shuffleBtn.setImageResource(R.drawable.ic_shuffle_on);

                }
                else {
                    shuffleBoolean = true;
                    shuffleBtn.setImageResource(R.drawable.ic_shuffle_off);
                }
            }
        });
        ImageButton btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareSong();
            }
        });
    }

    //Implicit intent
    private void shareSong() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Now playing: " + listSongs.get(position).getTitle());
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Listen to this amazing song: " + listSongs.get(position).getTitle() +
                " by " + listSongs.get(position).getArtist() + " on MyAudioPlayer app!");

        startActivity(Intent.createChooser(shareIntent, "Share this song via"));
    }
    protected void onResume () {
        playThreadBtn();
        nextThreadBtn();
        prevThreadBtn();
        super.onResume();
    }

    private void prevThreadBtn() {
        prevThread =new Thread() {
            @Override
            public void run() {
                super.run();
                prevBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prevBtnClicked();
                    }
                });
            }
        };
        prevThread.start();
    }

    private void prevBtnClicked() {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1 ) < 0 ? (listSongs.size()-1) :(position-1));
            uri=Uri.parse(listSongs.get(position).getPath());
            mediaPlayer= MediaPlayer.create(getApplicationContext(),uri);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist_name.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            Player.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() /1000 ;
                        seekBar.setProgress(mCurrentPosition);

                    }
                    handler.postDelayed(this ,1000);

                }
            });
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();


        }
        else {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1 ) < 0 ? (listSongs.size()-1) :(position-1));
            uri=Uri.parse(listSongs.get(position).getPath());
            mediaPlayer= MediaPlayer.create(getApplicationContext(),uri);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist_name.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            Player.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() /1000 ;
                        seekBar.setProgress(mCurrentPosition);

                    }
                    handler.postDelayed(this ,1000);

                }
            });
            playPauseBtn.setImageResource(R.drawable.ic_play);
        }

    }

    private void nextThreadBtn() {
        nextThread =new Thread() {
            @Override
            public void run() {
                super.run();
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextBtnClicked();
                    }
                });
            }
        };
        nextThread.start();
    }

    private void nextBtnClicked() {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position +1 ) % listSongs.size());
            uri=Uri.parse(listSongs.get(position).getPath());
            mediaPlayer= MediaPlayer.create(getApplicationContext(),uri);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist_name.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            Player.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() /1000 ;
                        seekBar.setProgress(mCurrentPosition);

                    }
                    handler.postDelayed(this ,1000);

                }
            });
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();


        }
        else {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position +1 ) % listSongs.size());
            uri=Uri.parse(listSongs.get(position).getPath());
            mediaPlayer= MediaPlayer.create(getApplicationContext(),uri);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist_name.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            Player.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() /1000 ;
                        seekBar.setProgress(mCurrentPosition);

                    }
                    handler.postDelayed(this ,1000);

                }
            });
            playPauseBtn.setImageResource(R.drawable.ic_play);
        }
    }

    private void playThreadBtn() {
        playThread =new Thread() {
            @Override
            public void run() {
                super.run();
                playPauseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playPauseBtnClicked();
                    }
                });
            }
        };
        playThread.start();

    }

    private void playPauseBtnClicked() {
        if(mediaPlayer.isPlaying()) {
            playPauseBtn.setImageResource(R.drawable.ic_play);
            mediaPlayer.pause();
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            Player.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() /1000 ;
                        seekBar.setProgress(mCurrentPosition);

                    }
                    handler.postDelayed(this ,1000);

                }
            });
        }
        else {
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            Player.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() /1000 ;
                        seekBar.setProgress(mCurrentPosition);

                    }
                    handler.postDelayed(this ,1000);

                }
            });
        }
    }

    private String formattedTime(int mCurrentPosition) {
        String totalout = "";
        String totalNew ="";
        String seconds = String.valueOf(mCurrentPosition % 60) ;
        String minutes = String.valueOf(mCurrentPosition / 60) ;
        totalout = minutes  + ":" + seconds;
        totalNew = minutes  + ":" + "0" + seconds;
        if(seconds.length() == 1) {
            return totalNew ;

        }
        else {
           return totalout ;
        }


    }

    private void getInenMethod() {
        position = getIntent().getIntExtra("position" , -1);
        listSongs = musicFiles ;
        if(listSongs != null) {
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            uri = Uri.parse(listSongs.get(position).getPath());

        }
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext() ,uri);
            mediaPlayer.start();
        }
        else {
            mediaPlayer = MediaPlayer.create(getApplicationContext() ,uri);
            mediaPlayer.start();
        }
        seekBar.setMax(mediaPlayer.getDuration() / 1000);
        metaData( uri);



    }

    private void initViews() {
        song_name =findViewById(R.id.song_name);
        artist_name=findViewById(R.id.song_artist);
        duration_played=findViewById(R.id.durationPlayed);
        duration_total=findViewById(R.id.durationTotal);
        cover_art= findViewById(R.id.cover_art);
        nextBtn= findViewById(R.id.id_next);
        prevBtn= findViewById(R.id.id_skip);
        backBtn= findViewById(R.id.back_btn);
        shuffleBtn= findViewById(R.id.id_suffle_off);
        repeatBtn= findViewById(R.id.id_repeat);
        playPauseBtn= findViewById(R.id.play_pause);
        seekBar= findViewById(R.id.seekBar);

    }
    private void metaData ( Uri uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int durationTotal= Integer.parseInt(listSongs.get(position).getDuration())/1000;
        duration_total.setText(formattedTime(durationTotal));
        byte [] art= retriever.getEmbeddedPicture();
        Bitmap bitmap = BitmapFactory.decodeByteArray(art,0, art.length);
        if(art != null) {
            Glide.with( this)
                    .asBitmap()
                    .load(art)
                    .into(cover_art);

        }
        else {
            Glide.with( this)
                    .asBitmap()
                    .load(R.drawable.icon)
                    .into(cover_art);
        }


    }
}