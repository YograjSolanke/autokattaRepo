package autokatta.com.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.flexbox.FlexboxLayout;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.other.CustomToast;
import autokatta.com.other.ImageLoader;
import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import fisk.chipcloud.ChipListener;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageVideoPreviewActivity extends AppCompatActivity implements View.OnClickListener, RequestNotifier {

    String videoPath, imagesPath, statusText;
    EditText mStatusText;
    Dialog mBottomSheetDialog;
    List<String> interestList = new ArrayList<>();
    private ProgressDialog dialog;
    String myContact, updatedImages = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_video_preview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Post Preview");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");

        if (getIntent().getExtras() != null) {
            videoPath = getIntent().getExtras().getString("videoPath", "");
            imagesPath = getIntent().getExtras().getString("imagesPath", "");
            statusText = getIntent().getExtras().getString("statusText", "");
        }

        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        ImageView mClose = (ImageView) findViewById(R.id.close);
        final VideoView mVideoView = (VideoView) findViewById(R.id.VideoView);
        RelativeLayout mVideoRel = (RelativeLayout) findViewById(R.id.relVideo);
        RelativeLayout mImageRel = (RelativeLayout) findViewById(R.id.relImage);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ;
        mStatusText = (EditText) findViewById(R.id.status);
        //final Button play = (Button) findViewById(R.id.play);
        if (!statusText.equals("")) {
            mStatusText.setText(statusText);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        
        /*play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVideoView.start();
                play.setVisibility(View.GONE);
            }
        });*/

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mBottomSheetDialog.dismiss();
            }
        });

        //video code
        if (!videoPath.equals("") && imagesPath.equals("")) {
            mVideoRel.setVisibility(View.VISIBLE);
            mImageRel.setVisibility(View.GONE);
            try {
                // Start the MediaController
                final MediaController mediacontroller = new MediaController(
                        ImageVideoPreviewActivity.this);
                mediacontroller.setAnchorView(mVideoView);
                // set media controller object for a video view
                mVideoView.setMediaController(mediacontroller);
                // Get the URL from String VideoURL
               /* Uri video = Uri.parse(videoPath);
                mVideoView.setVideoURI(video);*/
                mVideoView.setVideoPath(videoPath);
                mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediacontroller.show();
                        mVideoView.start();
                    }
                });

                // mVideoView.start();

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
        }
        //Imagecode
        else {
            mVideoRel.setVisibility(View.GONE);
            mImageRel.setVisibility(View.VISIBLE);
            List<String> ImgData2 = Arrays.asList(imagesPath.split(","));
            List<String> image = new ArrayList<>();
            for (int i = 0; i < ImgData2.size(); i++) {
                image.add(ImgData2.get(i));
            }
            for (int k = 0; k < image.size(); k++) {
                if (updatedImages.equals(""))
                    updatedImages = image.get(k);
                else
                    updatedImages = updatedImages + "," + image.get(k);
            }
            MyPagerAdapter myPagerAdapter = new MyPagerAdapter(ImageVideoPreviewActivity.this, image);
            viewPager.setAdapter(myPagerAdapter);
        }
    }


    private class MyPagerAdapter extends PagerAdapter {

        String[] mStrings;
        ImageLoader imageLoader;

        private MyPagerAdapter(ImageVideoPreviewActivity sliderActivity, List<String> image) {
            mStrings = new String[image.size()];
            mStrings = (String[]) image.toArray(mStrings);
            imageLoader = new ImageLoader(ImageVideoPreviewActivity.this);
        }

        @Override
        public int getCount() {
            return mStrings.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            TextView textView = new TextView(ImageVideoPreviewActivity.this);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(30);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setText(String.valueOf(position));

            ImageView imageView = new ImageView(ImageVideoPreviewActivity.this);

            try {
                Glide.with(ImageVideoPreviewActivity.this)
                        .load(mStrings[position]).override(320, 240)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

            ViewGroup.LayoutParams imageParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(imageParams);

            LinearLayout layout = new LinearLayout(ImageVideoPreviewActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layout.setBackgroundColor(Color.parseColor("#000000"));
            layout.setLayoutParams(layoutParams);
            layout.addView(imageView);

            final int page = position;
           /* layout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),
                            "Page " + page + " clicked",
                            Toast.LENGTH_LONG).show();


                    Bundle b = new Bundle();
                    try {
                        b.putString("path", image.get(page));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    b.putInt("Activity", 1);
                    b.putInt("number", page);

                    ImageEditFragment fragment2 = new ImageEditFragment();
                    fragment2.setArguments(b);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.vehicle_upload_container, fragment2, "imageeditfragment")
                            .addToBackStack("imageeditfragment")
                            .commit();

                }
            });*/
            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.postStatus:
                if (mStatusText.getText().toString().equals("") ||
                        (mStatusText.getText().toString().startsWith(" ") &&
                                mStatusText.getText().toString().endsWith(" "))) {
                    mStatusText.setError("Enter data");
                    mStatusText.requestFocus();
                } else {
                    openDialog();
                }
                break;
        }
    }

    private void openDialog() {
        try {
            View view = getLayoutInflater().inflate(R.layout.activity_add_tags, null);
            mBottomSheetDialog = new Dialog(ImageVideoPreviewActivity.this,
                    R.style.MaterialDialogSheet);
            mBottomSheetDialog.setContentView(view);
            mBottomSheetDialog.setCancelable(true);
            mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    500);
            mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
            mBottomSheetDialog.show();

            FlexboxLayout flexbox = (FlexboxLayout) view.findViewById(R.id.flexbox);
            Button ok = (Button) view.findViewById(R.id.ok);

            ChipCloudConfig config = new ChipCloudConfig()
                    .selectMode(ChipCloud.SelectMode.multi)
                    .checkedChipColor(Color.parseColor("#4169E1"))
                    .checkedTextColor(Color.parseColor("#ffffff"))
                    .uncheckedChipColor(Color.parseColor("#efefef"))
                    .uncheckedTextColor(Color.parseColor("#666666"));

            ChipCloud chipCloud = new ChipCloud(ImageVideoPreviewActivity.this, flexbox, config);
            String[] demoArray = getResources().getStringArray(R.array.demo_array);
            chipCloud.addChips(demoArray);
            chipCloud.deselectIndex(0);

            chipCloud.setListener(new ChipListener() {
                @Override
                public void chipCheckedChange(int index, boolean checked, boolean userClick) {
                    if (userClick && index != 0) {
                        if (checked) {
                            interestList.add(String.valueOf(index));
                        } else {
                            interestList.remove(String.valueOf(index));
                        }
                    }
                }
            });

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String finalInterests = "";
                    for (int k = 0; k < interestList.size(); k++) {
                        if (finalInterests.equals(""))
                            finalInterests = interestList.get(k);
                        else
                            finalInterests = finalInterests + "," + interestList.get(k);
                    }
                    PostData(finalInterests);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void PostData(String finalInterests) {
        dialog.show();
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.PostStatus(myContact, statusText, "", "", finalInterests);
    }

    private void uploadImage(String picturePath) {
        Log.i("PAth", "->" + picturePath);
        List<String> imgList = Arrays.asList(picturePath.split(","));
        for (int i = 0; i < imgList.size(); i++) {

            File file = new File(imgList.get(i));
            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

            ServiceApi getResponse = ApiCall.getRetrofit().create(ServiceApi.class);
            Call<String> call = getResponse.uploadVehiclePic(fileToUpload, filename);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i("uploadVehicle", "imageResponse->" + response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.getStackTraceString(t);
                }
            });
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "Post Status");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (str.equals("success")) {
                CustomToast.customToast(getApplicationContext(), "Status posted successfully");
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            } else
                CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));

        } else
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
            mBottomSheetDialog = null;
        }
    }


}
