package autokatta.com.register;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationContinue extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    FloatingActionButton mClicked;
    ImageView mProfilePic;
    EditText mAboutUs, mWebSite;
    String mediaPath, abouttext, websitetext, lastWord = "", fname;
    Uri selectedImage = null;
    Bitmap bitmap, bitmapRotate;
    File file;
    Button mSubmit;
    Boolean flag = false;
    CoordinatorLayout mCoordinate;
    ConnectionDetector mTestConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_continue);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTestConnection = new ConnectionDetector(RegistrationContinue.this);
                mClicked = (FloatingActionButton) findViewById(R.id.click);
                mProfilePic = (ImageView) findViewById(R.id.user_image);
                mAboutUs = (EditText) findViewById(R.id.about_us);
                mWebSite = (EditText) findViewById(R.id.website);
                mSubmit = (Button) findViewById(R.id.btnSub);
                mCoordinate = (CoordinatorLayout) findViewById(R.id.coordinate);
                mClicked.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onPickImage(v);
                    }
                });
            }
        });
        mSubmit.setOnClickListener(this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                mProfilePic.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
                ///storage/emulated/0/DCIM/Camera/20170411_124425.jpg
                lastWord = mediaPath.substring(mediaPath.lastIndexOf("/") + 1);
                Log.i("Media", "path" + lastWord);
                uploadImage(mediaPath);

            } else if (requestCode == 101) {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        selectedImage = data.getData(); // the uri of the image taken
                        if (String.valueOf((Bitmap) data.getExtras().get("data")).equals("null")) {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        } else {
                            bitmap = (Bitmap) data.getExtras().get("data");
                        }
                        if (Float.valueOf(getImageOrientation()) >= 0) {
                            bitmapRotate = rotateImage(bitmap, Float.valueOf(getImageOrientation()));
                        } else {
                            bitmapRotate = bitmap;
                            bitmap.recycle();
                        }
                        mProfilePic.setImageBitmap(bitmapRotate);

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
            uploadImage(mediaPath);
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
        File file = new File(picturePath);
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        if (mTestConnection.isConnectedToInternet()) {
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
        } else {
           /* Snackbar snackbar = Snackbar.make(mCoordinate, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
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
            snackbar.show();*/
            CustomToast.customToast(getApplicationContext(),getString(R.string.no_internet));
        }
    }

    //    In some mobiles image will get rotate so to correting that this code will help us
    private int getImageOrientation() {
        final String[] imageColumns = {MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.ORIENTATION};
        final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                imageColumns, null, null, imageOrderBy);

        if (cursor.moveToFirst()) {
            int orientation = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION));
            cursor.close();
            return orientation;
        } else {
            return 0;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSub:
                abouttext = mAboutUs.getText().toString();
                websitetext = mWebSite.getText().toString();
                if (!websitetext.equalsIgnoreCase("")) {
                    if (!(isValidUrl(websitetext))) {
                        mWebSite.setError("Enter valid website");
                    } else {
                        flag = true;
                    }
                } else {
                    flag = true;
                }
                if (flag) {
                    if (mTestConnection.isConnectedToInternet()) {
                        ApiCall mApiCall = new ApiCall(RegistrationContinue.this, this);
                        mApiCall.updateRegistration(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                                .getInt("loginregistrationid", Integer.parseInt("")), 1, lastWord, abouttext, websitetext);
                    } else {
                       /* Snackbar snackbar = Snackbar.make(mCoordinate, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
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
                        snackbar.show();*/
                        //   errorMessage(getActivity(), getString(R.string.no_internet));
                        CustomToast.customToast(getApplicationContext(),getString(R.string.no_internet));
                    }
                }
                break;
        }
    }

    public boolean isValidUrl(String txtWebsite) {
        Pattern regex = Pattern.compile("^(WWW|www)\\.+[a-zA-Z0-9\\-\\.]+\\.(com|org|net|mil|edu|in|IN|COM|ORG|NET|MIL|EDU)$");
        Matcher matcher = regex.matcher(txtWebsite);
        return matcher.matches();
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(),getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(),getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(),getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(),getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(),getString(R.string.no_internet));
        }  else {
            Log.i("Check Class-", "Continue Registration");
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("Success")) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent i = new Intent(getApplication(), NextRegistrationContinue.class);
                i.putExtra("action", "ContinueRegistration");
                startActivity(i, options.toBundle());
            }
        } else {
            CustomToast.customToast(getApplicationContext(),getString(R.string.no_response));
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
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(RegistrationContinue.this, R.anim.pull_in_left, R.anim.push_out_right);
            startActivity(new Intent(getApplicationContext(), LoginActivity.class), options.toBundle());
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }*/
    }
}
