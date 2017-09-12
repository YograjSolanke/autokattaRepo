package autokatta.com.initial_fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.groups.JoinedGroupsFragment;
import autokatta.com.groups.MyGroupsFragment;

/**
 * Created by ak-001 on 25/3/17.
 */

public class GroupMyJoined extends Fragment {
    View mGroupMyJoined;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGroupMyJoined = inflater.inflate(R.layout.fragment_group_my_joined, container, false);
        getActivity().setTitle("My Groups");
        ViewPager mviewPager = (ViewPager) mGroupMyJoined.findViewById(R.id.activity_groups_tab_viewpager);
        if (mviewPager != null) {
            setupViewPager(mviewPager);
        }
        TabLayout tabLayout = (TabLayout) mGroupMyJoined.findViewById(R.id.activity_groups_tab);
        tabLayout.setupWithViewPager(mviewPager);
        //showMessage();
        return mGroupMyJoined;
    }

    private void showMessage() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage(Html.fromHtml("This is trial period, enjoy unlimited uploads and grow your business. After trial period, continue to grow your business for a small amount of Rs. 750/- per month. This Package we call the IGNITION as this gives a start to your digital business.Groups forms a part of IGNITION package.<br>" +
                "<font color='#FF0000'>1. IGNITION package of price is 750/- per month gives you unlimited uploads, Unlimited groups, unlimited stores. Payment options will be monthly, quarterly, half yearly and annually.</font>"));
        alertDialog.setCancelable(false);
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setPositiveButton("Continue Trial Version", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getChildFragmentManager());
        tabAdapterName.addFragment(new MyGroupsFragment(), "My Groups");
        tabAdapterName.addFragment(new JoinedGroupsFragment(), "Joined Groups");
        viewPager.setAdapter(tabAdapterName);
    }
}
