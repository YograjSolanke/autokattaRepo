package autokatta.com.groups;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;

/**
 * Created by ak-005 on 22/5/17.
 */

public class MemberDetailTabs extends Fragment {
    View mMemberdetails;
    MemberDetailsMemberFragment memberDetailsMemberFragment;
    MemberVehicleListFragment groupVehicleList;
    MemberCommunicationFragment memberCommunicationFragment;
    MemberProductFragment memberProductFragment;
    MemberServicesFragment memberServicesFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMemberdetails=inflater.inflate(R.layout.member_details_tab_fragment,container,false);

        Bundle b = new Bundle();
        Bundle b1 = getArguments();

        if (b1 != null) {
            getActivity().setTitle("Member: " + b1.getString("bundle_UserName"));
            b.putString("Rcontact",b1.getString("Rcontact"));
            b.putString("grouptype", b1.getString("grouptype"));
            b.putString("className", b1.getString("className"));
            b.putInt("bundle_GroupId", b1.getInt("bundle_GroupId"));
           // b.putString("bundle_GroupName", b1.getString("bundle_GroupName"));
            Log.i("GroupId", "GroupTab->" + b1.getInt("bundle_GroupId"));
        }

     /*   memberDetailsMemberFragment =new MemberDetailsMemberFragment();
        memberDetailsMemberFragment.setArguments(b);
*/
        groupVehicleList = new MemberVehicleListFragment();
        groupVehicleList.setArguments(b);

        memberCommunicationFragment=new MemberCommunicationFragment();
        memberCommunicationFragment.setArguments(b);

        memberProductFragment =new MemberProductFragment();
        memberProductFragment.setArguments(b);

        memberServicesFragment =new MemberServicesFragment();
        memberServicesFragment.setArguments(b);


        ViewPager mViewPager = (ViewPager) mMemberdetails.findViewById(R.id.member_details_viewpager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        TabLayout tabLayout = (TabLayout) mMemberdetails.findViewById(R.id.member_details_tab);
        tabLayout.setupWithViewPager(mViewPager);

        return mMemberdetails;
    }

    private void setupViewPager(ViewPager mViewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getChildFragmentManager());
        tabAdapterName.addFragment(memberCommunicationFragment, "Communication");
      //  tabAdapterName.addFragment(memberDetailsMemberFragment, "Group Members");
        tabAdapterName.addFragment(groupVehicleList, "Member Vehicles");
        tabAdapterName.addFragment(memberProductFragment, "Member Products");
        tabAdapterName.addFragment(memberServicesFragment, "Member Services");
        mViewPager.setAdapter(tabAdapterName);
    }
}
