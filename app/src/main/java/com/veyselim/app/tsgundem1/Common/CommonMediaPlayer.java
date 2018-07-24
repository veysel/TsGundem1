package com.veyselim.app.tsgundem1.Common;

import android.media.MediaPlayer;

import com.veyselim.app.tsgundem1.Model.PodcastModel;

/**
 * Created by veysel on 04.07.2018.
 */

public class CommonMediaPlayer {
    public static MediaPlayer staticMediaPlayer = new MediaPlayer();
    public static PodcastModel staticPodcastModel = new PodcastModel();

    private static boolean _IsMediaData = false;

    public static boolean GetIsMediaData() {
        return _IsMediaData;
    }

    public static void IsMediaDataNo() {
        _IsMediaData = false;
    }

    public static void IsMediaDataYes() {
        _IsMediaData = true;
    }
}
