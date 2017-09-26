package autokatta.com.share;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ModelGroups;
import autokatta.com.response.ProfileGroupResponse;
import retrofit2.Response;

/**
 * Created by ak-005 on 18/6/16.
 */
public class ShareWithGroupFragment extends Fragment implements RequestNotifier {
    String contactnumber, profile_contact;
    String sharedata, keyword;
    int store_id, vehicle_id, product_id, service_id, search_id, status_id, auction_id, loan_id,
            exchange_id;
    ListView grouplist;
    Button btnSend;
    ShareWithGroupAdapter mGroupAdapter;
    String allGroupIDs = "";
    String allGroupNames = "";

    public ShareWithGroupFragment() {
        //Empty Constructor...
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.generic_list_view, container, false);
        contactnumber = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "7841023392");
        grouplist = (ListView) root.findViewById(R.id.generic_list);
        btnSend = (Button) root.findViewById(R.id.send);
        btnSend.setVisibility(View.VISIBLE);
        getData(contactnumber);

        try {
            Bundle b = getArguments();
            sharedata = b.getString("generic_list_view");
            store_id = b.getInt("store_id");
            vehicle_id = b.getInt("vehicle_id");
            product_id = b.getInt("product_id");
            service_id = b.getInt("service_id");
            search_id = b.getInt("search_id");
            profile_contact = b.getString("profile_contact");
            status_id = b.getInt("status_id");
            auction_id = b.getInt("auction_id");
            loan_id = b.getInt("loan_id");
            exchange_id = b.getInt("exchange_id");
            keyword = b.getString("keyword");
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List getGroupIds = mGroupAdapter.getGroupIdsList();
                List getGroupNames = mGroupAdapter.getGroupNamesList();
                allGroupIDs = "";
                allGroupNames = "";
                for (int i = 0; i < getGroupIds.size(); i++) {
                    if (!getGroupIds.get(i).equals("0") && !getGroupNames.get(i).equals("")) {
                        if (allGroupIDs.equals("") && allGroupNames.equals("")) {
                            allGroupIDs = (String) getGroupIds.get(i);
                            allGroupNames = (String) getGroupNames.get(i);
                        } else {
                            allGroupIDs = allGroupIDs + "," + getGroupIds.get(i);
                            allGroupNames = allGroupNames + "," + getGroupNames.get(i);
                        }

                    }
                }

                Log.i("group", "names" + allGroupNames);
                Log.i("group", "ids" + allGroupIDs);
                if (allGroupIDs.equals(""))
                    CustomToast.customToast(getActivity(), "Please select group to share data");
                else {
                    Bundle b = new Bundle();
                    b.putString("generic_list_view", sharedata);
                    b.putInt("store_id", store_id);
                    b.putInt("vehicle_id", vehicle_id);
                    b.putInt("product_id", product_id);
                    b.putInt("service_id", service_id);
                    b.putString("profile_contact", profile_contact);
                    b.putInt("search_id", search_id);
                    b.putInt("status_id", status_id);
                    b.putInt("auction_id", auction_id);
                    b.putInt("loan_id", loan_id);
                    b.putInt("exchange_id", exchange_id);
                    b.putString("number", "");
                    b.putString("keyword", keyword);
                    b.putString("groupname", allGroupNames);
                    b.putString("groupid", allGroupIDs);
                    b.putString("tab", "group");

                    ShareWithCaptionFragment frag = new ShareWithCaptionFragment();
                    frag.setArguments(b);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.shareInApp_container, frag);
                    fragmentTransaction.addToBackStack("ShareWithCaptionFragment");
                    fragmentTransaction.commit();
                }
            }
        });
        return root;
    }

    /*
    Get Group Data...
    */
    private void getData(String loginContact) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.profileGroup(loginContact);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                List<String> alldata = new ArrayList<String>();
                ProfileGroupResponse profileGroupResponse = (ProfileGroupResponse) response.body();
                //My Groups data
                for (ProfileGroupResponse.MyGroup myGroup : profileGroupResponse.getSuccess().getMyGroups()) {
                    ModelGroups modelGroups = new ModelGroups();
                    modelGroups.setId(myGroup.getId());
                    modelGroups.setTitle(myGroup.getTitle());
                    modelGroups.setImage(myGroup.getImage());
                    modelGroups.setGroupCount(myGroup.getGroupcount());
                    modelGroups.setVehicleCount(myGroup.getVehiclecount());

                    int id = myGroup.getId();
                    String title = myGroup.getTitle();
                    String image = myGroup.getImage();
                    if (image == null || image.equals("null") || image.equals(""))
                        alldata.add(title + "=" + id + "=null");
                    else
                        alldata.add(title + "=" + id + "=" + image);
                }
                //Joined Groups data
                for (ProfileGroupResponse.JoinedGroup joinedGroup : profileGroupResponse.getSuccess().getJoinedGroups()) {
                    ModelGroups modelGroups = new ModelGroups();
                    modelGroups.setId(joinedGroup.getId());
                    modelGroups.setTitle(joinedGroup.getTitle());
                    modelGroups.setImage(joinedGroup.getImage());
                    modelGroups.setGroupCount(joinedGroup.getGroupcount());
                    modelGroups.setVehicleCount(joinedGroup.getVehiclecount());

                    int id = joinedGroup.getId();
                    String title = joinedGroup.getTitle();
                    String image = joinedGroup.getImage();

                    if (image == null || image.equals("null") || image.equals(""))
                        alldata.add(title + "=" + id + "=null");
                    else
                        alldata.add(title + "=" + id + "=" + image);
                }
                mGroupAdapter = new ShareWithGroupAdapter(getActivity(), alldata, sharedata, contactnumber, store_id,
                        vehicle_id, product_id, service_id, profile_contact, search_id, status_id, auction_id, loan_id, exchange_id, keyword);
                grouplist.setAdapter(mGroupAdapter);
            } else {
//                if (isAdded())
//                    CustomToast.customToast(getActivity(), getString(R.string._404_));
            }
        } else {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404_));
        }

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
//            if (isAdded())
//                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
//            if (isAdded())
//                CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof ConnectException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "Share With Group Fragment");
        }

    }

    @Override
    public void notifyString(String str) {

    }

}
