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
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import retrofit2.Response;

/**
 * Created by ak-002 on 10/9/16.
 */
public class ShareWithCaptionFragment extends Fragment implements RequestNotifier {

    EditText editShare;
    TextView txtreceiver;
    ListView sharelist;
    String editdata = "";
    String contactnumber, sharedata, storecontact, store_id, vehicle_id, product_id, service_id, profile_contact,
            search_id, status_id, auction_id, loan_id, exchange_id, keyword, tab, groupid = "", broadcastgroupid = "",
            groupname, number, name, layoutNumber = "";

    Button sharebutton;
    ApiCall mApiCall;

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
        number = b.getString("number");
        name = b.getString("name");
        keyword = b.getString("keyword");
        tab = b.getString("tab");
        groupid = b.getString("groupid");
        broadcastgroupid = b.getString("broadcastgroupid");
        groupname = b.getString("groupname");

        if (!profile_contact.equals(""))
            layoutNumber = "1";

        else if (!store_id.equals(""))
            layoutNumber = "2";

        else if (!vehicle_id.equals(""))
            layoutNumber = "4";

        else if (!product_id.equals(""))
            layoutNumber = "5";

        else if (!service_id.equals(""))
            layoutNumber = "6";

        else if (!status_id.equals(""))
            layoutNumber = "7";

        else if (!search_id.equals(""))
            layoutNumber = "8";

        else if (!auction_id.equals(""))
            layoutNumber = "9";

        else if (!loan_id.equals(""))
            layoutNumber = "10";

        else if (!exchange_id.equals(""))
            layoutNumber = "11";


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


    private void shareTask(String layoutNumber, String editdata) {
        mApiCall.shareTaskInApp(contactnumber, number, groupid, broadcastgroupid, editdata, layoutNumber, profile_contact,
                store_id, vehicle_id, product_id, service_id, status_id, search_id, auction_id, loan_id, exchange_id);
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            // Snackbar.make(getView(), getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            //Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            // Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
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
                    , "Share With Caption");
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("success")) {
                Toast.makeText(getActivity(), "Shared successfully", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                /*startActivity(new Intent(getActivity(), AutokattaMainActivity.class));*/
            }
        }

    }

}
