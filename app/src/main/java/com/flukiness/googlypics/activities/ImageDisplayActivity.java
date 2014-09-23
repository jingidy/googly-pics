package com.flukiness.googlypics.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flukiness.googlypics.R;
import com.flukiness.googlypics.models.ImageResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        getActionBar().hide();

        final ImageResult result = getIntent().getParcelableExtra(SearchActivity.IMAGE_RESULT_PARAM);
        ImageView ivFullImage = (ImageView) findViewById(R.id.ivFullImage);
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        Picasso.with(this).load(result.fullUrl).into(ivFullImage, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.GONE);
                Log.e("ERROR", "Could not load full-sized image at" + result.fullUrl);
            }
        });
        TextView tvFullTitle = (TextView)findViewById(R.id.tvFullTitle);
        tvFullTitle.setText(Html.fromHtml(result.title));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_display, menu);
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
}
