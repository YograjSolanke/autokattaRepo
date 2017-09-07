package autokatta.com.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.WallResponse;
import autokatta.com.view.OtherProfile;
import autokatta.com.view.ShareWithinAppActivity;
import autokatta.com.view.StoreViewActivity;
import autokatta.com.view.UserProfile;
import retrofit2.Response;

/**
 * Created by ak-004 on 7/7/17.
 */

public class StoreNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RequestNotifier {

    private Activity mActivity;
    private List<WallResponse.Success.WallNotification> notificationList = new ArrayList<>();
    private String mLoginContact;
    private ApiCall mApiCall;
    private int store_likecountint, store_followcountint;

    /* constructor */
    public StoreNotificationAdapter(Activity activity, List<WallResponse.Success.WallNotification> notificationList1,
                                    String myContact) {
        mActivity = activity;
        mActivity = activity;
        notificationList = notificationList1;
        mLoginContact = myContact;
        this.mApiCall = new ApiCall(mActivity, this);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(notificationList.get(position).getLayout());
    }

    /* view class Group*/
    private static class StoreNotifications extends RecyclerView.ViewHolder {
        CardView mStoreCardView;
        ImageView mProfilePic, mStoreImage;
        ImageButton mStoreAutokattaShare, mCall, mStoreFav, mStoreUnfav;
        Button mLike, mUnlike, mFollow, mUnfollow;
        RatingBar mStoreRating;
        TextView mStoreActionName, mActionTime, mStoreName, mStoreCategory, mStoreType, mStoreWebSite, mStoreTiming,
                mStoreWorkingDay, mStoreLocation, mFollowCount, mLikes, mShares;
        RelativeLayout mRelativeLike;

        private StoreNotifications(View storeView) {
            super(storeView);
            mStoreCardView = (CardView) storeView.findViewById(R.id.store_card_view);
            mProfilePic = (ImageView) storeView.findViewById(R.id.store_pic);
            mStoreImage = (ImageView) storeView.findViewById(R.id.store_image);

            mStoreAutokattaShare = (ImageButton) storeView.findViewById(R.id.share_autokatta);
            //mCall = (ImageButton) storeView.findViewById(R.id.call);
            mLike = (Button) storeView.findViewById(R.id.like);
            mUnlike = (Button) storeView.findViewById(R.id.unlike);
            mFollow = (Button) storeView.findViewById(R.id.follow);
            mUnfollow = (Button) storeView.findViewById(R.id.unfollow);
            mStoreRating = (RatingBar) storeView.findViewById(R.id.store_rating);
            mStoreFav = (ImageButton) storeView.findViewById(R.id.store_favourite);
            mStoreUnfav = (ImageButton) storeView.findViewById(R.id.store_unfavourite);

            mStoreActionName = (TextView) storeView.findViewById(R.id.store_action_names);
            mActionTime = (TextView) storeView.findViewById(R.id.store_action_time);
            mStoreName = (TextView) storeView.findViewById(R.id.store_name);
            mStoreCategory = (TextView) storeView.findViewById(R.id.store_category);
            mStoreType = (TextView) storeView.findViewById(R.id.store_type);
            mStoreWebSite = (TextView) storeView.findViewById(R.id.store_website);
            mStoreTiming = (TextView) storeView.findViewById(R.id.store_time);
            mStoreWorkingDay = (TextView) storeView.findViewById(R.id.store_working_day);
            mStoreLocation = (TextView) storeView.findViewById(R.id.store_location);
            mFollowCount = (TextView) storeView.findViewById(R.id.followcnt);
            mLikes = (TextView) storeView.findViewById(R.id.likes);
            mShares = (TextView) storeView.findViewById(R.id.share);
            //mRelativeLike = (RelativeLayout) storeView.findViewById(R.id.rlLike);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_store_notifications, parent, false);
        return new StoreNotifications(mView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final StoreNotifications mStoreHolder = (StoreNotifications) holder;
        Log.i("Wall", "Store-LayType ->" + notificationList.get(position).getLayoutType());
        SpannableStringBuilder sb2 = new SpannableStringBuilder();

        if (notificationList.get(position).getLayoutType().equalsIgnoreCase("MyAction")) {
            //mStoreHolder.mCall.setVisibility(View.GONE);
            mStoreHolder.mRelativeLike.setVisibility(View.GONE);

        } else {
            //mStoreHolder.mCall.setVisibility(View.VISIBLE);
            mStoreHolder.mRelativeLike.setVisibility(View.VISIBLE);
        }

        mStoreHolder.mStoreActionName.setText(notificationList.get(position).getSenderName() + " "
                + notificationList.get(position).getAction() + " " + notificationList.get(position).getReceiverName() + " "
                + notificationList.get(position).getStoreName() + " " + "Store");


        //Spannable code here
        sb2.append(notificationList.get(position).getSenderName());
        sb2.append(" ");
        sb2.append(notificationList.get(position).getAction());
        sb2.append("\n");
        sb2.append(notificationList.get(position).getReceiverName());
        sb2.append(" ");
        sb2.append(notificationList.get(position).getStoreName());
        sb2.append(" Store");

        sb2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

                if (notificationList.get(mStoreHolder.getAdapterPosition()).getLayoutType().equalsIgnoreCase("MyAction")) {
                    mActivity.startActivity(new Intent(mActivity, UserProfile.class));
                } else {
                    Intent intent = new Intent(mActivity, OtherProfile.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("contactOtherProfile", notificationList.get(mStoreHolder.getAdapterPosition()).getSender());
                    intent.putExtras(bundle);
                    mActivity.startActivity(intent);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
                ds.setColor(ContextCompat.getColor(mActivity, R.color.colorPrimaryDark));
                ds.setFakeBoldText(true);
                ds.setTextSize((float) 31.0);
                Log.i("TextSize", "->" + ds.getTextSize());
            }
        }, 0, notificationList.get(position).getSenderName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        sb2.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {

                            if (notificationList.get(mStoreHolder.getAdapterPosition()).getLayoutType().equalsIgnoreCase("MyAction") ||
                                    notificationList.get(mStoreHolder.getAdapterPosition()).getLayoutType().equalsIgnoreCase("MyNotification")) {
                                mActivity.startActivity(new Intent(mActivity, UserProfile.class));
                            } else {
                                Intent intent = new Intent(mActivity, OtherProfile.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("contactOtherProfile", notificationList.get(mStoreHolder.getAdapterPosition()).getReceiver());
                                intent.putExtras(bundle);
                                mActivity.startActivity(intent);
                            }
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            ds.setUnderlineText(false);
                            ds.setColor(ContextCompat.getColor(mActivity, R.color.colorPrimaryDark));
                            ds.setFakeBoldText(true);
                            ds.setTextSize((float) 31.0);
                        }
                    }, notificationList.get(position).getSenderName().length() + notificationList.get(position).getAction().length() + 2,
                notificationList.get(position).getSenderName().length() +
                        notificationList.get(position).getAction().length() + 2 + notificationList.get(position).getReceiverName().length()
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        sb2.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            Bundle b = new Bundle();
                            b.putInt("store_id", notificationList.get(mStoreHolder.getAdapterPosition()).getStoreID());
                            Intent intent = new Intent(mActivity, StoreViewActivity.class);
                            intent.putExtras(b);
                            mActivity.startActivity(intent);
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            ds.setUnderlineText(false);
                            ds.setColor(ContextCompat.getColor(mActivity, R.color.colorPrimaryDark));
                            ds.setFakeBoldText(true);
                            ds.setTextSize((float) 31.0);
                        }
                    }, notificationList.get(position).getSenderName().length() + notificationList.get(position).getAction().length() +
                        notificationList.get(position).getReceiverName().length() + 3,

                notificationList.get(position).getSenderName().length() +
                        notificationList.get(position).getAction().length() +
                        notificationList.get(position).getReceiverName().length() + 3 +
                        notificationList.get(position).getStoreName().length() + 1
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        mStoreHolder.mStoreActionName.setText(sb2);
        mStoreHolder.mStoreActionName.setMovementMethod(LinkMovementMethod.getInstance());
        mStoreHolder.mStoreActionName.setHighlightColor(Color.TRANSPARENT);


        mStoreHolder.mActionTime.setText(notificationList.get(position).getDateTime());
        mStoreHolder.mStoreName.setText(notificationList.get(position).getStoreName());
        mStoreHolder.mStoreCategory.setText(notificationList.get(position).getStoreCategory());
        mStoreHolder.mStoreType.setText(notificationList.get(position).getStoreType());
        mStoreHolder.mStoreWebSite.setText(notificationList.get(position).getStoreWebsite());
        mStoreHolder.mStoreTiming.setText(notificationList.get(position).getStoreTiming());
        mStoreHolder.mStoreWorkingDay.setText(notificationList.get(position).getWorkingDays());
        mStoreHolder.mStoreLocation.setText(notificationList.get(position).getStoreLocation());
        mStoreHolder.mFollowCount.setText("Followers(" + notificationList.get(position).getStoreFollowCount() + ")");
        mStoreHolder.mLikes.setText("Likes(" + notificationList.get(position).getStoreLikeCount() + ")");
        mStoreHolder.mShares.setText("Shares(" + notificationList.get(position).getStoreShareCount() + ")");
        mStoreHolder.mStoreRating.setRating(notificationList.get(position).getStoreRating());

         /* Sender Profile Pic */

        if (notificationList.get(position).getSenderPicture() == null ||
                notificationList.get(position).getSenderPicture().equals("") ||
                notificationList.get(position).getSenderPicture().equals("null")) {
            mStoreHolder.mProfilePic.setBackgroundResource(R.drawable.logo48x48);
        } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getSenderPicture())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mStoreHolder.mProfilePic);
        }

        /* Store pic */

        if (notificationList.get(position).getStoreImage() == null ||
                notificationList.get(position).getStoreImage().equals("") ||
                notificationList.get(position).getStoreImage().equals("null")) {
            mStoreHolder.mStoreImage.setBackgroundResource(R.drawable.store);
        } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getStoreImage())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mStoreHolder.mStoreImage);
        }

        /*mStoreHolder.mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otherContact = notificationList.get(mStoreHolder.getAdapterPosition()).getSender();
                call(otherContact);
            }
        });*/

     /* Like & Unlike Functionality */

        if (notificationList.get(position).getStoreLikeStatus().equalsIgnoreCase("yes")) {
            mStoreHolder.mLike.setVisibility(View.VISIBLE);
            mStoreHolder.mUnlike.setVisibility(View.GONE);
        } else {
            mStoreHolder.mUnlike.setVisibility(View.VISIBLE);
            mStoreHolder.mLike.setVisibility(View.GONE);
        }

        mStoreHolder.mLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Unlike web service
                String otherContact = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreContact();
                int storeId = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreID();
                mStoreHolder.mLike.setVisibility(View.GONE);
                mStoreHolder.mUnlike.setVisibility(View.VISIBLE);
                mApiCall.UnLike(mLoginContact, otherContact, "2", storeId, 0, 0, 0, 0, 0, 0);
                store_likecountint = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreLikeCount();
                store_likecountint = store_likecountint - 1;
                mStoreHolder.mLikes.setText("Likes(" + store_likecountint + ")");
                notificationList.get(mStoreHolder.getAdapterPosition()).setStoreLikeCount(store_likecountint);
                notificationList.get(mStoreHolder.getAdapterPosition()).setStoreLikeStatus("no");
            }
        });

        mStoreHolder.mUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Like web service
                String otherContact = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreContact();
                int storeId = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreID();
                mStoreHolder.mUnlike.setVisibility(View.GONE);
                mStoreHolder.mLike.setVisibility(View.VISIBLE);
                mApiCall.Like(mLoginContact, otherContact, "2", storeId, 0, 0, 0, 0, 0, 0);
                store_likecountint = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreLikeCount();
                store_likecountint = store_likecountint + 1;
                mStoreHolder.mLikes.setText("Likes(" + store_likecountint + ")");
                notificationList.get(mStoreHolder.getAdapterPosition()).setStoreLikeCount(store_likecountint);
                notificationList.get(mStoreHolder.getAdapterPosition()).setStoreLikeStatus("yes");
            }
        });

      /* Fav & Unfav Functionality */
        if (notificationList.get(position).getMyFavStatus().equalsIgnoreCase("yes")) {
            mStoreHolder.mStoreFav.setVisibility(View.VISIBLE);
            mStoreHolder.mStoreUnfav.setVisibility(View.GONE);
        } else {
            mStoreHolder.mStoreUnfav.setVisibility(View.VISIBLE);
            mStoreHolder.mStoreFav.setVisibility(View.GONE);
        }

        mStoreHolder.mStoreFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Unfavorite web service
                int notiId = notificationList.get(mStoreHolder.getAdapterPosition()).getActionID();
                mStoreHolder.mStoreFav.setVisibility(View.GONE);
                mStoreHolder.mStoreUnfav.setVisibility(View.VISIBLE);
                mApiCall.removeFromFavorite(mLoginContact, "", 0, "", notiId);
                notificationList.get(mStoreHolder.getAdapterPosition()).setMyFavStatus("no");
            }
        });

        mStoreHolder.mStoreUnfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Favorite web service
                int notiId = notificationList.get(mStoreHolder.getAdapterPosition()).getActionID();
                mStoreHolder.mStoreUnfav.setVisibility(View.GONE);
                mStoreHolder.mStoreFav.setVisibility(View.VISIBLE);
                mApiCall.addToFavorite(mLoginContact, "", 0, "", notiId);
                notificationList.get(mStoreHolder.getAdapterPosition()).setMyFavStatus("yes");
            }
        });


         /* Follow & UnfollowFunctionality */

        if (notificationList.get(position).getStoreFollowStatus().equalsIgnoreCase("yes")) {
            mStoreHolder.mFollow.setVisibility(View.VISIBLE);
            mStoreHolder.mUnfollow.setVisibility(View.GONE);
        } else {
            mStoreHolder.mUnfollow.setVisibility(View.VISIBLE);
            mStoreHolder.mFollow.setVisibility(View.GONE);
        }

        mStoreHolder.mFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Unfollow web service
                String otherContact = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreContact();
                int storeId = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreID();
                mStoreHolder.mFollow.setVisibility(View.GONE);
                mStoreHolder.mUnfollow.setVisibility(View.VISIBLE);

                mApiCall.UnFollow(mLoginContact, otherContact, "2", storeId, 0, 0, 0);
                store_followcountint = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreFollowCount();
                store_followcountint--;
                mStoreHolder.mFollowCount.setText("Followers(" + store_followcountint + ")");
                notificationList.get(mStoreHolder.getAdapterPosition()).setStoreFollowCount(store_followcountint);
                notificationList.get(mStoreHolder.getAdapterPosition()).setStoreFollowStatus("no");

            }
        });

        mStoreHolder.mUnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Follow web service
                String otherContact = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreContact();
                int storeId = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreID();
                mStoreHolder.mUnfollow.setVisibility(View.GONE);
                mStoreHolder.mFollow.setVisibility(View.VISIBLE);

                mApiCall.Follow(mLoginContact, otherContact, "2", storeId, 0, 0, 0);
                store_followcountint = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreFollowCount();
                store_followcountint = store_followcountint + 1;
                mStoreHolder.mFollowCount.setText("Followers(" + store_followcountint + ")");
                notificationList.get(mStoreHolder.getAdapterPosition()).setStoreFollowCount(store_followcountint);
                notificationList.get(mStoreHolder.getAdapterPosition()).setStoreFollowStatus("yes");

            }
        });

        mStoreHolder.mStoreAutokattaShare.setOnClickListener(new View.OnClickListener() {
            String imageFilePath = "", imagename;
            Intent intent = new Intent(Intent.ACTION_SEND);

            @Override
            public void onClick(View v) {
                //shareProfileData();
                android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(mActivity);
                alert.setTitle("Share");
                alert.setMessage("with Autokatta or to other?");
                alert.setIconAttribute(android.R.attr.alertDialogIcon);

                alert.setPositiveButton("Autokatta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String allStoreDetails = mStoreHolder.mStoreName.getText().toString() + "=" +
                                mStoreHolder.mStoreWebSite.getText().toString() + "=" +
                                mStoreHolder.mStoreTiming.getText().toString() + "=" +
                                mStoreHolder.mStoreWorkingDay.getText().toString() + "=" +
                                mStoreHolder.mStoreType.getText().toString() + "=" +
                                mStoreHolder.mStoreLocation.getText().toString() + "=" +
                                notificationList.get(mStoreHolder.getAdapterPosition()).getStoreImage() + "=" +
                                mStoreHolder.mStoreRating.getRating() + "=" +
                                notificationList.get(mStoreHolder.getAdapterPosition()).getStoreLikeCount() + "=" +
                                notificationList.get(mStoreHolder.getAdapterPosition()).getStoreFollowCount();

                        System.out.println("all store detailssss======Auto " + allStoreDetails);

                        mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                putString("Share_sharedata", allStoreDetails).apply();
                        mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                putInt("Share_store_id", notificationList.get(mStoreHolder.getAdapterPosition()).getStoreID()).apply();
                        mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                putString("Share_keyword", "store").apply();


                        Intent i = new Intent(mActivity, ShareWithinAppActivity.class);
                        mActivity.startActivity(i);
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("Other", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (notificationList.get(mStoreHolder.getAdapterPosition()).getProductImage().equalsIgnoreCase("") ||
                                notificationList.get(mStoreHolder.getAdapterPosition()).getProductImage().equalsIgnoreCase(null) ||
                                notificationList.get(mStoreHolder.getAdapterPosition()).getProductImage().equalsIgnoreCase("null")) {
                            imagename = mActivity.getString(R.string.base_image_url) + "logo48x48.png";
                        } else {
                            imagename = mActivity.getString(R.string.base_image_url) + notificationList.get(mStoreHolder.getAdapterPosition()).getStoreImage();
                        }
                        Log.e("TAG", "img : " + imagename);

                        DownloadManager.Request request = new DownloadManager.Request(
                                Uri.parse(imagename));
                        request.allowScanningByMediaScanner();
                        String filename = URLUtil.guessFileName(imagename, null, MimeTypeMap.getFileExtensionFromUrl(imagename));
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                        Log.e("ShareImagePath :", filename);
                        Log.e("TAG", "img : " + imagename);

                        DownloadManager manager = (DownloadManager) mActivity.getApplication()
                                .getSystemService(Context.DOWNLOAD_SERVICE);

                        Log.e("TAG", "img URL: " + imagename);

                        manager.enqueue(request);

                        imageFilePath = "/storage/emulated/0/Download/" + filename;
                        System.out.println("ImageFilePath:" + imageFilePath);

                        String allStoreDetails = "Store name : " + mStoreHolder.mStoreName.getText().toString() + "\n" +
                                "Store type : " + mStoreHolder.mStoreType.getText().toString() + "\n" +
                                "Ratings : " + mStoreHolder.mStoreRating.getRating() + "\n" +
                                "Likes : " + notificationList.get(mStoreHolder.getAdapterPosition()).getStoreLikeCount() + "\n" +
                                "Website : " + mStoreHolder.mStoreWebSite.getText().toString() + "\n" +
                                "Timing : " + mStoreHolder.mStoreTiming.getText().toString() + "\n" +
                                "Working Days : " + mStoreHolder.mStoreWorkingDay.getText().toString() + "\n" +
                                "Location : " + mStoreHolder.mStoreLocation.getText().toString();

                        System.out.println("all product detailssss======Other " + allStoreDetails);

                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my store on Autokatta. Stay connected for Product and Service updates and enquiries"
                                + "\n" + "http://autokatta.com/store/main/" + notificationList.get(mStoreHolder.getAdapterPosition()).getStoreID()
                                + "/" + notificationList.get(mStoreHolder.getAdapterPosition()).getStoreContact()
                                + "\n" + "\n" + allStoreDetails);
                        intent.setType("image/jpeg");
                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        mActivity.startActivity(Intent.createChooser(intent, "Autokatta"));


                                /*intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.putExtra(Intent.EXTRA_TEXT, allStoreDetails);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                mActivity.startActivity(intent);*/

                        dialog.dismiss();
                    }

                });
                alert.create();
                alert.show();
            }
        });
    }

    private void call(String otherContact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + otherContact));
        try {
            mActivity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Car Details Fragment\n");
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            switch (str) {
                case "success_follow":
                    CustomToast.customToast(mActivity, "Following");
                /*mFollow.setVisibility(View.GONE);
                mUnFollow.setVisibility(View.VISIBLE);
                mFolllowstr = "yes";*/
                    break;
                case "success_unfollow":
                    CustomToast.customToast(mActivity, "UnFollowing");
                /*mFollow.setVisibility(View.VISIBLE);
                mUnFollow.setVisibility(View.GONE);
                mFolllowstr = "no";*/
                    break;
                case "success_like":
                    CustomToast.customToast(mActivity, "Liked");
                /*mLike.setVisibility(View.VISIBLE);
                mUnlike.setVisibility(View.GONE);*/
                    //mLikestr = "yes";
                    break;
                case "success_unlike":
                    CustomToast.customToast(mActivity, "Unliked");
                /*mLike.setVisibility(View.GONE);
                mUnlike.setVisibility(View.VISIBLE);*/
                    //mLikestr = "no";
                    break;
                case "success_favourite":
                    CustomToast.customToast(mActivity, "Favorite");
                /*mLike.setVisibility(View.GONE);
                mUnlike.setVisibility(View.VISIBLE);*/
                    //mLikestr = "no";
                    break;
                case "success_remove":
                    CustomToast.customToast(mActivity, "Unfavorite");
                /*mLike.setVisibility(View.GONE);
                mUnlike.setVisibility(View.VISIBLE);*/
                    //mLikestr = "no";
                    break;
            }
        }

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "Store Notification Adapter");
            error.printStackTrace();
        }
    }

}

