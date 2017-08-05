package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment.UploadedVehicleBuyerList;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyUploadedVehiclesResponse;
import autokatta.com.response.ProfileGroupResponse;
import autokatta.com.view.ManualEnquiry;
import autokatta.com.view.MyBroadcastGroupsActivity;
import autokatta.com.view.VehicleDetails;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 1/4/17.
 */

public class MyUploadedVehicleAdapter extends RecyclerView.Adapter<MyUploadedVehicleAdapter.VehicleHolder> implements RequestNotifier {

    Activity activity;
    List<MyUploadedVehiclesResponse.Success> mMainList;
  //  private ConnectionDetector connectionDetector;
    ApiCall apiCall;
    private String prefcontact;
    int groupid;
    String groupname;
    //SubType
    List<String> mGrouplist = new ArrayList<>();
    List<String> parsedData = new ArrayList<>();
    HashMap<String, Integer> mGrouplist1 = new HashMap<>();
    private ConnectionDetector mConnectionDetector;


    public MyUploadedVehicleAdapter(Activity activity1, List<MyUploadedVehiclesResponse.Success> successList) {

        this.activity = activity1;
        this.mMainList = successList;
        mConnectionDetector = new ConnectionDetector(activity);
        apiCall = new ApiCall(this.activity, this);
    }

    @Override
    public MyUploadedVehicleAdapter.VehicleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_uploaded_vehicle_adapter, parent, false);
        return new VehicleHolder(view);
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
        prefcontact = activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

        if (mMainList.get(position).getKmsRunning().equals(""))
            holder.editkms.setText(mMainList.get(position).getHrsRunning());
        else
            holder.editkms.setText(mMainList.get(position).getKmsRunning());

        //To set Date
        try {

            //To set Date
            DateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat newDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

            holder.edituploadedon.setText(newDateFormat.format(inputDate.parse(mMainList.get(position).getDate())));

           /* DateFormat date = new SimpleDateFormat(" MMM dd ");
            DateFormat time = new SimpleDateFormat(" hh:mm a");

            holder.edituploadedon.setText(date.format(mMainList.get(position).getDate()) + time.format(mMainList.get(position).getDate()));
*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mMainList.get(position).getNotificationstatus() == null || mMainList.get(position).getNotificationstatus().equalsIgnoreCase("stop")) {
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

                String vimagename = activity.getString(R.string.base_image_url)+ vimages.get(0);
                vimagename = vimagename.replaceAll(" ", "%20");
                try {

                    Glide.with(activity)
                            .load(activity.getString(R.string.base_image_url) + vimages.get(0))
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

        holder.mEnquiry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ManualEnquiry.class);
                activity.startActivity(intent);
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((!mConnectionDetector.isConnectedToInternet())) {
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

                if (!mConnectionDetector.isConnectedToInternet()) {
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


       /* holder.vehidetails.setOnClickListener(new View.OnClickListener() {
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
        });*/

        holder.vehidetails.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent i = new Intent(activity, VehicleDetails.class);
                bundle.putString("vehicle_id", mMainList.get(holder.getAdapterPosition()).getVehicleId());
                i.putExtras(bundle);
                activity.startActivity(i, options.toBundle());
            }
        });
        holder.editleads.setOnClickListener(new OnClickListener() {
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

        holder.mBroadcast.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent i = new Intent(activity, MyBroadcastGroupsActivity.class);
                //     bundle.putString("vehicle_id", mMainList.get(holder.getAdapterPosition()).getVehicleId());
                b.putString("title", mMainList.get(holder.getAdapterPosition()).getTitle());
                b.putString("price", mMainList.get(holder.getAdapterPosition()).getPrice());
                b.putString("category", mMainList.get(holder.getAdapterPosition()).getCategory());
                b.putString("brand", mMainList.get(holder.getAdapterPosition()).getManufacturer());
                b.putString("model", mMainList.get(holder.getAdapterPosition()).getModel());
                b.putString("image", mMainList.get(holder.getAdapterPosition()).getImages());
                b.putString("rto_city", mMainList.get(holder.getAdapterPosition()).getRtoCity());
                b.putString("manufacture_year", mMainList.get(holder.getAdapterPosition()).getYearOfManufacture());
                b.putString("kms", mMainList.get(holder.getAdapterPosition()).getKmsRunning());
                i.putExtras(b);
                activity.startActivity(i, options.toBundle());
            }
        });

        holder.mQuotation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog openDialog = new Dialog(activity);
                openDialog.setContentView(R.layout.get_quotation);
                openDialog.setTitle("Fill Form For Quotation");
                final TextView titleText = (TextView) openDialog.findViewById(R.id.txtTitle);
                final EditText edtResPrice = (EditText) openDialog.findViewById(R.id.edtReservedPrice);
                final EditText edtDate = (EditText) openDialog.findViewById(R.id.edtDate);
                final Spinner mGroupsSpinner = (Spinner) openDialog.findViewById(R.id.spinnergroup);

                Button sendQuotation = (Button) openDialog.findViewById(R.id.btnSend);

                /*Spinner to get groups*/

                try {
                    if (mConnectionDetector.isConnectedToInternet()) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(activity.getString(R.string.base_url))
                                .addConverterFactory(GsonConverterFactory.create())
                                .client(initLog().build())
                                .build();

                        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                        Call<ProfileGroupResponse> add = serviceApi._autokattaProfileGroup(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null));
                        add.enqueue(new Callback<ProfileGroupResponse>() {
                            @Override
                            public void onResponse(Call<ProfileGroupResponse> call, Response<ProfileGroupResponse> response) {
                                if (response.isSuccessful()) {
                                    mGrouplist.clear();
                                    mGrouplist1.clear();
                                    parsedData.clear();
                                    mGrouplist.add("Select Group");
                                    ProfileGroupResponse mProfilegroup = (ProfileGroupResponse) response.body();
                                    for (ProfileGroupResponse.MyGroup groupresponse : mProfilegroup.getSuccess().getMyGroups()) {
                                        groupresponse.setId(groupresponse.getId());
                                        groupresponse.setTitle(groupresponse.getTitle());
                                        mGrouplist.add(groupresponse.getTitle());
                                        mGrouplist1.put(groupresponse.getTitle(), groupresponse.getId());
                                    }
                                    parsedData.addAll(mGrouplist);
                                    if (activity != null) {
                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, parsedData);
                                        //  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        mGroupsSpinner.setAdapter(adapter);
                                    }
                                    mGroupsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            if (position != 0) {
                                                groupid = mGrouplist1.get(parsedData.get(position));
                                                groupname = parsedData.get(position);

                                                System.out.println("group id::" + groupid);
                                                System.out.println("group name::" + groupname);
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });

                                } else {
                                    CustomToast.customToast(activity, activity.getString(R.string._404));
                                }
                            }

                            @Override
                            public void onFailure(Call<ProfileGroupResponse> call, Throwable t) {

                            }

                        });
                    } else
                        CustomToast.customToast(activity.getApplicationContext(), activity.getString(R.string.no_internet));
                } catch (Exception e) {
                    e.printStackTrace();
                }


                titleText.setText(mMainList.get(holder.getAdapterPosition()).getTitle() + " of Category "
                        + mMainList.get(holder.getAdapterPosition()).getCategory());

                edtDate.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            edtDate.setInputType(InputType.TYPE_NULL);
                            edtDate.setError(null);
                            new SetMyDateAndTime("date", edtDate, activity);
                        }
                        return false;
                    }
                });
                sendQuotation.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        String strTitle = titleText.getText().toString();
                        String strPrice = edtResPrice.getText().toString();
                        String deadlineDate = edtDate.getText().toString();

                        openDialog.dismiss();
                    }
                });
                openDialog.show();
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

        ImageView vehicleimage;
        TextView edittitles, editprices, editcategorys, editbrands, editmodels, editleads, edituploadedon, editmfgyr, editkms, editrto, editlocation, editregNo;
        Button vehidetails, btnnotify, delete, mEnquiry, mQuotation;
        CardView mcardView;
        RelativeLayout mBroadcast;


        VehicleHolder(View itemView) {
            super(itemView);

            edittitles = (TextView) itemView.findViewById(R.id.edittitle);
            editprices = (TextView) itemView.findViewById(R.id.editprice);
            editcategorys = (TextView) itemView.findViewById(R.id.editcategory);
            editbrands = (TextView) itemView.findViewById(R.id.editbrand);
            editmodels = (TextView) itemView.findViewById(R.id.editmodel);
            editleads = (TextView) itemView.findViewById(R.id.editleads);
            edituploadedon = (TextView) itemView.findViewById(R.id.edituploadedon);
            vehicleimage = (ImageView) itemView.findViewById(R.id.vehiprofile);
            delete = (Button) itemView.findViewById(R.id.delete);
            vehidetails = (Button) itemView.findViewById(R.id.vehibtndetails);
            btnnotify = (Button) itemView.findViewById(R.id.btnnotify);
            mEnquiry = (Button) itemView.findViewById(R.id.Enquiry);
            editmfgyr = (TextView) itemView.findViewById(R.id.year);
            editkms = (TextView) itemView.findViewById(R.id.km_hrs);
            //edithrs=(TextView)itemView.findViewById(R.id.km_hrs);
            editrto = (TextView) itemView.findViewById(R.id.RTO);
            editlocation = (TextView) itemView.findViewById(R.id.location);
            editregNo = (TextView) itemView.findViewById(R.id.registrationNo);
            mcardView = (CardView) itemView.findViewById(R.id.card_view);
            mBroadcast = (RelativeLayout) itemView.findViewById(R.id.relativebroadcast);


            mQuotation = (Button) itemView.findViewById(R.id.quotation);

        }
    }

    /***
     * Retrofit Logs
     ***/
    private OkHttpClient.Builder initLog() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging).readTimeout(90, TimeUnit.SECONDS);
        return httpClient;
    }
}
