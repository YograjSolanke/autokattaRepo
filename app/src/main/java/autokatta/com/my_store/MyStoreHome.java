package autokatta.com.my_store;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.model.LikeUnlike;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreOldAdminResponse;
import autokatta.com.response.StoreResponse;
import autokatta.com.view.StoreViewActivity;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 1/6/17.
 */

public class MyStoreHome extends Fragment implements View.OnClickListener, RequestNotifier {
    View mMyStoreHome;
    LinearLayout mLinear;
    ImageView mBannerImage, mStoreImage;
    TextView mStoreName, mWebSite, mLocation, mLikeCount, mFollowCount, mCategory;
    Button mCall, mEnquiry;
    RelativeLayout otherViewLayout;
    RatingBar mRatingBar;
    ImageView mLike, mUnlike, mRating, mFollow, mUnFollow, mMap, mAddReview;
    boolean hasLoadedOnce = false;
    Float csrate = 0.0f, qwrate = 0.0f, frrate = 0.0f, prrate = 0.0f, tmrate = 0.0f, total = 0.0f, count = 0.0f;
    RatingBar csbar, qwbar, frbar, prbar, tmbar, overallbar;
    String precsrate = "";
    String preqwrate = "";
    String prefrrate = "";
    String preprrate = "";
    String pretmrate = "";
    String preoverall = "";
    String isDealing = "";
    String myContact, storeAdmins = "";
    Activity mActivity;
    String mOtherContact, mLoginContact, store_id, storeOtherContact, mFolllowstr, mLikestr, storeRating;
    String storeName = "", storeImage = "", storeCoverImage = "", storeType = "", storeWebsite = "", storeTiming = "", storeLocation = "", storeWorkingDays = "",
            storeLikeCount, storeFollowCount, strDetailsShare = "", productCount, serviceCount, vehicleCount;
    Double storelattitude;
    Double storelongitude;
    ApiCall mApiCall;
    private int likecountint, followcountint;
    private ProgressDialog dialog;
    LikeUnlike likeUnlike = new LikeUnlike();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyStoreHome = inflater.inflate(R.layout.fragment_my_store_home, container, false);
        return mMyStoreHome;
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (this.isVisible()) {
//            if (isVisibleToUser && !hasLoadedOnce) {
//                getOtherStore(myContact, store_id);
//                hasLoadedOnce = false;
//            }
//        }
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mApiCall = new ApiCall(getActivity(), this);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", "");
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Loading...");

                mLinear = (LinearLayout) mMyStoreHome.findViewById(R.id.my_home);
                mLike = (ImageView) mMyStoreHome.findViewById(R.id.like);
                mUnlike = (ImageView) mMyStoreHome.findViewById(R.id.unlike);
                mRating = (ImageView) mMyStoreHome.findViewById(R.id.rating);
                mFollow = (ImageView) mMyStoreHome.findViewById(R.id.unfollow);
                mUnFollow = (ImageView) mMyStoreHome.findViewById(R.id.follow);
                mMap = (ImageView) mMyStoreHome.findViewById(R.id.map);
                mAddReview = (ImageView) mMyStoreHome.findViewById(R.id.add_review);
                otherViewLayout = (RelativeLayout) mMyStoreHome.findViewById(R.id.otherViewRelative);

                mBannerImage = (ImageView) mMyStoreHome.findViewById(R.id.other_store_image);
                mStoreImage = (ImageView) mMyStoreHome.findViewById(R.id.other_store_images);

                mStoreName = (TextView) mMyStoreHome.findViewById(R.id.store_name);
                mWebSite = (TextView) mMyStoreHome.findViewById(R.id.web);
                mLocation = (TextView) mMyStoreHome.findViewById(R.id.location);
                mLikeCount = (TextView) mMyStoreHome.findViewById(R.id.likeCount);
                mFollowCount = (TextView) mMyStoreHome.findViewById(R.id.followCount);
                mCategory = (TextView) mMyStoreHome.findViewById(R.id.category);

                mCall = (Button) mMyStoreHome.findViewById(R.id.call);
                mEnquiry = (Button) mMyStoreHome.findViewById(R.id.enquiry);

                mRatingBar = (RatingBar) mMyStoreHome.findViewById(R.id.store_rating);

                Bundle b = getArguments();
                store_id = b.getString("store_id");

                getOtherStore(myContact, store_id);
                mRating.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        filterResult();
                    }
                });

                mLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLike.setVisibility(View.GONE);
                        mUnlike.setVisibility(View.VISIBLE);
                        mApiCall.otherStoreUnlike(myContact, mOtherContact, "2", store_id);
                        likecountint = Integer.parseInt(likeUnlike.getCount());
                        likecountint = likecountint - 1;
                        mLikeCount.setText(String.valueOf("Likes(" + likecountint + ")"));
                        storeLikeCount = String.valueOf(likecountint);
                        likeUnlike.setCount(String.valueOf(likecountint));
                    }
                });

                mUnlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLike.setVisibility(View.VISIBLE);
                        mUnlike.setVisibility(View.GONE);
                        mApiCall.otherStoreLike(myContact, mOtherContact, "2", store_id);
                        likecountint = Integer.parseInt(likeUnlike.getCount());
                        likecountint = likecountint + 1;
                        mLikeCount.setText(String.valueOf("Likes(" + likecountint + ")"));
                        storeLikeCount = String.valueOf(likecountint);
                        likeUnlike.setCount(String.valueOf(likecountint));
                    }
                });

                mFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFollow.setVisibility(View.GONE);
                        mUnFollow.setVisibility(View.VISIBLE);
                        mApiCall.otherStoreFollow(myContact, mOtherContact, "2", store_id);
                        followcountint = Integer.parseInt(likeUnlike.getFollowCount());
                        followcountint = followcountint + 1;
                        mFollowCount.setText(String.valueOf("Followers(" + followcountint + ")"));
                        storeFollowCount = String.valueOf(followcountint);
                        likeUnlike.setFollowCount(String.valueOf(followcountint));
                    }
                });

                mUnFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFollow.setVisibility(View.VISIBLE);
                        mUnFollow.setVisibility(View.GONE);
                        mApiCall.otherStoreUnFollow(myContact, mOtherContact, "2", store_id);
                        followcountint = Integer.parseInt(likeUnlike.getFollowCount());
                        followcountint--;
                        mFollowCount.setText(String.valueOf("Followers(" + followcountint + ")"));
                        likeUnlike.setFollowCount(String.valueOf(followcountint));
                    }
                });

                mMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drawMap(storelattitude, storelongitude);
                    }
                });
            }
        });
        mCall.setOnClickListener(this);
        mWebSite.setOnClickListener(this);
    }

    private void goToUrl(String url) {
        String fullUrl = "http://" + url;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(fullUrl));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call:
                if (!storeAdmins.equals("")) {
                    // createCotactsList();
                    final String[] items;
                    // @Here are the list of items to be shown in the list
                    if (storeAdmins.contains(",")) {
                        storeAdmins = storeAdmins + "," + mOtherContact + "-" + "owner";
                        items = storeAdmins.split(",");

                    } else {
                        items = new String[]{storeAdmins, mOtherContact + "-" + "Owner"};

                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Make your selection");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {

                            // will toast your selection
                            //   showToast("Name: " + items[item]);
                            String[] arr = items[item].split("-");
                            call(arr[0]);


                            dialog.dismiss();

                        }
                    }).show();

                } else
                    call(mOtherContact);
                break;
            case R.id.web:
                String website = mWebSite.getText().toString().trim();
                goToUrl(website);
                break;
        }
    }

    /*
    Call Intent...
     */
    private void call(String contact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact));
        System.out.println("calling started");
        try {
            startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Other Profile\n");
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (response != null) {
            if (response.isSuccessful()) {

                if (response.body() instanceof StoreResponse) {
                    StoreResponse storeResponse = (StoreResponse) response.body();
                    mLinear.setVisibility(View.VISIBLE);
                    for (StoreResponse.Success success : storeResponse.getSuccess()) {
                    storeName = success.getName();
                    storeImage = success.getStoreImage();
                    storeOtherContact = success.getContact();
                    storeCoverImage = success.getCoverImage();
                    storeWebsite = success.getWebsite();
                    storeTiming = success.getStoreOpenTime() + " " + success.getStoreCloseTime();
                    storeLocation = success.getLocation();
                    storeWorkingDays = success.getWorkingDays();
                    storeType = success.getStoreType();
                    storeLikeCount = success.getLikecount();
                    likeUnlike.setCount(success.getLikecount());
                    storeFollowCount = success.getFollowcount();
                    likeUnlike.setFollowCount(success.getFollowcount());
                    mCategory.setText(success.getCategory());

                    mOtherContact = success.getContact();
                    Log.i("dsafdsafdas", "->" + mOtherContact);
                    storeRating = success.getRating();

                    mLikestr = success.getLikestatus();
                    mFolllowstr = success.getFollowstatus();
                    preoverall = success.getRate();
                    precsrate = success.getRate1();
                    preqwrate = success.getRate2();
                    prefrrate = success.getRate3();
                    preprrate = success.getRate4();
                    pretmrate = success.getRate5();
                    storelattitude = success.getLatitude();
                    storelongitude = success.getLongitude();
                    isDealing = success.getIsDealing();

                    getActivity().setTitle(storeName + " Store");
                }

                if (mOtherContact.contains(myContact)) {
                    otherViewLayout.setVisibility(View.GONE);
                }

                mRatingBar.setRating(Float.parseFloat(storeRating));
                //  mBundle.putString("StoreContact", mOtherContact);

                if (!storeImage.equals("")) {
                    String dp_path = "http://autokatta.com/mobile/store_profiles/" + storeImage;
                    Glide.with(this)
                            .load(dp_path)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.logo)
                            .into(mStoreImage);
                } else {
                    mStoreImage.setImageResource(R.drawable.logo);
                }
                if (!storeCoverImage.equals("")) {
                    String dp_paths = "http://autokatta.com/mobile/store_profiles/" + storeCoverImage;
                    Glide.with(this)
                            .load(dp_paths)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.logo)
                            .into(mBannerImage);
                } else {
                    mBannerImage.setImageResource(R.drawable.logo);
                }

                mStoreName.setText(storeName);
                mLocation.setText(storeLocation);
                mWebSite.setText(storeWebsite);
                mFollowCount.setText("Followers(" + storeFollowCount + ")");
                mLikeCount.setText("Likes(" + storeLikeCount + ")");


                if (mLikestr.equalsIgnoreCase("no")) {
                    mLike.setVisibility(View.GONE);
                    mUnlike.setVisibility(View.VISIBLE);
                } else {
                    mLike.setVisibility(View.VISIBLE);
                    mUnlike.setVisibility(View.GONE);
                }
                if (mFolllowstr.equalsIgnoreCase("no")) {
                    mFollow.setVisibility(View.VISIBLE);
                    mUnFollow.setVisibility(View.GONE);
                } else {
                    mFollow.setVisibility(View.GONE);
                    mUnFollow.setVisibility(View.VISIBLE);
                }
                } else if (response.body() instanceof StoreOldAdminResponse) {
                    StoreOldAdminResponse adminResponse = (StoreOldAdminResponse) response.body();
                    if (!adminResponse.getSuccess().isEmpty()) {
                        for (StoreOldAdminResponse.Success success : adminResponse.getSuccess()) {
                            if (storeAdmins.equals(""))
                                storeAdmins = success.getAdmin();
                            else
                                storeAdmins = storeAdmins + "," + success.getAdmin();
                        }

                        System.out.println("alreadyadmin=" + storeAdmins);

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            if (mActivity != null)
                mActivity = getActivity();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "StoreViewActivity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("success_follow")) {
                //if (mActivity != null)
                CustomToast.customToast(getActivity(), "Following");
                /*mFollow.setVisibility(View.GONE);
                mUnFollow.setVisibility(View.VISIBLE);*/
                mFolllowstr = "yes";
            } else if (str.equals("success_unfollow")) {
                //if (mActivity != null)
                CustomToast.customToast(getActivity(), "UnFollowing");
                /*mFollow.setVisibility(View.VISIBLE);
                mUnFollow.setVisibility(View.GONE);*/
                mFolllowstr = "no";
            } else if (str.equals("success_like")) {
                //if (mActivity != null)
                CustomToast.customToast(getActivity(), "Liked");
                /*mLike.setVisibility(View.VISIBLE);
                mUnlike.setVisibility(View.GONE);*/
                mLikestr = "yes";
            } else if (str.equals("success_unlike")) {
                //if (mActivity != null)
                CustomToast.customToast(getActivity(), "Unliked");
                /*mLike.setVisibility(View.GONE);
                mUnlike.setVisibility(View.VISIBLE);*/
                mLikestr = "no";
            } else if (str.equals("success_rating_submitted")) {
                if (mActivity != null)
                    CustomToast.customToast(getActivity(), "Rating Submitted");
                Bundle bundle = new Bundle();
                bundle.putString("store_id", store_id);
                bundle.putString("StoreContact", storeOtherContact);
                getActivity().finish();
                Intent intent = new Intent(getActivity(), StoreViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            } else if (str.equals("success_rating_updated")) {
                //if (mActivity != null)
                CustomToast.customToast(getActivity(), "Rating Updated");
                Bundle bundle = new Bundle();
                bundle.putString("store_id", store_id);
                bundle.putString("StoreContact", storeOtherContact);
                getActivity().finish();
                Intent intent = new Intent(getActivity(), StoreViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            } else if (str.equals("success_recommended")) {
                //if (mActivity != null)
                CustomToast.customToast(getActivity(), "Store recommended");
            }
        }
    }

    //call list of admin contacts
    public void createCotactsList() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView = inflater.inflate(R.layout.custom_store_rate_layout, null);
        alertDialog.setView(convertView);
        final AlertDialog alert = alertDialog.show();
        alert.setTitle("Rate This Store");

        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    /*
    Rate Store...
     */
    public void filterResult() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView = inflater.inflate(R.layout.custom_store_rate_layout, null);
        alertDialog.setView(convertView);
        final AlertDialog alert = alertDialog.show();
        alert.setTitle("Rate This Store");
        Button yes = (Button) convertView.findViewById(R.id.btnyes);
        Button no = (Button) convertView.findViewById(R.id.btnno);
        csbar = (RatingBar) convertView.findViewById(R.id.cs_rating);
        qwbar = (RatingBar) convertView.findViewById(R.id.qw_rating);
        frbar = (RatingBar) convertView.findViewById(R.id.fr_rating);
        prbar = (RatingBar) convertView.findViewById(R.id.pr_rating);
        tmbar = (RatingBar) convertView.findViewById(R.id.tm_rating);
        overallbar = (RatingBar) convertView.findViewById(R.id.overall_rating);

        if (!precsrate.equals("")) {
            csbar.setRating(Float.parseFloat(precsrate));
            csrate = Float.parseFloat(precsrate);
        }
        if (!preqwrate.equals("")) {
            qwbar.setRating(Float.parseFloat(preqwrate));
            qwrate = Float.parseFloat(preqwrate);
        }
        if (!prefrrate.equals("")) {
            frbar.setRating(Float.parseFloat(prefrrate));
            frrate = Float.parseFloat(prefrrate);
        }
        if (!preprrate.equals("")) {
            prbar.setRating(Float.parseFloat(preprrate));
            prrate = Float.parseFloat(preprrate);
        }
        if (!pretmrate.equals("")) {
            tmbar.setRating(Float.parseFloat(pretmrate));
            tmrate = Float.parseFloat(pretmrate);
        }
        if (!preoverall.equals("")) {
            overallbar.setRating(Float.parseFloat(preoverall));
        }

        csbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                csrate = v;
                calculate(csrate, qwrate, frrate, prrate, tmrate);
            }
        });

        qwbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                qwrate = v;
                calculate(csrate, qwrate, frrate, prrate, tmrate);
            }
        });

        frbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                frrate = v;
                calculate(csrate, qwrate, frrate, prrate, tmrate);
            }
        });

        prbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                prrate = v;
                calculate(csrate, qwrate, frrate, prrate, tmrate);
            }
        });

        tmbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                tmrate = v;
                calculate(csrate, qwrate, frrate, prrate, tmrate);
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preoverall.equals("0")) {

                    if (count == 0.0f) {
                        mApiCall.sendNewrating(myContact, store_id, "", "", String.valueOf(preoverall),
                                String.valueOf(precsrate),
                                String.valueOf(preqwrate),
                                String.valueOf(prefrrate),
                                String.valueOf(preprrate),
                                String.valueOf(pretmrate),
                                "store");

                    } else {
                        mApiCall.sendNewrating(myContact, store_id, "", "", String.valueOf(count),
                                String.valueOf(csrate),
                                String.valueOf(qwrate),
                                String.valueOf(frrate),
                                String.valueOf(prrate),
                                String.valueOf(tmrate),
                                "store");
                    }
                } else {
                    if (count == 0.0f) {
                        mApiCall.sendUpdatedrating(myContact, store_id, "", "", String.valueOf(preoverall),
                                String.valueOf(precsrate),
                                String.valueOf(preqwrate),
                                String.valueOf(prefrrate),
                                String.valueOf(preprrate),
                                String.valueOf(pretmrate),
                                "store");

                    } else {
                        mApiCall.sendUpdatedrating(myContact, store_id, "", "", String.valueOf(count),
                                String.valueOf(csrate),
                                String.valueOf(qwrate),
                                String.valueOf(frrate),
                                String.valueOf(prrate),
                                String.valueOf(tmrate),
                                "store");
                    }
                }
                mApiCall.recommendStore(myContact, store_id);
                alert.dismiss();
            }
        });


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preoverall.equals("0")) {

                    if (count == 0.0f) {
                        mApiCall.sendNewrating(myContact, store_id, "", "", String.valueOf(preoverall),
                                String.valueOf(precsrate),
                                String.valueOf(preqwrate),
                                String.valueOf(prefrrate),
                                String.valueOf(preprrate),
                                String.valueOf(pretmrate),
                                "store");

                    } else {
                        mApiCall.sendNewrating(myContact, store_id, "", "", String.valueOf(count),
                                String.valueOf(csrate),
                                String.valueOf(qwrate),
                                String.valueOf(frrate),
                                String.valueOf(prrate),
                                String.valueOf(tmrate),
                                "store");
                    }
                } else {
                    if (count == 0.0f) {
                        mApiCall.sendUpdatedrating(myContact, store_id, "", "", String.valueOf(preoverall),
                                String.valueOf(precsrate),
                                String.valueOf(preqwrate),
                                String.valueOf(prefrrate),
                                String.valueOf(preprrate),
                                String.valueOf(pretmrate),
                                "store");

                    } else {
                        mApiCall.sendUpdatedrating(myContact, store_id, "", "", String.valueOf(count),
                                String.valueOf(csrate),
                                String.valueOf(qwrate),
                                String.valueOf(frrate),
                                String.valueOf(prrate),
                                String.valueOf(tmrate),
                                "store");
                    }
                }
                alert.dismiss();

            }
        });

        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }

    //calculate rating fuction
    public void calculate(Float r1, Float r2, Float r3, Float r4, Float r5) {
        total = r1 + r2 + r3 + r4 + r5;
        count = total / 5;
        overallbar.setRating(count);
    }

    private void getOtherStore(String contact, String store_id) {
        dialog.show();
        mApiCall.getStoreData(contact, store_id);
        mApiCall.StoreAdmin(store_id);
    }

    private void drawMap(Double storelattitude, Double storelongitude) {
        double destinationLatitude = storelattitude;
        double destinationLongitude = storelongitude;

        String url = "http://maps.google.com/maps?f=d&daddr=" + destinationLatitude + "," + destinationLongitude + "&dirflg=d&layer=t";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }


    /*public void showMessage(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG);
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    public void errorMessage(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getOtherStore(myContact, store_id);
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.BLUE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }*/
}
