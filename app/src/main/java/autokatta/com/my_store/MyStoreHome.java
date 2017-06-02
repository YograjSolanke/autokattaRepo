package autokatta.com.my_store;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.StoreResponse;
import autokatta.com.view.StoreViewActivity;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 1/6/17.
 */

public class MyStoreHome extends Fragment implements View.OnClickListener, RequestNotifier {
    View mMyStoreHome;
    ImageView mBannerImage, mStoreImage;
    TextView mStoreName, mWebSite, mLocation;
    Button mCall, mEnquiry;
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
    String myContact;
    String mOtherContact, mLoginContact, store_id, storeOtherContact, mFolllowstr, mLikestr, storeRating;
    String storeName = "", storeImage = "", storeCoverImage = "", storeType = "", storeWebsite = "", storeTiming = "", storeLocation = "", storeWorkingDays = "",
            storeLikeCount, storeFollowCount, strDetailsShare = "";
    Double storelattitude;
    Double storelongitude;
    ApiCall mApiCall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyStoreHome = inflater.inflate(R.layout.fragment_my_store_home, container, false);
        return mMyStoreHome;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
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
                mLike = (ImageView) mMyStoreHome.findViewById(R.id.like);
                mUnlike = (ImageView) mMyStoreHome.findViewById(R.id.unlike);
                mRating = (ImageView) mMyStoreHome.findViewById(R.id.rating);
                mFollow = (ImageView) mMyStoreHome.findViewById(R.id.follow);
                mUnFollow = (ImageView) mMyStoreHome.findViewById(R.id.unfollow);
                mMap = (ImageView) mMyStoreHome.findViewById(R.id.map);
                mAddReview = (ImageView) mMyStoreHome.findViewById(R.id.add_review);

                mBannerImage = (ImageView) mMyStoreHome.findViewById(R.id.other_store_image);
                mStoreImage = (ImageView) mMyStoreHome.findViewById(R.id.other_store_images);

                mStoreName = (TextView) mMyStoreHome.findViewById(R.id.store_name);
                mWebSite = (TextView) mMyStoreHome.findViewById(R.id.web);
                mLocation = (TextView) mMyStoreHome.findViewById(R.id.location);

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
                        mApiCall.otherStoreLike(myContact, mOtherContact, "2", store_id);
                    }
                });

                mUnlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mApiCall.otherStoreUnlike(myContact, mOtherContact, "2", store_id);
                    }
                });

                mFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mApiCall.otherStoreFollow(myContact, mOtherContact, "2", store_id);
                    }
                });

                mUnFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mApiCall.otherStoreUnFollow(myContact, mOtherContact, "2", store_id);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call:
                call();
                break;
        }
    }

    /*
    Call Intent...
     */
    private void call() {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "7841023392"));
        System.out.println("calling started");
        try {
            startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Other Profile\n");
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                StoreResponse storeResponse = (StoreResponse) response.body();
                for (StoreResponse.Success success : storeResponse.getSuccess()) {
                    storeName = success.getName();
                    storeImage = success.getStoreImage();
                    storeCoverImage = success.getCoverImage();
                    storeWebsite = success.getWebsite();
                    storeTiming = success.getStoreOpenTime() + " " + success.getStoreCloseTime();
                    storeLocation = success.getLocation();
                    storeWorkingDays = success.getWorkingDays();
                    storeType = success.getStoreType();
                    storeLikeCount = success.getLikecount();
                    storeFollowCount = success.getFollowcount();

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
                }

                if (mOtherContact.equals(myContact)) {
                    mCall.setVisibility(View.GONE);
                    mLike.setVisibility(View.GONE);
                    mUnlike.setVisibility(View.GONE);
                    mFollow.setVisibility(View.GONE);
                    mUnFollow.setVisibility(View.GONE);
                    /*mRate.setVisibility(View.GONE);
                    mAdd.setVisibility(View.VISIBLE);*/
                }

                mRatingBar.setRating(Float.parseFloat(storeRating));
                //  mBundle.putString("StoreContact", mOtherContact);

                String dp_path = "http://autokatta.com/mobile/store_profiles/" + storeImage;
                Glide.with(this)
                        .load(dp_path)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mStoreImage);

                String dp_paths = "http://autokatta.com/mobile/store_profiles/" + storeCoverImage;
                Glide.with(this)
                        .load(dp_paths)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mBannerImage);

                mStoreName.setText(storeName);
                mLocation.setText(storeLocation);
                mWebSite.setText(storeWebsite);

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
            } else {
                Snackbar.make(getView(), getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Toast.makeText(getActivity(), getString(R.string._404), Toast.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else if (error instanceof UnknownHostException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
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
                Snackbar.make(getView(), "Following", Snackbar.LENGTH_SHORT).show();
                mFollow.setVisibility(View.GONE);
                mUnFollow.setVisibility(View.VISIBLE);
                mFolllowstr = "yes";
            } else if (str.equals("success_unfollow")) {
                mFollow.setVisibility(View.VISIBLE);
                mUnFollow.setVisibility(View.GONE);
                mFolllowstr = "no";
            } else if (str.equals("success_like")) {
                Snackbar.make(getView(), "Liked", Snackbar.LENGTH_SHORT).show();
                mLike.setVisibility(View.GONE);
                mUnlike.setVisibility(View.VISIBLE);
                mLikestr = "yes";
            } else if (str.equals("success_unlike")) {
                mLike.setVisibility(View.VISIBLE);
                mUnlike.setVisibility(View.GONE);
                mLikestr = "no";
            } else if (str.equals("success_rating_submitted")) {
                Snackbar.make(getView(), "Rating Submitted", Snackbar.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("store_id", store_id);
                bundle.putString("StoreContact", storeOtherContact);
                getActivity().finish();
                Intent intent = new Intent(getActivity(), StoreViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            } else if (str.equals("success_rating_updated")) {
                Snackbar.make(getView(), "SRating updated", Snackbar.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("store_id", store_id);
                bundle.putString("StoreContact", storeOtherContact);
                getActivity().finish();
                Intent intent = new Intent(getActivity(), StoreViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            } else if (str.equals("success_recommended")) {
                Snackbar.make(getView(), "Store Recommended", Snackbar.LENGTH_SHORT).show();
            }
        }
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

        if (!precsrate.equals("0")) {
            csbar.setRating(Float.parseFloat(precsrate));
        }
        if (!preqwrate.equals("0")) {
            qwbar.setRating(Float.parseFloat(preqwrate));
        }
        if (!prefrrate.equals("0")) {
            frbar.setRating(Float.parseFloat(prefrrate));
        }
        if (!preprrate.equals("0")) {
            prbar.setRating(Float.parseFloat(preprrate));
        }
        if (!pretmrate.equals("0")) {
            tmbar.setRating(Float.parseFloat(pretmrate));
        }
        if (!preoverall.equals("0")) {
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
        mApiCall.getStoreData(contact, store_id);
    }

    private void drawMap(Double storelattitude, Double storelongitude) {
        double destinationLatitude = storelattitude;
        double destinationLongitude = storelongitude;

        String url = "http://maps.google.com/maps?f=d&daddr=" + destinationLatitude + "," + destinationLongitude + "&dirflg=d&layer=t";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }
}
