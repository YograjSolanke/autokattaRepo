package autokatta.com.auction;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.YourBidResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ak-001 on 6/4/17.
 */

class BidRecyclerAdapter extends RecyclerView.Adapter<BidRecyclerAdapter.MyViewHolder> {

    private Activity mActivity;
    private List<YourBidResponse.Success> mItemList = new ArrayList<>();
    private String openClose, showPrice;
    private int auctionId;
    private ConnectionDetector mConnectionDetector;
    private String result;
    private String tabNo;
    private TextView mLimitForBid;

    BidRecyclerAdapter(Activity mActivity, List<YourBidResponse.Success> mItemList, int auctionId, String openClose,
                       String showPrice, String tabNo, TextView limitForBid) {
        this.mActivity = mActivity;
        this.mItemList = mItemList;
        this.auctionId = auctionId;
        this.openClose = openClose;
        this.showPrice = showPrice;
        this.tabNo = tabNo;
        this.mLimitForBid = limitForBid;
        mConnectionDetector = new ConnectionDetector(mActivity);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mRegistrationNo, mLotNo, mTitle, mBrand, mModel, mYear, mKms, mRc, mLocation, mRtoCity, mBidPrice,
                mBidReceive, mBidStatus, mStartPrice, mBidIncrement, mStartPriceText;
        ImageView mImageView;
        Button mBid;

        MyViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.your_bid_card_view);

            mRegistrationNo = (TextView) itemView.findViewById(R.id.your_bid_registration_txt);
            mLotNo = (TextView) itemView.findViewById(R.id.your_bid_setlotno);
            mTitle = (TextView) itemView.findViewById(R.id.your_bid_name);
            mBrand = (TextView) itemView.findViewById(R.id.your_bid_brand);
            mModel = (TextView) itemView.findViewById(R.id.your_bid_model);
            mYear = (TextView) itemView.findViewById(R.id.your_bid_year);
            mKms = (TextView) itemView.findViewById(R.id.your_bid_kms_hrs);
            mRc = (TextView) itemView.findViewById(R.id.your_bid_rc);
            mLocation = (TextView) itemView.findViewById(R.id.your_bid_locations);
            mRtoCity = (TextView) itemView.findViewById(R.id.your_bid_rto_city);
            mBidPrice = (TextView) itemView.findViewById(R.id.current_bid_price);
            mBidReceive = (TextView) itemView.findViewById(R.id.bidrecieved);
            mBidStatus = (TextView) itemView.findViewById(R.id.bidderstatus);
            mStartPrice = (TextView) itemView.findViewById(R.id.startprice);
            mBidIncrement = (TextView) itemView.findViewById(R.id.bidincreament);
            mStartPriceText = (TextView) itemView.findViewById(R.id.startpricetxt);

            mImageView = (ImageView) itemView.findViewById(R.id.your_bid_vehicle_image);
            mBid = (Button) itemView.findViewById(R.id.bid);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_your_bid_recycle_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.mRegistrationNo.setText(mItemList.get(position).getRegNo());
        holder.mLotNo.setText(mItemList.get(position).getLotNo());
        holder.mTitle.setText(mItemList.get(position).getTitle());
        holder.mBrand.setText(mItemList.get(position).getBrand());
        holder.mModel.setText(mItemList.get(position).getModel());
        holder.mYear.setText(mItemList.get(position).getYear());
        holder.mKms.setText(mItemList.get(position).getKmsRunning());
        holder.mRc.setText(mItemList.get(position).getRcAvailable());
        holder.mLocation.setText(mItemList.get(position).getLocationCity());
        holder.mRtoCity.setText(mItemList.get(position).getRtoCity());
        holder.mBidPrice.setText(mItemList.get(position).getCurrentBidPrice());
        holder.mBidReceive.setText(mItemList.get(position).getBidReceivedPrice());
        holder.mStartPrice.setText(mItemList.get(position).getStartPrice());
        holder.mBidIncrement.setText(mActivity.getString(R.string.bid_increment));

        holder.mBidStatus.setVisibility(View.VISIBLE);
        holder.mBidStatus.setText(mActivity.getString(R.string.bid_status));

        if (mItemList.get(position).getKmsRunning() != null || !mItemList.get(position).getKmsRunning().equals("")) {
            holder.mKms.setText(mItemList.get(position).getKmsRunning() + " " + "Kms");
        } else {
            holder.mKms.setText(mItemList.get(position).getKmsRunning() + " " + "Hrs");
        }

        if (openClose.equalsIgnoreCase("Open")) {
            holder.mBidReceive.setVisibility(View.VISIBLE);
            if (mItemList.get(position).getBidReceivedPrice() != null || !mItemList.get(position).getBidReceivedPrice().isEmpty()
                    || !mItemList.get(position).getBidReceivedPrice().equals("0") || !mItemList.get(position).getBidReceivedPrice().equals("null"))
                holder.mBidReceive.setText("Bid received" + mActivity.getString(R.string.Rs)
                        + mItemList.get(position).getBidReceivedPrice());
            else
                holder.mBidReceive.setText(mActivity.getString(R.string.bid_received));
        } else
            holder.mBidReceive.setVisibility(View.GONE);


        if (showPrice.equalsIgnoreCase("Show")) {
            holder.mStartPrice.setVisibility(View.VISIBLE);
            holder.mStartPriceText.setVisibility(View.VISIBLE);
            holder.mStartPrice.setText("Rs." + " " + mItemList.get(position).getStartPrice());
        } else {
            holder.mStartPrice.setVisibility(View.GONE);
            holder.mStartPriceText.setVisibility(View.GONE);
        }

        holder.mBidPrice.setVisibility(View.VISIBLE);
        holder.mBidPrice.setText("Your current bid price Rs." + " " + mItemList.get(position).getCurrentBidPrice());

        if (mItemList.get(position).getImage() == null || mItemList.get(position).getImage().equals("") ||
                mItemList.get(position).getImage().equals("null")) {
            holder.mImageView.setBackgroundResource(R.drawable.vehiimg);
        } else {
            String images[] = mItemList.get(position).getImage().split(",");
            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.base_image_url) + images[0].replaceAll(" ", "%20"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    //.placeholder(R.drawable.logo) //To show image before loading an original image.
                    //.error(R.drawable.blocked) //To show error image if problem in loading.
                    .into(holder.mImageView);
        }

        holder.mBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLimitForBid.getText().toString().equals("0")) {
                    Toast.makeText(mActivity, "Your bid limit is 0, please pay EMD", Toast.LENGTH_LONG).show();
                    //paymentMethodCall();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
                    alertDialog.setTitle("Bid for vehicle");
                    alertDialog.setMessage("Enter bid amount");

                    final EditText input = new EditText(mActivity);
                    input.setHint("Amount");
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    alertDialog.setView(input);
                    // alertDialog.setIcon(R.drawable.key);

                    alertDialog.setPositiveButton("Bid",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        String BidAmount = input.getText().toString();
                                        if (BidAmount.equals("")) {
                                            input.setError("Please Enter Amount");
                                            Toast.makeText(mActivity, "Price should not be empty", Toast.LENGTH_LONG).show();
                                        } else if (tabNo.equals("0")) {
                                            Long IntBidAmount = Long.parseLong(BidAmount);
                                            Long IntCurrentBidPrice = Long.parseLong(mItemList.get(holder.getAdapterPosition()).getCurrentBidPrice());
                                            if (IntBidAmount >= IntCurrentBidPrice) {
                                                try {
                                                    if (mConnectionDetector.isConnectedToInternet()) {
                                                        //JSON to Gson conversion
                                                        Gson gson = new GsonBuilder()
                                                                .setLenient()
                                                                .create();
                                                        Retrofit retrofit = new Retrofit.Builder()
                                                                .baseUrl(mActivity.getString(R.string.base_url))
                                                                .addConverterFactory(GsonConverterFactory.create(gson))
                                                                .client(initLog().build())
                                                                .build();

                                                        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                                                        Call<String> addBid = serviceApi.addMyBid(auctionId, mItemList.get(holder.getAdapterPosition()).getVehicleid(),
                                                                BidAmount, "0", mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference),
                                                                        Context.MODE_PRIVATE).getString("loginContact", ""));
                                                        addBid.enqueue(new Callback<String>() {
                                                            @Override
                                                            public void onResponse(Call<String> call, Response<String> response) {
                                                                if (response.isSuccessful()) {
                                                                    result = response.body();
                                                                    if (result.equals("same")) {
                                                                        Toast.makeText(mActivity, "Same bid amount not acceptable..!", Toast.LENGTH_LONG).show();
                                                                    } else if (result.startsWith("0")) {
                                                                        Toast.makeText(mActivity, "Thanks for your bid", Toast.LENGTH_LONG).show();
                                                                        callToLiveAuctionBidding("0");
                                                                    } else if (result.startsWith("1")) {
                                                                        Toast.makeText(mActivity, "Thanks for your bid", Toast.LENGTH_LONG).show();
                                                                        callToLiveAuctionBidding("1");
                                                                    } else {
                                                                        Toast.makeText(mActivity, "Bid should be greater than your previous bid!", Toast.LENGTH_LONG).show();
                                                                    }
                                                                } else {
                                                                    Log.e("No", "Response");
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<String> call, Throwable t) {
                                                                t.printStackTrace();
                                                            }
                                                        });
                                                    } else
                                                        CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                Toast.makeText(mActivity, "Bid should not be reverse", Toast.LENGTH_LONG).show();
                                            }
                                        } else if (tabNo.equals("1")) {
                                            Long IntBidAmount = Long.parseLong(BidAmount);
                                            Long IntCurrentBidPrice = Long.parseLong(mItemList.get(holder.getAdapterPosition()).getCurrentBidPrice());
                                            if (IntBidAmount >= IntCurrentBidPrice) {
                                                try {
                                                    if (mConnectionDetector.isConnectedToInternet()) {
                                                        //JSON to Gson conversion
                                                        Gson gson = new GsonBuilder()
                                                                .setLenient()
                                                                .create();
                                                        Retrofit retrofit = new Retrofit.Builder()
                                                                .baseUrl(mActivity.getString(R.string.base_url))
                                                                .addConverterFactory(GsonConverterFactory.create(gson))
                                                                .client(initLog().build())
                                                                .build();
                                                        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                                                        Call<String> addBid = serviceApi.addMyBid(auctionId, mItemList.get(holder.getAdapterPosition()).getVehicleid(),
                                                                BidAmount, "1", mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference),
                                                                        Context.MODE_PRIVATE).getString("loginContact", ""));
                                                        addBid.enqueue(new Callback<String>() {
                                                            @Override
                                                            public void onResponse(Call<String> call, Response<String> response) {
                                                                if (response.isSuccessful()) {
                                                                    result = response.body();
                                                                    if (result.startsWith("same"))
                                                                        Toast.makeText(mActivity, "Same bid amount not acceptable..!", Toast.LENGTH_LONG).show();
                                                                    else if (result.startsWith("1")) {
                                                                        Toast.makeText(mActivity, "Thanks for your bid", Toast.LENGTH_LONG).show();
                                                                        callToLiveAuctionBidding("1");
                                                                    } else {
                                                                        Toast.makeText(mActivity, "Bid should be greater than your previous bid!", Toast.LENGTH_LONG).show();
                                                                    }
                                                                } else {
                                                                    Log.e("No", "Response");
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<String> call, Throwable t) {
                                                                t.printStackTrace();
                                                            }
                                                        });
                                                    } else
                                                        CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                Toast.makeText(mActivity, "Bid should not be reverse", Toast.LENGTH_LONG).show();
                                            }
                                        } else if (tabNo.equals("2")) {
                                            Long IntBidAmount = Long.parseLong(BidAmount);
                                            Long IntCurrentBidPrice = Long.parseLong(mItemList.get(holder.getAdapterPosition()).getCurrentBidPrice());
                                            if (IntBidAmount >= IntCurrentBidPrice) {
                                                try {
                                                    if (mConnectionDetector.isConnectedToInternet()) {
                                                        //JSON to Gson conversion
                                                        Gson gson = new GsonBuilder()
                                                                .setLenient()
                                                                .create();
                                                        Retrofit retrofit = new Retrofit.Builder()
                                                                .baseUrl(mActivity.getString(R.string.base_url))
                                                                .addConverterFactory(GsonConverterFactory.create(gson))
                                                                .client(initLog().build())
                                                                .build();
                                                        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                                                        Call<String> addBid = serviceApi.addMyBid(auctionId, mItemList.get(holder.getAdapterPosition()).getVehicleid(),
                                                                BidAmount, "2", mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference),
                                                                        Context.MODE_PRIVATE).getString("loginContact", ""));
                                                        addBid.enqueue(new Callback<String>() {
                                                            @Override
                                                            public void onResponse(Call<String> call, Response<String> response) {
                                                                if (response.isSuccessful()) {
                                                                    result = response.body();
                                                                    if (result.startsWith("same"))
                                                                        Toast.makeText(mActivity, "Same bid amount not acceptable..!", Toast.LENGTH_LONG).show();
                                                                    else if (result.startsWith("0")) {
                                                                        Toast.makeText(mActivity, "Thanks for your bid", Toast.LENGTH_LONG).show();
                                                                        callToLiveAuctionBidding("2");
                                                                    } else if (result.startsWith("1")) {
                                                                        Toast.makeText(mActivity, "Thanks for your bid", Toast.LENGTH_LONG).show();
                                                                        callToLiveAuctionBidding("1");
                                                                    } else if (result.startsWith("2")) {
                                                                        Toast.makeText(mActivity, "Thanks for your bid", Toast.LENGTH_LONG).show();
                                                                        callToLiveAuctionBidding("2");
                                                                    } else {
                                                                        Toast.makeText(mActivity, "Bid should be greater than your previous bid!", Toast.LENGTH_LONG).show();
                                                                    }
                                                                } else {
                                                                    Log.e("No", "Response");
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<String> call, Throwable t) {
                                                                t.printStackTrace();
                                                            }
                                                        });
                                                    } else
                                                        CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                Toast.makeText(mActivity, "Bid should not be reverse", Toast.LENGTH_LONG).show();
                                            }
                                        } else if (tabNo.equals("3")) {
                                            Long IntBidAmount = Long.parseLong(BidAmount);
                                            Long IntCurrentBidPrice = Long.parseLong("0");
                                            if (IntBidAmount >= IntCurrentBidPrice) {
                                                try {
                                                    if (mConnectionDetector.isConnectedToInternet()) {
                                                        //JSON to Gson conversion
                                                        Gson gson = new GsonBuilder()
                                                                .setLenient()
                                                                .create();
                                                        Retrofit retrofit = new Retrofit.Builder()
                                                                .baseUrl(mActivity.getString(R.string.base_url))
                                                                .addConverterFactory(GsonConverterFactory.create(gson))
                                                                .client(initLog().build())
                                                                .build();
                                                        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                                                        Call<String> addBid = serviceApi.addMyBid(auctionId, mItemList.get(holder.getAdapterPosition()).getVehicleid(),
                                                                BidAmount, "0", mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference),
                                                                        Context.MODE_PRIVATE).getString("loginContact", ""));
                                                        addBid.enqueue(new Callback<String>() {
                                                            @Override
                                                            public void onResponse(Call<String> call, Response<String> response) {
                                                                if (response.isSuccessful()) {
                                                                    result = response.body();
                                                                    if (result.startsWith("same"))
                                                                        Toast.makeText(mActivity, "Same bid amount not acceptable..!", Toast.LENGTH_LONG).show();
                                                                    else if (result.startsWith("0")) {
                                                                        Toast.makeText(mActivity, "Thanks for your bid", Toast.LENGTH_LONG).show();
                                                                        callToLiveAuctionBidding("0");
                                                                    } else if (result.startsWith("1")) {
                                                                        Toast.makeText(mActivity, "Thanks for your bid", Toast.LENGTH_LONG).show();
                                                                        callToLiveAuctionBidding("1");
                                                                    } else if (result.startsWith("2")) {
                                                                        Toast.makeText(mActivity, "Thanks for your bid", Toast.LENGTH_LONG).show();
                                                                        callToLiveAuctionBidding("2");
                                                                    } else {
                                                                        Toast.makeText(mActivity, "Bid should be greater than your previous bid!", Toast.LENGTH_LONG).show();
                                                                    }
                                                                } else {
                                                                    Log.e("No", "Response");
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<String> call, Throwable t) {
                                                                t.printStackTrace();
                                                            }
                                                        });
                                                    } else
                                                        CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                Toast.makeText(mActivity, "Bid should not be reverse", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    alertDialog.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener()

                            {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();
                }
            }
        });
    }

    /*
    Call To Live Auction Biding...
     */

    private void callToLiveAuctionBidding(String tabNo) {
        LiveAuctionEventBiding strContext = (LiveAuctionEventBiding) mActivity;
        Bundle b1 = new Bundle();
        b1.putInt("auction_id", auctionId);
        b1.putString("auctioneer", strContext.auctioneername);
        b1.putString("action_title", strContext.action_title);
        b1.putString("auction_startdate", strContext.auction_startdate);
        b1.putString("auction_starttime", strContext.auction_starttime);
        b1.putString("auction_enddate", strContext.auction_enddate);
        b1.putString("auction_endtime", strContext.auction_endtime);
        b1.putString("no_of_vehicles", strContext.no_of_vehicles);
        b1.putString("auction_type", strContext.auctiontype);
        b1.putString("auctioncontact", strContext.auctioncontact);
        b1.putString("specialcluases", strContext.specialcluases);
        b1.putString("endDateTime", strContext.endDateTime);
        b1.putString("openClose", strContext.openClose);
        b1.putString("showPrice", strContext.showPrice);
        b1.putString("tabNo", tabNo);
        b1.putBoolean("isPayEMD", true);
        Log.i("tabNo", "->" + tabNo);

        mActivity.finish();
        Intent intent = new Intent(mActivity, LiveAuctionEventBiding.class);
        intent.putExtras(b1);
        mActivity.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    /***
     * Retrofit Logs
     ***/
    private OkHttpClient.Builder initLog() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging).readTimeout(90, TimeUnit.SECONDS);
        return httpClient;
    }
}
