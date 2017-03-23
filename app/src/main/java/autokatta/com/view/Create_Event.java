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

public class Create_Event extends AppCompatActivity implements View.OnClickListener {

    TextView textAuction, textLoanMela, textExchangeMela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textAuction = (TextView) findViewById(R.id.textauctionevent);
        textLoanMela = (TextView) findViewById(R.id.textloanmela);
        textExchangeMela = (TextView) findViewById(R.id.textexchangemela);

        textAuction.setOnClickListener(this);
        textLoanMela.setOnClickListener(this);
        textExchangeMela.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()) {


            case (R.id.textauctionevent):


                CreateAuctionFragment fragment = new CreateAuctionFragment();


                fragmentTransaction.replace(R.id.createEventFrame, fragment);
                fragmentTransaction.addToBackStack("imagecapturefragmentservice");
                fragmentTransaction.commit();
                break;
            case (R.id.textloanmela):


                CreateLoanMelaFragment fragmentloan = new CreateLoanMelaFragment();

                fragmentTransaction.replace(R.id.createEventFrame, fragmentloan);
                fragmentTransaction.addToBackStack("imagecapturefragmentservice");
                fragmentTransaction.commit();
                break;
            case (R.id.textexchangemela):


                CreateExchangeMelafragment fragmentexch = new CreateExchangeMelafragment();

                fragmentTransaction.replace(R.id.createEventFrame, fragmentexch);
                fragmentTransaction.addToBackStack("imagecapturefragmentservice");
                fragmentTransaction.commit();
                break;
        }

    }
}
