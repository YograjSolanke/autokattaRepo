package autokatta.com.auction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;

/**
 * Created by ak-001 on 4/4/17.
 */

public class PreviewUpcomingEventByOther extends Fragment {
    View mPreviewEvent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPreviewEvent = inflater.inflate(R.layout.fragment_preview_upcoming_event, container, false);
        return mPreviewEvent;
    }
}
