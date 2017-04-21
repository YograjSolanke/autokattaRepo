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
import autokatta.com.response.ModelGroups;
import autokatta.com.view.GroupsActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 20/3/17.
 */

public class GroupsExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<String> mListHeaders;
    private HashMap<String, List<ModelGroups>> mSettingList;

    public GroupsExpandableListAdapter(Context mContext, List<String> mListHeaders, HashMap<String, List<ModelGroups>> mSettingList) {
        this.mContext = mContext;
        this.mListHeaders = mListHeaders;
        this.mSettingList = mSettingList;
    }

    @Override
    public int getGroupCount() {
        return mListHeaders.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListHeaders.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    private class GroupView {
        TextView mGroupHeader;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupView mView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_setting_header, null);
            mView = new GroupView();
            mView.mGroupHeader = (TextView) convertView.findViewById(R.id.header);
            convertView.setTag(mView);
        } else {
            mView = (GroupView) convertView.getTag();
        }
        String header = (String) getGroup(groupPosition);
        mView.mGroupHeader.setText(header);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mSettingList.get(mListHeaders.get(groupPosition)).size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mSettingList.get(mListHeaders.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    private class ChildView {
        TextView mHeader;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildView mView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_setting_child, null);
            mView = new ChildView();
            mView.mHeader = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(mView);
        } else {
            mView = (ChildView) convertView.getTag();
        }

        List<ModelGroups> list = (List<ModelGroups>) getChild(groupPosition, childPosition);
        final ModelGroups rowItem = list.get(childPosition);

        mView.mHeader.setText(rowItem.getTitle());
        mView.mHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  mContext.getSharedPreferences(mContext.getString(R.string.my_preference),MODE_PRIVATE).edit().putString("group_id", rowItem.getId()).apply();
                FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
                FragmentTransaction mTransaction = fragmentManager.beginTransaction();
                mTransaction.replace(R.id.profile_groups_container, new GroupDetailTabs()).commit();*/
                Intent i=new Intent(mContext, GroupsActivity.class);
                mContext.getSharedPreferences(mContext.getString(R.string.my_preference),MODE_PRIVATE).edit().putString("group_id", rowItem.getId()).apply();
                mContext.startActivity(i);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
