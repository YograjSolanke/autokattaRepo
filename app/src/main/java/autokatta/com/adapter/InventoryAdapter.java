package autokatta.com.adapter;

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
import autokatta.com.response.GetInventoryResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-001 on 9/5/17.
 */

public class InventoryAdapter extends BaseAdapter {
    Activity mActivity;
    List<GetInventoryResponse.Success> mItemList = new ArrayList<>();
    String name;
    boolean positionArray[];
    ArrayList<String> checked_ids;

    public InventoryAdapter(Activity mActivity, List<GetInventoryResponse.Success> mItemList, String name) {
        this.mActivity = mActivity;
        this.mItemList = mItemList;
        this.name = name;
        positionArray = new boolean[mItemList.size()];
        checked_ids = new ArrayList<>(mItemList.size());
        for (int i = 0; i < mItemList.size(); i++) {
            checked_ids.add("0");
            positionArray[i] = false;
        }
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
        return mItemList.get(position).hashCode();
    }

    class ViewHolder {
        TextView mVehicleName, mCategory, mSubCategory, mModel;
        ImageView mVehicleImage;
        CheckBox mCheckBox;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list_used_vehicles, null);
            holder = new ViewHolder();
            holder.mVehicleName = (TextView) convertView.findViewById(R.id.vehicle_title);
            holder.mCategory = (TextView) convertView.findViewById(R.id.category_str);
            holder.mSubCategory = (TextView) convertView.findViewById(R.id.sub_category_str);
            holder.mModel = (TextView) convertView.findViewById(R.id.model_str);
            holder.mVehicleImage = (ImageView) convertView.findViewById(R.id.vehicle_icon);
            holder.mCheckBox = (CheckBox) convertView.findViewById(R.id.check_box);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.mCheckBox.setOnCheckedChangeListener(null);
        }

        GetInventoryResponse.Success list = mItemList.get(position);
        holder.mVehicleName.setText(list.getTitle());
        holder.mCategory.setText(list.getCategory());
        holder.mSubCategory.setText(list.getSubCategory());
        holder.mModel.setText(list.getModel());

        if (mItemList.get(position).getImage().equals("") || mItemList.get(position).getImage().equals(null) ||
                mItemList.get(position).getImage().equals("null")) {
            holder.mVehicleImage.setBackgroundResource(R.mipmap.ic_launcher);
        } else {
            //mItemList.get(position).getImage() = mItemList.get(position).getImage().replaceAll(" ", "%20");
            String dppath = "http://autokatta.com/mobile/uploads/" + mItemList.get(position).getImage();
            Glide.with(mActivity)
                    .load(dppath)
                    .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    //.placeholder(R.drawable.logo) //To show image before loading an original image.
                    //.error(R.drawable.blocked) //To show error image if problem in loading.
                    .into(holder.mVehicleImage);
        }

        holder.mCheckBox.setFocusable(false);
        holder.mCheckBox.setChecked(positionArray[position]);

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checked_ids.set(position, mItemList.get(position).getVehicleId());
                    positionArray[position] = true;
                } else if (checked_ids.contains(mItemList.get(position).getVehicleId())) {
                    checked_ids.set(position, "0");
                    positionArray[position] = false;
                }
            }

        });
        return convertView;
    }

    public List<String> getInventoryList() {
        return checked_ids;
    }
}
