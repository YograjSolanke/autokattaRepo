package autokatta.com.share;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private String sharedata, groupid, contactnumber, number, profile_contact, keyword, grouptab;
    private int store_id, vehicle_id, product_id, service_id,
            search_id, status_id, auction_id, loan_id, exchange_id;
    private List<String> finalGroupNames = new ArrayList<>();
    private List<String> finalGroupIds = new ArrayList<>();

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

        if (finalGroupIds.size() == 0) {
            for (int i = 0; i < this.broadcastlist.size(); i++) {
                finalGroupIds.add("0");
                finalGroupNames.add("");
            }
        }
    }

    static class ViewHolder {
        TextView name;
        ImageView profile_pic;
        CheckBox checkBox;
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

        LayoutInflater mInflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ShareWithBroadcastAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new ShareWithBroadcastAdapter.ViewHolder();
            assert mInflater != null;
            convertView = mInflater.inflate(R.layout.adapter_share_with_groups, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.profile_pic = (ImageView) convertView.findViewById(R.id.profile_image);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else
            holder = (ShareWithBroadcastAdapter.ViewHolder) convertView.getTag();

        final MyBroadcastGroupsResponse.Success obj = broadcastlist.get(position);
        holder.name.setText(obj.getGroupTitle());
        holder.profile_pic.setVisibility(View.GONE);


        if (!finalGroupIds.get(position).equalsIgnoreCase("0"))
            holder.checkBox.setChecked(true);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    finalGroupNames.set(position, holder.name.getText().toString());
                    finalGroupIds.set(position, String.valueOf(broadcastlist.get(position).getGroupId()));
                } else {
                    finalGroupNames.set(position, "");
                    finalGroupIds.set(position, "0");
                }
            }
        });

        return convertView;
    }

    List getGroupIdsList() {
        return finalGroupIds;
    }

    List getGroupNamesList() {
        return finalGroupNames;
    }
}
