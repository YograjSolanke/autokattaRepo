package autokatta.com.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import autokatta.com.my_store.MultiSelectionSpinner;
import autokatta.com.my_store.MultiSelectionSpinnerForBrands;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
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

public class CreateStoreActivity extends AppCompatActivity implements Multispinner.MultiSpinnerListener, View.OnClickListener, View.OnTouchListener, MultiSelectionSpinner.MultiSpinnerListener,
        RequestNotifier, MultiSelectionSpinnerForBrands.MultiSpinnerListener {


    Activity mActivity;
    TextView storetypetext;
    ImageView mCoverImage,mPhoto;
    Button btnaddprofile, create, btnaddcover;
    CheckBox rbtstoreproduct, rbtstoreservice, rbtstorevehicle;
    String myContact, callFrom, lastWord = "", coverlastWord = "",
            storetype = "", preLastWord = "", preCoverLastWord, brandtagpart = "", finalbrandtags = "";
    int store_id;
    MultiSelectionSpinner weekspn;
    MultiAutoCompleteTextView multiautotext, multiautobrand;
    EditText storename, storecontact, storewebsite, opentime, closetime, storeaddress, edtStoreDesc;
    AutoCompleteTextView storelocation;

    Boolean typeproduct = false, typeservice = false, typevehicle = false;
    Bundle bundle = new Bundle();
    private ApiCall mApiCall;
    final List<String> brandTags = new ArrayList<>();
    private GenericFunctions genericFunctions;
    ImageUpload mImageUpload;

    LinearLayout mLinearautobrand;
    RelativeLayout mRelativeBrand, mParent;

    String mediaPath = "", mediaPath1 = "";
    Uri selectedImage = null;
    Bitmap bitmap, bitmapRotate;
    List<String> weekdays = new ArrayList<>();
    ConnectionDetector mTestConnection;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        /*
          Create store in registration and also MY store (create store)
        */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        setTitle("Create Store");
        mTestConnection = new ConnectionDetector(this);
        storename = (EditText) findViewById(R.id.editstorename);
        storecontact = (EditText) findViewById(R.id.editstorecontact);
        storelocation = (AutoCompleteTextView) findViewById(R.id.editstoreloc);
        opentime = (EditText) findViewById(R.id.editopen);
        closetime = (EditText) findViewById(R.id.editclose);
        multiautotext = (MultiAutoCompleteTextView) findViewById(R.id.multiautotext);
        storeaddress = (EditText) findViewById(R.id.editstoreadd);
        edtStoreDesc = (EditText) findViewById(R.id.editstoredescription);
        weekspn = (MultiSelectionSpinner) findViewById(R.id.multiweekdays);
        storewebsite = (EditText) findViewById(R.id.editstorewebsite);
        //textstore = (TextView) mCreateStore.findViewById(R.id.textstore);
        rbtstoreproduct = (CheckBox) findViewById(R.id.rbtproduct);
        rbtstoreservice = (CheckBox) findViewById(R.id.rbtservice);
        rbtstorevehicle = (CheckBox) findViewById(R.id.rbtvehicle);
        create = (Button) findViewById(R.id.btncreatestore);
        btnaddprofile = (Button) findViewById(R.id.btnaddphoto);
        btnaddcover = (Button) findViewById(R.id.btnaddcover);
        storetypetext = (TextView) findViewById(R.id.textstoretype);
        multiautobrand = (MultiAutoCompleteTextView) findViewById(R.id.multiautobrand);
        mLinearautobrand = (LinearLayout) findViewById(R.id.linearautobrand);
        mRelativeBrand = (RelativeLayout) findViewById(R.id.rell);
        mParent = (RelativeLayout) findViewById(R.id.relativeparent);
        mCoverImage = (ImageView) findViewById(R.id.imgaddcover);
        mPhoto = (ImageView) findViewById(R.id.imgaddphoto);
        //  brandSpinner = (MultiSelectionSpinnerForBrands) mCreateStore.findViewById(R.id.brandSpinner);
        btnaddprofile.setOnClickListener(this);
        btnaddcover.setOnClickListener(this);
        create.setOnClickListener(this);
        multiautotext.setOnTouchListener(this);
        multiautobrand.setOnTouchListener(this);
        opentime.setOnTouchListener(this);
        closetime.setOnTouchListener(this);

        mApiCall = new ApiCall(this, this);
        genericFunctions = new GenericFunctions();
        /*
        Image upload to server
         */
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        // Change base URL to your upload server URL.
        mImageUpload = new Retrofit.Builder().baseUrl(getString(R.string.base_url)).client(client).build().create(ImageUpload.class);
        if (getIntent().getExtras() != null) {
            callFrom = getIntent().getExtras().getString("className");
        }
        myContact = getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "");
        if (callFrom.equals("StoreViewActivity")) {
            store_id = getIntent().getExtras().getInt("store_id");
            setTitle("Update Store");
            getBrandTags();
            dialog.show();
            mApiCall.getStoreData(myContact, store_id);
        }
        storelocation.setAdapter(new GooglePlacesAdapter(this, R.layout.simple));
        weekdays.add("Mon");
        weekdays.add("Tue");
        weekdays.add("Wed");
        weekdays.add("Thu");
        weekdays.add("Fri");
        weekdays.add("Sat");
        weekdays.add("Sun");
        weekspn.setItems(weekdays, "-Select Working Days-", this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    storecontact.setText(myContact);
                    storecontact.setEnabled(false);
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        multiautobrand.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    //  Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
                    multiautobrand.setText(multiautobrand.getText().toString() + ",");
                    multiautobrand.setSelection(multiautobrand.getText().toString().length());
                    check();
                    return true;
                }
                return false;
            }
        });
        multiautobrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                check();
            }
        });
    }

    public boolean check() {
        String text = multiautobrand.getText().toString();
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        String[] parts = text.split(",");
        if (parts.length > 6) {
            multiautobrand.setError("You can add maximum five tags only");
            return true;
        } else
            return false;
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
                finalbrandtags = "";
                Boolean flag = false;
                Boolean flagtime = false;
                String address = "", name = "", contact = "", location = "", website = "", storeDescription = "", workdays = "",
                        stropen = "", strclose = "", category = "";
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
                List<String> tempbrands = new ArrayList<String>();
                String textbrand = multiautobrand.getText().toString();
                if (textbrand.endsWith(","))
                    textbrand = textbrand.substring(0, textbrand.length() - 1);
                textbrand = textbrand.trim();

                if (!textbrand.equals("")) {
                    String[] bparts = textbrand.split(",");
                    for (String bpart : bparts) {
                        brandtagpart = bpart.trim();
                        if (!brandtagpart.equals("") && !brandtagpart.equalsIgnoreCase(" "))
                            tempbrands.add(brandtagpart);
                        if (!brandTags.contains(brandtagpart) && !brandtagpart.equals("") && !brandtagpart.equalsIgnoreCase(" ")) {
                            System.out.println("brand tag going to add=" + brandtagpart);
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
                    storetype = "product,service";
                } else if (rbtstoreproduct.isChecked() && !rbtstoreservice.isChecked() && rbtstorevehicle.isChecked()) {
                    storetype = "product,vehicle";
                } else if (!rbtstoreproduct.isChecked() && rbtstoreservice.isChecked() && rbtstorevehicle.isChecked()) {
                    storetype = "service,vehicle";
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
                if (name.equals("") || name.startsWith(" ") && name.endsWith(" ")) {
                    storename.setError("Enter Valid Name");
                    storename.requestFocus();
                } else if (!name.matches("[a-zA-Z ]*")) {
                    storename.setError("Enter Valid Name");
                    storename.requestFocus();
                } else if (location.equals("")) {
                    storelocation.setError("Enter location");
                    storelocation.requestFocus();
                } else if (!flag) {
                    storelocation.setError("Please Select Address From Dropdown Only");
                    storelocation.requestFocus();
                } else if (address.equals("")) {
                    storeaddress.setError("Enter address");
                    storeaddress.requestFocus();
                }
                //Validation for website is not valid
                else if (!website.equals("") && !(genericFunctions.isValidUrl(website))) {
                    storewebsite.setError("Enter valid website");
                    storewebsite.requestFocus();
                } else if (stropen.equals("")) {
                    opentime.setError("Enter Open Time");
                    opentime.requestFocus();
                } else if (strclose.equals("")) {
                    closetime.setError("Enter Close Time");
                    closetime.requestFocus();
                } else if (opentime.getText().toString().contains("PM") && closetime.getText().toString().contains("AM")) {
                    CustomToast.customToast(getApplicationContext(), "Enter Valid Time");
                } else if (flagtime) {
                    CustomToast.customToast(getApplicationContext(), "Enter Valid Time");
                } else if (workdays.equals("-Select Working Days-") || workdays.equals("")) {
                    CustomToast.customToast(getApplicationContext(), "Please select Working Days");
                } else if (storetype.equalsIgnoreCase("")) {
                    CustomToast.customToast(getApplicationContext(), "Select store type");
                } else if (category.equalsIgnoreCase("")) {
                    CustomToast.customToast(getApplicationContext(), "Provide services offered");
                } else if ((rbtstoreproduct.isChecked() || rbtstoreservice.isChecked()) && finalbrandtags.equals("")) {
                    CustomToast.customToast(getApplicationContext(), "Provide brand tags");
                    multiautobrand.requestFocus();
                } else if (check()) {
                    multiautobrand.setError("You can add maximum five tags only");
                    multiautobrand.requestFocus();
                }
                else {
                    if (!callFrom.equals("StoreViewActivity")) {
                       // dialog.show();

                     /*   createStore(name, contact, location, website, storetype, lastWord, workdays,
                                stropen, strclose, category, address, coverlastWord, storeDescription
                                , finalbrandtags, "");*/

                        Bundle b=new Bundle();
                        b.putString("storename",name);
                        b.putString("contact",contact);
                        b.putString("location",location);
                        b.putString("website",website);
                        b.putString("storetype",storetype);
                        b.putString("lastWord",lastWord);
                        b.putString("workdays",workdays);
                        b.putString("stropen",stropen);
                        b.putString("strclose",strclose);
                        b.putString("category",category);
                        b.putString("address",address);
                        b.putString("coverlastWord",coverlastWord);
                        b.putString("storeDescription",storeDescription);
                        b.putString("finalbrandtags",finalbrandtags);
                        b.putInt("store_id",store_id);
                        b.putString("callFrom","createnewstore");


                        ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                        Intent intent = new Intent(CreateStoreActivity.this, AddAdminsForStoreActivity.class);
                        intent.putExtras(b);
                        startActivityForResult(intent, 1);

                        if (!lastWord.equals(""))
                            uploadImage(mediaPath);
                        if (!coverlastWord.equals(""))
                            uploadImage(mediaPath1);

                    } else {
                        if (lastWord.equals(""))
                            lastWord = preLastWord;
                        if (coverlastWord.equals(""))
                            coverlastWord = preCoverLastWord;
                     //   dialog.show();
                     /*   updateStore(name, store_id, location, website, stropen, strclose, lastWord, category, workdays, storeDescription, storetype, address,
                                coverlastWord, finalbrandtags, "");*/


                        Bundle b=new Bundle();
                        b.putString("storename",name);
                        b.putString("contact",contact);
                        b.putString("location",location);
                        b.putString("website",website);
                        b.putString("storetype",storetype);
                        b.putString("lastWord",lastWord);
                        b.putString("workdays",workdays);
                        b.putString("stropen",stropen);
                        b.putString("strclose",strclose);
                        b.putString("category",category);
                        b.putString("address",address);
                        b.putString("coverlastWord",coverlastWord);
                        b.putString("storeDescription",storeDescription);
                        b.putString("finalbrandtags",finalbrandtags);
                        b.putInt("store_id",store_id);
                        b.putString("callFrom",callFrom);
                        b.putString("mediapath",mediaPath);
                        b.putString("mediaPath1",mediaPath1);

                        ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                        Intent intent = new Intent(CreateStoreActivity.this, AddAdminsForStoreActivity.class);
                        intent.putExtras(b);
                        startActivityForResult(intent, 1);

                        if (!lastWord.equals(""))
                            uploadImage(mediaPath);
                        if (!coverlastWord.equals(""))
                            uploadImage(mediaPath1);
                    }
                }
                break;
        }
    }


    public void onPickImage(View view, String whichButton) {
        getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().putString("imageCallStore", whichButton).apply();
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
            String result = getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("imageCallStore", "");
            getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().putString("imageCallStore", "").apply();
            String fname;
            if (result.equalsIgnoreCase("addProfile")) {
                if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    // Set the Image in ImageView for Previewing the Media
                 //   mPhoto.setImageBitmap(BitmapFactory.decodeFile(mediaPath));

                    Glide.with(CreateStoreActivity.this)
                            .load(mediaPath)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mPhoto);

                    cursor.close();
                    ///storage/emulated/0/DCIM/Camera/20170411_124425.jpg
                    lastWord = mediaPath.substring(mediaPath.lastIndexOf("/") + 1);
                    Log.i("Media", "path" + lastWord);
                    mediaPath = compressImage(mediaPath);
                    //uploadImage(mediaPath);

                } else if (requestCode == 101) {
                    if (resultCode == RESULT_OK) {
                        if (data != null) {
                            selectedImage = data.getData(); // the uri of the image taken
                            if (String.valueOf((Bitmap) data.getExtras().get("data")).equals("null")) {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                            } else {
                                bitmap = (Bitmap) data.getExtras().get("data");
                            }
                            if (Float.valueOf(getImageOrientation()) >= 0) {
                                bitmapRotate = rotateImage(bitmap, Float.valueOf(getImageOrientation()));
                            } else {
                                bitmapRotate = bitmap;
                                bitmap.recycle();
                            }
                            mPhoto.setImageBitmap(bitmapRotate);

                            /*Glide.with(CreateStoreActivity.this)
                                    .load(bitmapRotate)
                                    .centerCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(mPhoto);*/
//                            Saving image to mobile internal memory for sometime
                            String root = getFilesDir().toString();
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
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath1 = cursor.getString(columnIndex);
                    // Set the Image in ImageView for Previewing the Media
                    //mProfilePic.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    Glide.with(CreateStoreActivity.this)
                            .load(mediaPath1)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mCoverImage);

                    cursor.close();
                    ///storage/emulated/0/DCIM/Camera/20170411_124425.jpg
                    coverlastWord = mediaPath1.substring(mediaPath1.lastIndexOf("/") + 1);
                    Log.i("Media", "path" + coverlastWord);
                    mediaPath1 = compressImage(mediaPath1);
                    //uploadImage(mediaPath1);

                } else if (requestCode == 101) {
                    if (resultCode == RESULT_OK) {
                        if (data != null) {
                            selectedImage = data.getData(); // the uri of the image taken
                            if (String.valueOf((Bitmap) data.getExtras().get("data")).equals("null")) {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                            } else {
                                bitmap = (Bitmap) data.getExtras().get("data");
                            }
                            if (Float.valueOf(getImageOrientation()) >= 0) {
                                bitmapRotate = rotateImage(bitmap, Float.valueOf(getImageOrientation()));
                            } else {
                                bitmapRotate = bitmap;
                                bitmap.recycle();
                            }
                            mCoverImage.setImageBitmap(bitmapRotate);
//                            Saving image to mobile internal memory for sometime
                        /*    Glide.with(CreateStoreActivity.this)
                                    .load(bitmapRotate)
                                    .centerCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(mCoverImage);*/

                            String root = getFilesDir().toString();
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

            String result = getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("imageCallStore", "");
            if (result.equalsIgnoreCase("addProfile"))
                lastWord = mediaPath.substring(mediaPath.lastIndexOf("/") + 1);
            else if (result.equalsIgnoreCase("addCover"))
                coverlastWord = mediaPath1.substring(mediaPath1.lastIndexOf("/") + 1);
            // uploadImage(mediaPath);
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
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        }
    }

    //    In some mobiles image will get rotate so to correting that this code will help us
    private int getImageOrientation() {
        final String[] imageColumns = {MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.ORIENTATION};
        final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
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
                    new SetMyDateAndTime("time", opentime, this);
                }
                break;

            case R.id.editclose:
                if (action == MotionEvent.ACTION_DOWN) {
                    closetime.setInputType(InputType.TYPE_NULL);
                    closetime.setError(null);
                    new SetMyDateAndTime("time", closetime, this);
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
            // type = "1";
            type = "product";
        else if (typeservice && !typeproduct)
            //  type = "2";
            type = "service";
        else
            // type = "1,2";
            type = "both";
        mApiCall.getBrandTags(type);
    }

    private void addOtherBrandTags(String brandtagpart) {
        String type = "";
        if (typeproduct && !typeservice)
            //type = "1";
            type = "product";
        else if (typeservice && !typeproduct)
            type = "service";
        else
            type = "both";
        mApiCall.addOtherBrandTags(brandtagpart, type);
    }

    private void createStore(String name, String contact, String location, String website, String storetype, String lastWord,
                             String workdays, String open, String close, String category, String address,
                             String coverlastWord, String storeDescription, String textbrand, String strBrandSpinner) {
        mApiCall.CreateStore(name, contact, location, website, storetype, lastWord, workdays, open, close, category, address, coverlastWord,
                storeDescription, textbrand, strBrandSpinner);
    }

    private void updateStore(String name, int store_id, String location, String website, String stropen, String strclose,
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
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(this, R.layout.addproductspinner_color, module);
                        multiautotext.setAdapter(dataadapter);
                    } else
                        CustomToast.customToast(this, getString(R.string.no_response));
                }
                /*
                        Response to get Brand tags
                 */
                if (response.body() instanceof BrandsTagResponse) {
                    BrandsTagResponse brandResponse = (BrandsTagResponse) response.body();
                    brandTags.clear();
                    if (!brandResponse.getSuccess().isEmpty()) {
                        for (BrandsTagResponse.Success message : brandResponse.getSuccess()) {
                            message.setTagID(message.getTagID());
                            message.setTagName(message.getTagName());
                            brandTags.add(message.getTagName());
                        }

                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(this, R.layout.registration_spinner, brandTags);
                        multiautobrand.setAdapter(dataadapter);

                    } else
                        CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
                }
                 /*
                        Response after creating store
                 */
                if (response.body() instanceof CreateStoreResponse) {
                    CreateStoreResponse createStoreResponse = (CreateStoreResponse) response.body();
                    if (createStoreResponse.getSuccess() != null) {
                        int id = createStoreResponse.getSuccess().getStoreID();
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), "Store created", Toast.LENGTH_SHORT).show();
                        if (!lastWord.equals(""))
                            uploadImage(mediaPath);
                        if (!coverlastWord.equals(""))
                            uploadImage(mediaPath1);

                        bundle = new Bundle();
                        bundle.putInt("store_id", id);
                        bundle.putString("call", callFrom);
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                        Intent intent = new Intent(CreateStoreActivity.this, AddAdminsForStoreActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent, options.toBundle());
                        finish();
                    } else {
                        CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
                    }
                }

                if (response.body() instanceof StoreResponse) {
                    StoreResponse storeResponse = (StoreResponse) response.body();
                    if (!storeResponse.getSuccess().isEmpty()) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        for (StoreResponse.Success success : storeResponse.getSuccess()) {
                            storename.setText(success.getName());
                            storelocation.setText(success.getLocation());
                            storewebsite.setText(success.getWebsite());
                            weekspn.setItems(weekdays, success.getWorkingDays(), this);
                            opentime.setText(success.getStoreOpenTime());
                            closetime.setText(success.getStoreCloseTime());
                            storeaddress.setText(success.getAddress());
                            edtStoreDesc.setText(success.getStoreDescription());
                            multiautobrand.setText(success.getBrandtags());
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
                            } else if (storeType.equalsIgnoreCase("product,service")) {
                                rbtstoreproduct.setChecked(true);
                                rbtstoreservice.setChecked(true);
                            } else if (storeType.equalsIgnoreCase("product,vehicle")) {
                                rbtstoreproduct.setChecked(true);
                                rbtstorevehicle.setChecked(true);
                            } else if (storeType.equalsIgnoreCase("service,vehicle")) {
                                rbtstoreservice.setChecked(true);
                                rbtstorevehicle.setChecked(true);
                            } else if (storeType.equalsIgnoreCase("product,service,vehicle")) {
                                rbtstoreproduct.setChecked(true);
                                rbtstoreservice.setChecked(true);
                                rbtstorevehicle.setChecked(true);
                            }
                        }
                    } else {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
                    }
                }
            } else {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            }
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            //CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            // CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "Create Store Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("brand_tag_added")) {
                CustomToast.customToast(getApplicationContext(), "Brand Tags Added");
            } else if (str.equals("store_updated")) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                CustomToast.customToast(getApplicationContext(), "Store updated");
                if (!lastWord.equals(preLastWord) && !lastWord.equals(""))
                    uploadImage(mediaPath);
                if (!coverlastWord.equals(preCoverLastWord) && !coverlastWord.equals(""))
                    uploadImage(mediaPath1);
                bundle = new Bundle();
                bundle.putInt("store_id", store_id);
                bundle.putString("call", callFrom);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent intent = new Intent(CreateStoreActivity.this, AddAdminsForStoreActivity.class);
                intent.putExtras(bundle);
                startActivity(intent, options.toBundle());
            }
        } else
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
    }

    @Override
    public void onItemsSelected(boolean[] selected) {

    }


    public String compressImage(String imageUri) {
        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
//      max Height and width values of the compressed image is taken as 816x612
        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;
//      width and height values are set maintaining the aspect ratio of the image
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;
//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];
        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);
//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
