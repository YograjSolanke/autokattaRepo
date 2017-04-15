package autokatta.com.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.BrowseStoreResponse;
import autokatta.com.view.OtherStoreView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 5/4/17.
 */

public class BrowseStoreAdapter extends RecyclerView.Adapter<BrowseStoreAdapter.StoreHolder> {

    List<BrowseStoreResponse.Success> mMainlist;
    Activity activity;
    String image = "";
    String StoreContact, StoreId, allDetails;
    int likecountint, followcountint;


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


        holder.storename.setText(success.getStoreName());
        holder.storelocation.setText(success.getLocation());
        holder.storewebsite.setText(success.getWebsite());

        holder.storeworkingdays.setText("working days:" + success.getWorkingDays());
        holder.btnlike.setText("Likes(" + success.getLikecount() + ")");
        holder.btnfollow.setText("Follow(" + success.getFollowcount() + ")");
        holder.storerating.setEnabled(false);


        if (success.getLikestatus().equalsIgnoreCase("yes")) {
            holder.linearlike.setVisibility(View.INVISIBLE);
            holder.linearunlike.setVisibility(View.VISIBLE);
        }
        if (success.getLikestatus().equalsIgnoreCase("no")) {
            holder.linearlike.setVisibility(View.VISIBLE);
            holder.linearunlike.setVisibility(View.INVISIBLE);
        }

        if (success.getFollowstatus().equalsIgnoreCase("yes")) {
            holder.linearfollow.setVisibility(View.INVISIBLE);
            holder.linearunfollow.setVisibility(View.VISIBLE);
        }
        if (success.getFollowstatus().equalsIgnoreCase("no")) {
            holder.linearfollow.setVisibility(View.VISIBLE);
            holder.linearunfollow.setVisibility(View.INVISIBLE);
        }

        if (success.getRating().equals("0")) {
        } else if (!success.getRating().equals("0")) {
            holder.storerating.setRating(Float.parseFloat(success.getRating()));

        }

        image = "http://autokatta.com/mobile/store_profiles/" + success.getStoreImage();

        if (success.getStoreImage() == null) {
            holder.store_image.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.profile));
        } else {


            /****************
             Glide code for image uploading

             *****************/
            Glide.with(activity)
                    .load(image)
                    .bitmapTransform(new CropCircleTransformation(activity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    //.placeholder(R.drawable.logo) //To show image before loading an original image.
                    //.error(R.drawable.blocked) //To show error image if problem in loading.
                    .into(holder.store_image);
        }

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();

                b.putString("StoreContact", success.getContactNo());
                b.putString("store_id", success.getStoreId());
                Intent intent = new Intent(activity, OtherStoreView.class);
                intent.putExtras(b);
                activity.startActivity(intent);

//                StoreviewFragment fragment = new StoreviewFragment();
//                fragment.setArguments(b);
//                FragmentManager mFragmentManagerm = ctx.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = mFragmentManagerm.beginTransaction();
//                fragmentTransaction.replace(R.id.containerView, fragment).addToBackStack("store_view").commit();
            }
        });


        holder.linearlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StoreContact = success.getContactNo();
                if (!StoreContact.equals(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""))) {
                    String likecountstr = success.getLikecount();
                    likecountint = Integer.parseInt(likecountstr);

                    StoreId = success.getStoreId();

                    //sendLike(StoreId, StoreContact);

                    holder.linearunlike.setVisibility(View.VISIBLE);
                    holder.linearlike.setVisibility(View.INVISIBLE);

                    likecountint++;
                    success.setLikecount(String.valueOf(likecountint));
                    holder.btnlike.setText("Likes(" + likecountint + ")");

                    success.setLikestatus("yes");
                } else {
                    Snackbar.make(v, "Sorry!! you can not like your own store", Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        holder.linearunlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "clicked on unlike " + position, Toast.LENGTH_LONG).show();

                StoreContact = success.getContactNo();
                if (!StoreContact.equals(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""))) {
                    String likecountstr = success.getLikecount();
                    likecountint = Integer.parseInt(likecountstr);

                    StoreId = success.getStoreId();

                    //    sendUnLike(StoreId, StoreContact);

                    holder.linearunlike.setVisibility(View.INVISIBLE);
                    holder.linearlike.setVisibility(View.VISIBLE);
                    likecountint--;
                    success.setLikecount(String.valueOf(likecountint));
                    holder.btnlike.setText("Likes(" + likecountint + ")");

                    success.setLikestatus("no");
                } else {
                    Snackbar.make(v, "Sorry!! you can not unlike your own store", Snackbar.LENGTH_SHORT).show();
                }

            }
        });


        holder.linearfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StoreContact = success.getContactNo();
                if (!StoreContact.equals(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""))) {
                    String followcountstr = success.getFollowcount();
                    followcountint = Integer.parseInt(followcountstr);

                    StoreId = success.getStoreId();

                    //sendFollower(StoreId, StoreContact);
                    holder.linearfollow.setVisibility(View.INVISIBLE);
                    holder.linearunfollow.setVisibility(View.VISIBLE);

                    followcountint++;
                    success.setFollowcount(String.valueOf(followcountint));
                    holder.btnfollow.setText("Follow(" + followcountint + ")");

                    success.setFollowstatus("yes");
                } else {
                    Snackbar.make(v, "Sorry!! you can not follow your own store", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
//


        holder.call_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreContact = success.getContactNo();
                if (!StoreContact.equals(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""))) {
                    call(StoreContact);
                } else {
                    Snackbar.make(v, "Sorry!! you can not call yourself ", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
//
//
        holder.linearshare1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
                allDetails = success.getStoreName() + "=" +
                        success.getWebsite() + "=" +
                        "-" + "=" +
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
                        putString("Share_store_id", success.getStoreId()).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "store").apply();


//                ShareWithinApp fr = new ShareWithinApp();
//                // fr.setArguments(b);
//
//                FragmentManager fragmentManager = ctx.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.containerView, fr);
//                fragmentTransaction.addToBackStack("sharewithinapp");
//                fragmentTransaction.commit();

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
                    image = "http://autokatta.com/mobile/store_profiles/" + "a.jpg";
                } else {
                    image = "http://autokatta.com/mobile/store_profiles/" + success.getStoreImage();
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

    static class StoreHolder extends RecyclerView.ViewHolder {


        TextView storename, storelocation, storewebsite, storetiming, storeworkingdays;
        ImageView store_image, leftarrow, rihgtarrow, call_image;
        TextView btnshare, btnlike, btnfollow, btnunlike, btnunfollow;
        RatingBar storerating;
        RelativeLayout linearlike, linearunlike, linearfollow, linearunfollow;
        LinearLayout linearshare, linearshare1;
        Button btndetail;
        CardView mCardView;

        public StoreHolder(View itemView) {
            super(itemView);

            storename = (TextView) itemView.findViewById(R.id.storename);
            storelocation = (TextView) itemView.findViewById(R.id.storelocation);
            storewebsite = (TextView) itemView.findViewById(R.id.storewebsite);
            storetiming = (TextView) itemView.findViewById(R.id.storetiming);
            storeworkingdays = (TextView) itemView.findViewById(R.id.storeworkingdays);

            store_image = (ImageView) itemView.findViewById(R.id.storeprofileimage);
            call_image = (ImageView) itemView.findViewById(R.id.call_image);

            btnlike = (TextView) itemView.findViewById(R.id.like);
            btnfollow = (TextView) itemView.findViewById(R.id.follow);
            linearshare = (LinearLayout) itemView.findViewById(R.id.linearshare);
            linearshare1 = (LinearLayout) itemView.findViewById(R.id.linearshare1);
            linearlike = (RelativeLayout) itemView.findViewById(R.id.linearlike);
            linearunlike = (RelativeLayout) itemView.findViewById(R.id.linearunlike);
            linearfollow = (RelativeLayout) itemView.findViewById(R.id.linearfollow);
            linearunfollow = (RelativeLayout) itemView.findViewById(R.id.linearunfollow);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);

            btndetail = (Button) itemView.findViewById(R.id.details);
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
