package autokatta.com.view;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Random;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment_profile.About;
import autokatta.com.fragment_profile.AboutStore;
import autokatta.com.fragment_profile.Follow;
import autokatta.com.fragment_profile.Groups;
import autokatta.com.fragment_profile.Modules;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
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
    TextView mUserName, mWorkedAt, mAbout, mGroup, mStore, mModule, mFollow, mMyVideo, mImages, mBlog, mPost;
    Bundle mUserProfileBundle;
    CollapsingToolbarLayout collapsingToolbar;
    String mLoginContact;
    FloatingActionButton mfab_edit, mfab_done;
    com.github.clans.fab.FloatingActionButton addVehicle, mCreateStore, mCreateGroup;
    Bitmap bitmap;
    String lastWord = "";
    String mediaPath = "";
    Uri selectedImage = null;
    Bitmap bitmapRotate;
    String fname;
    File file;
    ViewPager viewPager;
    String dp;
    String  updatedUsername;
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
        appBar = (AppBarLayout) findViewById(R.id.appbar);
       /* mfab_edit = (FloatingActionButton) findViewById(R.id.fab_edit_prof);
        mfab_done = (FloatingActionButton) findViewById(R.id.fab_edit_done);*/
        //addVehicle = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.add_vehicle);
        /*mCreateStore = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.create_store);
        mCreateGroup = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.create_group);*/
//        mfab_done.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

//        mCreateGroup.setOnClickListener(this);
        // mCreateStore.setOnClickListener(this);
//        addVehicle.setOnClickListener(this);


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
            //collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            //collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
            mProfilePicture = (ImageView) findViewById(R.id.user_image);
            mUserName = (TextView) findViewById(R.id.user_name);
            mWorkedAt = (TextView) findViewById(R.id.worked_at);
            mAbout = (TextView) findViewById(R.id.about);
            mGroup = (TextView) findViewById(R.id.group);
            mStore = (TextView) findViewById(R.id.store);
            mModule = (TextView) findViewById(R.id.modules);
            mFollow = (TextView) findViewById(R.id.follow);
            mMyVideo = (TextView) findViewById(R.id.my_video);
            mImages = (TextView) findViewById(R.id.images);
            mBlog = (TextView) findViewById(R.id.blog);
            mPost = (TextView) findViewById(R.id.post);
            //mProfilePicture.setEnabled(false);
            mProfilePicture.setOnClickListener(this);
            //viewPager = (ViewPager) findViewById(R.id.user_profile_viewpager);
            /*if (viewPager != null) {
                setupViewPager(viewPager);
            }*/

            /*TabLayout tabLayout = (TabLayout) findViewById(R.id.user_profile_tabs);
            tabLayout.setupWithViewPager(viewPager);*/

            /*int tab_position = tabLayout.getSelectedTabPosition();
            if (tab_position == 0) {
                addVehicle.hide(true);
                mCreateStore.hide(true);
                mCreateGroup.hide(true);
            }

            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                    animateFab(tab.getPosition());
                    Log.i("Position", "->" + tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*EDIT USER NAME AND PROFILE PIC*/
        /*mfab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                *//*Snackbar.make(view, "Edit enable", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mProfilePicture.setEnabled(true);
                mfab_done.setVisibility(View.VISIBLE);
                mfab_edit.setVisibility(View.GONE);*//*
                String dp_path = getString(R.string.base_image_url) + dp;
                LayoutInflater layoutInflater = LayoutInflater.from(UserProfile.this);
                View mViewDialogOtp = layoutInflater.inflate(R.layout.custom_alert_my_profile_edit, null);
                img = (ImageView) mViewDialogOtp.findViewById(R.id.img);
                final EditText name = (EditText) mViewDialogOtp.findViewById(R.id.editPersonName);
                name.setText(mUserName);
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(UserProfile.this);
                builder1.setTitle("EDIT PROFILE");
                builder1.setIcon(R.drawable.hdlogo);
                builder1.setView(mViewDialogOtp);

                if (dp==null || dp=="null") {
                    img.setBackgroundResource(R.drawable.hdlogo);

                } else {
                    Glide.with(UserProfile.this)
                            .load(dp_path)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(img);
                }
                img.setOnClickListener(new OnClickListener() {
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
                                    *//*Snackbar.make(view, "Please Enter Your Name", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();*//*
                                } else {
                                    updateProfile();
                                    uploadImage(mediaPath);
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

            }
        });*/
       /* mProfilePicture.setOnClickListener(new View.OnClickListener() {

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
            }
        });
        mfab_done.setVisibility(View.GONE);
        mfab_edit.setVisibility(View.VISIBLE);*/
    }

    /*private void animateFab(int position) {
        switch (position) {
            case 0:
                addVehicle.hide(true);
                mCreateStore.hide(true);
                mCreateGroup.hide(true);
                break;

            case 1:
                mCreateGroup.show(true);
                addVehicle.hide(true);
                mCreateStore.hide(true);
                break;

            case 2:
                mCreateStore.show(true);
                addVehicle.hide(true);
                mCreateGroup.hide(true);
                break;

            *//*case 3:
                addVehicle.hide(true);
                mCreateStore.hide(true);
                mCreateGroup.hide(true);
                break;

            case 4:
                addVehicle.hide(true);
                mCreateStore.hide(true);
                mCreateGroup.hide(true);
                break;*//*

          *//*  case 3:
                addVehicle.hide(true);
                mCreateStore.hide(true);
                mCreateGroup.hide(true);
                break;*//*

            case 4:
                addVehicle.hide(true);
                mCreateStore.hide(true);
                mCreateGroup.hide(true);
                break;

            case 5:
                addVehicle.show(true);
                mCreateStore.hide(true);
                mCreateGroup.hide(true);
                break;

            default:
                addVehicle.hide(true);
                mCreateStore.hide(true);
                mCreateGroup.hide(true);
                break;
        }
    }*/
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_edit, menu);

        return true;
    }*/

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
        if (lastWord != "") {
            mApiCall.updateUsername(RegID, lastWord, updatedUsername);
        } else
            mApiCall.updateUsername(RegID,dp,updatedUsername);
    }

    /*
    SETUP TABS INTO VIEWPAGER...
     */
    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(new About(), "ABOUT");
        adapter.addFragment(new Groups(), "GROUP");
        adapter.addFragment(new AboutStore(), "STORE");
        //adapter.addFragment(new Event(), "EVENT");
        //adapter.addFragment(new Katta(), "KATTA");
        adapter.addFragment(new Modules(), "MODULE");
        adapter.addFragment(new Follow(), "FOLLOW");
     //   adapter.addFragment(new MyVehicles(), "MY VEHICLES");
        /*adapter.addFragment(new MyVideos(), "MY VIDEO");
        adapter.addFragment(new Blog(), "BLOG");
        adapter.addFragment(new Post(), "POST");
        adapter.addFragment(new Images(), "IMAGES");*/
        viewPager.setAdapter(adapter);
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
                    dp = mProfileAboutResponse.getSuccess().get(0).getProfilePic();
                    mUserName.setText(mProfileAboutResponse.getSuccess().get(0).getUsername());
                    mWorkedAt.setText(mProfileAboutResponse.getSuccess().get(0).getCompanyName());
                    RegID = mProfileAboutResponse.getSuccess().get(0).getRegId();
                    String dp_path = getString(R.string.base_image_url)+ dp;
                    final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);

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
                //hud.dismiss();
                Snackbar.make(mUserParent, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            //hud.dismiss();
            Snackbar.make(mUserParent, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        //hud.dismiss();
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
                getProfileData();
                mProfilePicture.setEnabled(false);
                //collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
                // mfab_done.setVisibility(View.GONE);
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
                img.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
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
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(UserProfile.this, R.anim.pull_in_left, R.anim.push_out_right);
            startActivity(new Intent(getApplicationContext(), AutokattaMainActivity.class), options.toBundle());
            finish();
        } else {
            finish();
            startActivity(new Intent(getApplicationContext(), AutokattaMainActivity.class));
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about:
                break;

            case R.id.group:
                break;

            case R.id.store:
                break;

            case R.id.modules:
                break;

            case R.id.follow:
                break;

            case R.id.my_video:
                break;

            case R.id.images:
                break;

            case R.id.post:
                break;

            case R.id.blog:
                break;
            /*case R.id.create_group:
                ActivityOptions option1 = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right,
                        R.anim.ok_right_to_left);
                Intent intent = new Intent(getApplicationContext(), GroupTabs.class);
                intent.putExtra("ClassName", "Groups");
                startActivity(intent, option1.toBundle());
                break;

            case R.id.create_store:
                Bundle bundle = new Bundle();
                // bundle.putString("store_id", Store_id);
                bundle.putString("className", "AboutStore");
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right,
                        R.anim.ok_right_to_left);
                Intent intents = new Intent(getApplicationContext(), MyStoreListActivity.class);
                intents.putExtras(bundle);
                startActivity(intents, options.toBundle());
                break;*/

            /*case R.id.add_vehicle:
                *//*ActivityOptions option = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right,
                        R.anim.ok_right_to_left);
                Intent i = new Intent(getApplicationContext(), NextRegistrationContinue.class);
                i.putExtra("action", "profile");
                i.putExtra("className", "profile");
                startActivity(i, option.toBundle());*//*
                break;*/

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


    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    @Override
    protected void onResume() {
        super.onResume();
        expandToolbar();
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
}
