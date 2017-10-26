package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.GetCompaniesResponse;

/**
 * Created by ak-005 on 26/10/17.
 */

public class EditCompanyNameAdapter extends RecyclerView.Adapter<EditCompanyNameAdapter.MyViewHolder> {

    Activity mActivity;
    List<GetCompaniesResponse.Success>mList=new ArrayList<>();

    public EditCompanyNameAdapter(Activity activity,List<GetCompaniesResponse.Success>list) {
        this.mActivity=activity;
        this.mList=list;
        Log.i("aaaaaaaaaaaaaaaaaa","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.Name.setText(mList.get(position).getCompanyName());
    }

   /* @Override
    public long getItemId(int i) {
        return 0;
    }
*/
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
