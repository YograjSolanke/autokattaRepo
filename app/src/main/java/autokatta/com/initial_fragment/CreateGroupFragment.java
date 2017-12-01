package autokatta.com.initial_fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import autokatta.com.groups.GroupContactFragment;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.other.CustomToast;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 20/4/17
 */

public class CreateGroupFragment extends Fragment implements View.OnClickListener, RequestNotifier {
    View mCreateGroup;
    EditText mGroupTitle;
    Button mAddmember;
    String lastWord = "";
    ImageView mGroupImg;
    String mediaPath = "", mContact;
    Uri selectedImage = null;
    Bitmap bitmap, bitmapRotate;
    String fname;
    File file;
    Bundle b = new Bundle();
    ApiCall mApiCall;
    String classname;
    RadioButton rdbPublic, rdbPrivate;

    public CreateGroupFragment() {
        //empty fragment...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCreateGroup = inflater.inflate(R.layout.create_group_fragment, container, false);
        getActivity().setTitle("Create Group");
        mGroupTitle = (EditText) mCreateGroup.findViewById(R.id.group_title);
        mAddmember = (Button) mCreateGroup.findViewById(R.id.BtnAddMember);
        mGroupImg = (ImageView) mCreateGroup.findViewById(R.id.group_profile_pic);
        rdbPublic = (RadioButton) mCreateGroup.findViewById(R.id.radioPublic);
        rdbPrivate = (RadioButton) mCreateGroup.findViewById(R.id.radioPrivate);
        mContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
        mApiCall = new ApiCall(getActivity(), this);
        mGroupImg.setOnClickListener(this);
        mAddmember.setOnClickListener(this);

        mGroupTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mGroupTitle.getText().toString().trim().length() > 0 && mGroupTitle.getText().toString().trim().startsWith(" ")) {

                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return mCreateGroup;
    }

    @Override
    public void onClick(View view) {
        String strGroupPriavcy = "";
        switch (view.getId()) {
            case R.id.BtnAddMember:
                if (mGroupTitle.getText().toString().equalsIgnoreCase("") ||
                        mGroupTitle.getText().toString().startsWith(" ") &&
                                mGroupTitle.getText().toString().endsWith(" ")/*|| mGroupTitle.getText().toString().startsWith(" ")*/) {
                    CustomToast.customToast(getActivity(), "Please provide group name and optional group icon");
                } else {
                    if (rdbPublic.isChecked()) {
                        strGroupPriavcy = "public";
                    } else if (rdbPrivate.isChecked()) {
                        strGroupPriavcy = "private";
                    }
                    mApiCall.createGroups(mGroupTitle.getText().toString(), lastWord, mContact, strGroupPriavcy);
                }
                break;
            case R.id.group_profile_pic:
                onPickImage(view);
                break;
        }
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
              //  mGroupImg.setImageBitmap(BitmapFactory.decodeFile(mediaPath));//This code was used to set image
                Glide.with(CreateGroupFragment.this)
                        .load(mediaPath)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mGroupImg);
                cursor.close();
                lastWord = mediaPath.substring(mediaPath.lastIndexOf("/") + 1);
                Log.i("Media", "path" + lastWord);
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
                        Glide.with(CreateGroupFragment.this)
                                .load(bitmapRotate)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(mGroupImg);
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
            lastWord = mediaPath.substring(mediaPath.lastIndexOf("/") + 1);
            Log.i("image", "path" + lastWord);
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

        ServiceApi getResponse = ApiCall.getRetrofit().create(ServiceApi.class);
        Call<String> call = getResponse.uploaGroupProfilePic(fileToUpload, filename);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("createGroup", "image->" + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.getMessage();
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
            CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "Create Group Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        int id = Integer.parseInt(str);
        if (str != null) {
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putInt("group_id", id).apply();
            if (!mediaPath.equals("")) {
                uploadImage(mediaPath);
            }
            Bundle b1 = getArguments();
            if (b1 != null) {
                classname = b1.getString("classname");
            }
            b.putInt("bundle_GroupId", id);
            b.putString("call", "newGroup");
            mGroupTitle.getText().clear();
            GroupContactFragment fragment2 = new GroupContactFragment();    // Call Another Fragment
            fragment2.setArguments(b);   // send values to another fragment
            if (classname.equalsIgnoreCase("uploadvehicle")) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.vehicle_upload_container, fragment2, "groupContactFragment")
                        .addToBackStack("groupContactFragment")
                        .commit();
            } else {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.group_container, fragment2, "groupContactFragment")
                        .addToBackStack("groupContactFragment")
                        .commit();
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string._404_));
        }
    }
}
