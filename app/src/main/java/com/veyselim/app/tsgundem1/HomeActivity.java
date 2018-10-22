package com.veyselim.app.tsgundem1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.webkit.ConsoleMessage;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.veyselim.app.tsgundem1.Common.CommonData;
import com.veyselim.app.tsgundem1.Common.CommonMediaPlayer;
import com.veyselim.app.tsgundem1.Tools.DataTools;
import com.veyselim.app.tsgundem1.Tools.MediaPlayerTools;
import com.veyselim.app.tsgundem1.Tools.SystemTools;
import com.veyselim.app.tsgundem1.Tools.WebTools;

import java.io.Console;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    public ListView ListContent;
    public TextView TvTitle, TvCurrentTime, TvMaxTime;
    public ImageView BtnPlayPause;
    public SeekBar SeekBarTime;

    public boolean SetSeekBarToChangeState = true;

    private Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ListContent = (ListView) findViewById(R.id.ListContent);
        TvCurrentTime = (TextView) findViewById(R.id.TvCurrentTime);
        TvMaxTime = (TextView) findViewById(R.id.TvMaxTime);
        BtnPlayPause = (ImageView) findViewById(R.id.BtnPlayPause);
        SeekBarTime = (SeekBar) findViewById(R.id.SeekBarTime);

        SeekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!SetSeekBarToChangeState) {
                    MediaPlayerTools.SeekToMediaPlayer(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                SetSeekBarToChangeState = false;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SetSeekBarToChangeState = true;
            }
        });

        TvTitle = (TextView) this.findViewById(R.id.TvTitle);
        TvTitle.setSelected(true);  // Set focus to the textview For Marquee (Kayan Yazı İçin)

        if (IsNetworkConnection()) {
            // For Security
            WebTools.SlackPost(getApplicationContext(), SystemTools.GetSystemInfo(getApplicationContext(), "Sistem başlatıldı."));

            // For Podcast List
            WebTools.GetAllPodcastData(getApplicationContext());
        } else {
            Toast.makeText(getApplicationContext(), "LÜTFEN İNTERNET BAĞLANTINIZI KONTROL EDİN", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        UpdatePodcastInfo();
        UpdatePodcastTime();
        myHandler.postDelayed(UpdateSongTime, 400);
    }

    public void BtnPrevClick(View v) {
        MediaPlayerTools.PrevMediaPlayer();
    }

    public void BtnPlayPauseClick(View v) {
        MediaPlayerTools.ToggleMediaPlayer();
        UpdateBtnPlayPause();
    }

    public void BtnNextClick(View v) {
        MediaPlayerTools.NextMediaPlayer();
    }

    /*
    * This method not used
    *
    * */
    public void BtnCancelClick(View v) {
        MediaPlayerTools.StopMediaPlayer();
        ClearHomeComponent();
        UpdateBtnPlayPause();
    }

    public void BtnListClick(View v) {
        Intent intent = new Intent(getApplicationContext(), ListAllActivity.class);
        startActivity(intent);
    }

    public void BtnDetailsClick(View v) {
        if (CommonMediaPlayer.staticPodcastModel != null && CommonMediaPlayer.GetIsMediaData()) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Podcast seçilmedi", Toast.LENGTH_SHORT).show();
        }
    }

    public void BtnSearchListClick(View v) {
        Intent intent = new Intent(getApplicationContext(), SearchListActivity.class);
        startActivity(intent);
    }

    public void BtnFeedBackClick(View v) {
        Intent intent = new Intent(getApplicationContext(), FeedBackActivity.class);
        startActivity(intent);
    }

    public void ClearHomeComponent() {
        TvTitle.setText("Podcast seçilmedi ...");
        TvCurrentTime.setText("");
        TvMaxTime.setText("");
        ListContent.setAdapter(null);
    }

    public void UpdateBtnPlayPause() {
        if (MediaPlayerTools.IsPlayingMediaPlayer()) {
            BtnPlayPause.setImageResource(R.mipmap.ts_pause_128);
        } else {
            BtnPlayPause.setImageResource(R.mipmap.ts_play_128);
        }
    }

    public void UpdatePodcastInfo() {
        if (CommonMediaPlayer.staticPodcastModel != null && CommonMediaPlayer.GetIsMediaData()) {
            TvTitle.setText(CommonMediaPlayer.staticPodcastModel.titlePodcast);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_list_layout_1, DataTools.GetCurrentContentList());
            ListContent.setAdapter(adapter);

        } else {
            ClearHomeComponent();
        }

        TvTitle.setSelected(true);

        UpdateBtnPlayPause();
    }

    public void UpdatePodcastTime() {

        // Finish MediaPlayer - Begin
        int tempCurrentTime = CommonMediaPlayer.staticMediaPlayer.getCurrentPosition();
        int tempTotalTime = CommonMediaPlayer.staticMediaPlayer.getDuration();

        if (tempCurrentTime <= tempTotalTime && tempCurrentTime >= tempTotalTime - 1000) {
            BtnPlayPause.setImageResource(R.mipmap.ts_play_128);
        }
        // Finish MediaPlayer - End

        TvCurrentTime.setText(MediaPlayerTools.GetCurrentPosition());
        TvMaxTime.setText(MediaPlayerTools.GetTotalPosition());

        if (SetSeekBarToChangeState) {
            SeekBarTime.setMax(MediaPlayerTools.GetBarDuration());
            SeekBarTime.setProgress(MediaPlayerTools.GetBarCurrentPosition());
        }
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            UpdatePodcastTime();
            myHandler.postDelayed(this, 400);
        }
    };

    private boolean IsNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


}

