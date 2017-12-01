package autokatta.com.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
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
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.SearchStoreResponse;
import autokatta.com.view.ShareWithinAppActivity;
import autokatta.com.view.StoreViewActivity;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 7/4/17.
 */

public class ViewSearchedStoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RequestNotifier {

    List<SearchStoreResponse.Success> mMainList;
    Activity activity;
    private String StoreContact;
    private int likecountint, followcountint, StoreId;
    private String allDetails;
    ApiCall mApiCall;

    public ViewSearchedStoreAdapter(Activity activity, List<SearchStoreResponse.Success> successList) {
        this.activity = activity;
        this.mMainList = successList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_searched_store_adapter, parent, false);
        return new StoreHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, int position) {

        final StoreHolder holder = (StoreHolder) holder1;

        mApiCall = new ApiCall(activity, this);
        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "font/Roboto-Light.ttf");

        final SearchStoreResponse.Success object = mMainList.get(holder.getAdapterPosition());
        final String image;

        if (object.getLikestatus().equalsIgnoreCase("yes")) {
            holder.linearunlike.setVisibility(View.VISIBLE);
            holder.linearlike.setVisibility(View.GONE);
        } else if (object.getLikestatus().equalsIgnoreCase("no")) {
            holder.linearunlike.setVisibility(View.GONE);
            holder.linearlike.setVisibility(View.VISIBLE);
        }

        if (object.getFollowstatus().equalsIgnoreCase("yes")) {
            holder.linearfollow.setVisibility(View.GONE);
            holder.linearunfollow.setVisibility(View.VISIBLE);
        } else if (object.getFollowstatus().equalsIgnoreCase("no")) {
            holder.linearfollow.setVisibility(View.VISIBLE);
            holder.linearunfollow.setVisibility(View.GONE);

        }

        holder.storename.setText(object.getStoreName());
        holder.storelocation.setText(object.getLocation());
        holder.storewebsite.setText(object.getWebsite());
        holder.storetiming.setText(object.getOpenTime() + " TO " + object.getCloseTime());
        holder.serviceOffered.setText(object.getCategory());
        holder.storeworkingdays.setText("working days:" + object.getWorkingDays());
        holder.btnlike.setText("Likes(" + object.getLikecount() + ")");
        holder.btnfollow.setText("Follow(" + object.getFollowcount() + ")");
        holder.ratingBar.setRating(Float.parseFloat(String.valueOf(object.getRating())));

        if (object.getWebsite() == null || object.getWebsite().isEmpty() || object.getWebsite().equals("null")) {
            holder.storewebsite.setText("No website found");
        } else {
            holder.storewebsite.setText(object.getWebsite());
        }

        holder.storename.setTypeface(tf);
        holder.storelocation.setTypeface(tf);
        holder.storewebsite.setTypeface(tf);
        holder.storeworkingdays.setTypeface(tf);
        holder.storetiming.setTypeface(tf);
        holder.btnlike.setTypeface(tf);
        holder.btnfollow.setTypeface(tf);
        holder.serviceOffered.setTypeface(tf);

        holder.call_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StoreContact = object.getContact();
                call(StoreContact);

            }
        });

        image = activity.getString(R.string.base_image_url) + object.getStoreImage();

        if (object.getStoreImage() == null || object.getStoreImage().equals("") || object.getStoreImage().equals("null")) {
            holder.store_image.setBackgroundResource(R.drawable.logo48x48);
        } else {

            /****************
             Glide code for image uploading

             *****************/
            Glide.with(activity)
                    .load(image)
                    .bitmapTransform(new CropCircleTransformation(activity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .placeholder(R.drawable.logo48x48) //To show image before loading an original image.
                    //.error(R.drawable.blocked) //To show error image if problem in loading.
                    .into(holder.store_image);
        }


        holder.detailrel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("StoreContact", object.getContact());
                b.putInt("store_id", object.getStoreId());
                Intent intent = new Intent(activity, StoreViewActivity.class);
                intent.putExtras(b);
                activity.startActivity(intent);
            }
        });

        holder.linearlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likecountint = object.getLikecount();
                StoreContact = object.getContact();//holder.callText.getText().toString();
                StoreId = object.getStoreId();
                mApiCall.Like(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""), object.getContact(), "2", StoreId, 0, 0, 0, 0, 0, 0);

                holder.linearunlike.setVisibility(View.VISIBLE);
                holder.linearlike.setVisibility(View.GONE);

                likecountint++;
                object.setLikecount(likecountint);
                holder.btnlike.setText("Likes(" + String.valueOf(likecountint) + ")");
                object.setLikestatus("yes");
            }
        });

        holder.linearunlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likecountint = object.getLikecount();
                StoreContact = object.getContact();//holder.callText.getText().toString();
                StoreId = object.getStoreId();

                mApiCall.UnLike(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""), StoreContact, "2", StoreId, 0, 0, 0, 0, 0, 0);

                holder.linearunlike.setVisibility(View.GONE);
                holder.linearlike.setVisibility(View.VISIBLE);
                likecountint--;
                object.setLikecount(likecountint);
                holder.btnlike.setText("Likes(" + String.valueOf(likecountint) + ")");
                object.setLikestatus("no");
            }
        });


        holder.linearfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followcountint = object.getFollowcount();
                StoreContact = object.getContact();//holder.callText.getText().toString();
                StoreId = object.getStoreId();
                mApiCall.Follow(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""), StoreContact, "2", StoreId, 0, 0, 0);
                holder.linearfollow.setVisibility(View.GONE);
                holder.linearunfollow.setVisibility(View.VISIBLE);

                followcountint++;
                object.setFollowcount(followcountint);
                holder.btnfollow.setText("Follow(" + followcountint + ")");
                object.setFollowstatus("yes");
            }
        });

        holder.linearunfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followcountint = object.getFollowcount();
                StoreContact = object.getContact();//holder.callText.getText().toString();
                StoreId = object.getStoreId();
                mApiCall.UnFollow(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""), StoreContact, "2", StoreId, 0, 0, 0);
                holder.linearfollow.setVisibility(View.VISIBLE);
                holder.linearunfollow.setVisibility(View.GONE);

                followcountint--;
                object.setFollowcount(followcountint);
                holder.btnfollow.setText("Follow(" + followcountint + ")");
                object.setFollowstatus("no");
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
                                allDetails = object.getStoreName() + "=" +
                                        object.getWebsite() + "=" +
                                        "-" + "=" +
                                        object.getWorkingDays() + "=" +
                                        object.getOpenTime() + "to" + object.getCloseTime() + "=" +
                                        object.getLocation() + "=" +
                                        object.getStoreImage() + "=" +
                                        object.getRating() + "=" +
                                        object.getLikecount() + "=" +
                                        object.getFollowcount();

                                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allDetails).apply();
                                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putInt("Share_store_id", object.getStoreId()).apply();
                                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_keyword", "store").apply();

                                Intent i = new Intent(activity, ShareWithinAppActivity.class);
                                activity.startActivity(i);
                                break;

                            case R.id.other:
                                if (object.getStoreImage().equalsIgnoreCase(null) ||
                                        object.getStoreImage().equalsIgnoreCase("") ||
                                        object.getStoreImage().equalsIgnoreCase("null")) {
                                    imagename = activity.getString(R.string.base_image_url) + "logo48x48.png";
                                } else {
                                    imagename = activity.getString(R.string.base_image_url) + object.getStoreImage();
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

                                String allStoreDetailss = "Store name : " + object.getStoreName() + "\n" +
                                        "Store type : " + object.getStoreType() + "\n" +
                                        "Ratings : " + object.getRating() + "\n" +
                                        "Likes : " + object.getLikecount() + "\n" +
                                        "Website : " + object.getWebsite() + "\n" +
                                        "Timing : " + object.getOpenTime() + "to" + object.getCloseTime() + "\n" +
                                        "Working Days : " + object.getWorkingDays() + "\n" +
                                        "Location : " + object.getLocation();


                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my store on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/store/main/" + object.getStoreId()
                                        + "/" + object.getContact()
                                        + "\n" + "\n" + allStoreDetailss);
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

    //Calling Functio
    private void call(String contact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact));
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

    @Override
    public int getItemCount() {
        return mMainList.size();
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
        TextView storename, storelocation, storewebsite, storetiming, storeworkingdays, serviceOffered;
        ImageView store_image, call_image;
        TextView btnshare, btnlike, btnfollow, btnunlike, btnunfollow;
        Button linearlike, linearunlike, linearfollow, linearunfollow, mShare;
        CardView detailrel;

        //RelativeLayout detailrel;
        RatingBar ratingBar;

        StoreHolder(View itemView) {
            super(itemView);

            storename = (TextView) itemView.findViewById(R.id.storename);
            storelocation = (TextView) itemView.findViewById(R.id.storelocation);
            storewebsite = (TextView) itemView.findViewById(R.id.storewebsite);
            storetiming = (TextView) itemView.findViewById(R.id.storetiming);
            storeworkingdays = (TextView) itemView.findViewById(R.id.storeworkingdays);
            ratingBar = (RatingBar) itemView.findViewById(R.id.store_rating);
            store_image = (ImageView) itemView.findViewById(R.id.storeprofileimage);
            call_image = (ImageView) itemView.findViewById(R.id.call_image);
            btnlike = (TextView) itemView.findViewById(R.id.like);
            btnfollow = (TextView) itemView.findViewById(R.id.follow);
            detailrel = (CardView) itemView.findViewById(R.id.card_view);
            serviceOffered = (TextView) itemView.findViewById(R.id.servicesOffered);

            linearlike = (Button) itemView.findViewById(R.id.linearlike);
            linearunlike = (Button) itemView.findViewById(R.id.linearunlike);
            linearfollow = (Button) itemView.findViewById(R.id.linearfollow);
            linearunfollow = (Button) itemView.findViewById(R.id.linearunfollow);
            mShare = (Button) itemView.findViewById(R.id.linearshare);

        }
    }
}
