package autokatta.com.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.other.FullImageActivity;
import autokatta.com.response.BrowseStoreResponse;
import autokatta.com.view.ShareWithinAppActivity;
import autokatta.com.view.StoreViewActivity;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 5/4/17.
 */

public class BrowseStoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RequestNotifier {

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_store_adapter, parent, false);
        return new StoreHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {
        final StoreHolder holder = (StoreHolder) holder1;
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
        holder.productCount.setText("Products(" + success.getProductcount() + ")");
        holder.serviceCount.setText("Services(" + success.getServicecount() + ")");
        holder.storeOwner.setText(success.getOwnerName());

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
        holder.storeOwner.setTypeface(tf);

        if (success.getLikestatus().equalsIgnoreCase("yes")) {
            holder.linearlike.setVisibility(View.GONE);
            holder.linearunlike.setVisibility(View.VISIBLE);
        } else if (success.getLikestatus().equalsIgnoreCase("no")) {
            holder.linearlike.setVisibility(View.VISIBLE);
            holder.linearunlike.setVisibility(View.GONE);
        }

        if (success.getFollowstatus().equalsIgnoreCase("yes")) {
            holder.linearfollow.setVisibility(View.GONE);
            holder.linearunfollow.setVisibility(View.VISIBLE);
        } else if (success.getFollowstatus().equalsIgnoreCase("no")) {
            holder.linearfollow.setVisibility(View.VISIBLE);
            holder.linearunfollow.setVisibility(View.GONE);
        }

        if (success.getRating() == null) {

        } else if (!success.getRating().equals("0")) {
            holder.storerating.setRating(Float.parseFloat(String.valueOf(success.getRating())));
        }

        LayerDrawable stars = (LayerDrawable) holder.storerating.getProgressDrawable();
        stars.getDrawable(1)
                .setColorFilter(activity.getResources().getColor(R.color.colorAccent),
                        PorterDuff.Mode.DST); // for filled stars

        image = activity.getString(R.string.base_image_url) + success.getStoreImage();

        if (success.getStoreImage() == null || success.getStoreImage().isEmpty() || success.getStoreImage().equals("null")) {
            holder.store_image.setBackgroundResource(R.drawable.logo48x48);
        } else {
            /****************
             Glide code for image uploading

             *****************/
            Glide.with(activity)
                    .load(image)
                    .bitmapTransform(new CropSquareTransformation(activity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .placeholder(R.drawable.logo48x48) //To show image before loading an original image.
                    //.error(R.drawable.blocked) //To show error image if problem in loading.
                    .into(holder.store_image);
        }

        holder.store_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String image;
                if (success.getStoreImage().equals(""))
                    image = activity.getString(R.string.base_image_url) + "logo48x48.png";
                else
                    image = activity.getString(R.string.base_image_url) + success.getStoreImage();
                Intent intent = new Intent(activity, FullImageActivity.class);
                Bundle b = new Bundle();
                b.putString("image", image);
                intent.putExtras(b);
                activity.startActivity(intent);
            }
        });

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
                    likecountint = success.getLikecount();

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
                    likecountint = success.getLikecount();

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
                    followcountint = success.getFollowcount();

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

        holder.linearunfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StoreContact = success.getContactNo();
                if (!StoreContact.equals(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""))) {
                    followcountint = success.getFollowcount();

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

        holder.mShare.setOnClickListener(new View.OnClickListener() {
            String imageFilePath = "", imagename;
            Intent intent = new Intent(Intent.ACTION_SEND);

            @Override
            public void onClick(View v) {
                //shareProfileData();
                PopupMenu mPopupMenu = new PopupMenu(activity, holder.mShare);
                mPopupMenu.getMenuInflater().inflate(R.menu.more_menu, mPopupMenu.getMenu());
                mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.autokatta:
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
                                break;

                            case R.id.other:
                                if (success.getStoreImage().equalsIgnoreCase("") || success.getStoreImage().equalsIgnoreCase(null) ||
                                        success.getStoreImage().equalsIgnoreCase("null")) {
                                    imagename = activity.getString(R.string.base_image_url) + "logo48x48.png";
                                } else {
                                    imagename = activity.getString(R.string.base_image_url) + success.getStoreImage();
                                }
                                Log.e("TAG", "img : " + imagename);

                                DownloadManager.Request request = new DownloadManager.Request(
                                        Uri.parse(imagename));
                                request.allowScanningByMediaScanner();
                                String filename = URLUtil.guessFileName(imagename, null, MimeTypeMap.getFileExtensionFromUrl(imagename));
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                                Log.e("ShareImagePath :", filename);
                                Log.e("TAG", "img : " + imagename);

                                DownloadManager manager = (DownloadManager) activity.getApplication()
                                        .getSystemService(Context.DOWNLOAD_SERVICE);

                                Log.e("TAG", "img URL: " + imagename);

                                assert manager != null;
                                manager.enqueue(request);

                                imageFilePath = "/storage/emulated/0/Download/" + filename;
                                System.out.println("ImageFilePath:" + imageFilePath);

                                String allStoreDetails = "Store name : " + holder.storename.getText().toString() + "\n" +
                                        "Store type : " + holder.storetype.getText().toString() + "\n" +
                                        "Ratings : " + holder.storerating.getRating() + "\n" +
                                        "Likes : " + success.getLikecount() + "\n" +
                                        "Website : " + holder.storewebsite.getText().toString() + "\n" +
                                        "Timing : " + holder.storetiming.getText().toString() + "\n" +
                                        "Working Days : " + holder.storeworkingdays.getText().toString() + "\n" +
                                        "Location : " + holder.storelocation.getText().toString();


                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my store on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/store/main/" + success.getStoreId()
                                        + "/" + success.getContactNo()
                                        + "\n" + "\n" + allStoreDetails);
                                intent.setType("image/jpeg");
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                activity.startActivity(Intent.createChooser(intent, "Autokatta"));
                                break;
                        }
                        return false;
                    }
                });
                mPopupMenu.show(); //showing popup menu
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
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(activity, activity.getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            // CustomToast.customToast(activity, getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            //CustomToast.customToast(activity, getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "BrowseStoreAdapter");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            switch (str) {
                case "success_follow":
                    CustomToast.customToast(activity, "Following");
                    break;
                case "success_unfollow":
                    CustomToast.customToast(activity, "UnFollowing");
                    break;
                case "success_like":
                    CustomToast.customToast(activity, "Liked");
                    break;
                case "success_unlike":
                    CustomToast.customToast(activity, "Unliked");
                    break;
            }
        }
    }

    private class StoreHolder extends RecyclerView.ViewHolder {
        TextView storename, storelocation, storewebsite, storetiming, storeworkingdays, storetype, storeservices, storeOwner;
        ImageView store_image, call_image;
        TextView btnlike, btnfollow;
        RatingBar storerating;
        Button linearlike, linearunlike, linearfollow, linearunfollow;
        TextView linearshare, linearshare1, mRatingCount, mStoreTime, productCount, serviceCount;
        Button mShare;
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
            mShare = (Button) itemView.findViewById(R.id.linear_share);

            store_image = (ImageView) itemView.findViewById(R.id.storeprofileimage);
            call_image = (ImageView) itemView.findViewById(R.id.call_image);

            btnlike = (TextView) itemView.findViewById(R.id.like);
            btnfollow = (TextView) itemView.findViewById(R.id.follow);
            linearshare = (TextView) itemView.findViewById(R.id.share_clk);
            linearshare1 = (TextView) itemView.findViewById(R.id.autokatta_share_clk);
            linearlike = (Button) itemView.findViewById(R.id.rellike);
            linearunlike = (Button) itemView.findViewById(R.id.relunlike);
            linearfollow = (Button) itemView.findViewById(R.id.linear_follow);
            linearunfollow = (Button) itemView.findViewById(R.id.linear_unfollow);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            productCount = (TextView) itemView.findViewById(R.id.productCount);
            serviceCount = (TextView) itemView.findViewById(R.id.serviceCount);
            storeOwner = (TextView) itemView.findViewById(R.id.storeOwner);

            //btndetail = (Button) itemView.findViewById(R.id.details);
            storerating = (RatingBar) itemView.findViewById(R.id.storerating);


        }
    }

    //Calling Functionality
    private void call(String StoreContact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + StoreContact));
        try {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            activity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
