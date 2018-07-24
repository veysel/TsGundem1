package com.veyselim.app.tsgundem1.Tools;

import android.media.MediaPlayer;

import com.veyselim.app.tsgundem1.Common.CommonMediaPlayer;
import com.veyselim.app.tsgundem1.Model.PodcastModel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by veysel on 04.07.2018.
 */

public class MediaPlayerTools {

    public static void KillMediaPlayer() {
        try {
            if (CommonMediaPlayer.staticMediaPlayer != null) {
                CommonMediaPlayer.staticMediaPlayer.release();
                CommonMediaPlayer.IsMediaDataNo();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void CreateMediaPlayer(PodcastModel podcastModel) {
        try {
            CommonMediaPlayer.staticPodcastModel = podcastModel;
            KillMediaPlayer();

            CommonMediaPlayer.staticMediaPlayer = new MediaPlayer();
            CommonMediaPlayer.staticMediaPlayer.setDataSource(CommonMediaPlayer.staticPodcastModel.podcastLink);
            CommonMediaPlayer.staticMediaPlayer.prepare();
            CommonMediaPlayer.staticMediaPlayer.start();

            CommonMediaPlayer.IsMediaDataYes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void StartMediaPlayer() {
        try {
            if (CommonMediaPlayer.staticMediaPlayer != null) {
                CommonMediaPlayer.staticMediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void PauseMediaPlayer() {
        try {
            if (CommonMediaPlayer.staticMediaPlayer != null) {
                CommonMediaPlayer.staticMediaPlayer.pause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void StopMediaPlayer() {
        try {
            if (CommonMediaPlayer.staticMediaPlayer != null) {
                CommonMediaPlayer.staticMediaPlayer.stop();
                CommonMediaPlayer.IsMediaDataNo();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean IsPlayingMediaPlayer() {
        if (CommonMediaPlayer.staticMediaPlayer != null && CommonMediaPlayer.GetIsMediaData()) {
            return CommonMediaPlayer.staticMediaPlayer.isPlaying();
        } else {
            return false;
        }
    }

    public static void ToggleMediaPlayer() {
        if (IsPlayingMediaPlayer()) {
            PauseMediaPlayer();
        } else {
            StartMediaPlayer();
        }
    }

    public static String GetCurrentPosition() {
        String tempText = "";

        if (CommonMediaPlayer.staticMediaPlayer != null && CommonMediaPlayer.GetIsMediaData()) {

            double tempNow = CommonMediaPlayer.staticMediaPlayer.getCurrentPosition();

            tempText = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours((long) tempNow),
                    TimeUnit.MILLISECONDS.toMinutes((long) tempNow) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours((long) tempNow)),
                    TimeUnit.MILLISECONDS.toSeconds((long) tempNow) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) tempNow)));

            return tempText;

        } else {
            return tempText;
        }
    }

    public static String GetTotalPosition() {
        String tempText = "";

        if (CommonMediaPlayer.staticMediaPlayer != null && CommonMediaPlayer.GetIsMediaData()) {

            double tempTotal = CommonMediaPlayer.staticMediaPlayer.getDuration();

            tempText = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours((long) tempTotal),
                    TimeUnit.MILLISECONDS.toMinutes((long) tempTotal) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours((long) tempTotal)),
                    TimeUnit.MILLISECONDS.toSeconds((long) tempTotal) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) tempTotal)));

            return tempText;

        } else {
            return tempText;
        }
    }

    public static int GetBarCurrentPosition() {
        if (CommonMediaPlayer.GetIsMediaData()) {
            return (int) CommonMediaPlayer.staticMediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }

    public static int GetBarDuration() {
        if (CommonMediaPlayer.GetIsMediaData()) {
            return (int) CommonMediaPlayer.staticMediaPlayer.getDuration();
        } else {
            return 0;
        }
    }

    public static void NextMediaPlayer() {
        if (CommonMediaPlayer.staticMediaPlayer != null && CommonMediaPlayer.GetIsMediaData()) {
            int tempTime = CommonMediaPlayer.staticMediaPlayer.getCurrentPosition() + (10 * 1000);
            if (tempTime < CommonMediaPlayer.staticMediaPlayer.getDuration()) {
                CommonMediaPlayer.staticMediaPlayer.seekTo(tempTime);
            }
        }
    }

    public static void PrevMediaPlayer() {
        if (CommonMediaPlayer.staticMediaPlayer != null && CommonMediaPlayer.GetIsMediaData()) {
            int tempTime = CommonMediaPlayer.staticMediaPlayer.getCurrentPosition() - (10 * 1000);
            if (tempTime > 0) {
                CommonMediaPlayer.staticMediaPlayer.seekTo(tempTime);
            } else {
                CommonMediaPlayer.staticMediaPlayer.seekTo(0);
            }
        }
    }

    public static void SeekToMediaPlayer(int position) {
        if (CommonMediaPlayer.staticMediaPlayer != null && CommonMediaPlayer.GetIsMediaData()) {
            CommonMediaPlayer.staticMediaPlayer.seekTo(position);
        }
    }

    public static PodcastModel GetMediaPlayerPodcastModel() {
        return CommonMediaPlayer.staticPodcastModel;
    }

}
