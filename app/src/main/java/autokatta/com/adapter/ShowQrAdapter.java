package autokatta.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.other.ViewQrCode;
import autokatta.com.response.GetStoreProfileInfoResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by ak-001 on 13/9/17.
 */

public class ShowQrAdapter extends BaseAdapter {
    Context mActivity;
    private List<GetStoreProfileInfoResponse.Success> mItemList = new ArrayList<>();

    public ShowQrAdapter(Context context, List<GetStoreProfileInfoResponse.Success> mItemList) {
        this.mActivity = context;
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
        Button mGetQR;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ShowQrAdapter.ViewHolder mViewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_qr_store_code, null);

            mViewHolder = new ShowQrAdapter.ViewHolder();
            mViewHolder.mStoreName = (TextView) convertView.findViewById(R.id.store_name);
            mViewHolder.mStoreLocation = (TextView) convertView.findViewById(R.id.store_location);
            mViewHolder.imageView = (ImageView) convertView.findViewById(R.id.store_pic);
            mViewHolder.mGetQR = (Button) convertView.findViewById(R.id.show_qr);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ShowQrAdapter.ViewHolder) convertView.getTag();
        }

        GetStoreProfileInfoResponse.Success success = mItemList.get(position);
        mViewHolder.mStoreName.setText(mItemList.get(position).getStoreName());
        mViewHolder.mStoreLocation.setText(mItemList.get(position).getLocation());

        if (!mItemList.get(position).getStoreLogo().equals("") || !mItemList.get(position).getStoreLogo().equals("null")
                || mItemList.get(position).getStoreLogo() != null) {
            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.base_image_url) + mItemList.get(position).getStoreLogo())
                    .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .placeholder(R.drawable.logo48x48)
                    .override(110, 100)
                    .into(mViewHolder.imageView);
        } else {
            mViewHolder.imageView.setImageResource(R.drawable.logo48x48);
        }

        mViewHolder.mGetQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("storeid", mItemList.get(position).getStoreId());
                    obj.put("storecontact", mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), MODE_PRIVATE)
                            .getString("loginContact", ""));

                    Log.i("Object", "->" + obj.toString());
                    Intent intent = new Intent(mActivity, ViewQrCode.class);
                    intent.putExtra("objectString", obj.toString());
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    mActivity.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return convertView;
    }
}
