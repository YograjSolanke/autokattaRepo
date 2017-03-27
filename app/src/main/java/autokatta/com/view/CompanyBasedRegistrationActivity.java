package autokatta.com.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

import autokatta.com.R;

public class CompanyBasedRegistrationActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompany, autoDesignation;
    MultiAutoCompleteTextView autoSkills, autoDeals;
    Button Next, Cancel;
    String strCompany, strDesignation, updatecompany, updatedesignation, RegiId = "";
    String skillpart = "", skillid = "";
    String dealpart = "", dealid = "";
    String Skidlist = "", Deidlist = "";
    boolean skillflag = false;
    boolean dealflag = false;
    String Skills = "", Deals = "";
    String result = "";


    final ArrayList<String> mSkillNames = new ArrayList<>();
    final HashMap<String,String> mSkillList = new HashMap<>();

    final ArrayList<String> dealIdsList = new ArrayList<>();
    final ArrayList<String> dealNames = new ArrayList<>();

    final ArrayList<String> compId = new ArrayList<>();
    final ArrayList<String> companyList = new ArrayList<>();

    final ArrayList<String> desgId = new ArrayList<>();
    final ArrayList<String> designationList = new ArrayList<>();


    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    RelativeLayout relativeKms, relativeDistrict, relativeState;
    Spinner spinArea, spinKms;
 //   MultiSelectionSpinner spinDistrict, spinState;
    String strArea = "", strKms = "", strDistrict = "", strState = "";

    SharedPreferences prefs;
    public static final String MyloginPREFERENCES = "login";

    FragmentActivity ctx;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_based_registration);


        autoCompany = (AutoCompleteTextView) findViewById(R.id.autocompany);
        autoDesignation = (AutoCompleteTextView) findViewById(R.id.autodesignation);
        autoSkills = (MultiAutoCompleteTextView) findViewById(R.id.autoskills);
        autoDeals = (MultiAutoCompleteTextView) findViewById(R.id.autodeals);
        Next = (Button) findViewById(R.id.btnnext);
        Cancel = (Button) findViewById(R.id.btncancel);

        spinArea = (Spinner) findViewById(R.id.spinnerArea);
        spinKms = (Spinner) findViewById(R.id.spinnerKms);

      //  spinDistrict = (MultiSelectionSpinner) root.findViewById(R.id.spinnerDistrict);
      //  spinState = (MultiSelectionSpinner) root.findViewById(R.id.spinnerState);

        relativeKms = (RelativeLayout) findViewById(R.id.relSpinnerKms);
        relativeDistrict = (RelativeLayout) findViewById(R.id.relSpinnerDistrict);
        relativeState = (RelativeLayout) findViewById(R.id.relSpinnerState);


            prefs = getApplicationContext().getSharedPreferences(MyloginPREFERENCES, Context.MODE_PRIVATE);
            RegiId = prefs.getString("RegID", "");
            System.out.println("Registration id in company based registeration" + RegiId);


            try {
               // getcompanyName();
                //getCompanyNames();
              //  getcompanyDesignation();
                //getCompanyDesignations();
              //  getskills();
                //getSkils();
              //  getDealsVolley();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void checkSkills() {
        String text = autoSkills.getText().toString();
        System.out.println("texttttttttttttttttt Skills" + text.substring(0, text.length() - 1));
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        String[] parts = text.split(",");
        System.out.println("size of partssssssssssssssssss skills" + parts.length);
        if (parts.length > 5) {
            autoSkills.setError("You can add maximum five skills");
        }

    }

    public void checkDeals() {
        String text = autoDeals.getText().toString();
        System.out.println("texttttttttttttttttt Deals" + text.substring(0, text.length() - 1));
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        String[] parts = text.split(",");
        System.out.println("size of partssssssssssssssssss Deals" + parts.length);
//            if(parts.length>5)
//            {
//                autoDeals.setError("You can add maximum five Deals");
//            }

    }

}
