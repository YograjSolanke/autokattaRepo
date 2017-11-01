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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-005 on 20/6/16.
 */

public class ShareWithGroupAdapter extends BaseAdapter {


    private Activity activity;
    private List<String> alldata;

    private List<String> group_id = new ArrayList<>();
    private List<String> title_array = new ArrayList<>();
    private List<String> image_array = new ArrayList<>();
    private List<String> finalGroupNames = new ArrayList<>();
    private List<String> finalGroupIds = new ArrayList<>();

    private String sharedata, contactnumber, number, profile_contact, keyword, groupname;
    private int store_id, vehicle_id, groupid, product_id, service_id,
            search_id, status_id, auction_id, loan_id, exchange_id;

    ShareWithGroupAdapter(Activity activity, List<String> alldata,
                          String sharedata, String contactnumber, int store_id, int vehicle_id,
                          int product_id, int service_id, String profile_contact, int search_id,
                          int status_id, int auction_id, int loan_id, int exchange_id, String keyword) {

        this.activity = activity;
        this.alldata = alldata;
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

        for (int i = 0; i < alldata.size(); i++) {
            String array[] = alldata.get(i).split("=");
            title_array.add(array[0]);
            group_id.add(array[1]);
            image_array.add(array[2]);
        }

        if (finalGroupIds.size() == 0) {
            for (int i = 0; i < this.title_array.size(); i++) {
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
        return alldata.size();
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
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            assert mInflater != null;
            convertView = mInflater.inflate(R.layout.adapter_share_with_groups, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.profile_pic = (ImageView) convertView.findViewById(R.id.profile_image);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.name.setText(title_array.get(position));

        if (image_array.get(position) == null || image_array.get(position).equals("null") || image_array.get(position).equals("")) {
            holder.profile_pic.setBackgroundResource(R.drawable.workers);
        } else {
            Glide.with(activity)
                    .load(activity.getString(R.string.base_image_url) + image_array.get(position))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new CropCircleTransformation(activity))
                    .override(100, 100)
                    .into(holder.profile_pic);
        }


        if (!finalGroupIds.get(position).equalsIgnoreCase("0"))
            holder.checkBox.setChecked(true);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    finalGroupNames.set(position, holder.name.getText().toString());
                    finalGroupIds.set(position, group_id.get(position));
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



