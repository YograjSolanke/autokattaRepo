package autokatta.com.view;

import android.content.DialogInterface;
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
import autokatta.com.response.ProductAddedResponse;
import autokatta.com.response.ProfileGroupResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ak-004 on 21/4/17.
 */

public class AddProductActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener {



    String store_id, myContact;
    EditText productname, productprice, productdetails, producttype;
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
    ArrayList<String> groupId = new ArrayList<>();
    ArrayList<String> groupTitle = new ArrayList<>();
    String[] stringTitles = new String[0], stringIds = new String[0];
    AlertDialog alertDialog;
    String allimgpath = "";
    ArrayList<Image> mImages = new ArrayList<>();
    int REQUEST_CODE_PICKER = 2000;
    String stringgroupids = "";
    String name, price, details, type, name1, category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        store_id = getIntent().getExtras().getString("store_id");


        productname = (EditText) findViewById(R.id.editproductname);
        productprice = (EditText) findViewById(R.id.editproductprice);
        productdetails = (EditText) findViewById(R.id.editproductdetails);
        save = (Button) findViewById(R.id.btnsave);
        addphotos = (Button) findViewById(R.id.btnaddphotos);
        producttype = (EditText) findViewById(R.id.editproducttype);
        multiautotext = (MultiAutoCompleteTextView) findViewById(R.id.multiautotext);
        autoCategory = (MultiAutoCompleteTextView) findViewById(R.id.multiautocategory);
        multiautobrand = (MultiAutoCompleteTextView) findViewById(R.id.multiautobrand);


        multiautotext.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiautobrand.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        autoCategory.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        save.setOnClickListener(this);
        addphotos.setOnClickListener(this);
        getCategory();
        getTags();
        getBrandTags();
        getGroupData();

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnsave:


                name = productname.getText().toString();
                price = productprice.getText().toString();
                details = productdetails.getText().toString();
                type = producttype.getText().toString();
                category = autoCategory.getText().toString();


                category = category.trim();
                if (category.endsWith(","))
                    category = category.substring(0, category.length() - 1);
                category = category.trim();


                //fields validation
                if (name.equals("") && price.equals("") && details.equals("") && type.equals("")) {

                    Toast.makeText(AddProductActivity.this, "Please Enter All details", Toast.LENGTH_SHORT).show();
                } else if (type.equals("") || type.startsWith(" ") && type.endsWith(" ")) {
                    producttype.setError("Enter Product Type");
                    producttype.requestFocus();
                } else if (name.equals("") || name.startsWith(" ") && name.endsWith(" ")) {
                    productname.setError("Enter Valid Product Name");
                    productname.requestFocus();
                } else if (price.equals("") || price.startsWith(" ") && price.endsWith(" ")) {
                    productprice.setError("Enter Valid Product Price");
                    productprice.requestFocus();
                } else if (details.equals("") || details.startsWith(" ")) {
                    productdetails.setError("Enter Product details");
                    productdetails.requestFocus();
                } else if (category.equalsIgnoreCase("")) {
                    Toast.makeText(AddProductActivity.this, "Please Select Product Category", Toast.LENGTH_SHORT).show();
                } else if (check()) {
                    multiautotext.setError("You can add maximum five tags only");
                    multiautotext.requestFocus();
                } else {


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


                    if (stringTitles.length == 0) {

                        new AlertDialog.Builder(AddProductActivity.this)
                                .setTitle("No groups to display")
                                .setMessage("Do you want to create group?")

                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {


                                        Intent intent = new Intent(AddProductActivity.this, GroupTabs.class);
                                        intent.putExtra("ClassName", "AddProductActivity");
                                        startActivity(intent);
                                    }
                                })

                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        createProduct(store_id, name, price, details, "", type, allimg, category, finalbrandtags, "");
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else
                        alertBoxToSelectExcelSheet(stringTitles);

                    // createProduct(store_id, name, price, details, "", type, allimg, category, finalbrandtags,"");

                }


                break;

            case R.id.btnaddphotos:

                LayoutInflater layoutInflater = LayoutInflater.from(AddProductActivity.this);
                View mViewDialogOtp = layoutInflater.inflate(R.layout.custom_alert_dialog_image, null);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(AddProductActivity.this);
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

    private void createProduct(String store_id,
                               String product_name,
                               String price,
                               String product_details,
                               String product_tags,
                               String product_type,
                               String images,
                               String category,
                               String brandtags,
                               String group_id) {

        ApiCall mApiCall = new ApiCall(AddProductActivity.this, this);
        mApiCall.addProduct(store_id, product_name, price, product_details, product_tags, product_type, images, category, brandtags, group_id);
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
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(AddProductActivity.this, R.layout.addproductspinner_color, module);
                        autoCategory.setAdapter(dataadapter);
                    } else
                        CustomToast.customToast(AddProductActivity.this, getString(R.string.no_response));
                } else if (response.body() instanceof BrandsTagResponse) {
                    BrandsTagResponse brandsTagResponse = (BrandsTagResponse) response.body();
                    brandTags.clear();
                    if (!brandsTagResponse.getSuccess().isEmpty()) {
                        for (BrandsTagResponse.Success success : brandsTagResponse.getSuccess()) {
                            brandTags.add(success.getTag());
                        }
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(AddProductActivity.this, R.layout.addproductspinner_color, brandTags);
                        multiautobrand.setAdapter(dataadapter);
                    }
                } else if (response.body() instanceof GetTagsResponse) {
                    GetTagsResponse tagsResponse = (GetTagsResponse) response.body();
                    id.clear();
                    tagname.clear();
                    if (!tagsResponse.getSuccess().isEmpty()) {
                        for (GetTagsResponse.Success success : tagsResponse.getSuccess()) {
                            tagname.add(success.getTag());
                            id.add(success.getId());
                        }
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(AddProductActivity.this, R.layout.addproductspinner_color, tagname);
                        multiautotext.setAdapter(dataadapter);
                    }
                } else if (response.body() instanceof OtherBrandTagAddedResponse) {
                    CustomToast.customToast(AddProductActivity.this, "Brand Tag added successfully");
                } else if (response.body() instanceof OtherTagAddedResponse) {
                    CustomToast.customToast(AddProductActivity.this, "Other Tag added successfully");
                    tagid = tagid + "," + ((OtherTagAddedResponse) response.body()).getSuccess().getTagID().toString();
                    tagflag = true;
                } else if (response.body() instanceof ProductAddedResponse) {
                    CustomToast.customToast(AddProductActivity.this, "Product added successfully");

                    ProductAddedResponse productAddedResponse = (ProductAddedResponse) response.body();
                    String product_id = productAddedResponse.getSuccess().getProductId().toString();
                    sendTags(product_id);
                    uploadImage(allimgpath);

                    Bundle b = new Bundle();
                    b.putString("store_id", store_id);
                    Intent intent = new Intent(AddProductActivity.this, StoreViewActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                } else if (response.body() instanceof ProfileGroupResponse) {
                    ProfileGroupResponse profileGroupResponse = (ProfileGroupResponse) response.body();
                    for (ProfileGroupResponse.MyGroup success : profileGroupResponse.getSuccess().getMyGroups()) {
                        groupId.add(success.getId());
                        groupTitle.add(success.getTitle());
                    }

                    stringTitles = groupTitle.toArray(new String[groupTitle.size()]);
                    stringIds = groupId.toArray(new String[groupId.size()]);
                }
            } else {
                CustomToast.customToast(AddProductActivity.this, getString(R.string._404));
            }
        } else {
            CustomToast.customToast(AddProductActivity.this, getString(R.string.no_response));
        }

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
                    Toast.makeText(AddProductActivity.this,
                            "You can upload 12 picture only",
                            Toast.LENGTH_LONG).show();
                }
            }

            if (cnt == 0) {
                Toast.makeText(AddProductActivity.this,
                        "Please select at least one image",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(AddProductActivity.this,
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

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

        if (str != null) {

            if (str.equals("success")) {
                CustomToast.customToast(AddProductActivity.this, "Tags sent");
            }

        }

    }

    public boolean check() {
        String text = multiautotext.getText().toString();
        System.out.println("texttttttttttttttttt" + text.substring(0, text.length() - 1));
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        String[] parts = text.split(",");
        System.out.println("size of partssssssssssssssssss" + parts.length);
        if (parts.length > 5) {
            multiautotext.setError("You can add maximum five tags only");
            return true;
        } else
            return false;


    }

    private void sendTags(String product_id) {
        ApiCall mApiCall = new ApiCall(AddProductActivity.this, this);
        mApiCall.TagAssociation(product_id, "", idlist);
    }

    /*
   Add Other Brand Tags...
    */
    private void addOtherBrandTags(String brandtagpart) {
        ApiCall mApiCall = new ApiCall(AddProductActivity.this, this);
        mApiCall.addOtherBrandTags(brandtagpart, "1");
    }

    /*
    Add Other Tags...
     */
    private void addOtherTags() {
        ApiCall mApiCall = new ApiCall(AddProductActivity.this, this);
        mApiCall._autoAddTags(tagpart, "1");
    }

    /*
    Get Tags...
     */
    private void getTags() {
        ApiCall mApiCall = new ApiCall(AddProductActivity.this, this);
        mApiCall._autoGetTags("1");
    }

    /*
    Get Brand Tags...
     */
    private void getBrandTags() {
        ApiCall mApiCall = new ApiCall(AddProductActivity.this, this);
        mApiCall.getBrandTags("1");
    }


    /*
    Get Module...
     */
    private void getCategory() {
        ApiCall mApiCall = new ApiCall(AddProductActivity.this, this);
        mApiCall.Categories("Product");
    }

    private void getGroupData() {
        ApiCall mApiCall = new ApiCall(AddProductActivity.this, this);
        mApiCall.Groups(myContact);
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
            Call<String> call = getResponse.uploadProductPic(fileToUpload, filename);
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


    ///////////////////////////////////////////////
    public void alertBoxToSelectExcelSheet(final String[] choices) {

        final ArrayList<String> mSelectedItems = new ArrayList<>();
        mSelectedItems.clear();
        stringgroupids = "";

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddProductActivity.this);
        // set the dialog title
        builder.setTitle("Select Groups Where You Want Show this Product")
                .setCancelable(true)
                .setMultiChoiceItems(choices, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            mSelectedItems.add(choices[which]);

                        } else if (mSelectedItems.contains(choices[which])) {

                            mSelectedItems.remove(choices[which]);

                        }
                    }

                })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                        if (!(mSelectedItems.size() == 0)) {
                            for (int i = 0; i < mSelectedItems.size(); i++) {
                                for (int j = 0; j < stringTitles.length; j++) {
                                    if (mSelectedItems.get(i).equals(stringTitles[j])) {
                                        if (stringgroupids.equals("")) {
                                            stringgroupids = groupId.get(j);
                                        } else {
                                            stringgroupids = stringgroupids + "," + groupId.get(j);

                                        }
                                    }
                                }

                            }


                            System.out.println("newwwwwwwwwwwwwwwwwwwwwwwww id=" + stringgroupids);
                            createProduct(store_id, name, price, details, "", type, allimg, category, finalbrandtags, stringgroupids);
                        } else {
                            CustomToast.customToast(AddProductActivity.this, "Please Select Atleast One Group");
                            android.app.AlertDialog alert = builder.create();
                            alert.show();
                        }


                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // removes the AlertDialog in the screen

                        stringgroupids = "";
                        System.out.println("newwwwwwwwwwwwwwwwwwwwwwwww id=" + stringgroupids);
                        createProduct(store_id, name, price, details, "", type, allimg, category, finalbrandtags, "");


                    }

                })

                .show();

    }
}
