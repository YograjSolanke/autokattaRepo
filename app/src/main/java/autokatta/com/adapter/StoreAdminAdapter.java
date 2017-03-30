package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.Db_AutokattaContactResponse;

/**
 * Created by ak-003 on 29/3/17.
 */

public class StoreAdminAdapter extends BaseAdapter implements Filterable {
    private LayoutInflater mInflater;
    Activity activity;
    ArrayList<Boolean> positionArray;
    String singleimg;
    String Scontact, Rcontact;
    public static ArrayList<String> boxdata = new ArrayList<>();


    ArrayList<String> alreadyAdmin = new ArrayList<>();

    private ItemFilter mFilter = new ItemFilter();
    String con;
    ArrayList<Db_AutokattaContactResponse> contactdata = new ArrayList<>();
    ArrayList<Db_AutokattaContactResponse> contactdata_copy = new ArrayList<>();


    public StoreAdminAdapter(Activity a, ArrayList<Db_AutokattaContactResponse> contactdata) {

        this.activity = a;
        this.contactdata = contactdata;
        contactdata_copy = contactdata;


        System.out.println("WELCOME ON All Profiles ADAPTER PAGE");

        positionArray = new ArrayList<Boolean>(contactdata.size());
        for (int i = 0; i < contactdata.size(); i++) {
            positionArray.add(false);
            boxdata.add("0");
        }

        // mInflater = LayoutInflater.from(context);
        mInflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public StoreAdminAdapter(Activity a, ArrayList<Db_AutokattaContactResponse> contactdata, ArrayList<String> alreadyAdmin) {

        this.activity = a;
        this.contactdata = contactdata;
        contactdata_copy = contactdata;
        this.alreadyAdmin = alreadyAdmin;

        positionArray = new ArrayList<>(contactdata.size());
        for (int i = 0; i < contactdata.size(); i++) {
            positionArray.add(false);
            boxdata.add("0");
        }

        // mInflater = LayoutInflater.from(context);
        mInflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return contactdata.size();
    }

    public Object getItem(int position) {
        return contactdata.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unnecessary calls
        // to findViewById() on each row.
        final ViewHolder holder;

        Scontact = activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "");


        if (convertView == null) {

            holder = new ViewHolder();

            convertView = mInflater.inflate(R.layout.adapter_store_admin, null);
            holder.text = (TextView) convertView.findViewById(R.id.txtname);
            holder.text1 = (TextView) convertView.findViewById(R.id.txtnumber);
            holder.check = (CheckBox) convertView.findViewById(R.id.check);
            holder.spn = (Spinner) convertView.findViewById(R.id.spntest);


            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
            holder.check.setOnCheckedChangeListener(null);

        }


        holder.text.setText(contactdata.get(position).getContact());
        holder.text1.setText(contactdata.get(position).getUsername());

        holder.check.setFocusable(false);
        holder.check.setChecked(positionArray.get(position));
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.spn.setVisibility(View.VISIBLE);
                    positionArray.set(position, true);
                    boxdata.set(position, holder.text1.getText().toString() + "-" + "0");

                } else {

                    holder.spn.setVisibility(View.GONE);
                    positionArray.set(position, false);
                    boxdata.set(position, "0");
                }
            }
        });


        if (!(alreadyAdmin.size() == 0)) {
            for (int i = 0; i < alreadyAdmin.size(); i++) {
                String[] arr = alreadyAdmin.get(i).split("-");
                if (holder.text1.getText().toString().equals(arr[0])) {
                    holder.check.setChecked(true);
                    holder.spn.setSelection(Integer.parseInt(arr[1]));


                }

            }


        }


        holder.spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                boxdata.set(position, holder.text1.getText().toString() + "-" + String.valueOf(pos));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                boxdata.set(position, holder.text1.getText().toString() + "-" + "0");

            }
        });
        return convertView;
    }


    static class ViewHolder {
        TextView text, text1;

        CheckBox check;
        Spinner spn;

    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Db_AutokattaContactResponse> list = contactdata_copy;

            int count = list.size();
            final ArrayList<Db_AutokattaContactResponse> nlist = new ArrayList<>(count);

            Db_AutokattaContactResponse filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.getUsername().toLowerCase().contains(filterString)) {
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
            contactdata = (ArrayList<Db_AutokattaContactResponse>) results.values;
            notifyDataSetChanged();
        }

    }


}
