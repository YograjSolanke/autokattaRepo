package autokatta.com.my_store;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.adapter.AdminCallContactAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.model.LikeUnlike;
import autokatta.com.other.CustomToast;
import autokatta.com.other.FullImageActivity;
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
    int precsrate = 0;
    int preqwrate = 0;
    int prefrrate = 0;
    int preprrate = 0;
    int pretmrate = 0;
    int preoverall = 0;
    String isDealing = "";
    AdminCallContactAdapter adapter;
    String myContact;
    ArrayList<String> storeAdmins = new ArrayList<>();
    Activity mActivity;
    String mOtherContact;
    String storeOtherContact;
    String mFolllowstr;
    String mLikestr;
    int storeRating;
    String storeName = "";
    String storeImage = "";
    String storeCoverImage = "";
    String storeType = "";
    String storeWebsite = "";
    String storeTiming = "";
    String storeLocation = "";
    String storeWorkingDays = "";
    int storeLikeCount;
    int storeFollowCount;
    String storelattitude;
    String storelongitude;
    int store_id;
    ApiCall mApiCall;
    private int likecountint, followcountint;
    private ProgressDialog dialog;
    LikeUnlike likeUnlike = new LikeUnlike();
    String[] items = new String[0];
    ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyStoreHome = inflater.inflate(R.layout.fragment_my_store_home, container, false);
        return mMyStoreHome;
    }


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
                store_id = b.getInt("store_id");

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
                        mApiCall.UnLike(myContact, mOtherContact, "2", store_id, 0, 0, 0, 0, 0, 0);
                        likecountint = likeUnlike.getCount();
                        likecountint = likecountint - 1;
                        mLikeCount.setText(String.valueOf("Likes(" + String.valueOf(likecountint) + ")"));
                        storeLikeCount = likecountint;
                        likeUnlike.setCount(likecountint);
                    }
                });

                mUnlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLike.setVisibility(View.VISIBLE);
                        mUnlike.setVisibility(View.GONE);
                        mApiCall.Like(myContact, mOtherContact, "2", store_id, 0, 0, 0, 0, 0, 0);
                        likecountint = likeUnlike.getCount();
                        likecountint = likecountint + 1;
                        mLikeCount.setText(String.valueOf("Likes(" + String.valueOf(likecountint) + ")"));
                        storeLikeCount = likecountint;
                        likeUnlike.setCount(likecountint);
                    }
                });

                mFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFollow.setVisibility(View.GONE);
                        mUnFollow.setVisibility(View.VISIBLE);
                        mApiCall.Follow(myContact, mOtherContact, "2", store_id, 0, 0, 0);
                        followcountint = likeUnlike.getFollowCount();
                        followcountint = followcountint + 1;
                        mFollowCount.setText(String.valueOf("Followers(" + String.valueOf(followcountint) + ")"));
                        storeFollowCount = followcountint;
                        likeUnlike.setFollowCount(followcountint);
                    }
                });

                mUnFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFollow.setVisibility(View.VISIBLE);
                        mUnFollow.setVisibility(View.GONE);
                        mApiCall.UnFollow(myContact, mOtherContact, "2", store_id, 0, 0, 0);
                        followcountint = likeUnlike.getFollowCount();
                        followcountint--;
                        mFollowCount.setText(String.valueOf("Followers(" + String.valueOf(followcountint) + ")"));
                        likeUnlike.setFollowCount(followcountint);
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
        mEnquiry.setOnClickListener(this);
        mBannerImage.setOnClickListener(this);
        mStoreImage.setOnClickListener(this);
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

        Intent intent = new Intent(getActivity(), FullImageActivity.class);
        Bundle b = new Bundle();
        switch (v.getId()) {
            case R.id.call:
                if (storeAdmins.size() == 0)
                    call(mOtherContact);
                else
                getCallContactList();

                break;

            case R.id.web:
                String website = mWebSite.getText().toString().trim();
                goToUrl(website);
                break;

            case R.id.enquiry:

                break;
            case R.id.other_store_image:
                String image;
                if (storeCoverImage.equals(""))
                    image = getString(R.string.base_image_url) + "logo48x48.png";
                else
                    image = getString(R.string.base_image_url) + storeCoverImage;

                b.putString("image", image);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case R.id.other_store_images:


                String imageStore;
                if (storeImage.equals(""))
                    imageStore = getString(R.string.base_image_url) + "logo48x48.png";
                else
                    imageStore = getString(R.string.base_image_url) + storeImage;

                b.putString("image", imageStore);
                intent.putExtras(b);
                startActivity(intent);
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
                        isDealing = success.getDealingWith();

                        getActivity().setTitle(storeName + " Store");
                    }

                    if (mOtherContact.contains(myContact)) {
                        otherViewLayout.setVisibility(View.GONE);
                    }

                    mRatingBar.setRating(storeRating);
                    //  mBundle.putString("StoreContact", mOtherContact);

                    if (!storeImage.equals("")) {
                        String dp_path = getString(R.string.base_image_url) + storeImage;
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
                        String dp_paths = getString(R.string.base_image_url) + storeCoverImage;
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
                    mFollowCount.setText("Followers(" + String.valueOf(storeFollowCount) + ")");
                    mLikeCount.setText("Likes(" + String.valueOf(storeLikeCount) + ")");


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
                        //8007855589-dealer-RUTU
                        storeAdmins.add(myContact + "-" + "Owner" + "-" + "Owner");
                        for (StoreOldAdminResponse.Success success : adminResponse.getSuccess()) {

                            storeAdmins.add(success.getAdmin());

                        }

                        System.out.println("alreadyadmin=" + storeAdmins.size());

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
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            // CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            //CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
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
            switch (str) {
                case "success_follow":
                    CustomToast.customToast(getActivity(), "Following");
                    mFolllowstr = "yes";
                    break;
                case "success_unfollow":
                    //if (mActivity != null)
                    CustomToast.customToast(getActivity(), "UnFollowing");
                /*mFollow.setVisibility(View.VISIBLE);
                mUnFollow.setVisibility(View.GONE);*/
                    mFolllowstr = "no";
                    break;
                case "success_like":
                    //if (mActivity != null)
                    CustomToast.customToast(getActivity(), "Liked");
                /*mLike.setVisibility(View.VISIBLE);
                mUnlike.setVisibility(View.GONE);*/
                    mLikestr = "yes";
                    break;
                case "success_unlike":
                    //if (mActivity != null)
                    CustomToast.customToast(getActivity(), "Unliked");
                /*mLike.setVisibility(View.GONE);
                mUnlike.setVisibility(View.VISIBLE);*/
                    mLikestr = "no";
                    break;
                case "success_rating_submitted": {
                    if (mActivity != null)
                        CustomToast.customToast(getActivity(), "Rating Submitted");
                    Bundle bundle = new Bundle();
                    bundle.putInt("store_id", store_id);
                    bundle.putString("StoreContact", storeOtherContact);
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), StoreViewActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                    break;
                }
                case "success_rating_updated": {
                    //if (mActivity != null)
                    CustomToast.customToast(getActivity(), "Rating Updated");
                    Bundle bundle = new Bundle();
                    bundle.putInt("store_id", store_id);
                    bundle.putString("StoreContact", storeOtherContact);
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), StoreViewActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                }
                case "success_recommended":
                    //if (mActivity != null)
                    CustomToast.customToast(getActivity(), "Store recommended");
                    break;
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


        if ((precsrate != 0)) {
            csbar.setRating(precsrate);
            csrate = Float.parseFloat(String.valueOf(precsrate));
        }
        if (preqwrate != 0) {
            qwbar.setRating(preqwrate);
            qwrate = Float.parseFloat(String.valueOf(preqwrate));
        }
        if (prefrrate != 0) {
            frbar.setRating(prefrrate);
            frrate = Float.parseFloat(String.valueOf(prefrrate));
        }
        if (preprrate != 0) {
            prbar.setRating(preprrate);
            prrate = Float.parseFloat(String.valueOf(preprrate));
        }
        if (pretmrate != 0) {
            tmbar.setRating(pretmrate);
            tmrate = Float.parseFloat(String.valueOf(pretmrate));
        }
        if (preoverall != 0) {
            overallbar.setRating(preoverall);
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
                if (preoverall == 0) {

                    if (count == 0.0f) {
                        mApiCall.sendNewrating(myContact, store_id, 0, 0, String.valueOf(preoverall),
                                String.valueOf(precsrate),
                                String.valueOf(preqwrate),
                                String.valueOf(prefrrate),
                                String.valueOf(preprrate),
                                String.valueOf(pretmrate),
                                "store");

                    } else {
                        mApiCall.sendNewrating(myContact, store_id, 0, 0, String.valueOf(count),
                                String.valueOf(csrate),
                                String.valueOf(qwrate),
                                String.valueOf(frrate),
                                String.valueOf(prrate),
                                String.valueOf(tmrate),
                                "store");
                    }
                } else {
                    if (count == 0.0f) {
                        mApiCall.sendUpdatedrating(myContact, store_id, 0, 0, String.valueOf(preoverall),
                                String.valueOf(precsrate),
                                String.valueOf(preqwrate),
                                String.valueOf(prefrrate),
                                String.valueOf(preprrate),
                                String.valueOf(pretmrate),
                                "store");

                    } else {
                        mApiCall.sendUpdatedrating(myContact, store_id, 0, 0, String.valueOf(count),
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
                if (preoverall == 0) {

                    if (count == 0.0f) {
                        mApiCall.sendNewrating(myContact, store_id, 0, 0, String.valueOf(preoverall),
                                String.valueOf(precsrate),
                                String.valueOf(preqwrate),
                                String.valueOf(prefrrate),
                                String.valueOf(preprrate),
                                String.valueOf(pretmrate),
                                "store");

                    } else {
                        mApiCall.sendNewrating(myContact, store_id, 0, 0, String.valueOf(count),
                                String.valueOf(csrate),
                                String.valueOf(qwrate),
                                String.valueOf(frrate),
                                String.valueOf(prrate),
                                String.valueOf(tmrate),
                                "store");
                    }
                } else {
                    if (count == 0.0f) {
                        mApiCall.sendUpdatedrating(myContact, store_id, 0, 0, String.valueOf(preoverall),
                                String.valueOf(precsrate),
                                String.valueOf(preqwrate),
                                String.valueOf(prefrrate),
                                String.valueOf(preprrate),
                                String.valueOf(pretmrate),
                                "store");

                    } else {
                        mApiCall.sendUpdatedrating(myContact, store_id, 0, 0, String.valueOf(count),
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

    private void getOtherStore(String contact, int store_id) {
        dialog.show();
        mApiCall.getStoreData(contact, store_id);
        mApiCall.StoreAdmin(store_id);
    }

    private void drawMap(String storelattitude, String storelongitude) {
        double destinationLatitude = Double.parseDouble(storelattitude);
        double destinationLongitude = Double.parseDouble(storelongitude);

        String url = "http://maps.google.com/maps?f=d&daddr=" + destinationLatitude + "," + destinationLongitude + "&dirflg=d&layer=t";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }

    private void getCallContactList() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.admin_contact_call_layout, null);
        dialogBuilder.setView(dialogView);

        RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        AlertDialog alertDialog = dialogBuilder.create();

        adapter = new AdminCallContactAdapter(getActivity(), storeAdmins);
        recyclerView.setAdapter(adapter);
        alertDialog.show();

    }


}
