package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
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
import autokatta.com.fragment.GroupNextTabFragment;
import autokatta.com.response.ModelGroups;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-001 on 24/3/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Activity mActivity;
    Context mContext;
    private List<ModelGroups> mItemList = new ArrayList<>();
    String GroupType;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mGroupTitleID, mMemberCount, mEditMemberCount, mVehicleCount, mEditVehicleCount;
        ImageView mGroupIcon, mGroupEdit, mGroupDelete;

        MyViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mGroupTitleID = (TextView) itemView.findViewById(R.id.group_title_id);
            mMemberCount = (TextView) itemView.findViewById(R.id.member_count);
            mEditMemberCount = (TextView) itemView.findViewById(R.id.edtmember_count);
            mVehicleCount = (TextView) itemView.findViewById(R.id.vehicle_count);
            mEditVehicleCount = (TextView) itemView.findViewById(R.id.edit_vehicle_count);
            mGroupIcon = (ImageView) itemView.findViewById(R.id.group_icon);
            mGroupEdit = (ImageView) itemView.findViewById(R.id.group_edit);
            mGroupDelete = (ImageView) itemView.findViewById(R.id.group_delete);
        }
    }

    public MyAdapter(Activity mActivity, List<ModelGroups> mItemList, String GroupType) {
        try {
            this.mActivity = mActivity;
            this.mContext = mActivity;
            this.mItemList = mItemList;
            this.GroupType = GroupType;
        } catch (ClassCastException c) {
            c.printStackTrace();
        }
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_card_joined_group, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, final int position) {
        holder.mGroupTitleID.setText(mItemList.get(position).getTitle());
        holder.mEditMemberCount.setText(mItemList.get(position).getGroupCount());
//        holder.mEditVehicleCount.setText(mItemList.get(position).getVehicleCount());

        /***Card Click Listener***/
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit()
                        .putString("group_id", mItemList.get(position).getId()).apply();
                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit()
                        .putString("group_type", GroupType).apply();
                FragmentManager fragmentManager = ((FragmentActivity) mActivity).getSupportFragmentManager();
                FragmentTransaction mTransaction = fragmentManager.beginTransaction();
                mTransaction.replace(R.id.group_container, new GroupNextTabFragment()).commit();
            }
        });

        if (GroupType.equals("JoinedGroups")) {
            holder.mGroupEdit.setVisibility(View.GONE);
            holder.mGroupDelete.setVisibility(View.GONE);
        }
        if (mItemList.get(position).getImage().equals("") || mItemList.get(position).getImage().equals(null) ||
                mItemList.get(position).getImage().equals("null")) {
            holder.mGroupIcon.setBackgroundResource(R.mipmap.ic_launcher);
        } else {
            //mItemList.get(position).getImage() = mItemList.get(position).getImage().replaceAll(" ", "%20");
            String dppath = "http://autokatta.com/mobile/group_profile_pics/" + mItemList.get(position).getImage();
            Glide.with(mActivity)
                    .load(dppath)
                    .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    //.placeholder(R.drawable.logo) //To show image before loading an original image.
                    //.error(R.drawable.blocked) //To show error image if problem in loading.
                    .into(holder.mGroupIcon);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
