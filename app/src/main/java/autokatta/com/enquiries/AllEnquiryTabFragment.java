package autokatta.com.enquiries;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import autokatta.com.R;
import autokatta.com.database.DbConstants;
import autokatta.com.database.DbOperation;
import autokatta.com.view.BussinessChatActivity;
import autokatta.com.view.ManualEnquiry;

/**
 * Created by ak-003 on 24/4/17.
 */

public class AllEnquiryTabFragment extends Fragment implements View.OnClickListener {

    View mEnquiryTab;
    RelativeLayout relativeBC, relativeTestDrive, relativeNewDealer, relativeManualEnquiry;
    TextView mManualCount;

    public AllEnquiryTabFragment() {
        //Empty Fragment...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mEnquiryTab = inflater.inflate(R.layout.fragment_all_enquiry_tab, container, false);

        relativeBC = (RelativeLayout) mEnquiryTab.findViewById(R.id.relBC);
        relativeTestDrive = (RelativeLayout) mEnquiryTab.findViewById(R.id.relTD);
        relativeNewDealer = (RelativeLayout) mEnquiryTab.findViewById(R.id.relND);
        relativeManualEnquiry = (RelativeLayout) mEnquiryTab.findViewById(R.id.relME);
        mManualCount = (TextView) mEnquiryTab.findViewById(R.id.manual_count);

        relativeBC.setOnClickListener(this);
        relativeTestDrive.setOnClickListener(this);
        relativeNewDealer.setOnClickListener(this);
        relativeManualEnquiry.setOnClickListener(this);

        DbOperation operation;
        operation = new DbOperation(getActivity().getApplicationContext());
        operation.OPEN();
        Cursor cursor = operation.getEnquiryCount();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToLast();
            mManualCount.setText(cursor.getString(cursor.getColumnIndex(DbConstants.enq_val)));
            Log.i("dsafdsfads", "->" + cursor.getString(cursor.getColumnIndex(DbConstants.enq_val)));
        }
        operation.CLOSE();
        return mEnquiryTab;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relBC:
                ActivityOptions option = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                startActivity(new Intent(getActivity(), BussinessChatActivity.class), option.toBundle());
                break;

            case R.id.relTD:
                Toast.makeText(getActivity(), "Comming Soon", Toast.LENGTH_SHORT).show();
                break;

            case R.id.relND:
                Toast.makeText(getActivity(), "Comming Soon", Toast.LENGTH_SHORT).show();
                break;

            case R.id.relME:
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                startActivity(new Intent(getActivity(), ManualEnquiry.class), options.toBundle());
                break;
            case R.id.rel5:
                Toast.makeText(getActivity(), "Comming Soon", Toast.LENGTH_SHORT).show();break;
            case R.id.rel6:
                Toast.makeText(getActivity(), "Comming Soon", Toast.LENGTH_SHORT).show(); break;
        }
    }
}
