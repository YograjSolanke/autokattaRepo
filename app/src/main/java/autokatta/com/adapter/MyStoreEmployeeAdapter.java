package autokatta.com.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.other.InventoryDashboard;
import autokatta.com.response.StoreEmployeeResponse;
import autokatta.com.view.AddEmployeeActivity;
import retrofit2.Response;

/**
 * Created by ak-004 on 14/11/17
 */

public class MyStoreEmployeeAdapter extends RecyclerView.Adapter<MyStoreEmployeeAdapter.YoHolder> implements RequestNotifier {
    private Activity mActivity;
    private List<StoreEmployeeResponse.Success.Employee> mEmpList = new ArrayList<>();
    ConnectionDetector mConnectionDetector;
    String myContact;

    private YoHolder yoHolder;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    static class YoHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mEmpName, mEmpContact;
        ImageView mCall, mEdit;
        Button mRemove, mInventory;

        YoHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.adapter_mystoreListCard_view);
            mEmpName = (TextView) itemView.findViewById(R.id.textName);
            mEmpContact = (TextView) itemView.findViewById(R.id.textContact);
            mCall = (ImageView) itemView.findViewById(R.id.call);
            mRemove = (Button) itemView.findViewById(R.id.btnRemove);
            mInventory = (Button) itemView.findViewById(R.id.btnInventory);
            //mEnquiries = (Button) itemView.findViewById(R.id.btnEnquiries);
            mEdit = (ImageView) itemView.findViewById(R.id.edit);
        }
    }

    @Override
    public int getItemCount() {
        return mEmpList.size();
    }

    public MyStoreEmployeeAdapter(Activity mActivity1, List<StoreEmployeeResponse.Success.Employee> mItemList) {
        try {
            this.mActivity = mActivity1;
            this.mEmpList = mItemList;
            mConnectionDetector = new ConnectionDetector(mActivity);
        } catch (ClassCastException c) {
            c.printStackTrace();
        }
    }

    @Override
    public MyStoreEmployeeAdapter.YoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_store_employee_adapter, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new YoHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyStoreEmployeeAdapter.YoHolder holder, int position) {
        myContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).
                getString("loginContact", "");
        yoHolder = holder;
        holder.mEmpName.setText(mEmpList.get(position).getName());
        holder.mEmpContact.setText(mEmpList.get(position).getContactNo());

        if (mEmpList.get(holder.getAdapterPosition()).getDeleteStatus().equalsIgnoreCase("Yes")) {

            holder.mRemove.setText("Removed");
        }
        holder.mInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mBundle = new Bundle();
                mBundle.putString("bundle_contact", holder.mEmpContact.getText().toString());
                mBundle.putInt("bundle_storeId", mEmpList.get(holder.getAdapterPosition()).getStoreID());
                Intent mIntent = new Intent(mActivity, InventoryDashboard.class);
                mIntent.putExtras(mBundle);
                mActivity.startActivity(mIntent);
            }
        });

        holder.mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = holder.mEmpContact.getText().toString();
                call(contact);
            }
        });

        holder.mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.mRemove.getText().equals("Removed"))
                    CustomToast.customToast(mActivity, "Already Deleted");
                else {
                    int emp_id = mEmpList.get(holder.getAdapterPosition()).getStoreEmplyeeID();
                    deleteEmployee(emp_id);
                }
            }
        });

        holder.mEdit.setOnClickListener(new View.OnClickListener() {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);

            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intentAddEmp = new Intent(mActivity, AddEmployeeActivity.class);
                bundle.putInt("id", mEmpList.get(holder.getAdapterPosition()).getStoreEmplyeeID());
                bundle.putString("name", mEmpList.get(holder.getAdapterPosition()).getName());
                bundle.putString("contact", mEmpList.get(holder.getAdapterPosition()).getContactNo());
                bundle.putString("designation", mEmpList.get(holder.getAdapterPosition()).getDesignation());
                bundle.putString("description", mEmpList.get(holder.getAdapterPosition()).getDescription());
                bundle.putInt("store_id", mEmpList.get(holder.getAdapterPosition()).getStoreID());
                bundle.putString("permission", mEmpList.get(holder.getAdapterPosition()).getPermission());
                bundle.putString("deletstatus", mEmpList.get(holder.getAdapterPosition()).getDeleteStatus());
                bundle.putString("keyword", "update");
                intentAddEmp.putExtras(bundle);
                mActivity.startActivity(intentAddEmp, options.toBundle());
            }
        });
    }


    private void deleteEmployee(int empId) {
        ApiCall apiCall = new ApiCall(mActivity, this);
        apiCall.updateDeleteEmployee(empId, "", "", "", "", "",
                "Delete");
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
        } else {
            Log.i("Check Class-", "MyStoreList Adapter");
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.startsWith("Success_Deleted")) {
                CustomToast.customToast(mActivity, "Employee deleted");
                yoHolder.mRemove.setText("Removed");
                mEmpList.get(yoHolder.getAdapterPosition()).setDeleteStatus("Yes");
                notifyDataSetChanged();
                //  yoHolder.mRemove.setClickable(false);
            }
        }
    }

    //Calling Functionality
    private void call(String StoreContact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + StoreContact));
        try {
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mActivity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Group contact adapter \n");
        }
    }
}
