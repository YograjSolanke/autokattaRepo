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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.GenericFunctions;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ExchangeMelaCreateResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 23/3/17.
 */

public class CreateExchangeMelafragment extends Fragment implements View.OnClickListener, RequestNotifier, View.OnTouchListener {

    View createExchangeView;
    ImageView picture;
    Button create, btnaddprofile;
    ApiCall apiCall;
    String picturePath = "";
    String lastWord = "";
    String userSelected;
    TextView textevent;
    Bitmap bitmap;
    GenericFunctions validObj = new GenericFunctions();
    EditText eventname, startdate, starttime, enddate, endtime, eventaddress, eventdetails;
    AutoCompleteTextView eventlocation;
    private List<String> resultList = new ArrayList<>();
    String myContact;

    public CreateExchangeMelafragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createExchangeView = inflater.inflate(R.layout.fragment_create_loanmela, container, false);
        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

        eventname = (EditText) createExchangeView.findViewById(R.id.editauctionname);
        startdate = (EditText) createExchangeView.findViewById(R.id.auctionstartdate);
        starttime = (EditText) createExchangeView.findViewById(R.id.auctionstarttime);
        enddate = (EditText) createExchangeView.findViewById(R.id.auctionenddate);
        endtime = (EditText) createExchangeView.findViewById(R.id.auctionendtime);
        eventlocation = (AutoCompleteTextView) createExchangeView.findViewById(R.id.editlocation);
        eventaddress = (EditText) createExchangeView.findViewById(R.id.editaddress);
        eventdetails = (EditText) createExchangeView.findViewById(R.id.editdetails);
        create = (Button) createExchangeView.findViewById(R.id.btncreate);
        textevent = (TextView) createExchangeView.findViewById(R.id.textevent);
        picture = (ImageView) createExchangeView.findViewById(R.id.loanmelaimg);
        btnaddprofile = (Button) createExchangeView.findViewById(R.id.btnaddphoto);
        apiCall = new ApiCall(getActivity(), this);

        btnaddprofile.setOnClickListener(this);
        create.setOnClickListener(this);
        startdate.setOnTouchListener(this);
        starttime.setOnTouchListener(this);
        enddate.setOnTouchListener(this);
        endtime.setOnTouchListener(this);
        eventlocation.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.simple));


        textevent.setText("Create Exchange Mela Event");
        picture.setImageResource(R.drawable.exchangeimage);

        return createExchangeView;
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

                if (!address.isEmpty()) {

                    resultList = GooglePlacesAdapter.getResultList();
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
                    eventname.setError("Enter auction title");
                    Toast.makeText(getActivity(), "Enter auction title", Toast.LENGTH_LONG).show();
//                    auctioname.setFocusable(true);
                } else if (stdate.equals("")) {
//                    startdate.setError("Enter start date");
                    Toast.makeText(getActivity(), "Enter start date", Toast.LENGTH_LONG).show();
                } else if (sttime.equals("")) {
//                    starttime.setError("Enter start time");
                    Toast.makeText(getActivity(), "Enter start time", Toast.LENGTH_LONG).show();
                } else if (eddate.equals("")) {
//                    enddate.setError("Enter end date");
                    Toast.makeText(getActivity(), "Enter end date", Toast.LENGTH_LONG).show();
                } else if (edtime.equals("")) {
//                    endtime.setError("Enter end time");
                    Toast.makeText(getActivity(), "Enter end time", Toast.LENGTH_LONG).show();
                } else if (!validObj.startDateValidatioon(stdate)) {
                    startdate.setError("Enter valid Date");
                } else if (!validObj.startDateEndDateValidation(eddate, stdate)) {
                    enddate.setError("Enter valid Date");
                } else if (stdate.equals(eddate)) {
                    if (!validObj.startTimeEndTimeValidation(sttime, edtime)) {
                        endtime.setError("Enter valid time");
                    }
                } else if (address.equals("")) {
                    eventaddress.setError("Enter address");
                } else if (location.equals("")) {
                    eventlocation.setError("Enter location");
                } else if (!flag) {
                    eventlocation.setError("Please Select Location From Dropdown Only");
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
                    Log.i("Exid", "->" + id);
                    CustomToast.customToast(getActivity(), "Exchange Event Created Successfully");

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
                System.out.println(lastWord);
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
                    //whichclick = "enddate";
                    startdate.setInputType(InputType.TYPE_NULL);
                    startdate.setError(null);
                    new SetMyDateAndTime("date", startdate, getActivity());
                }
                break;
            case (R.id.auctionstarttime):


                if (action == MotionEvent.ACTION_DOWN) {
                    //whichclick = "enddate";
                    starttime.setInputType(InputType.TYPE_NULL);
                    starttime.setError(null);
                    new SetMyDateAndTime("time", starttime, getActivity());
                }
                break;
            case (R.id.auctionenddate):
                break;
            case (R.id.auctionendtime):
                break;

        }

        return false;
    }
}
