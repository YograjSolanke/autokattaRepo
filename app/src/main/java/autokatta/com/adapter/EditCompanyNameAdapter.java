package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
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
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetCompaniesResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 26/10/17
 */

public class EditCompanyNameAdapter extends RecyclerView.Adapter<EditCompanyNameAdapter.MyViewHolder> implements RequestNotifier, Filterable {
    Activity mActivity;
    private List<GetCompaniesResponse.Success> mList = new ArrayList<>();
    private List<GetCompaniesResponse.Success> filteredData = new ArrayList<>();
    CustomFilter filter;
    private Button mAdd;
    private String mNewCompany;
    ApiCall mApiCall;

    public EditCompanyNameAdapter(Activity activity, List<GetCompaniesResponse.Success> list, Button add) {
        this.mActivity = activity;
        this.mList = list;
        this.filteredData = list;
        this.mAdd = add;
        mApiCall = new ApiCall(mActivity, this);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        public MyViewHolder(View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.names);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.edit_profile_adapter, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.Name.setText(mList.get(position).getCompanyName());
        holder.Name.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiCall.updateProfile(mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), MODE_PRIVATE)
                                .getInt("loginregistrationid", 0), "", "", "", "",
                        "", mList.get(position).getCompanyName(), "", "", "", "",
                        "", "", "Company");
            }
        });
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
            if (str.equals("success_update_Company")) {
                CustomToast.customToast(mActivity, "Company Added");
                mActivity.finish();
            } else {
                CustomToast.customToast(mActivity, "New Company Added");
                mActivity.finish();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
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
            mNewCompany = charSequence.toString();
            try {
                if (charSequence != null && charSequence.length() > 0) {
                    List<GetCompaniesResponse.Success> filterResults = new ArrayList<>();
                    for (GetCompaniesResponse.Success item : filteredData) {
                        if (item.getCompanyName().toUpperCase().startsWith(charSequence.toString().toUpperCase())) {
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
                mList = (List<GetCompaniesResponse.Success>) filterResults.values;
                EditCompanyNameAdapter.this.notifyDataSetChanged();
            } else {
                Toast.makeText(mActivity, "No record found Want to Add ??", Toast.LENGTH_SHORT).show();
                mAdd.setVisibility(View.VISIBLE);
                mAdd.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mApiCall.addNewCompany(mNewCompany);
                    }
                });
            }
        }
    }
}
