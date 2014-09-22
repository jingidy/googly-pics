package com.flukiness.googlypics.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.flukiness.googlypics.R;
import com.flukiness.googlypics.adapters.ImageResultsAdapter;
import com.flukiness.googlypics.models.ImageResult;
import com.flukiness.googlypics.models.ImageSearchQuery;
import com.flukiness.googlypics.utils.EndlessScrollingListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends Activity {
    private EditText etQuery;
    private GridView gvResults;

    private AsyncHttpClient client;
    private ImageSearchQuery searchQuery;

    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();

        client = new AsyncHttpClient();
        searchQuery = new ImageSearchQuery();
        imageResults = new ArrayList<ImageResult>();
        aImageResults = new ImageResultsAdapter(this, imageResults);
        gvResults.setAdapter(aImageResults);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onImageSearch(View v) {
        searchQuery.resetQuery();
        searchQuery.query = etQuery.getText().toString();
        loadSearchResults(0);
    }

    private boolean loadSearchResults(final int offset) {
        searchQuery.page = offset;
        if (!searchQuery.validate()) {
            return false;
        }

        client.get(searchQuery.toString(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());

                JSONArray imageResultsJson;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    if (offset == 0) {
                        aImageResults.clear();
                    }
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("ERROR", "JSON request failure: " + responseString, throwable);
            }
        });

        return true;
    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageResult result = imageResults.get(i);
                showFullImage(result);
            }
        });
        gvResults.setOnScrollListener(new EndlessScrollingListener() {
            @Override
            public boolean onLoadMore(int page, int totalCount) {
                return loadSearchResults(page);
            }
        });
    }

    private void showFullImage(ImageResult imageResult) {
        Intent i = new Intent(this, ImageDisplayActivity.class);
        i.putExtra("url", imageResult.fullUrl);
        startActivity(i);
    }
}
