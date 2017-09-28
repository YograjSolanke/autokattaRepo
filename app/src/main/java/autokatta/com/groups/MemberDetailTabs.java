package autokatta.com.groups;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import autokatta.com.R;
import autokatta.com.groups_container.MemberCommunicationContainer;
import autokatta.com.groups_container.MemberProductContainer;
import autokatta.com.groups_container.MemberServiceContainer;
import autokatta.com.groups_container.MemberVehicleContainer;

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
    Bundle b = new Bundle();
    GridView androidGridView;
    String[] gridViewString = {
            "Communication", "Vehicle", "Product", "Service",};

    int[] gridViewImageId = {
            R.mipmap.communication, R.mipmap.group_vehicles, R.mipmap.product, R.mipmap.services,
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMemberdetails=inflater.inflate(R.layout.member_details_tab_fragment,container,false);
        Bundle b1 = getArguments();
        if (b1 != null) {
            getActivity().setTitle("Member: " + b1.getString("bundle_UserName"));
            b.putString("Rcontact",b1.getString("Rcontact"));
            b.putString("grouptype", b1.getString("grouptype"));
            b.putString("className", b1.getString("className"));
            b.putString("bundle_UserName", b1.getString("bundle_UserName"));
            b.putInt("bundle_GroupId", b1.getInt("bundle_GroupId"));
           // b.putString("bundle_GroupName", b1.getString("bundle_GroupName"));
            Log.i("GroupId", "GroupTab->" + b1.getInt("bundle_GroupId"));
        }

        MemberDetailTabs.CustomGridViewActivity adapterViewAndroid = new MemberDetailTabs.CustomGridViewActivity(getActivity(), gridViewString, gridViewImageId);
        androidGridView = (GridView) mMemberdetails.findViewById(R.id.group_member_grid_view);
        androidGridView.setAdapter(adapterViewAndroid);

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                if (gridViewString[+i].equals("Communication")) {
                    Intent communication = new Intent(getActivity(), MemberCommunicationContainer.class);
                    communication.putExtras(b);
                    startActivity(communication);
                } else if (gridViewString[+i].equals("Vehicle")) {
                    Intent communication = new Intent(getActivity(), MemberVehicleContainer.class);
                    communication.putExtras(b);
                    startActivity(communication);
                } else if (gridViewString[+i].equals("Product")) {
                    Intent communication = new Intent(getActivity(), MemberProductContainer.class);
                    communication.putExtras(b);
                    startActivity(communication);
                } else if (gridViewString[+i].equals("Service")) {
                    Intent communication = new Intent(getActivity(), MemberServiceContainer.class);
                    communication.putExtras(b);
                    startActivity(communication);
                }
            }
        });
     /*   memberDetailsMemberFragment =new MemberDetailsMemberFragment();
        memberDetailsMemberFragment.setArguments(b);
*/
        /*groupVehicleList = new MemberVehicleListFragment();
        groupVehicleList.setArguments(b);*/

       /* memberCommunicationFragment=new MemberCommunicationFragment();
        memberCommunicationFragment.setArguments(b);*/

        /*memberProductFragment =new MemberProductFragment();
        memberProductFragment.setArguments(b);*/

        /*memberServicesFragment =new MemberServicesFragment();
        memberServicesFragment.setArguments(b);
*/

        /*ViewPager mViewPager = (ViewPager) mMemberdetails.findViewById(R.id.member_details_viewpager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        TabLayout tabLayout = (TabLayout) mMemberdetails.findViewById(R.id.member_details_tab);
        tabLayout.setupWithViewPager(mViewPager);*/

        return mMemberdetails;
    }

   /* private void setupViewPager(ViewPager mViewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getChildFragmentManager());
        tabAdapterName.addFragment(memberCommunicationFragment, "Communication");
      //  tabAdapterName.addFragment(memberDetailsMemberFragment, "Group Members");
        tabAdapterName.addFragment(groupVehicleList, "Member Vehicles");
        tabAdapterName.addFragment(memberProductFragment, "Member Products");
        tabAdapterName.addFragment(memberServicesFragment, "Member Services");
        mViewPager.setAdapter(tabAdapterName);
    }*/

    private class CustomGridViewActivity extends BaseAdapter {

        private Context mContext;
        private final String[] gridViewString;
        private final int[] gridViewImageId;

        private CustomGridViewActivity(Activity context, String[] gridViewString, int[] gridViewImageId) {
            mContext = context;
            this.gridViewImageId = gridViewImageId;
            this.gridViewString = gridViewString;
        }

        @Override
        public int getCount() {
            return gridViewString.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            View gridViewAndroid;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                gridViewAndroid = new View(mContext);
                gridViewAndroid = inflater.inflate(R.layout.gridview_layout, null);
                TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
                ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
                textViewAndroid.setText(gridViewString[i]);
                imageViewAndroid.setImageResource(gridViewImageId[i]);
            } else {
                gridViewAndroid = (View) convertView;
            }
            return gridViewAndroid;
        }
    }
}
