package autokatta.com.view;


import android.app.ActivityOptions;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BrandsTagResponse;
import autokatta.com.response.CategoryResponse;
import autokatta.com.response.EnquiryCountResponse;
import autokatta.com.response.GetTagsResponse;
import autokatta.com.response.OtherBrandTagAddedResponse;
import autokatta.com.response.OtherTagAddedResponse;
import autokatta.com.response.ProductResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 18/4/17.
 */
public class ProductViewActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener,
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    Button post, btnchat;
    Spinner spinCategory;
    TextView storename, website, txtlike, txtshare, txtreview;
    EditText productname, productprice, productdetails, producttype, writereview;
    Bundle b = new Bundle();
    //variables for getting data through bundle form adapter
    String name, web, rating, pname, pprice, pdetails, ptags, ptype, plikecnt, pimages, plikestatus, action, pcategory, prating, receiver_contact, prate, prate1, prate2, prate3, brandtags_list;
    ImageView edit, check, callme, deleteproduct;
    String allDetails;
    final ArrayList<String> spnid = new ArrayList<String>();
    final ArrayList<String> tagname = new ArrayList<String>();

    String imagename = "", reviewstring = "", pimagename = "", brandtagpart = "", finalbrandtags = "";

    int lcnt;
    LinearLayout linearlike, linearshare, linearshare1, linearunlike, linearreview;
    final ArrayList<String> brandTags = new ArrayList<>();

    // SharedPreferences settings;
    String contact;
    Float pricerate = 0.0f;
    Float qualityrate = 0.0f;
    Float stockrate = 0.0f;
    Float count;
    Float total;
    Button submitfeedback, seellreview;
    MultiAutoCompleteTextView producttags, multiautobrand;
    RelativeLayout relativerate;
    RatingBar pricebar, qualitybar, stockbar, overallbar, productrating, storerating;
    String store_id, storecontact, storeowner;

    //product updating variables
    String uptype, upname, upprice, updetails, uptags, upimgs, upcat;
    LinearLayout linearbtns, lineartxts;
    RelativeLayout spinnerlayout, relativewritereview;
    TextView photocount, no_of_enquiries;
    String tagpart = "", tagid = "";
    String idlist = "", product_id;
    boolean tagflag = false;
    ConnectionDetector mConnectionDetector;
    ArrayList<String> imageslist = new ArrayList<>();
    ApiCall mApiCall;
    SliderLayout sliderLayout;
    HashMap<String, String> Hash_file_maps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("View Products");

        mApiCall = new ApiCall(ProductViewActivity.this, this);
        contact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        mConnectionDetector = new ConnectionDetector(this);
        storename = (TextView) findViewById(R.id.txtstorename);
        website = (TextView) findViewById(R.id.txtstorewebsite);
        productname = (EditText) findViewById(R.id.txtpname);
        productprice = (EditText) findViewById(R.id.txtpprice);
        productdetails = (EditText) findViewById(R.id.txtpdetails);
        producttags = (MultiAutoCompleteTextView) findViewById(R.id.txtptags);
        producttype = (EditText) findViewById(R.id.txtptype);
        edit = (ImageView) findViewById(R.id.editproduct);
        check = (ImageView) findViewById(R.id.checkproduct);

        txtlike = (TextView) findViewById(R.id.txtlike);
        txtshare = (TextView) findViewById(R.id.txtshare);
        txtreview = (TextView) findViewById(R.id.txtreview);
        callme = (ImageView) findViewById(R.id.call);
        writereview = (EditText) findViewById(R.id.editwritereview);
        post = (Button) findViewById(R.id.btnpost);
        btnchat = (Button) findViewById(R.id.btnchat);
        seellreview = (Button) findViewById(R.id.btnseeall);
        linearlike = (LinearLayout) findViewById(R.id.linearlike);
        linearunlike = (LinearLayout) findViewById(R.id.linearunlike);
        linearshare = (LinearLayout) findViewById(R.id.linearshare);
        linearshare1 = (LinearLayout) findViewById(R.id.linearshare1);
        linearreview = (LinearLayout) findViewById(R.id.linearreview);

        spinCategory = (Spinner) findViewById(R.id.spincategory);
        spinnerlayout = (RelativeLayout) findViewById(R.id.linearcategory);

        deleteproduct = (ImageView) findViewById(R.id.deleteproduct);
        relativerate = (RelativeLayout) findViewById(R.id.relativerateproduct);
        lineartxts = (LinearLayout) findViewById(R.id.lineartxts);
        relativewritereview = (RelativeLayout) findViewById(R.id.linearwritereview);
        pricebar = (RatingBar) findViewById(R.id.pricebar);
        qualitybar = (RatingBar) findViewById(R.id.qualitybar);
        stockbar = (RatingBar) findViewById(R.id.stockbar);
        productrating = (RatingBar) findViewById(R.id.productrating);
        storerating = (RatingBar) findViewById(R.id.storerating);
        overallbar = (RatingBar) findViewById(R.id.overallbar);
        submitfeedback = (Button) findViewById(R.id.btnfeedback);
        photocount = (TextView) findViewById(R.id.no_of_photos);
        no_of_enquiries = (TextView) findViewById(R.id.no_of_enquiries);
        multiautobrand = (MultiAutoCompleteTextView) findViewById(R.id.txtbrandptags);

        overallbar.setEnabled(false);
        storerating.setEnabled(false);
        productrating.setEnabled(false);


        edit.setOnClickListener(this);
        check.setOnClickListener(this);
        linearreview.setOnClickListener(this);
        callme.setOnClickListener(this);
        post.setOnClickListener(this);
        btnchat.setOnClickListener(this);
        deleteproduct.setOnClickListener(this);
        linearlike.setOnClickListener(this);
        linearunlike.setOnClickListener(this);
        submitfeedback.setOnClickListener(this);
        linearshare1.setOnClickListener(this);
        linearshare.setOnClickListener(this);
        seellreview.setOnClickListener(this);

        product_id = getIntent().getExtras().getString("product_id");


        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                }

                getCategory();
                getProductData(product_id, contact);
                getNoOfEnquiryCount(product_id, contact);


                producttags.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                multiautobrand.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


                producttags.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                                (i == KeyEvent.KEYCODE_ENTER)) {
                            // Perform action on key press
                            producttags.setText(producttags.getText().toString() + ",");
                            producttags.setSelection(producttags.getText().toString().length());
                            check();
                            return true;
                        }
                        return false;
                    }
                });

                producttags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        check();
                    }
                });
                producttags.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if (!b && !producttags.getText().toString().equalsIgnoreCase("")) {
                            check();
                        }
                    }
                });

                pricebar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        pricerate = v;
                        calculate(pricerate, qualityrate, stockrate);
                    }
                });

                qualitybar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        qualityrate = v;
                        calculate(pricerate, qualityrate, stockrate);
                    }
                });

                stockbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        stockrate = v;
                        calculate(pricerate, qualityrate, stockrate);
                    }
                });

            }
        });

    }

    private void getNoOfEnquiryCount(String product_id, String contact) {
        ApiCall mApicall = new ApiCall(this, this);
        mApicall.getEnquiryCount(contact, product_id, "", "");

    }

    private void getChatEnquiryStatus(String contact, String receiver_contact, String product_id) {

        ApiCall mApicall = new ApiCall(this, this);
        mApicall.getChatEnquiryStatus(contact, receiver_contact, product_id, "", "");
    }

    private void getProductData(String id, String contact) {
        ApiCall mApicall = new ApiCall(this, this);
        mApicall.getProductDetails(id, contact);
    }

    /*
    Update Ratings...
     */
    private void sendupdatedproductrating() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.sendUpdatedrating(contact, "", product_id, "", String.valueOf(count), String.valueOf(pricerate), String.valueOf(qualityrate)
                , String.valueOf(stockrate), "", "", "product");
    }

    /*
    Ratings...
     */
    private void sendproductrating() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.sendNewrating(contact, "", product_id, "", String.valueOf(count), String.valueOf(pricerate), String.valueOf(qualityrate)
                , String.valueOf(stockrate), "", "", "product");
    }

    /*
    Unlike...
     */
    private void sendUnlike() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall._autokattaProductViewUnlike(contact, receiver_contact, "5", product_id);
    }

    /*
    Like
     */
    private void sendLike() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall._autokattaProductView(contact, receiver_contact, "5", product_id);
    }

    /*
    Delete Product
     */
    private void deleteproduct() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.deleteProduct(product_id, "delete");
    }

    /*
    Add Other Brand Tags...
     */
    private void addOtherBrandTags(String brandtagpart) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.addOtherBrandTags(brandtagpart, "1");
    }

    /*
    Add Other Tags...
     */
    private void addOtherTags() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall._autoAddTags(tagpart, "1");
    }

    /*
    Get Tags...
     */
    private void getTags() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall._autoGetTags("1");
    }

    /*
    Get Brand Tags...
     */
    private void getBrandTags() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.getBrandTags("1");
    }

    /*
    Review Task...
     */
    private void reviewTask() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.postProductReview(contact, receiver_contact, product_id, reviewstring);
    }

    /*
    Get Module...
     */
    private void getCategory() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.Categories("Product");
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
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(this, R.layout.addproductspinner_color, module);
                        spinCategory.setAdapter(dataadapter);
                    } else
                        CustomToast.customToast(this, getString(R.string.no_response));
                } else if (response.body() instanceof BrandsTagResponse) {
                    BrandsTagResponse brandsTagResponse = (BrandsTagResponse) response.body();
                    brandTags.clear();
                    if (!brandsTagResponse.getSuccess().isEmpty()) {
                        for (BrandsTagResponse.Success success : brandsTagResponse.getSuccess()) {
                            brandTags.add(success.getTag());
                        }
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(this, R.layout.addproductspinner_color, brandTags);
                        multiautobrand.setAdapter(dataadapter);
                    }
                } else if (response.body() instanceof GetTagsResponse) {
                    GetTagsResponse tagsResponse = (GetTagsResponse) response.body();
                    spnid.clear();
                    tagname.clear();
                    if (!tagsResponse.getSuccess().isEmpty()) {
                        for (GetTagsResponse.Success success : tagsResponse.getSuccess()) {
                            spnid.add(success.getId());
                            tagname.add(success.getTag());
                        }
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(this, R.layout.addproductspinner_color, tagname);
                        producttags.setAdapter(dataadapter);
                    }
                } else if (response.body() instanceof OtherBrandTagAddedResponse) {
                    CustomToast.customToast(ProductViewActivity.this, "Brand Tag added successfully");
                } else if (response.body() instanceof OtherTagAddedResponse) {
                    CustomToast.customToast(ProductViewActivity.this, "Other Tag added successfully");
                    tagid = tagid + "," + ((OtherTagAddedResponse) response.body()).getSuccess().getTagID().toString();
                    tagflag = true;
                } else if (response.body() instanceof ProductResponse) {
                    ProductResponse productresponse = (ProductResponse) response.body();
                    if (!productresponse.getSuccess().isEmpty()) {
                        for (ProductResponse.Success success : productresponse.getSuccess()) {
                            product_id = success.getProductId();
                            name = success.getStoreName();
                            web = success.getStoreWebsite();
                            rating = success.getStoreRating();
                            receiver_contact = success.getStoreContact();
                            pname = success.getProductName();
                            pprice = success.getPrice();
                            pdetails = success.getProductDetails();
                            ptags = success.getProductTagNames();
                            ptype = success.getProductType();
                            pimages = success.getImages();
                            prating = success.getProductrating();
                            pcategory = success.getCategory();
                            plikestatus = success.getProductlikestatus();
                            plikecnt = success.getProductlikecount();
                            prate = success.getPrate();
                            prate1 = success.getPrate1();
                            prate2 = success.getPrate2();
                            prate3 = success.getPrate3();
                            store_id = success.getStoreId();
                            storecontact = success.getStoreContact();
                            //  storecontact = "3030303030";
                            storeowner = success.getStoreOwner();
                            brandtags_list = success.getBrandtags();

                            getChatEnquiryStatus(contact, receiver_contact, product_id);
                            txtlike.setText("Like(" + plikecnt + ")");

                            if (storecontact.contains(contact)) {
                                btnchat.setVisibility(View.GONE);
                                no_of_enquiries.setVisibility(View.VISIBLE);
                                edit.setVisibility(View.VISIBLE);
                                deleteproduct.setVisibility(View.VISIBLE);
                                callme.setVisibility(View.GONE);
                                relativerate.setVisibility(View.GONE);
                                relativewritereview.setVisibility(View.GONE);
                              //  linearlike.setEnabled(false);
                                linearreview.setEnabled(false);
                            } else {
                                callme.setVisibility(View.VISIBLE);
                                relativerate.setVisibility(View.VISIBLE);
                                linearreview.setEnabled(true);
                                edit.setVisibility(View.GONE);
                                deleteproduct.setVisibility(View.GONE);
                                if (plikestatus.equals("yes")) {
                                    linearlike.setVisibility(View.GONE);
                                    linearunlike.setVisibility(View.VISIBLE);
                                } else if (plikestatus.equalsIgnoreCase("no")) {
                                    linearlike.setVisibility(View.VISIBLE);
                                    linearunlike.setVisibility(View.GONE);
                                }
                            }


                            //***************************setting previous rating*******************************
                            if (!prate.equals("0")) {
                                overallbar.setRating(Float.parseFloat(prate));
                            }
                            if (!prate1.equals("0")) {
                                pricebar.setRating(Float.parseFloat(prate1));
                            }
                            if (!prate2.equals("0")) {
                                qualitybar.setRating(Float.parseFloat(prate2));
                            }
                            if (!prate3.equals("0")) {
                                stockbar.setRating(Float.parseFloat(prate3));
                            }

                            //rating conditions for store
                            if (!rating.equals("null")) {
                                storerating.setRating(Float.parseFloat(rating));
                            }
                            //rating conditions for product

                            if (!prating.equals("null")) {
                                productrating.setRating(Float.parseFloat(prating));
                            }

                            if (pimages.equals("")) {
                                photocount.setText("0 Photos");
                            }
                            /*else {
                                String[] parts = pimages.split(",");
                                photocount.setText(parts.length + " Photos");
                                for (int l = 0; l < parts.length; l++) {
                                    imageslist.add(parts[l]);
                                    System.out.println(parts[l]);
                                }
                                pimagename = "http://autokatta.com/mobile/Product_pics/" + imageslist.get(0);
                                pimagename = pimagename.replaceAll(" ", "%20");
                                try {
                                    Glide.with(ProductViewActivity.this)
                                            .load(pimagename)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .bitmapTransform(new CropCircleTransformation(ProductViewActivity.this))
                                            .placeholder(R.drawable.logo)
                                            .into(picture);
                                } catch (Exception e) {
                                    System.out.println("Error in uploading images");
                                }
                            }*/

                            //...

                            storename.setText(name);
                            website.setText(web);
                            productname.setText(pname);
                            productprice.setText(pprice);
                            productdetails.setText(pdetails);
                            producttags.setText(ptags);
                            producttype.setText(ptype);
                            multiautobrand.setText(brandtags_list);

                            storename.setEnabled(false);
                            website.setEnabled(false);
                            productname.setEnabled(false);
                            productprice.setEnabled(false);
                            productdetails.setEnabled(false);
                            producttags.setEnabled(false);
                            producttype.setEnabled(false);
                            multiautobrand.setEnabled(false);

                            //like code
                            lcnt = Integer.parseInt(plikecnt);


                        }

                        if (!pimages.equals("")) {
                            //silder code?????????????????????????????????????????????????????????????????
                            Hash_file_maps = new HashMap<String, String>();
                            sliderLayout = (SliderLayout) findViewById(R.id.slider);
                            String dp_path = "http://autokatta.com/mobile/Product_pics/";// + dp;

                            if (pimages.contains(",")) {
                                String[] items = pimages.split(",");
                                imageslist.add(items[0].substring(items[0].length()));
                                for (String item : items) {
                                    Hash_file_maps.put("Image-" + item, dp_path + item.replaceAll(" ", ""));
                                }
                            } else {
                                Hash_file_maps.put("Image-" + pimages, dp_path + pimages.replaceAll(" ", ""));
                            }


                /* Banner...*/
                            for (String name : Hash_file_maps.keySet()) {
                                TextSliderView textSliderView = new TextSliderView(ProductViewActivity.this);
                                textSliderView
                                        //.description(name)
                                        .image(Hash_file_maps.get(name))
                                        .setScaleType(BaseSliderView.ScaleType.Fit)
                                        .setOnSliderClickListener(this);
                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle()
                                        .putString("extra", name);
                                sliderLayout.addSlider(textSliderView);
                            }
                            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
                            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                            sliderLayout.setCustomAnimation(new DescriptionAnimation());
                            sliderLayout.setDuration(3000);
                            sliderLayout.addOnPageChangeListener(this);
                        }
                    }
                    //??????????????????????????????????????????????????????????????????????????//


                } else if (response.body() instanceof EnquiryCountResponse) {
                    EnquiryCountResponse enquiryCountResponse = (EnquiryCountResponse) response.body();
                    if (enquiryCountResponse.getSuccess() != null) {
                        String count = enquiryCountResponse.getSuccess().getEnquiryCount();
                        no_of_enquiries.setText("No.Of Enquiries:" + count);
                    }
                }

            } else {
                CustomToast.customToast(ProductViewActivity.this, getString(R.string._404));
            }
        } else {
            CustomToast.customToast(ProductViewActivity.this, getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("Product_updated_successfully")) {
                CustomToast.customToast(ProductViewActivity.this, "Product Updated");
                updatetagids();
            } else if (str.equals("success_tag_updation")) {
                CustomToast.customToast(ProductViewActivity.this, "Tags Updated");
            } else if (str.equals("success_rating_submitted")) {
                CustomToast.customToast(getApplicationContext(), "Rating Submitted");
                Intent intent = new Intent(this, ProductViewActivity.class);
                intent.putExtra("product_id", product_id);
                startActivity(intent);
            } else if (str.equals("success_rating_updated")) {
                CustomToast.customToast(getApplicationContext(), "Rating updated");
                Intent intent = new Intent(this, ProductViewActivity.class);
                intent.putExtra("product_id", product_id);
                startActivity(intent);
            } else if (str.equals("success")) {
                CustomToast.customToast(getApplicationContext(), "Product Deleted");
                Intent intent = new Intent(this, StoreViewActivity.class);
                intent.putExtra("store_id", store_id);
                startActivity(intent);
            } else if (str.equals("success_message_saved")) {
                CustomToast.customToast(getApplicationContext(), "Enquiry Sent");
            } else if (str.equals("yes")) {
                btnchat.setText("Chat");
            } else if (str.equals("no")) {
                btnchat.setText("Send Enquiry");
            }
        }

    }

    private void updatetagids() {
        ApiCall mApiCall = new ApiCall(ProductViewActivity.this, this);
        mApiCall.updateTagAssociation(product_id, "", idlist);
    }

    /*
    Check...
     */
    public void check() {
        String text = producttags.getText().toString();
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        String[] parts = text.split(",");
        if (parts.length > 5) {
            producttags.setError("You can add maximum five tags");
        }
    }

    /*
    Calculate Rating Function
     */
    public void calculate(Float r1, Float r2, Float r3) {
        total = r1 + r2 + r3;
        count = total / 3;
        overallbar.setRating(count);
    }

    /*
    Calling Function
     */
    private void call(String storecontact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + storecontact));
        try {
            startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editproduct:
                try {
                    getTags();
                    getBrandTags();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                productname.setEnabled(true);
                productprice.setEnabled(true);
                productdetails.setEnabled(true);
                producttags.setEnabled(true);
                producttype.setEnabled(true);
                producttype.requestFocus();
                multiautobrand.setEnabled(true);
                spinnerlayout.setVisibility(View.VISIBLE);
                check.setVisibility(View.VISIBLE);
                edit.setVisibility(View.GONE);
                deleteproduct.setVisibility(View.GONE);
                break;

            case R.id.checkproduct:
                finalbrandtags = "";
                uptype = producttype.getText().toString();
                upname = productname.getText().toString();
                upprice = productprice.getText().toString();
                updetails = productdetails.getText().toString();
                upcat = spinCategory.getSelectedItem().toString();
                String text = producttags.getText().toString();
                ArrayList<String> images = new ArrayList<String>();
                ArrayList<String> othertag = new ArrayList<String>();
                if (text.endsWith(","))
                    text = text.substring(0, text.length() - 1);

                text = text.trim();
                String[] parts = text.split(",");

                for (int l = 0; l < parts.length; l++) {
                    System.out.println(parts[l]);
                    tagpart = parts[l].trim();
                    if (!tagpart.equalsIgnoreCase("") && !tagpart.equalsIgnoreCase(" "))
                        images.add(tagpart);
                    if (!tagname.contains(tagpart) && !tagpart.equalsIgnoreCase("") && !tagpart.equalsIgnoreCase(" ")) {
                        othertag.add(tagpart);
                        try {
                            addOtherTags();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

                getTags();
                for (int i = 0; i < images.size(); i++) {
                    for (int j = 0; j < tagname.size(); j++) {
                        if (images.get(i).equalsIgnoreCase(tagname.get(j)))
                            idlist = idlist + "," + spnid.get(j);
                    }
                }

                if (!producttags.getText().toString().equalsIgnoreCase("") && idlist.length() > 0) {
                    idlist = idlist.substring(1);
                }
                if (tagflag) {
                    tagid = tagid.substring(1);
                    if (!idlist.equalsIgnoreCase(""))
                        idlist = idlist + "," + tagid;
                    else
                        idlist = tagid;
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

                if (uptype.equals("")) {
                    producttype.setError("Enter Product Type");
                } else if (upname.equals("")) {
                    productname.setError("Enter Product Name");
                } else if (upprice.equals("")) {
                    productprice.setError("Enter Product Price");
                } else if (updetails.equals("")) {
                    productdetails.setError("Enter Product Details");
                } else {
                    productname.setEnabled(false);
                    productprice.setEnabled(false);
                    productdetails.setEnabled(false);
                    producttags.setEnabled(false);
                    producttype.setEnabled(false);
                    multiautobrand.setEnabled(false);
                    spinnerlayout.setVisibility(View.GONE);
                    // spinCategory.setEnabled(false);

                    check.setVisibility(View.GONE);
                    edit.setVisibility(View.VISIBLE);
                    deleteproduct.setVisibility(View.VISIBLE);
                    productname.clearFocus();
                    productprice.clearFocus();
                    productdetails.clearFocus();
                    producttags.clearFocus();
                    producttype.clearFocus();
                    spinCategory.clearFocus();

                    updateProduct(product_id, upname, upprice, updetails, uptags, uptype, upimgs, upcat, finalbrandtags);
                }
                break;

            case R.id.linearreview:
                relativewritereview.setVisibility(View.VISIBLE);
                break;
            case R.id.call:

                // @Here are the list of items to be shown in the list
                if (storecontact.contains(",")) {
                    final String[] items = storecontact.split(",");
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductViewActivity.this);
                    builder.setTitle("Make your selection");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            call(items[item]);
                            dialog.dismiss();

                        }
                    }).show();
                } else {
                    call(storecontact);
                }
                break;

            case R.id.btnpost:
                reviewstring = writereview.getText().toString();
                if (reviewstring.equalsIgnoreCase("")) {
                    Toast.makeText(ProductViewActivity.this, "Please provide review first.....", Toast.LENGTH_SHORT).show();
                } else {
                    reviewTask();
                }
                relativewritereview.setVisibility(View.GONE);
                break;
            case R.id.btnchat:

                if (btnchat.getText().toString().equalsIgnoreCase("send enquiry")) {
                    ApiCall mpApicall = new ApiCall(this, this);
                    mpApicall.sendChatMessage(contact, receiver_contact, "Please send information About this", "", product_id,
                            "", "");
                } else {
                    Bundle b = new Bundle();
                    b.putString("sender", storecontact);
                    b.putString("sendername", storeowner);
                    b.putString("product_id", product_id);
                    b.putString("service_id", "");
                    b.putString("vehicle_id", "");

                    Intent intent = new Intent(ProductViewActivity.this, ChatActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }

                break;
            case R.id.profile:
//                b.putString("images", simages);
//                ServiceImageSlider fragment = new ServiceImageSlider();
//                fragment.setArguments(b);
//
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.containerView, fragment);
//                fragmentTransaction.addToBackStack("serviceimageslider");
//                fragmentTransaction.commit();
                break;
            case R.id.deleteproduct:

                if (!mConnectionDetector.isConnectedToInternet()) {
                    Toast.makeText(ProductViewActivity.this, "Please try later", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(ProductViewActivity.this)
                            .setTitle("Delete?")
                            .setMessage("Are You Sure You Want To Delete This Product?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteproduct();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                break;

            case R.id.linearlike:
                if (storecontact.contains(contact)) {
                CustomToast.customToast(getApplicationContext(),"You can't Like Your Own Store");
                }else {
                    linearlike.setVisibility(View.GONE);
                    linearunlike.setVisibility(View.VISIBLE);
                    sendLike();
                    lcnt = lcnt + 1;
                    txtlike.setText("Like(" + lcnt + ")");
                }
                break;
            case R.id.linearunlike:
                if (storecontact.contains(contact)) {
                    CustomToast.customToast(getApplicationContext(),"You can't UnLike Your Own Store");
                }else {
                    linearlike.setVisibility(View.VISIBLE);
                    linearunlike.setVisibility(View.GONE);
                    sendUnlike();
                    lcnt = lcnt - 1;
                    txtlike.setText("Like(" + lcnt + ")");
                }
                break;
            case R.id.btnfeedback:
                if (prate.equals("0")) {
                    sendproductrating();
                    System.out.println("hiiii..............send rating called");
                }
                if (!prate.equals("0")) {
                    sendupdatedproductrating();
                    System.out.println("hiiii..............send updated product rating called");
                }
                break;
            case R.id.linearshare1:

                allDetails = pname + "=" + ptype + "=" + prating + "=" + plikecnt + "=" + imageslist;

                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", allDetails).apply();
                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_product_id", product_id).apply();
                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "product").apply();

                /*Intent i = new Intent(ProductViewActivity.this, ShareWithinAppActivity.class);
                startActivity(i);*/
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right,
                        R.anim.ok_right_to_left);
                startActivity(new Intent(getApplicationContext(), ShareWithinAppActivity.class), options.toBundle());

                /*String imageshare = "";
                imageshare = "http://autokatta.com/mobile/store_profiles/" + mStoreList.get(position).getStoreImage();

                imageshare = imageshare.replaceAll(" ", "%20");
                System.out.println("image============" + imageshare);

                String timing = mStoreList.get(position).getStoreOpenTime() + "" + mStoreList.get(position).getStoreCloseTime();

                strDetailsShare = mStoreList.get(position).getName() + "=" + mStoreList.get(position).getWebsite() + "="
                        + timing + "=" + mStoreList.get(position).getWorkingDays() + "="
                        + mStoreList.get(position).getStoreType() + "=" + mStoreList.get(position).getLocation() + "="
                        + mStoreList.get(position).getStoreImage() + "=" + mStoreList.get(position).getRating() + "="
                        + mStoreList.get(position).getLikecount() + "=" + mStoreList.get(position).getFollowcount();

                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", strDetailsShare).apply();
                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_store_id", mStoreList.get(position).getId()).apply();
                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "store").apply();

                mActivity.startActivity(new Intent(mActivity, ShareWithinAppActivity.class));
                mActivity.finish();*/
                break;
            case R.id.linearshare:

                Intent intent = new Intent(Intent.ACTION_SEND);
                String imageFilePath;
                if (pimages.equalsIgnoreCase("") || pimages.equalsIgnoreCase(null) ||
                        pimages.equalsIgnoreCase("null")) {
                    imagename = "http://autokatta.com/mobile/store_profiles/" + "a.jpg";
                } else {
                    imagename = "http://autokatta.com/mobile/Product_pics/" + imageslist;
                }
                Log.e("TAG", "img : " + imagename);
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(imagename));
                request.allowScanningByMediaScanner();
                String filename = URLUtil.guessFileName(imagename, null, MimeTypeMap.getFileExtensionFromUrl(imagename));
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                Log.e("ShareImagePath :", filename);
                Log.e("TAG", "img : " + imagename);

                DownloadManager manager = (DownloadManager) getApplication()
                        .getSystemService(Context.DOWNLOAD_SERVICE);

                Log.e("TAG", "img URL: " + imagename);

                manager.enqueue(request);

                imageFilePath = "/storage/emulated/0/Download/" + filename;
                System.out.println("ImageFilePath:" + imageFilePath);

                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my product on Autokatta. Stay connected for Product and Service updates and enquiries"
                        + "\n" + "http://autokatta.com/product/" + product_id);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                startActivity(Intent.createChooser(intent, "Autokatta"));

                break;
            case R.id.btnseeall:


                b.putString("product_id", product_id);
                b.putString("action", action);
                            /*SeeAllReviews frag = new SeeAllReviews();
                            frag.setArguments(b);

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.containerView, frag);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();*/
                break;


        }

    }

    private void updateProduct(String product_id, String upname, String upprice, String updetails, String uptags, String uptype, String upimgs, String upcat, String finalbrandtags) {

        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.updateProduct(product_id, upname, upprice, updetails, "", uptype, "", upcat, finalbrandtags);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

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