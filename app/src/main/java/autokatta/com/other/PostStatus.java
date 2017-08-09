package autokatta.com.other;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.ProfileAboutResponse;
import retrofit2.Response;

public class PostStatus extends AppCompatActivity implements RequestNotifier {

    TextView mPictureVideo;
    EditText mStatusText;
    int SELECT_GALLERY = 0;
    int SELECT_GALLERY_KITKAT = 1;
    private ApiCall mApiCall;
    private String mLoginContact;
    private ProgressDialog dialog;
    ImageView mProfile_image;
    TextView mProfile_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_status);
        mApiCall = new ApiCall(this, this);
        mLoginContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");

        setTitle("Post Status");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        /*
            Get Profile Data
             */
        getProfileData();


        mStatusText = (EditText) findViewById(R.id.status);
        mProfile_image = (ImageView) findViewById(R.id.profile_image);
        mProfile_name = (TextView) findViewById(R.id.profile_name);
        mPictureVideo = (TextView) findViewById(R.id.picture_video);
        mPictureVideo.setVisibility(View.GONE);     //functionality in 2nd phase
        mPictureVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Images", "Videos", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(PostStatus.this);
                builder.setTitle("Select From...");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Images")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            startActivityForResult(intent, 1);
                        } else if (options[item].equals("Videos")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            startActivityForResult(intent, 1);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                        dialog.dismiss();
                    }
                });
                builder.show();

                //CustomToast.customToast(getApplicationContext(), "Coming soon... please be connected for update..");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.post_status:
                String statusText = mStatusText.getText().toString();
                Log.i("status", statusText);
                if (statusText.equals("") || statusText.startsWith(" ") && statusText.endsWith(" ")) {
                    mStatusText.setError("Enter data");
                    mStatusText.requestFocus();
                } else
                    PostData(statusText);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void PostData(String statusText) {
        dialog.show();
        mApiCall.PostStatus(mLoginContact, statusText, "", "");
    }

    /*
    API Call for get profile data...
     */
    private void getProfileData() {
        ApiCall mApiCall = new ApiCall(PostStatus.this, this);

        dialog.show();
        mApiCall.profileAbout(mLoginContact, mLoginContact);
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (response != null) {
            if (response.isSuccessful()) {
                //hud.dismiss();
                ProfileAboutResponse mProfileAboutResponse = (ProfileAboutResponse) response.body();
                if (!mProfileAboutResponse.getSuccess().isEmpty()) {
                    String dp = mProfileAboutResponse.getSuccess().get(0).getProfilePic();
                    mProfile_name.setText(mProfileAboutResponse.getSuccess().get(0).getUsername());
                    int RegID = mProfileAboutResponse.getSuccess().get(0).getRegId();
                    String dp_path = getApplicationContext().getString(R.string.base_image_url) + dp;

                    if (!dp.equals("")) {
                        Glide.with(this)
                                .load(dp_path)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(mProfile_image);
                    } else {
                        mProfile_image.setBackgroundResource(R.drawable.profile);
                    }
                } else {

                }
            } else {
                //hud.dismiss();
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            }
        } else {
            //hud.dismiss();
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
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
}
