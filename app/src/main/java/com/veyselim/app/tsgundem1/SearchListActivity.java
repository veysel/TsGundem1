package com.veyselim.app.tsgundem1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.veyselim.app.tsgundem1.Tools.DataTools;
import com.veyselim.app.tsgundem1.Tools.MediaPlayerTools;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class SearchListActivity extends AppCompatActivity {

    public TextView TvTitle;
    public EditText EditSearch;
    public ListView ListAllContent;
    public ArrayList<String> AllContentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        TvTitle = (TextView) findViewById(R.id.TvTitle);
        ListAllContent = (ListView) findViewById(R.id.ListAllContent);

        ListAllContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String tempCount = String.valueOf(parent.getItemAtPosition(position)).split("-")[0];

                if (IsNetworkConnection()) {
                    MediaPlayerTools.CreateMediaPlayer(DataTools.GetPodcastFromTotalCount(String.valueOf(tempCount)));
                    Toast.makeText(getApplicationContext(), String.valueOf(tempCount) + " nolu podcast başladı", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "LÜTFEN İNTERNET BAĞLANTINIZI KONTROL EDİN", Toast.LENGTH_SHORT).show();
                }

            }
        });

        AllContentList = DataTools.GetAllContentListForSearch();

        EditSearch = (EditText) findViewById(R.id.EditSearch);
        EditSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                SearchText(EditSearch.getText().toString());
                return true;
            }
        });

    }

    public void BtnSearchClick(View v) {
        SearchText(EditSearch.getText().toString());
    }

    public void BtnBackClick(View v) {
        finish();
    }

    public void SetList(ArrayList<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_list_layout_1, list);
        ListAllContent.setAdapter(adapter);

        TvTitle.setText("Bulunan Sonuç : " + String.valueOf(list.size()));
    }

    public void SearchText(String searchText) {
        if (searchText.length() > 2) {
            ArrayList<String> tempList = new ArrayList<>();

            TvTitle.setText("Aranıyor...");
            for (int i = 0; i < AllContentList.size(); ++i) {
                if (AllContentList.get(i).toLowerCase().contains(searchText.toLowerCase())) {
                    tempList.add(AllContentList.get(i));
                }
            }

            SetList(tempList);
        }
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
