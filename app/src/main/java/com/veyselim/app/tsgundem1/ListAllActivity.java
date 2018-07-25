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

import com.veyselim.app.tsgundem1.Model.PodcastModel;
import com.veyselim.app.tsgundem1.Tools.DataTools;
import com.veyselim.app.tsgundem1.Tools.MediaPlayerTools;

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
                if (IsNetworkConnection()) {
                    MediaPlayerTools.CreateMediaPlayer(DataTools.GetPodcastFromTotalCount(String.valueOf(tempCount)));
                    Toast.makeText(getApplicationContext(), String.valueOf(tempCount) + " nolu podcast başladı", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "LÜTFEN İNTERNET BAĞLANTINIZI KONTROL EDİN", Toast.LENGTH_SHORT).show();
                }

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
