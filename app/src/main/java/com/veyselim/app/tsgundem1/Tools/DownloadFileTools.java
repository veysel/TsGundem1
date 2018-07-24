package com.veyselim.app.tsgundem1.Tools;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileTools extends AsyncTask<String, Integer, String> {

    public String TitlePodcast = "ts-gundem";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected void onProgressUpdate(String... progress) {

    }

    @Override
    protected void onPostExecute(String unused) {

    }

    @Override
    protected String doInBackground(String... url) {

        int count;
        try {
            URL urla = new URL(url[0]);

            URLConnection conexion = urla.openConnection();

            conexion.connect();
            int lenghtOfFile = conexion.getContentLength();
            InputStream input = new BufferedInputStream(urla.openStream());

            IsFileDir();
            String path = Environment.getExternalStorageDirectory().toString() + "/TsGundemPodcast/" + TitlePodcast + ".mp3";

            OutputStream output = new FileOutputStream(path);
            byte data[] = new byte[1024];
            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress((int) (total * 100 / lenghtOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("HATA", e.getMessage());
        }

        return null;
    }

    private void IsFileDir() {
        File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "TsGundemPodcast");
        if (!cacheDir.exists())
            cacheDir.mkdirs();
    }

}
