package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.events.CreateAuctionConfirmFragment;
import autokatta.com.response.AuctionAllVehicleData;

/**
 * Created by ak-003 on 4/4/17.
 */

public class AuctionConfirmAdapter extends BaseAdapter {

    Activity activity;
    private LayoutInflater mInflater;

    private List<AuctionAllVehicleData> finalVehiclesData;

    private List<Boolean> positionArray;
    private List<AuctionAllVehicleData> checkedVehicleData;

    private String auctionId, vehicleId;
    public static ArrayList<Boolean> isSave;

    public AuctionConfirmAdapter(Activity activity, String bundleAuctionId, List<AuctionAllVehicleData> finalVehiclesData) {

        this.activity = activity;
        this.finalVehiclesData = finalVehiclesData;
        auctionId = bundleAuctionId;

        positionArray = new ArrayList<>(finalVehiclesData.size());
        checkedVehicleData = new ArrayList<>(finalVehiclesData.size());
        isSave = new ArrayList<>(finalVehiclesData.size());

        for (int i = 0; i < finalVehiclesData.size(); i++) {
            positionArray.add(false);
            isSave.add(false);
        }


        mInflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return finalVehiclesData.size();
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

    public static class ViewHolder {

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

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;


        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_auction_confirm, null);
            holder = new ViewHolder();

            holder.texttitle = (TextView) convertView.findViewById(R.id.vehicletitle);
            holder.textcategory = (TextView) convertView.findViewById(R.id.vehiclecategory);
            holder.textbrand = (TextView) convertView.findViewById(R.id.vehiclebrand);
            holder.textmodel = (TextView) convertView.findViewById(R.id.vehiclemodel);
            holder.textcity = (TextView) convertView.findViewById(R.id.vehiclertocity);
            holder.textcolor = (TextView) convertView.findViewById(R.id.vehiclecolor);
            holder.textYOM = (TextView) convertView.findViewById(R.id.vehicle_manu_year);
            holder.image = (ImageView) convertView.findViewById(R.id.vehicleimage);
            holder.btnsave = (Button) convertView.findViewById(R.id.btnsave);
            holder.btnedit = (Button) convertView.findViewById(R.id.btnedit);

            holder.startprice1 = (EditText) convertView.findViewById(R.id.startprice1);
            holder.reserveprice1 = (EditText) convertView.findViewById(R.id.reserveprice1);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.checkBox.setOnCheckedChangeListener(null);
        }

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

        vehicleId = obj.vehicleId;
        System.out.println("AAAAAAAAA id1" + vehicleId);
        if (obj.vehicleId.startsWith("A ")) {
            holder.startprice1.setText(obj.vehicleStartPrice);
            holder.reserveprice1.setText(obj.vehicleReservedPrice);

        }

        if (obj.getVehicleSingleImage() != null) {

            Glide.with(activity)
                    .load("http://autokatta.com/mobile/uploads/" + obj.getVehicleSingleImage().replaceAll(" ", "%20"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .into(holder.image);
        } else {
            holder.image.setBackgroundResource(R.drawable.vehiimg);
        }


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

                if (startPrice.equals("")) {
                    //isSave.set(position,false);
                    holder.startprice1.setError("Start price should not empty");
                } else if (reservedPrice.equals("")) {
                    //isSave.set(position,false);
                    holder.reserveprice1.setError("Reserved price should not empty");
                } else if (!startPrice.equals("") && !reservedPrice.equals("")) {
                    try {
                        addPrice(startPrice, reservedPrice);
                        isSave.set(position, true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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


        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

//                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        return convertView;
    }

    public List<AuctionAllVehicleData> checkboxVehicleIds() {
        return checkedVehicleData;
    }

    private void addPrice(final String startPrice, final String reservedPrice) throws JSONException {
        /*RequestQueue requestQueue = Volley.newRequestQueue(activity);

        System.out.println("Start-->" + startPrice + " " + "Reserved-->" + reservedPrice);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://autokatta.com/mobile/addStartReservedPrice.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("onResponse:addPrice" + response);

                        Toast.makeText(activity, "Thank you price save successfully", Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("onError:addPrice" + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("auction_id", auctionId);
                params.put("vehicle_id", vehicleId);
                params.put("startPrice", startPrice);
                params.put("reservedPrice", reservedPrice);

                return params;
            }
        };
        requestQueue.add(stringRequest);*/
    }


}
