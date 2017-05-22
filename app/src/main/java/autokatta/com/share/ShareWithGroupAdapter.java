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

    private LayoutInflater mInflater;
    private String sharedata, groupid, contactnumber, number, store_id, vehicle_id, product_id, service_id, profile_contact,
            search_id, status_id, auction_id, loan_id, exchange_id, keyword, groupname;


    ShareWithGroupAdapter(Activity activity, List<String> alldata,
                          String sharedata, String contactnumber, String store_id, String vehicle_id,
                          String product_id, String service_id, String profile_contact, String search_id,
                          String status_id, String auction_id, String loan_id, String exchange_id, String keyword) {

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

    }

    static class ViewHolder {
        TextView name, number;
        ImageView profile_pic;
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

        mInflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_share_contact, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.number = (TextView) convertView.findViewById(R.id.number);
            holder.profile_pic = (ImageView) convertView.findViewById(R.id.profile_image);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.name.setText(title_array.get(position));
        holder.number.setVisibility(View.GONE);

        if (image_array.get(position) == null || image_array.get(position).equals("null") || image_array.get(position).equals("")) {
            holder.profile_pic.setBackgroundResource(R.drawable.workers);
        } else {
            Glide.with(activity)
                    .load("http://autokatta.com/mobile/group_profile_pics/" + image_array.get(position))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new CropCircleTransformation(activity))
                    .override(100, 100)
                    .into(holder.profile_pic);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                groupid = group_id.get(position);
                groupname = title_array.get(position);
                Bundle b = new Bundle();
                b.putString("generic_list_view", sharedata);
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
                b.putString("groupname", groupname);
                b.putString("groupid", groupid);
                b.putString("tab", "group");

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



