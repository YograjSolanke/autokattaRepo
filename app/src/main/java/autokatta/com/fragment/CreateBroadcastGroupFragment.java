package autokatta.com.fragment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

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
    String Mycontact, finalContacts = "", groupTitle;
    View root;
    int  group_id = 0;
    Button create_broadcast;
    EditText edittitle;
    ArrayList<String> incomingList = new ArrayList<>();
    ArrayList<String> checkedcontact = new ArrayList<>();

    ListView memberContactslist;
    ArrayList<Db_AutokattaContactResponse> contactdata = new ArrayList<>();
    BroadcastContactAdapter autokattaContactAdapter;
    ApiCall mApiCall;
    String contact;


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

        try {
            Bundle b = getArguments();
            calltype = b.getString("calltype");
            editgroupname = b.getString("groupname");
            editgrpmembers = b.getString("groupmembers");
            group_id = b.getInt("group_id");
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

                obj.setContact(cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
                obj.setUsername(cursor.getString(cursor.getColumnIndex(DbConstants.userName)));
                contactdata.add(obj);
            } while (cursor.moveToNext());
        } else {
            Log.e("No number", "");
        }
        dbAdpter.CLOSE();

        autokattaContactAdapter = new BroadcastContactAdapter(getActivity(), contactdata, checkedcontact);
        memberContactslist.setAdapter(autokattaContactAdapter);


        create_broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                if (groupTitle.equals("") || groupTitle.startsWith(" ") && groupTitle.endsWith(" ")) {
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


        return root;
    }


    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(),getString(R.string._404_));
            //   showMessage(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(),getString(R.string.no_response));
            // showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(),getString(R.string.no_response));
            //   showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getActivity(),getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getActivity(),getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "create BroadcastGroup");
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("success_updation")) {
                CustomToast.customToast(getActivity(), "Broadcast Group Updated Successfully");
                MyBroadcastGroupsFragment broadcastGroup = new MyBroadcastGroupsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.broadcast_groups_container, broadcastGroup);
                fragmentTransaction.addToBackStack("createbroadcastgroup");
                fragmentTransaction.commit();
               // getActivity().finish();
            } else {
                CustomToast.customToast(getActivity(), "Broadcast Group Created Successfully");
                MyBroadcastGroupsFragment broadcastGroup = new MyBroadcastGroupsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.broadcast_groups_container, broadcastGroup);
                fragmentTransaction.addToBackStack("createbroadcastgroup");
                fragmentTransaction.commit();
               // getActivity().finish();
            }
        }
    }


    /***********************************************Adapter*******************************************/

    private class BroadcastContactAdapter extends BaseAdapter {


        private ArrayList<Db_AutokattaContactResponse> contactdata = new ArrayList<>();
        private ArrayList<Db_AutokattaContactResponse> contactdataCopy = new ArrayList<>();
        Activity activity;
        private LayoutInflater inflater;
        private ArrayList<Boolean> positionArray;
        private ArrayList<String> contactlist;
        private ArrayList<String> checkedcontact = new ArrayList<>();


        BroadcastContactAdapter(Activity activity, ArrayList<Db_AutokattaContactResponse> contactdata,
                                ArrayList<String> checkedcontact) {
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

            //  viewHolder.checkBox.setFocusable(false);

            viewHolder.checkBox.setChecked(positionArray.get(position));
            //  holder.checkBox.setText(filteredData.get(position));
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
                    else {
                        CustomToast.customToast(getActivity(), "Please select Atleast one Contact");
                        create_broadcast.setEnabled(false);
                    }
                }

            });


            return view;
        }

        private ArrayList checkboxselect() {
            // TODO Auto-generated method stub
            return contactlist;
        }

    }

    static class ViewHolder {
        TextView PersonName, PersonContact;
        CheckBox checkBox;
    }


}
