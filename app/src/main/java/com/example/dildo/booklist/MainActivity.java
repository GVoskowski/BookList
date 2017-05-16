package com.example.dildo.booklist;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {


    private static final String BOOKS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=subject+";


    private static final int BOOK_LOADER_ID = 1;
    private static final String LOG_TAG = MainActivity.class.getName();
    /* max results for json url*/
    private static final String RESULTS = "&maxResults=15";
    private EditText searchField;
    private TextView emptyState;
    private BookAdapter mAdapter;
    private String query;
    private LoaderManager loaderManager;
    private Bundle bundle;
    private View progressBar;
    private LinearLayout explain;

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {

        progressBar.setVisibility(View.GONE);

        emptyState.setVisibility(View.VISIBLE);
        explain.setVisibility(View.VISIBLE);

        emptyState.setText(R.string.no_books);

        Log.i(LOG_TAG, "OnLoadFinished() run");
        // Clear the adapter of previous book information
        mAdapter.clear();

        //Add  list (if available) to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, " OnCreateLoader run ");
        // Create new loader
        return new BookLoader(this, query);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

        Log.i(LOG_TAG, "OnLoaderReset() run");
        // Reset loader
        mAdapter.clear();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(LOG_TAG, "  OnCreate() run");


        ImageButton searchBtn = (ImageButton) findViewById(R.id.search_button);

        searchField = (EditText) findViewById(R.id.search_edit);

        ListView bookListView = (ListView) findViewById(R.id.list);

        progressBar = findViewById(R.id.progress_bar);

        emptyState = (TextView) findViewById(R.id.empty_view);

        emptyState.setText(R.string.instructions);

        bookListView.setEmptyView(emptyState);

        progressBar.setVisibility(View.GONE);
        explain = (LinearLayout) findViewById(R.id.explaination);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());


        //Set adapter for listView
        bookListView.setAdapter(mAdapter);

        // Get a reference to the LoaderManager
        loaderManager = getLoaderManager();

        //When the activity is created, check if the loader with the BOOK_LOADER_ID exists.
        if (loaderManager.getLoader(BOOK_LOADER_ID) != null) {
            loaderManager.initLoader(BOOK_LOADER_ID, bundle, MainActivity.this);
        }

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* User Experience Set up*/
                emptyState.setVisibility(View.GONE); //Remove empty state text
                progressBar.setVisibility(View.VISIBLE); // Show progress bar loading completes


                query = BOOKS_REQUEST_URL + formatSearch(searchField.getText().toString());
                Log.i(LOG_TAG, "Test log - " + query);

                NetworkInfo networkInfo = checkInternetConenction();
                if (networkInfo != null && networkInfo.isConnected()) {

                    bundle = new Bundle();
                    bundle.putString("BOOK_API", query);


                    if (loaderManager.getLoader(BOOK_LOADER_ID) != null) {
                        loaderManager.restartLoader(BOOK_LOADER_ID, bundle, MainActivity.this);
                    }


                    loaderManager.initLoader(BOOK_LOADER_ID, bundle, MainActivity.this);
                    Log.i(LOG_TAG, "initloader() ");

                } else {

                    mAdapter.clear();
                    progressBar.setVisibility(View.GONE);
                    emptyState.setVisibility(View.VISIBLE);
                    explain.setVisibility(View.GONE);
                    emptyState.setText(R.string.network_fail);
                }

            }
        });
    }

    /*
    Format EditText
     */
    private String formatSearch(String query) {

        return query.replace(" ", "+") + RESULTS;
    }

    /*
    Internet connection checker
     */
    private NetworkInfo checkInternetConenction() {

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {

            Toast.makeText(getApplicationContext(), R.string.no_internet_conn, Toast.LENGTH_LONG).show();

        }
        return info;
    }
}