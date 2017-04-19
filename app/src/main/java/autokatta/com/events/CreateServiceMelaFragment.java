package autokatta.com.events;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.SocketTimeoutException;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.GenericFunctions;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.ImageUpload;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ExchangeMelaCreateResponse;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 19/4/17.
 */

public class CreateServiceMelaFragment extends Fragment implements RequestNotifier, View.OnClickListener, View.OnTouchListener {
    public CreateServiceMelaFragment() {

    }

    View createServiceView;
    ImageView picture;
    Button create, btnaddprofile;
    ApiCall apiCall;
    String picturePath = "";
    String lastWord = "";
    String userSelected;
    ImageUpload mImageUpload;
    TextView textevent;
    Bitmap bitmap;
    GenericFunctions validObj = new GenericFunctions();
    EditText eventname, startdate, starttime, enddate, endtime, eventaddress, eventdetails;
    AutoCompleteTextView eventlocation;
    String myContact;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createServiceView = inflater.inflate(R.layout.fragment_create_loanmela, container, false);
        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Change base URL to your upload server URL.
        mImageUpload = new Retrofit.Builder().baseUrl(getString(R.string.base_url)).client(client).build().create(ImageUpload.class);


        eventname = (EditText) createServiceView.findViewById(R.id.editauctionname);
        startdate = (EditText) createServiceView.findViewById(R.id.auctionstartdate);
        starttime = (EditText) createServiceView.findViewById(R.id.auctionstarttime);
        enddate = (EditText) createServiceView.findViewById(R.id.auctionenddate);
        endtime = (EditText) createServiceView.findViewById(R.id.auctionendtime);
        eventlocation = (AutoCompleteTextView) createServiceView.findViewById(R.id.editlocation);
        eventaddress = (EditText) createServiceView.findViewById(R.id.editaddress);
        eventdetails = (EditText) createServiceView.findViewById(R.id.editdetails);
        create = (Button) createServiceView.findViewById(R.id.btncreate);
        textevent = (TextView) createServiceView.findViewById(R.id.textevent);
        picture = (ImageView) createServiceView.findViewById(R.id.loanmelaimg);
        btnaddprofile = (Button) createServiceView.findViewById(R.id.btnaddphoto);
        apiCall = new ApiCall(getActivity(), this);

        btnaddprofile.setOnClickListener(this);
        create.setOnClickListener(this);
        startdate.setOnTouchListener(this);
        starttime.setOnTouchListener(this);
        enddate.setOnTouchListener(this);
        endtime.setOnTouchListener(this);
        eventlocation.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.simple));


        textevent.setText("Create Service Mela");
        picture.setImageResource(R.drawable.service);

        return createServiceView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case (R.id.btnaddphoto):
                selectImage();
                break;
            case (R.id.btncreate):


                Boolean flag = false;

                String name = eventname.getText().toString();
                String stdate = startdate.getText().toString();
                String sttime = starttime.getText().toString();
                String eddate = enddate.getText().toString();
                String edtime = endtime.getText().toString();
                String address = eventaddress.getText().toString();
                String details = eventdetails.getText().toString();
                String location = eventlocation.getText().toString();

                if (!location.isEmpty()) {

                    List<String> resultList = GooglePlacesAdapter.getResultList();
                    for (int i = 0; i < resultList.size(); i++) {

                        if (location.equalsIgnoreCase(resultList.get(i))) {
                            flag = true;
                            break;

                        } else {

                            flag = false;
                        }
                    }
                }


                if (name.equals("")) {
                    eventname.setError("Enter Service mela title");
                    eventname.requestFocus();

                } else if (stdate.equals("")) {
                    startdate.setError("Enter start date");
                    //Toast.makeText(getActivity(), "Enter start date", Toast.LENGTH_LONG).show();
                    startdate.requestFocus();
                } else if (sttime.equals("")) {
                    starttime.setError("Enter start time");
                    //Toast.makeText(getActivity(), "Enter start time", Toast.LENGTH_LONG).show();
                    starttime.requestFocus();
                } else if (eddate.equals("")) {
                    enddate.setError("Enter end date");
                    //Toast.makeText(getActivity(), "Enter end date", Toast.LENGTH_LONG).show();
                    enddate.requestFocus();
                } else if (edtime.equals("")) {
                    endtime.setError("Enter end time");
                    //Toast.makeText(getActivity(), "Enter end time", Toast.LENGTH_LONG).show();
                    enddate.requestFocus();
                } else if (!validObj.startDateValidatioon(stdate)) {
                    startdate.setError("Enter valid Date");
                    startdate.requestFocus();
                } else if (!validObj.startDateEndDateValidation(eddate, stdate)) {
                    enddate.setError("Enter valid Date");
                    enddate.requestFocus();
                } else if (stdate.equals(eddate)) {
                    if (!validObj.startTimeEndTimeValidation(sttime, edtime)) {
                        endtime.setError("Enter valid time");
                        endtime.requestFocus();
                    }
                } else if (location.equals("")) {
                    eventlocation.setError("Enter location");
                    eventlocation.requestFocus();
                } else if (!flag) {
                    eventlocation.setError("Please Select Location From Dropdown Only");
                } else if (address.equals("")) {
                    eventaddress.setError("Enter address");
                    eventaddress.requestFocus();
                } else {
                    apiCall.createExchangeMela(name, location, address, stdate, sttime, eddate, edtime, lastWord, details, myContact);

                }


                break;


        }

    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                ExchangeMelaCreateResponse createResponse = (ExchangeMelaCreateResponse) response.body();
                if (createResponse.getSuccess() != null) {
                    String id = createResponse.getSuccess().getExchangeID().toString();
                    Log.i("ServiceMelaId", "->" + id);
                    CustomToast.customToast(getActivity(), "Service Mela Created Successfully");
                    upload(picturePath);

                }
            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {

                    userSelected = "camera";

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent, 1);

                } else if (options[item].equals("Choose from Gallery")) {

                    userSelected = "gallery";
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });

        builder.show();
    }

    //new activity???????????????????????????????????????????///
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            ////image upload from camera
            if (userSelected == "camera") {
                Bundle b = data.getExtras();

                bitmap = (Bitmap) b.get("data");
                picture.setImageBitmap(bitmap);
                getImageUri(getActivity(), bitmap);
                System.out.println("rutu----------------" + picturePath);

                lastWord = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                System.out.println(lastWord);
            }
            //Image Upload from gallery
            else if (userSelected == "gallery") {
                System.out.println("rutu= userselected in gallery==========:" + userSelected);

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();

                GenericFunctions obj = new GenericFunctions();
                Bitmap rotatedBitmap = obj.decodeFile(picturePath);

                picture.setImageBitmap(rotatedBitmap);

                System.out.println(picturePath);
                lastWord = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                System.out.println("lastword=" + lastWord);
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        switch (view.getId()) {

            case (R.id.auctionstartdate):

                if (action == MotionEvent.ACTION_DOWN) {
                    startdate.setInputType(InputType.TYPE_NULL);
                    startdate.setError(null);
                    new SetMyDateAndTime("date", startdate, getActivity());
                }
                break;
            case (R.id.auctionstarttime):


                if (action == MotionEvent.ACTION_DOWN) {
                    starttime.setInputType(InputType.TYPE_NULL);
                    starttime.setError(null);
                    new SetMyDateAndTime("time", starttime, getActivity());
                }
                break;
            case (R.id.auctionenddate):

                if (action == MotionEvent.ACTION_DOWN) {
                    enddate.setInputType(InputType.TYPE_NULL);
                    enddate.setError(null);
                    new SetMyDateAndTime("date", enddate, getActivity());
                }

                break;
            case (R.id.auctionendtime):

                if (action == MotionEvent.ACTION_DOWN) {
                    endtime.setInputType(InputType.TYPE_NULL);
                    endtime.setError(null);
                    new SetMyDateAndTime("time", endtime, getActivity());
                }
                break;

        }

        return false;
    }


    //upload image to server

    public void upload(String picturePath) {

        System.out.println("picturePath while upload image:" + picturePath);
        System.out.println("rutu= userselected in upload==========:" + userSelected);

        try {

            // HttpPost httpPost = new HttpPost("http://www.autokatta.com/mobile/upload_profile_profile_pics.php"); // serverupdate_profile.php
            lastWord = picturePath.substring(picturePath.lastIndexOf("/") + 1);
            System.out.println(lastWord);


            File file = new File(picturePath);
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file.getPath());
            MultipartBody.Part body = MultipartBody.Part.createFormData("club_image", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

            retrofit2.Call<okhttp3.ResponseBody> req = mImageUpload.postLoanAndExchangeImage(body, name);
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    CustomToast.customToast(getActivity(), "Image Upladed");
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
}
