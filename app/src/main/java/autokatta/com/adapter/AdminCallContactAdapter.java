package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.view.OtherProfile;

/**
 * Created by ak-004 on 28/8/17.
 */


public class AdminCallContactAdapter extends RecyclerView.Adapter<AdminCallContactAdapter.MyViewHolder> {

    private Activity mActivity;
    private List<String> mItemList = new ArrayList<>();

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mName, mRole;
        ImageView mcall;
        CardView mCardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.textName);
            mRole = (TextView) itemView.findViewById(R.id.textRole);
            mcall = (ImageView) itemView.findViewById(R.id.call);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);

        }

    }

    public AdminCallContactAdapter(Activity mActivity, List<String> mItemList) {
        this.mActivity = mActivity;
        this.mItemList = mItemList;
    }

    @Override
    public AdminCallContactAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_contact_call_adapter, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final String[] arr = mItemList.get(position).split("-");

        holder.mName.setText(arr[2]);
        holder.mRole.setText(arr[1]);

        holder.mcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(arr[0]);

            }
        });

        holder.mCardView.setOnClickListener(new View.OnClickListener() {

            Bundle bundle = new Bundle();

            @Override
            public void onClick(View view) {
                bundle.putString("contactOtherProfile", arr[0]);

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

    /*
 Call Intent...
  */
    private void call(String contact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact));
        System.out.println("calling started");
        try {
            mActivity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Other Profile\n");
        }
    }
}