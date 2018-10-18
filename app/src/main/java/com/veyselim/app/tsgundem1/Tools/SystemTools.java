package com.veyselim.app.tsgundem1.Tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by veysel on 16.10.2018.
 */

public class SystemTools {
    public static String GetSystemInfo(Context context, String infoContentText) {
        String returnText = String.format("" +
                        "Id : %s\n" +
                        "Model : %s\n" +
                        "Versiyon: %s (%s)\n" +
                        "Üretici Firma : %s\n" +
                        "Tarih : %s\n\n" +
                        "%s",
                Build.ID,
                Build.MODEL,
                String.valueOf(Build.VERSION.RELEASE),
                String.valueOf(Build.VERSION.SDK_INT),
                Build.MANUFACTURER,
                new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()),
                infoContentText
        );
        return returnText;
    }
}
