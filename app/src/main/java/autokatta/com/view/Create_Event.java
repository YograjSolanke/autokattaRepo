package autokatta.com.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import autokatta.com.R;
import autokatta.com.events.CreateAuctionFragment;
import autokatta.com.events.CreateExchangeMelafragment;
import autokatta.com.events.CreateLoanMelaFragment;
import autokatta.com.events.CreateSaleMelaFragment;
import autokatta.com.events.CreateServiceMelaFragment;

public class Create_Event extends AppCompatActivity implements View.OnClickListener {

    TextView textAuction, textLoanMela, textExchangeMela, textSaleMela, textServiceMela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textAuction = (TextView) findViewById(R.id.textauctionevent);
        textLoanMela = (TextView) findViewById(R.id.textloanmela);
        textExchangeMela = (TextView) findViewById(R.id.textexchangemela);
        textSaleMela = (TextView) findViewById(R.id.textsalemela);
        textServiceMela = (TextView) findViewById(R.id.textservicemela);

        textAuction.setOnClickListener(this);
        textLoanMela.setOnClickListener(this);
        textExchangeMela.setOnClickListener(this);
        textSaleMela.setOnClickListener(this);
        textServiceMela.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()) {


            case (R.id.textauctionevent):


                CreateAuctionFragment fragment = new CreateAuctionFragment();
                fragmentTransaction.replace(R.id.createEventFrame, fragment);
                fragmentTransaction.addToBackStack("create_auction");
                fragmentTransaction.commit();
                break;
            case (R.id.textloanmela):


                CreateLoanMelaFragment fragmentloan = new CreateLoanMelaFragment();

                fragmentTransaction.replace(R.id.createEventFrame, fragmentloan);
                fragmentTransaction.addToBackStack("create_loan");
                fragmentTransaction.commit();
                break;
            case (R.id.textexchangemela):


                CreateExchangeMelafragment fragmentexch = new CreateExchangeMelafragment();

                fragmentTransaction.replace(R.id.createEventFrame, fragmentexch);
                fragmentTransaction.addToBackStack("create_exchange");
                fragmentTransaction.commit();
                break;

            case (R.id.textsalemela):


                CreateSaleMelaFragment fragmentsale = new CreateSaleMelaFragment();

                fragmentTransaction.replace(R.id.createEventFrame, fragmentsale);
                fragmentTransaction.addToBackStack("create_sale");
                fragmentTransaction.commit();
                break;

            case (R.id.textservicemela):


                CreateServiceMelaFragment fragmentservice = new CreateServiceMelaFragment();

                fragmentTransaction.replace(R.id.createEventFrame, fragmentservice);
                fragmentTransaction.addToBackStack("create_service");
                fragmentTransaction.commit();
                break;
        }

    }
}
