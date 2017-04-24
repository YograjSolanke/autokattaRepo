package autokatta.com.fragment;

import android.content.Context;
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
import autokatta.com.groups.GroupVehicleList;
import autokatta.com.groups.MemberListFragment;

/**
 * Created by ak-001 on 25/3/17.
 */

public class GroupNextTabFragment extends Fragment {
    View mGroupNext;
    MemberListFragment memberListFragment =new MemberListFragment();
            GroupVehicleList groupVehicleList=new GroupVehicleList();

    String group_id="";
    String Grp_id, backGrpId="";
    int tab=0;

    Bundle bundle = new Bundle();
    String call,grpType;
    int pos=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGroupNext = inflater.inflate(R.layout.fragment_group_next_tab, container, false);

        ViewPager mViewPager = (ViewPager) mGroupNext.findViewById(R.id.groups_next_tab_viewpager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        TabLayout tabLayout = (TabLayout) mGroupNext.findViewById(R.id.groups_next_tab);
        tabLayout.setupWithViewPager(mViewPager);
group_id=getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("group_id","");
        bundle = getArguments();
        if (bundle!=null) {


            grpType = bundle.getString("grouptype");
            System.out.println("Grp Id In Group Tab On Back Button Pressed From Bundle:" + backGrpId);

            call = bundle.getString("call");


            System.out.println("callllllllllllllllllllll" + call);

            if (call.equalsIgnoreCase("notification")) {
                tab = bundle.getInt("tab");

            }

            group_id = bundle.getString("id");
            System.out.println("Group Id From Bundle in Group Tab Fragment:" + group_id);
            grpType = bundle.getString("grouptype");

            Bundle bundle1 = new Bundle();
            bundle1.putString("id", group_id);
            bundle1.putString("grouptype", grpType);
            bundle1.putString("call", call);

            memberListFragment.setArguments(bundle1);
            groupVehicleList.setArguments(bundle1);
        }
        return mGroupNext;
    }

    private void setupViewPager(ViewPager mViewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getFragmentManager());
        tabAdapterName.addFragment(memberListFragment, "Group Members");
        tabAdapterName.addFragment(groupVehicleList, "Group Vehicles");
        mViewPager.setAdapter(tabAdapterName);
    }
}
