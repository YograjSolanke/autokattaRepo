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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import autokatta.com.response.SellerResponse;
import autokatta.com.view.CompareVehicleListActivity;
import autokatta.com.view.OtherProfile;
import retrofit2.Response;

/**
 * Created by ak-003 on 21/4/17.
 */
public class SellerNotificationFragment extends Fragment implements RequestNotifier {

    View mSellerView;
    public List<SellerResponse.Success.SavedSearch> mainList = new ArrayList<>();
    public List<SellerResponse.Success.MatchedResult> childlist;
    private LinearLayout mLinearListView;
    boolean isFirstViewClick[];
    String myContact;
    LinearLayout mLinearScrollSecond[];
    RelativeLayout relativeLayout;
    Button compare;
    String getBundle_vehicle_id = "";
    ApiCall mApiCall;
    boolean _hasLoadedOnce = false;
    private ProgressDialog dialog;
    TextView mNoData;

    public SellerNotificationFragment() {
        //Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSellerView = inflater.inflate(R.layout.example, container, false);
        return mSellerView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mApiCall = new ApiCall(getActivity(), this);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Loading Seller...");
                mLinearListView = (LinearLayout) mSellerView.findViewById(R.id.linear_ListView);
                relativeLayout = (RelativeLayout) mSellerView.findViewById(R.id.tablerow1);
                compare = (Button) mSellerView.findViewById(R.id.conpare);

                myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).
                        getString("loginContact", "");
                mNoData = (TextView) mSellerView.findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);

                getSavedSearchSellerList(myContact);
            }
        });

        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CompareVehicleListActivity.class);
                intent.putExtra("vehicle_ids", getBundle_vehicle_id);
                getActivity().startActivity(intent);
            }
        });
    }

    private void getSavedSearchSellerList(String myContact) {
        mApiCall.getSavedSearchSellerList(myContact);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (isVisibleToUser) {
                dialog.show();
                getSavedSearchSellerList(myContact);
                _hasLoadedOnce = true;
            }
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

    @Override
    public void notifySuccess(Response<?> response) {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        if (response != null) {

            if (response.isSuccessful()) {
                mNoData.setVisibility(View.GONE);
                //mainList.clear();
                Log.i("seller Response", "" + response);
                SellerResponse object = (SellerResponse) response.body();
                SellerResponse.Success objsuccess = object.getSuccess();
                for (SellerResponse.Success.SavedSearch obj : objsuccess.getSavedSearch()) {
                    childlist = new ArrayList<>();
                    obj.setCategory(obj.getCategory());
                    obj.setManufacturer(obj.getManufacturer());
                    obj.setModel(obj.getModel());
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
                    obj.setInsuranceValid(obj.getInsuranceValid());
                    obj.setHpcapacity(obj.getHpcapacity());

                    if (!obj.getDate().equalsIgnoreCase("")) {
                        try {
                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                            //format of date coming from services
                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        /*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault());*/
                            inputFormat.setTimeZone(utc);

                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        /*DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                                Locale.getDefault());*/
                            outputFormat.setTimeZone(utc);

                            Date date = inputFormat.parse(obj.getDate());
                            String output = outputFormat.format(date);
                            obj.setDate(output);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else
                        obj.setDate("");

                    for (SellerResponse.Success.MatchedResult objectmatch : objsuccess.getMatchedResult()) {
                        if (obj.getSearchId().equals(objectmatch.getSearchId())) {
                            objectmatch.setUsername(objectmatch.getUsername());
                            objectmatch.setProfilePic(objectmatch.getProfilePic());
                            objectmatch.setImage(objectmatch.getImage());
                            objectmatch.setContactNo(objectmatch.getContactNo());
                            objectmatch.setCategory(objectmatch.getCategory());
                            objectmatch.setManufacturer(objectmatch.getManufacturer());
                            objectmatch.setModel(objectmatch.getModel());
                            objectmatch.setYearOfManufacture(objectmatch.getYearOfManufacture());
                            objectmatch.setPrice(objectmatch.getPrice());
                            objectmatch.setDate(objectmatch.getDate());
                            objectmatch.setVehicleId(objectmatch.getVehicleId());
                            objectmatch.setFavstatus(objectmatch.getFavstatus());
                            objectmatch.setSearchId(objectmatch.getSearchId());
                            objectmatch.setRcAvailable(objectmatch.getRcAvailable());
                            objectmatch.setInsuranceValid(objectmatch.getInsuranceValid());
                            objectmatch.setHpCapacity(objectmatch.getHpCapacity());
                            objectmatch.setTitle(objectmatch.getTitle());

                            //to set buyer last call date
                            if (!objectmatch.getLastcall().equalsIgnoreCase("")) {
                                try {
                                    TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                                    //format of date coming from services
                                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                    inputFormat.setTimeZone(utc);

                                    //format of date which we want to show
                                    DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                                    outputFormat.setTimeZone(utc);

                                    Date date = inputFormat.parse(objectmatch.getLastcall());
                                    String output = outputFormat.format(date);

                                    objectmatch.setLastcall(output);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else
                                objectmatch.setLastcall("");

                            //to set vehicle uploaded date
                            if (!objectmatch.getDate().equalsIgnoreCase("")) {
                                try {
                                    TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                                    //format of date coming from services
                                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                    inputFormat.setTimeZone(utc);

                                    //format of date which we want to show
                                    DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                                    outputFormat.setTimeZone(utc);

                                    Date date = inputFormat.parse(objectmatch.getDate());
                                    String output = outputFormat.format(date);

                                    objectmatch.setDate(output);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else
                                objectmatch.setDate("");

                            childlist.add(objectmatch);
                        }
                    }
                    obj.setMatchedResult(childlist);
                    mainList.add(obj);
                }
                mLinearScrollSecond = new LinearLayout[mainList.size()];
                isFirstViewClick = new boolean[mainList.size()];

                //Adds data into first row
                for (int i = 0; i < mainList.size(); i++) {
                    LayoutInflater inflater = null;
                    inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View mLinearView = inflater.inflate(R.layout.seller_row, null);

                    final TextView mTitleName = (TextView) mLinearView.findViewById(R.id.settitle);
                    final TextView mModelName = (TextView) mLinearView.findViewById(R.id.setmodel);
                    final TextView mBrandName = (TextView) mLinearView.findViewById(R.id.setbrand);
                    final TextView mPriceName = (TextView) mLinearView.findViewById(R.id.setprice);
                    final TextView mYearName = (TextView) mLinearView.findViewById(R.id.setyear);
                    final TextView mrtoname = (TextView) mLinearView.findViewById(R.id.setrto);
                    final TextView mkmsname = (TextView) mLinearView.findViewById(R.id.setkms);
                    final TextView mlocationname = (TextView) mLinearView.findViewById(R.id.setlocation);
                    final TextView mregnoname = (TextView) mLinearView.findViewById(R.id.setregno);
                    final TextView msellerMatchCount = (TextView) mLinearView.findViewById(R.id.seller_match_count);
                    ImageView msellerdownarrow = (ImageView) mLinearView.findViewById(R.id.sellerdownarrow);
                    ImageView mselleruparrow = (ImageView) mLinearView.findViewById(R.id.selleruparrow);
                    final RelativeLayout mLinearFirstArrow = (RelativeLayout) mLinearView.findViewById(R.id.linearFirst);
                    final TextView mSearchDate = (TextView) mLinearView.findViewById(R.id.strsearchdate);
                    mLinearScrollSecond[i] = (LinearLayout) mLinearView.findViewById(R.id.linear_scroll);

                    //checkes if menu is already opened or not
                    if (!isFirstViewClick[i]) {
                        mLinearScrollSecond[i].setVisibility(View.GONE);
                    } else {
                        mLinearScrollSecond[i].setVisibility(View.VISIBLE);
                    }

                    final int finalI = i;
                    if (!(mainList.get(i).getMatchedResult().size() == 0)) {
                        msellerMatchCount.setText(String.valueOf(mainList.get(i).getMatchedResult().size()));
                    } else {
                        msellerMatchCount.setText("0");
                    }

                    if (mainList.get(i).getMatchedResult().size() == 0) {
                        msellerdownarrow.setVisibility(View.GONE);
                        mselleruparrow.setVisibility(View.GONE);
                    }
                    mLinearFirstArrow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mainList.get(finalI).getMatchedResult().size() == 0)
                                CustomToast.customToast(getActivity(), "No Seller Leads Found");
                            else
                                setViewsVisible(finalI);
                        }
                    });

                    mTitleName.setText(mainList.get(i).getCategory());
                    if (mainList.get(i).getModel().equalsIgnoreCase("-Select Models-")) {
                        mModelName.setText("Not Mention");
                    } else {
                        mModelName.setText(mainList.get(i).getModel());
                    }

                    if (mainList.get(i).getManufacturer().equalsIgnoreCase("-Select Brand-")) {
                        mBrandName.setText("Not Mention");
                    } else {
                        mBrandName.setText(mainList.get(i).getManufacturer());
                    }

                    if (mainList.get(i).getPrice().equals("")) {
                        mPriceName.setText("Not Mention");
                    } else {
                        mPriceName.setText(mainList.get(i).getPrice());
                    }

                    if (mainList.get(i).getYearOfManufacture().equalsIgnoreCase("")) {
                        mYearName.setText("Not Mention");
                    } else {
                        mYearName.setText(mainList.get(i).getYearOfManufacture());
                    }

                    mrtoname.setText(mainList.get(i).getRtoCity());
                    mkmsname.setText(mainList.get(i).getKmsRunning());
                    mlocationname.setText(mainList.get(i).getLocationCity());
                    mregnoname.setText(mainList.get(i).getRegistrationNumber());
                    mSearchDate.setText(mainList.get(i).getDate());

                    final List<Integer> checkedvehicle_ids = new ArrayList<>(mainList.get(i).getMatchedResult().size());
                    for (int j = 0; j < mainList.get(i).getMatchedResult().size(); j++) {
                        checkedvehicle_ids.add(0);
                    }

                    CheckBox checkBox[] = new CheckBox[mainList.get(i).getMatchedResult().size()];

                    //Adds data into second row
                    for (int j = 0; j < mainList.get(i).getMatchedResult().size(); j++) {
                        LayoutInflater inflater2 = null;
                        int showcheckboc = 0;
                        inflater2 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View mLinearView2 = inflater2.inflate(R.layout.seller_list_adapter, null);

                        TextView mtitle = (TextView) mLinearView2.findViewById(R.id.title);
                        checkBox[j] = (CheckBox) mLinearView2.findViewById(R.id.checkauc);
                        ViewFlipper mViewFlippersell = (ViewFlipper) mLinearView2.findViewById(R.id.sellvehicalimgflicker);
                        TextView mUserName = (TextView) mLinearView2.findViewById(R.id.username);
                        TextView mVehicleCount = (TextView) mLinearView2.findViewById(R.id.vehiclecount);
                        TextView mDateTime = (TextView) mLinearView2.findViewById(R.id.addon);
                        TextView lastcall = (TextView) mLinearView2.findViewById(R.id.lastcall);
                        ImageView mCallimg = (ImageView) mLinearView2.findViewById(R.id.sellcallimg);
                        final ImageView mFavimg = (ImageView) mLinearView2.findViewById(R.id.sellfevimg);
                        final ImageView unmFavimg = (ImageView) mLinearView2.findViewById(R.id.sellunfevimg);

                        CheckBox checkBox1 = (CheckBox) mLinearView2.findViewById(R.id.sellcheckBox1);
                        CheckBox checkBox2 = (CheckBox) mLinearView2.findViewById(R.id.sellcheckBox2);
                        CheckBox checkBox3 = (CheckBox) mLinearView2.findViewById(R.id.sellcheckBox3);
                        CheckBox checkBox4 = (CheckBox) mLinearView2.findViewById(R.id.sellcheckBox4);
                        CheckBox checkBox5 = (CheckBox) mLinearView2.findViewById(R.id.sellcheckBox5);
                        CheckBox checkBoxRc = (CheckBox) mLinearView2.findViewById(R.id.sellcheckBoxRc);
                        CheckBox checkBoxIns = (CheckBox) mLinearView2.findViewById(R.id.sellcheckBoxIns);
                        CheckBox checkBoxHp = (CheckBox) mLinearView2.findViewById(R.id.sellcheckBoxHp);

                        CheckBox checkBox6 = (CheckBox) mLinearView2.findViewById(R.id.sellcheckBox6);
                        CheckBox checkBox7 = (CheckBox) mLinearView2.findViewById(R.id.sellcheckBox7);
                        CheckBox checkBox8 = (CheckBox) mLinearView2.findViewById(R.id.sellcheckBox8);
                        CheckBox checkBox9 = (CheckBox) mLinearView2.findViewById(R.id.sellcheckBox9);
                        CheckBox checkBox10 = (CheckBox) mLinearView2.findViewById(R.id.sellcheckBox10);
                        CheckBox checkBoxRcright = (CheckBox) mLinearView2.findViewById(R.id.sellcheckBoxRcRight);
                        CheckBox checkBoxInsRight = (CheckBox) mLinearView2.findViewById(R.id.sellcheckBoxInsRight);
                        CheckBox checkBoxHpRight = (CheckBox) mLinearView2.findViewById(R.id.sellcheckBoxHpRight);

                        final String itemUserName = mainList.get(i).getMatchedResult().get(j).getUsername();
                        final String itemUserimage = mainList.get(i).getMatchedResult().get(j).getImage();
                        final String itemUserContact = mainList.get(i).getMatchedResult().get(j).getContactNo();

                        final String itemCaterogy = mainList.get(i).getMatchedResult().get(j).getCategory();
                        final String itemBrand = mainList.get(i).getMatchedResult().get(j).getManufacturer();
                        final String itemModel = mainList.get(i).getMatchedResult().get(j).getModel();
                        final String itemYearManu = mainList.get(i).getMatchedResult().get(j).getYearOfManufacture();
                        final String itemPrice = mainList.get(i).getMatchedResult().get(j).getPrice();
                        final String itemDate = mainList.get(i).getMatchedResult().get(j).getDate();
                        final int itemvehicleId = mainList.get(i).getMatchedResult().get(j).getVehicleId();
                        final String itemRto = mainList.get(i).getMatchedResult().get(j).getRtoCity();

                        final String favStatus = mainList.get(i).getMatchedResult().get(j).getFavstatus();
                        final int search_idget = mainList.get(i).getMatchedResult().get(j).getSearchId();
                        final String itemrc = mainList.get(i).getMatchedResult().get(j).getRcAvailable();
                        final String itemins = mainList.get(i).getMatchedResult().get(j).getInsuranceValid();
                        final String itemHp = mainList.get(i).getMatchedResult().get(j).getHpCapacity();

                        if (itemCaterogy.equalsIgnoreCase("Tractor")) {
                            checkBoxHp.setVisibility(View.VISIBLE);
                            checkBoxHpRight.setVisibility(View.VISIBLE);
                        }


                        lastcall.setText("" + "Last call On :" + mainList.get(i).getMatchedResult().get(j).getLastcall());
                        mDateTime.setText("" + "Uploaded On :" + mainList.get(i).getMatchedResult().get(j).getDate());

                        mtitle.setText(mainList.get(i).getMatchedResult().get(j).getTitle());
                        mUserName.setTextColor(Color.BLUE);
                        mUserName.setText(itemUserName);

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

                        if (mainList.get(i).getHpcapacity().equalsIgnoreCase("-") ||
                                mainList.get(i).getHpcapacity().equalsIgnoreCase("")
                                || mainList.get(i).getHpcapacity().equalsIgnoreCase("not mentioned"))
                            checkBoxHp.setText("Hp-NA");
                        else
                            checkBoxHp.setText("" + "Hp-" + mainList.get(i).getHpcapacity());


                        checkBox6.setText(itemCaterogy);
                        checkBox7.setText(itemBrand);
                        checkBox8.setText(itemModel);
                        checkBox9.setText(itemYearManu);
                        checkBox10.setText(itemRto);

                        if (itemrc.startsWith("-Select RC Available-") || itemrc.equalsIgnoreCase("") || itemrc.equalsIgnoreCase("-"))
                            checkBoxRcright.setText("RC avl-NA");
                        else
                            checkBoxRcright.setText("" + "RC avl-" + itemrc);
                        if (itemins.startsWith("-Select Insurance Valid-") || itemins.equalsIgnoreCase("") || itemins.equalsIgnoreCase("-"))
                            checkBoxInsRight.setText("INS avl-NA");
                        else
                            checkBoxInsRight.setText("" + "INS avl-" + itemins);

                        if (itemHp.equalsIgnoreCase("") || itemHp.equalsIgnoreCase("-") || itemHp.equalsIgnoreCase("not mentioned"))
                            checkBoxHpRight.setText("Hp-NA");
                        else
                            checkBoxHpRight.setText("" + "Hp-" + itemHp);

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
                            if (checkBoxRc.getText().toString().equalsIgnoreCase(checkBoxRcright.getText().toString())) {
                                checkBoxRc.setChecked(true);
                                checkBoxRcright.setChecked(true);
                            }

                            if (checkBoxIns.getText().toString().equalsIgnoreCase(checkBoxInsRight.getText().toString())) {
                                checkBoxIns.setChecked(true);
                                checkBoxInsRight.setChecked(true);
                            }

                            if (checkBoxHp.getText().toString().equalsIgnoreCase(checkBoxHpRight.getText().toString())) {
                                checkBoxHp.setChecked(true);
                                checkBoxHpRight.setChecked(true);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        checkBox1.setEnabled(false);
                        checkBox2.setEnabled(false);
                        checkBox3.setEnabled(false);
                        checkBox4.setEnabled(false);
                        checkBox5.setEnabled(false);
                        checkBox6.setEnabled(false);
                        checkBox7.setEnabled(false);
                        checkBox8.setEnabled(false);
                        checkBox9.setEnabled(false);
                        checkBox10.setEnabled(false);


                        mVehicleCount.setVisibility(View.GONE);
                        final String imagenames = mainList.get(i).getMatchedResult().get(j).getImage();
                        List<String> iname = new ArrayList<String>();

                        String[] imagenamecame = imagenames.split(",");
                        if (imagenamecame.length != 0 && !imagenamecame[0].equals("")) {
                            for (String anImagenamecame : imagenamecame) {
                                iname.add(anImagenamecame);
                            }
                            ImageView[] imageView = new ImageView[iname.size()];

                            for (int l = 0; l < imageView.length; l++) {
                                imageView[l] = new ImageView(getActivity());
                                Glide.with(getActivity())
                                        .load(getString(R.string.base_image_url) + iname.get(l).replaceAll(" ", "%20"))
                                        .into(imageView[l]);
                                mViewFlippersell.addView(imageView[l]);
                            }
                        } else {
                            ImageView[] imageView = new ImageView[2];
                            for (int l = 0; l < imageView.length; l++) {
                                imageView[l] = new ImageView(getActivity());
                                imageView[l].setBackgroundResource(R.drawable.vehiimg);
                                mViewFlippersell.addView(imageView[l]);
                            }

                        }


                        int mFlippingsell = 0;

                        /** Start Flipping */
                        mViewFlippersell.startFlipping();
                        mFlippingsell = 1;


                        final int finalJ = j;

                        final int v_ids = mainList.get(i).getMatchedResult().get(j).getVehicleId();
                        checkBox[j].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    checkedvehicle_ids.set(finalJ, v_ids);
                                    VisibleCompareButton(checkedvehicle_ids);
                                } else {
                                    checkedvehicle_ids.set(finalJ, 0);
                                    VisibleCompareButton(checkedvehicle_ids);
                                }
                            }
                        });


                        mUserName.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String recieverContact = mainList.get(finalI).getMatchedResult().get(finalJ).getContactNo();
                                if (!recieverContact.equals(myContact)) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("contactOtherProfile", recieverContact);
                                    bundle.putString("action", "otherProfile");
                                    Intent intent = new Intent(getActivity(), OtherProfile.class);
                                    intent.putExtras(bundle);
                                    getActivity().startActivity(intent);
                                }
                            }
                        });

                        mCallimg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String recieverContact = mainList.get(finalI).getMatchedResult().get(finalJ).getContactNo();
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                String calldate = df.format(c.getTime());
                                if (!recieverContact.equals(myContact)) {
                                    call(recieverContact);
                                    mApiCall.sendLastCallDate(myContact, recieverContact, calldate, "1");
                                }
                            }
                        });


                        if (mainList.get(finalI).getMatchedResult().get(finalJ).getFavstatus().equalsIgnoreCase("yes")) {
                            mFavimg.setVisibility(View.GONE);
                            unmFavimg.setVisibility(View.VISIBLE);
                        } else {
                            mFavimg.setVisibility(View.VISIBLE);
                            unmFavimg.setVisibility(View.GONE);
                        }

                        mFavimg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int sellerVehicleId = mainList.get(finalI).getMatchedResult().get(finalJ).getVehicleId();
                                int sellerSearchId = mainList.get(finalI).getMatchedResult().get(finalJ).getSearchId();

                                mApiCall.addToFavorite(myContact, sellerSearchId, sellerVehicleId, 0, 0, 0, 0);
                                mFavimg.setVisibility(View.GONE);
                                unmFavimg.setVisibility(View.VISIBLE);

                                mainList.get(finalI).getMatchedResult().get(finalJ).setFavstatus("yes");
                            }


                        });


                        unmFavimg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int sellerVehicleId = mainList.get(finalI).getMatchedResult().get(finalJ).getVehicleId();
                                int sellerSearchId = mainList.get(finalI).getMatchedResult().get(finalJ).getSearchId();

                                mApiCall.removeFromFavorite(myContact, sellerSearchId, sellerVehicleId, 0, 0, 0, 0);
                                mFavimg.setVisibility(View.VISIBLE);
                                unmFavimg.setVisibility(View.GONE);
                                mainList.get(finalI).getMatchedResult().get(finalJ).setFavstatus("no");
                            }


                        });
                        mLinearScrollSecond[i].addView(mLinearView2);
                    }
                    mLinearListView.addView(mLinearView);

                }
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            } else {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                mNoData.setVisibility(View.VISIBLE);
            }
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            mNoData.setVisibility(View.VISIBLE);
        }
    }

    public void VisibleCompareButton(List<Integer> vehicleids) {
        int flag = 0;
        getBundle_vehicle_id = "";
        for (int i = 0; i < vehicleids.size(); i++) {
            if (vehicleids.get(i) != 0) {
                flag++;
                if (getBundle_vehicle_id.equals(""))
                    getBundle_vehicle_id = vehicleids.get(i).toString();
                else
                    getBundle_vehicle_id = getBundle_vehicle_id + "," + vehicleids.get(i).toString();
            }
        }

        if (flag > 1)
            relativeLayout.setVisibility(View.VISIBLE);
        else
            relativeLayout.setVisibility(View.GONE);
    }


    @Override
    public void notifyError(Throwable error) {
        mNoData.setVisibility(View.VISIBLE);
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
            Log.i("Check Class", "Seller Notification Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    private void call(String recieverContact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + recieverContact));
        try {
            getActivity().startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
