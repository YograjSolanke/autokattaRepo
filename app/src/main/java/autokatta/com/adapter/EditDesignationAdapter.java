package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.GetDesignationResponse;
import autokatta.com.response.GetDesignationResponse.Success;

/**
 * Created by ak-005 on 26/10/17.
 */

public class EditDesignationAdapter extends RecyclerView.Adapter<EditDesignationAdapter.MyViewHolder> {
    Activity mActivity;
    List<GetDesignationResponse.Success> mList = new ArrayList<>();

    public EditDesignationAdapter(Activity mActivity, List<Success> mList) {
        this.mActivity = mActivity;
        this.mList = mList;
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mNames.setText(mList.get(position).getDesignationName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
