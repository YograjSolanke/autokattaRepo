package autokatta.com.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.initial_fragment.CreateGroupFragment;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ProfileGroupResponse;
import autokatta.com.response.StoreInventoryResponse;
import autokatta.com.view.VehicleDetails;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 18/4/17.
 */

public class StoreVehicleAdapter extends RecyclerView.Adapter<StoreVehicleAdapter.VehicleHolder> implements RequestNotifier {

    Activity activity;
    List<StoreInventoryResponse.Success.Vehicle> vehicleList = new ArrayList<>();
    String myContact, storeContact;
    String vimagename = "";
    ApiCall apiCall;
    private List<String> groupIdList = new ArrayList<>();
    private List<String> groupTitleList = new ArrayList<>();
    private String[] groupTitleArray = new String[0];
    private String[] groupIdArray = new String[0];
    ConnectionDetector connectionDetector;
    private String stringgroupids = "";
    private String stringgroupname = "";
    private int mVehicleId;
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
            holder.mLinear.setVisibility(View.VISIBLE);
        }

        //To set Date


        try {
            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
            //format of date coming from services
            DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss",
                    Locale.US);
            inputFormat.setTimeZone(utc);
            //format of date which want to show
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                    Locale.US);
            outputFormat.setTimeZone(utc);

            Date date = inputFormat.parse(obj.getDate());
            String output = outputFormat.format(date);
            System.out.println("jjj" + output);
            holder.edituploadedon.setText(output);
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
                System.out.println(activity.getString(R.string.base_image_url) + vimages.get(0));

                vimagename = activity.getString(R.string.base_image_url) + vimages.get(0);
                vimagename = vimagename.replaceAll(" ", "%20");
                try {


                    Glide.with(activity)
                            .load(activity.getString(R.string.base_image_url) + vimages.get(0))
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
                final int vehicle_id = obj.getVehicleId();

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
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, vehicleList.size());


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
                b.putInt("vehicle_id", obj.getVehicleId());
                Intent intent = new Intent(activity, VehicleDetails.class);
                intent.putExtras(b);
                activity.startActivity(intent);

            }
        });

        holder.mUploadGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getGroups();
                    mVehicleId = obj.getVehicleId();
                    /*if (groupTitleArray.length == 0) {
                        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(activity);
                        alertDialog.setTitle("No Group Found");
                        alertDialog.setMessage("Do you want to create Group...");
                        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                CreateGroupFragment createGroupFragment = new CreateGroupFragment();
                                Bundle b = new Bundle();
                                b.putString("classname", "uploadvehicle");
                                createGroupFragment.setArguments(b);

                                activity.getFragmentManager().beginTransaction()
                                        .replace(R.id.vehicle_upload_container, createGroupFragment, "Title")
                                        .addToBackStack("Title")
                                        .commit();


                            }
                        });
                        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                    } else {*/
                    //alertBoxToSelectExcelSheet(groupTitleArray);
                    //}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    /*
   Get Groups...
    */
    private void getGroups() {
        if (connectionDetector.isConnectedToInternet()) {
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
                        groupIdList.clear();
                        groupIdList.clear();
                        groupTitleList.clear();
                        ProfileGroupResponse mProfileGroupResponse = (ProfileGroupResponse) response.body();
                        for (ProfileGroupResponse.MyGroup success : mProfileGroupResponse.getSuccess().getMyGroups()) {
                            groupIdList.add(String.valueOf(success.getId()));
                            groupTitleList.add(success.getTitle());
                        }
                        groupTitleArray = groupTitleList.toArray(new String[groupTitleList.size()]);
                        groupIdArray = groupIdList.toArray(new String[groupIdList.size()]);

                        if (groupTitleArray.length == 0) {
                            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(activity);
                            alertDialog.setTitle("No Group Found");
                            alertDialog.setMessage("Do you want to create Group...");
                            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    CreateGroupFragment createGroupFragment = new CreateGroupFragment();
                                    Bundle b = new Bundle();
                                    b.putString("classname", "uploadvehicle");
                                    createGroupFragment.setArguments(b);

                                    ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.vehicle_upload_container, createGroupFragment, "Title")
                                            .addToBackStack("Title")
                                            .commit();
                                }
                            });
                            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog.show();
                        } else {
                            alertBoxToSelectExcelSheet(groupTitleArray);
                        }
                    } else {
                        CustomToast.customToast(activity, activity.getString(R.string._404));
                    }
                }

                @Override
                public void onFailure(Call<ProfileGroupResponse> call, Throwable t) {
                    t.printStackTrace();
                }

            });
        } else {
            CustomToast.customToast(activity.getApplicationContext(), activity.getString(R.string.no_internet));
        }
    }

    /*
    Alert Dialog
     */
    private void alertBoxToSelectExcelSheet(final String[] groupTitleArray) {
        final List<String> mSelectedItems = new ArrayList<>();
        mSelectedItems.clear();

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);

        // set the dialog title
        builder.setTitle("Select Groups From Following")
                .setCancelable(true)
                .setMultiChoiceItems(groupTitleArray, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            mSelectedItems.add(groupTitleArray[which]);
                        } else if (mSelectedItems.contains(groupTitleArray[which])) {
                            mSelectedItems.remove(groupTitleArray[which]);
                        }
                    }
                })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        stringgroupids = "";
                        stringgroupname = "";
                        for (int i = 0; i < mSelectedItems.size(); i++) {
                            for (int j = 0; j < groupTitleArray.length; j++) {
                                if (mSelectedItems.get(i).equals(groupTitleArray[j])) {
                                    if (stringgroupids.equals("")) {
                                        stringgroupids = groupIdList.get(j);
                                        stringgroupname = groupTitleArray[j];
                                    } else {
                                        stringgroupids = stringgroupids + "," + groupIdList.get(j);
                                        stringgroupname = stringgroupname + "," + groupTitleArray[j];
                                    }
                                }
                            }
                        }
                        setPrivacy(stringgroupids);
                        if (mSelectedItems.size() == 0) {
                            CustomToast.customToast(activity, "No Group Was Selected");
                            stringgroupids = "";
                            stringgroupname = "";
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        stringgroupids = "";
                        stringgroupname = "";
                    }

                })
                .show();
    }

    /*

     */
    private void setPrivacy(String groupId) {
        ApiCall apiCall = new ApiCall(activity, this);
        apiCall.VehiclePrivacy(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""),
                mVehicleId, groupId, "");
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
            CustomToast.customToast(activity, activity.getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_response));
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

        ImageView vehicleimage;
        TextView edittitles, editprices, editcategorys, editbrands, editmodels, editleads, edituploadedon;
        TextView Year, Location, Rto, Kms, Regno;
        Button vehidetails, delete, mUploadGroup, mUploadStore;
        LinearLayout mLinear;

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
            delete = (Button) itemView.findViewById(R.id.delete);
            mLinear = (LinearLayout) itemView.findViewById(R.id.linearbtns);
            mUploadGroup = (Button) itemView.findViewById(R.id.upload_group);
            mUploadStore = (Button) itemView.findViewById(R.id.upload_store);
            vehidetails = (Button) itemView.findViewById(R.id.vehibtndetails);


            Year = (TextView) itemView.findViewById(R.id.year);
            Kms = (TextView) itemView.findViewById(R.id.km_hrs);
            Rto = (TextView) itemView.findViewById(R.id.RTO);
            Location = (TextView) itemView.findViewById(R.id.location);
            Regno = (TextView) itemView.findViewById(R.id.registrationNo);
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
