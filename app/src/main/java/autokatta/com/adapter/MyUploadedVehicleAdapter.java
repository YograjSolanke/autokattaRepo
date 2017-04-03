package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.MyUploadedVehiclesResponse;

/**
 * Created by ak-004 on 1/4/17.
 */

public class MyUploadedVehicleAdapter extends RecyclerView.Adapter<MyUploadedVehicleAdapter.VehicleHolder> {

    Activity activity;
    List<MyUploadedVehiclesResponse.Success> mMainList;

    public MyUploadedVehicleAdapter(Activity activity, List<MyUploadedVehiclesResponse.Success> successList) {

        this.activity = activity;
        this.mMainList = successList;
    }

    @Override
    public MyUploadedVehicleAdapter.VehicleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_uploaded_vehicle_adapter, parent, false);

        VehicleHolder holder = new VehicleHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyUploadedVehicleAdapter.VehicleHolder holder, int position) {

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
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                vehicle_id = obj.vehicleId;
//
//                if (!NetworkUtils.isNetworkAvailable()) {
//                    Toast.makeText(activity, "No network....Please try later", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    new AlertDialog.Builder(activity)
//                            .setTitle("Delete?")
//                            .setMessage("Are You Sure You Want To Delete This Store?")
//
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                    //new deletevehicle().execute();
//                                    try {
//                                        deletevehicle();
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                    vehicledata.remove(position);
//                                    notifyDataSetChanged();
//
//                                }
//                            })
//
//                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            })
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();
//                }
//
//
//            }
//        });
//
//
//        holder.btnnotify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                vehicle_id = obj.vehicleId;
//                if (!NetworkUtils.isNetworkAvailable()) {
//                    Toast.makeText(activity, "No network....Please try later", Toast.LENGTH_SHORT).show();
//
//                } else {
//
//                    if (holder.btnnotify.getText().toString().equalsIgnoreCase("Stop Notification")) {
//                        keyword = "stop";
//                        holder.btnnotify.setText("Start Notification");
//                        holder.btnnotify.setBackgroundResource(R.color.orange);
//                    } else {
//                        keyword = "start";
//                        holder.btnnotify.setText("Stop Notification");
//                        holder.btnnotify.setBackgroundResource(R.drawable.button_background);
//                    }
//                    try {
//                        sendnotification();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//
//
//        holder.vehidetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Bundle b = new Bundle();
//                Vehical_Details frag = new Vehical_Details();
//                b.putString("Vehical_id", obj.vehicleId);
//                frag.setArguments(b);
//
//                FragmentManager fragmentManager = ctx.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.containerView, frag);
//                fragmentTransaction.addToBackStack("vehicle_details");
//                fragmentTransaction.commit();
//
//            }
//        });
//
//
//        holder.mcardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (holder.editleads.getText().toString().equalsIgnoreCase("0")) {
//                    Toast.makeText(activity, "No leads found", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    vehicle_id = obj.vehicleId;
//                    title = obj.vehicleTitle;
//                    price = obj.vehiclePrice;
//                    category = obj.vehicleCategory;
//                    brand = obj.vehicleBrand;
//                    model = obj.vehicleModel;
//                    image = obj.vehicleImages;
//                    uploaddate = holder.edituploadedon.getText().toString();
//                    noofleads = obj.vehicelLeadsNo;
//
//                    Bundle b = new Bundle();
//                    b.putString("vehicle_id", vehicle_id);
//                    b.putString("title", title);
//                    b.putString("price", price);
//                    b.putString("category", category);
//                    b.putString("brand", brand);
//                    b.putString("model", model);
//                    b.putString("image", image);
//                    b.putString("uploaddate", uploaddate);
//                    b.putString("noofleads", noofleads);
//                    System.out.println("***************vehicle id*****In Adapter ****************" + vehicle_id);
//
//                    VehicleBuyerList frag = new VehicleBuyerList();
//                    frag.setArguments(b);
//
//                    FragmentManager fragmentManager = ctx.getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.containerView, frag);
//                    fragmentTransaction.addToBackStack("vehicle_buyer_list");
//                    fragmentTransaction.commit();
//                }
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mMainList.size();
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
