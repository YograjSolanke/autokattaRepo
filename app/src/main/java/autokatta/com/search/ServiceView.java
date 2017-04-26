package autokatta.com.search;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.CategoryResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

/**
 * Created by ak-001 on 19/4/17.
 */

public class ServiceView extends Fragment implements RequestNotifier {
    View mServiceView;
    String contact;
    public static final String gal = "gallarypath";
    String gallarypath = "";
    Bundle b = new Bundle();
    String id, action, name, web, rating, receiver_contact, sname, sprice, sdetails,
            stags, stype, scategory, slikecnt, slikestatus, simages, srating, srate, srate1, srate2, srate3, store_id, storecontact, brandtags_list;
    TextView storename, website, textlike, textshare;
    EditText servicename, servicetype, serviceprice, servicedetails, writereview;
    ImageView check, edit, callme, deleteservice;
    ImageView picture;

    Button submitfeedback;
    RelativeLayout relativerate;
    LinearLayout linearlike, linearunlike, linearshare, linearshare1, linearreview;
    int lcnt;
    Button post, btnchat;
    String reviewstring = "";
    ProgressDialog pDialog;
    ArrayList<String> images = new ArrayList<String>();

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
    LinearLayout linearbtns, lineartxts;
    RelativeLayout spinnerlayout, relativewritereview;
    Spinner spinCategory;
    String simagename = "";
    int spinnerposition = 0;
    int rate;
    TextView photocount;
    final ArrayList<String> brandtagId = new ArrayList<>();
    final ArrayList<String> brandTags = new ArrayList<>();

    String tagpart = "", tagid = "";
    String idlist = "";
    boolean tagflag = false;
    ConnectionDetector mConnectionDetector;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mServiceView = inflater.inflate(R.layout.service_new_view, container, false);

        contact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", "");
        mConnectionDetector = new ConnectionDetector(getActivity());

        storename = (TextView) mServiceView.findViewById(R.id.txtstorename);
        website = (TextView) mServiceView.findViewById(R.id.txtstorewebsite);
        servicename = (EditText) mServiceView.findViewById(R.id.txtsname);
        serviceprice = (EditText) mServiceView.findViewById(R.id.txtsprice);
        servicedetails = (EditText) mServiceView.findViewById(R.id.txtsdetails);
        servicetags = (MultiAutoCompleteTextView) mServiceView.findViewById(R.id.txtstags);
        servicetype = (EditText) mServiceView.findViewById(R.id.txtstype);
        picture = (ImageView) mServiceView.findViewById(R.id.profile);
        edit = (ImageView) mServiceView.findViewById(R.id.editservice);
        check = (ImageView) mServiceView.findViewById(R.id.checkservice);
        spinCategory = (Spinner) mServiceView.findViewById(R.id.spincategory);
        spinnerlayout = (RelativeLayout) mServiceView.findViewById(R.id.linearcategory);
        callme = (ImageView) mServiceView.findViewById(R.id.call);
        textlike = (TextView) mServiceView.findViewById(R.id.txtlike);
        textshare = (TextView) mServiceView.findViewById(R.id.txtshare);
        linearlike = (LinearLayout) mServiceView.findViewById(R.id.linearlike);
        linearunlike = (LinearLayout) mServiceView.findViewById(R.id.linearunlike);
        linearshare = (LinearLayout) mServiceView.findViewById(R.id.linearshare);
        linearshare1 = (LinearLayout) mServiceView.findViewById(R.id.linearshare1);
        linearreview = (LinearLayout) mServiceView.findViewById(R.id.linearreview);
        post = (Button) mServiceView.findViewById(R.id.btnpost);
        btnchat = (Button) mServiceView.findViewById(R.id.btnchat);
        photocount = (TextView) mServiceView.findViewById(R.id.no_of_photos);
        multiautobrand = (MultiAutoCompleteTextView) mServiceView.findViewById(R.id.txtbrandptags);


        deleteservice = (ImageView) mServiceView.findViewById(R.id.deleteproduct);

        relativerate = (RelativeLayout) mServiceView.findViewById(R.id.relativerateservice);
        relativewritereview = (RelativeLayout) mServiceView.findViewById(R.id.linearwritereview);
        writereview = (EditText) mServiceView.findViewById(R.id.editwritereview);
        pricebar = (RatingBar) mServiceView.findViewById(R.id.pricebar);
        qualitybar = (RatingBar) mServiceView.findViewById(R.id.qualitybar);
        tmbar = (RatingBar) mServiceView.findViewById(R.id.tmbar);
        overallbar = (RatingBar) mServiceView.findViewById(R.id.overallbar);
        servicerating = (RatingBar) mServiceView.findViewById(R.id.servicerating);
        storerating = (RatingBar) mServiceView.findViewById(R.id.storerating);
        submitfeedback = (Button) mServiceView.findViewById(R.id.btnfeedback);

        overallbar.setEnabled(false);
        storerating.setEnabled(false);
        servicerating.setEnabled(false);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!mConnectionDetector.isConnectedToInternet()) {
                        Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    } else {
                        getCategory();
                        b = getArguments();
                        id = b.getString("sid");
                        name = b.getString("storename");
                        web = b.getString("storewebsite");
                        rating = b.getString("storerating");

                        receiver_contact = b.getString("storecontact");
                        sname = b.getString("name");
                        sprice = b.getString("price");
                        sdetails = b.getString("details");
                        stags = b.getString("tags");
                        stype = b.getString("type");
                        simages = b.getString("images");
                        srating = b.getString("srating");
                        scategory = b.getString("category");

                        slikecnt = b.getString("slikecnt");
                        slikestatus = b.getString("slikestatus");
                        srate = b.getString("srate");
                        srate1 = b.getString("srate1");
                        srate2 = b.getString("srate2");
                        srate3 = b.getString("srate3");
                        store_id = b.getString("store_id");
                        storecontact = b.getString("storecontact");
                        brandtags_list = b.getString("brandtags_list");

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


                        textlike.setText("like(" + slikecnt + ")");

                        if (contact.equals(storecontact)) {

                            edit.setVisibility(View.VISIBLE);
                            deleteservice.setVisibility(View.VISIBLE);
                            callme.setVisibility(View.GONE);
                            relativerate.setVisibility(View.GONE);
                            relativewritereview.setVisibility(View.GONE);
                            linearlike.setEnabled(false);
                            linearreview.setEnabled(false);


                        } else //if(action.equalsIgnoreCase("other"))
                        {
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

                        linearreview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                relativewritereview.setVisibility(View.VISIBLE);
                            }
                        });

                        btnchat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                /*if (storecontact.contains(contact)) {
                                    ProductSeviceVehicleMsgSender object = new ProductSeviceVehicleMsgSender();
                                    Bundle b = new Bundle();
                                    b.putString("product_id", "");
                                    b.putString("service_id", id);
                                    b.putString("vehicle_id", "");

                                    object.setArguments(b);
                                    FragmentManager fragmentManager = ctx.getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.containerView, object);
                                    fragmentTransaction.addToBackStack("chatactivity");
                                    fragmentTransaction.commit();

                                } else {

                                    String sender = "";

                                    if (storecontact.contains(",")) {
                                        String parts[] = storecontact.split(",");
                                        sender = parts[0];
                                    } else
                                        sender = storecontact;
                                    ChatActivity object = new ChatActivity();
                                    Bundle b = new Bundle();
                                    b.putString("sender", sender);
                                    b.putString("product_id", "");
                                    b.putString("service_id", id);
                                    b.putString("vehicle_id", "");

                                    object.setArguments(b);
                                    FragmentManager fragmentManager = ctx.getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.containerView, object);
                                    fragmentTransaction.addToBackStack("chatactivity");
                                    fragmentTransaction.commit();
                                }*/

                            }
                        });

                        //review functionality code

                        post.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                reviewstring = writereview.getText().toString();
                                if (reviewstring.equalsIgnoreCase("")) {
                                    Toast.makeText(getActivity(), "Please provide review first.....", Toast.LENGTH_SHORT).show();
                                } else {
                                    reviewTask();
                                }

                                relativewritereview.setVisibility(View.GONE);

                            }
                        });


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

                        try {
                            if (simages.equals("")) {
                                picture.setImageResource(R.drawable.store);
                                photocount.setText("0 Photos");
                            } else {
                                String[] parts = simages.split(",");
                                photocount.setText(parts.length + " Photos");
                                for (int l = 0; l < parts.length; l++) {
                                    images.add(parts[l]);
                                    System.out.println(parts[l]);
                                }
                                simagename = "http://autokatta.com/mobile/Service_pics/" + images.get(0);
                                simagename = simagename.replaceAll(" ", "%20");
                                try {

                                    Glide.with(getActivity())
                                            .load(simagename)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .bitmapTransform(new CropCircleTransformation(getActivity()))
                                            .placeholder(R.drawable.logo)
                                            .into(picture);

                                } catch (Exception e) {
                                    System.out.println("Error in uploading images");
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (simages.equals("")) {
                            picture.setEnabled(false);
                        }

                        picture.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                b.putString("images", simages);
                                ProductImageSlider fragment = new ProductImageSlider();
                                fragment.setArguments(b);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.search_product, fragment);
                                fragmentTransaction.addToBackStack("serviceimageslider");
                                fragmentTransaction.commit();
                            }
                        });

                        callme.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // @Here are the list of items to be shown in the list
                                if (storecontact.contains(",")) {
                                    final String[] items = storecontact.split(",");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                            }
                        });


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
                        edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    getTags();
                                    getBrandTags();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

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

                            }
                        });

                        check.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
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
                                //field validation
                                if (uptype.equals("")) {
                                    servicetype.setError("Enter Product Type");
                                } else if (upname.equals("")) {
                                    servicename.setError("Enter Product Name");
                                } else if (upprice.equals("")) {
                                    serviceprice.setError("Enter Product Price");
                                } else if (updetails.equals("")) {
                                    servicedetails.setError("Enter Product Details");
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
                                    Donetask();
                                }
                            }
                        });

                        deleteservice.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!mConnectionDetector.isConnectedToInternet()) {
                                    Toast.makeText(getActivity(), "Please try later", Toast.LENGTH_SHORT).show();
                                } else {
                                    new AlertDialog.Builder(getActivity())
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
                            }
                        });

                        //like code
                        lcnt = Integer.parseInt(slikecnt);
                        linearlike.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                linearlike.setVisibility(View.GONE);
                                linearunlike.setVisibility(View.VISIBLE);
                                sendLike();
                                lcnt = lcnt + 1;
                                textlike.setText("Like(" + lcnt + ")");
                            }
                        });
                        linearunlike.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                linearlike.setVisibility(View.VISIBLE);
                                linearunlike.setVisibility(View.GONE);
                                sendUnlike();
                                lcnt = lcnt - 1;
                                textlike.setText("Like(" + lcnt + ")");
                            }
                        });

                        imagename = "http://autokatta.com/mobile/Product_pics/autokattalogofinaltry.jpg";

                        linearshare1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                allDetails = sname + "=" + stype + "=" + srating + "=" + slikecnt + "=" + images.get(0);
                                /*shareedit.putString("sharedata", allDetails);
                                shareedit.putString("service_id", id);
                                shareedit.putString("keyword", "service");
                                shareedit.commit();

                                ShareWithinApp fr = new ShareWithinApp();

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.containerView, fr);
                                fragmentTransaction.addToBackStack("sharewithinapp");
                                fragmentTransaction.commit();*/
                            }
                        });


                        linearshare.setOnClickListener(new View.OnClickListener() {

                            Intent intent = new Intent(Intent.ACTION_SEND);
                            String imageFilePath;

                            @Override
                            public void onClick(View v) {
                                if (simages.equalsIgnoreCase("") || simages.equalsIgnoreCase(null) ||
                                        simages.equalsIgnoreCase("null")) {
                                    simagename = "http://autokatta.com/mobile/store_profiles/" + "a.jpg";
                                } else {
                                    simagename = "http://autokatta.com/mobile/Service_pics/" + images.get(0);
                                }
                                Log.e("TAG", "img : " + simagename);

                                DownloadManager.Request request = new DownloadManager.Request(
                                        Uri.parse(simagename));
                                request.allowScanningByMediaScanner();
                                String filename = URLUtil.guessFileName(simagename, null, MimeTypeMap.getFileExtensionFromUrl(simagename));
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                                Log.e("ShareImagePath :", filename);
                                Log.e("TAG", "img : " + simagename);

                                DownloadManager manager = (DownloadManager) getActivity().getApplication()
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

                            }
                        });

                        submitfeedback.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (srate.equals("0")) {
                                    sendproductrating();
                                    System.out.println("hiiiii.....send rating called");
                                }
                                if (!srate.equals("0")) {
                                    sendupdatedproductrating();
                                    System.out.println("hiiiii.....send updated service rating called");
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return mServiceView;
    }

    /*
    Delete Service
     */
    private void deleteservice() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.deleteService(id, "delete");
    }

    /*
    Ratings...
     */
    private void sendproductrating() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.sendNewrating(contact, "", id, "", String.valueOf(count), String.valueOf(pricerate), String.valueOf(qualityrate)
                , String.valueOf(tmrate), "", "", "service");
    }

    /*
    Update Ratings...
     */
    private void sendupdatedproductrating() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.sendUpdatedrating(contact, "", id, "", String.valueOf(count), String.valueOf(pricerate), String.valueOf(qualityrate)
                , String.valueOf(tmrate), "", "", "service");
    }

    /*
    Done Task...
     */
    private void Donetask() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.updateStoreService(upname, uptype, updetails, upprice, uptags, id, upcat, finalbrandtags);
    }

    /*
    Review Task...
     */
    private void reviewTask() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.postProductReview(contact, receiver_contact, id, reviewstring);
    }

    /*
    Check
     */
    public void check() {
        String text = servicetags.getText().toString();
        System.out.println("texttttttttttttttttt" + text.substring(0, text.length() - 1));
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        String[] parts = text.split(",");
        System.out.println("size of partssssssssssssssssss" + parts.length);
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
            getActivity().startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Service View Fragment\n");
        }
    }

    /*
    Get Category...
     */
    private void getCategory() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.Categories("Service");
    }

    /*
    Get Tags...
     */
    private void getTags() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall._autoGetTags("2");
    }

    /*
    Get Brand Tags...
     */
    private void getBrandTags() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getBrandTags("2");
    }

    /*
    Add Other Tags...
     */
    private void addOtherTags() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall._autoAddTags(tagpart, "2");
    }

    /*
   Add Other Brand Tags...
    */
    private void addOtherBrandTags(String brandtagpart) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.addOtherBrandTags(brandtagpart, "2");
    }

    /*
    Unlike...
     */
    private void sendUnlike() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall._autokattaProductViewUnlike(contact, receiver_contact, "6", id);
    }

    /*
    Like
     */
    private void sendLike() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall._autokattaProductView(contact, receiver_contact, "6", id);
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
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getActivity(), R.layout.addproductspinner_color, module);
                        spinCategory.setAdapter(dataadapter);
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

    }

    @Override
    public void notifyString(String str) {
        if (str.equals("success_updateStoreService")) {
            updatetagids();
        }
    }

    /*
    Update Gids...
     */

    private void updatetagids() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.updateTagAssociation(id, idlist);
    }
}