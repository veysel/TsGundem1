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

    private TextView TvTitle, TvNo, TvYear, TvYearNo, TvUploadDate, TvRangeDate, TvExplanation, TvContentCount, TvPodcastLink, TvVideoLink, TvSiteLink, TvContentList;

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
        TvContentCount = (TextView) findViewById(R.id.TvContentCount);
        TvPodcastLink = (TextView) findViewById(R.id.TvPodcastLink);
        TvVideoLink = (TextView) findViewById(R.id.TvVideoLink);
        TvSiteLink = (TextView) findViewById(R.id.TvSiteLink);
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
            TvContentCount.setText("Konu Başlık Sayısı : " + String.valueOf(tempModel.content.size()));
            TvPodcastLink.setText("Podcast Link (Chrome ile açılıp indirilebilir) : " + tempModel.podcastLink);
            TvVideoLink.setText("Video Link : " + tempModel.videoLink);
            TvSiteLink.setText("Site Link : " + tempModel.siteLink);

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
        TvContentCount.setText("Content Count");
        TvPodcastLink.setText("Podcast Link");
        TvVideoLink.setText("Video Link");
        TvSiteLink.setText("Site Link");
        TvContentList.setText("");
    }
}
