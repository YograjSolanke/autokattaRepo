package autokatta.com.fragment;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import autokatta.com.R;
import autokatta.com.adapter.BroadcastContactAdapter;
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
        mApiCall = new ApiCall(ctx, this);
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

                obj.setContact(cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
                obj.setUsername(cursor.getString(cursor.getColumnIndex(DbConstants.userName)));
                contactdata.add(obj);
            } while (cursor.moveToNext());
        }else{
            Log.e("No number","");
        }
        dbAdpter.CLOSE();

        autokattaContactAdapter = new BroadcastContactAdapter(getActivity(), contactdata, checkedcontact);
        memberContactslist.setAdapter(autokattaContactAdapter);

        /*create_broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Hiii Clicked11111111111111111111111111111111111111111111111111111111111");
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

                if (TextUtils.isEmpty(groupTitle)) {
                    Toast.makeText(getActivity(), "Please enter group Title",
                            Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Toast.makeText(getActivity(), "Now web service call", Toast.LENGTH_LONG).show();
                    System.out.println("group id &&&&&ContactList:" + finalContacts);
                    mApiCall.createBroadcastgroup(groupTitle, contact, finalContacts, calltype, group_id);
                    //  createBroadcastGroup(groupTitle,finalContacts,group_id);
                }

            }
        });*/

        return root;
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("success")) {
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
}