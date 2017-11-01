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
import autokatta.com.response.MyBroadcastGroupsResponse;
import retrofit2.Response;

/**
 * Created by ak-003 on 21/2/17.
 */

public class ShareWithBroadcastFragment extends Fragment implements RequestNotifier {
    String contactnumber, profile_contact;
    int store_id, vehicle_id, product_id, service_id, search_id, status_id, auction_id,
            loan_id, exchange_id;
    String sharedata, keyword;

    ListView grouplist;
    List<MyBroadcastGroupsResponse.Success> broadcastGroupsResponseList = new ArrayList<>();
    String allGroupIDs = "";
    String allGroupNames = "";
    Button btnSend;
    ShareWithBroadcastAdapter mBroadcastAdapter;

    public ShareWithBroadcastFragment() {
        //empty constructor...
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.generic_list_view, container, false);
        contactnumber = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "");
        grouplist = (ListView) root.findViewById(R.id.generic_list);
        btnSend = (Button) root.findViewById(R.id.send);
        btnSend.setVisibility(View.VISIBLE);
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.MyBroadcastGroups(contactnumber);
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
                List getGroupIds = mBroadcastAdapter.getGroupIdsList();
                List getGroupNames = mBroadcastAdapter.getGroupNamesList();
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

                Log.i("BroadcastGroup", "names" + allGroupNames);
                Log.i("BroadcastGroup", "ids" + allGroupIDs);

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
                    b.putString("broadcastgroupid", allGroupIDs);
                    b.putString("tab", "broadcastgroup");

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


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                broadcastGroupsResponseList.clear();
                MyBroadcastGroupsResponse myBroadcastGroupsResponse = (MyBroadcastGroupsResponse) response.body();
                if (!myBroadcastGroupsResponse.getSuccess().isEmpty()) {
                    for (MyBroadcastGroupsResponse.Success success : myBroadcastGroupsResponse.getSuccess()) {
                        success.setGroupId(success.getGroupId());
                        success.setGroupTitle(success.getGroupTitle());
                        success.setGroupOwner(success.getGroupOwner());
                        success.setGroupMemberContacts(success.getGroupMemberContacts());
                        success.setGroupStatus(success.getGroupStatus());
                        success.setGrpMemberCount(success.getGrpMemberCount());
                        success.setGrpCreatedDate(success.getGrpCreatedDate());
                        broadcastGroupsResponseList.add(success);
                    }
                    mBroadcastAdapter = new ShareWithBroadcastAdapter(getActivity(), broadcastGroupsResponseList, sharedata, contactnumber, store_id,
                            vehicle_id, product_id, service_id, profile_contact, search_id, status_id, auction_id, loan_id, exchange_id, keyword);
                    grouplist.setAdapter(mBroadcastAdapter);
                    mBroadcastAdapter.notifyDataSetChanged();
                } else {
                    if (isAdded())
                        CustomToast.customToast(getActivity(), getString(R.string.no_response));
                }
            } else {
                // CustomToast.customToast(getActivity(), getString(R.string._404_));
            }
        } else {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }


    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            //  CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            // CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));

        } else if (error instanceof UnknownHostException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "Share With Broadcast Fragment");
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
