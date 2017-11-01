package autokatta.com.share;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

/**
 * Created by ak-002 on 10/9/16.
 */
public class ShareWithCaptionFragment extends Fragment implements RequestNotifier {

    EditText editShare;
    TextView txtreceiver;
    ListView sharelist;
    String editdata = "";
    String sharedata, storecontact, contactnumber, profile_contact,
            keyword, tab, groupname, number = "", name, groupIds = "", broadcastGroupIds = "";
    int store_id, vehicle_id, product_id, service_id, broadcastgroupid = 0, groupid = 0,
            layoutNumber, search_id, status_id, auction_id, loan_id, exchange_id;
    Button sharebutton;
    ApiCall mApiCall;
    KProgressHUD hud;

    public ShareWithCaptionFragment() {
        //Empty Constructor...
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_share_caption, container, false);
        contactnumber = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "7841023392");
        sharebutton = (Button) root.findViewById(R.id.sharebutton);
        sharelist = (ListView) root.findViewById(R.id.listshare);
        editShare = (EditText) root.findViewById(R.id.editShare);
        txtreceiver = (TextView) root.findViewById(R.id.txtreceiver);

        mApiCall = new ApiCall(getActivity(), this);
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
        number = b.getString("number", "");
        name = b.getString("name");
        keyword = b.getString("keyword");
        tab = b.getString("tab");
        groupIds = b.getString("groupid", "");
        broadcastGroupIds = b.getString("broadcastgroupid", "");
        groupname = b.getString("groupname");

        System.out.println("Data in caption fragment" + "-" +
                "Keyword =" + keyword + "-" +
                "Loan id =" + loan_id + "-" +
                "Exchange id =" + exchange_id);

        if (!profile_contact.equals(""))
            layoutNumber = 1;

        else if (store_id != 0)
            layoutNumber = 2;

        else if (vehicle_id != 0)
            layoutNumber = 4;

        else if (product_id != 0)
            layoutNumber = 5;

        else if (service_id != 0)
            layoutNumber = 6;

        else if (status_id != 0)
            layoutNumber = 7;

        else if (search_id != 0)
            layoutNumber = 8;

        else if (auction_id != 0)
            layoutNumber = 9;

        else if (loan_id != 0)
            layoutNumber = 10;

        else if (exchange_id != 0)
            layoutNumber = 11;


        if (tab.equalsIgnoreCase("contact"))
            txtreceiver.setText(name);
        else
            txtreceiver.setText(groupname + " " + "Group");

        sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTask(layoutNumber, editShare.getText().toString());
            }
        });


        ShareWithCaptionAdapter adapter = new ShareWithCaptionAdapter(getActivity(), contactnumber, sharedata, store_id, keyword, product_id, service_id,
                vehicle_id, search_id, status_id, profile_contact, auction_id, loan_id, exchange_id);
        sharelist.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return root;
    }


    private void shareTask(int layoutNumber, String editdata) {

        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();
        mApiCall.shareTaskInApp(contactnumber, number, groupIds, broadcastGroupIds, editdata, layoutNumber, profile_contact,
                store_id, vehicle_id, product_id, service_id, status_id, search_id, auction_id, loan_id, exchange_id);
    }

    @Override
    public void notifySuccess(Response<?> response) {

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
                    , "Share With Caption Fragment");
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("success_share")) {
                hud.dismiss();
                if (isAdded())
                    CustomToast.customToast(getActivity(), "Shared successfully");
                getActivity().finish();
            }
        }

    }

}
