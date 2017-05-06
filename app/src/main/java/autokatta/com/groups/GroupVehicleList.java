package autokatta.com.groups;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GroupVehicleRefreshAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.other.EndlessRecyclerOnScrollListener;
import autokatta.com.response.GetGroupVehiclesResponse;
import autokatta.com.response.GetRTOCityResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 24/3/17.
 */

public class GroupVehicleList extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    View mGroupVehicleList;
    String brand = "", model = "", version = "", city = "", RTOcity = "", reg_year = "",
            mgf_year = "", price = "", kmsrunning = "", no_of_owner = "";
    EditText editbrand, editmodel, editversion, editcity, editregyr, editmgfyr, editprice, editkms, editowner;
    AutoCompleteTextView editRTOcity;
    ImageView filterimg;
    RelativeLayout relativefilter;
    List<GetGroupVehiclesResponse.Success> mSuccesses = new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    GroupVehicleRefreshAdapter mGroupVehicleRefreshAdapter;
    LinearLayoutManager mLayoutManager;
    // on scroll
    private static int current_page = 1;
    private int ival = 1;
    private int loadLimit = 10;
    ApiCall mApiCall;
    String mGroupId = "";
    String className = "";
    Button goSearch;
    Bundle getBundle;

    public GroupVehicleList() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGroupVehicleList = inflater.inflate(R.layout.fragment_group_vehicle_list, container, false);

        mApiCall = new ApiCall(getActivity(), this);
        mRecyclerView = (RecyclerView) mGroupVehicleList.findViewById(R.id.rv_recycler_view);
        editbrand = (EditText) mGroupVehicleList.findViewById(R.id.editbrand);
        editmodel = (EditText) mGroupVehicleList.findViewById(R.id.editmodel);
        editversion = (EditText) mGroupVehicleList.findViewById(R.id.editversion);
        editcity = (EditText) mGroupVehicleList.findViewById(R.id.editcity);
        editRTOcity = (AutoCompleteTextView) mGroupVehicleList.findViewById(R.id.editrtocity);
        editregyr = (EditText) mGroupVehicleList.findViewById(R.id.editregyear);
        editmgfyr = (EditText) mGroupVehicleList.findViewById(R.id.editmgfyear);
        editprice = (EditText) mGroupVehicleList.findViewById(R.id.editprice);
        editkms = (EditText) mGroupVehicleList.findViewById(R.id.editkmsrun);
        editowner = (EditText) mGroupVehicleList.findViewById(R.id.editowners);
        filterimg = (ImageView) mGroupVehicleList.findViewById(R.id.filterimg);
        relativefilter = (RelativeLayout) mGroupVehicleList.findViewById(R.id.relative_filter);
        goSearch = (Button) mGroupVehicleList.findViewById(R.id.goSearch);

        mSwipeRefreshLayout = (SwipeRefreshLayout) mGroupVehicleList.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //getData();//Get Api...
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                //getGroupVehicles();
                if (className != null && !className.equalsIgnoreCase("MemberListRefreshAdapter")) {
                    getGroupVehicles();
                } else if (className != null && className.equalsIgnoreCase("MemberListRefreshAdapter")) {
                    String Rcontact = getBundle.getString("Rcontact");
                    getMyVehicles(Rcontact);
                }
            }
        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getBundle = getArguments();
                    className = getBundle.getString("className");
                    mGroupId = getBundle.getString("bundle_GroupId");
                    if (className != null && !className.equalsIgnoreCase("MemberListRefreshAdapter")) {
                        getGroupVehicles();
                    } else if (className != null && className.equalsIgnoreCase("MemberListRefreshAdapter")) {
                        String Rcontact = getBundle.getString("Rcontact");
                        getMyVehicles(Rcontact);
                    }

                    getRtoCity();


                /*
                Recycler View OnScrollChanged Listener...
                 */
                    mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
                        @Override
                        public void onLoadMore(int current_page) {
                            Log.i("Loading", "->");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        filterimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSwipeRefreshLayout.getVisibility() == View.VISIBLE) {
                    mSwipeRefreshLayout.setVisibility(View.GONE);
                    relativefilter.setVisibility(View.VISIBLE);
                } else {
                    mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                    relativefilter.setVisibility(View.GONE);
                }
            }
        });

                /*
                get Relative layout data...
                 */

        goSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                brand = editbrand.getText().toString();
                model = editmodel.getText().toString();
                version = editversion.getText().toString();
                city = editcity.getText().toString();
                RTOcity = editRTOcity.getText().toString();
                price = editprice.getText().toString();
                reg_year = editregyr.getText().toString();
                mgf_year = editmgfyr.getText().toString();
                kmsrunning = editkms.getText().toString();
                no_of_owner = editowner.getText().toString();

                if (brand.equals("") && model.equals("") && version.equals("") && city.equals("") && RTOcity.equals("")
                        && price.equals("") && reg_year.equals("") && mgf_year.equals("") && kmsrunning.equals("")
                        && no_of_owner.equals("")) {
                    Snackbar.make(v, "Enter value to search", Snackbar.LENGTH_SHORT).show();
                } else {
                    getGroupVehicles();
                }

            }
        });
        return mGroupVehicleList;
    }

    /*
    get Owned vehicles
     */
    private void getMyVehicles(String rcontact) {
        mApiCall.getMyVehicles(rcontact);
    }

    /*
    Rto city
     */
    private void getRtoCity() {
        mApiCall.getVehicleRTOCity();
    }

    /*
    Group Vehicle List...
     */
    private void getGroupVehicles() {
        mApiCall.getGroupVehicles(mGroupId, brand, model, version, city, RTOcity, price, reg_year, mgf_year, kmsrunning, no_of_owner);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.body() instanceof GetGroupVehiclesResponse) {
                    mSuccesses.clear();
                    GetGroupVehiclesResponse mGetGroupVehiclesResponse = (GetGroupVehiclesResponse) response.body();
                    for (GetGroupVehiclesResponse.Success success : mGetGroupVehiclesResponse.getSuccess()) {
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
                        success.setPrice(success.getPrice());
                        //success.setImage(success.getImage());
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

                        String img = success.getImage();
                        String firstWord = "", all = "";
                        if (img.contains(",")) {
                            String arr[] = img.split(",", 2);

                            firstWord = arr[0].replaceAll(" ", "");

                            System.out.println("firstword imaggggg=========" + firstWord);

                            success.setSingleImage(firstWord);

                            all = img.replace(",", "/ ");
                            System.out.println("All images are::" + all);
                            success.setAllImage(all);

                        } else {

                            System.out.println("otherrrr imaggggg=========" + img);

                            success.setSingleImage(img);
                            success.setAllImage(all);
                        }

                        mSuccesses.add(success);
                    }
                    mGroupVehicleRefreshAdapter = new GroupVehicleRefreshAdapter(getActivity(), mSuccesses);
                    if (mSuccesses.size() != 0) {
                        mRecyclerView.setAdapter(mGroupVehicleRefreshAdapter);
                        mGroupVehicleRefreshAdapter.notifyDataSetChanged();
                    }
                    mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                    relativefilter.setVisibility(View.GONE);


                }
                //Rto city
                else if (response.body() instanceof GetRTOCityResponse) {
                    final List<String> mRtoCity = new ArrayList<String>();
                    mRtoCity.add("-Select RTO city-");
                    GetRTOCityResponse mGetRTOCityResponse = (GetRTOCityResponse) response.body();
                    for (GetRTOCityResponse.Success success : mGetRTOCityResponse.getSuccess()) {
                        success.setRtoCityId(success.getRtoCityId());
                        success.setRtoCityName(success.getRtoCityName());
                        mRtoCity.add(success.getRtoCityName());
                    }
                    if (getActivity() != null) {
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                                R.layout.registration_spinner, mRtoCity);
                        editRTOcity.setAdapter(dataAdapter);
                    }
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Toast.makeText(getActivity(), getString(R.string._404), Toast.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else {
            Log.i("Check Class-"
                    , "GroupVehicle List");
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {
        mSuccesses.clear();
        if (className != null && !className.equalsIgnoreCase("MemberListRefreshAdapter")) {
            getGroupVehicles();
        } else if (className != null && className.equalsIgnoreCase("MemberListRefreshAdapter")) {
            String Rcontact = getBundle.getString("Rcontact");
            getMyVehicles(Rcontact);
        }
        //getGroupVehicles();
    }
}
