package autokatta.com.share;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.response.Db_AutokattaContactResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-005 on 20/6/16.
 */

public class ShareWithContactAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Db_AutokattaContactResponse> contactdata = new ArrayList<>();
    private String sharedata,  storecontact, name, number, contactnumber, vehicle_id, product_id,
            service_id, profile_contact, search_id, status_id, auction_id, loan_id, exchange_id, keyword, contacttab;
private int store_id;
    private LayoutInflater mInflater;


    ShareWithContactAdapter(Activity activity,
                            ArrayList<Db_AutokattaContactResponse> data, String sharedata,
                            int store_id, String contactnumber, String vehicle_id, String product_id,
                            String service_id, String profile_contact, String search_id,
                            String status_id, String auction_id, String loan_id, String exchange_id, String keyword) {

        contactdata = data;
        this.activity = activity;
        this.sharedata = sharedata;
        this.store_id = store_id;
        this.contactnumber = contactnumber;
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
        return contactdata.size();
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


        Db_AutokattaContactResponse obj = contactdata.get(position);

        holder.name.setText(obj.getUsername());
        holder.number.setText(obj.getContact());


        if (obj.getUserprofile() == null || obj.getUserprofile().equals("null") || obj.getUserprofile().equals("")) {

            holder.profile_pic.setBackgroundResource(R.drawable.profile);
        } else {
            Glide.with(activity)
                    .load("http://autokatta.com/mobile/profile_profile_pics/" + obj.getUserprofile())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new CropCircleTransformation(activity))
                    .override(100, 100)
                    .into(holder.profile_pic);
        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = holder.number.getText().toString();
                name = holder.name.getText().toString();

                Bundle b = new Bundle();
                b.putString("generic_list_view", sharedata);
                b.putInt("store_id", store_id);
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
                b.putString("name", name);
                b.putString("keyword", keyword);
                b.putString("tab", "contact");

                ShareWithCaptionFragment frag = new ShareWithCaptionFragment();
                frag.setArguments(b);

                ((FragmentActivity) activity).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.shareInApp_container, frag, "ShareWithCaptionFragment")
                        .addToBackStack("ShareWithCaptionFragment")
                        .commit();
            }
        });

        return convertView;
    }


}
