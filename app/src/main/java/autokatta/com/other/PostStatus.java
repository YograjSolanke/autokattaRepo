package autokatta.com.other;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.ProfileAboutResponse;
import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import fisk.chipcloud.ChipListener;
import retrofit2.Response;

public class PostStatus extends AppCompatActivity implements RequestNotifier {

    TextView mPictureVideo;
    EditText mStatusText;
    private ApiCall mApiCall;
    private String mLoginContact;
    private ProgressDialog dialog;
    Dialog mBottomSheetDialog;
    ImageView mProfile_image;
    TextView mProfile_name;
    String statusText;
    List<String> lst = new ArrayList<>();
    private static final int SELECT_VIDEO = 3;
    private String selectedPath;
    int REQUEST_CODE_PICKER = 2000;
    List<Image> mImages = new ArrayList<>();
    String allimgpath = "";

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
        if (getIntent() != null) {
            String url = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            mStatusText.setText(url);
            Log.i("URL", "->" + url);
        }
        mProfile_image = (ImageView) findViewById(R.id.profile_image);
        mProfile_name = (TextView) findViewById(R.id.profile_name);
        mPictureVideo = (TextView) findViewById(R.id.picture_video);
        //mPictureVideo.setVisibility(View.GONE);     //functionality in 2nd phase
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
                            start();
                        } else if (options[item].equals("Videos")) {
                            /*Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            startActivityForResult(intent, 1);*/
                            Intent intent = new Intent();
                            intent.setType("video/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);
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

    private void start() {
        ImagePicker.create(this)
                .folderMode(true) // set folder mode (false by default)
                .folderTitle("Folder") // folder selection title
                .imageTitle("Tap to select") // image selection title
                .single() // single mode
                .multi() // multi mode (default mode)
                .limit(10) // max images can be selected (999 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera")   // captured image directory name ("Camera" folder by default)
                .origin((ArrayList<Image>) mImages) // original selected images, used in multi mode
                .start(REQUEST_CODE_PICKER); // start image picker activity with request code
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_VIDEO) {
                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
                VideoImagePreview(selectedPath, "");
            } else if (requestCode == REQUEST_CODE_PICKER && data != null) {
                mImages = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
                List<String> mPath = new ArrayList<>();
                mPath.clear();
                for (int i = 0; i < mImages.size(); i++) {
                    mPath.add(mImages.get(i).getPath());
                }
                List<String> mPath1 = new ArrayList<>();
                String selectImages = "";
                String selectedimg = "";
                String allimg = "";
                allimgpath = "";
                mPath1.clear();
                for (int i = 0; i < mPath.size(); i++) {

                    selectImages = mPath.get(i);
                    String lastWord = selectImages.substring(selectImages.lastIndexOf("/") + 1);
                    mPath1.add(selectImages);
                    //images with full path
                    if (allimgpath.equalsIgnoreCase("")) {
                        allimgpath = "" + selectImages;
                    } else {
                        allimgpath = allimgpath + "," + selectImages;
                    }
                    //only name of images
                    if (selectedimg.equalsIgnoreCase("") && allimg.equalsIgnoreCase("")) {
                        selectedimg = "" + selectImages;
                        allimg = "" + lastWord.replace(" ", "");
                    } else {
                        selectedimg = selectedimg + "," + selectImages;
                        allimg = allimg + "," + lastWord.replace(" ", "");
                    }

                }
                VideoImagePreview("", allimgpath);
            }
        }
    }

    private void VideoImagePreview(String videoPath, String imagesPath) {
        try {
            View view = getLayoutInflater().inflate(R.layout.image_video_view_card, null);
            mBottomSheetDialog = new Dialog(PostStatus.this,
                    R.style.MaterialDialogSheet);
            mBottomSheetDialog.setContentView(view);
            mBottomSheetDialog.setCancelable(true);
            mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    500);
            mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
            mBottomSheetDialog.show();

            //Button ok = (Button) view.findViewById(R.id.ok);
            ImageView mClose = (ImageView) view.findViewById(R.id.close);
            final VideoView mVideoView = (VideoView) view.findViewById(R.id.VideoView);
            RelativeLayout mVideoRel = (RelativeLayout) view.findViewById(R.id.relVideo);
            RelativeLayout mImageRel = (RelativeLayout) view.findViewById(R.id.relImage);
            final Button play = (Button) view.findViewById(R.id.play);


            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mVideoView.start();
                    play.setVisibility(View.GONE);
                }
            });

            mClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();
                }
            });

            //video code
            if (!videoPath.equals("") && imagesPath.equals("")) {
                mVideoRel.setVisibility(View.VISIBLE);
                try {
                    // Start the MediaController
                    MediaController mediacontroller = new MediaController(
                            PostStatus.this);
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

                //Rajjo please code like SelectedImagesFragment line 123,124 and from 154.
            }




            /*ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostData(statusText);
                }
            });*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        String document_id = null, path = null;
        if (cursor != null) {
            cursor.moveToFirst();

            document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();


            cursor = getContentResolver().query(
                    android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            if (cursor != null) {
                cursor.moveToFirst();
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                cursor.close();
            }
        }


        return path;
    }


    private void uploadVideo() {
        class UploadVideo extends AsyncTask<Void, Void, String> {
            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(PostStatus.this, "Uploading File", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
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
                statusText = mStatusText.getText().toString();
                Log.i("status", statusText);
                if (statusText.equals("") || statusText.startsWith(" ") && statusText.endsWith(" ")) {
                    mStatusText.setError("Enter data");
                    mStatusText.requestFocus();
                } else {

                    openDialog();

                }
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
                ProfileAboutResponse mProfileAboutResponse = (ProfileAboutResponse) response.body();
                if (!mProfileAboutResponse.getSuccess().isEmpty()) {
                    String dp = mProfileAboutResponse.getSuccess().get(0).getProfilePic();
                    mProfile_name.setText(mProfileAboutResponse.getSuccess().get(0).getUsername());
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
                }
            } else {
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            }
        } else {
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

    private void openDialog() {
        try {
            View view = getLayoutInflater().inflate(R.layout.activity_add_tags, null);
            mBottomSheetDialog = new Dialog(PostStatus.this,
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

            ChipCloud chipCloud = new ChipCloud(PostStatus.this, flexbox, config);
            String[] demoArray = getResources().getStringArray(R.array.demo_array);
            chipCloud.addChips(demoArray);
            chipCloud.deselectIndex(0);

            chipCloud.setListener(new ChipListener() {
                @Override
                public void chipCheckedChange(int index, boolean checked, boolean userClick) {
                    if (userClick && index != 0) {
                        if (checked) {
                            lst.add(String.valueOf(index));
                        } else {
                            lst.remove(String.valueOf(index));
                        }
                    }
                }
            });

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostData(statusText);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
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
