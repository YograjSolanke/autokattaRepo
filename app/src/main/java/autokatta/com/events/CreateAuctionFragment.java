package autokatta.com.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import autokatta.com.R;
import autokatta.com.interfaces.RequestNotifier;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 23/3/17.
 */

public class CreateAuctionFragment extends Fragment
        implements View.OnClickListener, RequestNotifier, View.OnTouchListener {

    EditText auctioname, startdate, starttime, enddate, endtime;
    AutoCompleteTextView address;
    RadioGroup rgauctiontype;
    RadioButton physical, online, banquet;
    Button addmore, create, cancel;
    String myContact;


    View createAuctionView;


    public CreateAuctionFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createAuctionView = inflater.inflate(R.layout.fragment_create_auction, container, false);
        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

        return createAuctionView;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
