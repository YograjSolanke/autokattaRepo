package autokatta.com.share;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.MyBroadcastGroupsResponse;

/**
 * Created by ak-003 on 21/2/17.
 */

public class ShareWithBroadcastAdapter extends BaseAdapter {
    private Activity activity;
    private List<MyBroadcastGroupsResponse.Success> broadcastlist = new ArrayList<>();
    private LayoutInflater mInflater;
    private String sharedata, groupid, contactnumber, number,  profile_contact, keyword, grouptab;
    private int store_id,vehicle_id, product_id, service_id,
    search_id, status_id, auction_id, loan_id, exchange_id;

    ShareWithBroadcastAdapter(Activity activity, List<MyBroadcastGroupsResponse.Success> alldata,
                              String sharedata, String contactnumber, int store_id, int vehicle_id,
                              int product_id, int service_id, String profile_contact, int search_id,
                              int status_id, int auction_id, int loan_id, int exchange_id, String keyword) {

        this.activity = activity;
        this.broadcastlist = alldata;
        this.sharedata = sharedata;
        this.contactnumber = contactnumber;
        this.store_id = store_id;
        this.vehicle_id = vehicle_id;
        this.product_id = product_id;
        this.service_id = service_id;
        this.profile_contact = profile_contact;
        this.search_id = search_id;
        this.status_id = status_id;
        this.auction_id = auction_id;
        this.loan_id = loan_id;
        this.exchange_id = exchange_id;
        this.keyword = keyword;
    }

    static class ViewHolder {
        TextView name, number;
        ImageView profile_pic;
    }

    @Override
    public int getCount() {
        return broadcastlist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        mInflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ShareWithBroadcastAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new ShareWithBroadcastAdapter.ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_share_contact, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.number = (TextView) convertView.findViewById(R.id.number);
            holder.profile_pic = (ImageView) convertView.findViewById(R.id.profile_image);
            convertView.setTag(holder);
        } else
            holder = (ShareWithBroadcastAdapter.ViewHolder) convertView.getTag();

        final MyBroadcastGroupsResponse.Success obj = broadcastlist.get(position);
        holder.name.setText(obj.getGroupTitle());
        holder.number.setVisibility(View.GONE);
        holder.profile_pic.setVisibility(View.GONE);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("generic_list_view", sharedata);
                b.putInt("store_id", store_id);
                b.putInt("vehicle_id", vehicle_id);
                b.putInt("product_id", product_id);
                b.putInt("service_id", service_id);
                b.putString("profile_contact", profile_contact);
                b.putInt("search_id", search_id);
                b.putInt("status_id", status_id);
                b.putInt("auction_id", auction_id);
                b.putInt("loan_id", loan_id);
                b.putInt("exchange_id", exchange_id);
                b.putString("number", number);
                b.putString("keyword", keyword);
                b.putString("groupname", obj.getGroupTitle());
                b.putInt("broadcastgroupid", obj.getGroupId());
                b.putString("tab", "broadcastgroup");

                ShareWithCaptionFragment frag = new ShareWithCaptionFragment();
                frag.setArguments(b);
                FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.shareInApp_container, frag);
                fragmentTransaction.addToBackStack("ShareWithCaptionFragment");
                fragmentTransaction.commit();
            }
        });
        return convertView;
    }
}
