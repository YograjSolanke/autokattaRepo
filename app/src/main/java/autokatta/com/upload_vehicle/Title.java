package autokatta.com.upload_vehicle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import autokatta.com.R;
import autokatta.com.fragment_profile.Modules;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 19/3/17.
 */

public class Title extends Fragment implements View.OnClickListener {
    View mTitle;
    ScrollView scrollView1;
    RadioButton radioButton1, radioButton2, storeradioyes, storeradiono, financeyes, financeno, exchangeyes, exchangeno;
    EditText title;
    TextView mCategory;
    Button mSubmit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTitle = inflater.inflate(R.layout.fragment_upload_vehicle_title, container, false);
        scrollView1 = (ScrollView) mTitle.findViewById(R.id.scrollView1);
        title = (EditText) mTitle.findViewById(R.id.titleText1);
        mCategory = (TextView) mTitle.findViewById(R.id.categorytext);
        radioButton1 = (RadioButton) mTitle.findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) mTitle.findViewById(R.id.radioButton2);
        storeradioyes = (RadioButton) mTitle.findViewById(R.id.storeradio1);
        storeradiono = (RadioButton) mTitle.findViewById(R.id.storeradio2);

        financeyes = (RadioButton) mTitle.findViewById(R.id.financeYes);
        financeno = (RadioButton) mTitle.findViewById(R.id.financeNo);
        exchangeyes = (RadioButton) mTitle.findViewById(R.id.exchangeYes);
        exchangeno = (RadioButton) mTitle.findViewById(R.id.exchangeNo);

        mSubmit = (Button) mTitle.findViewById(R.id.title_next);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return mTitle;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_next:
                FragmentManager manager = getFragmentManager();
                FragmentTransaction mTransaction = manager.beginTransaction();
                mTransaction.replace(R.id.vehicle_upload_container, new SubTypeFragment()).addToBackStack("title").commit();
                break;
        }
    }
}
