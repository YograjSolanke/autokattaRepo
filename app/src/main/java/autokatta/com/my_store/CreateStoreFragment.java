package autokatta.com.my_store;

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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.Registration.Multispinner;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.GenericFunctions;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.CategoryResponse;
import retrofit2.Response;

/**
 * Created by ak-003 on 29/3/17.
 */

public class CreateStoreFragment extends Fragment implements Multispinner.MultiSpinnerListener, View.OnClickListener, View.OnTouchListener, RequestNotifier {

    View mCreateStore;
    TextView textstore, storetypetext;
    Button btnaddprofile, create, btnaddcover;
    CheckBox rbtstoreproduct, rbtstoreservice;
    String myContact, callFrom, userSelected = "", picturePath = "", coverpicturePath = "", lastWord = "", coverlastWord = "";
    Multispinner weekspn;
    MultiAutoCompleteTextView multiautotext;
    EditText storename, storecontact, storewebsite, opentime, closetime, storeaddress, edtStoreDesc;
    AutoCompleteTextView storelocation;
    //variable for web service
    String name, location, contact, website, storetype = "", workdays, open, close, category, address, storeDescription;
    Boolean typeproduct = false;
    Boolean typeservice = false;
    Bundle bundle = new Bundle();
    ApiCall mApiCall;
    GenericFunctions genericFunctions;

    public CreateStoreFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCreateStore = inflater.inflate(R.layout.fragment_create_store, container, false);

        storename = (EditText) mCreateStore.findViewById(R.id.editstorename);
        storecontact = (EditText) mCreateStore.findViewById(R.id.editstorecontact);
        storelocation = (AutoCompleteTextView) mCreateStore.findViewById(R.id.editstoreloc);
        opentime = (EditText) mCreateStore.findViewById(R.id.editopen);
        closetime = (EditText) mCreateStore.findViewById(R.id.editclose);
        multiautotext = (MultiAutoCompleteTextView) mCreateStore.findViewById(R.id.multiautotext);
        storeaddress = (EditText) mCreateStore.findViewById(R.id.editstoreadd);
        edtStoreDesc = (EditText) mCreateStore.findViewById(R.id.editstoredescription);
        weekspn = (Multispinner) mCreateStore.findViewById(R.id.multiweekdays);
        storewebsite = (EditText) mCreateStore.findViewById(R.id.editstorewebsite);
        textstore = (TextView) mCreateStore.findViewById(R.id.textstore);
        rbtstoreproduct = (CheckBox) mCreateStore.findViewById(R.id.rbtproduct);
        rbtstoreservice = (CheckBox) mCreateStore.findViewById(R.id.rbtservice);
        create = (Button) mCreateStore.findViewById(R.id.btncreatestore);
        btnaddprofile = (Button) mCreateStore.findViewById(R.id.btnaddphoto);
        btnaddcover = (Button) mCreateStore.findViewById(R.id.btnaddcover);
        storetypetext = (TextView) mCreateStore.findViewById(R.id.textstoretype);

        btnaddprofile.setOnClickListener(this);
        btnaddcover.setOnClickListener(this);
        create.setOnClickListener(this);
        multiautotext.setOnTouchListener(this);
        opentime.setOnTouchListener(this);
        closetime.setOnTouchListener(this);

        mApiCall = new ApiCall(getActivity(), this);
        genericFunctions = new GenericFunctions();

        bundle = getArguments();
        callFrom = bundle.getString("call");
        System.out.println("Call from in Store Fragment" + callFrom);

        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("logiContact", "");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
//                    Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Gtek Technology free promo.ttf");
//                    textstore.setTypeface(typeface);

                    storecontact.setText(myContact);
                    storecontact.setEnabled(false);

                    storelocation.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.simple));
                    List<String> weekdays = new ArrayList<>();
                    weekdays.add("Mon");
                    weekdays.add("Tue");
                    weekdays.add("Wed");
                    weekdays.add("Thu");
                    weekdays.add("Fri");
                    weekdays.add("Sat");
                    weekdays.add("Sun");
                    weekspn.setItems(weekdays, "-Select Working Days-", CreateStoreFragment.this);

                    multiautotext.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

                    rbtstoreproduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                            typeproduct = b;
                        }
                    });
                    rbtstoreservice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                            typeservice = b;
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return mCreateStore;
    }

    @Override
    public void onItemsSelected(boolean[] selected) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnaddphoto:
                selectImage("addProfile");
                break;
            case R.id.btnaddcover:
                selectImage("addCover");
                break;
            case R.id.btncreatestore:
                Boolean flag = false;
                Boolean flagtime = false;
                String address = "", name = "", contact = "", location = "", website = "", storeDescription = "", workdays = "",
                        stropen = "", strclose = "", category = "", storetype = "";

                List<String> resultList = new ArrayList<>();

                address = storeaddress.getText().toString();
                name = storename.getText().toString();
                contact = storecontact.getText().toString();
                location = storelocation.getText().toString();
                website = storewebsite.getText().toString();
                storeDescription = edtStoreDesc.getText().toString();
                workdays = weekspn.getSelectedItem().toString();
                stropen = opentime.getText().toString();
                strclose = closetime.getText().toString();
                category = multiautotext.getText().toString();

                if (rbtstoreproduct.isChecked() && !rbtstoreservice.isChecked()) {
                    storetype = "product";
                } else if (rbtstoreservice.isChecked() && !rbtstoreproduct.isChecked()) {

                    storetype = "service";
                } else if (rbtstoreproduct.isChecked() && rbtstoreservice.isChecked()) {
                    storetype = "product & service";
                }


                category = category.trim();
                if (category.endsWith(","))
                    category = category.substring(0, category.length() - 1);
                category = category.trim();


                try {


                    for (int i = 0; i < resultList.size(); i++) {

                        System.out.println("resultlist " + i + "item=" + resultList.get(i));

                        if (location.equalsIgnoreCase(resultList.get(i))) {

                            System.out.println("user selected location===" + location);
                            System.out.println(" user selected google location" + resultList.get(i));
                            flag = true;
                            break;

                        } else {

                            flag = false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //time validation

                if (opentime.getText().toString().contains("AM") && closetime.getText().toString().contains("AM")) {
                    String[] parts = opentime.getText().toString().split(":");

                    String st = parts[0] + ":" + parts[1];

                    String[] parte = closetime.getText().toString().split(":");
                    String end = parte[0] + ":" + parte[1];

                    flagtime = genericFunctions.compareTime(st, end);
                }
                if (opentime.getText().toString().contains("PM") && closetime.getText().toString().contains("PM")) {

                    String[] parts = opentime.getText().toString().split(":");

                    String st = parts[0] + ":" + parts[1];

                    String[] parte = closetime.getText().toString().split(":");
                    String end = parte[0] + ":" + parte[1];
                    flagtime = genericFunctions.compareTime(st, end);
                }
                if (opentime.getText().toString().contains("AM") && closetime.getText().toString().contains("PM")) {
                    flagtime = false;
                }


                if (name.equals("")) {
                    storename.setError("Enter Name");
                } else if (!name.matches("[a-zA-Z ]*")) {
                    storename.setError("Enter Valid Name");
                } else if (location.equals("")) {
                    storelocation.setError("Enter location");
                } else if (!flag) {

                    storelocation.setError("Please Select Address From Dropdown Only");
                } else if (address.equals("")) {
                    storeaddress.setError("Enter address");
                }

                //Validation for website is not valid
                else if (!website.equals("") && !(genericFunctions.isValidUrl(website))) {
                    storewebsite.setError("Enter valid website");
                } else if (open.equals("")) {
                    // opentime.setError("Enter open time");
                    Toast.makeText(getActivity(), "Enter Open Time", Toast.LENGTH_SHORT).show();
                } else if (close.equals("")) {
                    // closetime.setError("Enter close time");
                    Toast.makeText(getActivity(), "Enter Close Time", Toast.LENGTH_SHORT).show();
                } else if (opentime.getText().toString().contains("PM") && closetime.getText().toString().contains("AM")) {
//                    opentime.setError("Wrong Time");
//                    closetime.setError("Wrong Time");
                    Toast.makeText(getActivity(), "Enter Valid Open Time And Close Time", Toast.LENGTH_SHORT).show();
                } else if (flagtime) {
                    Toast.makeText(getActivity(), "Close Time Should Be Graeter Than Open Time", Toast.LENGTH_SHORT).show();
                } else if (workdays.equals("")) {
                    Toast.makeText(getActivity(), "Please select Working Days", Toast.LENGTH_LONG).show();

                } else if (category.equalsIgnoreCase("-select category-") || category.equalsIgnoreCase("")) {

                    Toast.makeText(getActivity(), "Please select category", Toast.LENGTH_LONG).show();

                } else if (storetype.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select store type", Toast.LENGTH_LONG).show();
                } else {

                    /*if(!finaladmins.equalsIgnoreCase(""))
                        contact=contact+","+finaladmins;
                    params.put("store_name", name);
                    params.put("contact_no", contact);
                    params.put("location", location);
                    params.put("website", website);
                    params.put("store_type", storetype);
                    params.put("store_image", lastWord);
                    params.put("workingdays", workdays);
                    params.put("store_open_time", open);
                    params.put("store_close_time", close);
                    params.put("category", category);
                    params.put("address", address);
                    params.put("coverImage", coverlastWord);
                    params.put("storeDescription", storeDescription);*/
                    createStore(name, contact, location, website, storetype, lastWord, workdays, stropen, strclose, category, address, coverlastWord, storeDescription);
                }

        }

    }

    private void selectImage(String whichButton) {

        getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().putString("imageCallStore", whichButton).apply();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap;
        String result = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("imageCallStore", "");
        System.out.println("call from button" + result);
        if (data != null) {

            if (result.equalsIgnoreCase("addProfile")) {
                if (userSelected.equals("camera")) {
                    Bundle b = data.getExtras();

                    bitmap = (Bitmap) b.get("data");

                    getImageUri(getActivity(), bitmap);
                    System.out.println("rutu-------prof---------" + picturePath);

                    lastWord = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                    Log.i("Profile camera:", "->" + lastWord);
                }
                //Image Upload from gallery
                else if (userSelected.equals("gallery")) {
                    System.out.println("rutu= userselected in gallery==========:" + userSelected);

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();

//                    ValidationClass obj = new ValidationClass();
//                    Bitmap rotatedBitmap = obj.decodeFile(picturePath);
//
//                    storeprofile.setImageBitmap(rotatedBitmap);

                    System.out.println(picturePath);
                    lastWord = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                    Log.i("Profile gallery:", "->" + lastWord);
                }
            } else if (result.equalsIgnoreCase("addCover")) {
                if (userSelected == "camera") {
                    Bundle b = data.getExtras();

                    bitmap = (Bitmap) b.get("data");

                    getImageUric(getActivity(), bitmap);
                    System.out.println("rutu-------cover---------" + coverpicturePath);

                    coverlastWord = coverpicturePath.substring(coverpicturePath.lastIndexOf("/") + 1);
                    Log.i("Cover camera:", "->" + lastWord);
                }
                //Image Upload from gallery
                else if (userSelected == "gallery") {
                    System.out.println("rutu= userselected in gallery==========:" + userSelected);
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    coverpicturePath = cursor.getString(columnIndex);
                    cursor.close();

                    /*ValidationClass obj = new ValidationClass();
                    Bitmap rotatedBitmap = obj.decodeFile(coverpicturePath);

                    storecover.setImageBitmap(rotatedBitmap);*/

                    System.out.println(coverpicturePath);
                    coverlastWord = coverpicturePath.substring(coverpicturePath.lastIndexOf("/") + 1);
                    Log.i("Cover gallery:", "->" + lastWord);
                }
            }

        }
    }

    public Uri getImageUri(Context inContext, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        picturePath = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(picturePath);
    }

    public Uri getImageUric(Context inContext, Bitmap bitmapc) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmapc.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        coverpicturePath = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), bitmapc, "Title", null);
        return Uri.parse(coverpicturePath);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (v.getId()) {

            case R.id.multiautotext:
                getCategory();
                break;

            case R.id.editopen:
                if (action == MotionEvent.ACTION_DOWN) {
                    //whichclick = "enddate";
                    opentime.setInputType(InputType.TYPE_NULL);
                    opentime.setError(null);
                    new SetMyDateAndTime("time", opentime, getActivity());
                }
                break;

            case R.id.editclose:
                if (action == MotionEvent.ACTION_DOWN) {
                    //whichclick = "enddate";
                    closetime.setInputType(InputType.TYPE_NULL);
                    closetime.setError(null);
                    new SetMyDateAndTime("time", closetime, getActivity());
                }
                break;
        }
        return false;
    }

    private void getCategory() {
        String type = "";
        if (typeproduct && !typeservice)
            type = "Product";

        else if (typeservice && !typeproduct)
            type = "Service";
        else
            type = "both";

        mApiCall.Categories(type);
    }

    private void createStore(String name, String contact, String location, String website, String storetype, String lastWord, String workdays, String open, String close, String category, String address, String coverlastWord, String storeDescription) {

    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {

                /*
                        Response to get category
                 */
                if (response.body() instanceof CategoryResponse) {
                    CategoryResponse moduleResponse = (CategoryResponse) response.body();
                    final List<String> module = new ArrayList<String>();

                    if (!moduleResponse.getSuccess().isEmpty()) {

                        for (CategoryResponse.Success message : moduleResponse.getSuccess()) {
                            module.add(message.getTitle());

                        }
//                        MODULE = new String[module.size()];
//                        MODULE = (String[]) module.toArray(MODULE);

                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getActivity(), R.layout.addproductspinner_color, module);
                        multiautotext.setAdapter(dataadapter);
                    } else
                        CustomToast.customToast(getActivity(), getString(R.string.no_response));
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
            Log.i("Check Class-", "Create Store Fragment");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

    }
}
