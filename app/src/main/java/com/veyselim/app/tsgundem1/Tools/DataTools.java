package com.veyselim.app.tsgundem1.Tools;

import com.veyselim.app.tsgundem1.Common.CommonData;
import com.veyselim.app.tsgundem1.Common.CommonMediaPlayer;
import com.veyselim.app.tsgundem1.Model.PodcastModel;

import java.util.ArrayList;

/**
 * Created by veysel on 04.07.2018.
 */

public class DataTools {

    public static ArrayList<String> GetPodcastTitleList() {
        ArrayList<String> tempArray = new ArrayList<>();

        for (int i = 0; i < CommonData.staticPodcastList.size(); ++i) {

            StringBuilder tempText = new StringBuilder();
            tempText.append(CommonData.staticPodcastList.get(i).totalCount);
            tempText.append(" - ");
            tempText.append(CommonData.staticPodcastList.get(i).titlePodcast);

            tempArray.add(tempText.toString());
        }
        return tempArray;
    }

    public static PodcastModel GetPodcastFromTotalCount(String totalCount) {
        PodcastModel tempModel = new PodcastModel();

        for (int i = 0; i < CommonData.staticPodcastList.size(); ++i) {
            if (String.valueOf(CommonData.staticPodcastList.get(i).totalCount).equals(totalCount)) {
                tempModel = CommonData.staticPodcastList.get(i);
            }
        }

        return tempModel;
    }

    public static ArrayList<String> GetCurrentContentList() {
        ArrayList<String> tempList = new ArrayList<>();

        if (CommonMediaPlayer.staticPodcastModel != null) {
            for (int i = 0; i < CommonMediaPlayer.staticPodcastModel.content.size(); ++i) {
                String tempText = CommonMediaPlayer.staticPodcastModel.content.get(i).contentText;
                tempList.add(tempText);
            }
        }

        return tempList;
    }

    public static ArrayList<String> GetCurrentPodcastDetails() {
        ArrayList<String> tempList = new ArrayList<>();

        if (CommonMediaPlayer.staticPodcastModel != null) {
            PodcastModel tempModel = new PodcastModel();
            tempModel = CommonMediaPlayer.staticPodcastModel;

            tempList.add(tempModel.titlePodcast);
            tempList.add("No : " + tempModel.totalCount);
            tempList.add("Yükleme Tarihi : " + tempModel.uploadDate);
            tempList.add("Açıklama : " + tempModel.explanationPodcast);
            tempList.add("");
            tempList.add("");
            tempList.add("   İçerik Listesi   ");

            for (int i = 0; i < tempModel.content.size(); ++i) {
                tempList.add(tempModel.content.get(i).contentText + " (" + String.valueOf(tempModel.content.get(i).contentLink.size()) + ")");
            }
        }

        return tempList;
    }

    public static ArrayList<String> GetAllContentListForSearch() {
        ArrayList<String> tempList = new ArrayList<>();

        for (int i = 0; i < CommonData.staticPodcastList.size(); ++i) {
            for (int j = 0; j < CommonData.staticPodcastList.get(i).content.size(); ++j) {
                StringBuilder tempText = new StringBuilder();
                tempText.append(CommonData.staticPodcastList.get(i).totalCount);
                tempText.append("-");
                tempText.append(CommonData.staticPodcastList.get(i).content.get(j).contentText);
                tempList.add(tempText.toString());
            }
        }

        return tempList;
    }
}
