package autokatta.com.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BuyerResponse;
import autokatta.com.view.OtherProfile;
import retrofit2.Response;

/**
 * Created by ak-003 on 21/4/17.
 */

public class BuyerNotificationFragment extends Fragment implements RequestNotifier {

    public List<BuyerResponse.Success.Vehicle> mainList = new ArrayList<>();
    public List<BuyerResponse.Success.Found> childlist;
    private LinearLayout mLinearListView;
    boolean isFirstViewClick[];
    String myContact;
    String recieverContact;
    LinearLayout mLinearScrollSecond[];
    ApiCall mApiCall;
    View mBuyerView;
    boolean _hasLoadedOnce = false;
    private ProgressDialog dialog;

    public BuyerNotificationFragment() {
        //Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBuyerView = inflater.inflate(R.layout.example, container, false);
        return mBuyerView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mApiCall = new ApiCall(getActivity(), this);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Loading Buyer...");

                mLinearListView = (LinearLayout) mBuyerView.findViewById(R.id.linear_ListView);
                myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).
                        getString("loginContact", "");

                getUploadedVehicleBuyerlist(myContact);
            }
        });
    }

    private void getUploadedVehicleBuyerlist(String myContact) {
        dialog.show();
        mApiCall.getUploadedVehicleBuyerlist(myContact);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (isVisibleToUser && !_hasLoadedOnce) {
                getUploadedVehicleBuyerlist(myContact);
                _hasLoadedOnce = true;
            }
        }
    }


    @Override
    public void notifySuccess(Response<?> response) {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        if (response != null) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (response.isSuccessful()) {
                Log.i("buyer Response", "" + response);
                BuyerResponse object = (BuyerResponse) response.body();
                BuyerResponse.Success objsuccess = object.getSuccess();

                for (BuyerResponse.Success.Vehicle obj : objsuccess.getVehicles()) {
                    childlist = new ArrayList<>();

                    obj.setVehicleId(obj.getVehicleId());
                    obj.setCategory(obj.getCategory());
                    obj.setManufacturer(obj.getManufacturer());
                    obj.setModel(obj.getModel());
                    obj.setVersion(obj.getVersion());
                    obj.setPrice(obj.getPrice());
                    obj.setYearOfManufacture(obj.getYearOfManufacture());
                    obj.setRtoCity(obj.getRtoCity());
                    obj.setTitle(obj.getTitle());
                    if (obj.getRegistrationNumber().equals(""))
                        obj.setRegistrationNumber("NA");
                    else
                        obj.setRegistrationNumber(obj.getRegistrationNumber());
                    obj.setKmsRunning(obj.getKmsRunning());
                    obj.setHrsRunning(obj.getHrsRunning());
                    obj.setLocationCity(obj.getLocationCity());
                    obj.setRcAvailable(obj.getRcAvailable());
                    obj.setNoOfOwners(obj.getNoOfOwners());
                    obj.setImage(obj.getImage());
                    obj.setInsuranceValid(obj.getInsuranceValid());
                    obj.setHpCapacity(obj.getHpCapacity());

                    if (!obj.getDate().equalsIgnoreCase("")) {
                        try {
                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");

                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd",
                                    Locale.getDefault());
                            inputFormat.setTimeZone(utc);

                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy",
                                    Locale.getDefault());
                            outputFormat.setTimeZone(utc);

                            Date date = inputFormat.parse(obj.getDate());
                            String output = outputFormat.format(date);
                            System.out.println("jjj" + output);
                            obj.setDate(output);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else
                        obj.setDate("");

                    for (BuyerResponse.Success.Found objectmatch : objsuccess.getFound()) {

                        if (obj.getVehicleId() == objectmatch.getVehicleId()) {

                            objectmatch.setReceivername(objectmatch.getReceivername());
                            objectmatch.setReceiverPic(objectmatch.getReceiverPic());
                            objectmatch.setContactNo(objectmatch.getContactNo());
                            objectmatch.setCategory(objectmatch.getCategory());
                            objectmatch.setManufacturer(objectmatch.getManufacturer());
                            objectmatch.setModel(objectmatch.getModel());
                            objectmatch.setYearOfManufacture(objectmatch.getYearOfManufacture());
                            objectmatch.setLocationCity(objectmatch.getLocationCity());
                            objectmatch.setVersion(objectmatch.getVersion());
                            objectmatch.setRtoCity(objectmatch.getRtoCity());
                            objectmatch.setPrice(objectmatch.getPrice());
                            objectmatch.setDate(objectmatch.getDate());
                            objectmatch.setVehicleId(objectmatch.getVehicleId());
                            objectmatch.setFavstatus(objectmatch.getFavstatus());
                            objectmatch.setSearchId(objectmatch.getSearchId());
                            objectmatch.setRcAvailable(objectmatch.getRcAvailable());
                            objectmatch.setInsuranceValid(objectmatch.getInsuranceValid());
                            objectmatch.setHpcapacity(objectmatch.getHpcapacity());

                           /* Date d = null;
                            try {
                                d = f.parse(objectmatch.getLastcall());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            objectmatch.setLastCallDateNew(d);*/

                            if (!objectmatch.getLastcall().equalsIgnoreCase("")) {
                                try {
                                    TimeZone utc = TimeZone.getTimeZone("etc/UTC");

                                    DateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a",
                                            Locale.getDefault());
                                    inputFormat.setTimeZone(utc);

                                    DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a",
                                            Locale.getDefault());
                                    outputFormat.setTimeZone(utc);

                                    Date date = inputFormat.parse(objectmatch.getLastcall());
                                    String output = outputFormat.format(date);
                                    System.out.println("last call" + output);
                                    objectmatch.setLastcall(output);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else
                                objectmatch.setLastcall("");

                            childlist.add(objectmatch);

                        }
                    }

                    obj.setFound(childlist);
                    mainList.add(obj);
                }
                ///
                mLinearScrollSecond = new LinearLayout[mainList.size()];
                isFirstViewClick = new boolean[mainList.size()];

                //Adds data into first row
                for (int i = 0; i < mainList.size(); i++) {

                    LayoutInflater inflater = null;
                    inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View mLinearView = inflater.inflate(R.layout.buyer_row, null);

                    final TextView mCategoryName = (TextView) mLinearView.findViewById(R.id.settitle);
                    final TextView mBrandName = (TextView) mLinearView.findViewById(R.id.setbrand);
                    final TextView mModelName = (TextView) mLinearView.findViewById(R.id.setmodel);
                    final TextView mLocationName = (TextView) mLinearView.findViewById(R.id.setlocation);
                    final TextView mPriceName = (TextView) mLinearView.findViewById(R.id.setprice);
                    final TextView mYearName = (TextView) mLinearView.findViewById(R.id.setyear);
                    final TextView myreg = (TextView) mLinearView.findViewById(R.id.yreg);
                    final TextView mRto_city = (TextView) mLinearView.findViewById(R.id.setrto);
                    final TextView mKms = (TextView) mLinearView.findViewById(R.id.setkms);
                    final TextView mRegno = (TextView) mLinearView.findViewById(R.id.setregno);
                    final TextView mmatchCount = (TextView) mLinearView.findViewById(R.id.match_count);
                    final ImageView mdownarrow = (ImageView) mLinearView.findViewById(R.id.postdownarrow);
                    final ImageView muparrow = (ImageView) mLinearView.findViewById(R.id.postuparrow);
                    ViewFlipper mViewFlipperbuyer = (ViewFlipper) mLinearView.findViewById(R.id.buyervehicalimgflicker);
                    final RelativeLayout mLinearFirstArrow = (RelativeLayout) mLinearView.findViewById(R.id.linearFirst);
                    //final TextView mUploadedDate = (TextView) mLinearView.findViewById(R.id.uploaddate);
                    final TextView mUploadedDate = (TextView) mLinearView.findViewById(R.id.strSearchDate);
                    //final ImageView mImageArrowFirst=(ImageView)mLinearView.findViewById(R.id.imageFirstArrow);
                    mLinearScrollSecond[i] = (LinearLayout) mLinearView.findViewById(R.id.linear_scroll);

                    //checkes if menu is already opened or not
                    if (!isFirstViewClick[i]) {
                        mLinearScrollSecond[i].setVisibility(View.GONE);
                        muparrow.setVisibility(View.GONE);
                        mdownarrow.setVisibility(View.VISIBLE);
                        //mImageArrowFirst.setBackgroundResource(R.drawable.arw_lt);
                    } else {
                        mLinearScrollSecond[i].setVisibility(View.VISIBLE);
                        muparrow.setVisibility(View.VISIBLE);
                        mdownarrow.setVisibility(View.GONE);
                        //mImageArrowFirst.setBackgroundResource(R.drawable.arw_down);
                    }

                    final int villll = i;

                    mLinearFirstArrow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setViewsVisible(villll);
                        }
                    });

                    mCategoryName.setText(mainList.get(i).getCategory());
                    mBrandName.setText(mainList.get(i).getManufacturer());
                    mModelName.setText(mainList.get(i).getModel());
                    mLocationName.setText(mainList.get(i).getLocationCity());
                    mPriceName.setText(mainList.get(i).getPrice());
                    mYearName.setText(mainList.get(i).getYearOfManufacture());
                    mRto_city.setText(mainList.get(i).getRtoCity());

                    if (mainList.get(i).getRtoCity().equalsIgnoreCase("Unregistered") || mainList.get(i).getRtoCity().equalsIgnoreCase("") || mainList.get(i).getRtoCity().isEmpty()) {
                        mRegno.setVisibility(View.GONE);
                        myreg.setVisibility(View.GONE);
                    } else {
                        mRegno.setText(mainList.get(i).getRegistrationNumber());

                    }
                    mKms.setText(String.valueOf(mainList.get(i).getKmsRunning()));
                    mmatchCount.setText(String.valueOf(mainList.get(i).getFound().size()));

                    String count = String.valueOf(mainList.get(i).getFound().size());
                    System.out.print("Leads at position=" + i + "=" + count);

                    if (count.equalsIgnoreCase("0")) {
                        mdownarrow.setVisibility(View.GONE);
                        muparrow.setVisibility(View.GONE);
                    }


                    mUploadedDate.setText(mainList.get(i).getDate());

                    final String imagenames = mainList.get(i).getImage().replaceAll(" ", "");

                    List<String> iname = new ArrayList<>();

                    if (!imagenames.equalsIgnoreCase("")) {
                        String[] imagenamecame = imagenames.split(",");

                        if (imagenamecame.length != 0 && !imagenamecame[0].equals("")) {
                            for (int z = 0; z < imagenamecame.length; z++) {
                                iname.add(imagenamecame[z]);
                            }

                            ImageView[] imageView = new ImageView[iname.size()];

                            for (int l = 0; l < imageView.length; l++) {
                                imageView[l] = new ImageView(getActivity());
                                Glide.with(getActivity())
                                        .load(getString(R.string.base_image_url) + iname.get(l).replaceAll(" ", "%20"))
                                        .into(imageView[l]);
                                mViewFlipperbuyer.addView(imageView[l]);
                            }
                        } else {
                            ImageView[] imageView = new ImageView[2];
                            for (int l = 0; l < imageView.length; l++) {
                                imageView[l] = new ImageView(getActivity());
                                imageView[l].setBackgroundResource(R.drawable.vehiimg);
                                mViewFlipperbuyer.addView(imageView[l]);
                            }
                        }
                    } else
                        mViewFlipperbuyer.setBackgroundResource(R.drawable.vehiimg);

//
                    for (int j = 0; j < mainList.get(i).getFound().size(); j++) {
                        LayoutInflater inflater2 = null;
                        inflater2 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View mLinearView2 = inflater2.inflate(R.layout.buyer_list_adapter, null);

                        TextView mBuyerUserName = (TextView) mLinearView2.findViewById(R.id.buyerusername);
                        TextView mBuyerLocation = (TextView) mLinearView2.findViewById(R.id.buyerlocation);
                        final ImageView buyer_lead_image = (ImageView) mLinearView2.findViewById(R.id.buyer_lead_image);
                        final ImageView callbuyer = (ImageView) mLinearView2.findViewById(R.id.callbuyer);
                        final ImageView favouritebuyer = (ImageView) mLinearView2.findViewById(R.id.favouritebuyer);
                        final ImageView unfavouritebuyer = (ImageView) mLinearView2.findViewById(R.id.unfavouritebuyer);

                        TextView mItemNameCity = (TextView) mLinearView2.findViewById(R.id.namecity);
                        CheckBox checkBox1 = (CheckBox) mLinearView2.findViewById(R.id.checkBox1);
                        CheckBox checkBox2 = (CheckBox) mLinearView2.findViewById(R.id.checkBox2);
                        CheckBox checkBox3 = (CheckBox) mLinearView2.findViewById(R.id.checkBox3);
                        CheckBox checkBox4 = (CheckBox) mLinearView2.findViewById(R.id.checkBox4);
                        CheckBox checkBox5 = (CheckBox) mLinearView2.findViewById(R.id.checkBox5);
                        CheckBox checkBoxRc = (CheckBox) mLinearView2.findViewById(R.id.checkBoxRc);
                        CheckBox checkBoxIns = (CheckBox) mLinearView2.findViewById(R.id.checkBoxINS);
                        CheckBox checkBoxHpcap = (CheckBox) mLinearView2.findViewById(R.id.checkBoxHPcap);

                        CheckBox checkBox6 = (CheckBox) mLinearView2.findViewById(R.id.checkBox6);
                        CheckBox checkBox7 = (CheckBox) mLinearView2.findViewById(R.id.checkBox7);
                        CheckBox checkBox8 = (CheckBox) mLinearView2.findViewById(R.id.checkBox8);
                        CheckBox checkBox9 = (CheckBox) mLinearView2.findViewById(R.id.checkBox9);
                        CheckBox checkBox10 = (CheckBox) mLinearView2.findViewById(R.id.checkBox10);
                        CheckBox checkBoxRcRight = (CheckBox) mLinearView2.findViewById(R.id.checkBoxRcRight);
                        CheckBox checkBoxINSRight = (CheckBox) mLinearView2.findViewById(R.id.checkBoxINSRight);
                        CheckBox checkBoxHPcapRight = (CheckBox) mLinearView2.findViewById(R.id.checkBoxHPcapRight);

                        mBuyerUserName.setTextColor(Color.BLUE);
                        mBuyerUserName.setText(mainList.get(i).getFound().get(j).getReceivername());
                        mBuyerLocation.setText(mainList.get(i).getFound().get(j).getLocationCity());

                        String image_buyer = mainList.get(i).getFound().get(j).getReceiverPic();

                        if (image_buyer.equals("")) {
                            buyer_lead_image.setBackgroundResource(R.drawable.profile);
                        } else {
                            try {

                                Glide.with(getActivity())
                                        .load(getString(R.string.base_image_url) + image_buyer)
                                        //.bitmapTransform(new CropCircleTransformation(getActivity()))
                                        .into(buyer_lead_image);
                            } catch (Exception e) {
                                CustomToast.customToast(getActivity(), "Error image uploading");
                            }
                        }
                        final String itemLocation = mainList.get(i).getFound().get(j).getLocationCity();
                        final String itemcategory = mainList.get(i).getFound().get(j).getCategory();
                        final String itemBrand = mainList.get(i).getFound().get(j).getManufacturer();
                        final String itemModel = mainList.get(i).getFound().get(j).getModel();
                        final String itemYear = mainList.get(i).getFound().get(j).getYearOfManufacture();
                        final String itemrto_city = mainList.get(i).getFound().get(j).getRtoCity();
                        final String itemrc = mainList.get(i).getFound().get(j).getRcAvailable();
                        final String itemIns = mainList.get(i).getFound().get(j).getInsuranceValid();
                        final String itemHp = mainList.get(i).getFound().get(j).getHpcapacity();
                        if (itemcategory.equalsIgnoreCase("Tractor")) {
                            checkBoxHpcap.setVisibility(View.VISIBLE);
                            checkBoxHPcapRight.setVisibility(View.VISIBLE);
                        }

                        mItemNameCity.setText("" + "Last call On:" + mainList.get(i).getFound().get(j).getLastcall());

                        checkBox1.setText(mainList.get(i).getCategory());
                        checkBox2.setText(mainList.get(i).getManufacturer());
                        checkBox3.setText(mainList.get(i).getModel());
                        checkBox4.setText(mainList.get(i).getYearOfManufacture());
                        checkBox5.setText(mainList.get(i).getRtoCity());

                        if (mainList.get(i).getRcAvailable().startsWith("-Select RC Available-") ||
                                mainList.get(i).getRcAvailable().equalsIgnoreCase("") || mainList.get(i).getRcAvailable().equalsIgnoreCase("-"))
                            checkBoxRc.setText("RC avl-NA");
                        else
                            checkBoxRc.setText("" + "RC avl-" + mainList.get(i).getRcAvailable());

                        if (mainList.get(i).getInsuranceValid().startsWith("-Select Insurance Valid-") ||
                                mainList.get(i).getInsuranceValid().equalsIgnoreCase("") || mainList.get(i).getInsuranceValid().equalsIgnoreCase("-"))
                            checkBoxIns.setText("INS avl-NA");
                        else
                            checkBoxIns.setText("" + "INS avl-" + mainList.get(i).getInsuranceValid());

                        if (mainList.get(i).getHpCapacity().equalsIgnoreCase("-") || mainList.get(i).getHpCapacity().equalsIgnoreCase(""))
                            checkBoxHpcap.setText("Hp-NA");
                        else
                            checkBoxHpcap.setText("" + "Hp-" + mainList.get(i).getHpCapacity());

                        checkBox6.setText(itemcategory);
                        checkBox7.setText(itemBrand);
                        checkBox8.setText(itemModel);
                        checkBox9.setText(itemYear);
                        checkBox10.setText(itemrto_city);

                        if (itemrc.startsWith("-Select RC Available-") || itemrc.equalsIgnoreCase("") || itemrc.equalsIgnoreCase("-"))
                            checkBoxRcRight.setText("RC avl-NA");
                        else
                            checkBoxRcRight.setText("" + "RC avl-" + itemrc);
                        if (itemIns.startsWith("-Select Insurance Valid-") || itemIns.equalsIgnoreCase("") || itemIns.equalsIgnoreCase("-"))
                            checkBoxINSRight.setText("INS avl-NA");
                        else
                            checkBoxINSRight.setText("" + "INS avl-" + itemIns);

                        if (itemHp.equalsIgnoreCase("") || itemHp.equalsIgnoreCase("-"))
                            checkBoxHPcapRight.setText("Hp-NA");
                        else
                            checkBoxHPcapRight.setText("" + "Hp-" + itemHp);

                        try {

                            if (checkBox1.getText().toString().equalsIgnoreCase(checkBox6.getText().toString())) {
                                checkBox1.setChecked(true);
                                checkBox6.setChecked(true);
                            }

                            if (checkBox2.getText().toString().equalsIgnoreCase(checkBox7.getText().toString())) {
                                checkBox2.setChecked(true);
                                checkBox7.setChecked(true);
                            }

                            if (checkBox3.getText().toString().equalsIgnoreCase(checkBox8.getText().toString())) {
                                checkBox3.setChecked(true);
                                checkBox8.setChecked(true);
                            }

                            if (checkBox4.getText().toString().equalsIgnoreCase(checkBox9.getText().toString())) {
                                checkBox4.setChecked(true);
                                checkBox9.setChecked(true);
                            }

                            if (checkBox5.getText().toString().equalsIgnoreCase(checkBox10.getText().toString())) {
                                checkBox5.setChecked(true);
                                checkBox10.setChecked(true);
                            }

                            if (checkBoxRc.getText().toString().equalsIgnoreCase(checkBoxRcRight.getText().toString())) {
                                checkBoxRc.setChecked(true);
                                checkBoxRcRight.setChecked(true);
                            }

                            if (checkBoxIns.getText().toString().equalsIgnoreCase(checkBoxINSRight.getText().toString())) {
                                checkBoxIns.setChecked(true);
                                checkBoxINSRight.setChecked(true);
                            }

                            if (checkBoxHpcap.getText().toString().equalsIgnoreCase(checkBoxHPcapRight.getText().toString())) {
                                checkBoxHpcap.setChecked(true);
                                checkBoxHPcapRight.setChecked(true);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        checkBox1.setEnabled(false);
                        checkBox2.setEnabled(false);
                        checkBox3.setEnabled(false);
                        checkBox4.setEnabled(false);
                        checkBox5.setEnabled(false);
                        checkBoxRc.setEnabled(false);
                        checkBoxIns.setEnabled(false);
                        checkBoxHpcap.setEnabled(false);

                        checkBox6.setEnabled(false);
                        checkBox7.setEnabled(false);
                        checkBox8.setEnabled(false);
                        checkBox9.setEnabled(false);
                        checkBox10.setEnabled(false);
                        checkBoxRcRight.setEnabled(false);
                        checkBoxINSRight.setEnabled(false);
                        checkBoxHPcapRight.setEnabled(false);

                        final int finalI = i;
                        final int finalJ = j;
                        mBuyerUserName.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                recieverContact = mainList.get(finalI).getFound().get(finalJ).getContactNo();
                                if (!recieverContact.equalsIgnoreCase(myContact)) {

                                    Bundle bundle = new Bundle();
                                    bundle.putString("contactOtherProfile", recieverContact);
                                    bundle.putString("action", "otherProfile");
                                    Intent intent = new Intent(getActivity(), OtherProfile.class);
                                    intent.putExtras(bundle);
                                    getActivity().startActivity(intent);
                                }

                            }
                        });

                        buyer_lead_image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                recieverContact = mainList.get(finalI).getFound().get(finalJ).getContactNo();
                                if (!recieverContact.equalsIgnoreCase(myContact)) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("contactOtherProfile", recieverContact);
                                    bundle.putString("action", "otherProfile");
                                    Intent intent = new Intent(getActivity(), OtherProfile.class);
                                    intent.putExtras(bundle);
                                    getActivity().startActivity(intent);
                                }
                            }
                        });

                        callbuyer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                recieverContact = mainList.get(finalI).getFound().get(finalJ).getContactNo();

                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                String calldate = df.format(c.getTime());

                                if (!recieverContact.equals(myContact)) {
                                    call(recieverContact);

                                    mApiCall.sendLastCallDate(myContact, recieverContact, calldate, "1");
                                }
                            }
                        });


                        if (mainList.get(finalI).getFound().get(finalJ).getFavstatus().equalsIgnoreCase("yes")) {
                            unfavouritebuyer.setVisibility(View.VISIBLE);
                            favouritebuyer.setVisibility(View.GONE);
                        } else {
                            unfavouritebuyer.setVisibility(View.GONE);
                            favouritebuyer.setVisibility(View.VISIBLE);
                        }

                        favouritebuyer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int buyerSearchid = mainList.get(finalI).getFound().get(finalJ).getSearchId();
                                int buyerVehicleid = mainList.get(finalI).getFound().get(finalJ).getVehicleId();

                                mApiCall.addToFavorite(myContact, 0, 0, buyerSearchid,
                                        buyerVehicleid, 0, 0);

                                unfavouritebuyer.setVisibility(View.VISIBLE);
                                favouritebuyer.setVisibility(View.GONE);

                                mainList.get(finalI).getFound().get(finalJ).setFavstatus("yes");
                            }


                        });


                        unfavouritebuyer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int buyerSearchid = mainList.get(finalI).getFound().get(finalJ).getSearchId();
                                int buyerVehicleid = mainList.get(finalI).getFound().get(finalJ).getVehicleId();

                                mApiCall.removeFromFavorite(myContact, 0, 0, buyerSearchid,
                                        buyerVehicleid, 0, 0);

                                favouritebuyer.setVisibility(View.VISIBLE);
                                unfavouritebuyer.setVisibility(View.GONE);

                                mainList.get(finalI).getFound().get(finalJ).setFavstatus("no");
                            }


                        });

                        mLinearScrollSecond[i].addView(mLinearView2);
                    }
                    mLinearListView.addView(mLinearView);
                }
            }
        } else {
            dialog.setMessage("No Buyer data");
        }

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class", "Buyer Notification Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

        if (str != null) {
            switch (str) {
                case "success_favourite":
                    CustomToast.customToast(getActivity(), "Favorite");
                    break;
                case "success_remove":
                    CustomToast.customToast(getActivity(), "Unfavorite");
                    break;
            }
        }

    }

    private void call(String recieverContact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + recieverContact));

        System.out.println("calling started");
        try {
            getActivity().startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Service View Fragment\n");
        }
    }

    public void setViewsVisible(int s) {
        for (int i = 0; i < mainList.size(); i++) {
            if (s == i && !isFirstViewClick[i]) {
                isFirstViewClick[i] = true;
                mLinearScrollSecond[i].setVisibility(View.VISIBLE);
            } else {
                isFirstViewClick[i] = false;
                mLinearScrollSecond[i].setVisibility(View.GONE);
            }

        }
    }
}
