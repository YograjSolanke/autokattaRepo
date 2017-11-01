package autokatta.com.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.VideoAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetMediaResponse;
import retrofit2.Response;

public class VideosViewActivity extends AppCompatActivity implements RequestNotifier {

    ApiCall mApiCall;
    private ProgressDialog pDialog;
    String myContact;
    VideoAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager mLinearLayoutManager;
    List<GetMediaResponse.Success.Video> videosList = new ArrayList<GetMediaResponse.Success.Video>();
    int mGroupId, mStoreId;
    String mBundleContact;
    TextView mNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Store Videos");

        mNoData = (TextView) findViewById(R.id.no_category);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");

        recyclerView = (RecyclerView) findViewById(R.id.rv_home);

        mApiCall = new ApiCall(VideosViewActivity.this, this);
        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null);
        recyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        /*for (int i = 0; i < 5; i++) {
            //videosList.add("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp");
            videosList.add(getString(R.string.base_image_url) + "VID_20171005_185639.mp4");
        }*/

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }

                if (getIntent().getExtras() != null) {
                    mGroupId = getIntent().getExtras().getInt("bundle_GroupId", 0);
                    mStoreId = getIntent().getExtras().getInt("store_id", 0);
                    mBundleContact = getIntent().getExtras().getString("bundle_Contact", "");
                    Log.i("groupId", String.valueOf(mGroupId));
                    Log.i("storeId", String.valueOf(mStoreId));
                }

                if (mGroupId != 0) {
                    getGroupVideos(mGroupId);
                } else if (mStoreId != 0) {
                    getStoreVideos(mStoreId);
                } else if (!mBundleContact.equals("")) {
                    getMyVideos(mBundleContact);
                }
            }
        });
    }

    /*API call to get Group Videos*/
    private void getGroupVideos(int mGroupId) {
        pDialog.show();
        mApiCall.getGroupMedia(mGroupId);
    }

    /*API call to get Store Videos*/
    private void getStoreVideos(int mStoreId) {
        pDialog.show();
        mApiCall.getStoreMedia(mStoreId);
    }

    /*API call to get User Videos*/
    private void getMyVideos(String mBundleContact) {
        pDialog.show();
        mApiCall.getContactMedia(mBundleContact);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {

                GetMediaResponse mediaResponse = (GetMediaResponse) response.body();
                if (!mediaResponse.getSuccess().getVideo().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    if (pDialog.isShowing()) {
                        pDialog.dismiss();
                    }
                    videosList.clear();

                    for (GetMediaResponse.Success.Video sVideo : mediaResponse.getSuccess().getVideo()) {
                        sVideo.setLiveStatusID(sVideo.getLiveStatusID());
                        sVideo.setStatus(sVideo.getStatus());
                        sVideo.setUserContact(sVideo.getUserContact());
                        sVideo.setInterest(sVideo.getInterest());
                        sVideo.setType(sVideo.getType());
                        sVideo.setImage(sVideo.getImage());
                        sVideo.setVideo(sVideo.getVideo());
                        sVideo.setDateTime(sVideo.getDateTime());
                        videosList.add(sVideo);
                    }

                    adapter = new VideoAdapter(VideosViewActivity.this, videosList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    recyclerView.smoothScrollBy(0, 1);
                    recyclerView.smoothScrollBy(0, -1);
//                recyclerView.setCheckForMp4(false); // true by default
                    //recyclerView.setPlayOnlyFirstVideo(true); // false by default

                } else {
                    if (pDialog.isShowing()) {
                        pDialog.dismiss();
                    }
                    mNoData.setVisibility(View.VISIBLE);
                    //CustomToast.customToast(getApplicationContext(), getString(R.string.no_data));
                }

            } else {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                mNoData.setVisibility(View.VISIBLE);
                //CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
            }
        } else {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            //mNoData.setVisibility(View.VISIBLE);
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        }
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
                    , "VideosViewActivity");
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
