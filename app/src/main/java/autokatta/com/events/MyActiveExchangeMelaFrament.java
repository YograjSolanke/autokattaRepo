package autokatta.com.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;

/**
 * Created by ak-003 on 22/3/17.
 */

public class MyActiveExchangeMelaFrament extends Fragment {

    View mActiveExchange;

    public MyActiveExchangeMelaFrament() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActiveExchange = inflater.inflate(R.layout.fragment_simple_listview, container, false);

        return mActiveExchange;
    }
}
