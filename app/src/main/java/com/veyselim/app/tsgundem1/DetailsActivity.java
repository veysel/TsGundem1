package com.veyselim.app.tsgundem1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.veyselim.app.tsgundem1.Model.PodcastModel;
import com.veyselim.app.tsgundem1.Tools.DataTools;
import com.veyselim.app.tsgundem1.Tools.MediaPlayerTools;

public class DetailsActivity extends AppCompatActivity {

    private TextView TvTitle, TvNo, TvYear, TvYearNo, TvUploadDate, TvRangeDate, TvExplanation, TvContentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TvTitle = (TextView) findViewById(R.id.TvTitle);
        TvNo = (TextView) findViewById(R.id.TvNo);
        TvYear = (TextView) findViewById(R.id.TvYear);
        TvYearNo = (TextView) findViewById(R.id.TvYearNo);
        TvUploadDate = (TextView) findViewById(R.id.TvUploadDate);
        TvRangeDate = (TextView) findViewById(R.id.TvRangeDate);
        TvExplanation = (TextView) findViewById(R.id.TvExplanation);
        TvContentList = (TextView) findViewById(R.id.TvContentList);

        ListDetailsUpdate();
    }

    public void BtnBackClick(View v) {
        finish();
    }

    public void ListDetailsUpdate() {
        ClearListDetails();

        if (MediaPlayerTools.GetMediaPlayerPodcastModel() != null) {
            PodcastModel tempModel = MediaPlayerTools.GetMediaPlayerPodcastModel();

            TvTitle.setText(tempModel.titlePodcast);
            TvNo.setText("No : " + tempModel.totalCount);
            TvYear.setText("Yıl : " + tempModel.year);
            TvYearNo.setText("Yıl No : " + tempModel.count);
            TvUploadDate.setText("Yüklenme Tarihi : " + tempModel.uploadDate);

            StringBuilder tempRangeBuilder = new StringBuilder();
            tempRangeBuilder.append("Tarih Aralığı : ");
            tempRangeBuilder.append(tempModel.rangeFirstDate);
            tempRangeBuilder.append(" - ");
            tempRangeBuilder.append(tempModel.rangeLastDate);
            TvRangeDate.setText(tempRangeBuilder.toString());

            TvExplanation.setText("Açıklama : " + tempModel.explanationPodcast);

            StringBuilder tempBuilder = new StringBuilder();
            for (int i = 0; i < tempModel.content.size(); ++i) {
                tempBuilder.append(" - ");
                tempBuilder.append(tempModel.content.get(i).contentText);
                tempBuilder.append("\n\n");
            }

            TvContentList.setText(tempBuilder.toString());
        }

    }

    public void ClearListDetails() {
        TvTitle.setText("Title");
        TvNo.setText("No");
        TvYear.setText("Year");
        TvYearNo.setText("Year No");
        TvUploadDate.setText("Upload Date");
        TvRangeDate.setText("Range Date");
        TvExplanation.setText("Explanation");
        TvContentList.setText("");
    }
}
