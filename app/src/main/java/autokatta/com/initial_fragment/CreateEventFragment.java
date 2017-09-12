package autokatta.com.initial_fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
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
        //showMessage();
        return mCreateEventFragment;
    }

    private void showMessage() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage(Html.fromHtml("This is trial period, Participate in auction free without deposit. After trial period, you can participate for buying or selling through 5 auction per Rs. 1000/-. Deposits still needs to be paid for bidding limit. Other terms and conditions apply for auction participating. This package is called AUCTION PACK.<br>" +
                "<font color='#FF0000'>Rs. 1000/- you can create auctions for selling or participate in auction for buying. 1 AUCTION PACK gives you a total of 5 opportunities of buying or selling through auction in a fees of Rs. 1000/-</font>"));
        alertDialog.setCancelable(false);
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setPositiveButton("Continue Trial Version", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case (R.id.textauctionevent):

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.createEventFrame, new CreateAuctionFragment(), "create_auction")
                        .addToBackStack("create_auction")
                        .commit();
                break;
            case (R.id.textloanmela):

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.createEventFrame, new CreateLoanMelaFragment(), "create_loan")
                        .addToBackStack("create_loan")
                        .commit();
                break;
            case (R.id.textexchangemela):

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.createEventFrame, new CreateExchangeMelafragment(), "create_exchange")
                        .addToBackStack("create_exchange")
                        .commit();
                break;

            case (R.id.textsalemela):

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.createEventFrame, new CreateSaleMelaFragment(), "create_sale")
                        .addToBackStack("create_sale")
                        .commit();
                break;

            case (R.id.textservicemela):

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.createEventFrame, new CreateServiceMelaFragment(), "create_service")
                        .addToBackStack("create_service")
                        .commit();
                break;
        }

    }
}
