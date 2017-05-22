package autokatta.com.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

@SuppressLint("SimpleDateFormat")
public class BuyerNotificationFragment extends Fragment implements RequestNotifier {
    public BuyerNotificationFragment() {
    }

    public List<BuyerResponse.Success.Vehicle> mainList = new ArrayList<>();
    public List<BuyerResponse.Success.Found> childlist;
    private LinearLayout mLinearListView;
    boolean isFirstViewClick[];
    String myContact;
    String recieverContact;
    LinearLayout mLinearScrollSecond[];
    ApiCall mApiCall;

    @Override
    public View onCreateView(LayoutInflater infl, ViewGroup container, Bundle savedInstanceState) {
        View view = infl.inflate(R.layout.example, container, false);
        mLinearListView = (LinearLayout) view.findViewById(R.id.linear_ListView);
        mApiCall = new ApiCall(getActivity(), this);
        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).
                getString("loginContact", null);

        mApiCall.getUploadedVehicleBuyerlist(myContact);
        //mApiCall.getUploadedVehicleBuyerlist("2020202020");

        return view;
    }

    @Override
    public void notifySuccess(Response<?> response) {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (response.isSuccessful()) {

            if (response.isSuccessful()) {
                Log.i("seller Response", "" + response);
                BuyerResponse object = (BuyerResponse) response.body();
                BuyerResponse.Success objsuccess = (BuyerResponse.Success) object.getSuccess();

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
                    obj.setRegistrationNumber(obj.getRegistrationNumber());
                    obj.setKmsRunning(obj.getKmsRunning());
                    obj.setHrsRunning(obj.getHrsRunning());
                    obj.setLocationCity(obj.getLocationCity());
                    obj.setRcAvailable(obj.getRcAvailable());
                    obj.setNoOfOwners(obj.getNoOfOwners());
                    obj.setImage(obj.getImage());
                    obj.setInsuranceValid(obj.getInsuranceValid());
                    obj.setHpCapacity(obj.getHpCapacity());

                    for (BuyerResponse.Success.Found objectmatch : objsuccess.getFound()) {

                        if (obj.getVehicleId().equals(objectmatch.getVehicleId())) {

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

                            objectmatch.setLastcall(objectmatch.getLastcall());


                            Date d = null;
                            try {
                                d = f.parse(objectmatch.getLastcall());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            objectmatch.setLastCallDateNew(d);

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
                   final TextView myreg= (TextView) mLinearView.findViewById(R.id.yreg);
                    final TextView mRto_city = (TextView) mLinearView.findViewById(R.id.setrto);
                    final TextView mKms = (TextView) mLinearView.findViewById(R.id.setkms);
                    final TextView mRegno = (TextView) mLinearView.findViewById(R.id.setregno);

                    final TextView mmatchCount = (TextView) mLinearView.findViewById(R.id.match_count);

                    final ImageView mdownarrow = (ImageView) mLinearView.findViewById(R.id.postdownarrow);
                    final ImageView muparrow = (ImageView) mLinearView.findViewById(R.id.postuparrow);

                    ViewFlipper mViewFlipperbuyer = (ViewFlipper) mLinearView.findViewById(R.id.buyervehicalimgflicker);

                    final RelativeLayout mLinearFirstArrow = (RelativeLayout) mLinearView.findViewById(R.id.linearFirst);
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
                    final LinearLayout finalMLinearScrollSecond = mLinearScrollSecond[i];

                    //Handles onclick effect on list item
                    mLinearFirstArrow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //Toast.makeText(getActivity(),"clicked:"+villll,Toast.LENGTH_LONG).show();
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
                    if (mainList.get(i).getRtoCity().equalsIgnoreCase("Unregistered")||mainList.get(i).getRtoCity().equalsIgnoreCase("")||mainList.get(i).getRtoCity().isEmpty())
                    {
                        mRegno.setVisibility(View.GONE);
                        myreg.setVisibility(View.GONE);
                    }else
                    {
                        mRegno.setText(mainList.get(i).getRegistrationNumber());

                    }
                    mKms.setText(mainList.get(i).getKmsRunning());
                    mmatchCount.setText(String.valueOf(mainList.get(i).getFound().size()));

                    String count = String.valueOf(mainList.get(i).getFound().size());
                    System.out.print("Leads at position=" + i + "=" + count);

                    if (count.equalsIgnoreCase("0")) {
                        mdownarrow.setVisibility(View.GONE);
                        muparrow.setVisibility(View.GONE);
                    }


                    final String imagenames = mainList.get(i).getImage();

                    List<String> iname = new ArrayList<>();

                    try {
                        String[] imagenamecame = imagenames.split(",");

                        if (imagenamecame.length != 0) {
                            for (int z = 0; z < imagenamecame.length; z++) {
                                iname.add(imagenamecame[z]);
                            }

                            ImageView[] imageView = new ImageView[iname.size()];

                            for (int l = 0; l < imageView.length; l++) {
                                imageView[l] = new ImageView(getActivity());
                                Glide.with(getActivity())
                                        .load("http://autokatta.com/mobile/uploads/" + iname.get(l).replaceAll(" ", "%20"))
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


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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

                        mBuyerUserName.setText(mainList.get(i).getFound().get(j).getReceivername());
                        mBuyerLocation.setText(mainList.get(i).getFound().get(j).getLocationCity());

                        String image_buyer = mainList.get(i).getFound().get(j).getReceiverPic();


                        if (image_buyer.equals("")) {
                            buyer_lead_image.setBackgroundResource(R.drawable.logo);
                        } else {
                            try {

                                Glide.with(getActivity())
                                        .load("http://autokatta.com/mobile/profile_profile_pics/" + image_buyer)
                                        //.bitmapTransform(new CropCircleTransformation(getActivity()))
                                        .into(buyer_lead_image);
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "Error image uploading", Toast.LENGTH_LONG).show();
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


                        final String favStatus = mainList.get(i).getFound().get(j).getFavstatus();
                        final String vehicle_idget = mainList.get(i).getFound().get(j).getVehicleId();
                        final String search_idget = mainList.get(i).getFound().get(j).getSearchId();
                        final String lastcall = mainList.get(i).getFound().get(j).getLastcall();

                        if (favStatus.equalsIgnoreCase("yes")) {
                            favouritebuyer.setImageResource(R.drawable.fav2);
                            favouritebuyer.setEnabled(false);
                        } else {
                            favouritebuyer.setImageResource(R.drawable.fav1);
                        }


                        //mItemNameCity.setText("Last call on: " + lastcall);
                        //to set buyer last call date
                        try {

                            DateFormat date = new SimpleDateFormat(" MMM dd ");
                            DateFormat time = new SimpleDateFormat(" hh:mm a");

                            DateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd");
                            DateFormat newDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

                            /*holder.uploadDates.setText(newDateFormat.format(inputDate.parse(mGetOwnVehiclesList.get(position).getUploaddate())));

                            mItemNameCity.setText("Last call on:" + date.format(lastcall) +
                                    time.format(lastcall));*/

                            mItemNameCity.setText("Last call on:" + newDateFormat.format(inputDate.parse(lastcall)) + time.format(inputDate.parse(lastcall)));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        checkBox1.setText(mainList.get(i).getCategory());
                        checkBox2.setText(mainList.get(i).getManufacturer());
                        checkBox3.setText(mainList.get(i).getModel());
                        checkBox4.setText(mainList.get(i).getYearOfManufacture());
                        checkBox5.setText(mainList.get(i).getRtoCity());
                        if (mainList.get(i).getRcAvailable().startsWith("-Select RC Available-") ||
                                mainList.get(i).getRcAvailable().equalsIgnoreCase("") || mainList.get(i).getRcAvailable().equalsIgnoreCase("-"))
                            checkBoxRc.setText("RC avl-NA");
                        else
                            checkBoxRc.setText("RC avl-" + mainList.get(i).getRcAvailable());

                        if (mainList.get(i).getInsuranceValid().startsWith("-Select Insurance Valid-") ||
                                mainList.get(i).getInsuranceValid().equalsIgnoreCase("") || mainList.get(i).getInsuranceValid().equalsIgnoreCase("-"))
                            checkBoxIns.setText("INS avl-NA");
                        else
                            checkBoxIns.setText("INS avl-" + mainList.get(i).getInsuranceValid());

                        if (mainList.get(i).getHpCapacity().equalsIgnoreCase("-") || mainList.get(i).getHpCapacity().equalsIgnoreCase(""))
                            checkBoxHpcap.setText("Hp-NA");
                        else
                            checkBoxHpcap.setText("Hp-" + mainList.get(i).getHpCapacity());

                        checkBox6.setText(itemcategory);
                        checkBox7.setText(itemBrand);
                        checkBox8.setText(itemModel);
                        checkBox9.setText(itemYear);
                        checkBox10.setText(itemrto_city);
                        if (itemrc.startsWith("-Select RC Available-") || itemrc.equalsIgnoreCase("") || itemrc.equalsIgnoreCase("-"))
                            checkBoxRcRight.setText("RC avl-NA");
                        else
                            checkBoxRcRight.setText("RC avl-" + itemrc);
                        if (itemIns.startsWith("-Select Insurance Valid-") || itemIns.equalsIgnoreCase("") || itemIns.equalsIgnoreCase("-"))
                            checkBoxINSRight.setText("INS avl-NA");
                        else
                            checkBoxINSRight.setText("INS avl-" + itemIns);

                        if (itemHp.equalsIgnoreCase("") || itemHp.equalsIgnoreCase("-"))
                            checkBoxHPcapRight.setText("Hp-NA");
                        else
                            checkBoxHPcapRight.setText("Hp-" + itemHp);

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
                                System.out.println("Current time => " + c.getTime());

                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String calldate = df.format(c.getTime());

                                if (!recieverContact.equals(myContact))
                                    call(recieverContact);

                                mApiCall.sendLastCallDate(myContact, recieverContact, calldate, "1");
                            }
                        });


                        if (mainList.get(finalI).getFound().get(finalJ).getFavstatus().equalsIgnoreCase("yes")) {
                            favouritebuyer.setImageResource(R.drawable.fav2);
                            favouritebuyer.setEnabled(false);
                        } else {
                            favouritebuyer.setImageResource(R.drawable.fav1);
                        }

                        favouritebuyer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String searchid = mainList.get(finalI).getFound().get(finalJ).getSearchId();
                                String BuyerId = mainList.get(finalI).getFound().get(finalJ).getVehicleId()
                                        + "," + searchid;
                                mApiCall.addRemovefavouriteStatus(myContact, BuyerId, "", "");
                                favouritebuyer.setImageResource(R.drawable.fav2);

                                mainList.get(finalI).getFound().get(finalJ).setFavstatus("yes");
                            }


                        });

                        mLinearScrollSecond[i].addView(mLinearView2);

                    }

                    mLinearListView.addView(mLinearView);
                }
            }
        }

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check Class", "Buyer Notification Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

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
