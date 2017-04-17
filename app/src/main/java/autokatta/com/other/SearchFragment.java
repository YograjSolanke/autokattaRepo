package autokatta.com.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.fragment.JoinedGroupsFragment;
import autokatta.com.fragment.MyGroupsFragment;

/**
 * Created by ak-001 on 17/4/17.
 */

public class SearchFragment extends Fragment {
    View mSearchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchView = inflater.inflate(R.layout.activity_search, container, false);
       /* ViewPager mviewPager = (ViewPager) mSearchView.findViewById(R.id.search_viewpager);
        if (mviewPager != null) {
            setupViewPager(mviewPager);
        }
        TabLayout tabLayout = (TabLayout) mSearchView.findViewById(R.id.search_tabs);
        tabLayout.setupWithViewPager(mviewPager);*/
        return mSearchView;
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getFragmentManager());
        tabAdapterName.addFragment(new MyGroupsFragment(), "My Groups");
        tabAdapterName.addFragment(new JoinedGroupsFragment(), "Joined Groups");
        viewPager.setAdapter(tabAdapterName);
    }
}
