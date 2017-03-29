package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
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
import autokatta.com.response.GetStoreProfileInfoResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-001 on 29/3/17.
 */

public class ProfileMyStoreAdapter extends BaseAdapter {

    private Activity mActivity;
    private List<GetStoreProfileInfoResponse.Success> mItemList = new ArrayList<>();

    public ProfileMyStoreAdapter(Activity mActivity, List<GetStoreProfileInfoResponse.Success> mItemList) {
        this.mActivity = mActivity;
        this.mItemList = mItemList;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView mStoreName, mStoreLocation;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_store_child, null);

            mViewHolder = new ViewHolder();
            mViewHolder.mStoreName = (TextView) convertView.findViewById(R.id.store_name);
            mViewHolder.mStoreLocation = (TextView) convertView.findViewById(R.id.store_location);
            mViewHolder.imageView = (ImageView) convertView.findViewById(R.id.store_pic);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        GetStoreProfileInfoResponse.Success success = mItemList.get(position);
        mViewHolder.mStoreName.setText(mItemList.get(position).getStoreName());
        mViewHolder.mStoreLocation.setText(mItemList.get(position).getLocation());

        Glide.with(mActivity)
                .load("http://autokatta.com/mobile/store_profiles/" + mItemList.get(position).getStoreLogo())
                .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                .override(110, 100)
                .into(mViewHolder.imageView);

        return convertView;
    }
}
