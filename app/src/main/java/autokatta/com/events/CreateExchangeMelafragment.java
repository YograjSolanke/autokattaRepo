package autokatta.com.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;

/**
 * Created by ak-004 on 23/3/17.
 */

public class CreateExchangeMelafragment extends Fragment implements View.OnClickListener {

    View createExchangeView;
    ImageView picture;
    Button create, btnaddprofile;
    TextView textevent;
    EditText eventname, startdate, starttime, enddate, endtime, eventaddress, eventdetails;
    AutoCompleteTextView eventlocation;
    private List<String> resultList = new ArrayList<>();

    public CreateExchangeMelafragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createExchangeView = inflater.inflate(R.layout.fragment_create_loanmela, container, false);
        eventname = (EditText) createExchangeView.findViewById(R.id.editauctionname);
        startdate = (EditText) createExchangeView.findViewById(R.id.auctionstartdate);
        starttime = (EditText) createExchangeView.findViewById(R.id.auctionstarttime);
        enddate = (EditText) createExchangeView.findViewById(R.id.auctionenddate);
        endtime = (EditText) createExchangeView.findViewById(R.id.auctionendtime);
        eventlocation = (AutoCompleteTextView) createExchangeView.findViewById(R.id.editlocation);
        eventaddress = (EditText) createExchangeView.findViewById(R.id.editaddress);
        eventdetails = (EditText) createExchangeView.findViewById(R.id.editdetails);
        create = (Button) createExchangeView.findViewById(R.id.btncreate);
        textevent = (TextView) createExchangeView.findViewById(R.id.textevent);
        picture = (ImageView) createExchangeView.findViewById(R.id.loanmelaimg);
        btnaddprofile = (Button) createExchangeView.findViewById(R.id.btnaddphoto);

        btnaddprofile.setOnClickListener(this);
        create.setOnClickListener(this);
        startdate.setOnClickListener(this);
        starttime.setOnClickListener(this);
        enddate.setOnClickListener(this);
        endtime.setOnClickListener(this);
        eventlocation.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.simple));


        textevent.setText("Create Exchange Mela Event");
        picture.setImageResource(R.drawable.exchangeimage);

        return createExchangeView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case (R.id.auctionstartdate):
                break;
            case (R.id.auctionstarttime):
                break;
            case (R.id.auctionenddate):
                break;
            case (R.id.auctionendtime):
                break;
            case (R.id.btnaddphoto):
                break;
            case (R.id.btnaddmore):
                break;
            case (R.id.btncreate):


        }

    }
}
