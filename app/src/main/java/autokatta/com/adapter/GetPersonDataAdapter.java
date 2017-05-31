package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.GetPersonDataResponse;

/**
 * Created by ak-001 on 11/5/17.
 */

public class GetPersonDataAdapter extends RecyclerView.Adapter<GetPersonDataAdapter.PersonData> {

    Activity mActivity;
    List<GetPersonDataResponse.Success> list = new ArrayList<>();

    public GetPersonDataAdapter(Activity mActivity, List<GetPersonDataResponse.Success> list) {
        this.mActivity = mActivity;
        this.list = list;
    }

    static class PersonData extends RecyclerView.ViewHolder {

        TextView mPersonName, mContact, mAddress, mFollowUpDate, mDiscussion;
        ImageView mProfilePic;

        PersonData(View itemView) {
            super(itemView);
            mPersonName = (TextView) itemView.findViewById(R.id.name);
            mContact = (TextView) itemView.findViewById(R.id.contact);
            mAddress = (TextView) itemView.findViewById(R.id.address);
            mFollowUpDate = (TextView) itemView.findViewById(R.id.follow_up);
            mDiscussion = (TextView) itemView.findViewById(R.id.discussion);
            mProfilePic = (ImageView) itemView.findViewById(R.id.user_image);
        }
    }

    @Override
    public PersonData onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_person_data, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new PersonData(v);
    }

    @Override
    public void onBindViewHolder(PersonData holder, int position) {
        holder.mPersonName.setText(list.get(position).getUsername());
        holder.mContact.setText(list.get(position).getContactNo());
        holder.mAddress.setText(list.get(position).getCity());
        holder.mFollowUpDate.setText(list.get(position).getNextFollowupDate());

        if (list.get(position).getProfilePic().equals("") || list.get(position).getProfilePic().equals("null")
                || list.get(position).getProfilePic().equals(null)) {
            holder.mProfilePic.setBackgroundResource(R.drawable.hdlogo);
        } else {
            String used_pic = "http://autokatta.com/mobile/profile_profile_pics/" + list.get(position).getProfilePic();
            Glide.with(mActivity)
                    .load(used_pic)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.mProfilePic);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
