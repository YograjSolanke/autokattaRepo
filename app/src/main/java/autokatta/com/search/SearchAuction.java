package autokatta.com.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;

/**
 * Created by ak-001 on 19/4/17.
 */

public class SearchAuction extends Fragment {
    View mSearchAuction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchAuction = inflater.inflate(R.layout.fragment_search_product, container, false);
        return mSearchAuction;
    }
}
