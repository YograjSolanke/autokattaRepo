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
import autokatta.com.response.ModelGroups;
import autokatta.com.response.ProfileGroupResponse;
import retrofit2.Response;

/**
 * Created by ak-005 on 18/6/16.
 */
public class ShareWithGroupFragment extends Fragment implements RequestNotifier {
    String contactnumber, storecontact, profile_contact;
    String sharedata, keyword;
    int store_id, vehicle_id, product_id, service_id,search_id, status_id, auction_id, loan_id,
            exchange_id;
    ListView grouplist;

    public ShareWithGroupFragment() {
        //Empty Constructor...
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.generic_list_view, container, false);
        contactnumber = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "7841023392");
        grouplist = (ListView) root.findViewById(R.id.generic_list);
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
                    if (image.equals(null) || image.equals("null") || image.equals(""))
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

                    if (image.equals(null) || image.equals("null") || image.equals(""))
                        alldata.add(title + "=" + id + "=null");
                    else
                        alldata.add(title + "=" + id + "=" + image);
                }
                ShareWithGroupAdapter adapter = new ShareWithGroupAdapter(getActivity(), alldata, sharedata, contactnumber, store_id,
                        vehicle_id, product_id, service_id, profile_contact, search_id, status_id, auction_id, loan_id, exchange_id, keyword);
                grouplist.setAdapter(adapter);
            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404_));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string._404_));
        }

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string._404_));
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
                    , "Share With Group");
        }

    }

    @Override
    public void notifyString(String str) {

    }

}
