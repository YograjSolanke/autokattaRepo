package autokatta.com.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import autokatta.com.R;
import autokatta.com.groups.ReplyGroupQuot;
import autokatta.com.response.MyVehicleQuotationListResponse;
import autokatta.com.view.OtherProfile;

/**
 * Created by ak-004 on 20/9/17.
 */

public class QuotationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;
    private List<MyVehicleQuotationListResponse.Success> QuotationList;
    private String mLoginContact;

    public QuotationListAdapter(Activity activity1, List<MyVehicleQuotationListResponse.Success> QuotationList1,
                                String mLoginContact1) {
        mActivity = activity1;
        QuotationList = QuotationList1;
        mLoginContact = mLoginContact1;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_qoutation_list, parent, false);
        return new QuotationHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        final QuotationHolder holder = (QuotationHolder) holder1;

        if (QuotationList.get(holder.getAdapterPosition()).getCustContact().equals(mLoginContact)) {
            holder.name.setText("You");
            holder.priceText.setText("Your Quote");
        } else {
            holder.callMe.setVisibility(View.VISIBLE);
            holder.name.setText(QuotationList.get(position).getCustomerName());
        }
        holder.contact.setText(QuotationList.get(position).getCustContact());
        holder.reservedPrice.setText(String.valueOf(QuotationList.get(position).getReservePrice()));
        holder.date.setText(QuotationList.get(position).getCreatedDate());
        holder.query.setText(QuotationList.get(position).getQuery());

        holder.callMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(QuotationList.get(holder.getAdapterPosition()).getCustContact());
            }
        });
        holder.nameRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mLoginContact.equals(QuotationList.get(holder.getAdapterPosition()).getCustContact())) {
                    Bundle bundle = new Bundle();
                    bundle.putString("contactOtherProfile", QuotationList.get(holder.getAdapterPosition()).getCustContact());

                    ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent i = new Intent(mActivity, OtherProfile.class);
                    i.putExtras(bundle);
                    mActivity.startActivity(i, options.toBundle());
                }
            }
        });
        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity, ReplyGroupQuot.class);
                i.putExtra("VehicleId", QuotationList.get(holder.getAdapterPosition()).getVehicleID());
                i.putExtra("GroupId", QuotationList.get(holder.getAdapterPosition()).getGroupID());
                i.putExtra("type", QuotationList.get(holder.getAdapterPosition()).getType());
                mActivity.startActivity(i);
            }
        });
    }

    private void call(String custContact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + custContact));
        System.out.println("calling started");
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
            System.out.println("No Activity Found For Call in Other Profile\n");
        }
    }

    @Override
    public int getItemCount() {
        return QuotationList.size();
    }


    private static class QuotationHolder extends RecyclerView.ViewHolder {

        TextView name, contact, reservedPrice, date, priceText, query;
        ImageView callMe, reply;
        RelativeLayout nameRelative;

        QuotationHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.setName);
            contact = (TextView) itemView.findViewById(R.id.setContact);
            reservedPrice = (TextView) itemView.findViewById(R.id.setPrice);
            priceText = (TextView) itemView.findViewById(R.id.priceText);
            date = (TextView) itemView.findViewById(R.id.setDate);
            query = (TextView) itemView.findViewById(R.id.setQuery);
            callMe = (ImageView) itemView.findViewById(R.id.call);
            reply = (ImageView) itemView.findViewById(R.id.reply);
            nameRelative = (RelativeLayout) itemView.findViewById(R.id.relative1);
        }
    }
}
