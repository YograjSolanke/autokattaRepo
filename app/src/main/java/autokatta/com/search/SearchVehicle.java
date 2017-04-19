package autokatta.com.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.SearchVehicleResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 19/4/17.
 */

public class SearchVehicle extends Fragment implements RequestNotifier {
    View mSearchVehicle;
    private ListView searchList;

    String searchString;
    private ArrayList<SearchVehicleResponse> allSearchDataArrayList;

    String myContact;

    public static final String MyProfilePREFERENCES = "contact No";
    SharedPreferences prefs;
    ImageView filterImg;
    Button advanceSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchVehicle = inflater.inflate(R.layout.fragment_search_product, container, false);
        searchList = (ListView) mSearchVehicle.findViewById(R.id.searchlist);
        filterImg = (ImageView) mSearchVehicle.findViewById(R.id.filterimg);
        advanceSearch = (Button) mSearchVehicle.findViewById(R.id.advBtn);
        filterImg.setVisibility(View.GONE);
        advanceSearch.setVisibility(View.VISIBLE);

        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", "");

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bundle bundle = getArguments();
                    searchString = bundle.getString("searchText");
                    System.out.println("SearchString" + searchString);
                    getSearchResults(searchString);

                    advanceSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           /* FilterActivity filterActivity = new FilterActivity();
                            Bundle b=new Bundle();
                            b.putString("action", "Main");
                            filterActivity.setArguments(b);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.search_product, filterActivity);
                            fragmentTransaction.addToBackStack("filteractivity");
                            fragmentTransaction.commit();*/
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return mSearchVehicle;
    }

    /*
    search Results...
     */
    private void getSearchResults(String searchString) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getPersonSearchData(searchString, myContact);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                SearchVehicleResponse vehicleResponse = (SearchVehicleResponse) response.body();
                if (!vehicleResponse.getSuccess().isEmpty()) {
                    for (SearchVehicleResponse.Success success : vehicleResponse.getSuccess()) {
                        success.setVehicleId(success.getVehicleId());
                        success.setTitle(success.getTitle());
                        success.setCategory(success.getCategory());
                        success.setModel(success.getModel());
                        success.setManufacturer(success.getManufacturer());
                        success.setRTOCity(success.getRTOCity());
                        success.setLocationCity(success.getLocationCity());
                        success.setLocationState(success.getLocationState());
                        success.setLocationCountry(success.getLocationCountry());
                        success.setLocationAddress(success.getLocationAddress());
                        success.setYearOfRegistration(success.getYearOfRegistration());
                        success.setYearOfManufacture(success.getYearOfManufacture());
                        success.setContact(success.getContact());
                        success.setColor(success.getColor());
                        success.setRegistrationNumber(success.getRegistrationNumber());
                        success.setRcAvailable(success.getRcAvailable());
                        success.setInsuranceValid(success.getInsuranceValid());
                        success.setInsuranceIdv(success.getInsuranceIdv());
                        success.setTaxValidity(success.getTaxValidity());
                        success.setFitnessValidity(success.getFitnessValidity());
                        success.setPermitValidity(success.getPermitValidity());
                        success.setFualType(success.getFualType());
                        success.setSeatingCapacity(success.getSeatingCapacity());
                        success.setPermit(success.getPermit());
                        success.setKmsRunning(success.getKmsRunning());
                        success.setNoOfOwners(success.getNoOfOwners());
                        success.setHypothication(success.getHypothication());
                        success.setEngineNo(success.getEngineNo());
                        success.setChassisNo(success.getChassisNo());
                        success.setPermit(success.getPrice());
                        success.setImage(success.getImage());
                        success.setDrive(success.getDrive());
                        success.setTransmission(success.getTransmission());
                        success.setBodyType(success.getBodyType());
                        success.setBoatType(success.getBoatType());
                        success.setRvType(success.getRvType());
                        success.setApplication(success.getApplication());
                        success.setStatus(success.getStatus());
                        success.setDate(success.getDate());
                        success.setVehiclelikestatus(success.getVehiclelikestatus());
                        success.setUsername(success.getUsername());
                        success.setBuyerLeads(success.getBuyerLeads());
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

    }

    @Override
    public void notifyString(String str) {

    }
}
