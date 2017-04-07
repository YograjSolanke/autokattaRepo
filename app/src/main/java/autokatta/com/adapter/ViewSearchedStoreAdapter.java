package autokatta.com.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.SearchStoreResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-004 on 7/4/17.
 */

public class ViewSearchedStoreAdapter extends RecyclerView.Adapter<ViewSearchedStoreAdapter.StoreHolder> {

    List<SearchStoreResponse.Success> mMainList;
    Activity activity;
    String StoreContact, StoreId;
    int likecountint, followcountint;
    String imagename = "", allDetails;

    public ViewSearchedStoreAdapter(Activity activity, List<SearchStoreResponse.Success> successList) {

        this.activity = activity;
        this.mMainList = successList;
    }

    @Override
    public ViewSearchedStoreAdapter.StoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_searched_store_adapter, parent, false);
        StoreHolder holder = new StoreHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewSearchedStoreAdapter.StoreHolder holder, final int position) {

        final SearchStoreResponse.Success object = mMainList.get(position);
        final String image;

        if (object.getLikestatus().equals("yes")) {
            holder.linearunlike.setVisibility(View.VISIBLE);
            holder.linearlike.setVisibility(View.GONE);
        }
        if (object.getLikecount().equals("no")) {
            holder.linearunlike.setVisibility(View.GONE);
            holder.linearlike.setVisibility(View.VISIBLE);

        }

        if (object.getFollowstatus().equals("yes")) {
            holder.linearfollow.setVisibility(View.GONE);
            holder.linearunfollow.setVisibility(View.VISIBLE);
        }

        if (object.getFollowstatus().equals("no")) {
            holder.linearfollow.setVisibility(View.VISIBLE);
            holder.linearunfollow.setVisibility(View.GONE);

        }

        holder.storename.setText(object.getStoreName());
        holder.storelocation.setText(object.getLocation());
        holder.storewebsite.setText(object.getWebsite());
        holder.storetiming.setText(object.getOpenTime() + "to" + object.getCloseTime());
        holder.callText.setText(object.getContact());
        holder.storeworkingdays.setText("working days:" + object.getWorkingDays());
        holder.btnlike.setText("Likes(" + object.getLikecount() + ")");
        holder.btnfollow.setText("Follow(" + object.getFollowcount() + ")");

        holder.call_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StoreContact = object.getContact();
                call(StoreContact);

            }
        });

        image = "http://autokatta.com/mobile/store_profiles/" + object.getStoreImage();

        if (object.getStoreImage() == null) {
            holder.store_image.setBackgroundResource(R.drawable.profile);
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


        holder.detailrel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Bundle b=new Bundle();
//                b.putString("action", "wall");
//                //b.putString("contact", storecontact_list.get(position).toString());
//                b.putString("store_id", storeid_list.get(position));
//
//                StoreviewFragment fragment= new StoreviewFragment();
//                fragment.setArguments(b);
//                FragmentManager mFragmentManagerm  = ctx.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = mFragmentManagerm.beginTransaction();
//                fragmentTransaction.replace(R.id.containerView, fragment).addToBackStack("store_view").commit();
            }
        });

        holder.linearlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String likecountstr = object.getLikecount();
                likecountint = Integer.parseInt(likecountstr);
                StoreContact = holder.callText.getText().toString();
                StoreId = object.getStoreId();

                // sendLike(StoreId, StoreContact);

                holder.linearunlike.setVisibility(View.VISIBLE);
                holder.linearlike.setVisibility(View.GONE);

                likecountint++;
                object.setLikecount(String.valueOf(likecountint));
                holder.btnlike.setText("Likes(" + likecountint + ")");
                object.setLikestatus("yes");

            }
        });

        holder.linearunlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String likecountstr = object.getLikecount();
                likecountint = Integer.parseInt(likecountstr);
                StoreContact = holder.callText.getText().toString();
                StoreId = object.getStoreId();

                // sendUnLike(StoreId, StoreContact);

                holder.linearunlike.setVisibility(View.GONE);
                holder.linearlike.setVisibility(View.VISIBLE);
                likecountint--;
                object.setLikecount(String.valueOf(likecountint));
                holder.btnlike.setText("Likes(" + likecountint + ")");
                object.setLikestatus("no");

            }
        });


        holder.linearfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String followcountstr = object.getFollowcount();
                followcountint = Integer.parseInt(followcountstr);

                StoreContact = holder.callText.getText().toString();
                StoreId = object.getStoreId();

                //  sendFollower(StoreId, StoreContact);

                holder.linearfollow.setVisibility(View.GONE);
                holder.linearunfollow.setVisibility(View.VISIBLE);

                followcountint++;
                object.setFollowcount(String.valueOf(followcountint));
                holder.btnfollow.setText("Follow(" + followcountint + ")");

                object.setFollowstatus("yes");

            }
        });

        holder.linearunfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String followcountstr = object.getFollowcount();
                followcountint = Integer.parseInt(followcountstr);

                StoreContact = holder.callText.getText().toString();
                StoreId = object.getStoreId();

                // sendUnFollower(StoreId, StoreContact);

                holder.linearfollow.setVisibility(View.VISIBLE);
                holder.linearunfollow.setVisibility(View.GONE);

                followcountint--;
                object.setFollowcount(String.valueOf(followcountint));
                holder.btnfollow.setText("Follow(" + followcountint + ")");

                object.setFollowstatus("no");

            }
        });

        holder.linearshare1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                        putString("Share_store_id", object.getStoreId()).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "store").apply();


//                ShareWithinApp fr = new ShareWithinApp();
//                FragmentManager fragmentManager = ctx.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.containerView, fr);
//                fragmentTransaction.addToBackStack("sharewithinapp");
//                fragmentTransaction.commit();

            }
        });


        holder.linearshare.setOnClickListener(new View.OnClickListener() {

            Intent intent = new Intent(Intent.ACTION_SEND);
            String imageFilePath;


            @Override
            public void onClick(View v) {

                if (object.getStoreImage().equalsIgnoreCase("") || object.getStoreImage().equalsIgnoreCase(null) ||
                        object.getStoreImage().equalsIgnoreCase("null")) {
                    imagename = "http://autokatta.com/mobile/store_profiles/" + "a.jpg";
                } else {
                    imagename = "http://autokatta.com/mobile/store_profiles/" + object.getStoreImage();
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

                manager.enqueue(request);

                imageFilePath = "/storage/emulated/0/Download/" + filename;
                System.out.println("ImageFilePath:" + imageFilePath);

                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my store on Autokatta. Stay connected for Product and Service updates and enquiries"
                        + "\n" + "http://autokatta.com/store/main/" + object.getStoreImage() + "/" + object.getStoreImage());
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                activity.startActivity(Intent.createChooser(intent, "Autokatta"));


            }
        });
//
//        vi.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//
//                return false;
//            }
//        });


    }

    //Calling Functio
    private void call(String contact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact));
        System.out.println("calling started=========" + StoreContact);

        System.out.println("calling started");
        try {
            activity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Car Details Fragment\n");
        }
    }

    @Override
    public int getItemCount() {
        return mMainList.size();
    }

    static class StoreHolder extends RecyclerView.ViewHolder {

        TextView storename, storelocation, storewebsite, storetiming, storeworkingdays, callText;
        ImageView store_image, call_image;
        TextView btnshare, btnlike, btnfollow, btndetail, btnunlike, btnunfollow;

        ImageView starrating1, starrating2, starrating3, starrating4, starrating5;
        LinearLayout linearlike, linearunlike, linearfollow, linearunfollow, linearshare, linearshare1;

        RelativeLayout detailrel;

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
            callText = (TextView) itemView.findViewById(R.id.callText);


            linearshare = (LinearLayout) itemView.findViewById(R.id.linearshare);
            linearshare1 = (LinearLayout) itemView.findViewById(R.id.linearshare1);
            linearlike = (LinearLayout) itemView.findViewById(R.id.linearlike);
            linearunlike = (LinearLayout) itemView.findViewById(R.id.linearunlike);
            linearfollow = (LinearLayout) itemView.findViewById(R.id.linearfollow);
            linearunfollow = (LinearLayout) itemView.findViewById(R.id.linearunfollow);
            detailrel = (RelativeLayout) itemView.findViewById(R.id.rel);

            btndetail = (TextView) itemView.findViewById(R.id.details);

        }
    }
}
