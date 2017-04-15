package autokatta.com.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.register.NextRegistrationContinue;
import autokatta.com.response.GetOwnVehiclesResponse;

/**
 * Created by ak-005 on 14/4/17.
 */

public class MyVehiclesAdapter extends RecyclerView.Adapter<MyVehiclesAdapter.MyViewHolder> {

    List<GetOwnVehiclesResponse.Success> mGetOwnVehiclesList = new ArrayList<>();
    Activity activity;
    Bundle bundle = new Bundle();

    public MyVehiclesAdapter(Activity activity, List<GetOwnVehiclesResponse.Success> mList) {
        this.mGetOwnVehiclesList = mList;
        this.activity = activity;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vehicleType, vehicleYear, vehicleBrand, vehicleModel, vehicleVersion, vehicleSubcategory, uploadDates;
        ImageView vehi_pic, edit_vehi;

        public MyViewHolder(View itemView) {
            super(itemView);
            vehicleType = (TextView) itemView.findViewById(R.id.vehicle_type);
            vehicleYear = (TextView) itemView.findViewById(R.id.year);
            vehicleBrand = (TextView) itemView.findViewById(R.id.brand);
            vehicleModel = (TextView) itemView.findViewById(R.id.model);
            vehicleVersion = (TextView) itemView.findViewById(R.id.version);
            vehicleSubcategory = (TextView) itemView.findViewById(R.id.subcategory);
            uploadDates = (TextView) itemView.findViewById(R.id.year);

            vehi_pic = (ImageView) itemView.findViewById(R.id.image1);
            edit_vehi = (ImageView) itemView.findViewById(R.id.edtVehicle);

        }
    }


    @Override
    public MyVehiclesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_my_vehicles, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.vehicleType.setText(mGetOwnVehiclesList.get(position).getVehicleType());
        holder.vehicleYear.setText(mGetOwnVehiclesList.get(position).getYear());
        holder.vehicleBrand.setText(mGetOwnVehiclesList.get(position).getBrand());
        holder.vehicleModel.setText(mGetOwnVehiclesList.get(position).getModelNo());
        holder.vehicleVersion.setText(mGetOwnVehiclesList.get(position).getVersion());
        holder.vehicleSubcategory.setText(mGetOwnVehiclesList.get(position).getSubcategory());

        holder.edit_vehi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, NextRegistrationContinue.class);
                i.putExtra("action", "MyVehicles");
                i.putExtra("vehicletype", holder.vehicleType.getText().toString());
                i.putExtra("vehicleyear", holder.vehicleYear.getText().toString());
                i.putExtra("vehiclebrand", holder.vehicleBrand.getText().toString());
                i.putExtra("vehiclemodel", holder.vehicleModel.getText().toString());
                i.putExtra("vehicleversion", holder.vehicleVersion.getText().toString());
                i.putExtra("vehiclesubcategory", holder.vehicleSubcategory.getText().toString());

                i.putExtra("idss", mGetOwnVehiclesList.get(position).getId());
                i.putExtra("vehicleno", mGetOwnVehiclesList.get(position).getVehiNo());
                i.putExtra("taxvalidity", mGetOwnVehiclesList.get(position).getTaxValidity());
                i.putExtra("fitnessvalidity", mGetOwnVehiclesList.get(position).getFitnessValidity());
                i.putExtra("permitvalidity", mGetOwnVehiclesList.get(position).getPermitValidity());
                i.putExtra("insurance", mGetOwnVehiclesList.get(position).getInsurance());
                i.putExtra("puc", mGetOwnVehiclesList.get(position).getPuc());
                i.putExtra("lastservice", mGetOwnVehiclesList.get(position).getLastServiceDate());
                i.putExtra("nextservice", mGetOwnVehiclesList.get(position).getNextServiceDate());
                activity.startActivity(i);
                activity.finish();
            }
        });
        try {
//To set Date
            DateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat newDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

            holder.uploadDates.setText(newDateFormat.format(inputDate.parse(mGetOwnVehiclesList.get(position).getUploaddate())));

            //for time and date warnings

                 /*  DateFormat date = new SimpleDateFormat(" MMM dd ");
            DateFormat time = new SimpleDateFormat(" hh:mm a");

            holder.uploadDates.setText(date.format(mGetOwnVehiclesList.get(position).getUploaddate()) + time.format(mGetOwnVehiclesList.get(position).getUploaddate()));
       */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return mGetOwnVehiclesList.size();
    }
}
