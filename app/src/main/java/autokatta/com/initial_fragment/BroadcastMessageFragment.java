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
import autokatta.com.fragment.BroadcastReceivedFragment;
import autokatta.com.fragment.BroadcastSendFragment;

/**
 * Created by ak-004 on 7/4/17.
 */

public class BroadcastMessageFragment extends Fragment {

    public BroadcastMessageFragment() {
        //empty fragment...
    }

    View view;
    TabLayout broadcast_message_tab;
    ViewPager broadcast_message_viewpager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_broadcast_message, container, false);
        broadcast_message_tab = (TabLayout) view.findViewById(R.id.broadcast_message_tab);
        broadcast_message_viewpager = (ViewPager) view.findViewById(R.id.broadcast_message_viewpager);

        if (broadcast_message_viewpager != null) {
            //custom method
            setupViewPager(broadcast_message_viewpager);
        }

        broadcast_message_tab.setupWithViewPager(broadcast_message_viewpager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getChildFragmentManager());
        tabAdapterName.addFragment(new BroadcastReceivedFragment(), "Received");
        tabAdapterName.addFragment(new BroadcastSendFragment(), "Sent");
        viewPager.setAdapter(tabAdapterName);
    }
}
