package autokatta.com.adapter;

/**
 * Created by ak-001 on 7/4/17.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.response.Db_AutokattaContactResponse;

public class BroadcastContactAdapter extends BaseAdapter {

    private ArrayList<Db_AutokattaContactResponse> contactdata = new ArrayList<>();
    Activity activity;
    private LayoutInflater inflater;
    private ArrayList<Boolean> positionArray;
    private ArrayList<String> contactlist;
    private ArrayList<String> checkedcontact = new ArrayList<>();


    public BroadcastContactAdapter(Activity activity, ArrayList<Db_AutokattaContactResponse> contactdata,
                                   ArrayList<String> checkedcontact) {
        this.activity = activity;
        this.contactdata = contactdata;
        this.checkedcontact = checkedcontact;

        contactlist = new ArrayList<>(contactdata.size());

        positionArray = new ArrayList<>(contactdata.size());
        for (int i = 0; i < contactdata.size(); i++) {
            if (checkedcontact.size() != 0) {
                if (checkedcontact.contains(contactdata.get(i).getContact())) {
                    positionArray.add(true);
                    contactlist.add(contactdata.get(i).getContact());
                } else {
                    positionArray.add(false);
                    contactlist.add("0");
                }
            } else {
                positionArray.add(false);
                contactlist.add("0");
            }
        }

    }

    @Override
    public int getCount() {
        return contactdata.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return contactdata.get(position).hashCode();
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.broadcast_contact_list_adapter, null);
            viewHolder = new ViewHolder();
            viewHolder.PersonName = (TextView) view.findViewById(R.id.contact_name);
            viewHolder.PersonContact = (TextView) view.findViewById(R.id.contact_no);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.checkall);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.PersonName.setText(contactdata.get(position).getUsername());
        viewHolder.PersonContact.setText(contactdata.get(position).getContact());

        //  viewHolder.checkBox.setFocusable(false);

        viewHolder.checkBox.setChecked(positionArray.get(position));
        //  holder.checkBox.setText(filteredData.get(position));
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    System.out.println(position + "--- :)");

                    positionArray.set(position, true);
                    contactlist.set(position, viewHolder.PersonContact.getText().toString());


                } else {
                    positionArray.set(position, false);
                    contactlist.set(position, "0");
                }

               /* if (positionArray.contains(true))
                    create_broadcast.setEnabled(true);
                else
                    create_broadcast.setEnabled(false);*/

            }

        });
        return view;
    }

    private ArrayList checkboxselect() {
        // TODO Auto-generated method stub
        return contactlist;
    }

    private class ViewHolder {
        TextView PersonName, PersonContact;
        CheckBox checkBox;

    }
}
