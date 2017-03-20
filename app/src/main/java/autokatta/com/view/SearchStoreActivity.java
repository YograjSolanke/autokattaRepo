package autokatta.com.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import autokatta.com.R;

public class SearchStoreActivity extends AppCompatActivity implements View.OnClickListener {

    MultiAutoCompleteTextView autoTxtCategory;
    AutoCompleteTextView autoTxtLocation;
    EditText edtPhraseSearch, edtContactSearch;
    ImageView imgGetContact;
    Spinner spnRadius;
    Button btnSearch;
    String myContact;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_store);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        autoTxtCategory = (MultiAutoCompleteTextView) findViewById(R.id.auto_category);
        autoTxtLocation = (AutoCompleteTextView) findViewById(R.id.auto_location);
        edtPhraseSearch = (EditText) findViewById(R.id.edtPhrase);
        edtContactSearch = (EditText) findViewById(R.id.edtContact);
        imgGetContact = (ImageView) findViewById(R.id.getContact);
        spnRadius = (Spinner) findViewById(R.id.spn_radius);
        btnSearch = (Button) findViewById(R.id.search);

        imgGetContact.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        mSharedPreferences = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
        myContact = mSharedPreferences.getString("loginContact", "7841023392");
        Log.i("myContact", myContact);

        autoTxtCategory.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getContact:
                break;

            case R.id.search:
                break;

        }
    }
}
