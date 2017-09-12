package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.GetManualEnquiryPersonDataResponse;

/**
 * Created by ak-003 on 22/6/17.
 */

public class GetPersonsEnquiriesAdapter extends RecyclerView.Adapter<GetPersonsEnquiriesAdapter.EnquiriesView> {

    Activity mActivity;
    List<GetManualEnquiryPersonDataResponse.Success> list = new ArrayList<>();
    private String strId, strKeyword, strTitle,bundlecontact;

    public GetPersonsEnquiriesAdapter(Activity mActivity, List<GetManualEnquiryPersonDataResponse.Success> list, String strId, String strKeyword, String strTitle,String bundlecontact) {
        this.mActivity = mActivity;
        this.list = list;
        this.strId = strId;
        this.strKeyword = strKeyword;
        this.strTitle = strTitle;
        this.bundlecontact = bundlecontact;
    }

    class EnquiriesView extends RecyclerView.ViewHolder {
        TextView mContact, mFollowUpDate, mDiscussion, mLastEnquiry, mEnquiryStatus;
        CardView mCardView;

        EnquiriesView(View itemView) {
            super(itemView);
            mContact = (TextView) itemView.findViewById(R.id.contact);
            mFollowUpDate = (TextView) itemView.findViewById(R.id.follow_up);
            mDiscussion = (TextView) itemView.findViewById(R.id.discussion);
            mLastEnquiry = (TextView) itemView.findViewById(R.id.last_enquiry);
            mEnquiryStatus = (TextView) itemView.findViewById(R.id.enquiryStatus);
            mCardView = (CardView) itemView.findViewById(R.id.personEnquiry_card_view);
        }
    }

    @Override
    public EnquiriesView onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_get_person_enquiries, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new EnquiriesView(v);
    }

    @Override
    public void onBindViewHolder(GetPersonsEnquiriesAdapter.EnquiriesView holder, int position) {
        holder.mContact.setText(bundlecontact);
        holder.mFollowUpDate.setText(list.get(position).getNextFollowUpDate());
        holder.mLastEnquiry.setText(list.get(position).getCreatedDate());
        holder.mEnquiryStatus.setText(list.get(position).getEnquiryStatus());
        holder.mDiscussion.setText(list.get(position).getDiscussion());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
