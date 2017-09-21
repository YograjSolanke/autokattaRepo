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
import android.util.Log;
import android.view.Gravity;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment.UploadedVehicleBuyerList;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.initial_fragment.CreateGroupFragment;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyStoreResponse;
import autokatta.com.response.MyUploadedVehiclesResponse;
import autokatta.com.response.ProfileGroupResponse;
import autokatta.com.view.AddManualEnquiry;
import autokatta.com.view.MyBroadcastGroupsActivity;
import autokatta.com.view.MyVehicleQuotationListActivity;
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
 * Created by ak-004 on 1/4/17.
 */

public class MyUploadedVehicleAdapter extends RecyclerView.Adapter<MyUploadedVehicleAdapter.VehicleHolder> implements RequestNotifier {
    Activity activity;
    List<MyUploadedVehiclesResponse.Success> mMainList;
    ApiCall apiCall;
    private String myContact;
    private int groupid;
    private String groupname;

    private List<String> mGrouplist = new ArrayList<>();
    private List<String> groupIdList = new ArrayList<>();
    private List<String> groupTitleList = new ArrayList<>();
    private String[] groupTitleArray = new String[0];
    private String[] groupIdArray = new String[0];
    private List<String> parsedData = new ArrayList<>();
    private HashMap<String, Integer> mGrouplist1 = new HashMap<>();
    private ConnectionDetector mConnectionDetector;
    private String stringgroupids = "";
    private String stringgroupname = "";
    private int mVehicleId;

    private List<String> storeIdList = new ArrayList<>();
    private List<String> storeTitleList = new ArrayList<>();
    private String[] storeTitleArray = new String[0];
    private String[] storeIdArray = new String[0];
    private String stringstoreids = "", prevGroupIds = "", prevStoreIds = "";
    private String stringstorename = "";
    private KProgressHUD hud;
    private boolean[] itemsCheckedGroups;
    private boolean[] itemsCheckedStores;

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
    public void onBindViewHolder(final MyUploadedVehicleAdapter.VehicleHolder holder, int position) {
        final List<String> vimages = new ArrayList<>();
        holder.edittitles.setText(mMainList.get(position).getTitle());
        holder.editprices.setText(mMainList.get(position).getPrice());
        holder.editcategorys.setText(mMainList.get(position).getCategory());
        holder.editbrands.setText(mMainList.get(position).getManufacturer());
        holder.editmodels.setText(mMainList.get(position).getModel());
        holder.editleads.setText(mMainList.get(position).getBuyerLeads());
        holder.delete.setVisibility(View.VISIBLE);
        holder.mLinear.setVisibility(View.VISIBLE);
        holder.editmfgyr.setText(mMainList.get(position).getYearOfManufacture());
        holder.editrto.setText(mMainList.get(position).getRtoCity());
        holder.editlocation.setText(mMainList.get(position).getLocationCity());
        holder.editregNo.setText(mMainList.get(position).getRegistrationNumber());
        myContact = activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

        if (mMainList.get(position).getKmsRunning().equals(""))
            holder.editkms.setText(mMainList.get(position).getHrsRunning());
        else
            holder.editkms.setText(mMainList.get(position).getKmsRunning());


        //To set Date
        try {
            //To set Date
            DateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            DateFormat newDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            holder.edituploadedon.setText(newDateFormat.format(inputDate.parse(mMainList.get(position).getDate())));

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
                String vimagename = activity.getString(R.string.base_image_url) + vimages.get(0);
                vimagename = vimagename.replaceAll(" ", "%20");
                try {
                    Glide.with(activity)
                            .load(activity.getString(R.string.base_image_url) + vimages.get(0))
                            .bitmapTransform(new CropCircleTransformation(activity))
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

           /*Manual Enquiry */
        holder.mEnquiry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions option = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Bundle b = new Bundle();

                b.putString("sender", "");
                b.putString("sendername", "");
                b.putString("keyword", "Used Vehicle");
                b.putString("category", mMainList.get(holder.getAdapterPosition()).getCategory());
                b.putString("title", mMainList.get(holder.getAdapterPosition()).getTitle());
                b.putString("brand", mMainList.get(holder.getAdapterPosition()).getManufacturer());
                b.putString("model", mMainList.get(holder.getAdapterPosition()).getModel());
                b.putString("price", mMainList.get(holder.getAdapterPosition()).getPrice());
                b.putString("image", vimages.get(0));
                b.putInt("id", mMainList.get(holder.getAdapterPosition()).getVehicleId());
                b.putString("classname", "myuploadedvehicleadapter");

                Intent intent = new Intent(activity, AddManualEnquiry.class);
                intent.putExtras(b);
                activity.startActivity(intent, option.toBundle());

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
                                    View view = activity.getLayoutInflater().inflate(R.layout.custom_sold_vehicle_info, null);
                                    final EditText mCustomerName = (EditText) view.findViewById(R.id.customer_name);
                                    final EditText mContact = (EditText) view.findViewById(R.id.contact);
                                    final EditText mAddress = (EditText) view.findViewById(R.id.address);
                                    final EditText mSellingPrice = (EditText) view.findViewById(R.id.selling_price);
                                    final EditText mSoldDate = (EditText) view.findViewById(R.id.sold_date);
                                    Button mSave = (Button) view.findViewById(R.id.submit);
                                    ImageView mClose = (ImageView) view.findViewById(R.id.close);

                                    final Dialog mBottomSheetDialog = new Dialog(activity, R.style.MaterialDialogSheet);
                                    mBottomSheetDialog.setContentView(view);
                                    mBottomSheetDialog.setCancelable(true);
                                    mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                                    mBottomSheetDialog.show();

                                    mClose.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mBottomSheetDialog.dismiss();
                                        }
                                    });

                                    mSave.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            apiCall.soldVehicle(mMainList.get(holder.getAdapterPosition()).getVehicleId(),
                                                    mCustomerName.getText().toString(), mContact.getText().toString(),
                                                    Integer.parseInt(mSellingPrice.getText().toString()),
                                                    mAddress.getText().toString(), mSoldDate.getText().toString());

                                            apiCall.deleteUploadedVehicle(mMainList.get(holder.getAdapterPosition()).getVehicleId(), "delete");
                                            mMainList.remove(holder.getAdapterPosition());
                                            notifyDataSetChanged();
                                        }
                                    });
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


        holder.vehidetails.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent i = new Intent(activity, VehicleDetails.class);
                bundle.putInt("vehicle_id", mMainList.get(holder.getAdapterPosition()).getVehicleId());
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
                    b.putInt("vehicle_id", mMainList.get(holder.getAdapterPosition()).getVehicleId());
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


                                    for (ProfileGroupResponse.JoinedGroup groupresponse : mProfilegroup.getSuccess().getJoinedGroups()) {
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

                        Log.i("groupId", String.valueOf(groupid));
                        if (groupid == 0)
                            CustomToast.customToast(activity, "please select Group to send quotation");
                        else {
                            apiCall.SendQuotation(strTitle, strPrice, deadlineDate, String.valueOf(groupid),
                                    mMainList.get(holder.getAdapterPosition()).getVehicleId(), myContact,
                                    "UsedVehicle");
                            System.out.println(mMainList.get(holder.getAdapterPosition()).getVehicleId());
                            openDialog.dismiss();
                        }
                    }
                });
                openDialog.show();
            }
        });

        holder.mUploadGroup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    prevGroupIds = mMainList.get(holder.getAdapterPosition()).getGroupIDs().replaceAll(" ", "");
                    int position = holder.getAdapterPosition();

                    getGroups(position);
                    mVehicleId = mMainList.get(holder.getAdapterPosition()).getVehicleId();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        holder.mUploadStore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    prevStoreIds = mMainList.get(holder.getAdapterPosition()).getStoreIDs().replaceAll(" ", "");
                    int position = holder.getAdapterPosition();
                    getStores(position);
                    mVehicleId = mMainList.get(holder.getAdapterPosition()).getVehicleId();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /*
        To transfer stock...
         */
        holder.mTransferStock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = activity.getLayoutInflater().inflate(R.layout.custom_bottom_transfer_stock, null);
                EditText txtBackup = (EditText) view.findViewById(R.id.owner_name);
                EditText txtDetail = (EditText) view.findViewById(R.id.contact_no);
                EditText txtOpen = (EditText) view.findViewById(R.id.transfer_date);
                EditText txtUninstall = (EditText) view.findViewById(R.id.reason_for_transfer);
                EditText txtUninstall1 = (EditText) view.findViewById(R.id.description);
                ImageView mClose = (ImageView) view.findViewById(R.id.close);

                final Dialog mBottomSheetDialog = new Dialog(activity, R.style.MaterialDialogSheet);
                mBottomSheetDialog.setContentView(view);
                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                mBottomSheetDialog.show();

                mClose.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                    }
                });
            }
        });

        /* To View quote for vehicle */
        holder.mViewQuote.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog openDialog = new Dialog(activity);
                openDialog.setContentView(R.layout.view_quote_group_selection);
                openDialog.setTitle("view quotation");
                final Spinner mGroupsSpinner = (Spinner) openDialog.findViewById(R.id.spinnergroup);

                Button viewQuotation = (Button) openDialog.findViewById(R.id.btnSend);
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


                                    for (ProfileGroupResponse.JoinedGroup groupresponse : mProfilegroup.getSuccess().getJoinedGroups()) {
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
                viewQuotation.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Log.i("groupId", String.valueOf(groupid));
                        if (groupid == 0)
                            CustomToast.customToast(activity, "please select Group to view quotation");
                        else {
                            Bundle bundle = new Bundle();
                            bundle.putString("Title", holder.edittitles.getText().toString());
                            bundle.putString("Category", holder.editcategorys.getText().toString());
                            bundle.putString("Brand", holder.editbrands.getText().toString());
                            bundle.putString("Model", holder.editmodels.getText().toString());
                            bundle.putString("Price", holder.editprices.getText().toString());
                            bundle.putString("Image", vimages.get(0));
                            bundle.putInt("bundle_GroupId", groupid);
                            bundle.putInt("bundle_VehicleId", mMainList.get(holder.getAdapterPosition()).getVehicleId());
                            bundle.putString("bundle_Type", "UsedVehicle");
                            bundle.putString("bundle_Contact", mMainList.get(holder.getAdapterPosition()).getContactVehicle());

                            Intent intent = new Intent(activity, MyVehicleQuotationListActivity.class);
                            intent.putExtras(bundle);
                            activity.startActivity(intent);
                            openDialog.dismiss();
                        }
                    }
                });
                openDialog.show();
            }
        });
    }

    /*
    Get Groups...
     */
    private void getGroups(final int position) {
        if (mConnectionDetector.isConnectedToInternet()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(activity.getString(R.string.base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(initLog().build())
                    .build();

            ServiceApi serviceApi = retrofit.create(ServiceApi.class);
            Call<ProfileGroupResponse> add = serviceApi._autokattaProfileGroup(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null));

            hud = KProgressHUD.create(activity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("loading groups...")
                    .setMaxProgress(100)
                    .show();

            add.enqueue(new Callback<ProfileGroupResponse>() {
                @Override
                public void onResponse(Call<ProfileGroupResponse> call, Response<ProfileGroupResponse> response) {
                    if (response.isSuccessful()) {
                        groupIdList.clear();
                        groupTitleList.clear();
                        hud.dismiss();

                        ProfileGroupResponse mProfileGroupResponse = (ProfileGroupResponse) response.body();
                        for (ProfileGroupResponse.MyGroup success : mProfileGroupResponse.getSuccess().getMyGroups()) {
                            groupIdList.add(String.valueOf(success.getId()));
                            groupTitleList.add(success.getTitle());
                        }
                        for (ProfileGroupResponse.JoinedGroup success : mProfileGroupResponse.getSuccess().getJoinedGroups()) {
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
                            itemsCheckedGroups = new boolean[groupTitleArray.length];
                            alertBoxGroups(groupTitleArray, position);
                        }
                    } else {
                        hud.dismiss();
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
    Alert Dialog For Groups
     */
    private void alertBoxGroups(final String[] groupTitleArray, final int position) {
        final List<String> mSelectedItems = new ArrayList<>();
        mSelectedItems.clear();
        String[] prearra = prevGroupIds.split(",");


        for (int i = 0; i < groupIdList.size(); i++) {
            if (Arrays.asList(prearra).contains(groupIdList.get(i))) {
                itemsCheckedGroups[i] = true;
                mSelectedItems.add(groupIdList.get(i));
            } else
                itemsCheckedGroups[i] = false;
        }

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);

        // set the dialog title
        builder.setTitle("Select Groups From Following")
                .setCancelable(true)
                .setMultiChoiceItems(groupTitleArray, itemsCheckedGroups, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            mSelectedItems.add(groupIdArray[which]);
                            itemsCheckedGroups[which] = true;
                        } else if (mSelectedItems.contains(groupIdArray[which])) {
                            mSelectedItems.remove(groupIdArray[which]);
                            itemsCheckedGroups[which] = false;
                        }
                    }
                })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        System.out.println("selected ids=" + mSelectedItems);
                        stringgroupids = "";
                        stringgroupname = "";
                        prevGroupIds = "";
                        for (int i = 0; i < mSelectedItems.size(); i++) {
                            for (int j = 0; j < groupIdArray.length; j++) {
                                if (mSelectedItems.get(i).equals(groupIdArray[j])) {
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
                        prevGroupIds = stringgroupids;
                        mMainList.get(position).setGroupIDs(prevGroupIds);
                        setPrivacy(stringgroupids, prevStoreIds);

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
    Get Store...
     */
    private void getStores(final int position) {
        if (mConnectionDetector.isConnectedToInternet()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(activity.getString(R.string.base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(initLog().build())
                    .build();

            ServiceApi serviceApi = retrofit.create(ServiceApi.class);
            Call<MyStoreResponse> add = serviceApi._autokattaGetMyStoreList(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null));

            hud = KProgressHUD.create(activity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("loading stores...")
                    .setMaxProgress(100)
                    .show();

            add.enqueue(new Callback<MyStoreResponse>() {
                @Override
                public void onResponse(Call<MyStoreResponse> call, Response<MyStoreResponse> response) {
                    if (response.isSuccessful()) {
                        storeIdList.clear();
                        storeTitleList.clear();
                        hud.dismiss();

                        MyStoreResponse mProfileGroupResponse = response.body();
                        for (MyStoreResponse.Success success : mProfileGroupResponse.getSuccess()) {
                            storeIdList.add(String.valueOf(success.getId()));
                            storeTitleList.add(success.getName());
                        }
                        storeTitleArray = storeTitleList.toArray(new String[storeTitleList.size()]);
                        storeIdArray = storeIdList.toArray(new String[storeIdList.size()]);

                        /*if (storeTitleArray.length == 0) {
                            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(activity);
                            alertDialog.setTitle("No Store Found");
                            alertDialog.setMessage("Do you want to create Store...");
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
                        } else {*/
                        itemsCheckedStores = new boolean[storeTitleArray.length];
                        alertBoxStore(storeTitleArray, position);
                        //}
                    } else {
                        hud.dismiss();
                        CustomToast.customToast(activity, activity.getString(R.string._404));
                    }
                }

                @Override
                public void onFailure(Call<MyStoreResponse> call, Throwable t) {
                    t.printStackTrace();
                }

            });
        } else {
            CustomToast.customToast(activity.getApplicationContext(), activity.getString(R.string.no_internet));
        }
    }

    /*
    Alert Dialog For Stores
     */
    private void alertBoxStore(final String[] storeTitleArray, final int position) {
        final List<String> mSelectedItems = new ArrayList<>();
        mSelectedItems.clear();
        String[] prearra = prevStoreIds.split(",");

        for (int i = 0; i < storeIdList.size(); i++) {
            if (Arrays.asList(prearra).contains(storeIdList.get(i))) {
                itemsCheckedStores[i] = true;
                mSelectedItems.add(storeIdList.get(i));
            } else
                itemsCheckedStores[i] = false;
        }

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);

        // set the dialog title
        builder.setTitle("Select Store From Following")
                .setCancelable(true)
                .setMultiChoiceItems(storeTitleArray, itemsCheckedStores, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        if (isChecked) {
                            mSelectedItems.add(storeIdArray[which]);
                            itemsCheckedStores[which] = true;
                        } else if (mSelectedItems.contains(storeIdArray[which])) {
                            mSelectedItems.remove(storeIdArray[which]);
                            itemsCheckedStores[which] = false;
                        }
                    }
                })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        stringstoreids = "";
                        stringstorename = "";
                        prevStoreIds = "";
                        for (int i = 0; i < mSelectedItems.size(); i++) {
                            for (int j = 0; j < storeIdArray.length; j++) {
                                if (mSelectedItems.get(i).equals(storeIdArray[j])) {
                                    if (stringstoreids.equals("")) {
                                        stringstoreids = storeIdList.get(j);
                                        stringstorename = storeTitleArray[j];
                                    } else {
                                        stringstoreids = stringstoreids + "," + storeIdList.get(j);
                                        stringstorename = stringstorename + "," + storeTitleArray[j];
                                    }
                                }
                            }
                        }
                        prevStoreIds = stringstoreids;
                        mMainList.get(position).setGroupIDs(prevGroupIds);
                        setPrivacy(prevGroupIds, stringstoreids);
                        if (mSelectedItems.size() == 0) {
                            CustomToast.customToast(activity, "No store Was Selected");
                            stringstoreids = "";
                            stringstorename = "";
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        stringstoreids = "";
                        stringstorename = "";
                    }

                })
                .show();
    }


    private void setPrivacy(String groupIds, String storeIds) {
        ApiCall apiCall = new ApiCall(activity, this);
        apiCall.VehiclePrivacy(activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""),
                mVehicleId, groupIds, storeIds);
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
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(activity, activity.getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "MyUploadedVehiclesAdapter");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            switch (str) {
                case "success":
                    //CustomToast.customToast(activity, "vehicle deleted");
                    break;
                case "success_notification":
                    CustomToast.customToast(activity, "notification sent");
                    break;
                case "success_added":
                    CustomToast.customToast(activity, "data updated");
                    break;
                case "sent_quotation":
                    CustomToast.customToast(activity, "quotation sent");
                    break;
                case "sold_success":
                    CustomToast.customToast(activity, "vehicle sold");
                    break;
            }
        }
    }

    static class VehicleHolder extends RecyclerView.ViewHolder {
        ImageView vehicleimage;
        TextView edittitles, editprices, editcategorys, editbrands, editmodels, editleads, edituploadedon, editmfgyr,
                editkms, editrto, editlocation, editregNo;
        Button vehidetails, btnnotify, delete, mEnquiry, mQuotation, mUploadGroup, mUploadStore, mTransferStock,
                mViewQuote;
        CardView mcardView;
        RelativeLayout mBroadcast;
        LinearLayout mLinear;

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
            mUploadGroup = (Button) itemView.findViewById(R.id.upload_group);
            mUploadStore = (Button) itemView.findViewById(R.id.upload_store);
            editmfgyr = (TextView) itemView.findViewById(R.id.year);
            editkms = (TextView) itemView.findViewById(R.id.km_hrs);
            //edithrs=(TextView)itemView.findViewById(R.id.km_hrs);
            editrto = (TextView) itemView.findViewById(R.id.RTO);
            editlocation = (TextView) itemView.findViewById(R.id.location);
            editregNo = (TextView) itemView.findViewById(R.id.registrationNo);
            mcardView = (CardView) itemView.findViewById(R.id.card_view);
            mBroadcast = (RelativeLayout) itemView.findViewById(R.id.relativebroadcast);
            mLinear = (LinearLayout) itemView.findViewById(R.id.linearbtns);
            mQuotation = (Button) itemView.findViewById(R.id.quotation);
            mTransferStock = (Button) itemView.findViewById(R.id.transfer_stock);
            mViewQuote = (Button) itemView.findViewById(R.id.view_quotation);
        }
    }

    /***
     * Retrofit Logs
     ***/
    private OkHttpClient.Builder initLog() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging).readTimeout(90, TimeUnit.SECONDS);
        return httpClient;
    }
}
