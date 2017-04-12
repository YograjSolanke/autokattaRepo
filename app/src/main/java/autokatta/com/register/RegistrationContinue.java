package autokatta.com.register;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.other.ImagePicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationContinue extends AppCompatActivity {

    FloatingActionButton mClicked;
    ImageView mProfilePic;
    EditText mAboutUs, mWebSite;
    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter
    private static final int CAMERA_REQUEST = 1888;
    String picturePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_continue);

        mClicked = (FloatingActionButton) findViewById(R.id.click);
        mProfilePic = (ImageView) findViewById(R.id.user_image);
        mAboutUs = (EditText) findViewById(R.id.about_us);
        mWebSite = (EditText) findViewById(R.id.website);

        mClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickImage(v);
            }
        });
    }

    public void onPickImage(View view) {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_ID:
                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                // TODO use bitmap
                mProfilePic.setImageBitmap(bitmap);
                getImageUri(getApplicationContext(), bitmap);
                Log.i("data", "->" + data);
                Log.i("bitmap", "->" + bitmap);
                Log.i("picturePath", "->" + picturePath);
                uploadImage();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
        /*if(resultCode != RESULT_CANCELED){
            if (requestCode == PICK_IMAGE_ID) {
                if (requestCode == CAMERA_REQUEST) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    mProfilePic.setImageBitmap(photo);
                }
                if (data.getExtras() == null){
                    Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                    mProfilePic.setImageBitmap(bitmap);
                }else{
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    mProfilePic.setImageBitmap(photo);
                }
            }
        }*/
    }

    private void uploadImage() {
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

    //Code for getting uri from bitmap image only if image is set in ImageView
    public Uri getImageUri(Context inContext, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        picturePath = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(picturePath);
    }
}
