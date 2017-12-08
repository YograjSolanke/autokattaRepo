package autokatta.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import autokatta.com.R;
import autokatta.com.view.RecentVisitsActivity;

/**
 * Created by ak-001 on 20/11/17.
 */

public class WallMoreFragment extends Fragment {
    View mWallMore;
    RelativeLayout mMyFavourite, mGroupNotification, mStoreNotification, mRecentVisits;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mWallMore = inflater.inflate(R.layout.fragment_more_wall, container, false);

        mStoreNotification = (RelativeLayout) mWallMore.findViewById(R.id.relative1);
        mGroupNotification = (RelativeLayout) mWallMore.findViewById(R.id.relative2);
        mMyFavourite = (RelativeLayout) mWallMore.findViewById(R.id.relative3);
        mRecentVisits = (RelativeLayout) mWallMore.findViewById(R.id.relative4);

        mStoreNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(), StoreNotification.class));
            }
        });
        mGroupNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GroupNotification.class));
            }
        });
        mMyFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FavoriteNotificationFragment.class));
            }
        });
        mRecentVisits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RecentVisitsActivity.class));
            }
        });
        return mWallMore;
    }
}
