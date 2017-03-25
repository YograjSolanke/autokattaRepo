package autokatta.com.other;

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
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.fragment.GroupVehicleList;
import autokatta.com.fragment.MemberListFragment;
import autokatta.com.fragment.WallNotificationFragment;

/**
 * Created by ak-001 on 25/3/17.
 */

public class GroupNextFragment extends Fragment {
    View mGroupNextFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGroupNextFragment = inflater.inflate(R.layout.container_group, container, false);

        return mGroupNextFragment;
    }

}
