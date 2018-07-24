package com.veyselim.app.tsgundem1.Model;

import java.util.ArrayList;

/**
 * Created by veysel on 02.07.2018.
 */

public class PodcastModel {
    public String year;
    public int count;
    public int totalCount;
    public String uploadDate;
    public String podcastLink;
    public String videoLink;
    public String siteLink;
    public String rangeFirstDate;
    public String rangeLastDate;
    public String titlePodcast;
    public String explanationPodcast;
    public ArrayList<ContentModel> content;

    public PodcastModel() {
        content = new ArrayList<ContentModel>();
    }
}
