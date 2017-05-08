package autokatta.com.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }

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


                /*CreateAuctionFragment fragment = new CreateAuctionFragment();
                fragmentTransaction.replace(R.id.createEventFrame, fragment,"create_auction");
                fragmentTransaction.addToBackStack("create_auction");
                fragmentTransaction.commit();*/
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.createEventFrame, new CreateAuctionFragment(), "create_auction")
                        .addToBackStack("create_auction")
                        .commit();
                break;
            case (R.id.textloanmela):


                /*CreateLoanMelaFragment fragmentloan = new CreateLoanMelaFragment();

                fragmentTransaction.replace(R.id.createEventFrame, fragmentloan);
                fragmentTransaction.addToBackStack("create_loan");
                fragmentTransaction.commit();*/
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.createEventFrame, new CreateLoanMelaFragment(), "create_loan")
                        .addToBackStack("create_loan")
                        .commit();
                break;
            case (R.id.textexchangemela):


                /*CreateExchangeMelafragment fragmentexch = new CreateExchangeMelafragment();

                fragmentTransaction.replace(R.id.createEventFrame, fragmentexch);
                fragmentTransaction.addToBackStack("create_exchange");
                fragmentTransaction.commit();*/
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.createEventFrame, new CreateExchangeMelafragment(), "create_exchange")
                        .addToBackStack("create_exchange")
                        .commit();
                break;

            case (R.id.textsalemela):


               /* CreateSaleMelaFragment fragmentsale = new CreateSaleMelaFragment();

                fragmentTransaction.replace(R.id.createEventFrame, fragmentsale);
                fragmentTransaction.addToBackStack("create_sale");
                fragmentTransaction.commit();*/
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.createEventFrame, new CreateSaleMelaFragment(), "create_sale")
                        .addToBackStack("create_sale")
                        .commit();
                break;

            case (R.id.textservicemela):


                /*CreateServiceMelaFragment fragmentservice = new CreateServiceMelaFragment();

                fragmentTransaction.replace(R.id.createEventFrame, fragmentservice);
                fragmentTransaction.addToBackStack("create_service");
                fragmentTransaction.commit();*/
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.createEventFrame, new CreateServiceMelaFragment(), "create_service")
                        .addToBackStack("create_service")
                        .commit();
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }
}
