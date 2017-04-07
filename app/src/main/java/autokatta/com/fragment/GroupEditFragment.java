package autokatta.com.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.ByteArrayOutputStream;
import java.io.File;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.GenericFunctions;
import autokatta.com.interfaces.ImageUpload;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
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

import static autokatta.com.R.id.group_image;

/**
 * Created by ak-005 on 3/4/17.
 */

public class GroupEditFragment extends android.support.v4.app.Fragment implements RequestNotifier {

    ImageView mGroup_image;
    EditText group_name;
    Button BtnUpdateGroup;
    ApiCall mApiCall;
    String group_name_update;
    String bundle_id;
    ImageUpload mImageUpload;

    String picturePath = "";
    Bitmap bitmap;
    String userSelected = "";

    String result = null;
    String lastWord = "", localImage;
    String bundle_image;
    FragmentActivity ctx;

    @Override
    public void onAttach(Context activity) {
        ctx = (FragmentActivity) activity;

        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_edit, container, false);
        group_name = (EditText) view.findViewById(R.id.group_name);
        mGroup_image = (ImageView) view.findViewById(group_image);
        BtnUpdateGroup = (Button) view.findViewById(R.id.BtnUpdateGroup);
        mApiCall = new ApiCall(ctx, this);
        Bundle bundle = getArguments();
        //get the values out by key
        bundle_id = bundle.getString("bundle_id");
        String bundle_name = bundle.getString("bundle_name");
        bundle_image = bundle.getString("bundle_image");

        bundle_image = bundle_image.replaceAll(" ", "%20");

        group_name.setText(bundle_name);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Change base URL to your upload server URL.
        mImageUpload = new Retrofit.Builder().baseUrl(getString(R.string.base_url)).client(client).build().create(ImageUpload.class);

        try {

            if (bundle_image.equals("") || bundle_image.equalsIgnoreCase(null) || bundle_image.equalsIgnoreCase("null")) {

                mGroup_image.setBackgroundResource(R.drawable.profile);

            }
            if (!bundle_image.equals("") || !bundle_image.equalsIgnoreCase(null) || !bundle_image.equalsIgnoreCase("null")) {
                try {
                    Glide.with(ctx)
                            .load("http://autokatta.com/mobile/profile_profile_pics/"+bundle_image)
                            .bitmapTransform(new CropCircleTransformation(ctx))
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mGroup_image);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ctx, "Error uploading image", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mGroup_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        BtnUpdateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                group_name_update = group_name.getText().toString();
                if (group_name_update.equals("")) {
                    Snackbar.make(v, "Please provide group name", Snackbar.LENGTH_SHORT).show();
                } else {
                    if (lastWord != "")
                        mApiCall.editGroup(group_name_update, bundle_id, lastWord);
                    else
                        mApiCall.editGroup(group_name_update, bundle_id, bundle_image);
                }
            }
        });
        return view;
    }



    private void selectImage() {
        Log.i("In select image","");

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
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
                mGroup_image.setImageBitmap(bitmap);
                getImageUri(ctx, bitmap);
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

                Cursor cursor = ctx.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();

                GenericFunctions obj=new GenericFunctions();
                Bitmap rotatedBitmap = obj.decodeFile(picturePath);

                mGroup_image.setImageBitmap(rotatedBitmap);
                //groupimg.setImageBitmap(bitmap);
                System.out.println(picturePath);
                lastWord = picturePath.substring(picturePath.lastIndexOf("/")+1);
                System.out.println("lastword gallery->"+lastWord);
                upload(picturePath);
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
            retrofit2.Call<okhttp3.ResponseBody> req = mImageUpload.postGroupImage(body, name);
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    CustomToast.customToast(ctx,"Image Upladed");
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

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        Log.i("SStttrrrr","str"+str);
        if (str!="")
{
    CustomToast.customToast(ctx,"Group Updated Successfully");
    MyGroupsFragment frag = new MyGroupsFragment();
    FragmentManager fragmentManager = ctx.getSupportFragmentManager();
    FragmentTransaction mTransaction = fragmentManager.beginTransaction();
    mTransaction.replace(R.id.group_container, frag).commit();

}else
if (str.equals("successsuccess1group_profile_pics/"+str))
{
    try {
        Glide.with(ctx)
                .load("http://autokatta.com/mobile/profile_profile_pics/" + str)
                .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                .into(mGroup_image);
    } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(ctx, "Error uploading image", Toast.LENGTH_LONG).show();
    }
}
    }
    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.group_container);

        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }

    }
    @Override
    public void onResume() {

        super.onResume();

        group_name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    group_name.clearFocus();
                }
                return false;
            }
        });

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    onBackPressed();
                    return true;

                }

                return false;
            }
        });

    }


}
