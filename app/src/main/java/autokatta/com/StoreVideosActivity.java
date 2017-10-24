package autokatta.com;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.allattentionhere.autoplayvideos.AAH_CustomRecyclerView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.adapter.VideoAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

public class StoreVideosActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    ApiCall mApiCall;
    private ProgressDialog pDialog;
    String myContact;
    VideoAdapter adapter;

    AAH_CustomRecyclerView recyclerView;
    LinearLayoutManager mLinearLayoutManager;
    List<String> videosList = new ArrayList<>();
    //Picasso picasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_videos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Videos");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (AAH_CustomRecyclerView) findViewById(R.id.rv_home);


        mApiCall = new ApiCall(StoreVideosActivity.this, this);
        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null);
        recyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        for (int i = 0; i < 5; i++) {
            //videosList.add("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp");
            videosList.add(getString(R.string.base_image_url) + "VID_20171005_185639.mp4");
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }


                adapter = new VideoAdapter(getApplicationContext(), videosList);
                recyclerView.setActivity(StoreVideosActivity.this); //todo before setAdapter
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollBy(0, 1);
                recyclerView.smoothScrollBy(0, -1);
                recyclerView.setCheckForMp4(false); // true by default
                //recyclerView.setPlayOnlyFirstVideo(true); // false by default


            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        recyclerView.stopVideos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.playAvailableVideos(0);
    }
    @Override
    public void onClick(View view) {

    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            // CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            //CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "StoreViewActivity");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
