package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.my_store.AddMoreAdminsForStoreFrag;
import autokatta.com.response.Db_AutokattaContactResponse;

/**
 * Created by ak-003 on 29/3/17.
 */

public class StoreAdminAdapter extends RecyclerView.Adapter<StoreAdminAdapter.ItemHolder> implements Filterable {
    private List<Boolean> positionArray;
    public static ArrayList isSave;
    Activity activity;
    String Scontact, Rcontact;
    public static ArrayList<String> boxdata;

    ArrayList<String> alreadyAdmin = new ArrayList<>();
    String[] array = {"seller", "buyer", "financer", "dealer"};

    private ItemFilter mFilter = new ItemFilter();
    ArrayList<Db_AutokattaContactResponse> contactdata = new ArrayList<>();
    ArrayList<Db_AutokattaContactResponse> contactdata_copy = new ArrayList<>();

    static class ItemHolder extends RecyclerView.ViewHolder {

        TextView text, text1;
        RelativeLayout rolelayout;
        CheckBox check;
        Button save;
        AutoCompleteTextView roleText;

        public ItemHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.txtname);
            text1 = (TextView) itemView.findViewById(R.id.txtnumber);
            check = (CheckBox) itemView.findViewById(R.id.check);
            roleText = (AutoCompleteTextView) itemView.findViewById(R.id.autoRole);
            rolelayout = (RelativeLayout) itemView.findViewById(R.id.rolelayout);
            save = (Button) itemView.findViewById(R.id.save);
        }
    }


    public StoreAdminAdapter(Activity a, ArrayList<Db_AutokattaContactResponse> contactdata) {

        this.activity = a;
        this.contactdata = contactdata;
        contactdata_copy = contactdata;
        boxdata = new ArrayList<>(contactdata.size());
        isSave = new ArrayList<>(contactdata.size());

        positionArray = new ArrayList<Boolean>(contactdata.size());
        for (int i = 0; i < contactdata.size(); i++) {
            positionArray.add(false);
            boxdata.add("0");
            isSave.add(false);
        }

    }


    public StoreAdminAdapter(Activity a, ArrayList<Db_AutokattaContactResponse> contactdata, ArrayList<String> alreadyAdmin) {

        this.activity = a;
        this.contactdata = contactdata;
        contactdata_copy = contactdata;
        this.alreadyAdmin = alreadyAdmin;
        boxdata = new ArrayList<>(contactdata.size());
        isSave = new ArrayList<>(contactdata.size());
        AddMoreAdminsForStoreFrag.ok.setVisibility(View.VISIBLE);
        positionArray = new ArrayList<Boolean>(contactdata.size());
        for (int i = 0; i < contactdata.size(); i++) {
            positionArray.add(false);
            boxdata.add("0");
            isSave.add(false);
        }

        for (int i = 0; i < alreadyAdmin.size(); i++) {

            for (int j = 0; j < this.contactdata.size(); j++) {
                if (alreadyAdmin.get(i).contains(this.contactdata.get(j).getContact())) {
                    this.contactdata.get(j).setContact(alreadyAdmin.get(i));
                    boxdata.set(j, alreadyAdmin.get(i));

                }
            }
        }


    }


    @Override
    public StoreAdminAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_store_admin, parent, false);
        ItemHolder yoHolder = new ItemHolder(v);
        return yoHolder;
    }

    @Override
    public void onBindViewHolder(final StoreAdminAdapter.ItemHolder holder, final int position) {

//        }
        if (contactdata.get(position).getContact().contains("-")) {
            String[] arr = contactdata.get(position).getContact().split("-");
            holder.text.setText(contactdata.get(position).getUsername());
            holder.text1.setText(arr[0]);
            holder.roleText.setText(arr[1]);
            holder.check.setChecked(true);
            holder.rolelayout.setVisibility(View.VISIBLE);
            // boxdata.set(position,contactdata.get(position).getContact());
        } else {
            holder.text.setText(contactdata.get(position).getUsername());
            holder.text1.setText(contactdata.get(position).getContact());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, array);

        holder.roleText.setAdapter(adapter);

        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    positionArray.set(position, true);
                    AddMoreAdminsForStoreFrag.ok.setVisibility(View.VISIBLE);
                    holder.rolelayout.setVisibility(View.VISIBLE);
                    boxdata.set(position, holder.text1.getText().toString() + "-" + holder.roleText.getText().toString());

                } else {
                    positionArray.set(position, false);
                    boxdata.set(position, "0");
                    isSave.set(position, false);
                    holder.rolelayout.setVisibility(View.GONE);
                    System.out.println("Boxdata at position=" + position + " " + boxdata.get(position));
                    if (!positionArray.contains(true)) {
                        AddMoreAdminsForStoreFrag.ok.setVisibility(View.GONE);
                    }
                }
            }
        });

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.roleText.getText().toString().isEmpty() || holder.roleText.getText().toString().equals(" ")) {
                    isSave.set(position, false);
                    boxdata.set(position, holder.text1.getText().toString() + "-" + holder.roleText.getText().toString());
                    holder.roleText.setError("Enter Role");
                } else {
                    isSave.set(position, true);
                    boxdata.set(position, holder.text1.getText().toString() + "-" + holder.roleText.getText().toString());
                    System.out.println("Boxdata at position=" + position + " " + boxdata.get(position));
                    holder.rolelayout.setVisibility(View.GONE);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactdata.size();
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
