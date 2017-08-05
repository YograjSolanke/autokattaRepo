package autokatta.com.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.SellerResponse;
import autokatta.com.view.OtherProfile;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 14/4/17.
 */

public class SavedSearchSellerListAdapter extends RecyclerView.Adapter<SavedSearchSellerListAdapter.SellerHolder> implements RequestNotifier {

    List<SellerResponse.Success.MatchedResult> matchedResultList = new ArrayList<>();
    Activity activity;
    ApiCall apiCall;
    String category, brand, model, rto_city, manufacture_year, myContact;
    String recieverContact, SenderContact, SellerId, VehiId, calldate;
    int search_id;


    public SavedSearchSellerListAdapter(Activity activity, List<SellerResponse.Success.MatchedResult> matchedResults, int search_id,
                                        String category, String brand, String model, String manufacture_year, String rto_city) {
        this.activity = activity;
        this.matchedResultList = matchedResults;
        this.search_id = search_id;
        this.category = category;
        this.brand = brand;
        this.model = model;
        this.manufacture_year = manufacture_year;
        this.rto_city = rto_city;
        apiCall = new ApiCall(activity, this);
        myContact = activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");
    }

    @Override
    public SavedSearchSellerListAdapter.SellerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_list_adapter, parent, false);
        SellerHolder holder = new SellerHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SavedSearchSellerListAdapter.SellerHolder holder, final int position) {

        final SellerResponse.Success.MatchedResult object = matchedResultList.get(position);

        holder.buyerusername.setText(object.getUsername());

        holder.Title.setText(object.getTitle());

        //to set buyer last call date
        try {

            DateFormat date = new SimpleDateFormat(" MMM dd ");
            DateFormat time = new SimpleDateFormat(" hh:mm a");

            holder.lastCall.setText("Last call on:" + date.format(object.getLastCallDateNew()) + time.format(object.getLastCallDateNew()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        //to set vehicle uploaded date
        try {

            DateFormat date = new SimpleDateFormat(" MMM dd ");
            DateFormat time = new SimpleDateFormat(" hh:mm a");

            holder.UploadedDate.setText("Uploadede On :" + date.format(object.getDate()) + time.format(object.getDate()));

        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.checkBox1.setText(category);
        holder.checkBox2.setText(brand);
        holder.checkBox3.setText(model);
        holder.checkBox4.setText(manufacture_year);
        holder.checkBox5.setText(rto_city);


        holder.checkBox6.setText(object.getCategory());
        holder.checkBox7.setText(object.getManufacturer());
        holder.checkBox8.setText(object.getModel());
        holder.checkBox9.setText(object.getYearOfManufacture());
        holder.checkBox10.setText(object.getRtoCity());


        try {

            if (holder.checkBox1.getText().toString().equalsIgnoreCase(holder.checkBox6.getText().toString())) {
                holder.checkBox1.setChecked(true);
                holder.checkBox6.setChecked(true);
            }

            if (holder.checkBox2.getText().toString().equalsIgnoreCase(holder.checkBox7.getText().toString())) {
                holder.checkBox2.setChecked(true);
                holder.checkBox7.setChecked(true);
            }

            if (holder.checkBox3.getText().toString().equalsIgnoreCase(holder.checkBox8.getText().toString())) {
                holder.checkBox3.setChecked(true);
                holder.checkBox8.setChecked(true);
            }

            if (holder.checkBox4.getText().toString().equalsIgnoreCase(holder.checkBox9.getText().toString())) {
                holder.checkBox4.setChecked(true);
                holder.checkBox9.setChecked(true);
            }

            if (holder.checkBox5.getText().toString().equalsIgnoreCase(holder.checkBox10.getText().toString())) {
                holder.checkBox5.setChecked(true);
                holder.checkBox10.setChecked(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.checkBox1.setEnabled(false);
        holder.checkBox2.setEnabled(false);
        holder.checkBox3.setEnabled(false);
        holder.checkBox4.setEnabled(false);
        holder.checkBox5.setEnabled(false);

        holder.checkBox6.setEnabled(false);
        holder.checkBox7.setEnabled(false);
        holder.checkBox8.setEnabled(false);
        holder.checkBox9.setEnabled(false);
        holder.checkBox10.setEnabled(false);


        holder.Compare.setVisibility(View.GONE);
        holder.Vehic_cnt.setVisibility(View.GONE);
        holder.chckbox.setVisibility(View.GONE);

        final String imagenames = object.getImage();
        ArrayList<String> iname = new ArrayList<String>();

        try {
            String[] imagenamecame = imagenames.split(",");

            if (imagenamecame.length != 0) {
                for (int z = 0; z < imagenamecame.length; z++) {
                    iname.add(imagenamecame[z]);
                }

                System.out.println("lis=" + iname);

                ImageView[] imageView = new ImageView[iname.size()];

                for (int l = 0; l < imageView.length; l++) {
                    imageView[l] = new ImageView(activity);

                    /****************
                     Glide code for image uploading

                     *****************/
                    Glide.with(activity)
                            .load(activity.getString(R.string.base_image_url) + iname.get(l).replaceAll(" ", "%20"))
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(imageView[l]);

                    holder.vehi_images.addView(imageView[l]);
                }
            } else {
                ImageView[] imageView = new ImageView[2];
                for (int l = 0; l < imageView.length; l++) {
                    imageView[l] = new ImageView(activity);
                    imageView[l].setBackgroundResource(R.drawable.vehiimg);
                    holder.vehi_images.addView(imageView[l]);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int mFlippingsell = 0;

        if (mFlippingsell == 0) {
            /** Start Flipping */
            holder.vehi_images.startFlipping();
            mFlippingsell = 1;
        } else {
            /** Stop Flipping */
            holder.vehi_images.stopFlipping();
            mFlippingsell = 0;
        }

        holder.buyerusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recieverContact = object.getContactNo();

                Bundle bundle = new Bundle();
                bundle.putString("contactOtherProfile", recieverContact);
                Intent intent = new Intent(activity, OtherProfile.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });

//        holder.buyer_lead_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recieverContact=contact_no.get(position);
//
//                Bundle bundle = new Bundle();
//                bundle.putString("contact", recieverContact);
//                bundle.putString("action", "other");
//
//                SimpleProfile fragment = new SimpleProfile();
//                fragment.setArguments(bundle);
//                FragmentManager fragmentManager = ctx.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.containerView, fragment);
//                // fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });

        holder.callseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recieverContact = object.getContactNo();

                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                calldate = df.format(c.getTime());
                // formattedDate have current date/time
                System.out.println("current time=============================" + calldate);

                call(recieverContact);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(recieverContact));

                apiCall.sendLastCallDate(myContact, recieverContact, calldate, "1");
            }
        });


        if (object.getFavstatus().equalsIgnoreCase("yes")) {
            holder.favSeller.setImageResource(R.drawable.fav2);
            holder.favSeller.setEnabled(false);
        } else {
            holder.favSeller.setImageResource(R.drawable.fav1);
        }

        holder.favSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VehiId = object.getVehicleId();
                SellerId = search_id + "," + VehiId;

                apiCall.addToFavorite(myContact, "", 0, SellerId, 0);
                holder.favSeller.setImageResource(R.drawable.fav2);

                object.setFavstatus("yes");
            }


        });


    }

    @Override
    public int getItemCount() {
        return matchedResultList.size();
    }

    static class SellerHolder extends RecyclerView.ViewHolder {

        ImageView callseller, favSeller;
        ViewFlipper vehi_images;
        TextView buyerusername, buyerlocation, lastCall, Compare, Vehic_cnt, UploadedDate, Title;
        CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10, chckbox;

        public SellerHolder(View itemView) {
            super(itemView);

            buyerusername = (TextView) itemView.findViewById(R.id.username);
            Compare = (TextView) itemView.findViewById(R.id.compare);
            Vehic_cnt = (TextView) itemView.findViewById(R.id.vehiclecount);
            UploadedDate = (TextView) itemView.findViewById(R.id.addon);
            Title = (TextView) itemView.findViewById(R.id.title);


            vehi_images = (ViewFlipper) itemView.findViewById(R.id.sellvehicalimgflicker);
            callseller = (ImageView) itemView.findViewById(R.id.sellcallimg);

            checkBox1 = (CheckBox) itemView.findViewById(R.id.sellcheckBox1);
            checkBox2 = (CheckBox) itemView.findViewById(R.id.sellcheckBox2);
            checkBox3 = (CheckBox) itemView.findViewById(R.id.sellcheckBox3);
            checkBox4 = (CheckBox) itemView.findViewById(R.id.sellcheckBox4);
            checkBox5 = (CheckBox) itemView.findViewById(R.id.sellcheckBox5);
            checkBox6 = (CheckBox) itemView.findViewById(R.id.sellcheckBox6);
            checkBox7 = (CheckBox) itemView.findViewById(R.id.sellcheckBox7);
            checkBox8 = (CheckBox) itemView.findViewById(R.id.sellcheckBox8);
            checkBox9 = (CheckBox) itemView.findViewById(R.id.sellcheckBox9);
            checkBox10 = (CheckBox) itemView.findViewById(R.id.sellcheckBox10);

            chckbox = (CheckBox) itemView.findViewById(R.id.checkauc);

            favSeller = (ImageView) itemView.findViewById(R.id.sellfevimg);
            lastCall = (TextView) itemView.findViewById(R.id.lastcall);


        }
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(activity, activity.getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "VehicleBuyerListAdapter");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

        if (str != null) {

            if (str.equals("success")) {
                CustomToast.customToast(activity, "Call date Submitted");
            } else if (str.equals("success_favourite")) {
                CustomToast.customToast(activity, "favourite data submitted");

            }


        } else {
            CustomToast.customToast(activity, activity.getString(R.string.no_response));
        }


    }

    private void call(String recieverContact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + recieverContact));

        System.out.println("calling started");
        try {
            activity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Service View Fragment\n");
        }
    }
}
