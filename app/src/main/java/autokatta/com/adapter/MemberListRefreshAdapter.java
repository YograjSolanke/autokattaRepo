package autokatta.com.adapter;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
import autokatta.com.groups_container.MemberContainer;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetGroupContactsResponse;
import autokatta.com.view.OtherProfile;
import autokatta.com.view.UserProfile;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

/**
 * Created by ak-001 on 25/3/17.
 */

public class MemberListRefreshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RequestNotifier {
    private Activity mActivity;
    private List<GetGroupContactsResponse.Success> mItemList = new ArrayList<>();
    private List<Boolean> mCheckList = new ArrayList<>();
    private String mCallFrom;
    private String bundle_GroupName;
    private String myContact;
    private int mGroupId;
    private ApiCall mApiCall;
    private ConnectionDetector mTestConnection;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mName, mContact, mVehicleCount, mAdmin, mproductcnt, mServicecnt;
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
            mproductcnt = (TextView) itemView.findViewById(R.id.edtproductcnt);
            mServicecnt = (TextView) itemView.findViewById(R.id.edtservicecnt);
        }
    }

    public MemberListRefreshAdapter(Activity mActivity1, int GroupId, List<GetGroupContactsResponse.Success> mItemList,
                                    String mCallfrom, String bundle_GroupName) {
        this.mActivity = mActivity1;
        mGroupId = GroupId;
        this.mItemList = mItemList;
        this.mCallFrom = mCallfrom;
        this.bundle_GroupName = bundle_GroupName;
        mApiCall = new ApiCall(mActivity, this);
        mTestConnection = new ConnectionDetector(mActivity);
        setHasStableIds(true);
        mCheckList.clear();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_group_member_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        final MyViewHolder holder = (MyViewHolder) holder1;

        myContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference),
                Context.MODE_PRIVATE).getString("loginContact", "");
        holder.mName.setText(mItemList.get(holder.getAdapterPosition()).getUsername());
        holder.mContact.setText(mItemList.get(holder.getAdapterPosition()).getContact());
        holder.mAdmin.setText(mItemList.get(holder.getAdapterPosition()).getMember());
        holder.mVehicleCount.setText(String.valueOf(mItemList.get(holder.getAdapterPosition()).getVehiclecount()));
        holder.mproductcnt.setText(String.valueOf(mItemList.get(holder.getAdapterPosition()).getProductcount()));
        holder.mServicecnt.setText(String.valueOf(mItemList.get(holder.getAdapterPosition()).getServicecount()));

        //Set Profile Photo
        if (mItemList.get(holder.getAdapterPosition()).getDp() == null ||
                mItemList.get(holder.getAdapterPosition()).getDp().equalsIgnoreCase("") ||
                mItemList.get(holder.getAdapterPosition()).getDp().equalsIgnoreCase(null)
                || mItemList.get(holder.getAdapterPosition()).getDp().equalsIgnoreCase("null")) {
            holder.mProfilePic.setBackgroundResource(R.drawable.logo48x48);
        } else {
            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.base_image_url) + mItemList.get(holder.getAdapterPosition()).getDp())
                    .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .override(110, 100)
                    .into(holder.mProfilePic);
        }

        if (holder.mAdmin.getText().toString().equals("member")) {
            holder.mAdmin.setVisibility(View.GONE);
        }
        if (holder.mContact.getText().toString().equals(mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference),
                Context.MODE_PRIVATE).getString("loginContact", ""))) {
            holder.mCall.setVisibility(View.GONE);
        }

        /*String groupName = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("group_type", "");*/
        if (!mItemList.get(holder.getAdapterPosition()).getUsername().equals("Unknown")) {
            //If no is in mobile contact, number is invisible
            holder.mContact.setVisibility(View.GONE);
            //If type is admin
            if (mItemList.get(holder.getAdapterPosition()).getMember().equals("Admin")) {
                //if MyGroups
                if (mCallFrom.equalsIgnoreCase("MyGroups") && mItemList.get(holder.getAdapterPosition()).getContact()
                        .equalsIgnoreCase(myContact)) {
                    holder.mName.setText(mActivity.getString(R.string.You));
                    holder.mContact.setVisibility(View.GONE);
                    holder.mOption.setVisibility(View.VISIBLE);
                }
                //if Other Groups
                else if (!mCallFrom.equalsIgnoreCase("MyGroups")) {
                    if (mItemList.get(holder.getAdapterPosition()).getContact()
                            .equalsIgnoreCase(myContact)) {
                        holder.mName.setText(mActivity.getString(R.string.You));
                        holder.mContact.setVisibility(View.GONE);
                    } else
                        holder.mName.setText(mItemList.get(holder.getAdapterPosition()).getUsername());
                }
            }
            if (mItemList.get(holder.getAdapterPosition()).getContact().equalsIgnoreCase(myContact)) {
                holder.mName.setText(mActivity.getString(R.string.You));
                holder.mContact.setVisibility(View.GONE);

            }
        } else if (mItemList.get(holder.getAdapterPosition()).getUsername().equals("Unknown")) {
            if (mItemList.get(holder.getAdapterPosition()).getMember().equals("Admin")) {
                //If MyGroups
                if (mCallFrom.equalsIgnoreCase("MyGroups") && mItemList.get(holder.getAdapterPosition()).getContact()
                        .equalsIgnoreCase(myContact)) {
                    holder.mName.setText(mActivity.getString(R.string.You));
                    holder.mContact.setVisibility(View.GONE);
                    holder.mOption.setVisibility(View.VISIBLE);
                }
                //If OtherGroup
                else if (!mCallFrom.equalsIgnoreCase("MyGroups")) {
                    if (mItemList.get(holder.getAdapterPosition()).getContact().equalsIgnoreCase(myContact)) {
                        holder.mName.setText(mActivity.getString(R.string.You));
                        holder.mContact.setVisibility(View.GONE);
                    } else
                        holder.mName.setText(mItemList.get(holder.getAdapterPosition()).getUsername());
                }
            } else
                holder.mAdmin.setVisibility(View.GONE);

            if (mItemList.get(holder.getAdapterPosition()).getContact().equalsIgnoreCase(myContact)) {
                holder.mName.setText(mActivity.getString(R.string.You));
                holder.mContact.setVisibility(View.GONE);
            }
        }

        if (mCallFrom != null) {
            if (!mCallFrom.equals("MyGroups")) {
                if (holder.mName.getText().toString().equals("You") && mItemList.get(holder.getAdapterPosition()).getMember().equals("member")) {
                    holder.mOption.setText(mActivity.getString(R.string.Leave));
                } else {
                    holder.mOption.setVisibility(View.GONE);
                }
            }
            if (mCallFrom.equals("MyGroups")) {
                if (holder.mName.getText().toString().equals("You")) {
                    holder.mOption.setText(mActivity.getString(R.string.Leave));
                } else if (!holder.mName.getText().toString().equals("You") && mItemList.get(holder.getAdapterPosition()).getMember().equals("Admin")) {
                    holder.mOption.setVisibility(View.GONE);
                } else {
                    holder.mOption.setText(mActivity.getString(R.string.Options));
                }
            }
        }
        /*Requested Member check if admin*/
        if (mItemList.get(holder.getAdapterPosition()).getContact().equalsIgnoreCase(myContact) &&
                mItemList.get(holder.getAdapterPosition()).getMember().equalsIgnoreCase("Admin")) {
            mCheckList.add(true);
        } else {
            mCheckList.add(false);
        }

        holder.mCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                call(mItemList.get(holder.getAdapterPosition()).getContact());
            }
        });

        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*member details tabs*/
                Bundle bundle = new Bundle();
                bundle.putString("Rcontact", holder.mContact.getText().toString());
                bundle.putString("grouptype", mCallFrom);
                bundle.putString("className", "MemberListRefreshAdapter");
                bundle.putInt("bundle_GroupId", mGroupId);

                if (mItemList.get(holder.getAdapterPosition()).getContact().equalsIgnoreCase(myContact))
                    bundle.putString("bundle_UserName", "You");
                else
                    bundle.putString("bundle_UserName", mItemList.get(holder.getAdapterPosition()).getUsername());

                MemberDetailTabs memberDetailTabs = new MemberDetailTabs();
                memberDetailTabs.setArguments(bundle);
                ((FragmentActivity) mActivity).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.memberFrame, memberDetailTabs, "MemberList")
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
            String nextContact = "", rmContact;

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
                                                    if (mItemList.size() > 1) {
                                                        if (holder.getAdapterPosition() == mItemList.size() - 1) {
                                                            nextContact = mItemList.get(holder.getAdapterPosition() - 1).getContact();
                                                            System.out.println("nextcontact-" + nextContact);
                                                        } else if (holder.getAdapterPosition() < mItemList.size() - 1) {
                                                            nextContact = mItemList.get(holder.getAdapterPosition() + 1).getContact();
                                                            System.out.println("nextcontact+" + nextContact);
                                                        }
                                                    }


                                                }
                                            }

                                            // if(!nextContact.equals("")||!nextContact.equals("null")||!nextContact.equals(null))
                                            //new DeleteMembers().execute();
                                            DeleteMembers(holder.getAdapterPosition(), nextContact, rmContact);
//                                            mItemList.remove(holder.getAdapterPosition());
//                                            notifyItemRemoved(holder.getAdapterPosition());
//                                            notifyItemRangeChanged(holder.getAdapterPosition(), mItemList.size());
//                                            notifyDataSetChanged();

                                        }
                                    })

                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();

                        } else if (options[item].equals("Make an admin") || options[item].equals("Remove from Admin")) {
                            new AlertDialog.Builder(mActivity)
                                    //.setTitle(alertText)
                                    .setMessage("Are you sure want to make admin?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            rmContact = holder.mContact.getText().toString();
                                            makeAdmin(rmContact, finalAction);
                                            holder.mOption.setVisibility(View.GONE);
                                            holder.mAdmin.setText(mActivity.getString(R.string.admin));
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
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
            Log.i("next", "->" + nextContact);
            mApiCall.DeleteGroupMembers(mGroupId, mCallFrom, rmContact, myContact, nextContact, String.valueOf(mItemList.size()));
            mItemList.remove(position);
            notifyDataSetChanged();

        } else {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        }
    }

    private void makeAdmin(String contact, String action) {
        if (mTestConnection.isConnectedToInternet()) {
            // mView.mOption.setVisibility(View.GONE);
            mApiCall.makeGroupAdmin(mGroupId, contact, action);
        } else {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        }

    }

    //Calling Functionality
    private void call(String contact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact));
        try {
            if (ActivityCompat.checkSelfPermission(mActivity, permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
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
                    , "MemberListRefreshAdapter");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("success_admin")) {
                CustomToast.customToast(mActivity, "Admin Created");
                Intent intent = new Intent(mActivity, MemberContainer.class);
                intent.putExtra("grouptype", "MyGroups");
                intent.putExtra("className", "MyAdapter");
                intent.putExtra("bundle_GroupId", mGroupId);
                intent.putExtra("bundle_GroupName", bundle_GroupName);

                mActivity.startActivity(intent);
                mActivity.finish();
                //notifyDataSetChanged();
            } else if (str.startsWith("success_1")) {
                CustomToast.customToast(mActivity, "Removed");
            } else {
                CustomToast.customToast(mActivity, "Left");
                /*Intent intent = new Intent(mActivity, GroupTabs.class);
                mActivity.startActivity(intent);*/
                mActivity.finish();
            }
        } else
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
    }
}
