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

public class Modules extends Fragment {
    View mModules;
    public Modules(){
        //empty constructor...
    }
    public static Modules newInstance(String text){
        Modules f = new Modules();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mModules = inflater.inflate(R.layout.fragment_profile_module, container, false);
        TextView tv = (TextView) mModules.findViewById(R.id.tvFragFirst);
//        tv.setText(getArguments().getString("msg"));

       // mModules.findViewById(R.id.root_view).setBackgroundColor(Color.parseColor(getArguments().getString("color")));
        return mModules;
    }
}
