package autokatta.com.events;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import autokatta.com.response.AuctionAllVehicleData;
import autokatta.com.response.MyActiveAuctionNoBidResponse;
import retrofit2.Response;

/**
 * Created by ak-003 on 7/4/17.
 */

public class ActiveAuctionNoBidFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    public ActiveAuctionNoBidFragment() {
        //empty constructor
    }

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    String strAuctionId = "";

    @Override
    public View onCreateView(LayoutInflater infl, ViewGroup container, Bundle savedInstanceState) {

        View view = infl.inflate(R.layout.fragment_simple_listview, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerMain);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutMain);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bundle bundle = getArguments();
                    strAuctionId = bundle.getString("auctionid");

                    mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light);
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(true);

                            getNoBidVehicle(strAuctionId);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void getNoBidVehicle(String strAuctionId) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.ActiveAuctionNoBid(strAuctionId);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                ArrayList<AuctionAllVehicleData> auctionAllVehicleList = new ArrayList<>();
                auctionAllVehicleList.clear();
                MyActiveAuctionNoBidResponse noBidResponse = (MyActiveAuctionNoBidResponse) response.body();

                for (MyActiveAuctionNoBidResponse.Success.VehicleList success : noBidResponse.getSuccess().getVehicleList()) {


                    AuctionAllVehicleData auctionAllVehicleData = new AuctionAllVehicleData();

                    auctionAllVehicleData.setVehicleId(success.getAuctionid());
                    auctionAllVehicleData.setVehicleTitle(success.getTitle());
                    auctionAllVehicleData.setVehicleCategory(success.getCategory());
                    auctionAllVehicleData.setVehicleBrand(success.getBrand());
                    auctionAllVehicleData.setVehicleModel(success.getModel());
                    auctionAllVehicleData.setVehicleVersion(success.getVersion());
                    auctionAllVehicleData.setVehicleMfgYear(success.getYear());
                    auctionAllVehicleData.setVehicleLocation_city(success.getLocation());
                    auctionAllVehicleData.setVehicleRTOCity(success.getRtoCity());
                    auctionAllVehicleData.setVehicleColor(success.getColor());
                    // auctionAllVehicleData.setVehicleImages(success.getImage());
                    auctionAllVehicleData.setVehicleRegistrationNo(success.getRegNo());
                    auctionAllVehicleData.setVehicleStartPrice(success.getStartPrice());
                    auctionAllVehicleData.setVehicleReservedPrice(success.getReservePrice());

                    String img = success.getImage();
                    if (img.contains(",")) {
                        String arr[] = img.split(",", 2);

                        auctionAllVehicleData.setVehicleSingleImage(arr[0]);

                        String all = img.replace(",", "/ ");
                        auctionAllVehicleData.setVehicleImages(all);

                    } else {
                        auctionAllVehicleData.setVehicleSingleImage(img);
                        auctionAllVehicleData.setVehicleImages(img);
                    }

                    auctionAllVehicleList.add(auctionAllVehicleData);


                    //analyticsList.add(success);
                }
                mSwipeRefreshLayout.setRefreshing(false);
                ActiveAuctionNoBidAdapter adapter = new ActiveAuctionNoBidAdapter(getActivity(), strAuctionId, auctionAllVehicleList);
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
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
            Log.i("Check class", "Active Auction NoBid Fragment");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

    }
///////////////////////////////////////////////////////////////////////////////////////

    private class ActiveAuctionNoBidAdapter extends RecyclerView.Adapter<ActiveAuctionNoBidAdapter.MyViewHolder> {
        private Activity mActivity;
        private List<AuctionAllVehicleData> mItemList = new ArrayList<>();
        private String mAuctionId = "";

        ActiveAuctionNoBidAdapter(Activity mActivity, String auctionId, List<AuctionAllVehicleData> mItemList) {
            this.mActivity = mActivity;
            this.mItemList = mItemList;
            mAuctionId = auctionId;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            CardView mAuctionCardView;
            ImageView mAuctionVehicleImage;
            TextView mRegistrationNo, mSetLotNo, mVehicleName, mVehicleBrand, mVehicleModel, mVehicleYearOfMfg,
                    mVehicleKmsHrs, mVehicleLocation, mVehicleRtoCity, mViewMore;

            MyViewHolder(View itemView) {
                super(itemView);
                mAuctionCardView = (CardView) itemView.findViewById(R.id.auction_card_view);
                mAuctionVehicleImage = (ImageView) itemView.findViewById(R.id.auction_vehicle_image);

                mRegistrationNo = (TextView) itemView.findViewById(R.id.live_registration_no_txt);
                mSetLotNo = (TextView) itemView.findViewById(R.id.setlotno);
                mVehicleName = (TextView) itemView.findViewById(R.id.vehicle_name);
                mVehicleBrand = (TextView) itemView.findViewById(R.id.vehicle_brand);
                mVehicleModel = (TextView) itemView.findViewById(R.id.vehicle_model);
                mVehicleYearOfMfg = (TextView) itemView.findViewById(R.id.vehicle_year_of_mfg);
                mVehicleKmsHrs = (TextView) itemView.findViewById(R.id.vehicle_kms_hrs);
                mVehicleLocation = (TextView) itemView.findViewById(R.id.vehicle_locations);
                mVehicleRtoCity = (TextView) itemView.findViewById(R.id.vehicle_rto_city);
                mViewMore = (TextView) itemView.findViewById(R.id.view_more);
            }
        }

        @Override
        public ActiveAuctionNoBidAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_preview_live_event_list, parent, false);
            // set the view's size, margins, paddings and layout parameters
            ActiveAuctionNoBidAdapter.MyViewHolder vh = new ActiveAuctionNoBidAdapter.MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ActiveAuctionNoBidAdapter.MyViewHolder holder, final int position) {
            if (mItemList.get(position).getVehicleSingleImage() != null || mItemList.get(position).getVehicleSingleImage().equals("")
                    || mItemList.get(position).getVehicleSingleImage().isEmpty()) {
                String images[] = mItemList.get(position).getVehicleSingleImage().split(",");
                Glide.with(mActivity)
                        .load("http://autokatta.com/mobile/uploads/" + images[0].replaceAll(" ", "%20"))
                        //.bitmapTransform(new CropCircleTransformation(activity)) //To display image in Circular form.
                        .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                        //.placeholder(R.drawable.logo) //To show image before loading an original image.
                        //.error(R.drawable.blocked) //To show error image if problem in loading.
                        .override(100, 100)
                        .into(holder.mAuctionVehicleImage);
            } else {
                holder.mAuctionVehicleImage.setBackgroundResource(R.drawable.hdlogo);
            }

            holder.mRegistrationNo.setText(mItemList.get(position).getVehicleRegistrationNo());
            holder.mSetLotNo.setText(mItemList.get(position).getVehicleLotNo());
            holder.mVehicleName.setText(mItemList.get(position).getVehicleTitle());
            holder.mVehicleBrand.setText(mItemList.get(position).getVehicleBrand());
            holder.mVehicleModel.setText(mItemList.get(position).getVehicleModel());
            holder.mVehicleYearOfMfg.setText(mItemList.get(position).getVehicleMfgYear());
            holder.mVehicleKmsHrs.setText(mItemList.get(position).getVehicleKms());
            holder.mVehicleLocation.setText(mItemList.get(position).getVehicleLocation_city());
            holder.mVehicleRtoCity.setText(mItemList.get(position).getVehicleRTOCity());

            holder.mViewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mItemList.get(position).getVehicleId().startsWith("A ")) {
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
        }

        @Override
        public int getItemCount() {
            return mItemList.size();
        }
    }
}
