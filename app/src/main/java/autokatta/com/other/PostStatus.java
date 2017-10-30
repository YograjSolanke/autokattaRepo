package autokatta.com.other;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
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
import android.widget.TextView;

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
import autokatta.com.view.ImageVideoPreviewActivity;
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
    List<String> interestList = new ArrayList<>();
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
        mImages.clear();
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
            String allimg = "";
            if (requestCode == SELECT_VIDEO) {
                allimgpath = "";
                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(this, selectedImageUri);
                //selectedPath = getPath(selectedImageUri);
                //VideoImagePreview(selectedPath, "");

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

                allimgpath = "";
                selectedPath = "";
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
            }

            Bundle bundle = new Bundle();
            bundle.putString("videoPath", selectedPath);
            bundle.putString("imagesPath", allimgpath);
            bundle.putString("images", allimg);
            bundle.putString("statusText", mStatusText.getText().toString());
            Intent intent = new Intent(this, ImageVideoPreviewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
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
        /*
         String path = "";
         String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            cursor.close();
            return path;
        } else
            return path;*/
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

    private void PostData(String finalInterests) {
        dialog.show();
        mApiCall.PostStatus(mLoginContact, statusText, "", "", finalInterests);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
            mBottomSheetDialog = null;
        }
    }


    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author paulburke
     */
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
