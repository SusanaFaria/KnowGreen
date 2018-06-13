package com.example.android.knowgreen;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class GreenNewsLoader extends AsyncTaskLoader <List<GreenNews>>{

    /** Tag for log messages */
    private static final String LOG_TAG = GreenNewsLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link GreenNewsLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public GreenNewsLoader(Context context, String url) {
        super(context);

        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<GreenNews> loadInBackground() {
        // Don't perform the request if there are no URLs, or the first URL is null.
        if (mUrl == null) {
            return null;
        }
// Perform the network request, parse the response, and extract a list of earthquakes.
        List<GreenNews> greenNews = GreenQueryUtils.fetchNewsData(mUrl);

        return greenNews;


    }

}



