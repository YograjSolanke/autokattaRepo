package autokatta.com.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.GenericFunctions;
import autokatta.com.interfaces.ImageUpload;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Part;

public class ContinueRegistration extends AppCompatActivity implements RequestNotifier,View.OnClickListener,ImageUpload {


    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    ImageUpload mImageUpload;

    private GoogleApiClient client;
    public static final String MyloginPREFERENCES = "login";
    EditText about, website;
    ImageView groupimg;
    TextView registrationtext,uploadtxt;
    ArrayList id;
    ApiCall mApiCall;// = new ApiCall(ContinueRegistration.this, this);
    private static int RESULT_LOAD_IMAGE = 1;
    String lastWord = "";

    Button btnadd;
    String imageName = "";
    ListView li;
    Button btnsub;
    Bundle b= new Bundle();
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    String abouttext, websitetext;

    Boolean flag = false;

    String picturePath = "";
    Bitmap bitmap;
    String userSelected = "";
    String RegiId="324";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        registrationtext = (TextView) findViewById(R.id.text_registration);
        uploadtxt= (TextView) findViewById(R.id.upload);
        about = (EditText) findViewById(R.id.editabt);
        website = (EditText) findViewById(R.id.editwebsite);
        groupimg = (ImageView) findViewById(R.id.profile_pic);

        btnadd = (Button) findViewById(R.id.btnadd);
        btnadd.setVisibility(View.GONE);

        btnsub = (Button) findViewById(R.id.btnSub);
        btnsub.setOnClickListener(this);
        uploadtxt.setOnClickListener(this);

        li = (ListView) findViewById(R.id.vehicle_list);


        prefs = getSharedPreferences(MyloginPREFERENCES, Context.MODE_PRIVATE);
        mApiCall = new ApiCall(ContinueRegistration.this, this);
      //  RegiId = prefs.getString("RegID", "");
        System.out.println("Registration id in continue reg i.e about" + RegiId);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Change base URL to your upload server URL.
        mImageUpload = new Retrofit.Builder().baseUrl(getString(R.string.base_url)).client(client).build().create(ImageUpload.class);

        try {

            if (imageName.equals("") || imageName.equalsIgnoreCase(null) || imageName.equalsIgnoreCase("null")) {

                groupimg.setBackgroundResource(R.drawable.profile);

            }
            if (!imageName.equals("") || !imageName.equalsIgnoreCase(null) || !imageName.equalsIgnoreCase("null")) {
                try {
                    Glide.with(getApplicationContext())
                            .load("http://autokatta.com/mobile/profile_profile_pics/" + imageName)
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(groupimg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error uploading image", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        int cnt = li.getCount();
        System.out.println("items in list%%%%%%%%%%%%%%%%%%%%%%%%%%%" + cnt);

        if (cnt == 0) {

        } else {
            li.setVisibility(View.VISIBLE);
        }




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btnSub):

                abouttext = about.getText().toString();
                websitetext = website.getText().toString();


                System.out.println("Lastworddddddddddddddddddddddddddddddd=" + lastWord);

                System.out.println(abouttext);

                if (!websitetext.equalsIgnoreCase("")) {
                    if (!(isValidUrl(websitetext))) {
                        website.setError("Enter valid website");
                    } else {
                        flag = true;
                    }
                } else {
                    flag = true;
                }

                if (flag) {
                    String page="1";
                    mApiCall.updateRegistration("324",page,lastWord,abouttext,websitetext);

                }
                break;

            case (R.id.upload):

                Log.i("In upload","hiiiiiiiiiiii");
                selectImage();
                break;

        }
    }

    public boolean isValidUrl (String txtWebsite)
    {

        Pattern regex = Pattern.compile("^(WWW|www)\\.+[a-zA-Z0-9\\-\\.]+\\.(com|org|net|mil|edu|in|IN|COM|ORG|NET|MIL|EDU)$");
        Matcher matcher = regex.matcher(txtWebsite);
        return matcher.matches();

    }


    @Override
    public void onResume() {
        super.onResume();

        about.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    about.clearFocus();
                }
                return false;
            }
        });
        website.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    about.clearFocus();
                }
                return false;
            }
        });



    }


    private void selectImage() {
        Log.i("In select image","");

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {

                    userSelected="camera";

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);

                } else if (options[item].equals("Choose from Gallery")) {

                    userSelected="gallery";
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });

        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null){
            ////image upload from camera
            if(userSelected=="camera")
            {
                Bundle b = data.getExtras();

                bitmap = (Bitmap) b.get("data");
                groupimg.setImageBitmap(bitmap);
                getImageUri(getApplicationContext(), bitmap);
                System.out.println("rutu----------------" + picturePath);

                lastWord = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                System.out.println("lastword camera->"+lastWord);
                upload(picturePath);
            }
            //Image Upload from gallery
            else if(userSelected=="gallery")
            {
                System.out.println("rutu= userselected in gallery==========:"+userSelected);

                Uri selectedImage =  data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getApplicationContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();

               GenericFunctions obj=new GenericFunctions();
                Bitmap rotatedBitmap = obj.decodeFile(picturePath);

                groupimg.setImageBitmap(rotatedBitmap);
                //groupimg.setImageBitmap(bitmap);
                System.out.println(picturePath);
                lastWord = picturePath.substring(picturePath.lastIndexOf("/")+1);
                System.out.println("lastword gallery->"+lastWord);
              //  upload(picturePath);
            }

        }
    }
    //Code for getting uri from bitmap image only if image is set in ImageView
    public Uri getImageUri(Context inContext, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        picturePath = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(picturePath);
    }

        public void upload(String picturePath) {

        System.out.println("picturePath while upload image:"+picturePath);
        System.out.println("rutu= userselected in upload==========:"+userSelected);

        try {

            // HttpPost httpPost = new HttpPost("http://www.autokatta.com/mobile/upload_profile_profile_pics.php"); // serverupdate_profile.php
            lastWord = picturePath.substring(picturePath.lastIndexOf("/") + 1);
            System.out.println(lastWord);


            File file = new File(picturePath);
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file.getPath());
            MultipartBody.Part body = MultipartBody.Part.createFormData("club_image", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

            retrofit2.Call<okhttp3.ResponseBody> req = mImageUpload.postImage(body, name);
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    CustomToast.customToast(getApplicationContext(),"Image Upladed");
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        } catch (Exception e) {

            Log.e(e.getClass().getName(), e.getMessage(), e);

        }


    }



    @Override
    public void notifySuccess(Response<?> response) {
        if (response!=null){
            if (response.isSuccessful()){
                String abc = (String) response.body();
                Log.i("String----","->"+abc);
            }else {

            }
        }else {

        }

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "Continue Registration");
        }
    }

    @Override
    public void notifyString(String str) {

        if (str != null) {
            if (str.equals("success"))
            {
                Log.i("String----","->"+str);

                upload(picturePath);

               // b.putString("action", "ContinueRegisteration");
               /* Intent i= new Intent(getApplication(), ContinueNextRegistration.class);
                i.putExtra("action", "ContinueRegisteration");
                startActivity(i);
                finish();*/
               /* ContinueNextRegistration fr = new ContinueNextRegistration();
                fr.setArguments(b);
                mFragmentManager = getApplicationContext().getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_all, fr).addToBackStack("continuenextreg").commit();*/
            }

        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }
    public void onBackPressed() {


    }

    @Override
    public Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("name") RequestBody name) {
        return null;
    }
}


