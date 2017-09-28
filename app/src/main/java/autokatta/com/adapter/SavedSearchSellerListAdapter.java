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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.SellerResponse;
import autokatta.com.view.CompareVehicleListActivity;
import autokatta.com.view.OtherProfile;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 14/4/17.
 */

public class SavedSearchSellerListAdapter extends RecyclerView.Adapter<SavedSearchSellerListAdapter.SellerHolder> implements RequestNotifier {

    private List<SellerResponse.Success.MatchedResult> matchedResultList = new ArrayList<>();
    Activity activity;
    ApiCall apiCall;
    private String category, brand, model, rto_city, manufacture_year, myContact;
    private String recieverContact, SenderContact, SellerId, VehiId, calldate;
    private int search_id;
    private List<Integer> checkedvehicle_ids = new ArrayList<>();
    private String getBundle_vehicle_id = "";


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

        checkedvehicle_ids = new ArrayList<>(matchedResults.size());
        for (int j = 0; j < matchedResults.size(); j++) {
            checkedvehicle_ids.add(0);
        }
    }

    @Override
    public SavedSearchSellerListAdapter.SellerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_list_adapter, parent, false);
        return new SellerHolder(view);
    }

    @Override
    public void onBindViewHolder(final SavedSearchSellerListAdapter.SellerHolder holder, final int position) {

        final SellerResponse.Success.MatchedResult object = matchedResultList.get(position);
        CheckBox checkBox[] = new CheckBox[matchedResultList.size()];

        holder.sellerusername.setText(object.getUsername());
        holder.Title.setText(object.getTitle());

        //to set buyer last call date
       /* try {

            DateFormat date = new SimpleDateFormat(" MMM dd ", Locale.getDefault());
            DateFormat time = new SimpleDateFormat(" hh:mm a", Locale.getDefault());

            holder.lastCall.setText("Last call on:" + date.format(object.getLastCallDateNew()) + time.format(object.getLastCallDateNew()));

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        try {
            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
            //format of date coming from services
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            inputFormat.setTimeZone(utc);

            //format of date which we want to show
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            outputFormat.setTimeZone(utc);

            Date date = inputFormat.parse(object.getLastcall());
            String output = outputFormat.format(date);

            holder.lastCall.setText("" + "Last call on :" + output);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //to set vehicle uploaded date
        /*try {

            DateFormat date = new SimpleDateFormat(" MMM dd ", Locale.getDefault());
            DateFormat time = new SimpleDateFormat(" hh:mm a", Locale.getDefault());

            holder.UploadedDate.setText("Uploadede On :" + date.format(object.getDate()) + time.format(object.getDate()));

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        try {
            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
            //format of date coming from services
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            inputFormat.setTimeZone(utc);

            //format of date which we want to show
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            outputFormat.setTimeZone(utc);

            Date date = inputFormat.parse(object.getDate());
            String output = outputFormat.format(date);

            holder.UploadedDate.setText("" + "Uploaded On :" + output);
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
        List<String> iname = new ArrayList<String>();

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

        /*final int v_ids = Integer.parseInt(matchedResultList.get(holder.getAdapterPosition()).getVehicleId());
        checkBox[holder.getAdapterPosition()].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkedvehicle_ids.set(holder.getAdapterPosition(), v_ids);
                    VisibleCompareButton(checkedvehicle_ids);
                } else {
                    checkedvehicle_ids.set(holder.getAdapterPosition(), 0);
                    VisibleCompareButton(checkedvehicle_ids);
                }
            }
        });*/

        holder.sellerusername.setOnClickListener(new View.OnClickListener() {
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


        holder.callseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recieverContact = object.getContactNo();
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String calldate = df.format(c.getTime());
                if (!recieverContact.equals(myContact)) {
                    call(recieverContact);
                    apiCall.sendLastCallDate(myContact, recieverContact, calldate, "1");
                }
            }
        });


        if (object.getFavstatus().equalsIgnoreCase("yes")) {
            holder.mFavimg.setVisibility(View.GONE);
            holder.unmFavimg.setVisibility(View.VISIBLE);
        } else {
            holder.mFavimg.setVisibility(View.VISIBLE);
            holder.unmFavimg.setVisibility(View.GONE);
        }

        holder.mFavimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sellerVehicleId = object.getVehicleId();

                apiCall.addToFavorite(myContact, search_id, sellerVehicleId, 0, 0, 0, 0);
                holder.mFavimg.setVisibility(View.GONE);
                holder.unmFavimg.setVisibility(View.VISIBLE);

                object.setFavstatus("yes");
            }


        });

        holder.unmFavimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sellerVehicleId = object.getVehicleId();

                apiCall.removeFromFavorite(myContact, search_id, sellerVehicleId, 0, 0, 0, 0);
                holder.mFavimg.setVisibility(View.GONE);
                holder.unmFavimg.setVisibility(View.VISIBLE);
                object.setFavstatus("no");

            }


        });

        holder.Compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CompareVehicleListActivity.class);
                intent.putExtra("vehicle_ids", getBundle_vehicle_id);
                activity.startActivity(intent);
            }
        });

    }

    private void VisibleCompareButton(List<Integer> vehicleids) {
        int flag = 0;
        getBundle_vehicle_id = "";
        for (int i = 0; i < vehicleids.size(); i++) {
            if (vehicleids.get(i) != 0) {
                flag++;
                if (getBundle_vehicle_id.equals(""))
                    getBundle_vehicle_id = vehicleids.get(i).toString();
                else
                    getBundle_vehicle_id = getBundle_vehicle_id + "," + vehicleids.get(i).toString();
            }

        }

       /* if (flag > 1)
            relativeLayout.setVisibility(View.VISIBLE);
        else
            relativeLayout.setVisibility(View.GONE);*/

    }

    @Override
    public int getItemCount() {
        return matchedResultList.size();
    }

    static class SellerHolder extends RecyclerView.ViewHolder {

        ImageView callseller, mFavimg, unmFavimg;
        ViewFlipper vehi_images;
        TextView sellerusername, buyerlocation, lastCall, Compare, Vehic_cnt, UploadedDate, Title;
        CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10, chckbox;
        CheckBox checkBox[];

        SellerHolder(View itemView) {
            super(itemView);

            sellerusername = (TextView) itemView.findViewById(R.id.username);
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

            //checkBox[getAdapterPosition()] = (CheckBox) itemView.findViewById(R.id.checkauc);

            mFavimg = (ImageView) itemView.findViewById(R.id.sellfevimg);
            unmFavimg = (ImageView) itemView.findViewById(R.id.sellunfevimg);
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
            Log.i("Check Class-", "SavedSearchSellerListAdapter");
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
