package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import autokatta.com.R;
import autokatta.com.response.BroadcastReceivedResponse;
import autokatta.com.view.AddManualEnquiry;
import autokatta.com.view.ChatActivity;
import autokatta.com.view.OtherProfile;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-005 on 10/4/17.
 */

public class BussinessMsgSendersAdapter extends RecyclerView.Adapter<BussinessMsgSendersAdapter.MyViewHolder> {

    private Activity mActivity;
    private List<BroadcastReceivedResponse.Success> mItemList = new ArrayList<>();
    private String msender, msendername, mKeyword, mTitle, mPrice, mCategory, mBrand, mModel, mImage, lastmsg, time;
    private int product_id = 0, service_id = 0, vehicle_id = 0;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView msgFrom, msgFromCnt, lastMsg, lastMsgTime;
        ImageView profile, enquiry;
        CardView mCardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            msgFrom = (TextView) itemView.findViewById(R.id.msgFrom1);
            msgFromCnt = (TextView) itemView.findViewById(R.id.msgFromCnt);
            lastMsg = (TextView) itemView.findViewById(R.id.msgText);
            lastMsgTime = (TextView) itemView.findViewById(R.id.msgTime);
            profile = (ImageView) itemView.findViewById(R.id.profile);
            enquiry = (ImageView) itemView.findViewById(R.id.enquiry);
            mCardView = (CardView) itemView.findViewById(R.id.mCardview);

        }

    }

    public BussinessMsgSendersAdapter(Activity mActivity, List<BroadcastReceivedResponse.Success> mItemList, int product_id, int service_id, int vehicle_id, String keyword, String title, String price, String category, String brand, String model, String image) {
        this.mActivity = mActivity;
        this.mItemList = mItemList;
        this.product_id = product_id;
        this.service_id = service_id;
        this.vehicle_id = vehicle_id;
        this.mKeyword = keyword;
        this.mCategory = category;
        this.mTitle = title;
        this.mBrand = brand;
        this.mPrice = price;
        this.mModel = model;
        this.mImage = image;

    }

    @Override
    public BussinessMsgSendersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bussiness_msg_senders, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final BussinessMsgSendersAdapter.MyViewHolder holder, final int position) {

        msender = mItemList.get(position).getSender();
        msendername = mItemList.get(position).getSendername();
        lastmsg = mItemList.get(position).getMessage();
        time = mItemList.get(position).getDate();

        holder.msgFromCnt.setText(msender);
        holder.msgFrom.setText(msendername);
        holder.lastMsg.setText(lastmsg);
        holder.lastMsgTime.setText(time);


        Glide.with(mActivity)
                .load(mActivity.getString(R.string.base_image_url)+mItemList.get(position).getProfileImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CropCircleTransformation(mActivity))
                .into(holder.profile);

        try {
            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
            //format of date coming from services
            //DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a",
                    Locale.getDefault());
            inputFormat.setTimeZone(utc);
            //format of date which want to show
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a",
                    Locale.getDefault());
            outputFormat.setTimeZone(utc);

            Date date = inputFormat.parse(time);
            String output = outputFormat.format(date);
            System.out.println("jjj" + output);
            holder.lastMsgTime.setText(output);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putString("sender", holder.msgFromCnt.getText().toString());
                b.putString("sendername", holder.msgFrom.getText().toString());
                b.putString("senderLocation", mItemList.get(position).getLocation());
                b.putInt("product_id", product_id);
                b.putInt("service_id", service_id);
                b.putInt("vehicle_id", vehicle_id);

                ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent intent = new Intent(mActivity, ChatActivity.class);
                intent.putExtras(b);
                mActivity.startActivity(intent, options.toBundle());

            }
        });

        holder.enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions option = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Bundle b = new Bundle();
                b.putString("sender", holder.msgFromCnt.getText().toString());
                b.putString("sendername", holder.msgFrom.getText().toString());
                b.putString("keyword", mKeyword);
                b.putString("category", mCategory);
                b.putString("title", mTitle);
                b.putString("brand", mBrand);
                b.putString("model", mModel);
                b.putString("price", mPrice);
                b.putString("image", mImage);
                b.putInt("vehicleid", vehicle_id);
                b.putString("classname", "bussinessmsgsendersadapter");

                Intent intent = new Intent(mActivity, AddManualEnquiry.class);
                intent.putExtras(b);
                mActivity.startActivity(intent, option.toBundle());

            }
        });
        holder.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("contactOtherProfile", mItemList.get(holder.getAdapterPosition()).getSender());
                ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent i = new Intent(mActivity, OtherProfile.class);
                i.putExtras(bundle);
                mActivity.startActivity(i, options.toBundle());


            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}

