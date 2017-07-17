package autokatta.com.share;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    String contactnumber,profile_contact ;
    int store_id ,vehicle_id, product_id, service_id, search_id, status_id, auction_id,
    loan_id, exchange_id;
    String sharedata, keyword;

    ListView grouplist;
    List<MyBroadcastGroupsResponse.Success> broadcastGroupsResponseList = new ArrayList<>();

    public ShareWithBroadcastFragment() {
        //empty constructor...
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.generic_list_view, container, false);
        contactnumber = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", null);
        grouplist = (ListView) root.findViewById(R.id.generic_list);
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
                    ShareWithBroadcastAdapter adapter = new ShareWithBroadcastAdapter(getActivity(), broadcastGroupsResponseList, sharedata, contactnumber, store_id,
                            vehicle_id, product_id, service_id, profile_contact, search_id, status_id, auction_id, loan_id, exchange_id, keyword);
                    grouplist.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    CustomToast.customToast(getActivity(), getString(R.string.no_response));
                }
            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404_));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }


    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            //mNoInternetIcon.setVisibility(View.VISIBLE);
//            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Go Online", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
//                        }
//                    });
//            // Changing message text color
//            snackbar.setActionTextColor(Color.RED);
//            // Changing action button text color
//            View sbView = snackbar.getView();
//            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//            textView.setTextColor(Color.YELLOW);
//            snackbar.show();
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            //mNoInternetIcon.setVisibility(View.VISIBLE);
//            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Go Online", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
//                        }
//                    });
//            // Changing message text color
//            snackbar.setActionTextColor(Color.RED);
//            // Changing action button text color
//            View sbView = snackbar.getView();
//            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//            textView.setTextColor(Color.YELLOW);
//            snackbar.show();
        } else {
            Log.i("Check Class-"
                    , "Share With Broadcast");
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
