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

import java.net.SocketTimeoutException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import retrofit2.Response;

/**
 * Created by ak-002 on 10/9/16.
 */
public class ShareWithCaption extends Fragment implements RequestNotifier {

    public ShareWithCaption() {
    }

    EditText editShare;
    TextView txtreceiver;
    ListView sharelist;
    String editdata = "";
    String contactnumber, sharedata, storecontact, store_id, vehicle_id, product_id, service_id, profile_contact,
            search_id, status_id, auction_id, loan_id, exchange_id, keyword, tab, groupid = "", broadcastgroupid = "",
            groupname, number, name, layoutNumber = "";

    Button sharebutton;
    ApiCall mApiCall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.sharewithcaption, container, false);

        contactnumber = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "7841023392");
        System.out.print("Contact in share with contact " + contactnumber);

        sharebutton = (Button) root.findViewById(R.id.sharebutton);
        sharelist = (ListView) root.findViewById(R.id.listshare);
        editShare = (EditText) root.findViewById(R.id.editShare);
        txtreceiver = (TextView) root.findViewById(R.id.txtreceiver);

        mApiCall = new ApiCall(getActivity(), this);
        Bundle b = getArguments();
        sharedata = b.getString("sharewithcontact");
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


        Log.i("Share", "layout" + layoutNumber);

        sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editdata = editShare.getText().toString();

                /*System.out.println("dataaaaaaaaa=========******======11111" + editdata);

                if (tab.equals("contact")) {
                    new ShareTask().execute();
                    shareTask(layoutNumber);

                    Toast.makeText(getActivity(), "share successfully to " + name, Toast.LENGTH_SHORT).show();
                }
                if (tab.equals("group")) {
                    new ShareTask1().execute();

                    Toast.makeText(getActivity(), "share successfully to " + groupname + "group", Toast.LENGTH_SHORT).show();
                }
                if (tab.equals("broadcastgroup")) {
                    shareInBroadcast();
                }*/
                shareTask(layoutNumber);
            }
        });


        ShareWithCaptionAdapter adapter = new ShareWithCaptionAdapter(getActivity(), contactnumber, sharedata, store_id, keyword, product_id, service_id,
                vehicle_id, search_id, status_id, profile_contact, auction_id, loan_id, exchange_id);
        sharelist.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return root;
    }


    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Toast.makeText(getActivity(), getString(R.string._404), Toast.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else {
            Log.i("Check Class-"
                    , "Share With Caption");
        }
    }

    @Override
    public void notifyString(String str) {

    }

    private void shareTask(String layoutNumber) {
        mApiCall.shareTaskInApp(contactnumber, number, groupid, broadcastgroupid, editdata, layoutNumber, profile_contact,
                store_id, vehicle_id, product_id, service_id, status_id, search_id, auction_id, loan_id, exchange_id);
    }


}
