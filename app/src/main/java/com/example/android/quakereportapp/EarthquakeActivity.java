package com.example.android.quakereportapp;



import android.content.Context;
import android.content.Intent;
import android.media.metrics.Event;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.loader.content.AsyncTaskLoader;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>> {


    private static final int EARTHQUAKE_LOADER_ID = 1;

    private TextView EmptyTextView;
    EarthquakeAdapter mAdapter;
    ListView listView;
    boolean isConnected;

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        
        listView = (ListView) findViewById(R.id.listview);
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
        listView.setAdapter(mAdapter);

        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);



        EmptyTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(EmptyTextView);




        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting()) {

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        }else{
            View loadingIndicator = findViewById(R.id.progress_bar);
            loadingIndicator.setVisibility(View.GONE);
            EmptyTextView.setText("No Internet Connection");
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Earthquake currentEarthquake = mAdapter.getItem(position);
                Uri earthquakeUri = Uri.parse(currentEarthquake.geturl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(websiteIntent);
            }
        });

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
//        Log.i(LOG_TAG,"OnCreateLoader is called");

        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.

        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }


        View loadingIndicator = findViewById(R.id.progress_bar);
        loadingIndicator.setVisibility(View.GONE);
        EmptyTextView.setText("No Earthquakes Found");
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        mAdapter.clear();
    }


    private class EarthquakeAsyncTask extends AsyncTask<String, Void,List<Earthquake>> {

        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Earthquake> result = QueryUtils.fetchEarthquakeData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Earthquake> data) {
            // Clear the adapter of previous earthquake data
            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}