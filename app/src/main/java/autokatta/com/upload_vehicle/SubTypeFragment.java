package autokatta.com.upload_vehicle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import autokatta.com.R;
import autokatta.com.other.MonthYearPicker;

/**
 * Created by ak-001 on 21/3/17.
 */

public class SubTypeFragment extends Fragment {

    View mSubtype;
    private MonthYearPicker myp;
    EditText mMakeMonth, mMakeYear;
    EditText mRegisterMonth, mRegisterYear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSubtype = inflater.inflate(R.layout.fragment_subtype_fragment, container, false);
        mMakeMonth = (EditText) mSubtype.findViewById(R.id.make_month);
        mMakeYear = (EditText) mSubtype.findViewById(R.id.make_year);
        mRegisterMonth = (EditText) mSubtype.findViewById(R.id.register_month);
        mRegisterYear = (EditText) mSubtype.findViewById(R.id.register_year);

        /*mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myp.show();
            }
        });
        myp = new MonthYearPicker(getActivity());
        myp.build(new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textView1.setText(myp.getSelectedMonthName() + " >> " + myp.getSelectedYear());
            }
        }, null);*/
        return mSubtype;
    }
}
