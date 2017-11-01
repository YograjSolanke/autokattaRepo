package autokatta.com.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment_profile.EditAllAbout;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetDesignationResponse;
import autokatta.com.response.GetDesignationResponse.Success;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 26/10/17.
 */

public class EditDesignationAdapter extends RecyclerView.Adapter<EditDesignationAdapter.MyViewHolder> implements RequestNotifier, Filterable {
    Activity mActivity;
    List<GetDesignationResponse.Success> mList = new ArrayList<>();
    ApiCall mApiCall;
    private List<GetDesignationResponse.Success> filteredData = new ArrayList<>();
    CustomFilter filter;
    String mNewDesignation;
    Button mAdd;


    public EditDesignationAdapter(Activity Activity, List<Success> mList,Button add) {
        this.mActivity = Activity;
        this.mList = mList;
        this.mAdd=add;
        this.filteredData = mList;
        mApiCall = new ApiCall(mActivity, this);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mNames;


        public MyViewHolder(View itemView) {
            super(itemView);
            mNames = (TextView) itemView.findViewById(R.id.names);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_designation_adapter, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mNames.setText(mList.get(position).getDesignationName());
        holder.mNames.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiCall.updateProfile(mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), MODE_PRIVATE).getInt("loginregistrationid", 0), "", "", "", "", "", "", mList.get(position).getDesignationName(), "", "", "", "", "", "Designation");
                mActivity.startActivity(new Intent(mActivity, EditAllAbout.class));
                mActivity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        if (!str.equals("")) {
            if (str.equals("success_update_Designation")) {
                CustomToast.customToast(mActivity, "Designation Added");
                mActivity.finish();
            }else
            {
                CustomToast.customToast(mActivity, "New Designation Added");
                mActivity.finish();
            }
        }
    }

    /*
  Filter for stock type
   */
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter();
        }
        return filter;
    }

    /* **
       * Filter Class
       **/
    private class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            mNewDesignation = charSequence.toString();
            try {
                if (charSequence != null && charSequence.length() > 0) {
                    List<GetDesignationResponse.Success> filterResults = new ArrayList<>();
                    for (GetDesignationResponse.Success item : filteredData) {
                        if (item.getDesignationName().toUpperCase().startsWith(charSequence.toString().toUpperCase())) {
                            filterResults.add(item);
                        }
                    }
                    results.count = filterResults.size();
                    results.values = filterResults;
                } else {
                    results.values = mList;
                    results.count = mList.size();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            if (filterResults.count > 0) {
                mList = (List<GetDesignationResponse.Success>) filterResults.values;
                EditDesignationAdapter.this.notifyDataSetChanged();
            } else {
                Toast.makeText(mActivity, "No record found", Toast.LENGTH_SHORT).show();
                Log.i("Error", "->");
                mAdd.setVisibility(View.VISIBLE);
                mAdd.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mApiCall.addNewDesignation(mNewDesignation);
                        mActivity.finish();
                    }
                });
            }
        }
    }


}
