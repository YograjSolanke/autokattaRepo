package autokatta.com.initial_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import autokatta.com.R;
import autokatta.com.events.CreateAuctionFragment;
import autokatta.com.events.CreateExchangeMelafragment;
import autokatta.com.events.CreateLoanMelaFragment;
import autokatta.com.events.CreateSaleMelaFragment;
import autokatta.com.events.CreateServiceMelaFragment;

/**
 * Created by ak-003 on 8/5/17.
 */

public class CreateEventFragment extends Fragment implements View.OnClickListener {

    View mCreateEventFragment;
    TextView textAuction, textLoanMela, textExchangeMela, textSaleMela, textServiceMela;

    public CreateEventFragment() {
        //empty fragment
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCreateEventFragment = inflater.inflate(R.layout.fragment_create_event, container, false);

        textAuction = (TextView) mCreateEventFragment.findViewById(R.id.textauctionevent);
        textLoanMela = (TextView) mCreateEventFragment.findViewById(R.id.textloanmela);
        textExchangeMela = (TextView) mCreateEventFragment.findViewById(R.id.textexchangemela);
        textSaleMela = (TextView) mCreateEventFragment.findViewById(R.id.textsalemela);
        textServiceMela = (TextView) mCreateEventFragment.findViewById(R.id.textservicemela);

        textAuction.setOnClickListener(this);
        textLoanMela.setOnClickListener(this);
        textExchangeMela.setOnClickListener(this);
        textSaleMela.setOnClickListener(this);
        textServiceMela.setOnClickListener(this);

        return mCreateEventFragment;
    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()) {


            case (R.id.textauctionevent):


                /*CreateAuctionFragment fragment = new CreateAuctionFragment();
                fragmentTransaction.replace(R.id.createEventFrame, fragment,"create_auction");
                fragmentTransaction.addToBackStack("create_auction");
                fragmentTransaction.commit();*/
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.createEventFrame, new CreateAuctionFragment(), "create_auction")
                        .addToBackStack("create_auction")
                        .commit();
                break;
            case (R.id.textloanmela):


                /*CreateLoanMelaFragment fragmentloan = new CreateLoanMelaFragment();

                fragmentTransaction.replace(R.id.createEventFrame, fragmentloan);
                fragmentTransaction.addToBackStack("create_loan");
                fragmentTransaction.commit();*/
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.createEventFrame, new CreateLoanMelaFragment(), "create_loan")
                        .addToBackStack("create_loan")
                        .commit();
                break;
            case (R.id.textexchangemela):


                /*CreateExchangeMelafragment fragmentexch = new CreateExchangeMelafragment();

                fragmentTransaction.replace(R.id.createEventFrame, fragmentexch);
                fragmentTransaction.addToBackStack("create_exchange");
                fragmentTransaction.commit();*/
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.createEventFrame, new CreateExchangeMelafragment(), "create_exchange")
                        .addToBackStack("create_exchange")
                        .commit();
                break;

            case (R.id.textsalemela):


               /* CreateSaleMelaFragment fragmentsale = new CreateSaleMelaFragment();

                fragmentTransaction.replace(R.id.createEventFrame, fragmentsale);
                fragmentTransaction.addToBackStack("create_sale");
                fragmentTransaction.commit();*/
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.createEventFrame, new CreateSaleMelaFragment(), "create_sale")
                        .addToBackStack("create_sale")
                        .commit();
                break;

            case (R.id.textservicemela):


                /*CreateServiceMelaFragment fragmentservice = new CreateServiceMelaFragment();

                fragmentTransaction.replace(R.id.createEventFrame, fragmentservice);
                fragmentTransaction.addToBackStack("create_service");
                fragmentTransaction.commit();*/
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.createEventFrame, new CreateServiceMelaFragment(), "create_service")
                        .addToBackStack("create_service")
                        .commit();
                break;
        }

    }
}
