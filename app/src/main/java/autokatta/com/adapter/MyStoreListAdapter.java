package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.other.FullImageActivity;
import autokatta.com.response.MyStoreResponse;
import autokatta.com.view.ShareWithinAppActivity;
import autokatta.com.view.StoreViewActivity;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

/**
 * Created by ak-003 on 28/3/17.
 */

public class MyStoreListAdapter extends RecyclerView.Adapter<MyStoreListAdapter.YoHolder> implements RequestNotifier {
    private Activity mActivity;
    private List<MyStoreResponse.Success> mStoreList = new ArrayList<>();
    private ConnectionDetector mConnectionDetector;
    private String strDetailsShare = "", myContact;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class YoHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView stname, stlocation, userRole, stwebsite, storetiming, stlike, stshare, stfollow, stworkdays, serviceOffered;
        ImageView img, storedelete, storeShare;
        RatingBar storerating;
        LinearLayout linearlike, linearunlike, linearshare, linearshare1, linearfollow, linearunfollow;


        YoHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.adapter_mystoreListCard_view);
            stname = (TextView) itemView.findViewById(R.id.editstname);
            stlocation = (TextView) itemView.findViewById(R.id.autolocation);
            stwebsite = (TextView) itemView.findViewById(R.id.editwebsite);
            userRole = (TextView) itemView.findViewById(R.id.userRole);
            storetiming = (TextView) itemView.findViewById(R.id.edittiming);
            serviceOffered = (TextView) itemView.findViewById(R.id.servicesOffered);
            stworkdays = (TextView) itemView.findViewById(R.id.editworkingdays);
            img = (ImageView) itemView.findViewById(R.id.profile);
            storeShare = (ImageView) itemView.findViewById(R.id.storeShare);
            storerating = (RatingBar) itemView.findViewById(R.id.storerating);
            storedelete = (ImageView) itemView.findViewById(R.id.storedelete);
            stlike = (TextView) itemView.findViewById(R.id.textlike);
            stshare = (TextView) itemView.findViewById(R.id.textshare);
            stfollow = (TextView) itemView.findViewById(R.id.textfollow);
            linearlike = (LinearLayout) itemView.findViewById(R.id.linearlike);
            linearunlike = (LinearLayout) itemView.findViewById(R.id.linearunlike);
            linearshare = (LinearLayout) itemView.findViewById(R.id.linearshare);
            linearshare1 = (LinearLayout) itemView.findViewById(R.id.linearshare1);
            linearfollow = (LinearLayout) itemView.findViewById(R.id.linearfollow);
            linearunfollow = (LinearLayout) itemView.findViewById(R.id.linearunfollow);

        }
    }

    @Override
    public int getItemCount() {
        return mStoreList.size();
    }

    public MyStoreListAdapter(Activity mActivity1, List<MyStoreResponse.Success> mItemList) {
        try {
            this.mActivity = mActivity1;
            this.mStoreList = mItemList;

            mConnectionDetector = new ConnectionDetector(mActivity);
        } catch (ClassCastException c) {
            c.printStackTrace();
        }
    }

    @Override
    public MyStoreListAdapter.YoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_my_storelist, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new YoHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyStoreListAdapter.YoHolder holder, int position) {
        myContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).
                getString("loginContact", "");
        Typeface tf = Typeface.createFromAsset(mActivity.getAssets(), "font/Roboto-Light.ttf");
        holder.stname.setText(mStoreList.get(position).getName());
        holder.stlocation.setText(mStoreList.get(position).getLocation());
        holder.stwebsite.setText(mStoreList.get(position).getWebsite());
        holder.serviceOffered.setText(mStoreList.get(position).getCategory());
        holder.storetiming.setText(mStoreList.get(position).getStoreOpenTime()+" TO "+(mStoreList.get(position).getStoreCloseTime()));
       // holder.stclose.setText(mStoreList.get(position).getStoreCloseTime());
        holder.stlike.setText("Like(" + mStoreList.get(position).getLikecount() + ")");
        holder.stfollow.setText("Follow(" + mStoreList.get(position).getFollowcount() + ")");
        //   holder.stshare.setText("share "+sshare);
        holder.stworkdays.setText(mStoreList.get(position).getWorkingDays());
        holder.userRole.setText("Role :" + mStoreList.get(position).getRole());
        holder.storerating.setEnabled(false);
        if (mStoreList.get(position).getRating() != 0)
            holder.storerating.setRating(mStoreList.get(position).getRating());

        LayerDrawable stars = (LayerDrawable) holder.storerating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(ContextCompat.getColor(mActivity,R.color.medium_sea_green), PorterDuff.Mode.SRC_ATOP);//After filled
        stars.getDrawable(0).setColorFilter(ContextCompat.getColor(mActivity,R.color.black), PorterDuff.Mode.SRC_ATOP);//empty
        stars.getDrawable(1).setColorFilter(ContextCompat.getColor(mActivity,R.color.textColor), PorterDuff.Mode.SRC_ATOP);//



        if (mStoreList.get(position).getWebsite() == null || mStoreList.get(position).getWebsite().isEmpty() ||
                mStoreList.get(position).getWebsite().equals("null")) {
            holder.stwebsite.setText("No website found");
        } else {
            holder.stwebsite.setText(mStoreList.get(position).getWebsite());
        }

      //  holder.stname.setTypeface(tf);
        holder.stlocation.setTypeface(tf);
        holder.stwebsite.setTypeface(tf);
        holder.stworkdays.setTypeface(tf);
        holder.storetiming.setTypeface(tf);
        holder.stlike.setTypeface(tf);
        holder.stfollow.setTypeface(tf);
        holder.serviceOffered.setTypeface(tf);

        holder.storedelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = mStoreList.size();
                if (size == 1)
                    CustomToast.customToast(mActivity, "You can not delete this store");
                else {
                    if (!mConnectionDetector.isConnectedToInternet())
                        CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));

                    else {
                        new AlertDialog.Builder(mActivity)
                                .setTitle("Delete?")
                                .setMessage("Are You Sure You Want To Delete This Store?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteStore(mStoreList.get(holder.getAdapterPosition()).getId());
                                        mStoreList.remove(holder.getAdapterPosition());
                                        notifyItemRemoved(holder.getAdapterPosition());
                                        notifyItemRangeChanged(holder.getAdapterPosition(), mStoreList.size());
                                    }
                                })

                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
            }
        });

        holder.linearlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomToast.customToast(mActivity, "You can not like your own store");

            }
        });
        holder.linearfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomToast.customToast(mActivity, "You can not follow your own store");

            }
        });

        holder.storeShare.setOnClickListener(new View.OnClickListener() {

            Intent intent = new Intent(Intent.ACTION_SEND);
            String imageFilePath = "", imagename = "";
            @Override
            public void onClick(View view) {

                PopupMenu mPopupMenu = new PopupMenu(mActivity, holder.storeShare);
                mPopupMenu.getMenuInflater().inflate(R.menu.more_menu, mPopupMenu.getMenu());
                mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.autokatta:


                                String imageshare = "";
                                imageshare = mActivity.getString(R.string.base_image_url) + mStoreList.get(holder.getAdapterPosition()).getStoreImage();

                                imageshare = imageshare.replaceAll(" ", "%20");
                                System.out.println("image============" + imageshare);

                                String timing = mStoreList.get(holder.getAdapterPosition()).getStoreOpenTime() + " To " + mStoreList.get(holder.getAdapterPosition()).getStoreCloseTime();

                                strDetailsShare = mStoreList.get(holder.getAdapterPosition()).getName() + "=" + mStoreList.get(holder.getAdapterPosition()).getWebsite() + "="
                                        + timing + "=" + mStoreList.get(holder.getAdapterPosition()).getWorkingDays() + "="
                                        + mStoreList.get(holder.getAdapterPosition()).getStoreType() + "=" + mStoreList.get(holder.getAdapterPosition()).getLocation() + "="
                                        + mStoreList.get(holder.getAdapterPosition()).getStoreImage() + "=" + mStoreList.get(holder.getAdapterPosition()).getRating() + "="
                                        + mStoreList.get(holder.getAdapterPosition()).getLikecount() + "=" + mStoreList.get(holder.getAdapterPosition()).getFollowcount();

                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", strDetailsShare).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putInt("Share_store_id", mStoreList.get(holder.getAdapterPosition()).getId()).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_keyword", "store").apply();

                                mActivity.startActivity(new Intent(mActivity, ShareWithinAppActivity.class));
                                break;

                            case R.id.other:

                                if (mStoreList.get(holder.getAdapterPosition()).getStoreImage().equalsIgnoreCase("") || mStoreList.get(holder.getAdapterPosition()).getStoreImage().equalsIgnoreCase(null) ||
                                        mStoreList.get(holder.getAdapterPosition()).getStoreImage().equalsIgnoreCase("null")) {
                                    imagename = mActivity.getString(R.string.base_image_url) + "logo48x48.png";
                                } else {
                                    imagename = mActivity.getString(R.string.base_image_url) + mStoreList.get(holder.getAdapterPosition()).getStoreImage();
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


                                String timing1 = mStoreList.get(holder.getAdapterPosition()).getStoreOpenTime() + " To " + mStoreList.get(holder.getAdapterPosition()).getStoreCloseTime();

                                String allStoreDetailss = "Store name : " + mStoreList.get(holder.getAdapterPosition()).getName().toString() + "\n" +
                                        "Store type : " + mStoreList.get(holder.getAdapterPosition()).getStoreType().toString() + "\n" +
                                        "Ratings : " + mStoreList.get(holder.getAdapterPosition()).getRating().toString() + "\n" +
                                        "Likes : " + mStoreList.get(holder.getAdapterPosition()).getLikecount() + "\n" +
                                        "Website : " + mStoreList.get(holder.getAdapterPosition()).getWebsite() + "\n" +
                                        "Timing : " + timing1 + "\n" +
                                        "Working Days : " + mStoreList.get(holder.getAdapterPosition()).getWorkingDays().toString() + "\n" +
                                        "Location : " + mStoreList.get(holder.getAdapterPosition()).getLocation().toString();

                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my store on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/store/main/" + mStoreList.get(holder.getAdapterPosition()).getId()
                                        + "/" + mStoreList.get(holder.getAdapterPosition()).getContact()
                                        + "\n" + "\n" + allStoreDetailss);
                                intent.setType("image/jpeg");
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                mActivity.startActivity(Intent.createChooser(intent, "Autokatta"));
                                break;
                        }
                        return false;
                    }
                });
                mPopupMenu.show(); //showing popup menu

            }
        });

        /***Card Click Listener***/
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();

                b.putInt("store_id", mStoreList.get(holder.getAdapterPosition()).getId());
                ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent intent = new Intent(mActivity, StoreViewActivity.class);
                intent.putExtras(b);
                mActivity.startActivity(intent, options.toBundle());
                //mActivity.finish();
            }
        });

        Log.i("img", "adapter->" + mStoreList.get(position).getStoreImage() + "-" + position);
        if (mStoreList.get(position).getStoreImage().equals("") || mStoreList.get(position).getStoreImage().equals(null) ||
                mStoreList.get(position).getStoreImage().equals("null")) {
            holder.img.setBackgroundResource(R.drawable.logo);
        } else {
            //mItemList.get(position).getImage() = mItemList.get(position).getImage().replaceAll(" ", "%20");
            String dppath = mActivity.getString(R.string.base_image_url) + mStoreList.get(position).getStoreImage().trim();
            Glide.with(mActivity)
                    .load(dppath)
                    .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .placeholder(R.drawable.logo)
                    .into(holder.img);
        }

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String image;
                if (mStoreList.get(holder.getAdapterPosition()).getStoreImage().equals(""))
                    image = mActivity.getString(R.string.base_image_url) + "logo48x48.png";
                else
                    image = mActivity.getString(R.string.base_image_url) + mStoreList.get(holder.getAdapterPosition()).getStoreImage();
                Intent intent = new Intent(mActivity, FullImageActivity.class);
                Bundle b = new Bundle();
                b.putString("image", image);
                intent.putExtras(b);
                mActivity.startActivity(intent);
            }
        });
    }

    private void deleteStore(int storeId) {
        ApiCall apiCall = new ApiCall(mActivity, this);
        apiCall.DeleteStore(storeId, "delete");
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "MyStoreList Adapter");
        }
    }

    @Override
    public void notifyString(String str) {

        if (str != null) {
            if (str.startsWith("success")) {
                CustomToast.customToast(mActivity, "Store deleted");

                //mStoreList.remove(getAdapterPosition());
            }

        }
    }
}
