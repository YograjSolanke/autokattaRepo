package autokatta.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.EnquiryCountResponse;
import autokatta.com.response.GetVehicleByIdResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 27/3/17.
 */

public class VehicleDetails_Details extends Fragment implements RequestNotifier {

    View mVehicleDetails;
    TextView mTitleStr, mPriceStr, mViewsStr, mCallStr, mEnquiryStr;
    TextView mAddressDetails, mCategoryDetails, mSubCatDetails, mBrandDetails, mModelDetails, mVersionDetails, mYearDetails;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mVehicleDetails = inflater.inflate(R.layout.fragment_vehicle_details_details, container, false);

        mTitleStr = (TextView) mVehicleDetails.findViewById(R.id.title_str);
        mPriceStr = (TextView) mVehicleDetails.findViewById(R.id.price_str);
        mViewsStr = (TextView) mVehicleDetails.findViewById(R.id.views_str);
        mCallStr = (TextView) mVehicleDetails.findViewById(R.id.call_str);
        mEnquiryStr = (TextView) mVehicleDetails.findViewById(R.id.enquiry_str);

        mAddressDetails = (TextView) mVehicleDetails.findViewById(R.id.addressdetails);
        mCategoryDetails = (TextView) mVehicleDetails.findViewById(R.id.categorydetails);
        mSubCatDetails = (TextView) mVehicleDetails.findViewById(R.id.subcategorydetails);
        mBrandDetails = (TextView) mVehicleDetails.findViewById(R.id.branddetails);
        mModelDetails = (TextView) mVehicleDetails.findViewById(R.id.modeldetails);
        mVersionDetails = (TextView) mVehicleDetails.findViewById(R.id.versiondetails);
        mYearDetails = (TextView) mVehicleDetails.findViewById(R.id.yeardetails);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getVehicleData(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                                .getString("loginContact", ""),
                        getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("vehicle_id",""));
                getEnquiryCount(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                                .getString("loginContact", ""),
                        getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                                .getString("vehicle_id", ""));
            }
        });

        return mVehicleDetails;
    }

    private void getEnquiryCount(String loginContact, String vehicle_id) {
        ApiCall mApicall = new ApiCall(getActivity(), this);
        mApicall.getEnquiryCount(loginContact, "", "", vehicle_id);
    }

    /*
    Vehicle Details...
     */
    private void getVehicleData(String contact, String mVehicleId) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getVehicleById(contact, mVehicleId);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {

                if (response.body() instanceof GetVehicleByIdResponse) {

                GetVehicleByIdResponse mVehicleByIdResponse = (GetVehicleByIdResponse) response.body();
                for (GetVehicleByIdResponse.VehicleDatum datum : mVehicleByIdResponse.getSuccess().getVehicleData()) {
                    datum.setVehicleId(datum.getVehicleId());
                    mTitleStr.setText(datum.getTitle());
                    mPriceStr.setText(datum.getPrice());
                    mCategoryDetails.setText(datum.getCategory());
                    mSubCatDetails.setText(datum.getSubCategory());
                    mModelDetails.setText(datum.getModel());
                    mBrandDetails.setText(datum.getBrand());
                    datum.setRTOCity(datum.getRTOCity());
                    mAddressDetails.setText(datum.getLocationCity());
                    datum.setLocationState(datum.getLocationState());
                    datum.setLocationCountry(datum.getLocationCountry());
                    datum.setLocationAddress(datum.getLocationAddress());
                    datum.setYearOfRegistration(datum.getYearOfRegistration());
                    mYearDetails.setText(datum.getYearOfManufacture());
                    datum.setColor(datum.getColor());
                    datum.setRegistrationNumber(datum.getRegistrationNumber());
                    datum.setRcAvailable(datum.getRcAvailable());
                    datum.setInsuranceValid(datum.getInsuranceValid());
                    datum.setInsuranceIdv(datum.getInsuranceIdv());
                    datum.setTaxValidity(datum.getTaxValidity());
                    datum.setFitnessValidity(datum.getFitnessValidity());
                    datum.setPermitValidity(datum.getPermitValidity());
                    datum.setFualType(datum.getFualType());
                    datum.setSeatingCapacity(datum.getSeatingCapacity());
                    datum.setPermit(datum.getPermit());
                    datum.setKmsRunning(datum.getKmsRunning());
                    datum.setNoOfOwners(datum.getNoOfOwners());
                    datum.setHypothication(datum.getHypothication());
                    datum.setEngineNo(datum.getEngineNo());
                    datum.setChassisNo(datum.getChassisNo());
                    datum.setImage(datum.getImage());
                    datum.setDrive(datum.getDrive());
                    datum.setTransmission(datum.getTransmission());
                    datum.setBodyType(datum.getBodyType());
                    datum.setBoatType(datum.getBoatType());
                    datum.setRvType(datum.getRvType());
                    datum.setTyreCondition(datum.getTyreCondition());
                    datum.setBusType(datum.getBusType());
                    datum.setAirCondition(datum.getAirCondition());
                    datum.setInvoice(datum.getInvoice());
                    datum.setImplements(datum.getImplements());
                    datum.setApplication(datum.getApplication());
                    datum.setContact(datum.getContact());
                    mViewsStr.setText(datum.getViews());
                    mCallStr.setText(datum.getCalls());
                    datum.setBodyManufacturer(datum.getBodyManufacturer());
                    datum.setSeatManufacturer(datum.getSeatManufacturer());
                    datum.setUsername(datum.getUsername());
                    datum.setStoreId(datum.getStoreId());
                    datum.setStatus(datum.getStatus());

                }
                } else if (response.body() instanceof EnquiryCountResponse) {
                    EnquiryCountResponse enquiryCountResponse = (EnquiryCountResponse) response.body();
                    if (enquiryCountResponse.getSuccess() != null) {

                        String count = enquiryCountResponse.getSuccess().getEnquiryCount().toString();
                        mEnquiryStr.setText(count);

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
