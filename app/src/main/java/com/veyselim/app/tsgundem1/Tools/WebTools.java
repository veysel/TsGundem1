package com.veyselim.app.tsgundem1.Tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.veyselim.app.tsgundem1.Common.CommonData;
import com.veyselim.app.tsgundem1.Model.ContentModel;
import com.veyselim.app.tsgundem1.Model.PodcastModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by veysel on 02.07.2018.
 */

public class WebTools {
    private static String API_URL = "https://raw.githubusercontent.com/veysel/ts-gundem/master/ts-gundem.json";

    private static RequestQueue commonRequestQueue;

    public static void GetAllPodcastData(final Context context) {

        //Toast.makeText(context, "Veriler yükleniyor", Toast.LENGTH_SHORT).show();

        commonRequestQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    JSONArray list = object.getJSONArray("list");

                    CommonData.staticPodcastList = new ArrayList<>();

                    for (int i = 0; i < list.length(); ++i) {

                        PodcastModel tempModel = new PodcastModel();
                        tempModel.year = list.getJSONObject(i).getString("year");
                        tempModel.count = Integer.valueOf(list.getJSONObject(i).getString("count"));
                        tempModel.totalCount = Integer.valueOf(list.getJSONObject(i).getString("totalCount"));
                        tempModel.uploadDate = list.getJSONObject(i).getString("uploadDate");
                        tempModel.podcastLink = list.getJSONObject(i).getString("podcastLink");
                        tempModel.videoLink = list.getJSONObject(i).getString("videoLink");
                        tempModel.siteLink = list.getJSONObject(i).getString("siteLink");
                        tempModel.rangeFirstDate = list.getJSONObject(i).getString("rangeFirstDate");
                        tempModel.rangeLastDate = list.getJSONObject(i).getString("rangeLastDate");
                        tempModel.titlePodcast = list.getJSONObject(i).getString("titlePodcast");
                        tempModel.explanationPodcast = list.getJSONObject(i).getString("explanationPodcast");

                        JSONArray tempContentList = list.getJSONObject(i).getJSONArray("content");
                        if (tempContentList != null) {
                            for (int j = 0; j < tempContentList.length(); ++j) {
                                ContentModel tempContentModel = new ContentModel();
                                tempContentModel.contentText = tempContentList.getJSONObject(j).getString("contentText");

                                JSONArray tempLinkList = tempContentList.getJSONObject(j).getJSONArray("contentLink");
                                for (int a = 0; a < tempLinkList.length(); ++a) {
                                    String tempLink = tempLinkList.getString(a);
                                    tempContentModel.contentLink.add(tempLink);
                                }
                                tempModel.content.add(tempContentModel);
                            }
                        }

                        CommonData.staticPodcastList.add(tempModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(context, "Başarılı", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        commonRequestQueue.add(request);
    }


}
