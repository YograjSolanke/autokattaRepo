package autokatta.com.groups;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import autokatta.com.R;

public class GetQuotation extends AppCompatActivity {

    TextView Title, Category, Brand, Model, Keyword, price;
    RelativeLayout relCategory, relBrand, relModel, relPrice, MainRel;
    ImageView Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_quotation);
        setTitle("Get Quotation");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }

                Keyword = (TextView) findViewById(R.id.keyword);
                Title = (TextView) findViewById(R.id.settitle);
                Category = (TextView) findViewById(R.id.setcategory);
                Brand = (TextView) findViewById(R.id.setbrand);
                Model = (TextView) findViewById(R.id.setmodel);
                Image = (ImageView) findViewById(R.id.image);
                price = (TextView) findViewById(R.id.setprice);
                relCategory = (RelativeLayout) findViewById(R.id.relative2);
                relBrand = (RelativeLayout) findViewById(R.id.relative3);
                relModel = (RelativeLayout) findViewById(R.id.relative4);
                relPrice = (RelativeLayout) findViewById(R.id.relative5);
                MainRel = (RelativeLayout) findViewById(R.id.MainRel);
                Keyword.setVisibility(View.GONE);
            }
        });
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
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
