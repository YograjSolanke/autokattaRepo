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

/**
 * Created by ak-001 on 20/11/17.
 */

public class WallMoreFragment extends Fragment {
    View mWallMore;
    RelativeLayout mMyFavourite, mGroupNotification;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mWallMore = inflater.inflate(R.layout.fragment_more_wall, container, false);
        mMyFavourite = (RelativeLayout) mWallMore.findViewById(R.id.relative3);
        mGroupNotification = (RelativeLayout) mWallMore.findViewById(R.id.relative2);
        mMyFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FavoriteNotificationFragment.class));
            }
        });
        mGroupNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GroupNotification.class));
            }
        });
        return mWallMore;
    }
}
