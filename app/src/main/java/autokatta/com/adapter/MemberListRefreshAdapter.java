package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import autokatta.com.groups.MemberDetailTabs;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetGroupContactsResponse;
import autokatta.com.view.GroupTabs;
import autokatta.com.view.OtherProfile;
import autokatta.com.view.UserProfile;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

/**
 * Created by ak-001 on 25/3/17.
 */

public class MemberListRefreshAdapter extends RecyclerView.Adapter<MemberListRefreshAdapter.MyViewHolder> implements RequestNotifier {
    private Activity mActivity;
    private List<GetGroupContactsResponse.Success> mItemList = new ArrayList<>();
    private String mCallFrom;
    private String myContact;
    private String mGroupId;
    private ApiCall mApiCall;
    private MyViewHolder mView;
    private ConnectionDetector mTestConnection;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mName, mContact, mVehicleCount, mAdmin;
        ImageView mCall, mProfilePic;
        Button mOption;
        RelativeLayout mRelativeLayout;

        MyViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.names);
            mContact = (TextView) itemView.findViewById(R.id.contact);
            mAdmin = (TextView) itemView.findViewById(R.id.txtadmin);
            mVehicleCount = (TextView) itemView.findViewById(R.id.vehicle_cnt);
            mCall = (ImageView) itemView.findViewById(R.id.cal_cnt);
            mProfilePic = (ImageView) itemView.findViewById(R.id.pro_pic);
            mOption = (Button) itemView.findViewById(R.id.delete_member);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative);
        }
    }

    public MemberListRefreshAdapter(Activity mActivity1, String GroupId, List<GetGroupContactsResponse.Success> mItemList, String mCallfrom) {
        this.mActivity = mActivity1;
        mGroupId = GroupId;
        this.mItemList = mItemList;
        this.mCallFrom = mCallfrom;
        mApiCall = new ApiCall(mActivity, this);
        mTestConnection = new ConnectionDetector(mActivity);
        myContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference),
                Context.MODE_PRIVATE).getString("loginContact", "");

    }

    @Override
    public MemberListRefreshAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_group_member_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MemberListRefreshAdapter.MyViewHolder holder, final int position) {
        mView = holder;

        holder.mName.setText(mItemList.get(position).getUsername());
        holder.mContact.setText(mItemList.get(position).getContact());
        holder.mAdmin.setText(mItemList.get(position).getMember());
        holder.mVehicleCount.setText(mItemList.get(position).getVehiclecount());

        //Set Profile Photo
        if (mItemList.get(position).getDp().equalsIgnoreCase("") || mItemList.get(position).getDp().equalsIgnoreCase(null)
                || mItemList.get(position).getDp().equalsIgnoreCase("null")) {
            holder.mProfilePic.setBackgroundResource(R.drawable.hdlogo);
        } else {
            Glide.with(mActivity)
                    .load("http://autokatta.com/mobile/profile_profile_pics/" + mItemList.get(position).getDp())
                    .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .override(110, 100)
                    .into(holder.mProfilePic);
        }

        if (holder.mAdmin.getText().toString().equals("member")) {
            holder.mAdmin.setVisibility(View.INVISIBLE);
        }
        if (holder.mContact.getText().toString().equals(mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference),
                Context.MODE_PRIVATE).getString("loginContact", ""))) {
            holder.mCall.setVisibility(View.INVISIBLE);
        }

        /*String groupName = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("group_type", "");*/
        if (!mItemList.get(position).getMember().equals("Unknown")) {
            //If no is in mobile contact, number is invisible
            holder.mContact.setVisibility(View.GONE);
            //If type is admin
            if (mItemList.get(position).getMember().equals("Admin")) {
                //if MyGroups
                if (mCallFrom.equalsIgnoreCase("MyGroups") && mItemList.get(position).getContact()
                        .equalsIgnoreCase(myContact)) {
                    holder.mName.setText("You");
                    holder.mContact.setVisibility(View.GONE);
                    holder.mOption.setVisibility(View.VISIBLE);
                }
                //if Other Groups
                else if (!mCallFrom.equalsIgnoreCase("MyGroups")) {
                    if (mItemList.get(position).getContact()
                            .equalsIgnoreCase(myContact)) {
                        holder.mName.setText("You");
                        holder.mContact.setVisibility(View.GONE);
                    } else
                        holder.mName.setText(mItemList.get(position).getUsername());
                }
            }
            if (mItemList.get(position).getContact().equalsIgnoreCase(myContact)) {
                holder.mName.setText("You");
                holder.mContact.setVisibility(View.GONE);

            }
        } else if (mItemList.get(position).getUsername().equals("Unknown")) {
            if (mItemList.get(position).getMember().equals("Admin")) {
                //If MyGroups
                if (mCallFrom.equalsIgnoreCase("MyGroups") && mItemList.get(position).getContact()
                        .equalsIgnoreCase(myContact)) {
                    holder.mName.setText("You");
                    holder.mContact.setVisibility(View.GONE);
                    holder.mOption.setVisibility(View.VISIBLE);
                }
                //If OtherGroup
                else if (!mCallFrom.equalsIgnoreCase("MyGroups")) {
                    if (mItemList.get(position).getContact().equalsIgnoreCase(myContact)) {
                        holder.mName.setText("You");
                        holder.mContact.setVisibility(View.GONE);
                    } else
                        holder.mName.setText(mItemList.get(position).getUsername());
                }
            } else
                holder.mAdmin.setVisibility(View.INVISIBLE);

            if (mItemList.get(position).getContact().equalsIgnoreCase(myContact)) {
                holder.mName.setText("You");
                holder.mContact.setVisibility(View.GONE);
            }
        }

        if (mCallFrom != null) {
            if (!mCallFrom.equals("MyGroups")) {
                if (holder.mName.getText().toString().equals("You") && mItemList.get(position).getMember().equals("member")) {
                    holder.mOption.setText("Leave");
                } else {
                    holder.mOption.setVisibility(View.INVISIBLE);
                }
            }
            if (mCallFrom.equals("MyGroups")) {
                if (holder.mName.getText().toString().equals("You")) {
                    holder.mOption.setText("Leave");
                } else if (!holder.mName.getText().toString().equals("You") && mItemList.get(position).getMember().equals("Admin")) {
                    holder.mOption.setVisibility(View.INVISIBLE);
                } else {
                    holder.mOption.setText("Options");
                }
            }
        }
        holder.mCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                call(mItemList.get(position).getContact());
            }
        });

        /*if (mCallFrom != "profile") {
            mCallFrom = "groups";
        }*/
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*member details tabs*/
                Bundle bundle = new Bundle();
                bundle.putString("Rcontact", holder.mContact.getText().toString());
                bundle.putString("grouptype", mCallFrom);
                bundle.putString("className", "MemberListRefreshAdapter");
                bundle.putString("bundle_GroupId", mGroupId);
                bundle.putString("bundle_UserName", mItemList.get(position).getUsername());

                MemberDetailTabs memberDetailTabs = new MemberDetailTabs();
                memberDetailTabs.setArguments(bundle);
                ((FragmentActivity) mActivity).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.profile_groups_container, memberDetailTabs, "MemberList")
                        .addToBackStack("MemberList")
                        .commit();
            }
        });

        holder.mProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mContact.getText().toString().equals(myContact)) {
                    Intent i = new Intent(mActivity, UserProfile.class);
                    mActivity.startActivity(i);
                    Log.e("You", "->");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("contactOtherProfile", holder.mContact.getText().toString());
                    bundle.putString("action", "otherProfile");
                    Log.i("Contact", "->" + holder.mContact.getText().toString());
                    Intent mOtherProfile = new Intent(mActivity, OtherProfile.class);
                    mOtherProfile.putExtras(bundle);
                    mActivity.startActivity(mOtherProfile);

                }
            }
        });


        holder.mOption.setOnClickListener(new View.OnClickListener() {
            String nextContact, rmContact;

            @Override
            public void onClick(final View v) {
                final CharSequence[] options;
                String tx = "", action = "";
                if (holder.mName.getText().toString().equals("You")) {
                    tx = "Leave";
                    options = new CharSequence[]{tx, "Cancel"};
                } else if (holder.mAdmin.getText().toString().equals("Admin")) {
                    tx = "Remove Member";
                    action = "";
                    options = new CharSequence[]{"Cancel"};

                } else {
                    tx = "Remove Member";
                    action = "Admin";
                    options = new CharSequence[]{tx, "Make an admin", "Cancel"};
                }


                final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("Action");
                final String finalAction = action;
                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int item) {

                        if (options[item].equals("Remove Member") || options[item].equals("Leave")) {
                            new AlertDialog.Builder(mActivity)
                                    //.setTitle(alertText)
                                    .setMessage("Are you sure ?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            rmContact = holder.mContact.getText().toString();
                                            if (mCallFrom.equals("MyGroups")) {
                                                if (rmContact.equals(myContact)) {
                                                    if (mItemList.size() != 1)
                                                        nextContact = mItemList.get(position + 1).getContact();
                                                    //nextContact = next[1];
                                                    //}
                                                }
                                            }

                                            // if(!nextContact.equals("")||!nextContact.equals("null")||!nextContact.equals(null))
                                            //new DeleteMembers().execute();
                                            DeleteMembers(position, nextContact, rmContact);
                                            mItemList.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, mItemList.size());
//                                else
//                                    Snackbar.make(v,"please delete group",Snackbar.LENGTH_SHORT).show();

                                        }
                                    })

                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();

                        } else if (options[item].equals("Make an admin") || options[item].equals("Remove from Admin")) {
                            rmContact = holder.mContact.getText().toString();
                            makeAdmin(rmContact, finalAction);

                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }

                });
                builder.show();
            }

        });
    }

    private void DeleteMembers(int position, String nextContact, String rmContact) {
        if (mTestConnection.isConnectedToInternet()) {
            mApiCall.DeleteGroupMembers(mGroupId, mCallFrom, rmContact, myContact, nextContact, String.valueOf(mItemList.size()));
            /*mItemList.remove(position);
            notifyDataSetChanged();*/

        } else {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        }
    }

    private void makeAdmin(String contact, String action) {
        if (mTestConnection.isConnectedToInternet()) {
            mApiCall.makeGroupAdmin(mGroupId, contact, action);
        } else {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        }

    }

    //Calling Functionality
    private void call(String contact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact));
        try {
            mActivity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
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
            CustomToast.customToast(mActivity, mActivity.getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "memberlistfrgmentrefreshadapter");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("success_admin")) {
                CustomToast.customToast(mActivity, "Admin Successful");
                Intent intent = new Intent(mActivity, GroupTabs.class);
                /*intent.putExtra("grouptype", "MyGroup");
                intent.putExtra("className", "MemberListRefreshAdapter");
                intent.putExtra("bundle_GroupId", mGroupId);*/

                mActivity.startActivity(intent);
                mActivity.finish();
            } else if (str.equals("success_1")) {
                CustomToast.customToast(mActivity, "remove successfully");
            } else {
                CustomToast.customToast(mActivity, "left successfully");
                Intent intent = new Intent(mActivity, GroupTabs.class);
                mActivity.startActivity(intent);
                mActivity.finish();
            }


        } else
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
    }
}
