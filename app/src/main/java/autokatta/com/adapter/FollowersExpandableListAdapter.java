package autokatta.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.ModelFollowers;
import autokatta.com.view.OtherProfile;

/**
 * Created by ak-005 on 14/4/17.
 */

public class FollowersExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<String> mListHeaders;
    private HashMap<String, List<ModelFollowers>> mSettingList;

    public FollowersExpandableListAdapter(Context activity, List<String> mHeaderList, HashMap<String, List<ModelFollowers>> mSettingList) {
        this.mContext = activity;
        this.mListHeaders = mHeaderList;
        this.mSettingList = mSettingList;
    }

    @Override
    public int getGroupCount() {
        return mListHeaders.size();
    }

    @Override
    public Object getGroup(int position) {
         return mListHeaders.get(position);
    }


    @Override
    public long getGroupId(int position) {
        return position;
    }

    private class GroupView {
        TextView mFollowersHeader;
    }


    @Override
    public View getGroupView(int position, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupView mView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_setting_header, null);
            mView = new GroupView();
            mView.mFollowersHeader = (TextView) convertView.findViewById(R.id.header);
            convertView.setTag(mView);
        } else {
            mView = (GroupView) convertView.getTag();
        }
        String header = (String) getGroup(position);
        mView.mFollowersHeader.setText(header);
        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public int getChildrenCount(int position) {
        return mSettingList.get(mListHeaders.get(position)).size();
    }


    @Override
    public Object getChild(int position, int childPosition) {
        return mSettingList.get(mListHeaders.get(position));
    }

    @Override
    public long getChildId(int position, int childPosition) {
        return  childPosition;
    }
    private class ChildView {
        TextView mHeader;

    }
    @Override
    public View getChildView(int position, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildView mView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_setting_child, null);
            mView = new ChildView();
            mView.mHeader = (TextView) convertView.findViewById(R.id.name);
         //   mView.mContact = (TextView) convertView.findViewById(R.id.contact);

            convertView.setTag(mView);
        } else {
            mView = (ChildView) convertView.getTag();
        }

        List<ModelFollowers> list = (List<ModelFollowers>) getChild(position, childPosition);
        final ModelFollowers rowItem = list.get(childPosition);

        mView.mHeader.setText(rowItem.getName());
        mView.mHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(mContext,OtherProfile.class);
                System.out.println("========="+rowItem.getContact());
                i.putExtra("contactOtherProfile", rowItem.getContact());
                mContext.startActivity(i);
            }
        });
        //mView.mContact.setText(rowItem.getContact());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int position, int childPosition) {
        return false;
    }
}
