package autokatta.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import autokatta.com.R;
import autokatta.com.adapter.BussinessMsgSendersAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BroadcastReceivedResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 10/4/17.
 */

public class BussinessMsgSenders extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    String mContact;
    int product_id = 0, service_id = 0, vehicle_id = 0;
    View root;
    BussinessMsgSendersAdapter mMsgReplyAdapter;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView Title, Category, Brand, Model, Keyword, price;
    RelativeLayout relCategory, relBrand, relModel, relPrice, MainRel;
    /* String vehi_img_url;// = getActivity().getString(R.string.base_image_url);
     String prduct_img_url = getActivity().getString(R.string.base_image_url);
     String service_img_url = getActivity().getString(R.string.base_image_url);*/
    String fullpath = "";
    String bundle_keyword,bundle_title,bundle_price,bundle_category,bundle_brand,bundle_model,image;
    ImageView Image;
    ApiCall mApiCall;
    List<BroadcastReceivedResponse.Success> mSuccesses = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        root = inflater.inflate(R.layout.mybroadcastmsglist, null);
        mApiCall = new ApiCall(getActivity(), this);
        mContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_viewmsglist);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeRefreshLayoutBussinessChatmsglist);


        Keyword = (TextView) root.findViewById(R.id.keyword);
        Title = (TextView) root.findViewById(R.id.settitle);
        Category = (TextView) root.findViewById(R.id.setcategory);
        Brand = (TextView) root.findViewById(R.id.setbrand);
        Model = (TextView) root.findViewById(R.id.setmodel);
        Image = (ImageView) root.findViewById(R.id.image);
        price = (TextView) root.findViewById(R.id.setprice);
        relCategory = (RelativeLayout) root.findViewById(R.id.relative2);
        relBrand = (RelativeLayout) root.findViewById(R.id.relative3);
        relModel = (RelativeLayout) root.findViewById(R.id.relative4);
        relPrice = (RelativeLayout) root.findViewById(R.id.relative5);
        MainRel = (RelativeLayout) root.findViewById(R.id.MainRel);


        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);


        //Set animation attribute to each item
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        //getData();//Get Api...
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);

                try {
                    Bundle b = getArguments();
                    product_id = b.getInt("product_id");
                    service_id = b.getInt("service_id");
                    vehicle_id = b.getInt("vehicle_id");
                    bundle_keyword = b.getString("keyword");
                    bundle_title = b.getString("title");
                    bundle_price = b.getString("price");
                    bundle_category = b.getString("category");
                    bundle_brand = b.getString("brand");
                    bundle_model = b.getString("model");
                    Keyword.setText(bundle_keyword);
                    Title.setText(bundle_title);
                    price.setText(bundle_price);
                    Category.setText(bundle_category);
                    Brand.setText(bundle_brand);
                    Model.setText(bundle_model);
                    image = b.getString("image", "");

                    if (b.getString("keyword", "").equalsIgnoreCase("Product")) {
                        relCategory.setVisibility(View.GONE);
                        relBrand.setVisibility(View.GONE);
                        relModel.setVisibility(View.GONE);
                        if (!image.equals("") && !image.equals("null")) {
                            fullpath = getString(R.string.base_image_url) + image;
                            fullpath = fullpath.replaceAll(" ", "%20");
                            Glide.with(getActivity())
                                    .load(fullpath)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .bitmapTransform(new CropCircleTransformation(getActivity()))
                                    .placeholder(R.drawable.logo)
                                    .into(Image);
                        } else {
                            Image.setImageResource(R.drawable.logo);
                        }
                    } else if (b.getString("keyword", "").equalsIgnoreCase("Service")) {
                        relCategory.setVisibility(View.GONE);
                        relBrand.setVisibility(View.GONE);
                        relModel.setVisibility(View.GONE);
                        if (!image.equals("") && !image.equals("null")) {
                            fullpath = getString(R.string.base_image_url) + image;
                            fullpath = fullpath.replaceAll(" ", "%20");
                            Glide.with(getActivity())
                                    .load(fullpath)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .bitmapTransform(new CropCircleTransformation(getActivity()))
                                    .placeholder(R.drawable.logo)
                                    .into(Image);
                        } else {
                            Image.setImageResource(R.drawable.logo);
                        }
                    } else if (b.getString("keyword", "").equalsIgnoreCase("Vehicle")) {
                        if (!image.equals("") && !image.equals("null")) {
                            fullpath = getString(R.string.base_image_url) + image;
                            fullpath = fullpath.replaceAll(" ", "%20");
                            Glide.with(getActivity())
                                    .load(fullpath)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .bitmapTransform(new CropCircleTransformation(getActivity()))
                                    .placeholder(R.drawable.logo)
                                    .into(Image);
                        } else {
                            Image.setImageResource(R.drawable.logo);
                        }
                    }

                    mApiCall.getBroadcastReceivers(mContact, product_id, service_id, vehicle_id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return root;
    }

    @Override
    public void onRefresh() {
        try {
            Bundle b = getArguments();
            product_id = b.getInt("product_id");
            service_id = b.getInt("service_id");
            vehicle_id = b.getInt("vehicle_id");

            mApiCall.getBroadcastReceivers(mContact, product_id, service_id, vehicle_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSuccesses.clear();
                mSwipeRefreshLayout.setRefreshing(false);
                BroadcastReceivedResponse mGetBroadcastReceiver = (BroadcastReceivedResponse) response.body();
                for (BroadcastReceivedResponse.Success msenders : mGetBroadcastReceiver.getSuccess()) {
                    msenders.setSender(msenders.getSender());
                    msenders.setSendername(msenders.getSendername());
                    msenders.setMessage(msenders.getMessage());
                    msenders.setDate(msenders.getDate());
                    msenders.setProfileImage(msenders.getProfileImage());
                    msenders.setLocation(msenders.getLocation());


                    try {
                        TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                        //format of date coming from services
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                        /*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault());*/
                        inputFormat.setTimeZone(utc);

                        //format of date which we want to show
                        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
                        /*DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                                Locale.getDefault());*/
                        outputFormat.setTimeZone(utc);

                        Date date = inputFormat.parse(msenders.getDate());
                        //System.out.println("jjj"+date);
                        String output = outputFormat.format(date);
                        //System.out.println(mainList.get(i).getDate()+" jjj " + output);
                        msenders.setDate(output);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mSuccesses.add(msenders);
                }
                mMsgReplyAdapter = new BussinessMsgSendersAdapter(getActivity(), mSuccesses, product_id, service_id, vehicle_id,bundle_keyword,bundle_title,bundle_price,bundle_category,bundle_brand,bundle_model,image);
                mRecyclerView.setAdapter(mMsgReplyAdapter);
                mMsgReplyAdapter.notifyDataSetChanged();
            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
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
            Log.i("Check Class-", "Bussiness msg sender");
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
