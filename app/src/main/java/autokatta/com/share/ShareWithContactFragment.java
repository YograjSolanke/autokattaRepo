package autokatta.com.share;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.database.DbConstants;
import autokatta.com.database.DbOperation;
import autokatta.com.response.Db_AutokattaContactResponse;


/**
 * Created by ak-005 on 18/6/16.
 */
public class ShareWithContactFragment extends Fragment {

    public ShareWithContactFragment() {
        //empty Constructor...
    }

    SharedPreferences prefs;
    ArrayList<Db_AutokattaContactResponse> contactdata = new ArrayList<>();
    public static final String MyContactPREFERENCES = "contact No";

    String contactnumber, sharedata, storecontact,profile_contact ;
int store_id,vehicle_id, product_id, service_id,
        search_id, status_id, auction_id, loan_id, exchange_id;
    String keyword = "";
    ListView contactlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.generic_list_view, container, false);
        prefs = getActivity().getSharedPreferences(MyContactPREFERENCES, Context.MODE_PRIVATE);
        contactnumber = prefs.getString("loginContact", "");
        contactlist = (ListView) root.findViewById(R.id.generic_list);
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
            Log.i("key", "->" + keyword);

        } catch (Exception e) {
            e.printStackTrace();
        }

        DbOperation dbAdpter = new DbOperation(getActivity());
        dbAdpter.OPEN();
        Cursor cursor = dbAdpter.getAutokattaContact();
        if (cursor.getCount() > 0) {
            contactdata.clear();
            cursor.moveToFirst();
            do {
                Log.i(DbConstants.TAG, cursor.getString(cursor.getColumnIndex(DbConstants.userName)) + " = " + cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
                Db_AutokattaContactResponse obj = new Db_AutokattaContactResponse();

                obj.setContact(cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
                obj.setUsername(cursor.getString(cursor.getColumnIndex(DbConstants.userName)));
                obj.setMystatus(cursor.getString(cursor.getColumnIndex(DbConstants.myStatus)));
                obj.setFollowstatus(cursor.getString(cursor.getColumnIndex(DbConstants.followStatus)));
                obj.setUserprofile(cursor.getString(cursor.getColumnIndex(DbConstants.profilePic)));

                contactdata.add(obj);
            } while (cursor.moveToNext());
        }
        dbAdpter.CLOSE();
        ShareWithContactAdapter adapter = new ShareWithContactAdapter(getActivity(), contactdata, sharedata, store_id, contactnumber,
                vehicle_id, product_id, service_id, profile_contact, search_id, status_id, auction_id, loan_id, exchange_id, keyword);
        contactlist.setAdapter(adapter);

        return root;
    }

}
