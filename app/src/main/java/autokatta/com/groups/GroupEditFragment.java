package autokatta.com.groups;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Random;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.initial_fragment.GroupMyJoined;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.other.CustomToast;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static autokatta.com.R.id.group_image;

/**
 * Created by ak-005 on 3/4/17.
 */

public class GroupEditFragment extends Fragment implements RequestNotifier {

    ImageView mGroup_image;
    EditText group_name;
    Button BtnUpdateGroup;
    ApiCall mApiCall;
    int bundle_id;
    Uri selectedImage = null;
    Bitmap bitmapRotate, bitmap;
    File file;
    String bundle_image, bundle_groupPrivacy, lastWord = "",
            group_name_update, mediaPath = "", fname;
    RadioButton rdbPublic, rdbPrivate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_edit, container, false);

        getActivity().setTitle("Edit Group");

        group_name = (EditText) view.findViewById(R.id.group_name);
        mGroup_image = (ImageView) view.findViewById(group_image);
        BtnUpdateGroup = (Button) view.findViewById(R.id.BtnUpdateGroup);
        rdbPublic = (RadioButton) view.findViewById(R.id.radioPublic);
        rdbPrivate = (RadioButton) view.findViewById(R.id.radioPrivate);
        mApiCall = new ApiCall(getActivity(), this);
        Bundle bundle = getArguments();
        //get the values out by key
        bundle_id = bundle.getInt("bundle_GroupId");
        String bundle_name = bundle.getString("bundle_name");
        bundle_image = bundle.getString("bundle_image", "");
        bundle_image = bundle_image.replaceAll(" ", "%20");
        bundle_groupPrivacy = bundle.getString("bundle_groupPrivacy", "");

        if (bundle_groupPrivacy.equalsIgnoreCase("Private")) {
            rdbPublic.setChecked(false);
            rdbPrivate.setChecked(true);
        } else {
            rdbPublic.setChecked(true);
            rdbPrivate.setChecked(false);
        }
        group_name.setText(bundle_name);


        try {
            if (bundle_image.equals("") || bundle_image.equalsIgnoreCase(null) || bundle_image.equalsIgnoreCase("null")) {
                mGroup_image.setBackgroundResource(R.drawable.profile);
            } else {
                try {
                    Glide.with(getActivity())
                            .load(getString(R.string.base_image_url) + bundle_image)
                            .bitmapTransform(new CropSquareTransformation(getActivity()))
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mGroup_image);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (isAdded())
                        CustomToast.customToast(getActivity(), "Error uploading image");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mGroup_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickImage(view);
            }
        });

        BtnUpdateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strGroupPriavcy = "";
                group_name_update = group_name.getText().toString();
                if (group_name_update.equals("") || group_name_update.startsWith(" ") && group_name_update.endsWith(" ")) {
                    CustomToast.customToast(getActivity(), "Please provide group name");
                    //  showMessage(getActivity(), "Please provide group name");
                } else {
                    if (rdbPublic.isChecked()) {
                        strGroupPriavcy = "public";
                    } else if (rdbPrivate.isChecked()) {
                        strGroupPriavcy = "private";
                    }
                    if (!lastWord.equals("")) {
                        mApiCall.editGroup(group_name_update, bundle_id, lastWord, strGroupPriavcy);
                    } else {
                        mApiCall.editGroup(group_name_update, bundle_id, bundle_image, strGroupPriavcy);
                    }

                }
            }
        });
        return view;
    }

    public void onPickImage(View view) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                assert selectedImage != null;
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                mGroup_image.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
                ///storage/emulated/0/DCIM/Camera/20170411_124425.jpg
                lastWord = mediaPath.substring(mediaPath.lastIndexOf("/") + 1);
                Log.i("Media", "path" + lastWord);
                //  uploadImage(mediaPath);

            } else if (requestCode == 101) {
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        selectedImage = data.getData(); // the uri of the image taken
                        if (String.valueOf((Bitmap) data.getExtras().get("data")).equals("null")) {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        } else {
                            bitmap = (Bitmap) data.getExtras().get("data");
                        }
                        if ((float) getImageOrientation() >= 0) {
                            bitmapRotate = rotateImage(bitmap, (float) getImageOrientation());
                        } else {
                            bitmapRotate = bitmap;
                            bitmap.recycle();
                        }
                        mGroup_image.setImageBitmap(bitmapRotate);

//                            Saving image to mobile internal memory for sometime
                        String root = getActivity().getApplicationContext().getFilesDir().toString();
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
            // uploadImage(mediaPath);
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
        Call<String> call = getResponse.uploaGroupProfilePic(fileToUpload, filename);
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
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                imageColumns, null, null, imageOrderBy);

        assert cursor != null;
        if (cursor.moveToFirst()) {
            int orientation = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION));
            cursor.close();
            return orientation;
        } else {
            return 0;
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404_));

        } else if (error instanceof NullPointerException) {
            // CustomToast.customToast(getActivity(),getString(R.string.no_response));

        } else if (error instanceof ClassCastException) {
            //  CustomToast.customToast(getActivity(),getString(R.string.no_response));

        } else if (error instanceof ConnectException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));

        } else if (error instanceof UnknownHostException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));

        } else {
            Log.i("Check Class-"
                    , "editgroupfragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str.equals("Success")) {
            if (isAdded())
                CustomToast.customToast(getActivity(), "Group Updated");

            uploadImage(mediaPath);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.group_container, new GroupMyJoined())
                    .commit();

        } else if (str.equals("ProfileUpdated")) {
            try {
                Glide.with(getActivity())
                        .load(getString(R.string.base_image_url) + str)
                        .bitmapTransform(new CropCircleTransformation(getActivity()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                        .into(mGroup_image);
            } catch (Exception e) {
                e.printStackTrace();
                if (isAdded())
                    CustomToast.customToast(getActivity(), "Error uploading image");
            }
        }
    }

}
