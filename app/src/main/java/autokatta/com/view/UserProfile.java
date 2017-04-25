package autokatta.com.view;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import autokatta.com.AutokattaMainActivity;
import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment_profile.About;
import autokatta.com.fragment_profile.AboutStore;
import autokatta.com.fragment_profile.Event;
import autokatta.com.fragment_profile.Follow;
import autokatta.com.fragment_profile.Groups;
import autokatta.com.fragment_profile.Katta;
import autokatta.com.fragment_profile.Modules;
import autokatta.com.fragment_profile.MyVehicles;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ProfileAboutResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity implements RequestNotifier {

    ImageView mProfilePicture;
    Bundle mUserProfileBundle;
    String mUserName;
    CollapsingToolbarLayout collapsingToolbar;
    String mLoginContact;
    FloatingActionButton mfab_edit,mfab_done;
    Bitmap bitmap;
    String lastWord = "";
    String mediaPath = "";
    Uri selectedImage = null;
    Bitmap  bitmapRotate;
    String fname;
    File file;
    String dp;
    String RegID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mfab_edit = (FloatingActionButton) findViewById(R.id.fab_edit_prof);
        mfab_done= (FloatingActionButton) findViewById(R.id.fab_edit_done);
        mfab_done.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mLoginContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");
        mUserProfileBundle = new Bundle();

        try {

             /*
            Get Profile Data
             */
            getProfileData();
            collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            mProfilePicture = (ImageView) findViewById(R.id.user_image);
            mProfilePicture.setEnabled(false);
            ViewPager viewPager = (ViewPager) findViewById(R.id.user_profile_viewpager);
            if (viewPager != null) {
                setupViewPager(viewPager);
            }

            TabLayout tabLayout = (TabLayout) findViewById(R.id.user_profile_tabs);
            tabLayout.setupWithViewPager(viewPager);
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*EDIT USER NAME AND PROFILE PIC*/


        mfab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Edit enable", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mProfilePicture.setEnabled(true);
               mfab_done.setVisibility(View.VISIBLE);
                mfab_edit.setVisibility(View.GONE);

            }
        });
        mProfilePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                onPickImage(v);
            }
        });

        mfab_done.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Edit disable", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
               // mUserName = mUserName.toString();
                updateProfile();
                uploadImage(mediaPath);
                mfab_done.setVisibility(View.GONE);
                mfab_edit.setVisibility(View.VISIBLE);
                 mProfilePicture.setEnabled(false);
                collapsingToolbar.setEnabled(false);
            }
        });
        mfab_done.setVisibility(View.GONE);
        mfab_edit.setVisibility(View.VISIBLE);

    }


    /*
    API Call for get profile data...
     */
    private void getProfileData() {
        ApiCall mApiCall = new ApiCall(UserProfile.this, this);
        mApiCall.profileAbout(mLoginContact, mLoginContact);
    }
 /*
    API Call for get profile data...
     */
 private void updateProfile() {
     ApiCall mApiCall = new ApiCall(UserProfile.this, this);
     if (lastWord!="") {
         mApiCall.updateUsername(mUserName, lastWord, RegID);
     }else
         mApiCall.updateUsername(mUserName, dp,RegID );
     }
    /*
    SETUP TABS INTO VIEWPAGER...
     */
    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(new About(), "ABOUT");
        adapter.addFragment(new Groups(), "GROUP");
        adapter.addFragment(new AboutStore(), "STORE");
        adapter.addFragment(new Event(), "EVENT");
        adapter.addFragment(new Katta(), "KATTA");
        adapter.addFragment(new Modules(), "MODULE");
        adapter.addFragment(new Follow(), "FOLLOW");
        adapter.addFragment(new MyVehicles(), "MY VEHICLES");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                ProfileAboutResponse mProfileAboutResponse = (ProfileAboutResponse) response.body();
                if (!mProfileAboutResponse.getSuccess().isEmpty()) {

                    dp = mProfileAboutResponse.getSuccess().get(0).getProfilePic();
                    mUserName = mProfileAboutResponse.getSuccess().get(0).getUsername();
                    RegID=mProfileAboutResponse.getSuccess().get(0).getRegId();
                    String dp_path = "http://autokatta.com/mobile/profile_profile_pics/" + dp;
                    Glide.with(this)
                            .load(dp_path)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mProfilePicture);
                    collapsingToolbar.setTitle(mUserName);

                } else {

                }
            } else {
                //       Snackbar.make(findViewById(R.id.user_profile), getString(R.string._404), Snackbar.LENGTH_LONG).show();
            }
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        if (!str.equals("")) {
            System.out.println("Imagggggggggggggggggggeeeeeeeeeeeeee"+str);
            if (str.equals("Success_update_profile"))
            {
                CustomToast.customToast(getApplicationContext(), "Profile Updated Successfully");
                getProfileData();
                mProfilePicture.setEnabled(false);
                collapsingToolbar.setEnabled(false);
                mfab_done.setVisibility(View.GONE);
                mfab_edit.setVisibility(View.VISIBLE);
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
                mProfilePicture.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
                ///storage/emulated/0/DCIM/Camera/20170411_124425.jpg
                lastWord = mediaPath.substring(mediaPath.lastIndexOf("/") + 1);
                Log.i("Media", "path" + lastWord);


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
                        mProfilePicture.setImageBitmap(bitmapRotate);

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(UserProfile.this, R.anim.pull_in_left, R.anim.push_out_right);
            startActivity(new Intent(getApplicationContext(), AutokattaMainActivity.class), options.toBundle());
            finish();
        } else {
            finish();
            startActivity(new Intent(getApplicationContext(), AutokattaMainActivity.class));
        }
    }

}
