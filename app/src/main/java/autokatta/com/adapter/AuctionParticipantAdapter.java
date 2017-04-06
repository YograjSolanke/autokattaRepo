package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

import autokatta.com.R;
import autokatta.com.response.AuctionParticipantsResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-003 on 6/4/17.
 */

public class AuctionParticipantAdapter extends RecyclerView.Adapter<AuctionParticipantAdapter.AuctionAdapter> {

    Activity mActivity;
    private List<AuctionParticipantsResponse.Success> mParticipantList;
    private String strAuctionId;

    public AuctionParticipantAdapter(Activity activity, String strAuctionId, List<AuctionParticipantsResponse.Success> participantList) {
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
                    .load("http://autokatta.com/mobile/profile_profile_pics/" + obj.getProfilePhoto())
                    .bitmapTransform(new CropCircleTransformation(mActivity))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.Profilepic);

        } else
            holder.Profilepic.setBackgroundResource(R.drawable.profile);


        //Calling Functionality
        holder.Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Rcontact = holder.Contact.getText().toString();
                call();*/
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
/*        holder.AddBlacklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(mActivity)
                        .setTitle("Add To Blacklist")
                        .setMessage("Are you sure?")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                holder.AddBlacklist.setVisibility(View.GONE);
                                holder.RemoveBacklist.setVisibility(View.VISIBLE);

                                keyword="blacklist";
                                Rcontact = holder.Contact.getText().toString();
                                System.out.println("Receiver Contact for blacklist:-"+Rcontact);
                                obj.setBlackliststatus("yes");
                                addToBlacklist();
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

                keyword="remove";
                Rcontact = holder.Contact.getText().toString();
                System.out.println("Receiver Contact to remove from blacklist:-"+Rcontact);
                obj.setBlackliststatus("no");
                addToBlacklist();
                // new AddToBacklist().execute();
            }
        });*/

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

    public class AuctionAdapter extends RecyclerView.ViewHolder {
        TextView Username, Contact, Location, Profession, Subrofession, Auction_Limit;
        ImageView Profilepic, Call;
        EditText Increase_Limit;
        Button Reminder, AddBlacklist, RemoveBacklist, ConfirmLimit;

        public AuctionAdapter(View itemView) {
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
}
