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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.auction.AdminVehicleDetails;
import autokatta.com.auction.MyAuctionVehicleDetails;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.EndedAuctionApprovedVehiResponse;
import retrofit2.Response;

/**
 * Created by ak-003 on 10/4/17.
 */

public class MyEndedAuctionApprovedFragment extends Fragment implements RequestNotifier {

    public MyEndedAuctionApprovedFragment() {
        //empty constructor
    }

    private String keyword = "";
    private LinearLayout mLinearLayout;
    private String mAuctionId = "";
    private String mSpecialClauses = "";
    private String myContact = "";
    private String VehiId = "";
    private String RContact = "";
    List<EndedAuctionApprovedVehiResponse.BiddersList> mBiddersLists;
    List<EndedAuctionApprovedVehiResponse.VehicleList> mVehicleLists;
    LinearLayout mLinearScrollSecond[];
    boolean isFirstViewClick[];
    List<String> vehiImgList = new ArrayList<>();
    ApiCall mApiCall;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simple_linear_layout, container, false);

        mLinearLayout = (LinearLayout) view.findViewById(R.id.linear_Layout);
        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "7841023392");

        mApiCall = new ApiCall(getActivity(), this);
        bundle = getArguments();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    mAuctionId = bundle.getString("auctionid");
                    mSpecialClauses = bundle.getString("specialclauses");


                    mApiCall.EndedAuctionApprovedVehi(myContact, mAuctionId);
                    //mApiCall.EndedAuctionApprovedVehi(myContact, "379");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        return view;
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mVehicleLists = new ArrayList<>();
                mVehicleLists.clear();

                EndedAuctionApprovedVehiResponse mainSuccess = (EndedAuctionApprovedVehiResponse) response.body();
                EndedAuctionApprovedVehiResponse.Success subSuccess = mainSuccess.getSuccess();
                for (EndedAuctionApprovedVehiResponse.VehicleList vehicleList : subSuccess.getVehicleList()) {

                    mBiddersLists = new ArrayList<>();
                    mBiddersLists.clear();

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

                    for (EndedAuctionApprovedVehiResponse.BiddersList bidderList : subSuccess.getBiddersList()) {

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

                for (int i = 0; i < mVehicleLists.size(); i++) {

                    LayoutInflater inflater = null;
                    // int mFlippingsell = 0;

                    inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View mLinearView = inflater.inflate(R.layout.fragment_ended_auction_approve, null);


                    TextView Vehicletitle = (TextView) mLinearView.findViewById(R.id.vehicle_name);
                    TextView Vehiclemodel = (TextView) mLinearView.findViewById(R.id.vehicle_model);
                    TextView Vehiclebrand = (TextView) mLinearView.findViewById(R.id.vehicle_brand);
                    TextView Vehicleyear = (TextView) mLinearView.findViewById(R.id.vehicle_year_of_mfg);
                    TextView Moredetails = (TextView) mLinearView.findViewById(R.id.view_more);
                    TextView Startprice = (TextView) mLinearView.findViewById(R.id.startPrice);
                    TextView Reserveprice = (TextView) mLinearView.findViewById(R.id.reserveprice);
                    TextView BidReceive = (TextView) mLinearView.findViewById(R.id.bid);

                    TextView VehiRegno = (TextView) mLinearView.findViewById(R.id.live_registration_no_txt);
                    TextView VehiRtocity = (TextView) mLinearView.findViewById(R.id.vehicle_rto_city);
                    TextView Vehikms = (TextView) mLinearView.findViewById(R.id.vehicle_kms_hrs);
                    TextView Location = (TextView) mLinearView.findViewById(R.id.vehicle_locations);
                    final TextView lotNo = (TextView) mLinearView.findViewById(R.id.setlotno);

                    TextView ApproveDate = (TextView) mLinearView.findViewById(R.id.apprDate);
                    final RelativeLayout relApprove = (RelativeLayout) mLinearView.findViewById(R.id.relApprdate);
                    final RelativeLayout relReauction = (RelativeLayout) mLinearView.findViewById(R.id.relReauction);

                    Button approveVehi = (Button) mLinearView.findViewById(R.id.apprbtn);
                    final Button addToReauction = (Button) mLinearView.findViewById(R.id.reaucti);
                    Button bidderList = (Button) mLinearView.findViewById(R.id.bidderbtn);

                    ImageView Vehicleimage = (ImageView) mLinearView.findViewById(R.id.auction_vehicle_image);

                    approveVehi.setVisibility(View.GONE);

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
                    //ApproveDate.setText(mVehicleLists.get(i).getapproveddate());
                    BidReceive.setText(mVehicleLists.get(i).getBidReceived());

                    VehiRegno.setText(mVehicleLists.get(i).getRegNo());
                    VehiRtocity.setText(mVehicleLists.get(i).getRtoCity());
                    Vehikms.setText(mVehicleLists.get(i).getKmsRunning());
                    Location.setText(mVehicleLists.get(i).getLocation());
                    lotNo.setText(mVehicleLists.get(i).getLotNo());
                    //Vehikms.setText(mVehicleLists.get(i).getvehihrs());

                    VehiId = mVehicleLists.get(i).getVehicleid();

                    String vehicleSingleImg = "";
                    if ((mVehicleLists.get(i).getImage() == null) || mVehicleLists.get(i).getImage().equals("") || mVehicleLists.get(i).getImage().equals("null")) {

                        Vehicleimage.setBackgroundResource(R.drawable.vehiimg);
                        vehicleSingleImg = "http://autokatta.com/mobile/uploads/amitkamble.jpg";

                    } else {

                        String[] imagenamecame = mVehicleLists.get(i).getImage().split(",");

                        for (String anImagenamecame : imagenamecame) {
                            vehiImgList.add(anImagenamecame.replaceAll(" ", "%20"));
                        }

                        Log.i("imageList=", "" + vehiImgList.size());

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
                                Bundle b = new Bundle();
                                b.putString("vehicle_id", mVehicleLists.get(finalI).getVehicleid());
                                b.putString("auction_id", mAuctionId);

                                Intent intent = new Intent(getActivity(), MyAuctionVehicleDetails.class);
                                intent.putExtras(b);
                                getActivity().startActivity(intent);
                                getActivity().finish();


                            } else {
                                Bundle b = new Bundle();
                                b.putString("vehicle_id", mVehicleLists.get(finalI).getVehicleid());
                                b.putString("lotNo", lotNo.getText().toString());

                                Intent intent = new Intent(getActivity(), AdminVehicleDetails.class);
                                intent.putExtras(b);
                                getActivity().startActivity(intent);
                                getActivity().finish();


                            }
                        }
                    });


                    if (mVehicleLists.get(finalI).getVehicleStatus().equalsIgnoreCase("yes")) {
                        addToReauction.setVisibility(View.GONE);
                        relReauction.setVisibility(View.VISIBLE);

                    }
                    if (mVehicleLists.get(finalI).getVehicleStatus().equalsIgnoreCase("no")) {
                        addToReauction.setVisibility(View.VISIBLE);
                        relReauction.setVisibility(View.GONE);
                    }
                    //Add vehicle in reauction
                    addToReauction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addvehicleToReauction(mVehicleLists.get(finalI).getVehicleid());
                            addToReauction.setVisibility(View.GONE);
                            relReauction.setVisibility(View.VISIBLE);
                            mVehicleLists.get(finalI).setVehicleStatus("yes");
                        }
                    });

                    //Adds data into second row
                    for (int j = 0; j < mVehicleLists.get(i).getBiddersList().size(); j++) {
                        LayoutInflater inflater2 = null;

                        inflater2 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View mLinearView2 = inflater2.inflate(R.layout.fragment_ended_auction_bidderlist, null);

                        TextView name = (TextView) mLinearView2.findViewById(R.id.name);
                        TextView bidprice = (TextView) mLinearView2.findViewById(R.id.bidprice);
                        TextView vehicleCnt = (TextView) mLinearView2.findViewById(R.id.no_of_vehicle);
                        ImageView cal_img = (ImageView) mLinearView2.findViewById(R.id.call);
                        Button approve = (Button) mLinearView2.findViewById(R.id.approve);
                        Button reject = (Button) mLinearView2.findViewById(R.id.reject);
                        final Button addBacklist = (Button) mLinearView2.findViewById(R.id.backlist);
                        final Button removeBacklist = (Button) mLinearView2.findViewById(R.id.backlist1);


                        approve.setVisibility(View.GONE);
                        reject.setVisibility(View.GONE);

                        name.setText(mVehicleLists.get(i).getBiddersList().get(j).getBidderName());
                        bidprice.setText(mVehicleLists.get(i).getBiddersList().get(j).getBidPrice());
                        vehicleCnt.setText(mVehicleLists.get(i).getBiddersList().get(j).getNoOfBids());
                        ApproveDate.setText(mVehicleLists.get(i).getBiddersList().get(j).getApprovedDate());


                        String bidAmount = mVehicleLists.get(i).getBiddersList().get(j).getBidPrice();

                        final int finalJ1 = j;
                        cal_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (mVehicleLists.get(finalI).getBiddersList().get(finalJ1).getContact().equals(myContact)) {
                                    Snackbar.make(v, "You can't call yourself", Snackbar.LENGTH_SHORT).show();
                                } else {
//                                    action = "call";
//                                    //vehicle_id = v_ids[position];
//                                    Rcontact = holder.contact.getText().toString();
//                                    new submitview().execute();
                                    call(mVehicleLists.get(finalI).getBiddersList().get(finalJ1).getContact());
                                }
                            }
                        });

                        //    Blacklist Status
                        if (mVehicleLists.get(i).getBiddersList().get(j).getBlackList().equals("yes")) {
                            addBacklist.setVisibility(View.GONE);
                            removeBacklist.setVisibility(View.VISIBLE);
                        }
                        if (mVehicleLists.get(i).getBiddersList().get(j).getBlackList().equals("no")) {
                            addBacklist.setVisibility(View.VISIBLE);
                            removeBacklist.setVisibility(View.GONE);
                        }
                        //Add to blacklist
                        final int finalJ = j;
                        addBacklist.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                keyword = "blacklist";
                                addToBlacklist(mVehicleLists.get(finalI).getBiddersList().get(finalJ1).getContact());
                                addBacklist.setVisibility(View.GONE);
                                removeBacklist.setVisibility(View.VISIBLE);
                                mVehicleLists.get(finalI).getBiddersList().get(finalJ).setBlackList("yes");
                            }
                        });
                        //Remove from blacklist
                        removeBacklist.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                keyword = "remove";
                                addToBlacklist(mVehicleLists.get(finalI).getBiddersList().get(finalJ1).getContact());
                                addBacklist.setVisibility(View.VISIBLE);
                                removeBacklist.setVisibility(View.GONE);
                                mVehicleLists.get(finalI).getBiddersList().get(finalJ).setBlackList("no");
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

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check class", "MyEnded Auction Approved Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("success")) {
                if (keyword.equals("blacklist")) {
                    Toast.makeText(getActivity(), "Add To Blacklist", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Remove from blacklist", Toast.LENGTH_SHORT).show();
                }
            } else if (str.equals("success_reauction"))
                Toast.makeText(getActivity(), "Vehicle added to reauction", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getActivity(), "Problem while adding vehicle to reauction", Toast.LENGTH_SHORT).show();


        } else
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
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

    private void addvehicleToReauction(String vehicleid) {
        mApiCall.addToReauction(vehicleid, mAuctionId);
        //mApiCall.addToReauction(vehicleid, "379");
    }

    private void addToBlacklist(String rContact) {
        mApiCall.Add_RemoveBlacklistContact(myContact, mAuctionId, rContact, keyword);
        //mApiCall.Add_RemoveBlacklistContact(myContact, "379", rContact, keyword);
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
}
