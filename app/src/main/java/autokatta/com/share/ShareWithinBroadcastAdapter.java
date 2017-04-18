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

public class ShareWithinBroadcastAdapter extends BaseAdapter {

    private Activity activity;
    private List<MyBroadcastGroupsResponse.Success> broadcastlist = new ArrayList<>();

    private LayoutInflater mInflater;
    private String sharedata, groupid, contactnumber, number, store_id, vehicle_id, product_id, service_id, profile_contact,
            search_id, status_id, auction_id, loan_id, exchange_id, keyword, grouptab;

    ShareWithinBroadcastAdapter(Activity activity, List<MyBroadcastGroupsResponse.Success> alldata,
                                String sharedata, String contactnumber, String store_id, String vehicle_id,
                                String product_id, String service_id, String profile_contact, String search_id,
                                String status_id, String auction_id, String loan_id, String exchange_id, String keyword) {

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

        mInflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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


        final ShareWithinBroadcastAdapter.ViewHolder holder;


        if (convertView == null) {
            holder = new ShareWithinBroadcastAdapter.ViewHolder();

            convertView = mInflater.inflate(R.layout.sharecontact_list_row, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.number = (TextView) convertView.findViewById(R.id.number);

            holder.profile_pic = (ImageView) convertView.findViewById(R.id.profile_image);


            convertView.setTag(holder);
        } else
            holder = (ShareWithinBroadcastAdapter.ViewHolder) convertView.getTag();


        final MyBroadcastGroupsResponse.Success obj = broadcastlist.get(position);

        holder.name.setText(obj.getGroupTitle());

        holder.number.setVisibility(View.GONE);
        holder.profile_pic.setVisibility(View.GONE);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle b = new Bundle();
                b.putString("sharewithcontact", sharedata);
                b.putString("store_id", store_id);
                b.putString("vehicle_id", vehicle_id);
                b.putString("product_id", product_id);
                b.putString("service_id", service_id);
                b.putString("profile_contact", profile_contact);
                b.putString("search_id", search_id);
                b.putString("status_id", status_id);
                b.putString("auction_id", auction_id);
                b.putString("loan_id", loan_id);
                b.putString("exchange_id", exchange_id);
                b.putString("number", number);
                b.putString("keyword", keyword);
                b.putString("groupname", obj.getGroupTitle());
                b.putString("broadcastgroupid", obj.getGroupId());
                b.putString("tab", "broadcastgroup");


                ShareWithCaption frag = new ShareWithCaption();

                frag.setArguments(b);

                FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.shareInApp_container, frag);
                fragmentTransaction.addToBackStack("ShareWithCaption");
                fragmentTransaction.commit();

            }
        });


        return convertView;
    }


}
