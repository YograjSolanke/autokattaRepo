package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import autokatta.com.response.GetGroupVehiclesResponse;
import autokatta.com.view.ChatActivity;
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
    private MyViewHolder view;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mRegistrationNo, mTitle, mPrice, mModel, mBrand, mUpdatedBy, mLocation, mRtoCity, mYearOfMfg, mKmsHrs;
        ImageView mLike, mCall, mUnlike;
        ImageView mCardImage;
        LinearLayout mRlike, mRunlike, mShareOther,mOfferLayout;
        Button mChat;


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
            mChat = (Button) itemView.findViewById(R.id.chat_c);
            mOfferLayout = (LinearLayout) itemView.findViewById(R.id.offer);

            mShareOther = (LinearLayout) itemView.findViewById(R.id.share);
            mLike = (ImageView) itemView.findViewById(R.id.like);
            mUnlike = (ImageView) itemView.findViewById(R.id.unlike);
            mCall = (ImageView) itemView.findViewById(R.id.call);
            mCardImage = (ImageView) itemView.findViewById(R.id.card_image);
            mRlike = (LinearLayout) itemView.findViewById(R.id.rellike);
            mRunlike = (LinearLayout) itemView.findViewById(R.id.relunlike);

        }
    }

    public GroupVehicleRefreshAdapter(Activity mActivity1, List<GetGroupVehiclesResponse.Success> mItemList) {
        this.mActivity = mActivity1;
        this.mItemList = mItemList;
        mApiCall = new ApiCall(mActivity, this);
    }

    @Override
    public GroupVehicleRefreshAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_card_group_vehicle, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final GroupVehicleRefreshAdapter.MyViewHolder holder, final int position) {
        myContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "");
        getChatEnquiryStatus(myContact,mItemList.get(position).getContact(),mItemList.get(position).getVehicleId());
        if (!myContact.equalsIgnoreCase(mItemList.get(position).getContact()))
        {
            holder.mChat.setVisibility(View.VISIBLE);
            holder.mOfferLayout.setVisibility(View.VISIBLE);
        }
        view = holder;
        /*String register = mItemList.get(position).getRegistrationNumber();
        SpannableString sp = new SpannableString(mActivity.getString(R.string.no_register) + register);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.no_register).length(),
                mActivity.getString(R.string.no_register).length() + register.length(), 0);*/
        holder.mRegistrationNo.setText(mItemList.get(position).getRegistrationNumber());

        holder.mTitle.setText(mItemList.get(position).getTitle());

        /*String price = mItemList.get(position).getPrice();
        SpannableString sp1 = new SpannableString(mActivity.getString(R.string.price) + price);
        sp1.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.price).length(),
                mActivity.getString(R.string.price).length() + price.length(), 0);*/
        holder.mPrice.setText(mItemList.get(position).getPrice());

        /*String model = mItemList.get(position).getModel();
        SpannableString sp2 = new SpannableString(mActivity.getString(R.string.model) + model);
        sp2.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.model).length(),
                mActivity.getString(R.string.model).length() + model.length(), 0);*/
        holder.mModel.setText(mItemList.get(position).getModel());

        /*String brand = mItemList.get(position).getManufacturer();
        SpannableString sp3 = new SpannableString(mActivity.getString(R.string.brand_no) + brand);
        sp3.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.brand_no).length(),
                mActivity.getString(R.string.brand_no).length() + brand.length(), 0);*/
        holder.mBrand.setText(mItemList.get(position).getManufacturer());

        /*String uploaded_by = mItemList.get(position).getUsername();
        SpannableString sp4 = new SpannableString(mActivity.getString(R.string.updated_by) + uploaded_by);
        sp4.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.updated_by).length(),
                mActivity.getString(R.string.updated_by).length() + uploaded_by.length(), 0);*/
        holder.mUpdatedBy.setText(mItemList.get(position).getUsername());

        /*String location = mItemList.get(position).getLocationCity();
        SpannableString sp5 = new SpannableString(mActivity.getString(R.string.location) + location);
        sp5.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.location).length(),
                mActivity.getString(R.string.location).length() + location.length(), 0);*/
        holder.mLocation.setText(mItemList.get(position).getLocationCity());

        /*String rto_city = mItemList.get(position).getRTOCity();
        SpannableString sp6 = new SpannableString(mActivity.getString(R.string.rto_city) + rto_city);
        sp6.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.rto_city).length(),
                mActivity.getString(R.string.rto_city).length() + rto_city.length(), 0);*/
        holder.mRtoCity.setText(mItemList.get(position).getRTOCity());

       /* String year_of_mfg = mItemList.get(position).getYearOfManufacture();
        SpannableString sp7 = new SpannableString(mActivity.getString(R.string.year_of_mfg) + year_of_mfg);
        sp7.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.year_of_mfg).length(),
                mActivity.getString(R.string.year_of_mfg).length() + year_of_mfg.length(), 0);*/
        holder.mYearOfMfg.setText(mItemList.get(position).getYearOfManufacture());

        /*String kms_hrs = mItemList.get(position).getKmsRunning();
        SpannableString sp8 = new SpannableString(mActivity.getString(R.string.kms_hrs) + kms_hrs);
        sp8.setSpan(new ForegroundColorSpan(Color.parseColor("#0078c0")), mActivity.getString(R.string.kms_hrs).length(),
                mActivity.getString(R.string.kms_hrs).length() + kms_hrs.length(), 0);*/
        holder.mKmsHrs.setText(mItemList.get(position).getKmsRunning());

        if (mItemList.get(position).getSingleImage().equals("") || mItemList.get(position).getSingleImage().equals(null) ||
                mItemList.get(position).getSingleImage().equals("null")) {
            holder.mCardImage.setBackgroundResource(R.drawable.vehiimg);
        } else {
            //mItemList.get(position).getImage() = mItemList.get(position).getImage().replaceAll(" ", "%20");
            String dppath = mActivity.getString(R.string.base_image_url) + mItemList.get(position).getSingleImage();
            Glide.with(mActivity)
                    .load(dppath)
                    //.bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    //.placeholder(R.drawable.logo) //To show image before loading an original image.
                    //.error(R.drawable.blocked) //To show error image if problem in loading.
                    .into(holder.mCardImage);
        }

        if (mItemList.get(position).getVehiclelikestatus().equalsIgnoreCase("yes")) {
            holder.mRlike.setVisibility(View.GONE);
            holder.mRunlike.setVisibility(View.VISIBLE);
        }
        if (mItemList.get(position).getVehiclelikestatus().equalsIgnoreCase("no")) {
            holder.mRlike.setVisibility(View.VISIBLE);
            holder.mRunlike.setVisibility(View.GONE);
        }

        if (mItemList.get(position).getContact().equals(myContact)) {
            //Snackbar.make(holder.mCardView, "You Can't Like Your Profile: ", Snackbar.LENGTH_LONG).show();
           /* holder.mLike.setVisibility(View.VISIBLE);
            holder.mLike.setEnabled(false);
            holder.mUnlike.setVisibility(View.VISIBLE);
            holder.mUnlike.setEnabled(false);*/
            holder.mCall.setVisibility(View.GONE);
        }
        //Share In App

        holder.mShareOther.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu mPopupMenu = new PopupMenu(mActivity, holder.mShareOther);
                mPopupMenu.getMenuInflater().inflate(R.menu.more_menu, mPopupMenu.getMenu());
                mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.autokatta:
                                new AlertDialog.Builder(mActivity)
                                        .setTitle("Contact")
                                        .setMessage("Do you want to share your contact no as vehicle contact?")

                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                allDetails = mItemList.get(holder.getAdapterPosition()).getTitle() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getPrice() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getManufacturer() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getModel() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getYearOfManufacture() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getKmsRunning() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getRTOCity() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getLocationCity() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getRegistrationNumber() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getSingleImage() + "=" +
                                                        "" + "=" +
                                                        myContact + "=" + "0";

                                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                                        putString("Share_sharedata", allDetails).apply();
                                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                                        putInt("Share_vehicle_id", mItemList.get(holder.getAdapterPosition()).getVehicleId()).apply();
                                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                                        putString("Share_keyword", "vehicle").apply();

                                                Intent i = new Intent(mActivity, ShareWithinAppActivity.class);
                                                mActivity.startActivity(i);
                                                mActivity.finish();
                                            }
                                        })

                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                allDetails = mItemList.get(holder.getAdapterPosition()).getTitle() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getPrice() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getManufacturer() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getModel() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getYearOfManufacture() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getKmsRunning() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getRTOCity() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getLocationCity() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getRegistrationNumber() + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getSingleImage() + "=" +
                                                        "" + "=" +
                                                        mItemList.get(holder.getAdapterPosition()).getContact() +
                                                        "=" + "0";

                                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                                        putString("Share_sharedata", allDetails).apply();
                                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                                        putInt("Share_vehicle_id", mItemList.get(holder.getAdapterPosition()).getVehicleId()).apply();
                                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                                        putString("Share_keyword", "vehicle").apply();

                                                Intent i = new Intent(mActivity, ShareWithinAppActivity.class);
                                                mActivity.startActivity(i);
                                                mActivity.finish();

                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                                break;
                            case R.id.other:
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


                                                if (mItemList.get(holder.getAdapterPosition()).getSingleImage().equalsIgnoreCase("") || mItemList.get(holder.getAdapterPosition()).getSingleImage().equalsIgnoreCase(null)) {
                                                    imgUrl = mActivity.getString(R.string.base_image_url) + "logo48x48.png";
                                                } else {
                                                    imgUrl = mActivity.getString(R.string.base_image_url) + mItemList.get(holder.getAdapterPosition()).getSingleImage();
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


                                                String allGroupVehicleDetails =
                                                        "Vehicle Title : " + mItemList.get(holder.getAdapterPosition()).getTitle() + "\n" +
                                                                "Vehicle Price : " + mItemList.get(holder.getAdapterPosition()).getPrice() + "\n" +
                                                                "Vehicle Brand : " + mItemList.get(holder.getAdapterPosition()).getManufacturer() + "\n" +
                                                                "Vehicle Model : " + mItemList.get(holder.getAdapterPosition()).getModel() + "\n" +
                                                                "Vehicle Manufacturing Year : " + mItemList.get(holder.getAdapterPosition()).getYearOfManufacture() + "\n" +
                                                                "Vehicle Running (Km/Hr) : " + mItemList.get(holder.getAdapterPosition()).getKmsRunning() + "\n" +
                                                                "RTO City : " + mItemList.get(holder.getAdapterPosition()).getRTOCity() + "\n" +
                                                                "Vehicle Location City : " + mItemList.get(holder.getAdapterPosition()).getLocationCity() + "\n" +
                                                                "Vehicle Registration Number : " + mItemList.get(holder.getAdapterPosition()).getRegistrationNumber() + "\n" +
                                                                "Contact : " + mItemList.get(holder.getAdapterPosition()).getContact();

                                                intent.setType("text/plain");
                                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my vehicle on Autokatta. Stay connected for Product and Service updates and enquiries"
                                                        + "\n" + "http://autokatta.com/vehicle/main/" + mItemList.get(holder.getAdapterPosition()).getVehicleId() + "/" + myContact
                                                        + "\n" + "\n" + allGroupVehicleDetails);
                                                intent.setType("image/jpeg");
                                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                                mActivity.startActivity(Intent.createChooser(intent, "Autokatta"));

                                            }
                                        })

                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                String imageFilePath;
                                                Intent intent = new Intent(Intent.ACTION_SEND);

                                                if (mItemList.get(holder.getAdapterPosition()).getSingleImage().equalsIgnoreCase("") || mItemList.get(holder.getAdapterPosition()).getSingleImage().equalsIgnoreCase(null)) {
                                                    imgUrl = mActivity.getString(R.string.base_image_url) + "logo48x48.png";
                                                } else {
                                                    imgUrl = mActivity.getString(R.string.base_image_url) + mItemList.get(holder.getAdapterPosition()).getSingleImage();
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

                                                String allGroupVehicleDetails =
                                                        "Vehicle Title : " + mItemList.get(holder.getAdapterPosition()).getTitle() + "\n" +
                                                                "Vehicle Price : " + mItemList.get(holder.getAdapterPosition()).getPrice() + "\n" +
                                                                "Vehicle Brand : " + mItemList.get(holder.getAdapterPosition()).getManufacturer() + "\n" +
                                                                "Vehicle Model : " + mItemList.get(holder.getAdapterPosition()).getModel() + "\n" +
                                                                "Vehicle Manufacturing Year : " + mItemList.get(holder.getAdapterPosition()).getYearOfManufacture() + "\n" +
                                                                "Vehicle Running (Km/Hr) : " + mItemList.get(holder.getAdapterPosition()).getKmsRunning() + "\n" +
                                                                "RTO City : " + mItemList.get(holder.getAdapterPosition()).getRTOCity() + "\n" +
                                                                "Vehicle Location City : " + mItemList.get(holder.getAdapterPosition()).getLocationCity() + "\n" +
                                                                "Vehicle Registration Number : " + mItemList.get(holder.getAdapterPosition()).getRegistrationNumber();

                                                intent.setType("text/plain");
                                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my vehicle on Autokatta. Stay connected for Product and Service updates and enquiries"
                                                        + "\n" + "http://autokatta.com/vehicle/main/" + mItemList.get(holder.getAdapterPosition()).getVehicleId() + "/" + mItemList.get(holder.getAdapterPosition()).getContact()
                                                        + "\n" + "\n" + allGroupVehicleDetails);
                                                intent.setType("image/jpeg");
                                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                                mActivity.startActivity(Intent.createChooser(intent, "Autokatta"));

                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                                break;
                        }
                        return false;
                    }
                });
                mPopupMenu.show(); //showing popup menu
            }
        });

        holder.mChat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.mChat.getText().toString().equalsIgnoreCase("send Offer")) {

                    final Dialog openDialog = new Dialog(mActivity);
                    openDialog.setContentView(R.layout.give_offer);
                    openDialog.setTitle("Fill Form For Offer");
                    final EditText offerprice = (EditText) openDialog.findViewById(R.id.txtofferprice);
                    final EditText paymentmode = (EditText) openDialog.findViewById(R.id.paymentmode);
                    final EditText description = (EditText) openDialog.findViewById(R.id.description);

                    Button sendQuotation = (Button) openDialog.findViewById(R.id.btnSend);

                    sendQuotation.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            String strprice = offerprice.getText().toString();
                            String strpaymentmode = paymentmode.getText().toString();
                            String strdescription = description.getText().toString();

                            if (strprice.equalsIgnoreCase("")) {
                                CustomToast.customToast(mActivity, "please enter give offer price");
                            } else if (strpaymentmode.equalsIgnoreCase("")) {
                                CustomToast.customToast(mActivity, "please enter Payment mode");
                            } else if (strdescription.equalsIgnoreCase("")) {
                                CustomToast.customToast(mActivity, "please enter Description");
                            } else {
                               // ApiCall mpApicall = new ApiCall(this, mActivity);
                                mApiCall.sendChatMessage(myContact, mItemList.get(position).getContact(), "Offer Price-" + strprice + "\n" +
                                        "Payment Mode-" + strpaymentmode + "\n" +
                                        "Description-" + strdescription, "", 0, 0, mItemList.get(position).getVehicleId());
                                openDialog.dismiss();
                            }
                        }
                    });
                    openDialog.show();

                } else {
                    Bundle b = new Bundle();
                    b.putString("sender", mItemList.get(position).getContact());
                    b.putString("sendername", mItemList.get(position).getUsername());
                    b.putInt("product_id", 0);
                    b.putInt("service_id", 0);
                    b.putInt("vehicle_id",  mItemList.get(position).getVehicleId());
                    Intent intent = new Intent(mActivity, ChatActivity.class);
                    intent.putExtras(b);
                    mActivity.startActivity(intent);
                }
            }
        });
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mBundle = new Bundle();
                mBundle.putInt("vehicle_id", mItemList.get(holder.getAdapterPosition()).getVehicleId());
                ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent mVehicleDetails = new Intent(mActivity, VehicleDetails.class);
                mVehicleDetails.putExtras(mBundle);
                mActivity.startActivity(mVehicleDetails, options.toBundle());
            }
        });

        holder.mCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                call(mItemList.get(holder.getAdapterPosition()).getContact());
            }
        });

        holder.mRlike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemList.get(holder.getAdapterPosition()).getContact().equals(myContact)) {
                    Snackbar.make(holder.mCardView, "You Can't Like Your Own Vehicle ", Snackbar.LENGTH_LONG).show();
                } else {
                    holder.mRlike.setVisibility(View.GONE);
                    holder.mRunlike.setVisibility(View.VISIBLE);
                    mItemList.get(holder.getAdapterPosition()).setVehiclelikestatus("yes");
                    sendLike(mItemList.get(holder.getAdapterPosition()).getContact(), mItemList.get(holder.getAdapterPosition()).getVehicleId());
                }
            }
        });

        holder.mRunlike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mRlike.setVisibility(View.VISIBLE);
                holder.mRunlike.setVisibility(View.GONE);
                mItemList.get(holder.getAdapterPosition()).setVehiclelikestatus("no");
                sendUnlike(mItemList.get(holder.getAdapterPosition()).getContact(), mItemList.get(holder.getAdapterPosition()).getVehicleId());
            }
        });

    }

    /*
    Like
     */
    private void sendLike(String Rcontact, int vehicleId) {
        mApiCall.Like(myContact, Rcontact, "4", 0, 0, vehicleId, 0, 0, 0, 0);
    }

    /*
    Unlike...
     */
    private void sendUnlike(String Rcontact, int vehicleId) {
        mApiCall.UnLike(myContact, Rcontact, "4", 0, 0, vehicleId, 0, 0, 0, 0);
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
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "GroupVehiclRefresh Adapter");
            error.printStackTrace();
        }
    }
    private void getChatEnquiryStatus(String prefcontact, String contact, int mVehicle_id) {
        ApiCall mApicall = new ApiCall(mActivity, this);
        mApicall.getChatEnquiryStatus(prefcontact, contact, 0, 0, mVehicle_id);
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("success_like")) {
                CustomToast.customToast(mActivity, "Liked");
            } else if (str.equals("success_unlike")) {
                Log.e("Unlike", "->");
            }else if (str.equals("success_message_saved")) {
                CustomToast.customToast(mActivity, "Offer Sent");
            } else if (str.contains("yes")) {
                view.mChat.setText("Chat");
            } else if (str.contains("no")) {
                view.mChat.setText("Send Offer");
            }
        }
    }
}
