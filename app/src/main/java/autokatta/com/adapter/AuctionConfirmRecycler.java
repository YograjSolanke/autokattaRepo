package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.events.CreateAuctionConfirmFragment;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.AuctionAllVehicleData;
import retrofit2.Response;

/**
 * Created by ak-003 on 5/4/17.
 */

public class AuctionConfirmRecycler extends RecyclerView.Adapter<AuctionConfirmRecycler.AuctionHolder> implements RequestNotifier {

    private Activity mActivity;
    private List<AuctionAllVehicleData> finalVehiclesData;
    private List<Boolean> positionArray;
    private List<AuctionAllVehicleData> checkedVehicleData;
    private int auctionId = 0;
    private static ArrayList<Boolean> isSave;

    public AuctionConfirmRecycler(Activity activity, int bundleAuctionId, ArrayList<AuctionAllVehicleData> finalVehiclesData1) {
        mActivity = activity;
        this.finalVehiclesData = finalVehiclesData1;
        auctionId = bundleAuctionId;

        positionArray = new ArrayList<>(finalVehiclesData.size());
        checkedVehicleData = new ArrayList<>(finalVehiclesData.size());
        isSave = new ArrayList<>(finalVehiclesData.size());
        isSave.clear();

        for (int i = 0; i < finalVehiclesData.size(); i++) {
            positionArray.add(false);
            isSave.add(false);
        }
    }

    @Override
    public int getItemCount() {
        return finalVehiclesData.size();
    }

    @Override
    public AuctionConfirmRecycler.AuctionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_auction_confirm, parent, false);
        /*AuctionHolder auctionHolder = new AuctionHolder(v);*/
        return new AuctionHolder(v);
    }

    @Override
    public void onBindViewHolder(final AuctionConfirmRecycler.AuctionHolder holder, final int position) {

        final AuctionAllVehicleData obj = finalVehiclesData.get(position);

        holder.texttitle.setText(obj.vehicleTitle);
        holder.textcategory.setText(obj.vehicleCategory);
        holder.textbrand.setText(obj.vehicleBrand);
        holder.textmodel.setText(obj.vehicleModel);
        holder.textcity.setText(obj.vehicleLocation_city);
        holder.textcolor.setText(obj.vehicleColor);
        holder.textYOM.setText(obj.vehicleMfgYear);

        System.out.println("AAAAAAAAA id" + obj.vehicleId);
        System.out.println("AAAAAAAAA title" + obj.vehicleTitle);
        System.out.println("AAAAAAAAA category" + obj.vehicleCategory);
        System.out.println("AAAAAAAAA brand" + obj.vehicleBrand);
        System.out.println("AAAAAAAAA model" + obj.vehicleModel);
        System.out.println("AAAAAAAAA location" + obj.vehicleLocation_city);
        System.out.println("AAAAAAAAA color" + obj.vehicleColor);
        System.out.println("AAAAAAAAA startprice" + obj.vehicleStartPrice);
        System.out.println("AAAAAAAAA reserveprice" + obj.vehicleReservedPrice);

        if (obj.vehicleId.startsWith("A ")) {
            holder.startprice1.setText(obj.vehicleStartPrice);
            holder.reserveprice1.setText(obj.vehicleReservedPrice);

        }

        if (obj.getVehicleSingleImage() != null || !obj.getVehicleSingleImage().equals("")) {

            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.base_image_url) + obj.getVehicleSingleImage().replaceAll(" ", "%20"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .into(holder.image);
        } else
            holder.image.setBackgroundResource(R.drawable.vehiimg);


        holder.checkBox.setFocusable(false);
        holder.checkBox.setChecked(positionArray.get(position));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    CreateAuctionConfirmFragment.noOfVehicles++;
                    CreateAuctionConfirmFragment.editvehicle.setText(String.valueOf(CreateAuctionConfirmFragment.noOfVehicles));

                    positionArray.set(position, true);
                    checkedVehicleData.add(obj);

                } else {
                    CreateAuctionConfirmFragment.noOfVehicles--;
                    CreateAuctionConfirmFragment.editvehicle.setText(String.valueOf(CreateAuctionConfirmFragment.noOfVehicles));

                    positionArray.set(position, false);
                    checkedVehicleData.remove(obj);
                }
            }

        });


        holder.btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String startPrice = holder.startprice1.getText().toString();
                String reservedPrice = holder.reserveprice1.getText().toString();
                String vehicleId = obj.vehicleId;

                if (startPrice.equals("")) {
                    //isSave.set(position,false);
                    holder.startprice1.setError("Start price should not be empty");
                } else if (reservedPrice.equals("")) {
                    //isSave.set(position,false);
                    holder.reserveprice1.setError("Reserved price should not be empty");
                } else if (!startPrice.equals("") && !reservedPrice.equals("")) {

                    addPrice(startPrice, reservedPrice, vehicleId);
                    isSave.set(position, true);


                    holder.startprice1.setEnabled(false);
                    holder.reserveprice1.setEnabled(false);
                    holder.btnedit.setVisibility(View.VISIBLE);
                    holder.btnsave.setVisibility(View.INVISIBLE);
                }
            }
        });

        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnedit.setVisibility(View.GONE);
                holder.btnsave.setVisibility(View.VISIBLE);
                holder.startprice1.setEnabled(true);
                holder.reserveprice1.setEnabled(true);
            }
        });

    }


    class AuctionHolder extends RecyclerView.ViewHolder {
        TextView texttitle;
        TextView textcategory;
        TextView textbrand;
        TextView textmodel;
        TextView textcity;
        TextView textcolor;
        TextView textYOM;
        ImageView image;
        EditText startprice1, reserveprice1;
        Button btnsave, btnedit;

        CheckBox checkBox;

        public AuctionHolder(View itemView) {
            super(itemView);
            texttitle = (TextView) itemView.findViewById(R.id.vehicletitle);
            textcategory = (TextView) itemView.findViewById(R.id.vehiclecategory);
            textbrand = (TextView) itemView.findViewById(R.id.vehiclebrand);
            textmodel = (TextView) itemView.findViewById(R.id.vehiclemodel);
            textcity = (TextView) itemView.findViewById(R.id.vehiclertocity);
            textcolor = (TextView) itemView.findViewById(R.id.vehiclecolor);
            textYOM = (TextView) itemView.findViewById(R.id.vehicle_manu_year);
            image = (ImageView) itemView.findViewById(R.id.vehicleimage);
            btnsave = (Button) itemView.findViewById(R.id.btnsave);
            btnedit = (Button) itemView.findViewById(R.id.btnedit);

            startprice1 = (EditText) itemView.findViewById(R.id.startprice1);
            reserveprice1 = (EditText) itemView.findViewById(R.id.reserveprice1);

            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }


    public List<AuctionAllVehicleData> checkboxVehicleIds() {
        return checkedVehicleData;
    }

    private void addPrice(final String startPrice, final String reservedPrice, String vehicleId) {
        ApiCall apiCall = new ApiCall(mActivity, this);
        apiCall.Start_ReservedPrice(auctionId, vehicleId, startPrice, reservedPrice);
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
            Log.i("Check class", "Auction Confirm Adapter");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

        if (str != null) {

            if (str.equalsIgnoreCase("Success")) {
                Toast.makeText(mActivity, "Thank you price save successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mActivity, "Price not saved", Toast.LENGTH_SHORT).show();
                Log.i("Response", "->addPrice:" + str);
            }
        } else
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));

    }

}
