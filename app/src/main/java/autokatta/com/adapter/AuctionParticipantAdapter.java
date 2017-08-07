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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.SocketTimeoutException;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.AuctionParticipantsResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 6/4/17.
 */

public class AuctionParticipantAdapter extends RecyclerView.Adapter<AuctionParticipantAdapter.AuctionAdapter> implements RequestNotifier {

    Activity mActivity;
    private List<AuctionParticipantsResponse.Success> mParticipantList;
    private String keyword = "";
    private int strAuctionId = 0;

    public AuctionParticipantAdapter(Activity activity, int strAuctionId, List<AuctionParticipantsResponse.Success> participantList) {
        mActivity = activity;
        mParticipantList = participantList;
        this.strAuctionId = strAuctionId;
    }

    @Override
    public AuctionParticipantAdapter.AuctionAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_auction_participant, parent, false);

        return new AuctionAdapter(v);
    }

    @Override
    public void onBindViewHolder(final AuctionParticipantAdapter.AuctionAdapter holder, int position) {

        final AuctionParticipantsResponse.Success obj = mParticipantList.get(position);

        holder.Username.setText(obj.getUsername());
        holder.Location.setText(obj.getLocation());
        holder.Profession.setText(obj.getProfession());
        holder.Auction_Limit.setText("50000/-");


        if (!obj.getProfilePhoto().equals(null) || !obj.getProfilePhoto().equals("") || !obj.getProfilePhoto().equals("null")) {

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

        holder.Reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "Clicked", Toast.LENGTH_SHORT).show();
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

        holder.ConfirmLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mParticipantList.size();
    }

    static class AuctionAdapter extends RecyclerView.ViewHolder {
        TextView Username, Location, Profession, Subrofession, Auction_Limit;
        ImageView Profilepic, Call;
        EditText Increase_Limit;
        Button Reminder, AddBlacklist, RemoveBacklist, ConfirmLimit;

        AuctionAdapter(View itemView) {
            super(itemView);

            Username = (TextView) itemView.findViewById(R.id.name);
            Location = (TextView) itemView.findViewById(R.id.location);
            Profession = (TextView) itemView.findViewById(R.id.usertype);
            Auction_Limit = (TextView) itemView.findViewById(R.id.limit);
            Increase_Limit = (EditText) itemView.findViewById(R.id.inclimit);
            Reminder = (Button) itemView.findViewById(R.id.reminder_for_limit);
            AddBlacklist = (Button) itemView.findViewById(R.id.blacklist);
            RemoveBacklist = (Button) itemView.findViewById(R.id.removeblacklist);
            ConfirmLimit = (Button) itemView.findViewById(R.id.btnLimit);
            // Subrofession = (TextView) itemView.findViewById(R.id.groupname);
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
            System.out.println("No Activity Found For Call in Car Details Fragment\n");
        }
    }

    private void addToBlacklist(String rContact) {
        ApiCall mApiCall = new ApiCall(mActivity, this);
        mApiCall.addRemoveFromBlacklist(mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "")/*, strAuctionId*/, rContact, keyword, "Auction");
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
            Log.i("Check Class-", "Auction Participants Adapter");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

        if (str != null) {
            if (str.equals("success")) {
                if (keyword.equals("blacklist")) {
                    CustomToast.customToast(mActivity, "Add To Blacklist");
                } else {
                    CustomToast.customToast(mActivity, "Remove from blacklist");
                }
            }
        } else
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));

    }
}
