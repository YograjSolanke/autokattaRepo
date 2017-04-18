package autokatta.com.share;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;


/**
 * Created by ak-005 on 18/6/16.
 */
public class ShareWithinAppTabFragment extends Fragment {

    public ShareWithinAppTabFragment() {
        //empty constructor
    }

    TabLayout tabLayout;
    ViewPager viewPager;

    SharedPreferences prefs;


    String contactnumber;
    String sharedata, store_id, storecontact, vehicle_id, task_id, product_id, service_id, profile_contact,
            search_id, status_id, auction_id, exchange_id, loan_id;

    String keyword;

    @Override
    public View onCreateView(LayoutInflater infl, ViewGroup container, Bundle savedInstanceState) {

        View root = infl.inflate(R.layout.fragment_share_withinapp_tab, container, false);

        prefs = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE);

        contactnumber = prefs.getString("loginContact", "");
        store_id = prefs.getString("Share_store_id", "");
        sharedata = prefs.getString("Share_sharedata", "");
        vehicle_id = prefs.getString("Share_vehicle_id", "");
        product_id = prefs.getString("Share_product_id", "");
        service_id = prefs.getString("Share_service_id", "");
        profile_contact = prefs.getString("Share_profile_contact", "");
        search_id = prefs.getString("Share_search_id", "");
        status_id = prefs.getString("Share_status_id", "");
        auction_id = prefs.getString("Share_auction_id", "");
        loan_id = prefs.getString("Share_loan_id", "");
        exchange_id = prefs.getString("Share_exchange_id", "");
        keyword = prefs.getString("Share_keyword", "");
        Log.i("KEYWORD", "->" + keyword);

        Log.i("Share", "Contact" + contactnumber);
        Log.i("Share", "StoreID" + store_id);
        Log.i("Share", "sharedata" + sharedata);
        Log.i("Share", "vehicleid" + vehicle_id);
        Log.i("Share", "productid" + product_id);
        Log.i("Share", "serviceid" + service_id);
        Log.i("Share", "searchid" + search_id);
        Log.i("Share", "statusid" + status_id);
        Log.i("Share", "auctionid" + auction_id);
        Log.i("Share", "loanid" + loan_id);
        Log.i("Share", "exchangeid" + exchange_id);
        Log.i("Share", "profilecontact" + profile_contact);


        tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        viewPager = (ViewPager) root.findViewById(R.id.viewpager);


        viewPager.setAdapter(new ShareWithinAppAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });


        return root;


    }

    private class ShareWithinAppAdapter extends FragmentPagerAdapter {

        ShareWithinAppAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle b = new Bundle();
            b.putString("generic_list_view", sharedata);
            b.putString("store_id", store_id);
            b.putString("vehicle_id", vehicle_id);
            b.putString("product_id", product_id);
            b.putString("service_id", service_id);
            b.putString("profile_contact", profile_contact);
            b.putString("search_id", search_id);
            b.putString("status_id", status_id);
            b.putString("auction_id", auction_id);
            b.putString("loan_id", loan_id);
            b.putString("exchange_id", exchange_id);
            b.putString("keyword", keyword);


            switch (position) {
                case 0:

                    ShareWithContactFragment fr = new ShareWithContactFragment();

                    fr.setArguments(b);

                    return fr;

                case 1:

                    ShareWithGroupFragment fr1 = new ShareWithGroupFragment();

                    fr1.setArguments(b);

                    return fr1;

                case 2:

                    ShareWithBroadcastFragment fr2 = new ShareWithBroadcastFragment();

                    fr2.setArguments(b);

                    return fr2;

            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Contact(s)";
                case 1:
                    return "Group(s)";
                case 2:
                    return "Broadcast group(s)";

            }
            return null;
        }
    }


}