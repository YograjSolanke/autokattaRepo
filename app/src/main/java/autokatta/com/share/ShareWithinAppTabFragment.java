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
    String sharedata, storecontact,task_id,profile_contact;
    int store_id ,vehicle_id,  product_id, service_id,
    search_id, status_id, auction_id, exchange_id, loan_id;
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
        vehicle_id = prefs.getInt("Share_vehicle_id", 0);
        product_id = prefs.getInt("Share_product_id", 0);
        service_id = prefs.getInt("Share_service_id", 0);
        profile_contact = prefs.getString("Share_profile_contact", "");
        search_id = prefs.getInt("Share_search_id", 0);
        status_id = prefs.getInt("Share_status_id", 0);
        auction_id = prefs.getInt("Share_auction_id", 0);
        loan_id = prefs.getInt("Share_loan_id", 0);
        exchange_id = prefs.getInt("Share_exchange_id", 0);
        keyword = prefs.getString("Share_keyword", "");

        System.out.println("Data in share Tab before" + "-" +
                "Keyword =" + keyword + "-" +
                "Loan id =" + loan_id + "-" +
                "Exchange id =" + exchange_id);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Share_auction_id", 0);
        editor.putInt("Share_loan_id", 0);
        editor.putInt("Share_exchange_id", 0);
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
            b.putInt("vehicle_id", vehicle_id);
            b.putInt("product_id", product_id);
            b.putInt("service_id", service_id);
            b.putString("profile_contact", profile_contact);
            b.putInt("search_id", search_id);
            b.putInt("status_id", status_id);
            b.putInt("auction_id", auction_id);
            b.putInt("loan_id", loan_id);
            b.putInt("exchange_id", exchange_id);
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