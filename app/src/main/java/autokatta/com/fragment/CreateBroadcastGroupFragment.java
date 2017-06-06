package autokatta.com.fragment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.database.DbConstants;
import autokatta.com.database.DbOperation;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.Db_AutokattaContactResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 6/4/17.
 */

public class CreateBroadcastGroupFragment extends Fragment implements RequestNotifier {
    FragmentActivity ctx;
    String editgroupname = "", editgrpmembers = "", calltype;
    String Mycontact, group_id = "", finalContacts = "", groupTitle;
    View root;
    Button create_broadcast;
    EditText edittitle;
    List<String> incomingList = new ArrayList<>();
    List<String> checkedcontact = new ArrayList<>();

    ListView memberContactslist;
    List<Db_AutokattaContactResponse> contactdata = new ArrayList<>();
    BroadcastContactAdapter autokattaContactAdapter;
    ApiCall mApiCall;
    String contact;
    EditText inputSearch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        root = inflater.inflate(R.layout.create_broadcast_group_fragment, container, false);
        mApiCall = new ApiCall(getActivity(), this);
        create_broadcast = (Button) root.findViewById(R.id.btncreate);
        memberContactslist = (ListView) root.findViewById(R.id.broadcastgrplist);
        edittitle = (EditText) root.findViewById(R.id.edittitle);
        edittitle.requestFocus();
        contact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
        inputSearch = (EditText) root.findViewById(R.id.inputSearch);

        try {
            Bundle b = getArguments();
            calltype = b.getString("calltype");
            editgroupname = b.getString("groupname");
            editgrpmembers = b.getString("groupmembers");
            group_id = b.getString("group_id");
            System.out.println("group id ala=" + group_id);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (calltype.equalsIgnoreCase("update")) {
            create_broadcast.setText("update");

            edittitle.setText(editgroupname);
            checkedcontact = new ArrayList<String>(Arrays.asList(editgrpmembers.split(",")));
            System.out.println("conversion=" + checkedcontact.size() + " " + checkedcontact);

        }

        DbOperation dbAdpter = new DbOperation(getActivity());
        dbAdpter.OPEN();
        Cursor cursor = dbAdpter.getAutokattaContact();
        if (cursor.getCount() > 0) {
            contactdata.clear();
            cursor.moveToFirst();
            do {
                //Log.i(DbConstants.TAG, cursor.getString(cursor.getColumnIndex(DbConstants.userName)) + " = " + cursor.getString(cursor.getColumnIndex(contact)));
                Db_AutokattaContactResponse obj = new Db_AutokattaContactResponse();
                if (!cursor.getString(cursor.getColumnIndex(DbConstants.contact)).equals(contact)) {
                    obj.setContact(cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
                    obj.setUsername(cursor.getString(cursor.getColumnIndex(DbConstants.userName)));
                    contactdata.add(obj);
                }
            } while (cursor.moveToNext());
        } else {
            Log.e("No number", "");
        }
        dbAdpter.CLOSE();

        Collections.sort(contactdata, Db_AutokattaContactResponse.StuNameComparator);

        autokattaContactAdapter = new BroadcastContactAdapter(getActivity(), contactdata, checkedcontact);
        memberContactslist.setAdapter(autokattaContactAdapter);
        autokattaContactAdapter.notifyDataSetChanged();



        create_broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Hiii Clicked11111111111111111111111111111111111111111111111111111111111");


            /*get checked Items*/
                finalContacts = "";
                groupTitle = edittitle.getText().toString();
                incomingList = autokattaContactAdapter.checkboxselect();

                for (int i = 0; i < incomingList.size(); i++) {
                    if (!incomingList.get(i).equalsIgnoreCase("0")) {
                        if (finalContacts.equals(""))
                            finalContacts = incomingList.get(i);
                        else {

                            finalContacts = finalContacts + "," + incomingList.get(i);

                        }
                    }
                }
                if (groupTitle.equals("")) {
                    Toast.makeText(getActivity(), "Please enter group Title",
                            Toast.LENGTH_LONG).show();
                    //return;
                } else {
                    //Toast.makeText(getActivity(), "Now web service call", Toast.LENGTH_LONG).show();
                    System.out.println("group id &&&&&ContactList:" + finalContacts + "Groupid" + group_id + "contact" + contact + "calltype" + calltype);
                    if (calltype.equals("update")) {
                        mApiCall.updateBroadcastgroup(groupTitle, contact, finalContacts, calltype, group_id);

                        //  createBroadcastGroup(groupTitle,finalContacts,group_id);
                    } else {
                        mApiCall.createBroadcastgroup(groupTitle, contact, finalContacts, calltype);
                    }
                }

            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autokattaContactAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return root;
    }


    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(ctx, getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(ctx, getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(ctx, getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "create BroadcastGroup");
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("success updation")) {
                CustomToast.customToast(ctx, "Broadcast Group Updated Successfully");
                MyBroadcastGroupsFragment broadcastGroup = new MyBroadcastGroupsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.broadcast_groups_container, broadcastGroup);
                fragmentTransaction.addToBackStack("rrrrr");
                fragmentTransaction.commit();
            } else {
                CustomToast.customToast(ctx, "Broadcast Group Created Successfully");
                MyBroadcastGroupsFragment broadcastGroup = new MyBroadcastGroupsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.broadcast_groups_container, broadcastGroup);
                fragmentTransaction.addToBackStack("rrrrr");
                fragmentTransaction.commit();
            }
        }
    }


    /***********************************************Adapter*******************************************/

    private class BroadcastContactAdapter extends BaseAdapter {


        private List<Db_AutokattaContactResponse> contactdata = new ArrayList<>();
        private List<Db_AutokattaContactResponse> contactdataCopy = new ArrayList<>();
        Activity activity;
        private LayoutInflater inflater;
        private List<Boolean> positionArray;
        private List<String> contactlist;
        private List<String> checkedcontact = new ArrayList<>();
        private ItemFilter mFilter = new ItemFilter();


        BroadcastContactAdapter(Activity activity, List<Db_AutokattaContactResponse> contactdata,
                                List<String> checkedcontact) {
            this.activity = activity;
            this.contactdata = contactdata;
            this.contactdataCopy = contactdata;
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


            viewHolder.checkBox.setFocusable(false);
            viewHolder.checkBox.setChecked(positionArray.get(position));

            viewHolder.checkBox.setTag(position); // This line is important.
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        System.out.println(position + "--- :)" + viewHolder.PersonContact.getText().toString());

                        positionArray.set(position, true);
                        contactlist.set(position, viewHolder.PersonContact.getText().toString());


                    } else {
                        positionArray.set(position, false);
                        contactlist.set(position, "0");
                    }


                    if (positionArray.contains(true))
                        create_broadcast.setEnabled(true);
                    else
                        create_broadcast.setEnabled(false);

                }

            });


            //viewHolder.checkBox.setChecked(isChecked(position));

            // viewHolder.text.setText(list.get(position).getName());
            // viewHolder.checkBox.setChecked(Boolean.parseBoolean(contactlist.get(position)));


            return view;
        }

        private List checkboxselect() {
            // TODO Auto-generated method stub
            return contactlist;
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

                final List<Db_AutokattaContactResponse> list = contactdataCopy;

                int count = list.size();

                if (filterString != null && filterString.length() > 0) {
                    Db_AutokattaContactResponse filterableString;
                    final List<Db_AutokattaContactResponse> nlist = new ArrayList<>(count);
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
                contactdata = (ArrayList<Db_AutokattaContactResponse>) results.values;
                notifyDataSetChanged();
            }

        }
    }

    static class ViewHolder {
        TextView PersonName, PersonContact;
        CheckBox checkBox;
    }


}
