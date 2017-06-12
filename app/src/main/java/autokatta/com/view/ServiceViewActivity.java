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
import autokatta.com.response.ServiceResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 19/4/17.
 */

public class ServiceViewActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener,
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    String contact;
    Bundle b = new Bundle();
    String id, action, name, web, rating, receiver_contact, sname, sprice, sdetails, storeowner,
            stags, stype, scategory, slikecnt, slikestatus, simages, srating, srate, srate1, srate2, srate3, store_id, storecontact, brandtags_list;
    TextView storename, website, textlike, textshare;
    EditText servicename, servicetype, serviceprice, servicedetails, writereview;
    ImageView check, edit, callme, deleteservice;


    Button submitfeedback;
    RelativeLayout relativerate;
    LinearLayout linearlike, linearunlike, linearshare, linearshare1, linearreview;
    int lcnt;
    Button post, btnchat;
    String reviewstring = "";
    ArrayList<String> imageslist = new ArrayList<String>();

    final ArrayList<String> spnid = new ArrayList<String>();
    final ArrayList<String> tagname = new ArrayList<String>();

    Float pricerate = 0.0f;
    Float qualityrate = 0.0f;
    Float tmrate = 0.0f;
    Float count;
    Float total;
    RatingBar pricebar, qualitybar, tmbar, overallbar, servicerating, storerating;
    MultiAutoCompleteTextView servicetags, multiautobrand;

    //product updating variables
    String uptype, upname, upprice, updetails, uptags, upimgs, upcat, allDetails = "", imagename = "", brandtagpart = "", finalbrandtags = "";
    RelativeLayout spinnerlayout, relativewritereview;
    Spinner spinCategory;
    String simagename = "";
    TextView photocount, no_of_enquiries;
    final ArrayList<String> brandTags = new ArrayList<>();

    String tagpart = "", tagid = "";
    String idlist = "", service_id;
    boolean tagflag = false;
    ConnectionDetector mConnectionDetector;
    ApiCall mApiCall;
    SliderLayout sliderLayout;
    HashMap<String, String> Hash_file_maps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("View Services");

        mApiCall = new ApiCall(ServiceViewActivity.this, this);
        contact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");
        mConnectionDetector = new ConnectionDetector(this);

        storename = (TextView) findViewById(R.id.txtstorename);
        website = (TextView) findViewById(R.id.txtstorewebsite);
        no_of_enquiries = (TextView) findViewById(R.id.no_of_enquiries);
        servicename = (EditText) findViewById(R.id.txtsname);
        serviceprice = (EditText) findViewById(R.id.txtsprice);
        servicedetails = (EditText) findViewById(R.id.txtsdetails);
        servicetags = (MultiAutoCompleteTextView) findViewById(R.id.txtstags);
        servicetype = (EditText) findViewById(R.id.txtstype);
        edit = (ImageView) findViewById(R.id.editservice);
        check = (ImageView) findViewById(R.id.checkservice);
        spinCategory = (Spinner) findViewById(R.id.spincategory);
        spinnerlayout = (RelativeLayout) findViewById(R.id.linearcategory);
        callme = (ImageView) findViewById(R.id.call);
        textlike = (TextView) findViewById(R.id.txtlike);
        textshare = (TextView) findViewById(R.id.txtshare);
        linearlike = (LinearLayout) findViewById(R.id.linearlike);
        linearunlike = (LinearLayout) findViewById(R.id.linearunlike);
        linearshare = (LinearLayout) findViewById(R.id.linearshare);
        linearshare1 = (LinearLayout) findViewById(R.id.linearshare1);
        linearreview = (LinearLayout) findViewById(R.id.linearreview);
        post = (Button) findViewById(R.id.btnpost);
        btnchat = (Button) findViewById(R.id.btnchat);
        photocount = (TextView) findViewById(R.id.no_of_photos);
        multiautobrand = (MultiAutoCompleteTextView) findViewById(R.id.txtbrandptags);
        deleteservice = (ImageView) findViewById(R.id.deleteservice);
        relativerate = (RelativeLayout) findViewById(R.id.relativerateservice);
        relativewritereview = (RelativeLayout) findViewById(R.id.linearwritereview);
        writereview = (EditText) findViewById(R.id.editwritereview);
        pricebar = (RatingBar) findViewById(R.id.pricebar);
        qualitybar = (RatingBar) findViewById(R.id.qualitybar);
        tmbar = (RatingBar) findViewById(R.id.tmbar);
        overallbar = (RatingBar) findViewById(R.id.overallbar);
        servicerating = (RatingBar) findViewById(R.id.servicerating);
        storerating = (RatingBar) findViewById(R.id.storerating);
        submitfeedback = (Button) findViewById(R.id.btnfeedback);

        overallbar.setEnabled(false);
        storerating.setEnabled(false);
        servicerating.setEnabled(false);


        edit.setOnClickListener(this);
        check.setOnClickListener(this);
        linearreview.setOnClickListener(this);
        callme.setOnClickListener(this);
        post.setOnClickListener(this);
        btnchat.setOnClickListener(this);
        deleteservice.setOnClickListener(this);
        linearlike.setOnClickListener(this);
        linearunlike.setOnClickListener(this);
        submitfeedback.setOnClickListener(this);
        linearshare1.setOnClickListener(this);
        linearshare.setOnClickListener(this);


        service_id = getIntent().getExtras().getString("service_id");
        //service_id="115";


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setDisplayShowHomeEnabled(true);
                    }

                    if (!mConnectionDetector.isConnectedToInternet()) {
                        Toast.makeText(ServiceViewActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    } else {
                        getCategory();
                        getServiceData(service_id, contact);
                        getNoOfEnquiryCount(service_id, contact);


                        servicetags.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                        multiautobrand.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

                        servicetags.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                                        (i == KeyEvent.KEYCODE_ENTER)) {
                                    servicetags.setText(servicetags.getText().toString() + ",");
                                    servicetags.setSelection(servicetags.getText().toString().length());
                                    check();
                                    return true;
                                }
                                return false;
                            }
                        });

                        servicetags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                check();
                            }
                        });
                        servicetags.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View view, boolean b) {
                                if (!b && !servicetags.getText().toString().equalsIgnoreCase("")) {
                                    check();
                                }
                            }
                        });

                        overallbar.setEnabled(false);
                        pricebar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                                pricerate = v;
                                calculate(pricerate, qualityrate, tmrate);
                            }
                        });

                        qualitybar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                                qualityrate = v;
                                calculate(pricerate, qualityrate, tmrate);
                            }
                        });

                        tmbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                                tmrate = v;
                                calculate(pricerate, qualityrate, tmrate);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getNoOfEnquiryCount(String service_id, String contact) {
        ApiCall mApicall = new ApiCall(this, this);
        mApicall.getEnquiryCount(contact, "", service_id, "");

    }

    private void getServiceData(String service_id, String contact) {
        ApiCall mApicall = new ApiCall(this, this);
        mApicall.getServiceDetails(service_id, contact);
    }

    /*
    Delete Service
     */
    private void deleteservice() {
        ApiCall mApiCall = new ApiCall(ServiceViewActivity.this, this);
        mApiCall.deleteService(service_id, "delete");
    }

    /*
    Ratings...
     */
    private void sendproductrating() {
        ApiCall mApiCall = new ApiCall(ServiceViewActivity.this, this);
        mApiCall.sendNewrating(contact, "", "", service_id, String.valueOf(count), String.valueOf(pricerate), String.valueOf(qualityrate)
                , String.valueOf(tmrate), "", "", "service");
    }

    /*
    Update Ratings...
     */
    private void sendupdatedproductrating() {
        ApiCall mApiCall = new ApiCall(ServiceViewActivity.this, this);
        mApiCall.sendUpdatedrating(contact, "", "", service_id, String.valueOf(count), String.valueOf(pricerate), String.valueOf(qualityrate)
                , String.valueOf(tmrate), "", "", "service");
    }

    /*
    Review Task...
     */
    private void reviewTask() {
        ApiCall mApiCall = new ApiCall(ServiceViewActivity.this, this);
        mApiCall.postProductReview(contact, receiver_contact, service_id, reviewstring);
    }

    /*
    Check
     */
    public void check() {
        String text = servicetags.getText().toString();
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        String[] parts = text.split(",");
        if (parts.length > 5) {
            servicetags.setError("You can add maximum five tags");
        }
    }

    //calculate rating fuction
    public void calculate(Float r1, Float r2, Float r3) {
        total = r1 + r2 + r3;
        count = total / 3;
        overallbar.setRating(count);
    }

    //Calling Function
    private void call(String storecontact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + storecontact));
        try {
            startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Service View Fragment\n");
        }
    }

    /*
    Get Category...
     */
    private void getCategory() {
        ApiCall mApiCall = new ApiCall(ServiceViewActivity.this, this);
        mApiCall.Categories("Service");
    }

    /*
    Get Tags...
     */
    private void getTags() {
        ApiCall mApiCall = new ApiCall(ServiceViewActivity.this, this);
        mApiCall._autoGetTags("2");
    }

    /*
    Get Brand Tags...
     */
    private void getBrandTags() {
        ApiCall mApiCall = new ApiCall(ServiceViewActivity.this, this);
        mApiCall.getBrandTags("2");
    }

    /*
    Add Other Tags...
     */
    private void addOtherTags() {
        ApiCall mApiCall = new ApiCall(ServiceViewActivity.this, this);
        mApiCall._autoAddTags(tagpart, "2");
    }

    /*
   Add Other Brand Tags...
    */
    private void addOtherBrandTags(String brandtagpart) {
        ApiCall mApiCall = new ApiCall(ServiceViewActivity.this, this);
        mApiCall.addOtherBrandTags(brandtagpart, "2");
    }

    /*
    Unlike...
     */
    private void sendUnlike() {
        ApiCall mApiCall = new ApiCall(ServiceViewActivity.this, this);
        mApiCall._autokattaServiceViewUnlike(contact, receiver_contact, "6", service_id);
    }

    /*
    Like
     */
    private void sendLike() {
        ApiCall mApiCall = new ApiCall(ServiceViewActivity.this, this);
        mApiCall._autokattaServiceView(contact, receiver_contact, "6", service_id);
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
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(ServiceViewActivity.this, R.layout.addproductspinner_color, module);
                        spinCategory.setAdapter(dataadapter);
                    } else
                        CustomToast.customToast(ServiceViewActivity.this, getString(R.string.no_response));
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
                        servicetags.setAdapter(dataadapter);
                    }
                } else if (response.body() instanceof OtherBrandTagAddedResponse) {
                    CustomToast.customToast(ServiceViewActivity.this, "Brand Tag added successfully");
                } else if (response.body() instanceof OtherTagAddedResponse) {
                    CustomToast.customToast(ServiceViewActivity.this, "Other Tag added successfully");
                    tagid = tagid + "," + ((OtherTagAddedResponse) response.body()).getSuccess().getTagID().toString();
                    tagflag = true;
                } else if (response.body() instanceof ServiceResponse) {
                    ServiceResponse serviceresponse = (ServiceResponse) response.body();
                    if (!serviceresponse.getSuccess().isEmpty()) {
                        for (ServiceResponse.Success success : serviceresponse.getSuccess()) {
                            name = success.getStoreName();
                            web = success.getStoreWebsite();
                            rating = success.getStoreRating();
                            receiver_contact = success.getStoreContact();
                            sname = success.getName();
                            sprice = success.getPrice();
                            sdetails = success.getDetails();
                            stags = success.getServicetags();
                            stype = success.getType();
                            simages = success.getImages();
                            srating = success.getServicerating();
                            scategory = success.getCategory();

                            slikecnt = success.getServicelikecount();
                            slikestatus = success.getServicelikestatus();
                            srate = success.getSrate();
                            srate1 = success.getSrate1();
                            srate2 = success.getSrate2();
                            srate3 = success.getSrate3();
                            store_id = success.getStoreId();
                            storecontact = success.getStoreContact();
                            storeowner = success.getStoreOwner();
                            brandtags_list = success.getBrandtags();

                            getChatEnquiryStatus(contact, receiver_contact, service_id);
                            textlike.setText("like(" + slikecnt + ")");
                            if (storecontact.contains(contact)) {
                                btnchat.setVisibility(View.GONE);
                                no_of_enquiries.setVisibility(View.VISIBLE);
                                edit.setVisibility(View.VISIBLE);
                                deleteservice.setVisibility(View.VISIBLE);
                                callme.setVisibility(View.GONE);
                                relativerate.setVisibility(View.GONE);
                                relativewritereview.setVisibility(View.GONE);
                                linearlike.setEnabled(false);
                                linearreview.setEnabled(false);

                            } else {
                                callme.setVisibility(View.VISIBLE);
                                relativerate.setVisibility(View.VISIBLE);
                                edit.setVisibility(View.GONE);
                                deleteservice.setVisibility(View.GONE);

                                if (slikestatus.equals("yes")) {
                                    linearlike.setVisibility(View.GONE);
                                    linearunlike.setVisibility(View.VISIBLE);
                                } else if (slikestatus.equalsIgnoreCase("no")) {
                                    linearlike.setVisibility(View.VISIBLE);
                                    linearunlike.setVisibility(View.GONE);
                                }
                            }

                            storename.setText(name);
                            website.setText(web);
                            servicename.setText(sname);
                            serviceprice.setText(sprice);
                            servicedetails.setText(sdetails);
                            servicetags.setText(stags);
                            servicetype.setText(stype);
                            multiautobrand.setText(brandtags_list);

                            storename.setEnabled(false);
                            website.setEnabled(false);
                            servicename.setEnabled(false);
                            serviceprice.setEnabled(false);
                            servicedetails.setEnabled(false);
                            servicetags.setEnabled(false);
                            servicetype.setEnabled(false);
                            multiautobrand.setEnabled(false);


                            if (!simages.equals("")) {
                                //silder code?????????????????????????????????????????????????????????????????
                                Hash_file_maps = new HashMap<String, String>();
                                sliderLayout = (SliderLayout) findViewById(R.id.slider);
                                String dp_path = "http://autokatta.com/mobile/Service_pics/";// + dp;

                                if (simages.contains(",")) {
                                    String[] items = simages.split(",");
                                    imageslist.add(items[0].substring(items[0].length()));
                                    for (String item : items) {
                                        Hash_file_maps.put("Image-" + item, dp_path + item.replaceAll(" ", ""));
                                    }
                                } else {
                                    Hash_file_maps.put("Image-" + simages, dp_path + simages.replaceAll(" ", ""));
                                }

                                for (String name : Hash_file_maps.keySet()) {
                                    TextSliderView textSliderView = new TextSliderView(ServiceViewActivity.this);
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


                            //***************************setting previous rating*******************************
                            if (!srate.equals("0")) {
                                overallbar.setRating(Float.parseFloat(srate));
                            }
                            if (!srate1.equals("0")) {
                                pricebar.setRating(Float.parseFloat(srate1));
                            }
                            if (!srate2.equals("0")) {
                                qualitybar.setRating(Float.parseFloat(srate2));
                            }
                            if (!srate3.equals("0")) {
                                tmbar.setRating(Float.parseFloat(srate3));
                            }

                            if (!rating.equals("null")) {
                                storerating.setRating(Float.parseFloat(rating));
                            }

                            //rating conditions for service
                            if (!srating.equals("null")) {
                                servicerating.setRating(Float.parseFloat(srating));
                            }
                            //like code
                            lcnt = Integer.parseInt(slikecnt);
                        }
                    }
                } else if (response.body() instanceof EnquiryCountResponse) {
                    EnquiryCountResponse enquiryCountResponse = (EnquiryCountResponse) response.body();
                    if (enquiryCountResponse.getSuccess() != null) {
                        String count = enquiryCountResponse.getSuccess().getEnquiryCount();
                        no_of_enquiries.setText("No.Of Enquiries:" + count);
                    }
                }
            } else {
                CustomToast.customToast(ServiceViewActivity.this, getString(R.string._404));
            }
        } else {
            CustomToast.customToast(ServiceViewActivity.this, getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("Service_updated_successfully")) {
                CustomToast.customToast(ServiceViewActivity.this, "Service Updated");
                updatetagids();
            } else if (str.equals("success_tag_updation")) {
                CustomToast.customToast(ServiceViewActivity.this, "Tags Updated");
            } else if (str.equals("success_rating_submitted")) {
                CustomToast.customToast(getApplicationContext(), "Rating Submitted");
                Intent intent = new Intent(this, ServiceViewActivity.class);
                intent.putExtra("service_id", service_id);
                startActivity(intent);

            } else if (str.equals("success_rating_updated")) {
                CustomToast.customToast(getApplicationContext(), "Rating updated");
                Intent intent = new Intent(this, ServiceViewActivity.class);
                intent.putExtra("service_id", service_id);
                startActivity(intent);

            } else if (str.equals("success")) {
                CustomToast.customToast(getApplicationContext(), "Service Deleted");
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


    private void getChatEnquiryStatus(String contact, String receiver_contact, String service_id) {

        ApiCall mApicall = new ApiCall(this, this);
        mApicall.getChatEnquiryStatus(contact, receiver_contact, "", service_id, "");
    }

    /*
    Update Gids...
     */

    private void updatetagids() {
        ApiCall mApiCall = new ApiCall(ServiceViewActivity.this, this);
        mApiCall.updateTagAssociation("", id, idlist);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editservice:
                getTags();
                getBrandTags();
                servicename.setEnabled(true);
                serviceprice.setEnabled(true);
                servicedetails.setEnabled(true);
                servicetags.setEnabled(true);
                servicetype.setEnabled(true);
                servicetype.requestFocus();
                multiautobrand.setEnabled(true);
                spinnerlayout.setVisibility(View.VISIBLE);

                check.setVisibility(View.VISIBLE);
                edit.setVisibility(View.GONE);
                deleteservice.setVisibility(View.GONE);
                break;

            case R.id.checkservice:
                finalbrandtags = "";


                uptype = servicetype.getText().toString();
                upname = servicename.getText().toString();
                upprice = serviceprice.getText().toString();
                updetails = servicedetails.getText().toString();
                upcat = spinCategory.getSelectedItem().toString();

                String text = servicetags.getText().toString();
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
                try {
                    getTags();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < images.size(); i++) {
                    for (int j = 0; j < tagname.size(); j++) {
                        if (images.get(i).equalsIgnoreCase(tagname.get(j)))
                            idlist = idlist + "," + spnid.get(j);
                    }
                }

                if (!servicetags.getText().toString().equalsIgnoreCase("") && idlist.length() > 0) {
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
                //field validation
                if (uptype.equals("")||uptype.startsWith(" ")&&uptype.endsWith(" ")) {
                    servicetype.setError("Enter service Type");
                } else if (upname.equals("")||upname.startsWith(" ")&&upname.endsWith(" ")) {
                    servicename.setError("Enter Service Name");
                } else if (upprice.equals("")||upprice.startsWith(" ")&&upprice.endsWith(" ")) {
                    serviceprice.setError("Enter Service Price");
                } else if (updetails.equals("")||updetails.startsWith(" ")&&updetails.endsWith(" ")) {
                    servicedetails.setError("Enter Service Details");
                } else {
                    servicename.setEnabled(false);
                    serviceprice.setEnabled(false);
                    servicedetails.setEnabled(false);
                    servicetags.setEnabled(false);
                    servicetype.setEnabled(false);
                    multiautobrand.setEnabled(false);
                    spinnerlayout.setVisibility(View.GONE);
                    check.setVisibility(View.GONE);
                    edit.setVisibility(View.VISIBLE);
                    deleteservice.setVisibility(View.VISIBLE);
                    servicename.clearFocus();
                    serviceprice.clearFocus();
                    servicedetails.clearFocus();
                    servicetags.clearFocus();
                    servicetype.clearFocus();
                    spinCategory.clearFocus();
                    updateService(service_id, upname, upprice, updetails, "", uptype, "", upcat, finalbrandtags);
                }
                break;

            case R.id.linearreview:
                relativewritereview.setVisibility(View.VISIBLE);
                break;

            case R.id.call:

                // @Here are the list of items to be shown in the list
                if (storecontact.contains(",")) {
                    final String[] items = storecontact.split(",");
                    AlertDialog.Builder builder = new AlertDialog.Builder(ServiceViewActivity.this);
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
                    Toast.makeText(ServiceViewActivity.this, "Please provide review first.....", Toast.LENGTH_SHORT).show();
                } else {
                    reviewTask();
                }
                relativewritereview.setVisibility(View.GONE);
                break;

            case R.id.btnchat:
                if (btnchat.getText().toString().equalsIgnoreCase("send enquiry")) {
                    ApiCall mpApicall = new ApiCall(this, this);
                    mpApicall.sendChatMessage(contact, receiver_contact, "Please send information About this", "", "",
                            service_id, "");
                } else {

                    Bundle b = new Bundle();
                    b.putString("sender", storecontact);
                    b.putString("sendername", storeowner);
                    b.putString("product_id", "");
                    b.putString("service_id", service_id);
                    b.putString("vehicle_id", "");

                    Intent intent = new Intent(ServiceViewActivity.this, ChatActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }


                break;

            case R.id.profile:
                break;

            case R.id.deleteservice:
                if (!mConnectionDetector.isConnectedToInternet()) {
                    Toast.makeText(ServiceViewActivity.this, "Please try later", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(ServiceViewActivity.this)
                            .setTitle("Delete?")
                            .setMessage("Are You Sure You Want To Delete This Service?")

                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteservice();
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
                linearlike.setVisibility(View.GONE);
                linearunlike.setVisibility(View.VISIBLE);
                sendLike();
                lcnt = lcnt + 1;
                textlike.setText("Like(" + lcnt + ")");
                break;

            case R.id.linearunlike:
                linearlike.setVisibility(View.VISIBLE);
                linearunlike.setVisibility(View.GONE);
                sendUnlike();
                lcnt = lcnt - 1;
                textlike.setText("Like(" + lcnt + ")");
                break;

            case R.id.linearshare1:
                allDetails = sname + "=" + stype + "=" + srating + "=" + slikecnt + "=" + imageslist;
                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", allDetails).apply();
                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_service_id", service_id).apply();
                getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "service").apply();

                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right,
                        R.anim.ok_right_to_left);
                startActivity(new Intent(getApplicationContext(), ShareWithinAppActivity.class), options.toBundle());
                break;

            case R.id.linearshare:
                Intent intent = new Intent(Intent.ACTION_SEND);
                String imageFilePath;

                if (simages.equalsIgnoreCase("") || simages.equalsIgnoreCase(null) ||
                        simages.equalsIgnoreCase("null")) {
                    simagename = "http://autokatta.com/mobile/store_profiles/" + "a.jpg";
                } else {
                    simagename = "http://autokatta.com/mobile/Service_pics/" + imageslist;
                }
                Log.e("TAG", "img : " + simagename);

                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(simagename));
                request.allowScanningByMediaScanner();
                String filename = URLUtil.guessFileName(simagename, null, MimeTypeMap.getFileExtensionFromUrl(simagename));
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                Log.e("ShareImagePath :", filename);
                Log.e("TAG", "img : " + simagename);

                DownloadManager manager = (DownloadManager) getApplication()
                        .getSystemService(Context.DOWNLOAD_SERVICE);

                Log.e("TAG", "img URL: " + simagename);

                manager.enqueue(request);

                imageFilePath = "/storage/emulated/0/Download/" + filename;
                System.out.println("ImageFilePath:" + imageFilePath);

                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my Services on Autokatta. Stay connected for Product and Service updates and enquiries"
                        + "\n" + "http://autokatta.com/service/" + id);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                startActivity(Intent.createChooser(intent, "Autokatta"));
                break;

            case R.id.btnfeedback:
                if (srate.equals("0")) {
                    sendproductrating();
                }
                if (!srate.equals("0")) {
                    sendupdatedproductrating();
                }
                break;
        }
    }


    private void updateService(String service_id, String upname, String upprice, String updetails, String uptags,
                               String uptype, String upimgs, String upcat, String finalbrandtags) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.updateService(service_id, upname, upprice, updetails, "", uptype, "", upcat, finalbrandtags);
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