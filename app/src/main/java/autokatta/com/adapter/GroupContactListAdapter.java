package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.GetRegisteredContactsResponse;

/**
 * Created by ak-005 on 20/4/17
 */

public class GroupContactListAdapter extends BaseAdapter {

    private ItemFilter mFilter = new ItemFilter();
    private List<GetRegisteredContactsResponse> mList;
    private List<GetRegisteredContactsResponse> mListCopy;
    Activity activity;
    CheckBox checkbox;
    private Button mBtnAddContacts;
    private List<Boolean> positionArray = new ArrayList<>();
    private List<String> contactlist = new ArrayList<>();

    public GroupContactListAdapter(Activity a, List<GetRegisteredContactsResponse> mListt, Button addcontact) {
        this.activity = a;
        this.mList = mListt;
        this.mListCopy = mListt;
        this.mBtnAddContacts=addcontact;
        for (int i = 0; i < mList.size(); i++) {
            contactlist.add("0");
            positionArray.add(false);
        }
    }

    public int getCount() {
        return mList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder contactListHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_contact_list_adapter, null);
            contactListHolder = new ViewHolder();
            contactListHolder.mName = (TextView) convertView.findViewById(R.id.contact_name);
            contactListHolder.mContact = (TextView) convertView.findViewById(R.id.contact_no);
            contactListHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkall);
            convertView.setTag(contactListHolder);
        } else {
            contactListHolder = (ViewHolder) convertView.getTag();
            contactListHolder.checkBox.setOnCheckedChangeListener(null);
        }

        Log.i("aaaaaaaaaaaaaaaaa", "->" + mList);
        GetRegisteredContactsResponse success = mList.get(position);
        contactListHolder.mName.setText(success.getUsername());
        contactListHolder.mContact.setText(success.getContact());
        final ViewHolder ContactListHolder = contactListHolder;
        contactListHolder.checkBox.setFocusable(false);
        contactListHolder.checkBox.setChecked(positionArray.get(position));
        final ViewHolder finalContactListHolder = contactListHolder;
        contactListHolder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    positionArray.set(position, true);
                    contactlist.set(position, mList.get(position).getContact());
                } else {
                    positionArray.set(position, false);
                    contactlist.set(position, "0");
                }
                //For Button visible/invisible
                if (positionArray.contains(true))
                    mBtnAddContacts.setVisibility(View.VISIBLE);
                else
                    mBtnAddContacts.setVisibility(View.GONE);
            }

        });
        return convertView;
    }

    public List checkboxselect() {
        return contactlist;
    }

    private class ViewHolder {
        TextView mName;
        TextView mContact;
        CheckBox checkBox;
    }

    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ItemFilter();
        }
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final List<GetRegisteredContactsResponse> list = mListCopy;
            int count = list.size();
            if (filterString != null && filterString.length() > 0) {
                GetRegisteredContactsResponse filterableString;
                final ArrayList<GetRegisteredContactsResponse> nlist = new ArrayList<>(count);
                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i);
                    if (filterableString.getUsername().toLowerCase().contains(filterString)) {
                        nlist.add(filterableString);
                    }
                }
                results.values = nlist;
                results.count = nlist.size();
            } else {
                results.values = list;
                results.count = list.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mList = (ArrayList<GetRegisteredContactsResponse>) results.values;
            notifyDataSetChanged();
        }

    }
}
