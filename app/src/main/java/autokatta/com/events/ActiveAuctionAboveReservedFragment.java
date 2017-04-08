package autokatta.com.events;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyActiveAuctionAboveReservedResponse;
import retrofit2.Response;

/**
 * Created by ak-003 on 7/4/17.
 */

public class ActiveAuctionAboveReservedFragment extends Fragment implements RequestNotifier {

    public ActiveAuctionAboveReservedFragment() {
        //empty constructor
    }

    private LinearLayout mLinearLayout;
    private String mAuctionId = "";
    private String myContact = "";
    private String VehiId = "";
    private String RContact = "";
    List<MyActiveAuctionAboveReservedResponse.BiddersList> mBiddersLists;
    List<MyActiveAuctionAboveReservedResponse.VehicleList> mVehicleLists;
    LinearLayout mLinearScrollSecond[];
    boolean isFirstViewClick[];
    List<String> vehiImgList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simple_linear_layout, container, false);

        mLinearLayout = (LinearLayout) view.findViewById(R.id.linear_Layout);
        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "7841023392");

        Bundle bundle = getArguments();
        mAuctionId = bundle.getString("auctionid");

        ApiCall mApiCall = new ApiCall(getActivity(), this);
        //mApiCall.ActiveAuctionAboveReservedPrice(myContact, "1047");
        mApiCall.ActiveAuctionAboveReservedPrice(myContact, mAuctionId);

        return view;
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mVehicleLists = new ArrayList<>();

                MyActiveAuctionAboveReservedResponse mainSuccess = (MyActiveAuctionAboveReservedResponse) response.body();
                MyActiveAuctionAboveReservedResponse.Success subSuccess = mainSuccess.getSuccess();
                for (MyActiveAuctionAboveReservedResponse.VehicleList vehicleList : subSuccess.getVehicleList()) {

                    mBiddersLists = new ArrayList<>();

                    vehicleList.setVehicleid(vehicleList.getVehicleid());
                    vehicleList.setAuctionid(vehicleList.getAuctionid());
                    vehicleList.setTitle(vehicleList.getTitle());
                    vehicleList.setModel(vehicleList.getModel());
                    vehicleList.setBrand(vehicleList.getBrand());
                    vehicleList.setImage(vehicleList.getImage());
                    vehicleList.setYear(vehicleList.getYear());
                    vehicleList.setStartPrice(vehicleList.getStartPrice());
                    vehicleList.setReservePrice(vehicleList.getReservePrice());
                    vehicleList.setBidReceived(vehicleList.getBidReceived());
                    vehicleList.setRegNo(vehicleList.getRegNo());
                    vehicleList.setRtoCity(vehicleList.getRtoCity());
                    vehicleList.setKmsRunning(vehicleList.getKmsRunning());
                    vehicleList.setHrsRunning(vehicleList.getHrsRunning());
                    vehicleList.setLocation(vehicleList.getLocation());
                    vehicleList.setLotNo(vehicleList.getLotNo());

                    for (MyActiveAuctionAboveReservedResponse.BiddersList bidderList : subSuccess.getBiddersList()) {

                        if (vehicleList.getVehicleid().equals(bidderList.getVehicleid())) {

                            bidderList.setAuctionid(bidderList.getAuctionid());
                            bidderList.setContact(bidderList.getContact());
                            bidderList.setBidPrice(bidderList.getBidPrice());
                            bidderList.setBidderName(bidderList.getBidderName());
                            bidderList.setNoOfBids(bidderList.getNoOfBids());

                            mBiddersLists.add(bidderList);
                        }
                    }
                    vehicleList.setBiddersList(mBiddersLists);
                    mVehicleLists.add(vehicleList);

                    Log.i("sub list", "" + mBiddersLists.size());
                    Log.i("Main list", "" + mVehicleLists.size());
                }

                mLinearScrollSecond = new LinearLayout[mVehicleLists.size()];
                isFirstViewClick = new boolean[mVehicleLists.size()];


                //Adds data into first row
                for (int i = 0; i < mVehicleLists.size(); i++) {

                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View mLinearView = inflater.inflate(R.layout.fragment_active_highbid, null);


                    TextView Vehicletitle = (TextView) mLinearView.findViewById(R.id.vehicle_name);
                    TextView Vehiclemodel = (TextView) mLinearView.findViewById(R.id.vehicle_model);
                    TextView Vehiclebrand = (TextView) mLinearView.findViewById(R.id.vehicle_brand);
                    TextView Vehicleyear = (TextView) mLinearView.findViewById(R.id.vehicle_year_of_mfg);
                    TextView Moredetails = (TextView) mLinearView.findViewById(R.id.view_more);
                    TextView Startprice = (TextView) mLinearView.findViewById(R.id.startPrice);
                    TextView Reserveprice = (TextView) mLinearView.findViewById(R.id.reserveprice);
                    TextView BidReceive = (TextView) mLinearView.findViewById(R.id.bid);
                    Button bidderList = (Button) mLinearView.findViewById(R.id.bidderbtn);

                    TextView VehiRegno = (TextView) mLinearView.findViewById(R.id.live_registration_no_txt);
                    TextView VehiRtocity = (TextView) mLinearView.findViewById(R.id.vehicle_rto_city);
                    TextView Vehikms = (TextView) mLinearView.findViewById(R.id.vehicle_kms_hrs);
                    TextView Location = (TextView) mLinearView.findViewById(R.id.vehicle_locations);
                    final TextView lotNo = (TextView) mLinearView.findViewById(R.id.setlotno);

                    ImageView Vehicleimage = (ImageView) mLinearView.findViewById(R.id.auction_vehicle_image);
                    mLinearScrollSecond[i] = (LinearLayout) mLinearView.findViewById(R.id.linear2);

                    //checkes if menu is already opened or not
                    if (!isFirstViewClick[i]) {
                        mLinearScrollSecond[i].setVisibility(View.GONE);

                    } else {
                        mLinearScrollSecond[i].setVisibility(View.VISIBLE);
                    }

                    final int listPos = i;

                    bidderList.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            setViewsVisible(listPos);
//
                        }
                    });

                    //To set underlined string to textview
                    SpannableString spanString = new SpannableString("More Details");
                    spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
                    Moredetails.setText(spanString);

                    Vehicletitle.setText(mVehicleLists.get(i).getTitle());
                    Vehiclemodel.setText(mVehicleLists.get(i).getModel());
                    Vehiclebrand.setText(mVehicleLists.get(i).getBrand());
                    Vehicleyear.setText(mVehicleLists.get(i).getYear());
                    Startprice.setText(mVehicleLists.get(i).getStartPrice());
                    Reserveprice.setText(mVehicleLists.get(i).getReservePrice());
                    BidReceive.setText(mVehicleLists.get(i).getBidReceived());

                    VehiRegno.setText(mVehicleLists.get(i).getRegNo());
                    VehiRtocity.setText(mVehicleLists.get(i).getRtoCity());
                    Vehikms.setText(mVehicleLists.get(i).getKmsRunning());
                    Location.setText(mVehicleLists.get(i).getLocation());
                    lotNo.setText(mVehicleLists.get(i).getLotNo());
                    //Vehikms.setText(mVehicleLists.get(i).getvehihrs());


                    String vehicleSingleImg = "";
                    if ((mVehicleLists.get(i).getImage() == null) || mVehicleLists.get(i).getImage().equals("") || mVehicleLists.get(i).getImage().equals("null")) {

                        Vehicleimage.setBackgroundResource(R.drawable.vehiimg);
                        vehicleSingleImg = "http://autokatta.com/mobile/uploads/amitkamble.jpg";

                    } else {

                        String[] imagenamecame = mVehicleLists.get(i).getImage().split(",");

                        for (String anImagenamecame : imagenamecame) {
                            vehiImgList.add(anImagenamecame.replaceAll(" ", "%20"));
                        }

                        Log.i("Image list", "->" + vehiImgList.size());

                        vehicleSingleImg = vehiImgList.get(0);

                        Glide.with(getActivity())
                                .load("http://autokatta.com/mobile/uploads/" + vehicleSingleImg)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .override(100, 100)
                                .into(Vehicleimage);
                    }

                    final int finalI = i;
                    Moredetails.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!mVehicleLists.get(finalI).getVehicleid().startsWith("A ")) {
                                /*Bundle b = new Bundle();
                                MyAuctionVehicleDetails frag = new MyAuctionVehicleDetails();
                                b.putString("vehicle_id", VehiId);
                                b.putString("auction_id", auction_id);
                                frag.setArguments(b);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.containerView, frag);
                                fragmentTransaction.addToBackStack("myauctionvehicledetails");
                                fragmentTransaction.commit();*/
                            } else {
                                /*Toast.makeText(getActivity(),"Admin vehicle",Toast.LENGTH_SHORT).show();
                                Bundle b = new Bundle();
                                b.putString("vehicle_id", VehiId);
                                b.putString("lotNo", lotNo.getText().toString());

                                AdminVehicleMoreDetails fragment = new AdminVehicleMoreDetails();
                                fragment.setArguments(b);
                                FragmentManager mFragmentManagerm = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = mFragmentManagerm.beginTransaction();
                                fragmentTransaction.replace(R.id.containerView, fragment).addToBackStack("adminvehiclemoredetails").commit();
*/
                            }
                        }
                    });
                    //Adds data into second row
                    for (int j = 0; j < mVehicleLists.get(i).getBiddersList().size(); j++) {
                        LayoutInflater inflater2 = null;

                        inflater2 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View mLinearView2 = inflater2.inflate(R.layout.fragment_active_highbid_bidders, null);

                        TextView name = (TextView) mLinearView2.findViewById(R.id.name);
                        TextView bidprice = (TextView) mLinearView2.findViewById(R.id.bidprice);
                        TextView vehicleCnt = (TextView) mLinearView2.findViewById(R.id.no_of_vehicle);
                        ImageView cal_img = (ImageView) mLinearView2.findViewById(R.id.call);
                        Button approve = (Button) mLinearView2.findViewById(R.id.approve);

                        approve.setVisibility(View.GONE);
                        name.setText(mVehicleLists.get(i).getBiddersList().get(j).getBidderName());
                        bidprice.setText(mVehicleLists.get(i).getBiddersList().get(j).getBidPrice());
                        vehicleCnt.setText(mVehicleLists.get(i).getBiddersList().get(j).getNoOfBids());


                        final int finalI1 = i;
                        final int finalJ = j;
                        cal_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mVehicleLists.get(finalI1).getBiddersList().get(finalJ).getContact().equals(myContact)) {
                                    Snackbar.make(v, "You can't call yourself", Snackbar.LENGTH_SHORT).show();
                                } else {
//                                    action = "call";
//                                    //vehicle_id = v_ids[position];
//                                    Rcontact = holder.contact.getText().toString();
//                                    new submitview().execute();
                                    call(mVehicleLists.get(finalI1).getBiddersList().get(finalJ).getContact());
                                    System.out.println("Calling Image Called From vehicle image load adapter \n");
                                }
                            }
                        });

                        mLinearScrollSecond[i].addView(mLinearView2);

                    }

                    mLinearLayout.addView(mLinearView);
                }

            } else
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));

    }

    private void call(String rContact) {
        //Calling Functionality
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + rContact));
        try {
            getActivity().startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Car Details Fragment\n");
        }

    }

    private void setViewsVisible(int listPos) {
        for (int i = 0; i < mVehicleLists.size(); i++) {
            if (listPos == i && !isFirstViewClick[i]) {
                isFirstViewClick[i] = true;

                mLinearScrollSecond[i].setVisibility(View.VISIBLE);
            } else {
                isFirstViewClick[i] = false;

                mLinearScrollSecond[i].setVisibility(View.GONE);
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
            Log.i("Check class", "Active Auction AboveReservedBid Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
