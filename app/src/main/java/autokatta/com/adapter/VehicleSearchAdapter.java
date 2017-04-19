package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.SearchVehicleResponse;
import autokatta.com.view.VehicleDetails;

/**
 * Created by ak-001 on 19/4/17.
 */

public class VehicleSearchAdapter extends BaseAdapter {
    Activity activity;
    private String contactnumber;
    String vimagename = "";
    private LayoutInflater inflater;
    private List<SearchVehicleResponse.Success> mystorevehicle = new ArrayList<>();

    public VehicleSearchAdapter(Activity activity, List<SearchVehicleResponse.Success> mystorevehicle) {
        this.activity = activity;
        this.mystorevehicle = mystorevehicle;
        contactnumber = activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", "");
        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mystorevehicle.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    static class ViewHolder {
        ImageView vehicleimage, delete;
        TextView edittitles, editprices, editcategorys, editbrands, editmodels, editleads, edituploadedon;
        TextView Year, Location, Rto, Kms, Regno;
        Button vehidetails;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        ArrayList<String> vimages = new ArrayList<>();
        ArrayList<String> images = new ArrayList<String>();
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.vehicle_new, null);
            // holder = new ViewHolder();
            holder.edittitles = (TextView) convertView.findViewById(R.id.edittitle);
            holder.editprices = (TextView) convertView.findViewById(R.id.editprice);
            holder.editcategorys = (TextView) convertView.findViewById(R.id.editcategory);
            holder.editbrands = (TextView) convertView.findViewById(R.id.editbrand);
            holder.editmodels = (TextView) convertView.findViewById(R.id.editmodel);
            holder.editleads = (TextView) convertView.findViewById(R.id.editleads);
            holder.edituploadedon = (TextView) convertView.findViewById(R.id.edituploadedon);
            holder.vehicleimage = (ImageView) convertView.findViewById(R.id.vehiprofile);
            holder.delete = (ImageView) convertView.findViewById(R.id.delete);
            holder.vehidetails = (Button) convertView.findViewById(R.id.vehibtndetails);

            holder.Year = (TextView) convertView.findViewById(R.id.year);
            holder.Kms = (TextView) convertView.findViewById(R.id.km_hrs);
            holder.Rto = (TextView) convertView.findViewById(R.id.RTO);
            holder.Location = (TextView) convertView.findViewById(R.id.location);
            holder.Regno = (TextView) convertView.findViewById(R.id.registrationNo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final SearchVehicleResponse.Success obj = mystorevehicle.get(position);

        holder.edittitles.setText(obj.getTitle());
        holder.editprices.setText(obj.getPrice());
        holder.editcategorys.setText(obj.getCategory());
        holder.editbrands.setText(obj.getManufacturer());
        holder.editmodels.setText(obj.getModel());
        holder.editleads.setText(obj.getBuyerLeads());
        holder.Year.setText(obj.getYearOfManufacture());
        holder.Location.setText(obj.getLocationCity());
        holder.Kms.setText(obj.getKmsRunning());
        holder.Rto.setText(obj.getRTOCity());
        holder.Regno.setText(obj.getRegistrationNumber());

        //To set Date
        try {
           /* DateFormat date = new SimpleDateFormat(" MMM dd ");
            DateFormat time = new SimpleDateFormat(" hh:mm a");*/
            //holder.edituploadedon.setText(date.format(obj.getDate())+ time.format(obj.getDate()));
            holder.edituploadedon.setText(obj.getDate() + " " + obj.getDate());

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

        //  break;

        try {
            System.out.println(obj.getImage());
            if (obj.getImage().equalsIgnoreCase("") || obj.getImage().equalsIgnoreCase(null) || obj.getImage().equalsIgnoreCase("null")) {
                holder.vehicleimage.setBackgroundResource(R.drawable.vehiimg);
            }
            if (!obj.getImage().equals("") || !obj.getImage().equalsIgnoreCase(null) || !obj.getImage().equalsIgnoreCase("null")) {
                String[] parts = obj.getImage().split(",");
                for (int l = 0; l < parts.length; l++) {
                    vimages.add(parts[l]);
                    System.out.println(parts[l]);
                }
                vimagename = "http://autokatta.com/mobile/uploads/" + vimages.get(0);
                vimagename = vimagename.replaceAll(" ", "%20");
                try {
                    Glide.with(activity)
                            .load("http://autokatta.com/mobile/uploads/" + vimages.get(0))
                            //.bitmapTransform(new CropCircleTransformation(activity)) //To display image in Circular form.
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            //.placeholder(R.drawable.logo) //To show image before loading an original image.
                            //.error(R.drawable.blocked) //To show error image if problem in loading.
                            .override(100, 100)
                            .into(holder.vehicleimage);
                } catch (Exception e) {
                    System.out.println("Error in uploading images");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.vehidetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putString("vehicle_id", obj.getVehicleId());
                Intent intent = new Intent(activity, VehicleDetails.class);
                intent.putExtras(b);
                activity.startActivity(intent);
                /*
                Vehical_Details frag = new Vehical_Details();
                b.putString("Vehical_id",obj.vehicleId);
                frag.setArguments(b);

                FragmentManager fragmentManager = ctx.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerView, frag);
                fragmentTransaction.addToBackStack("vehicle_details");
                fragmentTransaction.commit();*/

            }
        });
        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        return convertView;
    }
}
