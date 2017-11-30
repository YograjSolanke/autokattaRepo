package autokatta.com.view;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Random;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.my_profile_container.AboutContainer;
import autokatta.com.my_profile_container.FollowContainer;
import autokatta.com.my_profile_container.GroupContainer;
import autokatta.com.my_profile_container.StoreContainer;
import autokatta.com.other.CustomToast;
import autokatta.com.other.FullImageActivity;
import autokatta.com.response.ProfileAboutResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    ImageView mProfilePicture;
    TextView mUserName, mWorkedAt;
    LinearLayout mAbout, mGroup, mStore, mModule, mFollow, mMyVideo, mImages, mBlog, mPost;
    ImageView mEdit;
    Bundle mUserProfileBundle;
    String mLoginContact;
    Bitmap bitmap;
    String lastWord = "";
    String mediaPath = "";
    Uri selectedImage = null;
    Bitmap bitmapRotate;
    String fname;
    File file;
    String dp, names;
    String updatedUsername;
    int RegID;
    ImageView img;
    CoordinatorLayout mUserParent;
    private ProgressDialog dialog;
    AppBarLayout appBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mLoginContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");
        mUserProfileBundle = new Bundle();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");

        try {
             /*
            Get Profile Data
             */
            getProfileData();
            mUserParent = (CoordinatorLayout) findViewById(R.id.main_content);
            mProfilePicture = (ImageView) findViewById(R.id.user_image);
            mUserName = (TextView) findViewById(R.id.user_name);
            mWorkedAt = (TextView) findViewById(R.id.worked_at);
            mAbout = (LinearLayout) findViewById(R.id.about);
            mGroup = (LinearLayout) findViewById(R.id.group);
            mStore = (LinearLayout) findViewById(R.id.store);
            mModule = (LinearLayout) findViewById(R.id.modules);
            mFollow = (LinearLayout) findViewById(R.id.follow);
            mMyVideo = (LinearLayout) findViewById(R.id.my_video);
            mImages = (LinearLayout) findViewById(R.id.images);
            mBlog = (LinearLayout) findViewById(R.id.blog);
            mPost = (LinearLayout) findViewById(R.id.post);
            mEdit = (ImageView) findViewById(R.id.edit);
            mProfilePicture.setOnClickListener(this);
            mAbout.setOnClickListener(this);
            mGroup.setOnClickListener(this);
            mStore.setOnClickListener(this);
            mFollow.setOnClickListener(this);
            mMyVideo.setOnClickListener(this);
            mImages.setOnClickListener(this);
            mBlog.setOnClickListener(this);
            mPost.setOnClickListener(this);
            mEdit.setOnClickListener(this);
            mModule.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    API Call for get profile data...
     */
    private void getProfileData() {
        ApiCall mApiCall = new ApiCall(UserProfile.this, this);
        dialog.show();
        mApiCall.profileAbout(mLoginContact, mLoginContact);
    }

    /*
       API Call for get profile data...
        */
    private void updateProfile() {
        ApiCall mApiCall = new ApiCall(UserProfile.this, this);
        if (!lastWord.equals("")) {
            mApiCall.updateUsername(RegID, lastWord, updatedUsername);
        } else
            mApiCall.updateUsername(RegID, dp, updatedUsername);
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
                    dp = mProfileAboutResponse.getSuccess().get(0).getProfilePic();
                    mUserName.setText(mProfileAboutResponse.getSuccess().get(0).getUsername());
                    setTitle(mProfileAboutResponse.getSuccess().get(0).getUsername());
                    names = mProfileAboutResponse.getSuccess().get(0).getUsername();
                    mWorkedAt.setText(mProfileAboutResponse.getSuccess().get(0).getCompanyName());
                    RegID = mProfileAboutResponse.getSuccess().get(0).getRegId();
                    String dp_path = getString(R.string.base_image_url) + dp;

                    Glide.with(this)
                            .load(dp_path)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    //   progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            //.placeholder(R.drawable.logo48x48)
                            .into(mProfilePicture);
                } else {

                }
            } else {
                Snackbar.make(mUserParent, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(mUserParent, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
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
            Snackbar snackbar = Snackbar.make(mUserParent, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else if (error instanceof UnknownHostException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(mUserParent, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else {
            Log.i("Check Class-"
                    , "UserProfile");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (!str.equals("")) {
            if (str.equals("success_update")) {
                Snackbar.make(mUserParent, "Profile Updated Successfuly", Snackbar.LENGTH_SHORT).show();
                if (!lastWord.equalsIgnoreCase(""))
                    uploadImage(mediaPath);
                getProfileData();
                mProfilePicture.setEnabled(false);
            }
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


    public void onPickImage(View view) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent cameraintent = new Intent(
                            android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraintent, 101);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 0);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                //  mProfilePicture.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
          //      img.setImageBitmap(BitmapFactory.decodeFile(mediaPath));

                Glide.with(UserProfile.this)
                        .load(mediaPath)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(img);

                cursor.close();
                ///storage/emulated/0/DCIM/Camera/20170411_124425.jpg
                lastWord = mediaPath.substring(mediaPath.lastIndexOf("/") + 1);
                Log.i("Media", "path" + lastWord);
                //mediaPath = compressImage(mediaPath);
            } else if (requestCode == 101) {
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        selectedImage = data.getData(); // the uri of the image taken
                        if (String.valueOf((Bitmap) data.getExtras().get("data")).equals("null")) {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        } else {
                            bitmap = (Bitmap) data.getExtras().get("data");
                        }
                        if (Float.valueOf(getImageOrientation()) >= 0) {
                            bitmapRotate = rotateImage(bitmap, Float.valueOf(getImageOrientation()));
                        } else {
                            bitmapRotate = bitmap;
                            bitmap.recycle();
                        }
                        // mProfilePicture.setImageBitmap(bitmapRotate);
                        img.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                        img.setImageBitmap(bitmapRotate);

                        Glide.with(UserProfile.this)
                                .load(bitmapRotate)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(img);


//                            Saving image to mobile internal memory for sometime
                        String root = getApplicationContext().getFilesDir().toString();
                        File myDir = new File(root + "/androidlift");
                        myDir.mkdirs();

                        Random generator = new Random();
                        int n = 10000;
                        n = generator.nextInt(n);
//                            Give the file name that u want
                        fname = "Autokatta" + n + ".jpg";

                        mediaPath = root + "/androidlift/" + fname;
                        file = new File(myDir, fname);
                        saveFile(bitmapRotate, file);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    Saving file to the mobile internal memory
    private void saveFile(Bitmap sourceUri, File destination) {
        if (destination.exists()) destination.delete();
        try {
            FileOutputStream out = new FileOutputStream(destination);
            sourceUri.compress(Bitmap.CompressFormat.JPEG, 60, out);
            out.flush();
            out.close();
            /*if (cd.isConnectingToInternet()) {*/
            lastWord = mediaPath.substring(mediaPath.lastIndexOf("/") + 1);
            //uploadImage(mediaPath);
            Log.i("image", "path" + lastWord);
            //      /data/data/autokatta.com/files/androidlift/Autokatta9460.jpg
            /*} else {
                Toast.makeText(MainActivity.this, "No Internet Connection..", Toast.LENGTH_LONG).show();
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        return retVal;
    }

    private void uploadImage(String picturePath) {
        Log.i("PAth", "->" + picturePath);
        //    /storage/emulated/0/DCIM/Camera/20170411_124425.jpg
        //    /data/data/autokatta.com/files/androidlift/Autokatta9460.jpg
        File file = new File(picturePath);
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        ServiceApi getResponse = ApiCall.getRetrofit().create(ServiceApi.class);
        Call<String> call = getResponse.uploadFile(fileToUpload, filename);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("image", "->" + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    //    In some mobiles image will get rotate so to correting that this code will help us
    private int getImageOrientation() {
        final String[] imageColumns = {MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.ORIENTATION};
        final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                imageColumns, null, null, imageOrderBy);

        if (cursor.moveToFirst()) {
            int orientation = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION));
            System.out.println("orientation===" + orientation);
            cursor.close();
            return orientation;
        } else {
            return 0;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about:
                startActivity(new Intent(getApplicationContext(), AboutContainer.class));
                break;

            case R.id.group:
                startActivity(new Intent(getApplicationContext(), GroupContainer.class));
                break;

            case R.id.store:
                startActivity(new Intent(getApplicationContext(), StoreContainer.class));
                break;

            case R.id.modules:
                CustomToast.customToast(getApplicationContext(), "Coming soon... be connected for update");
                break;

            case R.id.follow:
                startActivity(new Intent(getApplicationContext(), FollowContainer.class));
                break;

            case R.id.my_video:
                Bundle bundle = new Bundle();
                bundle.putString("bundle_Contact", mLoginContact);
                Intent intent = new Intent(getApplicationContext(), VideosViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.images:
                Bundle bundl = new Bundle();
                bundl.putString("bundle_Contact", mLoginContact);
                Intent intent1 = new Intent(getApplicationContext(), ImagesViewActivity.class);
                intent1.putExtras(bundl);
                startActivity(intent1);
                break;

            case R.id.post:
                CustomToast.customToast(getApplicationContext(), "Coming soon... be connected for update");
                break;

            case R.id.blog:
                CustomToast.customToast(getApplicationContext(), "Coming soon... be connected for update");
                break;

            case R.id.edit:
                String dp_path = getString(R.string.base_image_url) + dp;
                LayoutInflater layoutInflater = LayoutInflater.from(UserProfile.this);
                View mViewDialogOtp = layoutInflater.inflate(R.layout.custom_alert_my_profile_edit, null);
                img = (ImageView) mViewDialogOtp.findViewById(R.id.img);
                final EditText name = (EditText) mViewDialogOtp.findViewById(R.id.editPersonName);
                name.setText(names);
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(UserProfile.this);
                builder1.setTitle("EDIT PROFILE");
                builder1.setIcon(R.drawable.logo48x48);
                builder1.setView(mViewDialogOtp);

                if (dp == null || dp == "null" || dp.equalsIgnoreCase("")) {
                    img.setBackgroundResource(R.drawable.logo48x48);
                } else {
                    Glide.with(UserProfile.this)
                            .load(dp_path)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(img);
                }
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onPickImage(view);
                    }
                });

                builder1.setCancelable(false)
                        .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                updatedUsername = name.getText().toString();
                                if (updatedUsername.equals("") || updatedUsername.startsWith(" ") && updatedUsername.endsWith(" ")) {
                                    CustomToast.customToast(getApplicationContext(), "Please Enter Your Name");
                                } else {
                                    updateProfile();
                                    // uploadImage(mediaPath);
                                    dialogInterface.cancel();
                                }
                            }
                        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alertDialogBox = builder1.create();
                alertDialogBox.show();
                break;

            case R.id.user_image:
                String image;
                if (dp.equals(""))
                    image = getString(R.string.base_image_url) + "logo48x48.png";
                else
                    image = getString(R.string.base_image_url) + dp;

                Bundle b = new Bundle();
                b.putString("image", image);
                Intent intentimage = new Intent(UserProfile.this, FullImageActivity.class);
                intentimage.putExtras(b);
                startActivity(intentimage);
                break;
        }
    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfileData();
        // expandToolbar();
    }

    public void expandToolbar() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBar.getLayoutParams();
        final AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if (behavior != null) {
            ValueAnimator valueAnimator = ValueAnimator.ofInt();
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    behavior.setTopAndBottomOffset((Integer) animation.getAnimatedValue());
                    appBar.requestLayout();
                }
            });
            valueAnimator.setIntValues(0, -900);
            valueAnimator.setDuration(400);
            valueAnimator.start();
        }
    }

    /*
    Destroy Activity
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
