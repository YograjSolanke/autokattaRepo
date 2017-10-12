package autokatta.com.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.NewVehicleAllResponse;
import autokatta.com.response.ProfileGroupResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 12/10/17.
 */

public class NewVehicleContainerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RequestNotifier {

    private Activity mActivity;
    private List<NewVehicleAllResponse.Success.NewVehicle> mVehicleList = new ArrayList<>();
    private ConnectionDetector mConnectionDetector;
    private List<String> mGrouplist = new ArrayList<>();
    private HashMap<String, Integer> mGrouplist1 = new HashMap<>();
    private List<String> parsedData = new ArrayList<>();
    private int groupid = 0;
    private String groupname;
    ApiCall mApiCall;
    String myContact;


    public NewVehicleContainerAdapter(Activity activity, List<NewVehicleAllResponse.Success.NewVehicle> VehicleList, String myContact) {
        mActivity = activity;
        mVehicleList = VehicleList;
        mConnectionDetector = new ConnectionDetector(activity);
        mApiCall = new ApiCall(activity, this);
        this.myContact = myContact;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_new_vehicle_container, parent, false);
        return new YoHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final YoHolder mVehicleHolder = (YoHolder) holder;

        mVehicleHolder.mCategory.setText(mVehicleList.get(position).getCategoryName());
        mVehicleHolder.mSubCategory.setText(mVehicleList.get(position).getSubCategoryName());
        mVehicleHolder.mBrand.setText(mVehicleList.get(position).getBrandName());
        mVehicleHolder.mModel.setText(mVehicleList.get(position).getModelName());
        mVehicleHolder.mVersion.setText(mVehicleList.get(position).getVersionName());

        /*
        More Items click listener...
         */
        mVehicleHolder.mMoreItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = mActivity.getLayoutInflater().inflate(R.layout.custom_more_used_vehicle, null);
                ImageView mClose = (ImageView) view.findViewById(R.id.close);
                Button mEnquiry = (Button) view.findViewById(R.id.Enquiry);
                mEnquiry.setVisibility(View.GONE);
                Button mQuotation = (Button) view.findViewById(R.id.quotation);
                Button mTransferStock = (Button) view.findViewById(R.id.transfer_stock);
                mTransferStock.setVisibility(View.GONE);
                Button mSold = (Button) view.findViewById(R.id.delete);
                mSold.setVisibility(View.GONE);
                Button mViewQuote = (Button) view.findViewById(R.id.view_quotation);
                mViewQuote.setVisibility(View.GONE);
                Button mOfferRecived = (Button) view.findViewById(R.id.offerrecived);
                mOfferRecived.setVisibility(View.GONE);

                final Dialog mBottomSheetDialog = new Dialog(mActivity, R.style.MaterialDialogSheet);

                mBottomSheetDialog.setContentView(view);
                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                mBottomSheetDialog.show();

                mClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                    }
                });

                /*
                Send Quotation...
                 */
                mQuotation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                        final Dialog openDialog = new Dialog(mActivity);
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
                                        .baseUrl(mActivity.getString(R.string.base_url))
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .client(initLog().build())
                                        .build();

                                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                                Call<ProfileGroupResponse> add = serviceApi._autokattaProfileGroup(mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null));
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
                                            if (mActivity != null) {
                                                ArrayAdapter<String> adapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, parsedData);
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
                                            CustomToast.customToast(mActivity, mActivity.getString(R.string._404));
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ProfileGroupResponse> call, Throwable t) {

                                    }

                                });
                            } else
                                CustomToast.customToast(mActivity.getApplicationContext(), mActivity.getString(R.string.no_internet));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        sendQuotation.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                String strTitle = titleText.getText().toString();
                                String strPrice = edtResPrice.getText().toString();
                                String deadlineDate = edtDate.getText().toString();

                                Log.i("groupId", String.valueOf(groupid));
                                if (groupid == 0)
                                    CustomToast.customToast(mActivity, "please select Group to send quotation");
                                else {
                                    mApiCall.SendQuotation(strTitle, strPrice, deadlineDate, String.valueOf(groupid),
                                            mVehicleList.get(mVehicleHolder.getAdapterPosition()).getNewVehicleID(), myContact,
                                            "NewVehicle");
                                    System.out.println(mVehicleList.get(mVehicleHolder.getAdapterPosition()).getNewVehicleID());
                                    openDialog.dismiss();
                                }
                            }
                        });
                        openDialog.show();
                    }
                });
            }
        });

        mVehicleHolder.mDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mVehicleList.size();
    }

    private static class YoHolder extends RecyclerView.ViewHolder {

        TextView mCategory, mSubCategory, mBrand, mModel, mVersion;
        Button mDetails;
        ImageView mMoreItems;

        YoHolder(View itemView) {
            super(itemView);

            mCategory = (TextView) itemView.findViewById(R.id.editcategory);
            mSubCategory = (TextView) itemView.findViewById(R.id.editsubcategory);
            mBrand = (TextView) itemView.findViewById(R.id.editbrand);
            mModel = (TextView) itemView.findViewById(R.id.editmodel);
            mVersion = (TextView) itemView.findViewById(R.id.editversion);
            mDetails = (Button) itemView.findViewById(R.id.vehibtndetails);
            mMoreItems = (ImageView) itemView.findViewById(R.id.more_items);
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

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
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
                /*case "success":
                    //CustomToast.customToast(activity, "vehicle deleted");
                    break;
                case "success_notification":
                    CustomToast.customToast(mActivity, "notification sent");
                    break;
                case "success_added":
                    CustomToast.customToast(mActivity, "data updated");
                    break;*/
                case "sent_quotation":
                    CustomToast.customToast(mActivity, "quotation sent");
                    break;
               /* case "sold_success":
                    CustomToast.customToast(mActivity, "vehicle sold");
                    break;*/
            }
        }
    }

}
