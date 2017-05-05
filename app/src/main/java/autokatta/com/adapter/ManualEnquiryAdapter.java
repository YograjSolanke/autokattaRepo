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
import autokatta.com.response.ManualEnquiryResponse;

/**
 * Created by ak-001 on 5/5/17.
 */

public class ManualEnquiryAdapter extends RecyclerView.Adapter<ManualEnquiryAdapter.ManualEnquiryViewHolder> {

    Activity mActivity;
    List<ManualEnquiryResponse.Success> mItemList = new ArrayList<>();

    public ManualEnquiryAdapter(Activity mActivity, List<ManualEnquiryResponse.Success> mItemList) {
        this.mActivity = mActivity;
        this.mItemList = mItemList;
    }

    static class ManualEnquiryViewHolder extends RecyclerView.ViewHolder {

        TextView mCustomerName, mContact, mAddress, mFullAddress, mInventoryType, mDiscussion, mFollowUpDate;
        public ManualEnquiryViewHolder(View itemView) {
            super(itemView);
            mCustomerName = (TextView) itemView.findViewById(R.id.name);
            mContact = (TextView) itemView.findViewById(R.id.contact);
            mAddress = (TextView) itemView.findViewById(R.id.address);
            mFullAddress = (TextView) itemView.findViewById(R.id.full_address);
            mInventoryType = (TextView) itemView.findViewById(R.id.inventory);
            mDiscussion = (TextView) itemView.findViewById(R.id.discussion);
            mFollowUpDate = (TextView) itemView.findViewById(R.id.follow_up);
        }
    }

    @Override
    public ManualEnquiryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_manual_enquiry, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ManualEnquiryAdapter.ManualEnquiryViewHolder vh = new ManualEnquiryAdapter.ManualEnquiryViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ManualEnquiryViewHolder holder, int position) {
        holder.mCustomerName.setText(mItemList.get(position).getCustName());
        holder.mContact.setText(mItemList.get(position).getCustContact());
        holder.mAddress.setText(mItemList.get(position).getCustAddress());
        holder.mFullAddress.setText(mItemList.get(position).getCustFullAddress());
        holder.mInventoryType.setText(mItemList.get(position).getCustInventoryType());
        holder.mDiscussion.setText(mItemList.get(position).getDiscussion());
        holder.mFollowUpDate.setText(mItemList.get(position).getNextFollowupDate());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
