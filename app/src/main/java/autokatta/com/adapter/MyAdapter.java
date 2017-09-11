package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.groups.GroupEditFragment;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ModelGroups;
import autokatta.com.view.GroupsActivity;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by ak-001 on 24/3/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements RequestNotifier {
    private ApiCall mApiCall;
    private Activity mActivity;
    private Context mContext;
    private List<ModelGroups> mItemList = new ArrayList<>();
    private String GroupType, keyword, mGroupName, mGroupImage;
    private int mGroupid;
    private MyViewHolder view;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mGroupTitleID, mMemberCount, mEditMemberCount, mVehicleCount, mEditVehicleCount, mProductcount, mServicecount;
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
            mProductcount = (TextView) itemView.findViewById(R.id.edit_product_count);
            mServicecount = (TextView) itemView.findViewById(R.id.edit_service_count);
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_card_joined_group, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final int safePosition = holder.getAdapterPosition();
        view = holder;
        holder.mGroupTitleID.setText(mItemList.get(position).getTitle());
        holder.mEditMemberCount.setText(String.valueOf(mItemList.get(position).getGroupCount()));
        holder.mEditVehicleCount.setText(String.valueOf(mItemList.get(position).getVehicleCount()));
        holder.mServicecount.setText(String.valueOf(mItemList.get(position).getServicecount()));
        holder.mProductcount.setText(String.valueOf(mItemList.get(position).getProductcount()));

        holder.mGroupEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mGroupid = mItemList.get(holder.getAdapterPosition()).getId();
                mGroupName = mItemList.get(holder.getAdapterPosition()).getTitle();
                mGroupImage = mItemList.get(holder.getAdapterPosition()).getImage();

                GroupEditFragment frag = new GroupEditFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("bundle_GroupId", mGroupid);
                bundle.putString("bundle_name", mGroupName);
                bundle.putString("bundle_image", mGroupImage);
                frag.setArguments(bundle);

                ((FragmentActivity) mActivity).getSupportFragmentManager().beginTransaction().
                        replace(R.id.group_container, frag, "groupEdit")
                        .addToBackStack("groupEdit")
                        .commit();

            }
        });
        mApiCall = new ApiCall(mActivity, this);

        holder.mGroupDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mGroupid = mItemList.get(holder.getAdapterPosition()).getId();
                new AlertDialog.Builder(mContext)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this Group?")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

//                                groups = obj.groupId;
                                keyword = "delete";
                                //new DeleteGroup().execute();
                                mApiCall.deleteGroup(mGroupid, keyword, mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
                                mItemList.remove(safePosition);
                                notifyItemRemoved(safePosition);
                                notifyItemRangeChanged(safePosition, mItemList.size());
//  grouplist.remove(position);
//                                notifyDataSetChanged();
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }

        });

        /***Card Click Listener***/
        holder.mCardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                /*mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), MODE_PRIVATE).edit()
                        .putString("group_id", mItemList.get(position).getId()).apply();
                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), MODE_PRIVATE).edit()
                        .putString("grouptype", GroupType).apply();*/
                ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                mGroupid = mItemList.get(holder.getAdapterPosition()).getId();
                Intent intent = new Intent(mActivity, GroupsActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                //intent.putExtras();
                if (GroupType.equals("JoinedGroups")) {
                    intent.putExtra("grouptype", "JoinedGroups");
                } else {
                    intent.putExtra("grouptype", "MyGroups");
                }
                intent.putExtra("className", "MyAdapter");
                intent.putExtra("bundle_GroupId", mGroupid);
                intent.putExtra("bundle_GroupName", mItemList.get(holder.getAdapterPosition()).getTitle());
                mActivity.startActivity(intent, options.toBundle());
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
            String dppath = mActivity.getString(R.string.base_image_url) + mItemList.get(position).getImage();
            Glide.with(mActivity)
                    .load(dppath)
                    .bitmapTransform(new CropSquareTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    //.placeholder(R.drawable.logo) //To show image before loading an original image.
                    //.error(R.drawable.blocked) //To show error image if problem in loading.
                    .into(holder.mGroupIcon);
        }
    }

    public int getCount() {
        return mItemList.size();
    }

//

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }


    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "My Adapter");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("success")) {
                CustomToast.customToast(mActivity, "Group deleted");
            }
        } else {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        }
    }

}
