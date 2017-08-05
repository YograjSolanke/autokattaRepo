package autokatta.com.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BlacklistMemberResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 1/4/17.
 */

public class BlacklistMemberAdapter extends RecyclerView.Adapter<BlacklistMemberAdapter.MemberHolder> implements RequestNotifier {
    Activity activity;
    List<BlacklistMemberResponse.Success> mMainlist = new ArrayList<>();
    ApiCall apicall;

    public BlacklistMemberAdapter(Activity activity, List<BlacklistMemberResponse.Success> successList) {
        this.activity = activity;
        this.mMainlist = successList;
        apicall = new ApiCall(activity, this);
    }


    @Override
    public BlacklistMemberAdapter.MemberHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blacklist_member_adapter, parent, false);
        MemberHolder holder = new MemberHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final BlacklistMemberAdapter.MemberHolder holder, int position) {
        holder.UserName.setText(mMainlist.get(position).getUsername());
        holder.UserNumber.setText(mMainlist.get(position).getBlacklistContact());
        //Set User Profile pic
        if (mMainlist.get(position).getUserimage().equals("null") || mMainlist.get(position).getUserimage().equals(null) ||
                mMainlist.get(position).getUserimage().equals("")) {
            holder.UserPic.setBackgroundResource(R.drawable.profile);
        }
        if (!mMainlist.get(position).getUserimage().equals("null") || !mMainlist.get(position).getUserimage().equals(null) ||
                !mMainlist.get(position).getUserimage().equals("")) {
            Glide.with(activity)
                    .load(activity.getString(R.string.base_image_url) + mMainlist.get(position).getUserimage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(100, 100)
                    .into(holder.UserPic);
        }

        //Calling event
        holder.UserCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(mMainlist.get(holder.getAdapterPosition()).getBlacklistContact());
            }
        });

        //Remove functionality
        holder.RemBlacklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myContact = activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
                apicall.removeFromBlacklist(myContact, mMainlist.get(holder.getAdapterPosition()).getBlacklistContact(), "remove");
                mMainlist.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMainlist.size();
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("success")) {
            } else
                CustomToast.customToast(activity, activity.getString(R.string.no_response));
        }
    }

    static class MemberHolder extends RecyclerView.ViewHolder {
        ImageView UserPic, UserCall;
        TextView UserName, UserNumber;
        Button RemBlacklist;
        MemberHolder(View view) {
            super(view);
            UserName = (TextView) view.findViewById(R.id.txtname);
            UserNumber = (TextView) view.findViewById(R.id.txtnumber);
            UserPic = (ImageView) view.findViewById(R.id.userimage);
            UserCall = (ImageView) view.findViewById(R.id.callimage);
            RemBlacklist = (Button) view.findViewById(R.id.btnremove);
        }
    }


    //Calling Functionality
    private void call(String UserContact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + UserContact));
        try {
            activity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in My Blacklisted Adapter \n");
        }
    }
}
