package autokatta.com.initial_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.fragment.AutokattaContactFragment;
import autokatta.com.fragment.InviteContactFragment;

/**
 * Created by ak-003 on 28/3/17.
 */

public class MyAutokattaContactFragmentTab extends Fragment {
    View mAutokattaContact;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAutokattaContact = inflater.inflate(R.layout.fragment_autokatta_contact_tab, container, false);

        ViewPager mViewPager = (ViewPager) mAutokattaContact.findViewById(R.id.autokatta_contact_viewpager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        TabLayout tabLayout = (TabLayout) mAutokattaContact.findViewById(R.id.autokatta_contact_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return mAutokattaContact;
    }

    private void setupViewPager(ViewPager mViewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getFragmentManager());
        tabAdapterName.addFragment(new AutokattaContactFragment(), "My Autokatta Contact");
        tabAdapterName.addFragment(new InviteContactFragment(), "Invite Contact");
        mViewPager.setAdapter(tabAdapterName);
    }
}
