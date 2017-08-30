package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.database.DbOperation;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.SearchPersonResponse;
import autokatta.com.view.ChatActivity;
import autokatta.com.view.OtherProfile;
import autokatta.com.view.UserProfile;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by ak-001 on 19/4/17.
 */

public class SearchPersonAdapter extends RecyclerView.Adapter<SearchPersonAdapter.YoHolder> implements Filterable, RequestNotifier {

    private Activity mActivity;
    private List<SearchPersonResponse.Success> contactdata = new ArrayList<>();
    private List<SearchPersonResponse.Success> contactdata_copy = new ArrayList<>();
    private ItemFilter mFilter = new SearchPersonAdapter.ItemFilter();
    private String myContact = "";
    private ApiCall apicall;
    private String Rcontact = "";

    static class YoHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        ImageView imgProfile;
        TextView mTextName, mTextNumber, mTextStatus,mCity,mCompany;
        Button btnFollow, btnUnfollow, btnsendmsg;

        private YoHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.adapter_autokatta_contactCard_view);
            imgProfile = (ImageView) itemView.findViewById(R.id.profileImg);
            mTextName = (TextView) itemView.findViewById(R.id.txtname);
            mTextNumber = (TextView) itemView.findViewById(R.id.txtnumber);
            mTextStatus = (TextView) itemView.findViewById(R.id.txtstatus);
            mCompany = (TextView) itemView.findViewById(R.id.txtcompany);
            btnFollow = (Button) itemView.findViewById(R.id.btnfollow);
            btnUnfollow = (Button) itemView.findViewById(R.id.btnunfollow);
            btnsendmsg = (Button) itemView.findViewById(R.id.btnsendmsg);
            mCity = (TextView) itemView.findViewById(R.id.txtcity);
        }
    }

    public SearchPersonAdapter(Activity mActivity, List<SearchPersonResponse.Success> contactdata) {
        try {
            this.mActivity = mActivity;
            this.contactdata = contactdata;
            contactdata_copy = contactdata;
            myContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "");
            apicall = new ApiCall(this.mActivity, this);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SearchPersonAdapter.YoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_autokatta_contact, parent, false);
        return new YoHolder(v);
    }

    @Override
    public void onBindViewHolder(final SearchPersonAdapter.YoHolder holder, final int position) {
        myContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "");
        holder.mTextName.setText(contactdata.get(position).getUsername());
        holder.mTextNumber.setText(contactdata.get(position).getContact());
        holder.mCity.setVisibility(VISIBLE);
        holder.mCompany.setVisibility(VISIBLE);
        holder.mCity.setText(contactdata.get(position).getCity());
        holder.mCompany.setText(contactdata.get(position).getCompany());

        if (contactdata.get(position).getMystatus()==null)
            holder.mTextStatus.setText("No Status");
        else
        holder.mTextStatus.setText(contactdata.get(position).getMystatus());

        if (contactdata.get(position).getStatus().equals("yes")) {
            holder.btnUnfollow.setVisibility(VISIBLE);
            holder.btnFollow.setVisibility(GONE);
        }
        if (contactdata.get(position).getStatus().equals("no")) {
            holder.btnUnfollow.setVisibility(GONE);
            holder.btnFollow.setVisibility(VISIBLE);
        }

        if (contactdata.get(position).getProfilePhoto() == null || contactdata.get(position).getProfilePhoto().equals("") ||
                contactdata.get(position).getProfilePhoto().equals("null"))
            holder.imgProfile.setBackgroundResource(R.drawable.profile);
        else {
            /*
             Glide code for image uploading

             */
            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.base_image_url) + contactdata.get(position).getProfilePhoto())
                    .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .placeholder(R.drawable.logo) //To show image before loading an original image.
                    //.error(R.drawable.blocked) //To show error image if problem in loading.
                    .into(holder.imgProfile);
        }

       /* //calling Functionality
        holder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                call(contactdata.get(holder.getAdapterPosition()).getContact());

            }
        });*/

        /*Send Message*/
        holder.btnsendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putString("sender", contactdata.get(position).getContact());
                b.putString("sendername", contactdata.get(position).getUsername());
                b.putInt("product_id", 0);
                b.putInt("service_id", 0);
                b.putInt("vehicle_id", 0);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent intent = new Intent(mActivity, ChatActivity.class);
                intent.putExtras(b);
                mActivity.startActivity(intent, options.toBundle());
            }
        });

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
        } else {
            Log.i("Check Class-"
                    , "Search Person Adapter");
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
            final List<SearchPersonResponse.Success> list = contactdata_copy;
            int count = list.size();

            if (filterString != null && filterString.length() > 0) {
                final List<SearchPersonResponse.Success> nlist = new ArrayList<>(count);
                SearchPersonResponse.Success filterableString;

                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i);
                    /*if (filterableString.getUsername().toLowerCase().contains(filterString)) {
                        nlist.add(filterableString);
                    }*/

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
            contactdata = (ArrayList<SearchPersonResponse.Success>) results.values;
            notifyDataSetChanged();
        }

    }
}
