package autokatta.com.initial_fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment.SearchStoreFragment;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.CategoryResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 6/4/17.
 */

public class StoreSearchFragment extends Fragment implements View.OnClickListener, RequestNotifier {
    View view;

    MultiAutoCompleteTextView autoTxtCategory;
    AutoCompleteTextView autoTxtLocation;
    EditText edtPhraseSearch, edtContactSearch;
    ImageView imgGetContact;
    Spinner spnRadius;
    Button btnSearch;
    String myContact;
    SharedPreferences mSharedPreferences;
    ApiCall apiCall;
    private String[] MODULE = null;
    List<String> radiusList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_store_fragment, container, false);


        apiCall = new ApiCall(getActivity(), this);
        autoTxtCategory = (MultiAutoCompleteTextView) view.findViewById(R.id.auto_category);
        autoTxtLocation = (AutoCompleteTextView) view.findViewById(R.id.auto_location);
        edtPhraseSearch = (EditText) view.findViewById(R.id.edtPhrase);
        edtContactSearch = (EditText) view.findViewById(R.id.edtContact);
        imgGetContact = (ImageView) view.findViewById(R.id.getContact);
        spnRadius = (Spinner) view.findViewById(R.id.spn_radius);
        btnSearch = (Button) view.findViewById(R.id.search);

        imgGetContact.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
        myContact = mSharedPreferences.getString("loginContact", "7841023392");
        Log.i("myContact", myContact);

        /*
        "Select Radius", "Below 5 Kms", "5-10 Kms", "10-15 Kms", "15-20 Kms", "More than 20 Kms"
         */
        radiusList = new ArrayList<>();
        radiusList.add("Select Radius");
        radiusList.add("Below 5 Kms");
        radiusList.add("5-10 Kms");
        radiusList.add("10-15 Kms");
        radiusList.add("15-20 Kms");


        autoTxtCategory.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        autoTxtLocation.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.addproductspinner_color));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, radiusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRadius.setAdapter(adapter);
        //getCategories
        apiCall.Categories("");

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getContact:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // BoD con't: CONTENT_TYPE instead of CONTENT_ITEM_TYPE
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 1);
                break;

            case R.id.search:
                String strLocation = autoTxtLocation.getText().toString();
                String strCategory = autoTxtCategory.getText().toString();
                String strPhrase = edtPhraseSearch.getText().toString();
                String strContact = edtContactSearch.getText().toString();
                String strRadius = spnRadius.getSelectedItem().toString();
                int radiuspos = spnRadius.getSelectedItemPosition();

                Bundle b = new Bundle();
                b.putString("myContact", myContact);
                b.putString("location", strLocation);
                b.putString("category", strCategory);
                b.putString("phrase", strPhrase);
                b.putString("radius", strRadius);
                b.putInt("radiuspos", radiuspos);
                b.putString("contact_to_search", strContact);


                if (strCategory.equals("") && strContact.equals("")) {
                    Snackbar.make(v, "Please select category to search", Snackbar.LENGTH_SHORT).show();
                } else if (strContact.equalsIgnoreCase(myContact)) {
                    Snackbar.make(v, "you can not search store by your contact...", Snackbar.LENGTH_SHORT).show();

                } else {
                    SearchStoreFragment searchStoreFragment = new SearchStoreFragment();
                    searchStoreFragment.setArguments(b);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.searchStoreFrame, searchStoreFragment);
                    fragmentTransaction.commit();
                }
                break;

        }
    }


    /*
        To get contacts from mobile
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri uri = data.getData();

            if (uri != null) {
                Cursor c = null;
                try {
                    c = getActivity().getContentResolver().query(uri, new String[]{
                                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.CommonDataKinds.Phone.TYPE},
                            null, null, null);

                    if (c != null && c.moveToFirst()) {
                        String number = c.getString(0);
                        int type = c.getInt(1);

                        number = number.replaceAll("-", "");
                        number = number.replace("(", "").replace(")", "").replaceAll(" ", "");

                        if (number.length() > 10)
                            number = number.substring(number.length() - 10);

                        showSelectedNumber(type, number);
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
    }

    public void showSelectedNumber(int type, String number) {
//		Toast.makeText(getActivity(), type + ": " + number, Toast.LENGTH_LONG).show();
        edtContactSearch.setText(number);
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {
            if (response.isSuccessful()) {

                /*
                        Response to get category
                 */
                if (response.body() instanceof CategoryResponse) {
                    CategoryResponse moduleResponse = (CategoryResponse) response.body();
                    final List<String> module = new ArrayList<String>();

                    if (!moduleResponse.getSuccess().isEmpty()) {

                        for (CategoryResponse.Success message : moduleResponse.getSuccess()) {
                            module.add(message.getTitle());

                        }
                        MODULE = new String[module.size()];
                        MODULE = (String[]) module.toArray(MODULE);

                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getActivity(), R.layout.addproductspinner_color, MODULE);
                        autoTxtCategory.setAdapter(dataadapter);
                    }
                }

            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "SearchStore Activity");
        }
    }

    @Override
    public void notifyString(String str) {

    }

}