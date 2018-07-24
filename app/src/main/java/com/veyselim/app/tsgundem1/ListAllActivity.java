package com.veyselim.app.tsgundem1;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.veyselim.app.tsgundem1.Common.CommonData;
import com.veyselim.app.tsgundem1.Model.PodcastModel;
import com.veyselim.app.tsgundem1.Tools.DataTools;
import com.veyselim.app.tsgundem1.Tools.DownloadFileTools;
import com.veyselim.app.tsgundem1.Tools.MediaPlayerTools;

import java.util.ArrayList;

public class ListAllActivity extends AppCompatActivity {

    public ListView ListAllPodcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);

        ListAllPodcast = (ListView) findViewById(R.id.ListAllPodcast);

        ListUpdate();

        ListAllPodcast.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final int tempCount = position + 1;

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListAllActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.custom_dialog_layout, null);

                alertDialog.setView(convertView);
                alertDialog.setTitle(String.valueOf(tempCount) + " Nolu Podcast");

                ListView lv = (ListView) convertView.findViewById(R.id.listView1);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListAllActivity.this, R.layout.custom_list_layout_1, getResources().getStringArray(R.array.list_alert_dialog));
                lv.setAdapter(adapter);

                final AlertDialog ad = alertDialog.show();

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        switch (position) {
                            case 0: {
                                // Başlat
                                if (IsNetworkConnection()) {
                                    String tempTotalCount = String.valueOf(tempCount);
                                    MediaPlayerTools.CreateMediaPlayer(DataTools.GetPodcastFromTotalCount(tempTotalCount));
                                    Toast.makeText(getApplicationContext(), tempTotalCount + " nolu podcast başladı", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "LÜTFEN İNTERNET BAĞLANTINIZI KONTROL EDİN", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                            case 1: {
                                // İndir
                                if (IsNetworkConnection()) {

                                    PodcastModel tempPodcastModel = new PodcastModel();
                                    tempPodcastModel = DataTools.GetPodcastFromTotalCount(String.valueOf(tempCount));

                                    Toast.makeText(ListAllActivity.this, "İndirme Başladı", Toast.LENGTH_SHORT).show();

                                    DownloadFileTools tempTools = new DownloadFileTools();
                                    tempTools.TitlePodcast = String.valueOf(tempPodcastModel.totalCount) + "-TsGundem-" + tempPodcastModel.year + "-" + String.valueOf(tempPodcastModel.count);
                                    tempTools.execute(tempPodcastModel.podcastLink);

                                } else {
                                    Toast.makeText(getApplicationContext(), "LÜTFEN İNTERNET BAĞLANTINIZI KONTROL EDİN", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                            default: {
                                Toast.makeText(ListAllActivity.this, "Seçim sırasında hata oluştu !", Toast.LENGTH_SHORT).show();
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
        ListAllPodcast.setAdapter(null);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_list_layout_1, DataTools.GetPodcastTitleList());
        ListAllPodcast.setAdapter(adapter);
    }

    private boolean IsNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
