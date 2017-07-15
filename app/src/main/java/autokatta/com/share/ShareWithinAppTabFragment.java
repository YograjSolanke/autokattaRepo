package autokatta.com.share;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;


/**
 * Created by ak-005 on 18/6/16.
 */
public class ShareWithinAppTabFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    SharedPreferences prefs;
    String contactnumber;
    String sharedata, storecontact, vehicle_id, task_id, product_id, service_id, profile_contact,
            search_id, status_id, auction_id, exchange_id, loan_id;
    int store_id;
    String keyword;

    public ShareWithinAppTabFragment() {
        //empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater infl, ViewGroup container, Bundle savedInstanceState) {
        View root = infl.inflate(R.layout.fragment_share_withinapp_tab, container, false);
        prefs = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE);
        contactnumber = prefs.getString("loginContact", "");
        store_id = prefs.getInt("Share_store_id", 0);
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

        System.out.println("Data in share Tab before" + "-" +
                "Keyword =" + keyword + "-" +
                "Loan id =" + loan_id + "-" +
                "Exchange id =" + exchange_id);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Share_auction_id", "");
        editor.putString("Share_loan_id", "");
        editor.putString("Share_exchange_id", "");
        editor.apply();

        System.out.println("Data in share Tab after" + "\n" +
                "Keyword =" + keyword + "\n" +
                "Loan id =" + loan_id + "\n" +
                "Exchange id =" + exchange_id);

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
            b.putInt("store_id", store_id);
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

            getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit()
                    .putString("Share_keyword", "").apply();

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