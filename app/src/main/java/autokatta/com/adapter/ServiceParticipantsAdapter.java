package autokatta.com.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.SocketTimeoutException;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ServiceMelaParticipantsResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 28/4/17.
 */

public class ServiceParticipantsAdapter extends RecyclerView.Adapter<ServiceParticipantsAdapter.ServiceAdapter> implements RequestNotifier {

    Activity mActivity;
    private List<ServiceMelaParticipantsResponse.Success> mParticipantList;
    private String keyword = "";
    private int strServiceId = 0;

    public ServiceParticipantsAdapter(Activity activity, int strServiceId, List<ServiceMelaParticipantsResponse.Success> participantList) {
        mActivity = activity;
        mParticipantList = participantList;
        this.strServiceId = strServiceId;
    }

    @Override
    public ServiceParticipantsAdapter.ServiceAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_loan_participant, parent, false);

        return new ServiceAdapter(v);
    }

    @Override
    public void onBindViewHolder(final ServiceParticipantsAdapter.ServiceAdapter holder, int position) {

        final ServiceMelaParticipantsResponse.Success obj = mParticipantList.get(position);

        holder.Username.setText(obj.getUsername());
        holder.Location.setText(obj.getLocation());
        holder.Profession.setText(obj.getProfession());


        if (obj.getProfilePhoto() != null || !obj.getProfilePhoto().equals("") || !obj.getProfilePhoto().equals("null")) {

            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.base_image_url) + obj.getProfilePhoto())
                    .bitmapTransform(new CropCircleTransformation(mActivity))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.Profilepic);

        } else
            holder.Profilepic.setBackgroundResource(R.drawable.profile);


        //Calling Functionality
        holder.Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Rcontact = holder.Contact.getText().toString();
                call(obj.getContact());
                System.out.println("Calling Image Called From vehicle image load adapter \n");
            }
        });

        if (obj.getBlackliststatus().equals("yes")) {
            holder.AddBlacklist.setVisibility(View.GONE);
            holder.RemoveBacklist.setVisibility(View.VISIBLE);
        }
        if (obj.getBlackliststatus().equals("no")) {
            holder.AddBlacklist.setVisibility(View.VISIBLE);
            holder.RemoveBacklist.setVisibility(View.GONE);
        }
        holder.AddBlacklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(mActivity)
                        .setTitle("Add To Blacklist")
                        .setMessage("Are you sure?")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                holder.AddBlacklist.setVisibility(View.GONE);
                                holder.RemoveBacklist.setVisibility(View.VISIBLE);

                                keyword = "blacklist";
                                /*Rcontact = holder.Contact.getText().toString();
                                System.out.println("Receiver Contact for blacklist:-" + Rcontact);*/
                                obj.setBlackliststatus("yes");
                                addToBlacklist(obj.getContact());
                                //  new AddToBacklist().execute();

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
        holder.RemoveBacklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.RemoveBacklist.setVisibility(View.GONE);
                holder.AddBlacklist.setVisibility(View.VISIBLE);

                keyword = "remove";
                /*Rcontact = holder.Contact.getText().toString();
                System.out.println("Receiver Contact to remove from blacklist:-" + Rcontact);*/
                obj.setBlackliststatus("no");
                addToBlacklist(obj.getContact());
                // new AddToBacklist().execute();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mParticipantList.size();
    }

    static class ServiceAdapter extends RecyclerView.ViewHolder {
        TextView Username, Location, Profession;
        ImageView Profilepic, Call;

        Button AddBlacklist, RemoveBacklist, ConfirmLimit;

        ServiceAdapter(View itemView) {
            super(itemView);

            Username = (TextView) itemView.findViewById(R.id.name);
            Location = (TextView) itemView.findViewById(R.id.location);
            Profession = (TextView) itemView.findViewById(R.id.usertype);
            AddBlacklist = (Button) itemView.findViewById(R.id.blacklist);
            RemoveBacklist = (Button) itemView.findViewById(R.id.removeblacklist);
            Profilepic = (ImageView) itemView.findViewById(R.id.userimage);
            Call = (ImageView) itemView.findViewById(R.id.call);
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

    private void addToBlacklist(String rContact) {
        ApiCall mApiCall = new ApiCall(mActivity, this);
        mApiCall.addRemoveFromBlacklist(mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "")/*, strServiceId*/, rContact, keyword, "Service");
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
        } else {
            Log.i("Check Class-", "Service Participants Adapter");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

        if (str != null) {
            if (str.equals("success")) {
                if (keyword.equals("blacklist")) {
                    CustomToast.customToast(mActivity, "Added To Blacklist");
                } else {
                    CustomToast.customToast(mActivity, "Removed from blacklist");
                }
            }
        }

    }
}