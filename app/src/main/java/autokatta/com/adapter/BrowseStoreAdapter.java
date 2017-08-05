package autokatta.com.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BrowseStoreResponse;
import autokatta.com.view.ShareWithinAppActivity;
import autokatta.com.view.StoreViewActivity;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 5/4/17.
 */

public class BrowseStoreAdapter extends RecyclerView.Adapter<BrowseStoreAdapter.StoreHolder> implements RequestNotifier {

    List<BrowseStoreResponse.Success> mMainlist;
    Activity activity;
    String image = "";
    private String StoreContact, allDetails;
    private int StoreId;
    private int likecountint, followcountint;
    ApiCall mApiCall;

    public BrowseStoreAdapter(Activity activity, List<BrowseStoreResponse.Success> successList) {
        this.activity = activity;
        this.mMainlist = successList;
    }

    @Override
    public BrowseStoreAdapter.StoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_store_adapter, parent, false);
        StoreHolder holder = new StoreHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final BrowseStoreAdapter.StoreHolder holder, final int position) {
        final BrowseStoreResponse.Success success = mMainlist.get(holder.getAdapterPosition());
        mApiCall = new ApiCall(activity, this);
        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "font/Roboto-Light.ttf");

        holder.storename.setText(success.getStoreName());
        holder.storelocation.setText(success.getLocation());
        holder.storetype.setText(success.getStoreType());
        holder.storeservices.setText(success.getCategory());
        holder.storeworkingdays.setText(success.getWorkingDays());
        holder.mRatingCount.setText(String.valueOf(success.getRating()));
        holder.storetiming.setText(success.getStoreOpenTime() + " " + activity.getString(R.string.to) + " " + success.getStoreCloseTime());
        holder.btnlike.setText("Likes(" + success.getLikecount() + ")");
        holder.btnfollow.setText("Follow(" + success.getFollowcount() + ")");
        holder.storerating.setEnabled(false);
        holder.productCount.setText("Products(" + String.valueOf(success.getProductcount()) + ")");
        holder.serviceCount.setText("Services(" + String.valueOf(success.getServicecount()) + ")");

        if (success.getWebsite() == null || success.getWebsite().isEmpty() || success.getWebsite().equals("null")) {
            holder.storewebsite.setText("No website found");
        } else {
            holder.storewebsite.setText(success.getWebsite());
        }

        //   holder.storename.setTypeface(tf);
        holder.storelocation.setTypeface(tf);
        holder.storewebsite.setTypeface(tf);
        holder.storetype.setTypeface(tf);
        holder.storeservices.setTypeface(tf);
        holder.storeworkingdays.setTypeface(tf);
        holder.storetiming.setTypeface(tf);
        holder.btnlike.setTypeface(tf);
        holder.btnfollow.setTypeface(tf);
        holder.serviceCount.setTypeface(tf);
        holder.productCount.setTypeface(tf);

        if (success.getLikestatus().equalsIgnoreCase("yes")) {
            holder.linearlike.setVisibility(View.GONE);
            holder.linearunlike.setVisibility(View.VISIBLE);
        }

        if (success.getLikestatus().equalsIgnoreCase("no")) {
            holder.linearlike.setVisibility(View.VISIBLE);
            holder.linearunlike.setVisibility(View.GONE);
        }

        if (success.getFollowstatus().equalsIgnoreCase("yes")) {
            holder.linearfollow.setVisibility(View.GONE);
            holder.linearunfollow.setVisibility(View.VISIBLE);
        }
        if (success.getFollowstatus().equalsIgnoreCase("no")) {
            holder.linearfollow.setVisibility(View.VISIBLE);
            holder.linearunfollow.setVisibility(View.GONE);
        }

        if (success.getRating() == null) {

        } else if (!success.getRating().equals("0")) {
            holder.storerating.setRating(Float.parseFloat(String.valueOf(success.getRating())));
        }

        image = activity.getString(R.string.base_image_url) + success.getStoreImage();

        if (success.getStoreImage() == null || success.getStoreImage().isEmpty() || success.getStoreImage().equals("null")) {
            holder.store_image.setBackgroundResource(R.mipmap.ic_launcher);
        } else {
            /****************
             Glide code for image uploading

             *****************/
            Glide.with(activity)
                    .load(image)
                    .bitmapTransform(new CropSquareTransformation(activity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .placeholder(R.drawable.logo) //To show image before loading an original image.
                    //.error(R.drawable.blocked) //To show error image if problem in loading.
                    .into(holder.store_image);
        }

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("StoreContact", success.getContactNo());
                b.putInt("store_id", success.getStoreId());
                Intent intent = new Intent(activity, StoreViewActivity.class);
                intent.putExtras(b);
                activity.startActivity(intent);
            }
        });


        holder.linearlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreContact = success.getContactNo();
                if (!StoreContact.equals(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""))) {
                    int likecountstr = success.getLikecount();
                    likecountint = likecountstr;

                    StoreId = success.getStoreId();
                    mApiCall.Like(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                            .getString("loginContact", ""), StoreContact, "2", StoreId, 0, 0, 0, 0, 0, 0);
                    //sendLike(StoreId, StoreContact);

                    holder.linearunlike.setVisibility(View.VISIBLE);
                    holder.linearlike.setVisibility(View.GONE);

                    likecountint++;
                    success.setLikecount(likecountint);
                    holder.btnlike.setText("Likes(" + success.getLikecount() + ")");

                    success.setLikestatus("yes");
                } else {
                    CustomToast.customToast(activity, "Sorry!! you can not like your own store");
                }

            }
        });

        holder.linearunlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreContact = success.getContactNo();
                if (!StoreContact.equals(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""))) {
                    Integer likecountstr = success.getLikecount();
                    likecountint = likecountstr;

                    StoreId = success.getStoreId();
                    mApiCall.UnLike(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                            .getString("loginContact", ""), StoreContact, "2", StoreId, 0, 0, 0, 0, 0, 0);
                    //    sendUnLike(StoreId, StoreContact);

                    holder.linearunlike.setVisibility(View.GONE);
                    holder.linearlike.setVisibility(View.VISIBLE);
                    likecountint--;
                    success.setLikecount(likecountint);
                    holder.btnlike.setText("Likes(" + success.getLikecount() + ")");

                    success.setLikestatus("no");
                } else {
                    CustomToast.customToast(activity, "Sorry!! you can not unlike your own store");
                }

            }
        });


        holder.linearfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StoreContact = success.getContactNo();
                if (!StoreContact.equals(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""))) {
                    int followcountstr = success.getFollowcount();
                    followcountint = followcountstr;

                    StoreId = success.getStoreId();
                    mApiCall.Follow(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                            .getString("loginContact", ""), StoreContact, "2", StoreId, 0, 0, 0);
                    //sendFollower(StoreId, StoreContact);
                    holder.linearfollow.setVisibility(View.GONE);
                    holder.linearunfollow.setVisibility(View.VISIBLE);

                    followcountint++;
                    success.setFollowcount(followcountint);
                    holder.btnfollow.setText("Follow(" + success.getFollowcount() + ")");

                    success.setFollowstatus("yes");
                } else {
                    CustomToast.customToast(activity, "Sorry!! you can not follow your own store");
                }

            }
        });
//
        holder.linearunfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StoreContact = success.getContactNo();
                if (!StoreContact.equals(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""))) {
                    int followcountstr = success.getFollowcount();
                    followcountint = followcountstr;

                    StoreId = success.getStoreId();
                    mApiCall.UnFollow(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                            .getString("loginContact", ""), StoreContact, "2", StoreId, 0, 0, 0);
                    //sendFollower(StoreId, StoreContact);
                    holder.linearfollow.setVisibility(View.VISIBLE);
                    holder.linearunfollow.setVisibility(View.GONE);

                    followcountint--;
                    success.setFollowcount(followcountint);
                    holder.btnfollow.setText("Follow(" + success.getFollowcount() + ")");

                    success.setFollowstatus("no");
                } else {
                    CustomToast.customToast(activity, "Sorry!! you can not follow your own store");
                }

            }
        });


        holder.call_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreContact = success.getContactNo();
                if (!StoreContact.equals(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""))) {
                    call(StoreContact);
                } else {
                    CustomToast.customToast(activity, "Sorry!! you can not call yourself ");
                }
            }
        });

        holder.linearshare1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String timing = success.getStoreOpenTime() + "" + success.getStoreCloseTime();
                allDetails = success.getStoreName() + "=" +
                        success.getWebsite() + "="
                        + timing + "=" +
                        success.getWorkingDays() + "=" +
                        "" + "=" +
                        success.getLocation() + "=" +
                        success.getStoreImage() + "=" +
                        success.getRating() + "=" +
                        success.getLikecount() + "=" +
                        success.getFollowcount();


                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", allDetails).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putInt("Share_store_id", success.getStoreId()).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "store").apply();

                Intent i = new Intent(activity, ShareWithinAppActivity.class);
                activity.startActivity(i);
                //activity.finish();

            }
        });
//
//
        holder.linearshare.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String imageFilePath;

            @Override
            public void onClick(View v) {
                if (success.getStoreImage().equalsIgnoreCase("") || success.getStoreImage().equalsIgnoreCase(null) ||
                        success.getStoreImage().equalsIgnoreCase("null")) {
                    image = activity.getString(R.string.base_image_url) + "logo48x48.png";
                } else {
                    image = activity.getString(R.string.base_image_url) + success.getStoreImage();
                }
                Log.e("TAG", "img : " + image);

                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(image));
                request.allowScanningByMediaScanner();
                String filename = URLUtil.guessFileName(String.valueOf(image), null, MimeTypeMap.getFileExtensionFromUrl(image));
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                Log.e("ShareImagePath :", filename);
                Log.e("TAG", "img : " + image);

                DownloadManager manager = (DownloadManager) activity.getApplication()
                        .getSystemService(Context.DOWNLOAD_SERVICE);

                Log.e("TAG", "img URL: " + image);

                manager.enqueue(request);

                imageFilePath = "/storage/emulated/0/Download/" + filename;
                System.out.println("ImageFilePath:" + imageFilePath);

                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my store on Autokatta. Stay connected for Product and Service updates and enquiries"
                        + "\n" + "http://autokatta.com/store/" + success.getStoreImage());
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                activity.startActivity(Intent.createChooser(intent, "Autokatta"));
            }
        });


    }

    @Override
    public int getItemCount() {
        return mMainlist.size();
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }

    static class StoreHolder extends RecyclerView.ViewHolder {
        TextView storename, storelocation, storewebsite, storetiming, storeworkingdays, storetype, storeservices;
        ImageView store_image, call_image;
        TextView btnlike, btnfollow;
        RatingBar storerating;
        LinearLayout linearlike, linearunlike, linearfollow, linearunfollow;
        TextView linearshare, linearshare1, mRatingCount, mStoreTime, productCount, serviceCount;
        Button btndetail;
        CardView mCardView;

        public StoreHolder(View itemView) {
            super(itemView);

            storename = (TextView) itemView.findViewById(R.id.storename);
            storelocation = (TextView) itemView.findViewById(R.id.storelocation);
            storewebsite = (TextView) itemView.findViewById(R.id.storewebsite);
            storetiming = (TextView) itemView.findViewById(R.id.storetiming);
            storetype = (TextView) itemView.findViewById(R.id.storetype);
            storeservices = (TextView) itemView.findViewById(R.id.storeservices);
            storeworkingdays = (TextView) itemView.findViewById(R.id.storeworkingdays);
            mRatingCount = (TextView) itemView.findViewById(R.id.rating_count);

            store_image = (ImageView) itemView.findViewById(R.id.storeprofileimage);
            call_image = (ImageView) itemView.findViewById(R.id.call_image);

            btnlike = (TextView) itemView.findViewById(R.id.like);
            btnfollow = (TextView) itemView.findViewById(R.id.follow);
            linearshare = (TextView) itemView.findViewById(R.id.share_clk);
            linearshare1 = (TextView) itemView.findViewById(R.id.autokatta_share_clk);
            linearlike = (LinearLayout) itemView.findViewById(R.id.rellike);
            linearunlike = (LinearLayout) itemView.findViewById(R.id.relunlike);
            linearfollow = (LinearLayout) itemView.findViewById(R.id.linear_follow);
            linearunfollow = (LinearLayout) itemView.findViewById(R.id.linear_unfollow);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            productCount = (TextView) itemView.findViewById(R.id.productCount);
            serviceCount = (TextView) itemView.findViewById(R.id.serviceCount);

            //btndetail = (Button) itemView.findViewById(R.id.details);
            storerating = (RatingBar) itemView.findViewById(R.id.storerating);


        }
    }

    //Calling Functionality
    private void call(String StoreContact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + StoreContact));
        try {
            activity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Group contact adapter \n");
        }
    }
}
