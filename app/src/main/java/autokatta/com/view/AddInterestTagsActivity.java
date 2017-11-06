package autokatta.com.view;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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
import autokatta.com.other.UploadVideos;
import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import fisk.chipcloud.ChipListener;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddInterestTagsActivity extends AppCompatActivity implements RequestNotifier {

    FlexboxLayout flexbox;
    Button ok;
    private ApiCall mApiCall;
    private String mLoginContact;
    private ProgressDialog dialog;
    String videoPath, videoWithoutPath = "", imagesPath, imagesWithoutPath, statusText;
    List<String> interestList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_interest_tags);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mApiCall = new ApiCall(this, this);
        mLoginContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        setTitle("Add Interests");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (getIntent().getExtras() != null) {
            videoPath = getIntent().getExtras().getString("videoPath", "");
            imagesPath = getIntent().getExtras().getString("imagesPath", "");
            statusText = getIntent().getExtras().getString("statusText", "");
            imagesWithoutPath = getIntent().getExtras().getString("images", "");
            videoWithoutPath = getIntent().getExtras().getString("videos", "");
        }

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);


        flexbox = (FlexboxLayout) findViewById(R.id.flexbox);
        ok = (Button) findViewById(R.id.ok);

        ChipCloudConfig config = new ChipCloudConfig()
                .selectMode(ChipCloud.SelectMode.multi)
                .checkedChipColor(Color.parseColor("#4169E1"))
                .checkedTextColor(Color.parseColor("#ffffff"))
                .uncheckedChipColor(Color.parseColor("#efefef"))
                .uncheckedTextColor(Color.parseColor("#666666"));

        ChipCloud chipCloud = new ChipCloud(AddInterestTagsActivity.this, flexbox, config);
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
    }

    private void PostData(String finalInterests) {
        dialog.show();
        mApiCall.PostStatus(mLoginContact, statusText, imagesWithoutPath, videoWithoutPath, finalInterests);
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
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
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
                    , "AddInterestTagsActivity");
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

                if (videoPath.equals("") && !imagesPath.equals("")) {
                    uploadImage(imagesPath);
                    CustomToast.customToast(getApplicationContext(), "Status posted successfully");
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                } else {
                    uploadVideo(videoPath);
                }


            } else
                CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));

        } else
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
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
            Call<String> call = getResponse.uploadServicePic(fileToUpload, filename);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i("uploadStatusImage", "imageResponse->" + response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.getStackTraceString(t);
                }
            });
        }
    }

    private void uploadVideo(final String selectedPath) {
        class UploadVideo extends AsyncTask<Void, Void, String> {
            private ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(AddInterestTagsActivity.this, "Uploading File", "Please wait...", false, false);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
                CustomToast.customToast(getApplicationContext(), "Status posted successfully");
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                //textViewResponse.setText(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
                //textViewResponse.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            protected String doInBackground(Void... params) {
                UploadVideos u = new UploadVideos();
                return u.uploadVideo(selectedPath);
            }
        }
        UploadVideo uv = new UploadVideo();
        uv.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
