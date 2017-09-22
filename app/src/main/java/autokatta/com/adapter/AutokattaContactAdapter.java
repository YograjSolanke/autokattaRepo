package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.database.DbOperation;
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
import static android.view.View.VISIBLE;

/**
 * Created by ak-003 on 28/3/17.
 */

public class AutokattaContactAdapter extends RecyclerView.Adapter<AutokattaContactAdapter.YoHolder> implements Filterable, RequestNotifier {

    private Activity mActivity;
    private List<Db_AutokattaContactResponse> contactdata = new ArrayList<>();
    private List<Db_AutokattaContactResponse> contactdata_copy = new ArrayList<>();
    private ItemFilter mFilter;
    private String myContact = "";
    private ApiCall apicall;
    private String Rcontact = "";
    private String[] strGroupIds;
    private String[] groupTitleArray;


    static class YoHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        ImageView imgProfile, imgCall;
        TextView mTextName, mTextNumber, mTextStatus;
        Button btnFollow, btnUnfollow, btnsendmsg, btnGroups;

        private YoHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.adapter_autokatta_contactCard_view);
            imgProfile = (ImageView) itemView.findViewById(R.id.profileImg);
            imgCall = (ImageView) itemView.findViewById(R.id.callImg);
            mTextName = (TextView) itemView.findViewById(R.id.txtname);
            mTextNumber = (TextView) itemView.findViewById(R.id.txtnumber);
            mTextStatus = (TextView) itemView.findViewById(R.id.txtstatus);
            btnFollow = (Button) itemView.findViewById(R.id.btnfollow);
            btnUnfollow = (Button) itemView.findViewById(R.id.btnunfollow);
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
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AutokattaContactAdapter.YoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_autokatta_contact, parent, false);
        return new YoHolder(v);
    }

    @Override
    public void onBindViewHolder(final AutokattaContactAdapter.YoHolder holder, final int position) {
        myContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "");
        holder.mTextName.setText(contactdata.get(position).getUsername());
        holder.mTextNumber.setText(contactdata.get(position).getContact());
        holder.imgCall.setVisibility(VISIBLE);
        if (contactdata.get(position).getMystatus() != null && !contactdata.get(position).getMystatus().equals("null"))
            holder.mTextStatus.setText(contactdata.get(position).getMystatus());
        else
            holder.mTextStatus.setText("No Status");


        if (contactdata.get(position).getFollowstatus().equals("yes")) {
            holder.btnUnfollow.setVisibility(VISIBLE);
            holder.btnFollow.setVisibility(GONE);

        }
        if (contactdata.get(position).getFollowstatus().equals("no")) {
            holder.btnUnfollow.setVisibility(GONE);
            holder.btnFollow.setVisibility(VISIBLE);
        }


        if (contactdata.get(position).getUserprofile() == null || contactdata.get(position).getUserprofile().equals("") ||
                contactdata.get(position).getUserprofile().equals("null"))
            holder.imgProfile.setBackgroundResource(R.drawable.profile);
        else {
            /*
             Glide code for image uploading

             */
            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.base_image_url) + contactdata.get(position).getUserprofile())
                    .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .placeholder(R.drawable.hdlogo) //To show image before loading an original image.
                    //.error(R.drawable.blocked) //To show error image if problem in loading.
                    .into(holder.imgProfile);
        }


        //calling Functionality
        holder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(contactdata.get(holder.getAdapterPosition()).getContact());
            }
        });

       /*Send Message*/
        holder.btnsendmsg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putString("sender", contactdata.get(holder.getAdapterPosition()).getContact());
                b.putString("sendername", contactdata.get(holder.getAdapterPosition()).getUsername());
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
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            Bundle bundle = new Bundle();

            @Override
            public void onClick(View v) {
                bundle.putString("contactOtherProfile", contactdata.get(holder.getAdapterPosition()).getContact());

                if (myContact.equalsIgnoreCase(contactdata.get(holder.getAdapterPosition()).getContact())) {
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

        holder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.btnUnfollow.setVisibility(VISIBLE);
                holder.btnFollow.setVisibility(GONE);
                Rcontact = contactdata.get(holder.getAdapterPosition()).getContact();
                sendFollowerUnfollower(Rcontact, "follow");

            }
        });

        holder.btnUnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.btnUnfollow.setVisibility(GONE);
                holder.btnFollow.setVisibility(VISIBLE);
                Rcontact = contactdata.get(holder.getAdapterPosition()).getContact();
                sendFollowerUnfollower(Rcontact, "unfollow");

            }
        });




        holder.btnGroups.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                strGroupIds = new String[]{""};
                groupTitleArray = new String[]{""};
                if (contactdata.get(holder.getAdapterPosition()).getGroupIds().isEmpty()) {
                    //holder.btnGroups.setVisibility(GONE);
                } else if (contactdata.get(holder.getAdapterPosition()).getGroupIds().contains(",")) {
                    strGroupIds = contactdata.get(holder.getAdapterPosition()).getGroupIds().split(", ");
                    groupTitleArray = contactdata.get(holder.getAdapterPosition()).getGroupNames().trim().split(", ");
                } else if (!contactdata.get(holder.getAdapterPosition()).getGroupIds().contains(",") &&
                        !contactdata.get(holder.getAdapterPosition()).getGroupIds().isEmpty()) {
                    strGroupIds = new String[1];
                    groupTitleArray = new String[1];
                    strGroupIds[0] = contactdata.get(holder.getAdapterPosition()).getGroupIds();
                    groupTitleArray[0] = contactdata.get(holder.getAdapterPosition()).getGroupNames().trim();
                }

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
                        i.putExtra("bundle_Contact", contactdata.get(holder.getAdapterPosition()).getContact());
                        mActivity.startActivity(i);
                        dialog.dismiss();


                    }
                }).show();
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
                DbOperation dbAdpter = new DbOperation(mActivity);
                dbAdpter.OPEN();
                dbAdpter.updateAutoContacts(Rcontact, "yes");
                dbAdpter.CLOSE();
            } else if (str.equalsIgnoreCase("success_unfollow")) {
                CustomToast.customToast(mActivity, "Un follow profile");
                DbOperation dbAdpter = new DbOperation(mActivity);
                dbAdpter.OPEN();
                dbAdpter.updateAutoContacts(Rcontact, "no");
                dbAdpter.CLOSE();
            }
        } else
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
    }

    //Calling Functionality
    private void call(String rcontact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + rcontact));
        try {
            mActivity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Car Details Fragment\n");
        }
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
