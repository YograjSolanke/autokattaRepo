package autokatta.com.events;

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

import java.io.File;
import java.io.FileOutputStream;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.GenericFunctions;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.other.CustomToast;
import autokatta.com.response.SaleMelaCreateResponse;
import autokatta.com.view.MyActiveEventsTabActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 19/4/17.
 */

public class CreateSaleMelaFragment extends Fragment implements RequestNotifier, View.OnClickListener, View.OnTouchListener {
    public CreateSaleMelaFragment() {

    }

    View createSaleView;
    ImageView picture;
    Button create, btnaddprofile;
    ApiCall apiCall;
    String lastWord = "";
    String userSelected;
    TextView textevent;
    GenericFunctions validObj = new GenericFunctions();
    EditText eventname, startdate, starttime, enddate, endtime, eventaddress, eventdetails;
    AutoCompleteTextView eventlocation;
    String myContact;
    String mediaPath = "", startDateTime;
    Uri selectedImage = null;
    Bitmap bitmap, bitmapRotate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createSaleView = inflater.inflate(R.layout.fragment_create_events, container, false);
        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

        eventname = (EditText) createSaleView.findViewById(R.id.editauctionname);
        startdate = (EditText) createSaleView.findViewById(R.id.auctionstartdate);
        starttime = (EditText) createSaleView.findViewById(R.id.auctionstarttime);
        enddate = (EditText) createSaleView.findViewById(R.id.auctionenddate);
        endtime = (EditText) createSaleView.findViewById(R.id.auctionendtime);
        eventlocation = (AutoCompleteTextView) createSaleView.findViewById(R.id.editlocation);
        eventaddress = (EditText) createSaleView.findViewById(R.id.editaddress);
        eventdetails = (EditText) createSaleView.findViewById(R.id.editdetails);
        create = (Button) createSaleView.findViewById(R.id.btncreate);
        textevent = (TextView) createSaleView.findViewById(R.id.textevent);
        picture = (ImageView) createSaleView.findViewById(R.id.loanmelaimg);
        btnaddprofile = (Button) createSaleView.findViewById(R.id.btnaddphoto);
        apiCall = new ApiCall(getActivity(), this);

        btnaddprofile.setOnClickListener(this);
        create.setOnClickListener(this);
        startdate.setOnTouchListener(this);
        starttime.setOnTouchListener(this);
        enddate.setOnTouchListener(this);
        endtime.setOnTouchListener(this);
        eventlocation.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.simple));


        textevent.setText("Create Sale Mela");
        picture.setImageResource(R.mipmap.sale);

        return createSaleView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case (R.id.btnaddphoto):
                onPickImage(view);
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

                //date comparision
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date now = new Date();
                String dateString = sdf.format(now);
                SimpleDateFormat tm = new SimpleDateFormat("hh:mm a");
                String time = tm.format(Calendar.getInstance().getTime());

                System.out.println("current date=" + dateString);
                System.out.println("current time=" + time);


                if (name.equals("")) {
                    eventname.setError("Enter Sale title");
                    eventname.requestFocus();

                } else if (stdate.equals("")) {
                    startdate.setError("Enter start date");
                    //Toast.makeText(getActivity(), "Enter start date", Toast.LENGTH_LONG).show();
                    startdate.requestFocus();
                } else if (sttime.equals("")) {
                    starttime.setError("Enter start time");
                    //Toast.makeText(getActivity(), "Enter start time", Toast.LENGTH_LONG).show();
                    starttime.requestFocus();
                } else if (stdate.equals(dateString) && !validObj.startTimeEndTimeValidation(time, sttime)) {
                    starttime.setError("time is invalid");
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
                } else if (stdate.equals(eddate) && !validObj.startTimeEndTimeValidation(sttime, edtime)) {

                    endtime.setError("Enter valid time");
                    endtime.requestFocus();

                } else if (location.equals("")) {
                    eventlocation.setError("Enter location");
                    eventlocation.requestFocus();
                } else if (!flag) {
                    eventlocation.setError("Please Select Location From Dropdown Only");
                } else if (address.equals("")) {
                    eventaddress.setError("Enter address");
                    eventaddress.requestFocus();
                } else {
                    //startDateTime=stdate+" "+sttime;
                    apiCall.createSaleMela(name, location, address, stdate, sttime, eddate, edtime, lastWord, details, myContact);
                    //uploadImage(mediaPath);
                }


                break;


        }

    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                SaleMelaCreateResponse createResponse = (SaleMelaCreateResponse) response.body();
                if (createResponse.getSuccess() != null) {
                    String id = createResponse.getSuccess().getSaleID().toString();
                    Log.i("SaleMelaId", "->" + id);
                    CustomToast.customToast(getActivity(), "Sale Mela Created Successfully");
                    uploadImage(mediaPath);

                    /*String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                    Log.i("date","current"+date);
                    Log.i("date","event"+startDateTime);*/
                    startActivity(new Intent(getActivity(), MyActiveEventsTabActivity.class));
                    getActivity().finish();

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
            Log.i("Check class", "CreateSaleMela Fragment");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

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
            String fname;

            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                picture.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
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
                        if (Float.valueOf(getImageOrientation()) >= 0) {
                            bitmapRotate = rotateImage(bitmap, Float.valueOf(getImageOrientation()));
                        } else {
                            bitmapRotate = bitmap;
                            bitmap.recycle();
                        }
                        picture.setImageBitmap(bitmapRotate);

//                            Saving image to mobile internal memory for sometime
                        String root = getActivity().getFilesDir().toString();
                        File myDir = new File(root + "/androidlift");
                        myDir.mkdirs();

                        Random generator = new Random();
                        int n = 10000;
                        n = generator.nextInt(n);

//                            Give the file name that u want
                        fname = "Autokatta" + n + ".jpg";

                        mediaPath = root + "/androidlift/" + fname;
                        File file = new File(myDir, fname);
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

            //uploadImage(mediaPath);
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
        Log.i("PAth", "->" + picturePath);

        File file = new File(picturePath);
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        ServiceApi getResponse = ApiCall.getRetrofit().create(ServiceApi.class);
        Call<String> call = getResponse.uploadEventsPic(fileToUpload, filename);
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

}
