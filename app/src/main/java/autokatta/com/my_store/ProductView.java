package autokatta.com.my_store;


import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import autokatta.com.response.BrandsTagResponse;
import autokatta.com.response.CategoryResponse;
import autokatta.com.response.GetTagsResponse;
import autokatta.com.search.ProductImageSlider;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

/**
 * Created by ak-001 on 18/4/17.
 */
public class ProductView extends Fragment implements RequestNotifier {
    View mProductView;
    Button post, btnchat;
    Spinner spinCategory;
    TextView storename, website, txtlike, txtshare, txtreview;
    EditText productname, productprice, productdetails, producttype, writereview;
    Bundle b = new Bundle();
    Bundle sb = new Bundle();
    //variables for getting data through bundle form adapter
    String name, web, rating, pname, pprice, pdetails, ptags, ptype, plikecnt, psharecnt,
            pimages, plikestatus, action, id, pcategory, str_category, prating, receiver_contact, prate, prate1, prate2, prate3, brandtags_list;
    ImageView picture, edit, check, callme, deleteproduct;
    ArrayList<String> images = new ArrayList<String>();
    ProgressDialog pDialog;
    String result, allDetails;
    final ArrayList<String> spnid = new ArrayList<String>();
    final ArrayList<String> tagname = new ArrayList<String>();

    String imagename = "", reviewstring = "", pimagename = "", brandtagpart = "", finalbrandtags = "";

    int spinnerposition = 0;
    int rate;
    int lcnt;
    LinearLayout linearlike, linearshare, linearshare1, linearunlike, linearreview;
    final ArrayList<String> brandtagId = new ArrayList<>();
    final ArrayList<String> brandTags = new ArrayList<>();

    // SharedPreferences settings;
    String contact;
    Float pricerate = 0.0f;
    Float qualityrate = 0.0f;
    Float stockrate = 0.0f;
    Float count;
    Float total;
    Button submitfeedback, viewfeedback, seellreview;
    // LinearLayout linear1,linear2;
    MultiAutoCompleteTextView producttags, multiautobrand;
    RelativeLayout relativerate;
    RatingBar pricebar, qualitybar, stockbar, overallbar, productrating, storerating;
    String store_id, storecontact;

    //product updating variables
    String uptype, upname, upprice, updetails, uptags, upimgs, upcat, upbrandlist;
    LinearLayout linearbtns, lineartxts;
    RelativeLayout spinnerlayout, relativewritereview;
    //shared pref
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    public static final String gal = "gallarypath";
    String gallarypath = "";
    TextView photocount;


    String tagpart = "", tagid = "";
    String idlist = "", product_id;
    boolean tagflag = false;
    ConnectionDetector mConnectionDetector;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProductView = inflater.inflate(R.layout.product_new_view, container, false);
        contact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", "");

        mConnectionDetector = new ConnectionDetector(getActivity());
        storename = (TextView) mProductView.findViewById(R.id.txtstorename);
        website = (TextView) mProductView.findViewById(R.id.txtstorewebsite);
        productname = (EditText) mProductView.findViewById(R.id.txtpname);
        productprice = (EditText) mProductView.findViewById(R.id.txtpprice);
        productdetails = (EditText) mProductView.findViewById(R.id.txtpdetails);
        producttags = (MultiAutoCompleteTextView) mProductView.findViewById(R.id.txtptags);
        producttype = (EditText) mProductView.findViewById(R.id.txtptype);
        picture = (ImageView) mProductView.findViewById(R.id.profile);
        edit = (ImageView) mProductView.findViewById(R.id.editproduct);
        check = (ImageView) mProductView.findViewById(R.id.checkproduct);

        txtlike = (TextView) mProductView.findViewById(R.id.txtlike);
        txtshare = (TextView) mProductView.findViewById(R.id.txtshare);
        txtreview = (TextView) mProductView.findViewById(R.id.txtreview);
        callme = (ImageView) mProductView.findViewById(R.id.call);
        writereview = (EditText) mProductView.findViewById(R.id.editwritereview);
        post = (Button) mProductView.findViewById(R.id.btnpost);
        btnchat = (Button) mProductView.findViewById(R.id.btnchat);
        seellreview = (Button) mProductView.findViewById(R.id.btnseeall);
        linearlike = (LinearLayout) mProductView.findViewById(R.id.linearlike);
        linearunlike = (LinearLayout) mProductView.findViewById(R.id.linearunlike);
        linearshare = (LinearLayout) mProductView.findViewById(R.id.linearshare);
        linearshare1 = (LinearLayout) mProductView.findViewById(R.id.linearshare1);
        linearreview = (LinearLayout) mProductView.findViewById(R.id.linearreview);

        spinCategory = (Spinner) mProductView.findViewById(R.id.spincategory);
        spinnerlayout = (RelativeLayout) mProductView.findViewById(R.id.linearcategory);

        deleteproduct = (ImageView) mProductView.findViewById(R.id.deleteproduct);
        relativerate = (RelativeLayout) mProductView.findViewById(R.id.relativerateproduct);
        lineartxts = (LinearLayout) mProductView.findViewById(R.id.lineartxts);
        relativewritereview = (RelativeLayout) mProductView.findViewById(R.id.linearwritereview);
        pricebar = (RatingBar) mProductView.findViewById(R.id.pricebar);
        qualitybar = (RatingBar) mProductView.findViewById(R.id.qualitybar);
        stockbar = (RatingBar) mProductView.findViewById(R.id.stockbar);
        productrating = (RatingBar) mProductView.findViewById(R.id.productrating);
        storerating = (RatingBar) mProductView.findViewById(R.id.storerating);
        overallbar = (RatingBar) mProductView.findViewById(R.id.overallbar);
        submitfeedback = (Button) mProductView.findViewById(R.id.btnfeedback);
        photocount = (TextView) mProductView.findViewById(R.id.no_of_photos);
        multiautobrand = (MultiAutoCompleteTextView) mProductView.findViewById(R.id.txtbrandptags);

        overallbar.setEnabled(false);
        storerating.setEnabled(false);
        productrating.setEnabled(false);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getCategory();

                    b = getArguments();
                    id = b.getString("pid");
                    name = b.getString("storename");
                    web = b.getString("storewebsite");
                    rating = b.getString("storerating");
                    receiver_contact = b.getString("storecontact");
                    pname = b.getString("name");
                    pprice = b.getString("price");
                    pdetails = b.getString("details");
                    ptags = b.getString("tags");
                    ptype = b.getString("type");
                    pimages = b.getString("images");
                    prating = b.getString("prating");
                    pcategory = b.getString("category");
                    plikestatus = b.getString("likestatus");
                    plikecnt = b.getString("plikecnt");
                    psharecnt = b.getString("psharecnt");
                    prate = b.getString("prate");
                    prate1 = b.getString("prate1");
                    prate2 = b.getString("prate2");
                    prate3 = b.getString("prate3");
                    store_id = b.getString("store_id");
                    storecontact = b.getString("storecontact");
                    brandtags_list = b.getString("brandtags_list");

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

                    txtlike.setText("Like(" + plikecnt + ")");

                    if (storecontact.contains(contact)) {
                        edit.setVisibility(View.VISIBLE);
                        deleteproduct.setVisibility(View.VISIBLE);
                        callme.setVisibility(View.GONE);
                        relativerate.setVisibility(View.GONE);
                        relativewritereview.setVisibility(View.GONE);
                        linearlike.setEnabled(false);
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

                    linearreview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            relativewritereview.setVisibility(View.VISIBLE);
                        }
                    });

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

                    //calling functionality code
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

                    btnchat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /*if (storecontact.contains(contact)) {
                                ProductSeviceVehicleMsgSender object = new ProductSeviceVehicleMsgSender();
                                Bundle b = new Bundle();
                                b.putString("product_id", id);
                                b.putString("service_id", "");
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
                                b.putString("product_id", id);
                                b.putString("service_id", "");
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
                        picture.setImageResource(R.drawable.store);
                        photocount.setText("0 Photos");
                    } else {
                        String[] parts = pimages.split(",");
                        photocount.setText(parts.length + " Photos");
                        for (int l = 0; l < parts.length; l++) {
                            images.add(parts[l]);
                            System.out.println(parts[l]);
                        }
                        pimagename = "http://autokatta.com/mobile/Product_pics/" + images.get(0);
                        pimagename = pimagename.replaceAll(" ", "%20");
                        try {
                            Glide.with(getActivity())
                                    .load(pimagename)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .bitmapTransform(new CropCircleTransformation(getActivity()))
                                    .placeholder(R.drawable.logo)
                                    .into(picture);
                        } catch (Exception e) {
                            System.out.println("Error in uploading images");
                        }
                    }

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

                    if (pimages.equals("")) {
                        picture.setEnabled(false);
                    }

                    picture.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            b.putString("images", pimages);
                            ProductImageSlider fragment = new ProductImageSlider();
                            fragment.setArguments(b);

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.search_product, fragment);
                            fragmentTransaction.addToBackStack("productimageslider");
                            fragmentTransaction.commit();
                        }
                    });

                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
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
                        }
                    });

                    check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            gallarypath = prefs.getString("upgallary", "");
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
                               /* UpdateProductDetails productRequest = new UpdateProductDetails(setProductData(), ProductView.this);
                                productRequest.sendRequest();*/
                            }

                        }
                    });

                    deleteproduct.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!mConnectionDetector.isConnectedToInternet()) {
                                Toast.makeText(getActivity(), "Please try later", Toast.LENGTH_SHORT).show();
                            } else {
                                new AlertDialog.Builder(getActivity())
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
                        }
                    });

                    //like code
                    lcnt = Integer.parseInt(plikecnt);
                    linearlike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            linearlike.setVisibility(View.GONE);
                            linearunlike.setVisibility(View.VISIBLE);
                            sendLike();
                            lcnt = lcnt + 1;
                            txtlike.setText("Like(" + lcnt + ")");
                        }
                    });
                    linearunlike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            linearlike.setVisibility(View.VISIBLE);
                            linearunlike.setVisibility(View.GONE);
                            sendUnlike();
                            lcnt = lcnt - 1;
                            txtlike.setText("Like(" + lcnt + ")");
                        }
                    });

                    submitfeedback.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (prate.equals("0")) {
                                sendproductrating();
                                System.out.println("hiiii..............send rating called");
                            }
                            if (!prate.equals("0")) {
                                sendupdatedproductrating();
                                System.out.println("hiiii..............send updated product rating called");
                            }
                        }
                    });

                    if (pimages.equals("")) {
                        imagename = "http://autokatta.com/mobile/Product_pics/autokattalogofinaltry.jpg";
                    } else {
                        imagename = "http://autokatta.com/mobile/Product_pics/" + images.get(0);
                    }

                    linearshare1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            allDetails = pname + "=" + ptype + "=" + prating + "=" + plikecnt + "=" + images.get(0);

                            /*shareedit.putString("sharedata", allDetails);
                            shareedit.putString("product_id", id);
                            shareedit.putString("keyword", "product");

                            shareedit.commit();*/
                            /*ShareWithinApp fr = new ShareWithinApp();
                            // fr.setArguments(b);
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
                            if (pimages.equalsIgnoreCase("") || pimages.equalsIgnoreCase(null) ||
                                    pimages.equalsIgnoreCase("null")) {
                                imagename = "http://autokatta.com/mobile/store_profiles/" + "a.jpg";
                            } else {
                                pimagename = "http://autokatta.com/mobile/Product_pics/" + images.get(0);
                            }
                            Log.e("TAG", "img : " + imagename);
                            DownloadManager.Request request = new DownloadManager.Request(
                                    Uri.parse(imagename));
                            request.allowScanningByMediaScanner();
                            String filename = URLUtil.guessFileName(imagename, null, MimeTypeMap.getFileExtensionFromUrl(imagename));
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                            Log.e("ShareImagePath :", filename);
                            Log.e("TAG", "img : " + imagename);

                            DownloadManager manager = (DownloadManager) getActivity().getApplication()
                                    .getSystemService(Context.DOWNLOAD_SERVICE);

                            Log.e("TAG", "img URL: " + imagename);

                            manager.enqueue(request);

                            imageFilePath = "/storage/emulated/0/Download/" + filename;
                            System.out.println("ImageFilePath:" + imageFilePath);

                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my product on Autokatta. Stay connected for Product and Service updates and enquiries"
                                    + "\n" + "http://autokatta.com/product/" + id);
                            intent.setType("image/jpeg");
                            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                            startActivity(Intent.createChooser(intent, "Autokatta"));
                        }
                    });

                    //calling fragment which contains list of all reviews
                    seellreview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            b.putString("product_id", id);
                            b.putString("action", action);
                            /*SeeAllReviews frag = new SeeAllReviews();
                            frag.setArguments(b);

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.containerView, frag);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();*/
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return mProductView;
    }

    /*
    Update Ratings...
     */
    private void sendupdatedproductrating() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall._autokattaProductUpdateRatings(contact, id, String.valueOf(count), String.valueOf(pricerate), String.valueOf(qualityrate)
                , String.valueOf(stockrate), "product");
    }

    /*
    Ratings...
     */
    private void sendproductrating() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall._autokattaProductNewRatings(contact, id, String.valueOf(count), String.valueOf(pricerate), String.valueOf(qualityrate)
                , String.valueOf(stockrate), "product");
    }

    /*
    Unlike...
     */
    private void sendUnlike() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall._autokattaProductViewUnlike(contact, receiver_contact, "5", product_id);
    }

    /*
    Like
     */
    private void sendLike() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall._autokattaProductView(contact, receiver_contact, "5", id);
    }

    /*
    Delete Product
     */
    private void deleteproduct() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.deleteProduct(id, "delete");
    }

    /*
    Add Other Brand Tags...
     */
    private void addOtherBrandTags(String brandtagpart) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.addOtherBrandTags(brandtagpart, "1");
    }

    /*
    Add Other Tags...
     */
    private void addOtherTags() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall._autoAddTags(tagpart, "1");
    }

    /*
    Get Tags...
     */
    private void getTags() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall._autoGetTags("1");
    }

    /*
    Get Brand Tags...
     */
    private void getBrandTags() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getBrandTags("1");
    }

    /*
    Review Task...
     */
    private void reviewTask() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.postProductReview(contact, receiver_contact, id, reviewstring);
    }

    /*
    Get Module...
     */
    private void getCategory() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
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
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getActivity(), R.layout.addproductspinner_color, module);
                        spinCategory.setAdapter(dataadapter);
                    } else
                        CustomToast.customToast(getActivity(), getString(R.string.no_response));
                } else if (response.body() instanceof BrandsTagResponse) {
                    BrandsTagResponse brandsTagResponse = (BrandsTagResponse) response.body();
                    List<String> brands = new ArrayList<>();
                    if (!brandsTagResponse.getSuccess().isEmpty()) {
                        for (BrandsTagResponse.Success success : brandsTagResponse.getSuccess()) {
                            brands.add(success.getTag());
                        }
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getActivity(), R.layout.addproductspinner_color, brands);
                        multiautobrand.setAdapter(dataadapter);
                    }
                } else if (response.body() instanceof GetTagsResponse) {
                    GetTagsResponse tagsResponse = (GetTagsResponse) response.body();
                    List<String> tags = new ArrayList<>();
                    if (!tagsResponse.getSuccess().isEmpty()) {
                        for (GetTagsResponse.Success success : tagsResponse.getSuccess()) {
                            tags.add(success.getTag());
                        }
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getActivity(), R.layout.addproductspinner_color, tags);
                        producttags.setAdapter(dataadapter);
                    }
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
        Log.i("Tas", "->" + str);
    }

    /*
    Check...
     */
    public void check() {
        String text = producttags.getText().toString();
        System.out.println("texttttttttttttttttt" + text.substring(0, text.length() - 1));
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        String[] parts = text.split(",");
        System.out.println("size of partssssssssssssssssss" + parts.length);
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
        System.out.println("calling started");
        try {
            getActivity().startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Product View Fragment\n");
        }
    }
}