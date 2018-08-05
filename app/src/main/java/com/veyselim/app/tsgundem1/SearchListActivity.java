package com.veyselim.app.tsgundem1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.veyselim.app.tsgundem1.Tools.DataTools;

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

        AllContentList = DataTools.GetAllContentListForSearch();
        SetList(AllContentList);

        EditSearch = (EditText) findViewById(R.id.EditSearch);
        EditSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (EditSearch.getText().toString().length() > 2) {
                    SearchText(EditSearch.getText().toString());
                }
                return true;
            }
        });

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
