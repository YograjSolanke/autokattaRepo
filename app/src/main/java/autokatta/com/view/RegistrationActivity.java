package autokatta.com.view;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.generic.GenericFunctions;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, android.location.LocationListener {

    EditText personName,mobileNo,email,dateOfBirth,pincode,otherIndustry,password,confirmPassword;
    Spinner moduleSpinner,usertypeSpinner,industrySpinner;
    Button btnSubmit,btnClear;
    AutoCompleteTextView address;
    TextInputLayout otherLayout;
    RadioButton rbtmale,rbtfemale;
    ImageView dob_calender;
    RadioGroup rg1;
    GenericFunctions functions;
    private List<String> resultList=new ArrayList<>();
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    //------------ make your specific key ------------
    private static final String API_KEY = "AIzaSyDQy-sYUScw5BJkClbJH6HC93gpk4B2Am4";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        personName=(EditText)findViewById(R.id.editPersonName);
        mobileNo=(EditText)findViewById(R.id.editMobileNo);
        email=(EditText)findViewById(R.id.editEmail);
        dateOfBirth=(EditText)findViewById(R.id.editdob);
        address=(AutoCompleteTextView) findViewById(R.id.editAddress);
        pincode=(EditText)findViewById(R.id.editPincode);
        otherIndustry=(EditText)findViewById(R.id.editOtherType);
        password=(EditText)findViewById(R.id.editPassword);
        confirmPassword=(EditText)findViewById(R.id.editConfirmPassword);
        usertypeSpinner=(Spinner)findViewById(R.id.spinnerUsertype);
        industrySpinner=(Spinner)findViewById(R.id.spinnerindustry);
        moduleSpinner=(Spinner)findViewById(R.id.spinnerCategory);
        otherLayout=(TextInputLayout)findViewById(R.id.otherLayout);
        rbtmale = (RadioButton) findViewById(R.id.rbtmale);
        rbtfemale = (RadioButton) findViewById(R.id.rbtfemale);
        dob_calender=(ImageView)findViewById(R.id.dob_calender);

        rg1 = (RadioGroup) findViewById(R.id.radiogp1);
        functions=new GenericFunctions();


        btnSubmit=(Button)findViewById(R.id.btnsubmit);
        btnClear=(Button)findViewById(R.id.btnclear);
        btnClear.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);


        address.setAdapter(new GooglePlacesAdapter(RegistrationActivity.this, R.layout.simple));


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){


            case (R.id.btnsubmit) :

                String namestr,contactstr,emailstr,DOBstr,pincodestr,passwordstr,confirmpassstr,addressstr,genderstr,
                profession,sub_profession,strIndustry;

                namestr=personName.getText().toString();
                contactstr=mobileNo.getText().toString();
                emailstr=email.getText().toString();
                DOBstr=dateOfBirth.getText().toString();
                addressstr=address.getText().toString();
                pincodestr=pincode.getText().toString();
                passwordstr=password.getText().toString();
                confirmpassstr=confirmPassword.getText().toString();
                genderstr = ((RadioButton) findViewById(rg1.getCheckedRadioButtonId())).getText().toString();

                profession = usertypeSpinner.getSelectedItem().toString();
                strIndustry = industrySpinner.getSelectedItem().toString();
                sub_profession = moduleSpinner.getSelectedItem().toString();

                resultList=GooglePlacesAdapter.getResultList();

                for(int i=0;i<resultList.size();i++)
                    System.out.println("google location="+resultList.get(i));




            case (R.id.btnclear) :
                personName.setText("");
                mobileNo.setText("");
                mobileNo.clearFocus();
                email.setText("");
                email.clearFocus();
                dateOfBirth.setText("");
                dateOfBirth.clearFocus();
                address.setText("");
                address.clearFocus();
                pincode.setText("");
                pincode.clearFocus();
                otherIndustry.setText("");
                otherIndustry.clearFocus();
                password.setText("");
                password.clearFocus();
                confirmPassword.setText("");
                confirmPassword.clearFocus();
                industrySpinner.clearFocus();
                usertypeSpinner.clearFocus();


                usertypeSpinner.setSelection(0);
                industrySpinner.setSelection(0);
                moduleSpinner.setSelection(0);

                personName.setError(null);
                mobileNo.setError(null);
                email.setError(null);
                dateOfBirth.setError(null);
                address.setError(null);
                pincode.setError(null);
                otherIndustry.setError(null);
                password.setError(null);
                confirmPassword.setError(null);
                industrySpinner.setVisibility(View.GONE);

                break;

        }

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (view.getId()){

            case (R.id.spinnerUsertype):
                break;
            case (R.id.spinnerindustry):
                break;
            case (R.id.spinnerCategory):
                break;



        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    //  method for Google API
    public static ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            //sb.append("&components=country:gr");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());

            System.out.println("URL: "+url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            // Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            // Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
                //System.out.println("Result@@@@@@@@@@"+resultList.get(i).toString());
            }
        } catch (JSONException e) {
            //Log.e(LOG_TAG, "Cannot process JSON results", e);
            e.printStackTrace();
        }


        return resultList;
    }


}
