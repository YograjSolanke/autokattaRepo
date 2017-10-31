package autokatta.com.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetMediaResponse;
import retrofit2.Response;

public class ImagesViewActivity extends AppCompatActivity implements RequestNotifier {

    GridView androidGridView;
    List<GetMediaResponse.Success.Image> imagesList = new ArrayList<GetMediaResponse.Success.Image>();
    int mGroupId, mStoreId;
    String mBundleContact;
    private ProgressDialog pDialog;
    ApiCall mApiCall;
    TextView mNoData;

    /*Integer[] imageIDs = {
            R.drawable.exchangeimage, R.drawable.autokatta_splash, R.drawable.autoshare,
            R.drawable.common_google_signin_btn_icon_dark, R.drawable.ic_camera_white, R.drawable.workers,
            R.drawable.ic_cart_plus, R.drawable.pricelogo, R.drawable.profile
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_view);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Images");

        mNoData = (TextView) findViewById(R.id.no_category);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        mApiCall = new ApiCall(ImagesViewActivity.this, this);

        androidGridView = (GridView) findViewById(R.id.gridview_android_example);

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
                    getGroupImages(mGroupId);
                } else if (mStoreId != 0) {
                    getStoreImages(mStoreId);
                } else if (!mBundleContact.equals("")) {
                    getMyImages(mBundleContact);
                }

            }
        });

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                Toast.makeText(getBaseContext(), "Grid Item " + (position + 1) + " Selected", Toast.LENGTH_LONG).show();
            }
        });

    }

    /*API call to get Group Images*/
    private void getGroupImages(int mGroupId) {
        pDialog.show();
        mApiCall.getGroupMedia(mGroupId);
    }

    /*API call to get Store Images*/
    private void getStoreImages(int mStoreId) {
        pDialog.show();
        mApiCall.getStoreMedia(mStoreId);
    }

    /*API call to get User Images*/
    private void getMyImages(String mBundleContact) {
        pDialog.show();
        mApiCall.getContactMedia(mBundleContact);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {

                GetMediaResponse mediaResponse = (GetMediaResponse) response.body();
                if (!mediaResponse.getSuccess().getImage().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    if (pDialog.isShowing()) {
                        pDialog.dismiss();
                    }
                    imagesList.clear();

                    for (GetMediaResponse.Success.Image sImage : mediaResponse.getSuccess().getImage()) {
                        sImage.setLiveStatusID(sImage.getLiveStatusID());
                        sImage.setStatus(sImage.getStatus());
                        sImage.setUserContact(sImage.getUserContact());
                        sImage.setInterest(sImage.getInterest());
                        sImage.setType(sImage.getType());
                        sImage.setImage(sImage.getImage());
                        sImage.setVideo(sImage.getVideo());
                        sImage.setDateTime(sImage.getDateTime());
                        imagesList.add(sImage);
                    }

                    ImageAdapterGridView adapter = new ImageAdapterGridView(ImagesViewActivity.this, imagesList);
                    androidGridView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else {
                    if (pDialog.isShowing()) {
                        pDialog.dismiss();
                    }
                    mNoData.setVisibility(View.VISIBLE);
                    CustomToast.customToast(getApplicationContext(), getString(R.string.no_data));
                }

            } else {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                mNoData.setVisibility(View.VISIBLE);
                CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
            }
        } else {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            mNoData.setVisibility(View.VISIBLE);
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        }

    }

    @Override
    public void notifyError(Throwable error) {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        mNoData.setVisibility(View.VISIBLE);

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
                    , "ImagesViewActivity");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

    }

    public class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;
        List<GetMediaResponse.Success.Image> imagesList = new ArrayList<GetMediaResponse.Success.Image>();

        ImageAdapterGridView(Context c, List<GetMediaResponse.Success.Image> imagesLists) {
            mContext = c;
            imagesList = imagesLists;
        }

        public int getCount() {
            return imagesList.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView mImageView;

            if (convertView == null) {
                mImageView = new ImageView(mContext);
                mImageView.setLayoutParams(new GridView.LayoutParams(130, 130));
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageView.setPadding(16, 16, 16, 16);
            } else {
                mImageView = (ImageView) convertView;
            }
            //mImageView.setImageResource(Integer.parseInt(imagesList.get(position).getImage()));
            Glide.with(mContext)
                    .load(imagesList.get(position).getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logo48x48)
                    .into(mImageView);

            return mImageView;

            /*View gridViewAndroid;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                gridViewAndroid = new View(mContext);
                if (inflater != null) {
                    gridViewAndroid = inflater.inflate(R.layout.gridview_layout, null);
                }
                TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
                ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
                textViewAndroid.setText(gridViewString[i]);
                imageViewAndroid.setImageResource(gridViewImageId[i]);
            } else {
                gridViewAndroid = (View) convertView;
            }
            return gridViewAndroid;*/
        }
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
