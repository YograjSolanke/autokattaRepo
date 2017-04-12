package autokatta.com.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment.UploadedVehicleBuyerList;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyUploadedVehiclesResponse;
import retrofit2.Response;

/**
 * Created by ak-004 on 1/4/17.
 */

public class MyUploadedVehicleAdapter extends RecyclerView.Adapter<MyUploadedVehicleAdapter.VehicleHolder> implements RequestNotifier {

    Activity activity;
    List<MyUploadedVehiclesResponse.Success> mMainList;
    ConnectionDetector connectionDetector;
    ApiCall apiCall;

    public MyUploadedVehicleAdapter(Activity activity, List<MyUploadedVehiclesResponse.Success> successList) {

        this.activity = activity;
        this.mMainList = successList;
        connectionDetector = new ConnectionDetector(this.activity);
        apiCall = new ApiCall(this.activity, this);
    }

    @Override
    public MyUploadedVehicleAdapter.VehicleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_uploaded_vehicle_adapter, parent, false);

        VehicleHolder holder = new VehicleHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyUploadedVehicleAdapter.VehicleHolder holder, final int position) {

        ArrayList<String> vimages = new ArrayList<>();

        holder.edittitles.setText(mMainList.get(position).getTitle());
        holder.editprices.setText(mMainList.get(position).getPrice());
        holder.editcategorys.setText(mMainList.get(position).getCategory());
        holder.editbrands.setText(mMainList.get(position).getManufacturer());
        holder.editmodels.setText(mMainList.get(position).getModel());
        holder.editleads.setText(mMainList.get(position).getBuyerLeads());
        holder.delete.setVisibility(View.VISIBLE);

        holder.editmfgyr.setText(mMainList.get(position).getYearOfManufacture());
        holder.editrto.setText(mMainList.get(position).getRtoCity());
        holder.editlocation.setText(mMainList.get(position).getLocationCity());
        holder.editregNo.setText(mMainList.get(position).getRegistrationNumber());

        if (mMainList.get(position).getKmsRunning().equals(""))
            holder.editkms.setText(mMainList.get(position).getHrsRunning());
        else
            holder.editkms.setText(mMainList.get(position).getKmsRunning());

        //To set Date
        try {

            DateFormat date = new SimpleDateFormat(" MMM dd ");
            DateFormat time = new SimpleDateFormat(" hh:mm a");

            holder.edituploadedon.setText(date.format(mMainList.get(position).getDate()) + time.format(mMainList.get(position).getDate()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mMainList.get(position).getNotificationstatus().equalsIgnoreCase("stop")) {
            holder.btnnotify.setText("Start Notification");
            holder.btnnotify.setBackgroundResource(R.color.orange);
        } else {

            holder.btnnotify.setText("Stop Notification");
            holder.btnnotify.setBackgroundResource(R.drawable.buttonback);
        }


        holder.edittitles.setEnabled(false);
        holder.editprices.setEnabled(false);
        holder.editcategorys.setEnabled(false);
        holder.editbrands.setEnabled(false);
        holder.editmodels.setEnabled(false);
        holder.edituploadedon.setEnabled(false);
        holder.editleads.setEnabled(false);
        holder.editmfgyr.setEnabled(false);
        holder.editkms.setEnabled(false);
        holder.editrto.setEnabled(false);
        holder.editlocation.setEnabled(false);
        holder.editregNo.setEnabled(false);
        holder.btnnotify.setVisibility(View.VISIBLE);

        //  break;

        try {


            if (mMainList.get(position).getImages().equalsIgnoreCase("") || mMainList.get(position).getImages().equalsIgnoreCase(null) ||
                    mMainList.get(position).getImages().equalsIgnoreCase("null")) {

                holder.vehicleimage.setBackgroundResource(R.drawable.vehiimg);
            }
            if (!mMainList.get(position).getImages().equals("") || !mMainList.get(position).getImages().equalsIgnoreCase(null)
                    || !mMainList.get(position).getImages().equalsIgnoreCase("null")) {
                String[] parts = mMainList.get(position).getImages().split(",");

                for (int l = 0; l < parts.length; l++) {
                    vimages.add(parts[l]);
                    System.out.println(parts[l]);
                }
                System.out.println("http://autokatta.com/mobile/uploads/" + vimages.get(0));

                String vimagename = "http://autokatta.com/mobile/uploads/" + vimages.get(0);
                vimagename = vimagename.replaceAll(" ", "%20");
                try {

                    Glide.with(activity)
                            .load("http://autokatta.com/mobile/uploads/" + vimages.get(0))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
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

                if ((!connectionDetector.isConnectedToInternet())) {
                    Toast.makeText(activity, "No network....Please try later", Toast.LENGTH_SHORT).show();
                } else {

                    new AlertDialog.Builder(activity)
                            .setTitle("Delete?")
                            .setMessage("Are You Sure You Want To Delete This Store?")

                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    apiCall.deleteUploadedVehicle(mMainList.get(holder.getAdapterPosition()).getVehicleId(), "delete");

                                    mMainList.remove(holder.getAdapterPosition());
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


        holder.btnnotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!connectionDetector.isConnectedToInternet()) {
                    CustomToast.customToast(activity, activity.getString(R.string.no_internet));

                } else {
                    String keyword;

                    if (holder.btnnotify.getText().toString().equalsIgnoreCase("Stop Notification")) {
                        keyword = "stop";
                        holder.btnnotify.setText("Start Notification");
                        holder.btnnotify.setBackgroundResource(R.color.orange);
                    } else {
                        keyword = "start";
                        holder.btnnotify.setText("Stop Notification");
                        holder.btnnotify.setBackgroundResource(R.drawable.buttonback);
                    }
                    apiCall.sendNotificationOfUploadedVehicle(mMainList.get(holder.getAdapterPosition()).getVehicleId(), keyword);

                }
            }
        });


        holder.vehidetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
//                Vehical_Details frag = new Vehical_Details();
//                b.putString("Vehical_id", obj.vehicleId);
//                frag.setArguments(b);
//
//                FragmentManager fragmentManager = ctx.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.containerView, frag);
//                fragmentTransaction.addToBackStack("vehicle_details");
//                fragmentTransaction.commit();

            }
        });


        holder.mcardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.editleads.getText().toString().equalsIgnoreCase("0")) {
                    Toast.makeText(activity, "No leads found", Toast.LENGTH_SHORT).show();
                } else {

                    Bundle b = new Bundle();
                    b.putString("vehicle_id", mMainList.get(holder.getAdapterPosition()).getVehicleId());
                    b.putString("title", mMainList.get(holder.getAdapterPosition()).getTitle());
                    b.putString("price", mMainList.get(holder.getAdapterPosition()).getPrice());
                    b.putString("category", mMainList.get(holder.getAdapterPosition()).getCategory());
                    b.putString("brand", mMainList.get(holder.getAdapterPosition()).getManufacturer());
                    b.putString("model", mMainList.get(holder.getAdapterPosition()).getModel());
                    b.putString("image", mMainList.get(holder.getAdapterPosition()).getImages());
                    b.putString("uploaddate", mMainList.get(holder.getAdapterPosition()).getDate());
                    b.putString("noofleads", mMainList.get(holder.getAdapterPosition()).getBuyerLeads());
                    b.putString("rto_city", mMainList.get(holder.getAdapterPosition()).getRtoCity());
                    b.putString("manufacture_year", mMainList.get(holder.getAdapterPosition()).getYearOfManufacture());


                    UploadedVehicleBuyerList frag = new UploadedVehicleBuyerList();
                    frag.setArguments(b);

                    FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.myUploadedVehicleFrame, frag);
                    fragmentTransaction.addToBackStack("vehicle_buyer_list");
                    fragmentTransaction.commit();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mMainList.size();
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {

            if (str.equals("success")) {
                CustomToast.customToast(activity, "vehicle deleted");

            } else if (str.equals("success_notification")) {
                CustomToast.customToast(activity, "notification sent");
            }
        }

    }

    static class VehicleHolder extends RecyclerView.ViewHolder {

        ImageView vehicleimage, delete;
        TextView edittitles, editprices, editcategorys, editbrands, editmodels, editleads, edituploadedon, editmfgyr, editkms, editrto, editlocation, editregNo;
        Button vehidetails, btnnotify;
        CardView mcardView;

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
            btnnotify = (Button) itemView.findViewById(R.id.btnnotify);

            editmfgyr = (TextView) itemView.findViewById(R.id.year);
            editkms = (TextView) itemView.findViewById(R.id.km_hrs);
            //edithrs=(TextView)itemView.findViewById(R.id.km_hrs);
            editrto = (TextView) itemView.findViewById(R.id.RTO);
            editlocation = (TextView) itemView.findViewById(R.id.location);
            editregNo = (TextView) itemView.findViewById(R.id.registrationNo);
            mcardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
