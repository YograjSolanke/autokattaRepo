package autokatta.com.my_store;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import autokatta.com.R;
import autokatta.com.Registration.Multispinner;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.GenericFunctions;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.ImageUpload;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.response.BrandsTagResponse;
import autokatta.com.response.CategoryResponse;
import autokatta.com.response.CreateStoreResponse;
import autokatta.com.response.StoreResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ak-003 on 29/3/17.
 */

public class CreateStoreFragment extends Fragment implements Multispinner.MultiSpinnerListener, View.OnClickListener, View.OnTouchListener, MultiSelectionSpinner.MultiSpinnerListener, RequestNotifier {

    TextView textstore, storetypetext;
    Button btnaddprofile, create, btnaddcover;
    CheckBox rbtstoreproduct, rbtstoreservice, rbtstorevehicle;
    String myContact, callFrom, userSelected = "", picturePath = "", coverpicturePath = "", lastWord = "", coverlastWord = "",
            storetype = "", store_id, preLastWord = "", preCoverLastWord;
    Multispinner brandSpinner;
    MultiSelectionSpinner weekspn;
    MultiAutoCompleteTextView multiautotext, multiautobrand;
    EditText storename, storecontact, storewebsite, opentime, closetime, storeaddress, edtStoreDesc;
    AutoCompleteTextView storelocation;

    Boolean typeproduct = false, typeservice = false, typevehicle = false;
    Bundle bundle = new Bundle();
    private ApiCall mApiCall;
    private GenericFunctions genericFunctions;
    private ImageUpload mImageUpload;

    List<String> brandtagIdList = new ArrayList<>();
    List<String> brandTagsList = new ArrayList<>();
    LinearLayout mLinearautobrand;
    RelativeLayout mRelativeBrand, mParent;

    String mediaPath, mediaPath1;
    Uri selectedImage = null;
    Bitmap bitmap, bitmapRotate;
    List<String> weekdays = new ArrayList<>();
    ConnectionDetector mTestConnection;

    public CreateStoreFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mCreateStore = inflater.inflate(R.layout.fragment_create_store, container, false);

        mTestConnection = new ConnectionDetector(getActivity());
        storename = (EditText) mCreateStore.findViewById(R.id.editstorename);
        storecontact = (EditText) mCreateStore.findViewById(R.id.editstorecontact);
        storelocation = (AutoCompleteTextView) mCreateStore.findViewById(R.id.editstoreloc);
        opentime = (EditText) mCreateStore.findViewById(R.id.editopen);
        closetime = (EditText) mCreateStore.findViewById(R.id.editclose);
        multiautotext = (MultiAutoCompleteTextView) mCreateStore.findViewById(R.id.multiautotext);
        storeaddress = (EditText) mCreateStore.findViewById(R.id.editstoreadd);
        edtStoreDesc = (EditText) mCreateStore.findViewById(R.id.editstoredescription);
        weekspn = (MultiSelectionSpinner) mCreateStore.findViewById(R.id.multiweekdays);
        storewebsite = (EditText) mCreateStore.findViewById(R.id.editstorewebsite);
        textstore = (TextView) mCreateStore.findViewById(R.id.textstore);
        rbtstoreproduct = (CheckBox) mCreateStore.findViewById(R.id.rbtproduct);
        rbtstoreservice = (CheckBox) mCreateStore.findViewById(R.id.rbtservice);
        rbtstorevehicle = (CheckBox) mCreateStore.findViewById(R.id.rbtvehicle);
        create = (Button) mCreateStore.findViewById(R.id.btncreatestore);
        btnaddprofile = (Button) mCreateStore.findViewById(R.id.btnaddphoto);
        btnaddcover = (Button) mCreateStore.findViewById(R.id.btnaddcover);
        storetypetext = (TextView) mCreateStore.findViewById(R.id.textstoretype);
        multiautobrand = (MultiAutoCompleteTextView) mCreateStore.findViewById(R.id.multiautobrand);
        mLinearautobrand = (LinearLayout) mCreateStore.findViewById(R.id.linearautobrand);
        mRelativeBrand = (RelativeLayout) mCreateStore.findViewById(R.id.rell);
        mParent = (RelativeLayout) mCreateStore.findViewById(R.id.relativeparent);
        brandSpinner = (Multispinner) mCreateStore.findViewById(R.id.brandSpinner);

        btnaddprofile.setOnClickListener(this);
        btnaddcover.setOnClickListener(this);
        create.setOnClickListener(this);
        multiautotext.setOnTouchListener(this);
        multiautobrand.setOnTouchListener(this);
        opentime.setOnTouchListener(this);
        closetime.setOnTouchListener(this);

        mApiCall = new ApiCall(getActivity(), this);
        genericFunctions = new GenericFunctions();
        /*
        Image upload to server
         */
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Change base URL to your upload server URL.
        mImageUpload = new Retrofit.Builder().baseUrl(getString(R.string.base_url)).client(client).build().create(ImageUpload.class);

        bundle = getArguments();
        callFrom = bundle.getString("className");
        System.out.println("Call from in Store Fragment" + callFrom);
        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "");
        if (callFrom.equals("StoreViewActivity")) {
            store_id = bundle.getString("store_id");
            create.setText("update");
            textstore.setText("Update Store");
            mApiCall.getStoreData(myContact, store_id);
        }


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    storecontact.setText(myContact);
                    storecontact.setEnabled(false);

                    storelocation.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.simple));
                    weekdays.add("Mon");
                    weekdays.add("Tue");
                    weekdays.add("Wed");
                    weekdays.add("Thu");
                    weekdays.add("Fri");
                    weekdays.add("Sat");
                    weekdays.add("Sun");
                    weekspn.setItems(weekdays, "-Select Working Days-", CreateStoreFragment.this);

                    multiautotext.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                    multiautobrand.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

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
                    rbtstorevehicle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            typevehicle = b;
                        }
                    });


                    List<String> Brands = new ArrayList<>();
                    Brands.add("Honda");
                    Brands.add("Hero");
                    Brands.add("Mahindra");
                    Brands.add("Tata");
                    Brands.add("Bajaj");
                    Brands.add("Volvo");
                    Brands.add("Yamaha");
                    brandSpinner.setItems(Brands, "-Select Brands-", CreateStoreFragment.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return mCreateStore;
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnaddphoto:
                onPickImage(v, "addProfile");
                break;
            case R.id.btnaddcover:
                onPickImage(v, "addCover");
                break;
            case R.id.btncreatestore:
                Boolean flag = false;
                Boolean flagtime = false;
                String address = "", name = "", contact = "", location = "", website = "", storeDescription = "", workdays = "",
                        stropen = "", strclose = "", category = "", finalbrandtags = "", strBrandSpinner = "";
                List<String> resultList;

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
                strBrandSpinner = brandSpinner.getSelectedItem().toString().replaceAll(" ", "");

                List<String> tempbrands = new ArrayList<>();
                String textbrand = multiautobrand.getText().toString();

                if (textbrand.endsWith(","))
                    textbrand = textbrand.substring(0, textbrand.length() - 1);
                textbrand = textbrand.trim();
                if (!textbrand.equals("")) {
                    String[] bparts = textbrand.split(",");
                    for (int o = 0; o < bparts.length; o++) {
                        String brandtagpart = bparts[o].trim();
                        if (!brandtagpart.equals("") && !brandtagpart.equalsIgnoreCase(" "))
                            tempbrands.add(brandtagpart);
                        if (!brandTagsList.contains(brandtagpart) && !brandtagpart.equals("") && !brandtagpart.equalsIgnoreCase(" ")) {
                            try {
                                addOtherBrandTags(brandtagpart);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                for (int n = 0; n < tempbrands.size(); n++) {
                    if (finalbrandtags.equals(""))
                        finalbrandtags = tempbrands.get(n);
                    else
                        finalbrandtags = finalbrandtags + "," + tempbrands.get(n);
                }

                if (rbtstoreproduct.isChecked() && !rbtstoreservice.isChecked() && !rbtstorevehicle.isChecked()) {
                    storetype = "product";
                } else if (rbtstoreservice.isChecked() && !rbtstoreproduct.isChecked() && !rbtstorevehicle.isChecked()) {
                    storetype = "service";
                } else if (rbtstorevehicle.isChecked() && !rbtstoreproduct.isChecked() && !rbtstoreservice.isChecked()) {
                    storetype = "vehicle";
                } else if (rbtstoreproduct.isChecked() && rbtstoreservice.isChecked() && !rbtstorevehicle.isChecked()) {
                    storetype = "product & service";
                } else if (rbtstoreproduct.isChecked() && !rbtstoreservice.isChecked() && rbtstorevehicle.isChecked()) {
                    storetype = "product & vehicle";
                } else if (!rbtstoreproduct.isChecked() && rbtstoreservice.isChecked() && rbtstorevehicle.isChecked()) {
                    storetype = "service & vehicle";
                } else if (rbtstoreproduct.isChecked() && rbtstoreservice.isChecked() && rbtstorevehicle.isChecked()) {
                    storetype = "product,service,vehicle";
                }

                category = category.trim();
                if (category.endsWith(","))
                    category = category.substring(0, category.length() - 1);
                category = category.trim();

                if (!location.isEmpty()) {
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
                    storename.setFocusable(true);
                    storename.requestFocus();
                } else if (!name.matches("[a-zA-Z ]*")) {
                    storename.setError("Enter Valid Name");
                    storename.setFocusable(true);
                    storename.requestFocus();
                } else if (location.equals("")) {
                    storelocation.setError("Enter location");
                    storelocation.setFocusable(true);
                    storelocation.requestFocus();
                } else if (!flag) {
                    storelocation.setError("Please Select Address From Dropdown Only");
                    storelocation.setFocusable(true);
                    storelocation.requestFocus();
                } else if (address.equals("")) {
                    storeaddress.setError("Enter address");
                    storeaddress.setFocusable(true);
                    storeaddress.requestFocus();
                }
                //Validation for website is not valid
                else if (!website.equals("") && !(genericFunctions.isValidUrl(website))) {
                    storewebsite.setError("Enter valid website");
                    storewebsite.setFocusable(true);
                    storewebsite.requestFocus();
                } else if (stropen.equals("")) {
                    opentime.setError("Enter Open Time");
                    opentime.setFocusable(true);
                    opentime.requestFocus();
                } else if (strclose.equals("")) {
                    closetime.setError("Enter Close Time");
                    closetime.setFocusable(true);
                    closetime.requestFocus();
                } else if (opentime.getText().toString().contains("PM") && closetime.getText().toString().contains("AM")) {
                    Snackbar.make(mParent, "Enter Valid Time", Snackbar.LENGTH_SHORT).show();
                } else if (flagtime) {
                    Snackbar.make(mParent, "Close time & open time should not same", Snackbar.LENGTH_SHORT).show();
                } else if (workdays.equals("-Select Working Days-") || workdays.equals("")) {
                    Toast.makeText(getActivity(), "Please select Working Days", Toast.LENGTH_LONG).show();
                    Snackbar.make(mParent, "Select Working Days", Snackbar.LENGTH_SHORT).show();
                } else if (storetype.equalsIgnoreCase("")) {
                    Snackbar.make(mParent, "Select store type", Snackbar.LENGTH_SHORT).show();
                } else if (category.equalsIgnoreCase("")) {
                    Snackbar.make(mParent, "Provide services offered", Snackbar.LENGTH_SHORT).show();
                } else if ((rbtstoreproduct.isChecked() || rbtstoreservice.isChecked()) && finalbrandtags.equals("")) {
                    Snackbar.make(mParent, "Provide brand tags", Snackbar.LENGTH_SHORT).show();
                    multiautobrand.requestFocus();
                } else if (rbtstorevehicle.isChecked() && (strBrandSpinner.equalsIgnoreCase("-SelectBrands-") || strBrandSpinner.isEmpty())) {
                    Snackbar.make(mParent, "Select brands", Snackbar.LENGTH_SHORT).show();
                    brandSpinner.requestFocus();
                } else {
                    if (create.getText().toString().equalsIgnoreCase("create")) {
                        createStore(name, contact, location, website, storetype, lastWord, workdays, stropen, strclose, category, address, coverlastWord, storeDescription
                                , finalbrandtags, strBrandSpinner);
                    } else {
                        if (lastWord.equals(""))
                            lastWord = preLastWord;
                        if (coverlastWord.equals(""))
                            coverlastWord = preCoverLastWord;
                        updateStore(name, store_id, location, website, stropen, strclose, lastWord, category, workdays, storeDescription, storetype, address,
                                coverlastWord, finalbrandtags, strBrandSpinner);
                    }
                }
                break;
        }

    }


    public void onPickImage(View view, String whichButton) {
        getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().putString("imageCallStore", whichButton).apply();
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
            String result = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("imageCallStore", "");
            String fname;
            if (result.equalsIgnoreCase("addProfile")) {
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
                    //mProfilePic.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    cursor.close();
                    ///storage/emulated/0/DCIM/Camera/20170411_124425.jpg
                    lastWord = mediaPath.substring(mediaPath.lastIndexOf("/") + 1);
                    Log.i("Media", "path" + lastWord);
                    uploadImage(mediaPath);

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
                            //mProfilePic.setImageBitmap(bitmapRotate);

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
            } else if (result.equalsIgnoreCase("addCover")) {
                if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath1 = cursor.getString(columnIndex);
                    // Set the Image in ImageView for Previewing the Media
                    //mProfilePic.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    cursor.close();
                    ///storage/emulated/0/DCIM/Camera/20170411_124425.jpg
                    coverlastWord = mediaPath1.substring(mediaPath1.lastIndexOf("/") + 1);
                    Log.i("Media", "path" + coverlastWord);
                    uploadImage(mediaPath1);

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
                            //mProfilePic.setImageBitmap(bitmapRotate);

//                            Saving image to mobile internal memory for sometime
                            String root = getActivity().getFilesDir().toString();
                            File myDir = new File(root + "/androidlift");
                            myDir.mkdirs();

                            Random generator = new Random();
                            int n = 10000;
                            n = generator.nextInt(n);

//                            Give the file name that u want
                            fname = "Autokatta" + n + ".jpg";

                            mediaPath1 = root + "/androidlift/" + fname;
                            File file = new File(myDir, fname);
                            saveFile(bitmapRotate, file);
                        }
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

            String result = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("imageCallStore", "");
            if (result.equalsIgnoreCase("addProfile"))
                lastWord = mediaPath.substring(mediaPath.lastIndexOf("/") + 1);
            else if (result.equalsIgnoreCase("addCover"))
                coverlastWord = mediaPath1.substring(mediaPath1.lastIndexOf("/") + 1);
            uploadImage(mediaPath);
            Log.i("image", "path" + lastWord);
            Log.i("image", "path1" + coverlastWord);
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
        if (mTestConnection.isConnectedToInternet()) {
            Call<String> call = getResponse.uploadStorePic(fileToUpload, filename);
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
            Snackbar snackbar = Snackbar.make(mParent, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
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
            snackbar.show();
        }
    }

    //    In some mobiles image will get rotate so to correting that this code will help us
    private int getImageOrientation() {
        final String[] imageColumns = {MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.ORIENTATION};
        final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
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
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (v.getId()) {

            case R.id.multiautotext:
                getCategory();
                break;

            case R.id.multiautobrand:
                getBrandTags();
                break;

            case R.id.editopen:
                if (action == MotionEvent.ACTION_DOWN) {
                    opentime.setInputType(InputType.TYPE_NULL);
                    opentime.setError(null);
                    new SetMyDateAndTime("time", opentime, getActivity());
                }
                break;

            case R.id.editclose:
                if (action == MotionEvent.ACTION_DOWN) {
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

    private void getBrandTags() {
        String type = "";
        if (typeproduct && !typeservice)
            type = "1";

        else if (typeservice && !typeproduct)
            type = "2";
        else
            type = "1,2";

        mApiCall.getBrandTags(type);
    }

    private void addOtherBrandTags(String brandtagpart) {
        String type = "";
        if (typeproduct && !typeservice)
            type = "1";

        else if (typeservice && !typeproduct)
            type = "2";
        else
            type = "1,2";
        mApiCall.addOtherBrandTags(brandtagpart, type);

    }

    private void createStore(String name, String contact, String location, String website, String storetype, String lastWord,
                             String workdays, String open, String close, String category, String address,
                             String coverlastWord, String storeDescription, String textbrand, String strBrandSpinner) {

        mApiCall.CreateStore(name, contact, location, website, storetype, lastWord, workdays, open, close, category, address, coverlastWord,
                storeDescription, textbrand, strBrandSpinner);
    }

    private void updateStore(String name, String store_id, String location, String website, String stropen, String strclose,
                             String lastWord, String category, String workdays, String storeDescription, String storetype,
                             String address, String coverlastWord, String finalbrandtags, String strBrandSpinner) {

        mApiCall.updateStore(name, store_id, location, website, stropen, strclose, lastWord, category, workdays, storeDescription,
                storetype, address, coverlastWord, finalbrandtags, strBrandSpinner);
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
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getActivity(), R.layout.addproductspinner_color, module);
                        multiautotext.setAdapter(dataadapter);
                    } else
                        Snackbar.make(mParent, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
                }
                /*
                        Response to get Brand tags
                 */
                if (response.body() instanceof BrandsTagResponse) {
                    BrandsTagResponse brandResponse = (BrandsTagResponse) response.body();
                    brandTagsList.clear();
                    brandtagIdList.clear();
                    if (!brandResponse.getSuccess().isEmpty()) {
                        for (BrandsTagResponse.Success message : brandResponse.getSuccess()) {
                            message.setId(message.getId());
                            message.setTag(message.getTag());
                            brandtagIdList.add(message.getId());
                            brandTagsList.add(message.getTag());
                        }
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getActivity(), R.layout.registration_spinner, brandTagsList);
                        multiautobrand.setAdapter(dataadapter);
                    } else
                        Snackbar.make(mParent, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
                }
                 /*
                        Response after creating store
                 */
                if (response.body() instanceof CreateStoreResponse) {
                    CreateStoreResponse createStoreResponse = (CreateStoreResponse) response.body();
                    if (createStoreResponse.getSuccess() != null) {
                        String id = createStoreResponse.getSuccess().getStoreID().toString();
                        Snackbar.make(mParent, "Store created", Snackbar.LENGTH_SHORT).show();
                        uploadImage(picturePath);
                        uploadImage(coverpicturePath);

                        bundle = new Bundle();
                        bundle.putString("store_id", id);
                        bundle.putString("call", callFrom);

                        AddMoreAdminsForStoreFrag addAdmin = new AddMoreAdminsForStoreFrag();
                        addAdmin.setArguments(bundle);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.myStoreListFrame, addAdmin).addToBackStack("mystorelist").commit();
                    } else
                        Snackbar.make(mParent, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
                }

                if (response.body() instanceof StoreResponse) {
                    StoreResponse storeResponse = (StoreResponse) response.body();
                    if (!storeResponse.getSuccess().isEmpty()) {
                        for (StoreResponse.Success success : storeResponse.getSuccess()) {
                            storename.setText(success.getName());
                            storelocation.setText(success.getLocation());
                            storewebsite.setText(success.getWebsite());
                            weekspn.setItems(weekdays, success.getWorkingDays(), CreateStoreFragment.this);
                            opentime.setText(success.getStoreOpenTime());
                            closetime.setText(success.getStoreCloseTime());
                            storeaddress.setText(success.getAddress());
                            edtStoreDesc.setText(success.getStoreDescription());
                            multiautotext.setText(success.getCategory());
                            preLastWord = success.getStoreImage();
                            preCoverLastWord = success.getCoverImage();
                            String storeType = success.getStoreType();

                            if (storeType.equalsIgnoreCase("product")) {
                                rbtstoreproduct.setChecked(true);
                            } else if (storeType.equalsIgnoreCase("service")) {
                                rbtstoreservice.setChecked(true);
                            } else if (storeType.equalsIgnoreCase("vehicle")) {
                                rbtstorevehicle.setChecked(true);
                            } else if (storeType.equalsIgnoreCase("product & service")) {
                                rbtstoreproduct.setChecked(true);
                                rbtstoreservice.setChecked(true);
                            } else if (storeType.equalsIgnoreCase("product & vehicle")) {
                                rbtstoreproduct.setChecked(true);
                                rbtstorevehicle.setChecked(true);
                            } else if (storeType.equalsIgnoreCase("service & vehicle")) {
                                rbtstoreservice.setChecked(true);
                                rbtstorevehicle.setChecked(true);
                            } else {
                                rbtstoreproduct.setChecked(true);
                                rbtstoreservice.setChecked(true);
                                rbtstorevehicle.setChecked(true);
                            }
                        }
                    } else
                        Snackbar.make(mParent, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(mParent, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(mParent, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Snackbar.make(mParent, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(mParent, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(mParent, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(mParent, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
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
            snackbar.show();
        } else if (error instanceof UnknownHostException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(mParent, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
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
            snackbar.show();
        } else {
            Log.i("Check Class-", "Create Store Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("brand_tag_added")) {
                Snackbar.make(mParent, "No  Brand Tags Added", Snackbar.LENGTH_SHORT).show();
            } else if (str.equals("store_updated")) {
                Snackbar.make(mParent, "Store updated", Snackbar.LENGTH_SHORT).show();
                uploadImage(picturePath);
                uploadImage(coverpicturePath);

                bundle = new Bundle();
                bundle.putString("store_id", store_id);
                bundle.putString("call", callFrom);

                AddMoreAdminsForStoreFrag addAdmin = new AddMoreAdminsForStoreFrag();
                addAdmin.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.myStoreListFrame, addAdmin).addToBackStack("mystorelist").commit();
            }
        } else
            Snackbar.make(mParent, getString(R.string.no_internet), Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onItemsSelected(boolean[] selected) {


    }
}
