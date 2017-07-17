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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BuyerResponse;
import autokatta.com.view.OtherProfile;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 12/4/17.
 */

public class VehicleBuyerListAdapter extends RecyclerView.Adapter<VehicleBuyerListAdapter.BuyerHolder> implements RequestNotifier {
    Activity activity;
    List<BuyerResponse.Success.Found> foundList;
    String vcategory, brand, vmodel, manufacture_year, rto_city;
    String vehicle_id, recieverContact, srchid, BuyerId;
    String calldate;
    String myContact;
    ApiCall apicall;

    public VehicleBuyerListAdapter(Activity activity, List<BuyerResponse.Success.Found> foundList, String vehicle_id,
                                   String vcategory, String brand, String vmodel, String manufacture_year, String rto_city) {
        this.activity = activity;
        this.foundList = foundList;
        this.vcategory = vcategory;
        this.brand = brand;
        this.vmodel = vmodel;
        this.vehicle_id = vehicle_id;
        this.manufacture_year = manufacture_year;
        this.rto_city = rto_city;
        apicall = new ApiCall(activity, this);
        myContact = activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");


    }

    @Override
    public VehicleBuyerListAdapter.BuyerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyer_list_adapter, parent, false);
        BuyerHolder holder = new BuyerHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final VehicleBuyerListAdapter.BuyerHolder holder, final int position) {

        final BuyerResponse.Success.Found object = foundList.get(position);

        holder.buyerusername.setText(object.getReceivername());
        holder.buyerlocation.setText(object.getLocationCity());
        //to set buyer last call date
        try {

            DateFormat date = new SimpleDateFormat(" MMM dd ");
            DateFormat time = new SimpleDateFormat(" hh:mm a");


            holder.namecity.setText("Last call on:" + date.format(object.getLastCallDateNew()) + time.format(object.getLastCallDateNew()));

        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.checkBox1.setText(vcategory);
        holder.checkBox2.setText(brand);
        holder.checkBox3.setText(vmodel);
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


        if (object.getReceiverPic().equals("")) {
            holder.buyer_lead_image.setBackgroundResource(R.drawable.logo);
        } else {
            try {

                Glide.with(activity)
                        .load("http://autokatta.com/mobile/profile_profile_pics/" + object.getReceiverPic())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.logo)
                        .into(holder.buyer_lead_image);

            } catch (Exception e) {
                Toast.makeText(activity, "Error image uploading", Toast.LENGTH_LONG).show();
            }
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
//
        holder.buyer_lead_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recieverContact = object.getContactNo();
                Bundle bundle = new Bundle();
                bundle.putString("contactOtherProfile", recieverContact);
                Intent intent = new Intent(activity, OtherProfile.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
//
        holder.callbuyer.setOnClickListener(new View.OnClickListener() {
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

                apicall.sendLastCallDate(myContact, recieverContact, calldate, "1");


            }
        });

        if (object.getFavstatus().equalsIgnoreCase("yes")) {
            holder.favoritebuyer.setImageResource(R.drawable.fav2);
            holder.favoritebuyer.setEnabled(false);
        } else {
            holder.favoritebuyer.setImageResource(R.drawable.fav1);
        }

        holder.favoritebuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                srchid = object.getSearchId();
                BuyerId = vehicle_id + "," + srchid;
                apicall.addRemovefavouriteStatus(myContact, BuyerId, 0, "");
                holder.favoritebuyer.setImageResource(R.drawable.fav2);

                object.setFavstatus("yes");
            }


        });

    }

    @Override
    public int getItemCount() {
        return foundList.size();
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

    static class BuyerHolder extends RecyclerView.ViewHolder {

        ImageView buyer_lead_image, callbuyer, favoritebuyer;
        TextView buyerusername, buyerlocation, namecity;
        CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10;

        public BuyerHolder(View itemView) {
            super(itemView);

            buyerusername = (TextView) itemView.findViewById(R.id.buyerusername);
            buyerlocation = (TextView) itemView.findViewById(R.id.buyerlocation);
            namecity = (TextView) itemView.findViewById(R.id.namecity);

            buyer_lead_image = (ImageView) itemView.findViewById(R.id.buyer_lead_image);
            favoritebuyer = (ImageView) itemView.findViewById(R.id.favouritebuyer);
            callbuyer = (ImageView) itemView.findViewById(R.id.callbuyer);
            checkBox1 = (CheckBox) itemView.findViewById(R.id.checkBox1);
            checkBox2 = (CheckBox) itemView.findViewById(R.id.checkBox2);
            checkBox3 = (CheckBox) itemView.findViewById(R.id.checkBox3);
            checkBox4 = (CheckBox) itemView.findViewById(R.id.checkBox4);
            checkBox5 = (CheckBox) itemView.findViewById(R.id.checkBox5);
            checkBox6 = (CheckBox) itemView.findViewById(R.id.checkBox6);
            checkBox7 = (CheckBox) itemView.findViewById(R.id.checkBox7);
            checkBox8 = (CheckBox) itemView.findViewById(R.id.checkBox8);
            checkBox9 = (CheckBox) itemView.findViewById(R.id.checkBox9);
            checkBox10 = (CheckBox) itemView.findViewById(R.id.checkBox10);


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
