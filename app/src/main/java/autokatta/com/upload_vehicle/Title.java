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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import autokatta.com.R;
import autokatta.com.fragment_profile.Modules;

/**
 * Created by ak-001 on 19/3/17.
 */

public class Title extends Fragment implements View.OnTouchListener {
    View mTitle;
    ScrollView scrollView1;
    RadioButton radioButton1, radioButton2, storeradioyes, storeradiono, financeyes, financeno, exchangeyes, exchangeno;
    EditText title;

    public static Title newInstance(String text){
        Title f = new Title();
        /*Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);*/
        return f;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       mTitle = inflater.inflate(R.layout.fragment_upload_vehicle_title, container, false);
        scrollView1 = (ScrollView) mTitle.findViewById(R.id.scrollView1);
        title = (EditText) mTitle.findViewById(R.id.titleText1);
        radioButton1 = (RadioButton) mTitle.findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) mTitle.findViewById(R.id.radioButton2);
        storeradioyes = (RadioButton) mTitle.findViewById(R.id.storeradio1);
        storeradiono = (RadioButton) mTitle.findViewById(R.id.storeradio2);

        financeyes = (RadioButton) mTitle.findViewById(R.id.financeYes);
        financeno = (RadioButton) mTitle.findViewById(R.id.financeNo);
        exchangeyes = (RadioButton) mTitle.findViewById(R.id.exchangeYes);
        exchangeno = (RadioButton) mTitle.findViewById(R.id.exchangeNo);
        final GestureDetector gesture = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                try {
                    String titletext = title.getText().toString();
                    String privacy = null, exchangeYesNo = null, storeprivacy = null;
                    String financests = null, exchangests = null;

                    if (radioButton1.isChecked()) {
                        privacy = "Yes";
                    } else if (radioButton2.isChecked()) {
                        privacy = "No";
                    }
                    if (storeradioyes.isChecked()) {
                        storeprivacy = "Yes";
                    } else if (storeradiono.isChecked()) {

                        storeprivacy = "No";
                    }


                    if (financeyes.isChecked()) {
                        financests = "Yes";
                    } else if (financeno.isChecked()) {

                        financests = "No";
                    }


                    if (exchangeyes.isChecked()) {
                        exchangests = "Yes";
                    } else if (exchangeno.isChecked()) {

                        exchangests = "No";
                    }

                    Log.i("Onfling", "onFling has been called!");
                    final int SWIPE_MIN_DISTANCE = 120;
                    final int SWIPE_MAX_OFF_PATH = 250;
                    final int SWIPE_THRESHOLD_VELOCITY = 200;

                    if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                        return false;
                    if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        Log.i("Hiii", "Right to Left");
                        if (titletext.equals("")) {
                             Toast.makeText(getActivity(), "left2right swipe", Toast.LENGTH_SHORT).show ();
                            title.setError("Fill out this field");
                            title.setFocusable(true);
                        } else if ((radioButton1.isChecked() || radioButton2.isChecked()) && (storeradioyes.isChecked() || storeradiono.isChecked())
                                && (financeyes.isChecked() || financeno.isChecked()) && (exchangeyes.isChecked() || exchangeno.isChecked())) {

                           /* vehicleEditor.putString("title", titletext);
                            vehicleEditor.putString("privacy", privacy);
                            vehicleEditor.putString("exchange", exchangeYesNo);
                            vehicleEditor.putString("storeprivacy", storeprivacy);
                            vehicleEditor.putString("financestatus", financests);
                            vehicleEditor.putString("exchangestatus", exchangests);
                            vehicleEditor.putString("OwnerGroupIds", stringgroupids);
                            vehicleEditor.putString("OwnerStoreIds", stringstoreids);
                            vehicleEditor.putString("OwnerGroupName", stringgroupname);
                            vehicleEditor.putString("OwnerStoreName", stringstorename);
                            vehicleEditor.commit();

                            Bundle b = new Bundle();
                            b.putInt("call", call);
                            SubtypeFragment fragment2 = new SubtypeFragment();
                            fragment2.setArguments(b);

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.containerView, fragment2);
                            fragmentTransaction.commit();*/

                        } else {
                            Toast.makeText(getActivity(), "Please provide all information", Toast.LENGTH_LONG).show();
                        }
                    } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        Log.i("hello", "Left to Right");
                    }
                } catch (Exception e) {
                    // nothing
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

        scrollView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });
        return mTitle;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Toast.makeText(getActivity(), "left2right swipe", Toast.LENGTH_SHORT).show();
        return false;
    }
}
