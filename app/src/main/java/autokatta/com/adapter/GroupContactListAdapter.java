package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
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

    private List<Success>originalData = null;
    private List<Success>filteredData = null;
    private ItemFilter mFilter = new ItemFilter();

    private LayoutInflater mInflater;
    Activity activity;
    CheckBox checkbox;
    ArrayList<Boolean> positionArray;
    ArrayList<String> contactlist;
    public GroupContactListAdapter(Activity a, ArrayList<GetRegisteredContactsResponse.Success> data) {
        this.filteredData = data ;
        this.originalData = data ;
        this.activity=a;

        contactlist=new ArrayList<>(filteredData.size());

        positionArray = new ArrayList<>(filteredData.size());
        for(int i =0;i<filteredData.size();i++) {
            positionArray.add(false);
            contactlist.add("0");
        }
        mInflater = (LayoutInflater)activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return filteredData.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        // A ViewHolder keeps references to children views to avoid unnecessary calls
        // to findViewById() on each row.
        final ViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_contact_list_adapter, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.contact_name);
            holder.text1 = (TextView) convertView.findViewById(R.id.contact_no);
            holder.checkBox = (CheckBox) convertView.findViewById( R.id.checkall);


            convertView.setTag(holder);
        }
        else
        {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
            holder.checkBox.setOnCheckedChangeListener(null);
        }


        String contact_name=filteredData.get(position).getUsername();
        String arr[]=contact_name.split("-");
        holder.text.setText(arr[0]);
        holder.text1.setText(arr[1]);

        // holder.checkBox.setOnClickListener(this);
        holder.checkBox.setFocusable(false);
        holder.checkBox.setChecked(positionArray.get(position));

        holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked ){

                    positionArray.set(position, true);
                    contactlist.set(position,holder.text1.getText().toString());

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


    static class ViewHolder {
        TextView text,text1;
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

            final List<Success> list = originalData;

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
