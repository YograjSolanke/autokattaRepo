package autokatta.com.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BrandsTagResponse;
import autokatta.com.response.CategoryResponse;
import autokatta.com.response.GetTagsResponse;
import autokatta.com.response.OtherBrandTagAddedResponse;
import autokatta.com.response.OtherTagAddedResponse;
import autokatta.com.response.ServiceAddedResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ak-004 on 21/4/17.
 */

public class AddServiceActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener {


    String myContact, store_id;
    EditText servicename, serviceprice, servicedetails, servicetype;
    MultiAutoCompleteTextView multiautotext, autoCategory, multiautobrand;
    Button save, addphotos;
    String tagpart = "", tagid = "", brandtagpart = "", finalbrandtags = "";
    String idlist = "", product_id;
    boolean tagflag = false;
    String allimg = "";
    final ArrayList<String> id = new ArrayList<String>();
    final ArrayList<String> tagname = new ArrayList<String>();
    final ArrayList<String> brandtagId = new ArrayList<>();
    final ArrayList<String> brandTags = new ArrayList<>();
    AlertDialog alertDialog;
    String allimgpath = "";
    ArrayList<Image> mImages = new ArrayList<>();
    int REQUEST_CODE_PICKER = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_service_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");
        store_id = getIntent().getExtras().getString("store_id");


        servicename = (EditText) findViewById(R.id.editservicename);
        serviceprice = (EditText) findViewById(R.id.editserviceprice);
        servicedetails = (EditText) findViewById(R.id.editservicedetails);
        servicetype = (EditText) findViewById(R.id.editservicetype);
        multiautotext = (MultiAutoCompleteTextView) findViewById(R.id.multiautotext);
        autoCategory = (MultiAutoCompleteTextView) findViewById(R.id.multiautocategory);
        multiautobrand = (MultiAutoCompleteTextView) findViewById(R.id.multiautobrand);
        save = (Button) findViewById(R.id.btnsave);
        addphotos = (Button) findViewById(R.id.btnaddphotos);


        multiautotext.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiautobrand.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        autoCategory.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        save.setOnClickListener(this);
        addphotos.setOnClickListener(this);
        getCategory();
        getTags();
        getBrandTags();

        multiautotext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {


                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    //  Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
                    multiautotext.setText(multiautotext.getText().toString() + ",");
                    multiautotext.setSelection(multiautotext.getText().toString().length());
                    check();
                    return true;
                }
                return false;
            }
        });

        multiautotext.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                check();
//
            }
        });


    }


    public void check() {
        String text = multiautotext.getText().toString();
        System.out.println("texttttttttttttttttt" + text.substring(0, text.length() - 1));
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        String[] parts = text.split(",");
        System.out.println("size of partssssssssssssssssss" + parts.length);
        if (parts.length > 5) {
            multiautotext.setError("You can add maximum five tags");
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnsave:
                String name, price, details, type, name1, category;

                name = servicename.getText().toString();
                price = serviceprice.getText().toString();
                details = servicedetails.getText().toString();
                type = servicetype.getText().toString();
                category = autoCategory.getText().toString();

                category = category.trim();
                if (category.endsWith(","))
                    category = category.substring(0, category.length() - 1);
                category = category.trim();

                String text = multiautotext.getText().toString();
                ArrayList<String> images = new ArrayList<String>();
                ArrayList<String> othertag = new ArrayList<String>();
                if (text.endsWith(","))
                    text = text.substring(0, text.length() - 1);
                System.out.println("txttttt=" + text);
                text = text.trim();


                String[] parts = text.split(",");

                for (int l = 0; l < parts.length; l++) {
                    System.out.println(parts[l]);
                    tagpart = parts[l].trim();
                    if (!tagpart.equalsIgnoreCase("") && !tagpart.equalsIgnoreCase(" "))
                        images.add(tagpart);
                    if (!tagname.contains(tagpart) && !tagpart.equalsIgnoreCase("") && !tagpart.equalsIgnoreCase(" ")) {
                        othertag.add(tagpart);
                        System.out.println("tag going to add=" + tagpart);
                        try {
                            addOtherTags();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("other categoryyyyyyyyyyyyyyyy=" + othertag);

                }
                System.out.println("tagname arrat before change***************" + tagname);

                getTags();

                for (int i = 0; i < images.size(); i++) {

                    for (int j = 0; j < tagname.size(); j++) {
                        if (images.get(i).toString().equalsIgnoreCase(tagname.get(j).toString()))
                            idlist = idlist + "," + id.get(j).toString();
                    }

                }


                if (!multiautotext.getText().toString().equalsIgnoreCase("") && idlist.length() > 0) {
                    idlist = idlist.substring(1);
                    System.out.println("substring idddddddddd=" + idlist);


                }
                if (tagflag) {
                    tagid = tagid.substring(1);
                    System.out.println("response tag iddddddddddddddd=" + tagid);
                    if (!idlist.equalsIgnoreCase(""))
                        idlist = idlist + "," + tagid;
                    else
                        idlist = tagid;
                    System.out.println("final idlist iddddddddddddddd=" + idlist);

                }


                ArrayList<String> tempbrands = new ArrayList<String>();


                String textbrand = multiautobrand.getText().toString();

                if (textbrand.endsWith(","))
                    textbrand = textbrand.substring(0, textbrand.length() - 1);

                textbrand = textbrand.trim();

                if (!textbrand.equals("")) {

                    String[] bparts = textbrand.split(",");
                    for (int o = 0; o < bparts.length; o++) {
                        brandtagpart = bparts[o].trim();
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


                //fields validation
                if (name.equals("") && price.equals("") && details.equals("") && type.equals("")) {

                    Toast.makeText(AddServiceActivity.this, "Please Enter All details", Toast.LENGTH_SHORT).show();
                } else if (type.equals("")) {
                    servicetype.setError("Enter Service Type");
                } else if (name.equals("")) {
                    servicename.setError("Enter Service Name");
                } else if (price.equals("")) {
                    serviceprice.setError("Enter Service Price");
                } else if (details.equals("")) {
                    servicedetails.setError("Enter Service details");
                } else if (category.equalsIgnoreCase("")) {
                    Toast.makeText(AddServiceActivity.this, "Please Select Service Category", Toast.LENGTH_SHORT).show();
                } else {

                    createService(store_id, name, price, details, "", type, allimg, category, finalbrandtags);

                }


                break;

            case R.id.btnaddphotos:

                LayoutInflater layoutInflater = LayoutInflater.from(AddServiceActivity.this);
                View mViewDialogOtp = layoutInflater.inflate(R.layout.custom_alert_dialog_image, null);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(AddServiceActivity.this);
                builder1.setTitle("Upload Image");
                builder1.setIcon(R.mipmap.ic_launcher);
                builder1.setView(mViewDialogOtp);

                ImageView mGallery = (ImageView) mViewDialogOtp.findViewById(R.id.gallery);
                mGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        start();
                    }
                });
                alertDialog = builder1.create();
                alertDialog.show();
                break;

        }

    }

    private void createService(String store_id,
                               String service_name,
                               String price,
                               String service_details,
                               String service_tags,
                               String service_type,
                               String images,
                               String category,
                               String brandtags) {

        ApiCall mApiCall = new ApiCall(AddServiceActivity.this, this);
        mApiCall.addService(store_id, service_name, price, service_details, service_tags, service_type, images, category, brandtags);
    }
    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (response.body() instanceof CategoryResponse) {
                    CategoryResponse moduleResponse = (CategoryResponse) response.body();
                    final List<String> module = new ArrayList<String>();
                    if (!moduleResponse.getSuccess().isEmpty()) {
                        for (CategoryResponse.Success message : moduleResponse.getSuccess()) {
                            module.add(message.getTitle());
                        }
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(AddServiceActivity.this, R.layout.addproductspinner_color, module);
                        autoCategory.setAdapter(dataadapter);
                    } else
                        CustomToast.customToast(AddServiceActivity.this, getString(R.string.no_response));
                } else if (response.body() instanceof BrandsTagResponse) {
                    BrandsTagResponse brandsTagResponse = (BrandsTagResponse) response.body();
                    List<String> brands = new ArrayList<>();
                    if (!brandsTagResponse.getSuccess().isEmpty()) {
                        for (BrandsTagResponse.Success success : brandsTagResponse.getSuccess()) {
                            brands.add(success.getTag());
                        }
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(AddServiceActivity.this, R.layout.addproductspinner_color, brands);
                        multiautobrand.setAdapter(dataadapter);
                    }
                } else if (response.body() instanceof GetTagsResponse) {
                    GetTagsResponse tagsResponse = (GetTagsResponse) response.body();
                    List<String> tags = new ArrayList<>();
                    if (!tagsResponse.getSuccess().isEmpty()) {
                        for (GetTagsResponse.Success success : tagsResponse.getSuccess()) {
                            tags.add(success.getTag());
                        }
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(AddServiceActivity.this, R.layout.addproductspinner_color, tags);
                        multiautotext.setAdapter(dataadapter);
                    }
                } else if (response.body() instanceof OtherBrandTagAddedResponse) {
                    CustomToast.customToast(AddServiceActivity.this, "Brand Tag added successfully");
                } else if (response.body() instanceof OtherTagAddedResponse) {
                    CustomToast.customToast(AddServiceActivity.this, "Other Tag added successfully");
                    tagid = tagid + "," + ((OtherTagAddedResponse) response.body()).getSuccess().getTagID();
                    tagflag = true;
                } else if (response.body() instanceof ServiceAddedResponse) {
                    CustomToast.customToast(AddServiceActivity.this, "Service added successfully");


                    uploadImage(allimgpath);

                    Bundle b = new Bundle();
                    b.putString("store_id", store_id);
                    Intent intent = new Intent(AddServiceActivity.this, StoreViewActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }
            } else {
                CustomToast.customToast(AddServiceActivity.this, getString(R.string._404));
            }
        } else {
            CustomToast.customToast(AddServiceActivity.this, getString(R.string.no_response));
        }

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }

    private void start() {
        ImagePicker.create(this)
                .folderMode(true) // set folder mode (false by default)
                .folderTitle("Folder") // folder selection title
                .imageTitle("Tap to select") // image selection title
                .single() // single mode
                .multi() // multi mode (default mode)
                .limit(10) // max images can be selected (999 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera")   // captured image directory name ("Camera" folder by default)
                .origin(mImages) // original selected images, used in multi mode
                .start(REQUEST_CODE_PICKER); // start image picker activity with request code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
            mImages = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
            StringBuilder sb = new StringBuilder();
            ArrayList<String> addData = new ArrayList<>();
            ArrayList<String> mPath = new ArrayList<>();
            for (int i = 0; i < mImages.size(); i++) {
                sb.append(mImages.get(i).getPath());
                mPath.add(mImages.get(i).getPath());
            }
            ArrayList<String> mPath1 = new ArrayList<>();
            int cnt = 0;
            String selectImages = "";
            String selectedimg = "";
            for (int i = 0; i < mPath.size(); i++) {
                cnt++;
                if (cnt <= 12) {
                    selectImages = mPath.get(i);
                    String lastWord = selectImages.substring(selectImages.lastIndexOf("/") + 1);
                    mPath1.add(selectImages);
                    if (allimgpath.equalsIgnoreCase("")) {
                        allimgpath = "" + selectImages;
                    } else {
                        allimgpath = allimgpath + "," + selectImages;
                    }
                    if (selectedimg.equalsIgnoreCase("") && allimg.equalsIgnoreCase("")) {
                        selectedimg = "" + selectImages;
                        allimg = "" + lastWord.replace(" ", "");
                    } else {
                        selectedimg = selectedimg + "," + selectImages;
                        allimg = allimg + "," + lastWord.replace(" ", "");
                    }

                } else {
                    Toast.makeText(AddServiceActivity.this,
                            "You can upload 12 picture only",
                            Toast.LENGTH_LONG).show();
                }
            }

            if (cnt == 0) {
                Toast.makeText(AddServiceActivity.this,
                        "Please select at least one image",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(AddServiceActivity.this,
                        "You've selected Total " + cnt + " image(s).",
                        Toast.LENGTH_LONG).show();
                Log.d("SelectedImages", selectImages);

                getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("images", allimgpath).apply();


                alertDialog.dismiss();

            }
            System.out.println("all selected images=" + allimg);
            System.out.println(selectedimg);
        }
        //textView.setText(sb.toString());
    }


    /*
   Add Other Brand Tags...
    */
    private void addOtherBrandTags(String brandtagpart) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.addOtherBrandTags(brandtagpart, "2");
    }

    /*
    Add Other Tags...
     */
    private void addOtherTags() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall._autoAddTags(tagpart, "2");
    }

    /*
    Get Tags...
     */
    private void getTags() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall._autoGetTags("2");
    }

    /*
    Get Brand Tags...
     */
    private void getBrandTags() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.getBrandTags("2");
    }


    /*
    Get Module...
     */
    private void getCategory() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.Categories("Service");
    }

    private void uploadImage(String picturePath) {
        Log.i("PAth", "->" + picturePath);
        List<String> imgList = Arrays.asList(picturePath.split(","));
        int s = imgList.size();
        for (int i = 0; i < imgList.size(); i++) {


            File file = new File(imgList.get(i));
            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

            ServiceApi getResponse = ApiCall.getRetrofit().create(ServiceApi.class);
            Call<String> call = getResponse.uploadServicePic(fileToUpload, filename);
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
    }
}
