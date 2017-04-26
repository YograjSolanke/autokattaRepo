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
import autokatta.com.response.GetSearchProductResponse;

/**
 * Created by ak-001 on 18/4/17.
 */

public class SearchProductAdapter extends BaseAdapter {
    Activity activity;
    List<GetSearchProductResponse.Success> allSearchData = new ArrayList<>();
    private LayoutInflater inflater;

    public SearchProductAdapter(Activity activity, List<GetSearchProductResponse.Success> allSearchDataArrayList) {
        this.activity = activity;
        this.allSearchData = allSearchDataArrayList;
        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return allSearchData.size();
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
        YoHolder yoHolder = null;
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
        final GetSearchProductResponse.Success obj = allSearchData.get(position);
        yoHolder.productrating.setVisibility(View.GONE);
        yoHolder.deleteproduct.setVisibility(View.GONE);
        yoHolder.productName.setText(obj.getProductName());
        yoHolder.productPrice.setText(obj.getPrice());
        yoHolder.productDetails.setText(obj.getProductDetails());
        yoHolder.productTags.setText(obj.getProductTags());
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
                b.putString("name", obj.getProductName());
                b.putString("pid", obj.getProductId());
                b.putString("price", obj.getPrice());
                b.putString("details", obj.getProductDetails());
                b.putString("tags", obj.getProductTags());
                b.putString("type", obj.getType());
                b.putString("likestatus", obj.getProductlikestatus());
                b.putString("images", obj.getImages());
                b.putString("category", obj.getCategory());
                b.putString("plikecnt", obj.getProductlikecount());
                b.putString("prating", obj.getProductrating());
                b.putString("prate", obj.getPrate());
                b.putString("prate1", obj.getPrate1());
                b.putString("prate2", obj.getPrate2());
                b.putString("prate3", obj.getPrate3());
                b.putString("store_id", obj.getStoreId());
                b.putString("storecontact", obj.getStorecontact());
                b.putString("storename", obj.getStoreName());
                b.putString("storewebsite", obj.getStorewebsite());
                b.putString("storerating", obj.getStorerating());
                b.putString("brandtags_list", obj.getBrandtags());

               /* ProductViewActivity frag = new ProductViewActivity();
                frag.setArguments(b);

                FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.search_product, frag);
                fragmentTransaction.commit();*/
            }
        });
        return convertView;
    }
}
