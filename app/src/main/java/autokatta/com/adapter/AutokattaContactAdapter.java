package autokatta.com.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.database.DbOperation;
import autokatta.com.generic.Base64;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.Db_AutokattaContactResponse;
import autokatta.com.view.ChatActivity;
import autokatta.com.view.GroupsActivity;
import autokatta.com.view.OtherProfile;
import autokatta.com.view.UserProfile;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

import static android.view.View.GONE;

/**
 * Created by ak-003 on 28/3/17.
 */

public class AutokattaContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable, RequestNotifier {

    private Activity mActivity;
    private List<Db_AutokattaContactResponse> contactdata = new ArrayList<>();
    private List<Db_AutokattaContactResponse> contactdata_copy = new ArrayList<>();
    private ItemFilter mFilter;
    private String myContact = "";
    private ApiCall apicall;
    private String Rcontact = "";
    private String[] strGroupIds;
    private String[] groupTitleArray;

    class YoHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        ImageView imgProfile;
        TextView mTextName, mTextNumber, mTextStatus;
        Button btnFollow, btnUnfollow, btnsendmsg, btnGroups;

        private YoHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.adapter_autokatta_contactCard_view);
            imgProfile = (ImageView) itemView.findViewById(R.id.profileImg);
            mTextName = (TextView) itemView.findViewById(R.id.txtname);
            mTextNumber = (TextView) itemView.findViewById(R.id.txtnumber);
            mTextStatus = (TextView) itemView.findViewById(R.id.txtstatus);
            btnFollow = (Button) itemView.findViewById(R.id.btnfollow);
            btnUnfollow = (Button) itemView.findViewById(R.id.btnunfollow);
            btnUnfollow.setVisibility(GONE);
            btnsendmsg = (Button) itemView.findViewById(R.id.btnsendmsg);
            btnGroups = (Button) itemView.findViewById(R.id.btnGroups);
        }

    }

    public AutokattaContactAdapter(Activity mActivity, List<Db_AutokattaContactResponse> contactdata) {
        try {
            this.mActivity = mActivity;
            this.contactdata = contactdata;
            contactdata_copy = contactdata;
            apicall = new ApiCall(this.mActivity, this);
            setHasStableIds(true);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_autokatta_contact, parent, false);
        return new YoHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final YoHolder yoHolder = (YoHolder) holder;

        myContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "");
        yoHolder.mTextName.setText(contactdata.get(yoHolder.getAdapterPosition()).getUsername());
        yoHolder.mTextNumber.setText(contactdata.get(yoHolder.getAdapterPosition()).getContact());
        yoHolder.setIsRecyclable(false);
        if (contactdata.get(yoHolder.getAdapterPosition()).getMystatus() != null && !contactdata.get(yoHolder.getAdapterPosition()).getMystatus().equals("null")) {

            /*decode string code (Getting)*/

            /* /*decode string code*//*
                    String decodedString = null;
                    byte[] data = Base64.decode(notificationList.get(position).getStatus(), Base64.DEFAULT);
                    try {
                        decodedString = new String(data, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }*/
            String decodedString = null;
            byte[] data = new byte[0];
            try {
                data = Base64.decode(contactdata.get(yoHolder.getAdapterPosition()).getMystatus());
                decodedString = new String(data, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

            yoHolder.mTextStatus.setText(decodedString);

        } else
            yoHolder.mTextStatus.setText("No Status");


        if (contactdata.get(yoHolder.getAdapterPosition()).getFollowstatus().equalsIgnoreCase("yes")) {
            /*yoHolder.btnUnfollow.setVisibility(VISIBLE);
            yoHolder.btnFollow.setVisibility(GONE);*/
            yoHolder.btnFollow.setText(mActivity.getString(R.string.unfollow));

        } else if (contactdata.get(yoHolder.getAdapterPosition()).getFollowstatus().equalsIgnoreCase("no")) {
            /*yoHolder.btnUnfollow.setVisibility(GONE);
            yoHolder.btnFollow.setVisibility(VISIBLE);*/
            yoHolder.btnFollow.setText(mActivity.getString(R.string.follow));
        }


        if (contactdata.get(yoHolder.getAdapterPosition()).getUserprofile() == null || contactdata.get(yoHolder.getAdapterPosition()).getUserprofile().equals("") ||
                contactdata.get(yoHolder.getAdapterPosition()).getUserprofile().equals("null"))
            yoHolder.imgProfile.setBackgroundResource(R.drawable.profile);
        else {
            /*
             Glide code for image uploading

             */
            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.base_image_url) + contactdata.get(yoHolder.getAdapterPosition()).getUserprofile())
                    .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .placeholder(R.drawable.logo48x48) //To show image before loading an original image.
                    //.error(R.drawable.blocked) //To show error image if problem in loading.
                    .into(yoHolder.imgProfile);
        }

       /*Send Message*/
        yoHolder.btnsendmsg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putString("sender", contactdata.get(yoHolder.getAdapterPosition()).getContact());
                b.putString("sendername", contactdata.get(yoHolder.getAdapterPosition()).getUsername());
                b.putInt("product_id", 0);
                b.putInt("service_id", 0);
                b.putInt("vehicle_id", 0);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent intent = new Intent(mActivity, ChatActivity.class);
                intent.putExtras(b);
                mActivity.startActivity(intent, options.toBundle());
            }
        });

        /*go to other profile*/
        yoHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            Bundle bundle = new Bundle();

            @Override
            public void onClick(View v) {
                bundle.putString("contactOtherProfile", contactdata.get(yoHolder.getAdapterPosition()).getContact());

                if (myContact.equalsIgnoreCase(contactdata.get(yoHolder.getAdapterPosition()).getContact())) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent i = new Intent(mActivity, UserProfile.class);
                    i.putExtras(bundle);
                    mActivity.startActivity(i, options.toBundle());

                } else {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent i = new Intent(mActivity, OtherProfile.class);
                    i.putExtras(bundle);
                    mActivity.startActivity(i, options.toBundle());

                }
            }
        });

        yoHolder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*yoHolder.btnUnfollow.setVisibility(VISIBLE);
                yoHolder.btnFollow.setVisibility(GONE);*/
                Rcontact = contactdata.get(yoHolder.getAdapterPosition()).getContact();

                if (yoHolder.btnFollow.getText().toString().equalsIgnoreCase(mActivity.getString(R.string.follow))) {
                    sendFollowerUnfollower(Rcontact, "follow");

                    yoHolder.btnFollow.setText(mActivity.getString(R.string.unfollow));
                    contactdata.get(yoHolder.getAdapterPosition()).setFollowstatus("yes");
                } else {
                    sendFollowerUnfollower(Rcontact, "unfollow");
                    yoHolder.btnFollow.setText(mActivity.getString(R.string.follow));
                    contactdata.get(yoHolder.getAdapterPosition()).setFollowstatus("no");
                }

            }
        });

        /*yoHolder.btnUnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                *//*yoHolder.btnUnfollow.setVisibility(GONE);
                yoHolder.btnFollow.setVisibility(VISIBLE);*//*
                Rcontact = contactdata.get(yoHolder.getAdapterPosition()).getContact();
                sendFollowerUnfollower(Rcontact, "unfollow");

            }
        });*/


        yoHolder.btnGroups.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                strGroupIds = new String[]{""};
                groupTitleArray = new String[]{""};
                /*if (contactdata.get(yoHolder.getAdapterPosition()).getGroupIds().isEmpty()) {
                    //yoHolder.btnGroups.setVisibility(GONE);
                } else */
                if (contactdata.get(yoHolder.getAdapterPosition()).getGroupIds().contains(",")) {
                    strGroupIds = contactdata.get(yoHolder.getAdapterPosition()).getGroupIds().split(", ");
                    groupTitleArray = contactdata.get(yoHolder.getAdapterPosition()).getGroupNames().trim().split(", ");
                } else if (!contactdata.get(yoHolder.getAdapterPosition()).getGroupIds().contains(",") &&
                        !contactdata.get(yoHolder.getAdapterPosition()).getGroupIds().isEmpty()) {
                    strGroupIds = new String[1];
                    groupTitleArray = new String[1];
                    strGroupIds[0] = contactdata.get(yoHolder.getAdapterPosition()).getGroupIds();
                    groupTitleArray[0] = contactdata.get(yoHolder.getAdapterPosition()).getGroupNames().trim();
                }

                if (!contactdata.get(yoHolder.getAdapterPosition()).getGroupIds().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                    builder.setTitle("Select Group To View");
                    builder.setItems(groupTitleArray, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {

                            Toast.makeText(mActivity, "Group" + strGroupIds[item], Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(mActivity, GroupsActivity.class);
                            i.putExtra("grouptype", "OtherGroup");
                            i.putExtra("className", "OtherProfile");

                            i.putExtra("bundle_GroupId", Integer.parseInt(strGroupIds[item]));
                            i.putExtra("bundle_GroupName", groupTitleArray[item]);
                            i.putExtra("bundle_Contact", contactdata.get(yoHolder.getAdapterPosition()).getContact());
                            mActivity.startActivity(i);
                            dialog.dismiss();


                        }
                    })
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else
                    CustomToast.customToast(mActivity, "No common groups available");
            }
        });
    }


    private void sendFollowerUnfollower(String contact, String keyword) {
        if (keyword.equalsIgnoreCase("follow")) {
            apicall.Follow(myContact, contact, "1", 0, 0, 0, 0);
        } else {
            apicall.UnFollow(myContact, contact, "1", 0, 0, 0, 0);
        }
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
                    , "AutokattaContactAdapter");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

        if (str != null) {
            if (str.equalsIgnoreCase("success_follow")) {
                CustomToast.customToast(mActivity, "Follow profile");
                notifyDataSetChanged();
                DbOperation dbAdpter = new DbOperation(mActivity);
                dbAdpter.OPEN();
                dbAdpter.updateAutoContacts(Rcontact, "yes");
                dbAdpter.CLOSE();
                /*yoHolder.btnUnfollow.setVisibility(VISIBLE);
                yoHolder.btnFollow.setVisibility(GONE);*/


            } else if (str.equalsIgnoreCase("success_unfollow")) {
                CustomToast.customToast(mActivity, "Un follow profile");
                notifyDataSetChanged();
                DbOperation dbAdpter = new DbOperation(mActivity);
                dbAdpter.OPEN();
                dbAdpter.updateAutoContacts(Rcontact, "no");
                dbAdpter.CLOSE();
                /*yoHolder.btnUnfollow.setVisibility(GONE);
                yoHolder.btnFollow.setVisibility(VISIBLE);*/


            }
        } else
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
    }

    //Calling Functionality
    private void call(String rcontact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + rcontact));
        try {
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return contactdata.size();
    }


    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ItemFilter();
        }
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Db_AutokattaContactResponse> list = contactdata_copy;

            int count = list.size();

            if (filterString.length() > 0) {
                Db_AutokattaContactResponse filterableString;
                ArrayList<Db_AutokattaContactResponse> nlist = new ArrayList<Db_AutokattaContactResponse>(count);
                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i);
                    if (filterableString.getUsername().contains(" ")) {
                        String[] arr = filterableString.getUsername().split(" ");
                        String fname = arr[0];
                        String lname = arr[1];

                        if (fname.toLowerCase().startsWith(filterString) || lname.toLowerCase().startsWith(filterString)) {
                            nlist.add(filterableString);
                        }
                    } else if (filterableString.getUsername().toLowerCase().startsWith(filterString)) {
                        nlist.add(filterableString);
                    }
                }


                results.values = nlist;
                results.count = nlist.size();
            } else {
                results.values = list;
                results.count = list.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactdata = (ArrayList<Db_AutokattaContactResponse>) results.values;
            notifyDataSetChanged();

        }

    }
}
