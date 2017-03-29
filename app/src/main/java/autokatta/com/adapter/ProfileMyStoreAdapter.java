package autokatta.com.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.response.GetStoreProfileInfoResponse;

/**
 * Created by ak-001 on 29/3/17.
 */

public class ProfileMyStoreAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<GetStoreProfileInfoResponse> mItemList = new ArrayList<>();

    public ProfileMyStoreAdapter(Activity mActivity, List<GetStoreProfileInfoResponse> mItemList) {
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

    class ViewHolder{

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
