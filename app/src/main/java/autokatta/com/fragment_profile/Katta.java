package autokatta.com.fragment_profile;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import autokatta.com.R;

/**
 * Created by ak-001 on 18/3/17.
 */

public class Katta extends Fragment {
    View mKatta;

    public Katta(){
        //empty constructor...
    }
    public static Katta newInstance(String text) {
        Katta f = new Katta();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mKatta = inflater.inflate(R.layout.fragment_profile_katta, container, false);
        TextView tv = (TextView) mKatta.findViewById(R.id.tvFragFirst);
        tv.setText(getArguments().getString("msg"));

        //mKatta.findViewById(R.id.root_view).setBackgroundColor(Color.parseColor(getArguments().getString("color")));
        return mKatta;
    }
}
