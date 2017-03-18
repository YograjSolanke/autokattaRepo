package autokatta.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapter;

/**
 * Created by ak-001 on 17/3/17.
 */

public class SocialFragment extends Fragment {

    View mSocialFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSocialFragment = inflater.inflate(R.layout.fragment_social_fragment, container, false);

        ViewPager viewPager = (ViewPager) mSocialFragment.findViewById(R.id.social_viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) mSocialFragment.findViewById(R.id.social_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);
        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_launcher);

        return mSocialFragment;
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new WallNotificationFragment());
        adapter.addFragment(new WallNotificationFragment());
        viewPager.setAdapter(adapter);
    }
}
