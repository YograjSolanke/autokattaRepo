package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.GetServiceSearchResponse;

/**
 * Created by ak-001 on 18/4/17.
 */

public class SearchServiceAdapter extends BaseAdapter {

    Activity activity;
    List<GetServiceSearchResponse.Success> allSearchDataArrayList = new ArrayList<>();
    private LayoutInflater inflater;

    public SearchServiceAdapter(Activity activity, List<GetServiceSearchResponse.Success> allSearchDataArrayList) {
        this.activity = activity;
        this.allSearchDataArrayList = allSearchDataArrayList;
        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return allSearchDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class YoHolder {
        TextView productName, productPrice, productDetails, productTags, productType;
        Button ViewDetails;
        ImageView productImage, deleteproduct;
        RatingBar productrating;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final YoHolder yoHolder;
        if (convertView == null) {
            yoHolder = new YoHolder();
            convertView = inflater.inflate(R.layout.product_new, null);
            yoHolder.productName = (TextView) convertView.findViewById(R.id.edittxt);
            yoHolder.productPrice = (TextView) convertView.findViewById(R.id.priceedit);
            yoHolder.productDetails = (TextView) convertView.findViewById(R.id.editdetails);
            yoHolder.productTags = (TextView) convertView.findViewById(R.id.edittags);
            yoHolder.productType = (TextView) convertView.findViewById(R.id.editproducttype);
            yoHolder.ViewDetails = (Button) convertView.findViewById(R.id.btnviewdetails);
            yoHolder.productImage = (ImageView) convertView.findViewById(R.id.profile);
            yoHolder.productrating = (RatingBar) convertView.findViewById(R.id.productrating);
            yoHolder.deleteproduct = (ImageView) convertView.findViewById(R.id.deleteproduct);
            convertView.setTag(yoHolder);
        } else {
            yoHolder = (YoHolder) convertView.getTag();
        }

        final GetServiceSearchResponse.Success obj = allSearchDataArrayList.get(position);

        yoHolder.productrating.setVisibility(View.GONE);
        yoHolder.deleteproduct.setVisibility(View.GONE);

        yoHolder.productName.setText(obj.getStoreName());
        yoHolder.productPrice.setText(obj.getPrice());
        yoHolder.productDetails.setText(obj.getDetails());
        yoHolder.productTags.setText(obj.getServiceTags());
        yoHolder.productType.setText(obj.getType());

        if (obj.getImages().equals("") || obj.getImages().equals("null") || obj.getImages().equals("")) {
            yoHolder.productImage.setBackgroundResource(R.drawable.store);
        } else {
            try {
                String dp_path = "http://autokatta.com/mobile/Product_pics/" + obj.getImages();
                Glide.with(activity)
                        .load(dp_path)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(yoHolder.productImage);
            } catch (Exception e) {
                System.out.println("Error in uploading images");
            }
        }

        yoHolder.ViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("name", obj.getName());
                b.putString("sid", obj.getStoreId());
                b.putString("price", obj.getPrice());
                b.putString("details", obj.getDetails());
                b.putString("tags", obj.getServiceTags());
                b.putString("type", obj.getType());
                b.putString("slikestatus", obj.getServicelikestatus());
                b.putString("images", obj.getImages());
                b.putString("category", obj.getCategory());
                b.putString("slikecnt", obj.getServicelikecount());
                b.putString("srating", obj.getServicerating());
                b.putString("srate", obj.getSrate());
                b.putString("srate1", obj.getSrate1());
                b.putString("srate2", obj.getSrate2());
                b.putString("srate3", obj.getSrate3());
                b.putString("store_id", obj.getStoreId());
                b.putString("storecontact", obj.getStorecontact());
                b.putString("storename", obj.getStoreName());
                b.putString("storewebsite", obj.getStorewebsite());
                b.putString("storerating", obj.getServicerating());
                b.putString("brandtags_list", obj.getBrandtags());


//                ServiceViewActivity frag = new ServiceViewActivity();
//                frag.setArguments(b);
//
//                FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.search_product, frag);
//                fragmentTransaction.addToBackStack("service_view");
//                fragmentTransaction.commit();
            }
        });

//            if(!obj.visibility)
//                allSearchDataArrayList.remove(obj);
//           notifyDataSetChanged();


        return convertView;
    }
}
