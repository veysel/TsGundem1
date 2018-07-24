package com.veyselim.app.tsgundem1;

import android.app.AlertDialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.veyselim.app.tsgundem1.Model.PodcastModel;
import com.veyselim.app.tsgundem1.Tools.DataTools;
import com.veyselim.app.tsgundem1.Tools.DownloadFileTools;
import com.veyselim.app.tsgundem1.Tools.MediaPlayerTools;

import java.io.File;
import java.util.ArrayList;

public class ListOfflineActivity extends AppCompatActivity {

    public ArrayList<String> PodcastList = new ArrayList<>();
    public ListView ListAllPodcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_offline);

        ListAllPodcast = (ListView) findViewById(R.id.ListAllPodcast);

        ListUpdate();

        ListAllPodcast.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final int TempPosition = position;
                final String titles[] = {"Başlat", "Sil"};
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListOfflineActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.custom_dialog_layout, null);

                alertDialog.setView(convertView);
                alertDialog.setTitle(PodcastList.get(position));

                ListView lv = (ListView) convertView.findViewById(R.id.listView1);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListOfflineActivity.this, R.layout.custom_list_layout_1, titles);
                lv.setAdapter(adapter);

                final AlertDialog ad = alertDialog.show();

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        switch (position) {
                            case 0: {
                                // Başlat
                                try {
                                    PodcastModel tempPodcastModel = new PodcastModel();
                                    tempPodcastModel.titlePodcast = PodcastList.get(TempPosition);
                                    tempPodcastModel.podcastLink = Environment.getExternalStorageDirectory().toString() + "/TsGundemPodcast/" + PodcastList.get(TempPosition);

                                    MediaPlayerTools.CreateMediaPlayer(tempPodcastModel);
                                    Toast.makeText(getApplicationContext(), "Podcast başladı", Toast.LENGTH_SHORT).show();
                                } catch (Exception exp) {
                                    Toast.makeText(ListOfflineActivity.this, "Hata. İndirilen podcastler 'TsGundemPodcast' klasöründedir.", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                            case 1: {
                                // Sil
                                try {
                                    File file = new File(Environment.getExternalStorageDirectory().toString() + "/TsGundemPodcast/" + PodcastList.get(TempPosition));
                                    boolean deleted = file.delete();

                                    if (deleted)
                                        Toast.makeText(ListOfflineActivity.this, "Silme Başarılı", Toast.LENGTH_SHORT).show();

                                    ListUpdate();

                                } catch (Exception exp) {
                                    Toast.makeText(ListOfflineActivity.this, "Hata. İndirilen podcastler 'TsGundemPodcast' klasöründedir.", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                            default: {
                                Toast.makeText(ListOfflineActivity.this, "Seçim sırasında hata oluştu !", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }

                        ad.dismiss();
                    }
                });

            }
        });
    }

    public void BtnBackClick(View v) {
        finish();
    }

    public void BtnUpdateClick(View v) {
        ListUpdate();
        Toast.makeText(getApplicationContext(), "Liste Güncellendi", Toast.LENGTH_SHORT).show();
    }

    public void ListUpdate() {
        try {
            IsFileDir();

            ListAllPodcast.setAdapter(null);
            PodcastList = new ArrayList<>();

            File f = new File(Environment.getExternalStorageDirectory().toString() + "/TsGundemPodcast");
            File[] files = f.listFiles();
            for (File inFile : files) {
                PodcastList.add(inFile.getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_list_layout_1, PodcastList);
            ListAllPodcast.setAdapter(adapter);
        } catch (Exception exp) {
            Toast.makeText(this, "Hata. İndirilen podcastler 'TsGundemPodcast' klasöründedir.", Toast.LENGTH_SHORT).show();
        }
    }

    private void IsFileDir() {
        File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "TsGundemPodcast");
        if (!cacheDir.exists())
            cacheDir.mkdirs();
    }
}
