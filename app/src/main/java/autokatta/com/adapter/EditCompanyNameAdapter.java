package autokatta.com.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment_profile.EditAllAbout;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetCompaniesResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 26/10/17.
 */

public class EditCompanyNameAdapter extends RecyclerView.Adapter<EditCompanyNameAdapter.MyViewHolder> implements RequestNotifier {

    Activity mActivity;
    List<GetCompaniesResponse.Success>mList=new ArrayList<>();
ApiCall mApiCall;
    public EditCompanyNameAdapter(Activity activity,List<GetCompaniesResponse.Success>list) {
        this.mActivity=activity;
        this.mList=list;
        mApiCall=new ApiCall(mActivity,this);
    }


    class MyViewHolder extends RecyclerView.ViewHolder
   {
        TextView Name;
       public MyViewHolder(View itemView) {
           super(itemView);
           Name= (TextView) itemView.findViewById(R.id.names);
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
                mApiCall.updateProfile(mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), MODE_PRIVATE).getInt("loginregistrationid",0),"","","","","",mList.get(position).getCompanyName(),"","","","","","","Company");
                mActivity.startActivity(new Intent(mActivity, EditAllAbout.class));
                mActivity.finish();
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
                CustomToast.customToast(mActivity,"Company Added");
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

}
