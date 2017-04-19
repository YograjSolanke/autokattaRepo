package autokatta.com.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreInventoryResponse;
import autokatta.com.view.VehicleDetails;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

/**
 * Created by ak-004 on 18/4/17.
 */

public class StoreVehicleAdapter extends RecyclerView.Adapter<StoreVehicleAdapter.VehicleHolder> implements RequestNotifier {

    Activity activity;
    List<StoreInventoryResponse.Success.Vehicle> vehicleList = new ArrayList<>();
    String myContact, storeContact;
    String vimagename = "";
    ApiCall apiCall;
    ConnectionDetector connectionDetector;

    public StoreVehicleAdapter(Activity activity, List<StoreInventoryResponse.Success.Vehicle> vehicleList, String myContact,
                               String storeContact) {
        this.activity = activity;
        this.vehicleList = vehicleList;
        this.myContact = myContact;
        this.storeContact = storeContact;
        apiCall = new ApiCall(activity, this);
        connectionDetector = new ConnectionDetector(activity);


    }

    @Override
    public StoreVehicleAdapter.VehicleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_uploaded_vehicle_adapter, parent, false);
        VehicleHolder holder = new VehicleHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final StoreVehicleAdapter.VehicleHolder holder, final int position) {


        final StoreInventoryResponse.Success.Vehicle obj = vehicleList.get(position);
        ArrayList<String> vimages = new ArrayList<>();

        holder.edittitles.setText(obj.getTitle());
        holder.editprices.setText(obj.getPrice());
        holder.editcategorys.setText(obj.getCategory());
        holder.editbrands.setText(obj.getManufacturer());
        holder.editmodels.setText(obj.getModel());
        holder.editleads.setText(obj.getBuyerLeads());

        holder.Year.setText(obj.getYear());
        holder.Location.setText(obj.getLocation());
        holder.Kms.setText(obj.getKms());
        holder.Rto.setText(obj.getRto());
        holder.Regno.setText(obj.getRegno());


        if (myContact.equalsIgnoreCase(storeContact)) {
            holder.delete.setVisibility(View.VISIBLE);
        }

        //To set Date
        try {

            DateFormat date = new SimpleDateFormat(" MMM dd ");
            DateFormat time = new SimpleDateFormat(" hh:mm a");

            holder.edituploadedon.setText(date.format(obj.getVehicleDate()) + time.format(obj.getVehicleDate()));

        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.edittitles.setEnabled(false);
        holder.editprices.setEnabled(false);
        holder.editcategorys.setEnabled(false);
        holder.editbrands.setEnabled(false);
        holder.editmodels.setEnabled(false);
        holder.edituploadedon.setEnabled(false);
        holder.editleads.setEnabled(false);


        try {

            if (obj.getImages().equalsIgnoreCase("") || obj.getImages().equalsIgnoreCase(null) || obj.getImages().equalsIgnoreCase("null")) {

                holder.vehicleimage.setBackgroundResource(R.drawable.vehiimg);
            }
            if (!obj.getImages().equals("") || !obj.getImages().equalsIgnoreCase(null) || !obj.getImages().equalsIgnoreCase("null")) {
                String[] parts = obj.getImages().split(",");

                for (int l = 0; l < parts.length; l++) {
                    vimages.add(parts[l]);
                    System.out.println(parts[l]);
                }
                System.out.println("http://autokatta.com/mobile/uploads/" + vimages.get(0));

                vimagename = "http://autokatta.com/mobile/uploads/" + vimages.get(0);
                vimagename = vimagename.replaceAll(" ", "%20");
                try {


                    Glide.with(activity)
                            .load("http://autokatta.com/mobile/uploads/" + vimages.get(0))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .bitmapTransform(new CropCircleTransformation(activity))
                            .placeholder(R.drawable.logo)
                            .into(holder.vehicleimage);

                } catch (Exception e) {
                    System.out.println("Error in uploading images");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String vehicle_id = obj.getVehicleId();

                if (!connectionDetector.isConnectedToInternet()) {
                    Toast.makeText(activity, "Please try later", Toast.LENGTH_SHORT).show();
                } else {
                    new android.support.v7.app.AlertDialog.Builder(activity)
                            .setTitle("Delete?")
                            .setMessage("Are You Sure You Want To Delete This Vehicle")

                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    apiCall.deleteVehicle(vehicle_id, "delete");
                                    vehicleList.remove(holder.getAdapterPosition());
                                    notifyDataSetChanged();


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
        });

        holder.vehidetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("vehicle_id", obj.getVehicleId());
                Intent intent = new Intent(activity, VehicleDetails.class);
                intent.putExtras(b);
                activity.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Toast.makeText(activity, activity.getString(R.string._404), Toast.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Toast.makeText(activity, activity.getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Toast.makeText(activity, activity.getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else {
            Log.i("Check Class-"
                    , "StoreServiceAdaper");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

        if (str != null) {
            if (str.equals("success")) {

                CustomToast.customToast(activity, "Vehicle Deleted");

            }

        }

    }

    public class VehicleHolder extends RecyclerView.ViewHolder {

        ImageView vehicleimage, delete;
        TextView edittitles, editprices, editcategorys, editbrands, editmodels, editleads, edituploadedon;
        TextView Year, Location, Rto, Kms, Regno;
        Button vehidetails;

        public VehicleHolder(View itemView) {
            super(itemView);

            edittitles = (TextView) itemView.findViewById(R.id.edittitle);
            editprices = (TextView) itemView.findViewById(R.id.editprice);
            editcategorys = (TextView) itemView.findViewById(R.id.editcategory);
            editbrands = (TextView) itemView.findViewById(R.id.editbrand);
            editmodels = (TextView) itemView.findViewById(R.id.editmodel);
            editleads = (TextView) itemView.findViewById(R.id.editleads);
            edituploadedon = (TextView) itemView.findViewById(R.id.edituploadedon);
            vehicleimage = (ImageView) itemView.findViewById(R.id.vehiprofile);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            vehidetails = (Button) itemView.findViewById(R.id.vehibtndetails);


            Year = (TextView) itemView.findViewById(R.id.year);
            Kms = (TextView) itemView.findViewById(R.id.km_hrs);
            Rto = (TextView) itemView.findViewById(R.id.RTO);
            Location = (TextView) itemView.findViewById(R.id.location);
            Regno = (TextView) itemView.findViewById(R.id.registrationNo);
        }
    }
}
