package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.GetRegisteredContactsResponse;
import autokatta.com.response.GetRegisteredContactsResponse.Success;

import static autokatta.com.fragment.GroupContactFragment.AddContacts;

/**
 * Created by ak-005 on 20/4/17.
 */

public class GroupContactListAdapter extends BaseAdapter {


    private List<Success>filteredData = null;
    private ItemFilter mFilter = new ItemFilter();
    private List<GetRegisteredContactsResponse.Success> mList;
    private List<GetRegisteredContactsResponse.Success> mListCopy;
    Activity activity;
    CheckBox checkbox;
    ArrayList<Boolean> positionArray=new ArrayList<>();
    ArrayList<String> contactlist=new ArrayList<>();
    public GroupContactListAdapter(Activity a, ArrayList<GetRegisteredContactsResponse.Success> mList) {
        this.activity = a;
        this.mList = mList;
        this.mListCopy=mList;

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
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_contact_list_adapter, null);


            contactListHolder = new ViewHolder();
            contactListHolder.mName = (TextView) convertView.findViewById(R.id.contact_name);
            contactListHolder.mContact = (TextView) convertView.findViewById(R.id.contact_no);
            contactListHolder.checkBox = (CheckBox) convertView.findViewById( R.id.checkall);
            convertView.setTag(contactListHolder);
        }else {
            contactListHolder = (ViewHolder) convertView.getTag();
            contactListHolder.checkBox.setOnCheckedChangeListener(null);
        }
        Log.i("aaaaaaaaaaaaaaaaa","->"+mList);
        GetRegisteredContactsResponse.Success success = mList.get(position);
        contactListHolder.mName.setText(success.getUsername());
        contactListHolder.mContact.setText(success.getContact());

        final ViewHolder ContactListHolder = contactListHolder;


        contactListHolder.checkBox.setFocusable(false);
        contactListHolder.checkBox.setChecked(positionArray.get(position));

        final ViewHolder finalContactListHolder = contactListHolder;
        contactListHolder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked ){

                    positionArray.set(position, true);
                    contactlist.set(position, mList.get(position).getContact());

                }else{
                    positionArray.set(position, false);
                    contactlist.set(position,"0");
                }

                //For Button visible/invisible

                if (positionArray.contains(true))
                    AddContacts.setEnabled(true);
                else
                    AddContacts.setEnabled(false);

            }

        });


        return convertView;
    }

    public ArrayList checkboxselect() {
        // TODO Auto-generated method stub
        return contactlist;
    }


    private class ViewHolder {
        TextView mName;
        TextView mContact;
        CheckBox checkBox ;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Success> list = mList;

            int count = list.size();
            final ArrayList<Success> nlist = new ArrayList<Success>(count);

            Success filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterString.toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<Success>) results.values;
            notifyDataSetChanged();
        }

    }
}
