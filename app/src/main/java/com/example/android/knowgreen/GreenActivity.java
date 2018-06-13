package com.example.android.knowgreen;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GreenActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<GreenNews>> {

    private static final String GUARDIAN_ENVIRONMENT_REQUEST = "https://content.guardianapis.com/search?show-fields=thumbnail&show-tags=contributor&section=environment&order-by=newest&api-key=490ad52e-b745-4b3e-bfa8-09448aff76c6";
    private static final String LOG_TAG = GreenNewsLoader.class.getName();
    private static final int GREEN_NEWS_LOADER = 1;
    private GreenNewsAdapter mGreenAdapter;
    private TextView mEmptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green);

        ListView greenNewsListView = (ListView) findViewById(R.id.list);
        mEmptyText = (TextView) (findViewById(R.id.empty_list));
        greenNewsListView.setEmptyView(mEmptyText);

        // Create a new adapter that takes an empty list of greenNews as input
        mGreenAdapter = new GreenNewsAdapter(this, new ArrayList<GreenNews>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        greenNewsListView.setAdapter(mGreenAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected news.
        greenNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current news that was clicked on
                GreenNews currentGreenNews = mGreenAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri greenNewsUri = Uri.parse(currentGreenNews.getUrl());

                // Create a new intent to view the news URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, greenNewsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });


        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(GREEN_NEWS_LOADER, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.progress);
            assert loadingIndicator != null;
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyText.setText(getString(R.string.no_connection));
        }
    }


    @Override
    public Loader<List<GreenNews>> onCreateLoader(int i, Bundle bundle) {


        return new GreenNewsLoader(this, GUARDIAN_ENVIRONMENT_REQUEST);
    }

    @Override
    public void onLoadFinished(Loader<List<GreenNews>> loader, List<GreenNews> greenNews) {
        mEmptyText.setText(getString(R.string.no_data));
        // Clear the adapter of previous data
        mGreenAdapter.clear();

        // If there is a valid list of news, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (greenNews != null && !greenNews.isEmpty()) {
            mGreenAdapter.addAll(greenNews);
        }

        ProgressBar myCircle = (ProgressBar) findViewById(R.id.progress);
        myCircle.setVisibility(View.GONE);

    }


    @Override
    public void onLoaderReset(Loader<List<GreenNews>> loader) {

        mGreenAdapter.clear();

    }
}










