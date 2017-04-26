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
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;
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
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetGroupVehiclesResponse;
import autokatta.com.view.ShareWithinAppActivity;
import autokatta.com.view.VehicleDetails;
import retrofit2.Response;

/**
 * Created by ak-001 on 25/3/17.
 */

public class GroupVehicleRefreshAdapter extends RecyclerView.Adapter<GroupVehicleRefreshAdapter.MyViewHolder> implements RequestNotifier {
    Activity mActivity;
    private List<GetGroupVehiclesResponse.Success> mItemList = new ArrayList<>();
    private String allDetails;
    private String imgUrl;
    private String myContact;
    private ApiCall mApiCall;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mRegistrationNo, mTitle, mPrice, mModel, mBrand, mUpdatedBy, mLocation, mRtoCity, mYearOfMfg, mKmsHrs;
        ImageView mShareAutokatta, mShareOther, mLike, mCall, mUnlike;
        ImageView mCardImage;

        MyViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mRegistrationNo = (TextView) itemView.findViewById(R.id.registration_no);
            mTitle = (TextView) itemView.findViewById(R.id.card_title);
            mPrice = (TextView) itemView.findViewById(R.id.price);
            mModel = (TextView) itemView.findViewById(R.id.model);
            mBrand = (TextView) itemView.findViewById(R.id.brand);
            mUpdatedBy = (TextView) itemView.findViewById(R.id.updated_by);
            mLocation = (TextView) itemView.findViewById(R.id.location);
            mRtoCity = (TextView) itemView.findViewById(R.id.rto_city);
            mYearOfMfg = (TextView) itemView.findViewById(R.id.year_of_mfg);
            mKmsHrs = (TextView) itemView.findViewById(R.id.kms_hrs);

            mShareAutokatta = (ImageView) itemView.findViewById(R.id.share_autokatta);
            mShareOther = (ImageView) itemView.findViewById(R.id.share_other);
            mLike = (ImageView) itemView.findViewById(R.id.like);
            mUnlike = (ImageView) itemView.findViewById(R.id.unlike);
            mCall = (ImageView) itemView.findViewById(R.id.call);
            mCardImage = (ImageView) itemView.findViewById(R.id.card_image);

        }
    }

    public GroupVehicleRefreshAdapter(Activity mActivity1, List<GetGroupVehiclesResponse.Success> mItemList) {
        this.mActivity = mActivity1;
        this.mItemList = mItemList;
        mApiCall = new ApiCall(mActivity, this);

        myContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "");
    }

    @Override
    public GroupVehicleRefreshAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_card_group_vehicle, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final GroupVehicleRefreshAdapter.MyViewHolder holder, final int position) {
        String register = mItemList.get(position).getRegistrationNumber();
        SpannableString sp = new SpannableString(mActivity.getString(R.string.no_register) + register);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.no_register).length(),
                mActivity.getString(R.string.no_register).length() + register.length(), 0);
        holder.mRegistrationNo.setText(sp);

        holder.mTitle.setText(mItemList.get(position).getTitle());

        String price = mItemList.get(position).getPrice();
        SpannableString sp1 = new SpannableString(mActivity.getString(R.string.price) + price);
        sp1.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.price).length(),
                mActivity.getString(R.string.price).length() + price.length(), 0);
        holder.mPrice.setText(sp1);

        String model = mItemList.get(position).getModel();
        SpannableString sp2 = new SpannableString(mActivity.getString(R.string.model) + model);
        sp2.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.model).length(),
                mActivity.getString(R.string.model).length() + model.length(), 0);
        holder.mModel.setText(sp2);

        String brand = mItemList.get(position).getManufacturer();
        SpannableString sp3 = new SpannableString(mActivity.getString(R.string.brand_no) + brand);
        sp3.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.brand_no).length(),
                mActivity.getString(R.string.brand_no).length() + brand.length(), 0);
        holder.mBrand.setText(sp3);

        String uploaded_by = mItemList.get(position).getUsername();
        SpannableString sp4 = new SpannableString(mActivity.getString(R.string.updated_by) + uploaded_by);
        sp4.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.updated_by).length(),
                mActivity.getString(R.string.updated_by).length() + uploaded_by.length(), 0);
        holder.mUpdatedBy.setText(sp4);

        String location = mItemList.get(position).getLocationCity();
        SpannableString sp5 = new SpannableString(mActivity.getString(R.string.location) + location);
        sp5.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.location).length(),
                mActivity.getString(R.string.location).length() + location.length(), 0);
        holder.mLocation.setText(sp5);

        String rto_city = mItemList.get(position).getRTOCity();
        SpannableString sp6 = new SpannableString(mActivity.getString(R.string.rto_city) + rto_city);
        sp6.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.rto_city).length(),
                mActivity.getString(R.string.rto_city).length() + rto_city.length(), 0);
        holder.mRtoCity.setText(sp6);

        String year_of_mfg = mItemList.get(position).getYearOfManufacture();
        SpannableString sp7 = new SpannableString(mActivity.getString(R.string.year_of_mfg) + year_of_mfg);
        sp7.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.year_of_mfg).length(),
                mActivity.getString(R.string.year_of_mfg).length() + year_of_mfg.length(), 0);
        holder.mYearOfMfg.setText(sp7);

        String kms_hrs = mItemList.get(position).getKmsRunning();
        SpannableString sp8 = new SpannableString(mActivity.getString(R.string.kms_hrs) + kms_hrs);
        sp8.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.kms_hrs).length(),
                mActivity.getString(R.string.kms_hrs).length() + kms_hrs.length(), 0);
        holder.mKmsHrs.setText(sp8);

        if (mItemList.get(position).getSingleImage().equals("") || mItemList.get(position).getSingleImage().equals(null) ||
                mItemList.get(position).getSingleImage().equals("null")) {
            holder.mCardImage.setBackgroundResource(R.mipmap.ic_launcher);
        } else {
            //mItemList.get(position).getImage() = mItemList.get(position).getImage().replaceAll(" ", "%20");
            String dppath = "http://autokatta.com/mobile/uploads/" + mItemList.get(position).getSingleImage();
            Glide.with(mActivity)
                    .load(dppath)
                    //.bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    //.placeholder(R.drawable.logo) //To show image before loading an original image.
                    //.error(R.drawable.blocked) //To show error image if problem in loading.
                    .into(holder.mCardImage);
        }

        if (mItemList.get(position).getVehiclelikestatus().equalsIgnoreCase("yes")) {
            holder.mLike.setVisibility(View.GONE);
            holder.mUnlike.setVisibility(View.VISIBLE);
        }
        if (mItemList.get(position).getVehiclelikestatus().equalsIgnoreCase("no")) {
            holder.mLike.setVisibility(View.VISIBLE);
            holder.mUnlike.setVisibility(View.GONE);
        }

        if (mItemList.get(position).getContact().equals(myContact)) {
            holder.mLike.setVisibility(View.GONE);
            holder.mUnlike.setVisibility(View.GONE);
            holder.mCall.setVisibility(View.GONE);
        }
        //Share In App
        holder.mShareAutokatta.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(mActivity)
                        .setTitle("Contact")
                        .setMessage("Do you want to share your contact no as vehicle contact?")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                allDetails = mItemList.get(position).getTitle() + "=" +
                                        mItemList.get(position).getCategory() + "=" +
                                        "-" + "=" +
                                        mItemList.get(position).getPrice() + "=" +
                                        "" + "=" +
                                        mItemList.get(position).getModel() + "=" +
                                        mItemList.get(position).getYearOfManufacture() + "=" +
                                        mItemList.get(position).getKmsRunning() + "=" +
                                        mItemList.get(position).getRTOCity() + "=" +
                                        mItemList.get(position).getLocationCity() + "=" +
                                        mItemList.get(position).getRegistrationNumber() + "=" +
                                        mItemList.get(position).getSingleImage() + "=" +
                                        myContact + "=" + "0";

                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allDetails).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_vehicle_id", mItemList.get(position).getVehicleId()).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_keyword", "vehicle").apply();

                                Intent i = new Intent(mActivity, ShareWithinAppActivity.class);
                                mActivity.startActivity(i);
                                mActivity.finish();

                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                allDetails = mItemList.get(position).getTitle() + "=" +
                                        mItemList.get(position).getCategory() + "=" +
                                        "-" + "=" +
                                        mItemList.get(position).getPrice() + "=" +
                                        "" + "=" +
                                        mItemList.get(position).getModel() + "=" +
                                        mItemList.get(position).getYearOfManufacture() + "=" +
                                        mItemList.get(position).getKmsRunning() + "=" +
                                        mItemList.get(position).getRTOCity() + "=" +
                                        mItemList.get(position).getLocationCity() + "=" +
                                        mItemList.get(position).getRegistrationNumber() + "=" +
                                        mItemList.get(position).getSingleImage() + "=" +
                                        mItemList.get(position).getContact();

                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allDetails).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_vehicle_id", mItemList.get(position).getVehicleId()).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_keyword", "vehicle").apply();

                                Intent i = new Intent(mActivity, ShareWithinAppActivity.class);
                                mActivity.startActivity(i);
                                mActivity.finish();

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
        holder.mShareOther.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(mActivity)
                        .setTitle("Contact")
                        .setMessage("Do you want to share your contact no as vehicle contact?")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //ChoiceContact = obj.vehicleContact;
                                //Rcontact=holder.contact.getText().toString();
                                System.out.println("Choice contact before share applying yes........" + myContact);
                                String imageFilePath;
                                Intent intent = new Intent(Intent.ACTION_SEND);


                                if (mItemList.get(position).getSingleImage().equalsIgnoreCase("") || mItemList.get(position).getSingleImage().equalsIgnoreCase(null)) {
                                    imgUrl = "http://autokatta.com/mobile/uploads/" + "abc.jpg";
                                } else {
                                    imgUrl = "http://autokatta.com/mobile/uploads/" + mItemList.get(position).getSingleImage();
                                }
                                Log.e("TAG", "img : " + imgUrl);

                                DownloadManager.Request request = new DownloadManager.Request(
                                        Uri.parse(imgUrl));
                                request.allowScanningByMediaScanner();
                                String filename = URLUtil.guessFileName(imgUrl, null, MimeTypeMap.getFileExtensionFromUrl(imgUrl));
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                                Log.e("ShareImagePath :", filename);
                                Log.e("TAG", "img : " + imgUrl);

                                DownloadManager manager = (DownloadManager) mActivity.getApplication()
                                        .getSystemService(Context.DOWNLOAD_SERVICE);

                                Log.e("TAG", "img URL: " + imgUrl);

                                manager.enqueue(request);

                                imageFilePath = "/storage/emulated/0/Download/" + filename;
                                System.out.println("ImageFilePath:" + imageFilePath);

                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my vehicle on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/vehicle/main/" + mItemList.get(position).getVehicleId() + "/" + myContact);
                                intent.setType("image/jpeg");
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                                mActivity.startActivity(Intent.createChooser(intent, "Autokatta"));
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //ChoiceContact = Contact;
                                System.out.println("Choice contact before share applying yes........" + mItemList.get(position).getContact());
                                System.out.println("Choice contact before share applying yes........" + myContact);
                                String imageFilePath;
                                Intent intent = new Intent(Intent.ACTION_SEND);


                                if (mItemList.get(position).getSingleImage().equalsIgnoreCase("") || mItemList.get(position).getSingleImage().equalsIgnoreCase(null)) {
                                    imgUrl = "http://autokatta.com/mobile/uploads/" + "abc.jpg";
                                } else {
                                    imgUrl = "http://autokatta.com/mobile/uploads/" + mItemList.get(position).getSingleImage();
                                }
                                Log.e("TAG", "img : " + imgUrl);

                                DownloadManager.Request request = new DownloadManager.Request(
                                        Uri.parse(imgUrl));
                                request.allowScanningByMediaScanner();
                                String filename = URLUtil.guessFileName(imgUrl, null, MimeTypeMap.getFileExtensionFromUrl(imgUrl));
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                                Log.e("ShareImagePath :", filename);
                                Log.e("TAG", "img : " + imgUrl);

                                DownloadManager manager = (DownloadManager) mActivity.getApplication()
                                        .getSystemService(Context.DOWNLOAD_SERVICE);

                                Log.e("TAG", "img URL: " + imgUrl);

                                manager.enqueue(request);

                                imageFilePath = "/storage/emulated/0/Download/" + filename;
                                System.out.println("ImageFilePath:" + imageFilePath);

                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my vehicle on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/vehicle/main/" + mItemList.get(position).getVehicleId() + "/" + mItemList.get(position).getContact());
                                intent.setType("image/jpeg");
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                                mActivity.startActivity(Intent.createChooser(intent, "Autokatta"));
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mBundle = new Bundle();
                mBundle.putString("vehicle_id", mItemList.get(position).getVehicleId());

                Intent mVehicleDetails = new Intent(mActivity, VehicleDetails.class);
                mVehicleDetails.putExtras(mBundle);
                mActivity.startActivity(mVehicleDetails);
            }
        });
        holder.mCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                call(mItemList.get(position).getContact());
            }
        });

        holder.mLike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mLike.setVisibility(View.GONE);
                holder.mUnlike.setVisibility(View.VISIBLE);

                mItemList.get(position).setVehiclelikestatus("yes");

                sendLike(mItemList.get(position).getContact(), mItemList.get(position).getVehicleId());
            }
        });

        holder.mUnlike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mLike.setVisibility(View.VISIBLE);
                holder.mUnlike.setVisibility(View.GONE);

                mItemList.get(position).setVehiclelikestatus("no");

                sendUnlike(mItemList.get(position).getContact(), mItemList.get(position).getVehicleId());
            }
        });

    }

    /*
    Like
     */
    private void sendLike(String Rcontact, String vehicleId) {
        mApiCall.vehicleLike(Rcontact, myContact, "4", vehicleId);
    }

    /*
    Unlike...
     */
    private void sendUnlike(String Rcontact, String vehicleId) {
        mApiCall.vehicleUnLike(Rcontact, myContact, "4", vehicleId);
    }


    //Calling Functionality
    private void call(String contact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact));
        try {
            mActivity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Car Details Fragment\n");
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
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
            Log.i("Check Class-", "GroupVehiclRefresh Adapter");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("success_like")) {
                CustomToast.customToast(mActivity, " Liked Successfully");

            } else if (str.equals("success_unlike")) {
                CustomToast.customToast(mActivity, " UnLiked Successfully");

            }
        }
    }
}
