package autokatta.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.adapter.GroupContactListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetRegisteredContactsResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 20/4/17.
 */

public class GroupContactFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,RequestNotifier {
View mGcontact;
    String mContact,mGroup_id,grp_id,call,grp1_id,pass_id;
    ListView lv;
    ApiCall mApiCall;
    String receiver_contact;
    GroupContactListAdapter CntctListadapter;
    Bundle args = new Bundle();
    public static Button AddContacts;
    EditText inputSearch;
    ArrayList<GetRegisteredContactsResponse.Success> cntlist = new ArrayList<>();

    boolean flag = true;
    String allcontacts = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGcontact = inflater.inflate(R.layout.group_contact_list, container, false);

        mContact=getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");
        mGroup_id=getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("group_id", "");
         mApiCall=new ApiCall(getActivity(),this);

        AddContacts = (Button) mGcontact.findViewById(R.id.add_contacts);
        inputSearch = (EditText) mGcontact.findViewById(R.id.inputSearch);
        lv = (ListView) mGcontact.findViewById(R.id.list_view);



        mApiCall.getRegisteredContacts();

        grp_id = args.getString("GrpId", "");
        System.out.println("Group Id From Create Fragment Bundle In Group Contact Fragment:" + grp_id);

        call = args.getString("call", "");

        grp1_id = args.getString("id", "");
        System.out.println("Group Id From Grp Member List Bundle In Group Contct Frag:" + grp1_id);


        if (grp_id == "") {
            pass_id = grp1_id;
        } else if (grp1_id == "") {
            pass_id = grp_id;
        }



        AddContacts.setEnabled(false);

        AddContacts.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                final ArrayList<String> GetList = args.getStringArrayList("list");
                System.out.println("List From Web Service: " + GetList);

                cntlist = CntctListadapter.checkboxselect();

                for (int i = 0; i < cntlist.size(); i++) {
                    if (!cntlist.get(i).equals("0")) {
                        if (allcontacts.equals(""))
                            allcontacts = String.valueOf(cntlist.get(i));
                        else
                            allcontacts = allcontacts + "," + cntlist.get(i);

                    }
                }

                if (allcontacts.equalsIgnoreCase("")) {
                    Snackbar.make(v,
                            "Please add atleast one contact",
                            Snackbar.LENGTH_SHORT).show();
                    flag = false;

                } else if (allcontacts.contains(mContact)) {
                    Snackbar.make(v,
                            "Please check the number",
                            Snackbar.LENGTH_SHORT).show();
                    flag = false;
                }

                //Existing Group
                else if (call.equalsIgnoreCase("existGroup")) {

                    //For Group Which Doesn't Have Any Contact No In WebService
                    if (GetList.size() == 0) {
                        flag = true;

                    }

                    //If Group Already Contains Selected Contact
                    else {
                        for (int i = 0; i < GetList.size(); i++) {
                            String no = GetList.get(i);

                            String[] parts = allcontacts.split(",");

                            for (int j = 0; j < parts.length; j++) {
                                if (parts[j].contains(no)) {

                                    Snackbar.make(v,
                                            "Sorry..No Is Already added in Group",
                                            Snackbar.LENGTH_LONG).show();
                                    flag = false;
                                }
                            }
                        }
                    }
                }
                if (!flag) {
/*

                    GroupTabFrag frag = new GroupTabFrag();
                    args.putString("id", pass_id);
                    args.putString("call", "groupContact");
                    args.putString("grouptype", "MyGroups");
                    frag.setArguments(args);

                    System.out.println("Back To Fragment From Group Contact Fragment:\n");
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, frag);
                    // fragmentTransaction.addToBackStack("grouptabfrag");

                    //content_frame is a Id  of Frame Layout
                    fragmentTransaction.commit();
*/

                }

                if (flag) {
                    if (call.equalsIgnoreCase("newGroup")) {
                        allcontacts = allcontacts + "," + mContact;
                    }

                   mApiCall.addContactInGroup(pass_id,allcontacts);
                    String[] parts = allcontacts.split(",");

                    for (int i = 0; i < parts.length; i++) {

                        receiver_contact = parts[i];
                        if (!receiver_contact.equalsIgnoreCase(mContact)) {
                            mApiCall.groupLikeNotification(pass_id,mContact,receiver_contact,"3");
                        }
                    }

/*

                    GroupTabFrag frag2 = new GroupTabFrag();    // Call Another Fragment
                    args.putString("id", pass_id);
                    args.putString("call", "groupContact");
                    args.putString("grouptype", "MyGroups");
                    frag2.setArguments(args);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, frag2);
                    fragmentTransaction.commit();
*/


                    System.out.println("All contacts ::" + allcontacts);
                }
            }


        });


        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text [" + s + "]");

                CntctListadapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });





        return mGcontact;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response!=null){
            GetRegisteredContactsResponse mGetRegisteredContactsResponse = (GetRegisteredContactsResponse) response.body();
            for (GetRegisteredContactsResponse.Success contactRegistered : mGetRegisteredContactsResponse.getSuccess()) {
                contactRegistered.setContact(contactRegistered.getContact());
                contactRegistered.setUsername(contactRegistered.getUsername());
                cntlist.add(contactRegistered);
            }

            CntctListadapter = new GroupContactListAdapter(getActivity(), cntlist);
            lv.setAdapter(CntctListadapter);

        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
if(str!=null)
{
    if (str.equals("success_add_contact"))
    {
        CustomToast.customToast(getActivity(),"Contact Added Successfully");
    }else
    {
        CustomToast.customToast(getActivity(),"Notification Sent Successfully");
    }
}

    }
}
