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
        String returnText = String.format("Id : %s\nModel : %s\nMac : %s\nÜretici Firma : %s\nTarih : %s\n\n%s",
                Build.ID,
                Build.MODEL,
                GetMacAddress(context),
                Build.MANUFACTURER,
                new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()),
                infoContentText
        );
        return returnText;
    }

    public static String GetMacAddress(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        try {
            WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInf = wifiMan.getConnectionInfo();
            return wifiInf.getMacAddress();
        } catch (Exception e) {
            return "00:00:00";
        }
    }
}
