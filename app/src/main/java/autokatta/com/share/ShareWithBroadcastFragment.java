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
import autokatta.com.response.MyBroadcastGroupsResponse;
import retrofit2.Response;

/**
 * Created by ak-003 on 21/2/17.
 */

public class ShareWithBroadcastFragment extends Fragment implements RequestNotifier {
    String contactnumber, store_id, vehicle_id, product_id, service_id, profile_contact, search_id, status_id, auction_id,
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
            store_id = b.getString("store_id");
            vehicle_id = b.getString("vehicle_id");
            product_id = b.getString("product_id");
            service_id = b.getString("service_id");
            search_id = b.getString("search_id");
            profile_contact = b.getString("profile_contact");
            status_id = b.getString("status_id");
            auction_id = b.getString("auction_id");
            loan_id = b.getString("loan_id");
            exchange_id = b.getString("exchange_id");
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
//                    Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
                }
            } else {
//                Snackbar.make(getView(), getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
            }
        } else {
//            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }
    }


    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            //  Snackbar.make(getView(), getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            //  Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            //  Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
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
