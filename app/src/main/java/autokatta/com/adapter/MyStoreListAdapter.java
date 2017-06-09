package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
        TextView stname, stlocation, stwebsite, storetiming, stlike, stshare, stfollow, stworkdays;
        ImageView img, storedelete;
        RatingBar storerating;
        LinearLayout linearlike, linearunlike, linearshare, linearshare1, linearfollow, linearunfollow;


        YoHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.adapter_mystoreListCard_view);
            stname = (TextView) itemView.findViewById(R.id.editstname);
            stlocation = (TextView) itemView.findViewById(R.id.autolocation);
            stwebsite = (TextView) itemView.findViewById(R.id.editwebsite);
            storetiming = (TextView) itemView.findViewById(R.id.edittiming);
          //  stclose = (TextView) itemView.findViewById(R.id.edittiming1);
            stworkdays = (TextView) itemView.findViewById(R.id.editworkingdays);
            img = (ImageView) itemView.findViewById(R.id.profile);
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
    public void onBindViewHolder(final MyStoreListAdapter.YoHolder holder, final int position) {
        myContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).
                getString("loginContact", "");
        holder.stname.setText(mStoreList.get(position).getName());
        holder.stlocation.setText(mStoreList.get(position).getLocation());
        holder.stwebsite.setText(mStoreList.get(position).getWebsite());
        holder.storetiming.setText(mStoreList.get(position).getStoreOpenTime()+" TO "+(mStoreList.get(position).getStoreCloseTime()));
       // holder.stclose.setText(mStoreList.get(position).getStoreCloseTime());
        holder.stlike.setText("Like(" + mStoreList.get(position).getLikecount() + ")");
        holder.stfollow.setText("Follow(" + mStoreList.get(position).getFollowcount() + ")");
        //   holder.stshare.setText("share "+sshare);
        holder.stworkdays.setText(mStoreList.get(position).getWorkingDays());
        holder.storerating.setEnabled(false);
        if (!(mStoreList.get(position).getRating() == null) && !mStoreList.get(position).getRating().equals("null"))
            holder.storerating.setRating(Float.parseFloat(mStoreList.get(position).getRating()));

        holder.storedelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = mStoreList.size();
                if (size == 1)
                    Snackbar.make(view, "You can not delete this store", Snackbar.LENGTH_SHORT).show();
                else {
                    if (!mConnectionDetector.isConnectedToInternet())
                        Snackbar.make(view, mActivity.getString(R.string.no_internet), Snackbar.LENGTH_SHORT).show();
                    else {
                        new AlertDialog.Builder(mActivity)
                                .setTitle("Delete?")
                                .setMessage("Are You Sure You Want To Delete This Store?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteStore(mStoreList.get(position).getId());
                                        mStoreList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, mStoreList.size());
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
                Snackbar.make(view, "You can not like your own store", Snackbar.LENGTH_SHORT).show();
            }
        });
        holder.linearfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You can not follow your own store", Snackbar.LENGTH_SHORT).show();
            }
        });


        holder.linearshare1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String imageshare = "";
                imageshare = "http://autokatta.com/mobile/store_profiles/" + mStoreList.get(position).getStoreImage();

                imageshare = imageshare.replaceAll(" ", "%20");
                System.out.println("image============" + imageshare);

                String timing = mStoreList.get(position).getStoreOpenTime() + "" + mStoreList.get(position).getStoreCloseTime();

                strDetailsShare = mStoreList.get(position).getName() + "=" + mStoreList.get(position).getWebsite() + "="
                        + timing + "=" + mStoreList.get(position).getWorkingDays() + "="
                        + mStoreList.get(position).getStoreType() + "=" + mStoreList.get(position).getLocation() + "="
                        + mStoreList.get(position).getStoreImage() + "=" + mStoreList.get(position).getRating() + "="
                        + mStoreList.get(position).getLikecount() + "=" + mStoreList.get(position).getFollowcount();

                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", strDetailsShare).apply();
                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_store_id", mStoreList.get(position).getId()).apply();
                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "store").apply();

                mActivity.startActivity(new Intent(mActivity, ShareWithinAppActivity.class));
                mActivity.finish();

            }
        });


        holder.linearshare.setOnClickListener(new View.OnClickListener() {

            Intent intent = new Intent(Intent.ACTION_SEND);
            String imageFilePath = "", imagename = "";

            @Override
            public void onClick(View v) {

                if (mStoreList.get(position).getStoreImage().equalsIgnoreCase("") || mStoreList.get(position).getStoreImage().equalsIgnoreCase(null) ||
                        mStoreList.get(position).getStoreImage().equalsIgnoreCase("null")) {
                    imagename = "http://autokatta.com/mobile/store_profiles/" + "a.jpg";
                } else {
                    imagename = "http://autokatta.com/mobile/store_profiles/" + mStoreList.get(position).getStoreImage();
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

                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my store on Autokatta. Stay connected for Product and Service updates and enquiries"
                        + "\n" + "http://autokatta.com/store/main/" + mStoreList.get(position).getId() + "/" + myContact);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                mActivity.startActivity(Intent.createChooser(intent, "Autokatta"));

            }

        });

        /***Card Click Listener***/
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();

                b.putString("store_id", mStoreList.get(holder.getAdapterPosition()).getId());
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
            String dppath = "http://autokatta.com/mobile/store_profiles/" + mStoreList.get(position).getStoreImage().trim();
            Glide.with(mActivity)
                    .load(dppath)
                    .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .placeholder(R.drawable.logo)
                    .into(holder.img);
        }
    }

    private void deleteStore(String storeId) {
        ApiCall apiCall = new ApiCall(mActivity, this);
        apiCall.DeleteStore(storeId, "delete");
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string._404));
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
