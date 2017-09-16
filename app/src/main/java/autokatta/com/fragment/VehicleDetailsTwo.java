package autokatta.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetVehicleByIdResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 27/3/17.
 */

public class VehicleDetailsTwo extends Fragment implements RequestNotifier {
    View mVehicleTwo;
    TextView regyeardetails, kmsdetails, rtodetails, insurencedetails, ownerdetails;
    TextView fueldetails, colordetails, rcdetails, insvaldetails, taxdetails, fitnessdetails, permitvaldetails, seatingdetails, permitdetails, hypodetails, drivedetails, transmissiondetails, bodydetails, boatdetails, rvdetails, applicationdetails;
    TextView tyredetails, bustypedetails, airdetails, invoicedetails, impldetails, registrationdetails, enginedetails, chassicdetails, bodymfgdetails, seatmfgdetails;
    TextView rtotext, insurencetext, ownertext, fueltext, colortext, rctext, insvaltext, taxtext, fitnesstext, permitvaltext, seatingtext, permittext, hypotext, drivetext, transmissiontext, bodytext, boattext, rvtext, applicationtext;
    TextView tyretext, bustypetext, airtext, invoicetext, impltext, registrationtext, enginetext, chassictext, bodymfgtext, seatmfgtext;
    ConnectionDetector mTestConnection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mVehicleTwo = inflater.inflate(R.layout.fragment_vehicle_details_two, container, false);

        mTestConnection = new ConnectionDetector(getActivity());

        fueldetails = (TextView) mVehicleTwo.findViewById(R.id.fueldetails);
        colordetails = (TextView) mVehicleTwo.findViewById(R.id.colordetails);
        kmsdetails = (TextView) mVehicleTwo.findViewById(R.id.kmsdetails);

        rtodetails = (TextView) mVehicleTwo.findViewById(R.id.rtodetails);
        regyeardetails = (TextView) mVehicleTwo.findViewById(R.id.regyeardetails);

        rcdetails = (TextView) mVehicleTwo.findViewById(R.id.rc_availabledetails);
        insurencedetails = (TextView) mVehicleTwo.findViewById(R.id.insurencedetails);
        ownerdetails = (TextView) mVehicleTwo.findViewById(R.id.ownerdetails);
        insvaldetails = (TextView) mVehicleTwo.findViewById(R.id.ins_validitydetails);
        taxdetails = (TextView) mVehicleTwo.findViewById(R.id.tax_validitydetails);
        fitnessdetails = (TextView) mVehicleTwo.findViewById(R.id.fitness_validitydetails);
        permitvaldetails = (TextView) mVehicleTwo.findViewById(R.id.permit_validitydetails);
        seatingdetails = (TextView) mVehicleTwo.findViewById(R.id.seatingdetails);
        permitdetails = (TextView) mVehicleTwo.findViewById(R.id.permitdetails);
        hypodetails = (TextView) mVehicleTwo.findViewById(R.id.hypodetails);
        drivedetails = (TextView) mVehicleTwo.findViewById(R.id.drivedetails);
        transmissiondetails = (TextView) mVehicleTwo.findViewById(R.id.transmissiondetails);
        bodydetails = (TextView) mVehicleTwo.findViewById(R.id.bodydetails);
        boatdetails = (TextView) mVehicleTwo.findViewById(R.id.boatdetails);
        rvdetails = (TextView) mVehicleTwo.findViewById(R.id.rvdetails);
        applicationdetails = (TextView) mVehicleTwo.findViewById(R.id.applicationdetails);

        tyredetails = (TextView) mVehicleTwo.findViewById(R.id.tyredetails);
        bustypedetails = (TextView) mVehicleTwo.findViewById(R.id.bustypedetails);
        airdetails = (TextView) mVehicleTwo.findViewById(R.id.airdetails);
        invoicedetails = (TextView) mVehicleTwo.findViewById(R.id.invoicedetails);
        impldetails = (TextView) mVehicleTwo.findViewById(R.id.implementationdetails);
        registrationdetails = (TextView) mVehicleTwo.findViewById(R.id.registrationdetails);
        enginedetails = (TextView) mVehicleTwo.findViewById(R.id.enginedetails);
        chassicdetails = (TextView) mVehicleTwo.findViewById(R.id.chassicdetails);
        bodymfgdetails = (TextView) mVehicleTwo.findViewById(R.id.bodymfgdetails);
        seatmfgdetails = (TextView) mVehicleTwo.findViewById(R.id.seatmfgdetails);

        insurencetext = (TextView) mVehicleTwo.findViewById(R.id.insurencetext);
        ownertext = (TextView) mVehicleTwo.findViewById(R.id.ownertext);
        rtotext = (TextView) mVehicleTwo.findViewById(R.id.rtotext);
        rctext = (TextView) mVehicleTwo.findViewById(R.id.rc_availabletxt);
        insvaltext = (TextView) mVehicleTwo.findViewById(R.id.ins_validitytext);
        taxtext = (TextView) mVehicleTwo.findViewById(R.id.tax_validitytext);
        fitnesstext = (TextView) mVehicleTwo.findViewById(R.id.fitness_validitytext);
        seatingtext = (TextView) mVehicleTwo.findViewById(R.id.seatcaptxt);
        permittext = (TextView) mVehicleTwo.findViewById(R.id.permittext);
        permitvaltext = (TextView) mVehicleTwo.findViewById(R.id.permit_validitytext);
        hypotext = (TextView) mVehicleTwo.findViewById(R.id.hypotext);
        drivetext = (TextView) mVehicleTwo.findViewById(R.id.drivetext);
        transmissiontext = (TextView) mVehicleTwo.findViewById(R.id.transmissiontext);
        bodytext = (TextView) mVehicleTwo.findViewById(R.id.bodytext);
        boattext = (TextView) mVehicleTwo.findViewById(R.id.boattext);
        rvtext = (TextView) mVehicleTwo.findViewById(R.id.rvtext);
        applicationtext = (TextView) mVehicleTwo.findViewById(R.id.applicationtext);
        tyretext = (TextView) mVehicleTwo.findViewById(R.id.tyretext);
        bustypetext = (TextView) mVehicleTwo.findViewById(R.id.bustext);
        airtext = (TextView) mVehicleTwo.findViewById(R.id.airtext);
        invoicetext = (TextView) mVehicleTwo.findViewById(R.id.invoicetext);
        impltext = (TextView) mVehicleTwo.findViewById(R.id.implimentstext);
        fueltext = (TextView) mVehicleTwo.findViewById(R.id.fueltxt);
        colortext = (TextView) mVehicleTwo.findViewById(R.id.colortext);
        registrationtext = (TextView) mVehicleTwo.findViewById(R.id.registrationtext);
        enginetext = (TextView) mVehicleTwo.findViewById(R.id.enginetext);
        chassictext = (TextView) mVehicleTwo.findViewById(R.id.chassictext);
        bodymfgtext = (TextView) mVehicleTwo.findViewById(R.id.bodymfgtext);
        seatmfgtext = (TextView) mVehicleTwo.findViewById(R.id.seatmfgtext);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getVehicleData(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                                .getString("loginContact", ""),
                        getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                                .getInt("vehicle_id", 0));
            }
        });
        return mVehicleTwo;
    }

    /*
    Vehicle Details...
     */
    private void getVehicleData(String contact, int mVehicleId) {

        if (mTestConnection.isConnectedToInternet()) {
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.getVehicleById(contact, mVehicleId);
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            // errorMessage(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                GetVehicleByIdResponse mVehicleByIdResponse = (GetVehicleByIdResponse) response.body();
                for (GetVehicleByIdResponse.VehicleDatum datum : mVehicleByIdResponse.getSuccess().getVehicleData()) {

                    regyeardetails.setText(datum.getYearOfRegistration());

                    if (datum.getInsuranceIdv() == null || datum.getInsuranceIdv().equalsIgnoreCase("")) {
                        insurencedetails.setText(getString(R.string.not_mentioned));
                    } else {
                        insurencedetails.setText(datum.getInsuranceIdv());
                    }

                    if (datum.getNoOfOwners() == null || datum.getNoOfOwners() == 0) {
                        ownerdetails.setText(getString(R.string.not_mentioned));
                    } else {
                        ownerdetails.setText(String.valueOf(datum.getNoOfOwners()));
                    }

                    if (datum.getFualType() == null || datum.getFualType().equalsIgnoreCase("")) {
                        fueldetails.setText(getString(R.string.not_mentioned));
                    } else {
                        fueldetails.setText(datum.getFualType());
                    }

                    if (datum.getColor() == null || datum.getColor().equalsIgnoreCase("")) {
                        colordetails.setText(getString(R.string.not_mentioned));
                    } else {
                        colordetails.setText(datum.getColor());
                    }

                    if (datum.getKmsRunning() == null || datum.getKmsRunning() == 0.0 || datum.getKmsRunning() == 0) {
                        kmsdetails.setText(getString(R.string.not_mentioned));
                    } else {
                        kmsdetails.setText(String.valueOf(datum.getKmsRunning()));
                    }

                    if (datum.getRTOCity() == null || datum.getRTOCity().equalsIgnoreCase("")) {
                        rtodetails.setText(getString(R.string.not_mentioned));
                    } else {
                        rtodetails.setText(datum.getRTOCity());
                    }

                    if (datum.getRcAvailable() == null || datum.getRcAvailable().equalsIgnoreCase("")) {
                        rcdetails.setText(getString(R.string.not_mentioned));
                    } else {
                        rcdetails.setText(datum.getRcAvailable());
                    }

                    if (datum.getInsuranceValid() == null || datum.getInsuranceValid().equalsIgnoreCase("")) {
                        insvaldetails.setText(getString(R.string.not_mentioned));
                    } else {
                        insvaldetails.setText(datum.getInsuranceValid());
                    }

                    if (datum.getTaxPaidUpto() == null || datum.getTaxPaidUpto().equalsIgnoreCase("")) {
                        taxdetails.setText(getString(R.string.not_mentioned));
                    } else {
                        taxdetails.setText(datum.getTaxPaidUpto());
                    }

                    if (datum.getFitnessYesNo() == null || datum.getFitnessYesNo().equalsIgnoreCase("")) {
                        fitnessdetails.setText(getString(R.string.not_mentioned));
                    } else {
                        fitnessdetails.setText(datum.getFitnessYesNo());
                    }

                    if (datum.getPermitYesNo() == null || datum.getPermitYesNo().equalsIgnoreCase("")) {
                        permitvaldetails.setText(getString(R.string.not_mentioned));
                    } else {
                        permitvaldetails.setText(datum.getPermitYesNo());
                    }

                    if (datum.getPermit() == null || datum.getPermit().equalsIgnoreCase("")) {
                        permitdetails.setText(getString(R.string.not_mentioned));
                    } else {
                        permitdetails.setText(datum.getPermit());
                    }

                    if (datum.getSeatingCapacity() == null || datum.getSeatingCapacity().equalsIgnoreCase("")) {
                        seatingdetails.setText(getString(R.string.not_mentioned));
                    } else {
                        seatingdetails.setText(datum.getSeatingCapacity());
                    }

                    if (datum.getHypothication() == null || datum.getHypothication().equalsIgnoreCase("")) {
                        hypodetails.setText(getString(R.string.not_mentioned));
                    } else {
                        hypodetails.setText(datum.getHypothication());
                    }

                    if (datum.getDrive() == null || datum.getDrive().equalsIgnoreCase("")) {
                        drivedetails.setText(getString(R.string.not_mentioned));
                    } else {
                        drivedetails.setText(datum.getDrive());
                    }

                    if (datum.getTransmission() == null || datum.getTransmission().equalsIgnoreCase("")) {
                        transmissiondetails.setText(getString(R.string.not_mentioned));
                    } else {
                        transmissiondetails.setText(datum.getTransmission());
                    }

                    if (datum.getBodyType() == null || datum.getBodyType().equalsIgnoreCase("")) {
                        bodydetails.setText(getString(R.string.not_mentioned));
                    } else {
                        bodydetails.setText(datum.getBodyType());
                    }

                    if (datum.getBoatType() == null || datum.getBoatType().equalsIgnoreCase("")) {
                        boatdetails.setText(getString(R.string.not_mentioned));
                    } else {
                        boatdetails.setText(datum.getBoatType());
                    }

                    if (datum.getRvType() == null || datum.getRvType().equalsIgnoreCase("")) {
                        rvdetails.setText(getString(R.string.not_mentioned));
                    } else {
                        rvdetails.setText(datum.getRvType());
                    }

                    if (datum.getApplication() == null || datum.getApplication().equalsIgnoreCase("")) {
                        applicationdetails.setText(getString(R.string.not_mentioned));
                    } else {
                        applicationdetails.setText(datum.getApplication());
                    }

                    if (datum.getTyreCondition() == null || datum.getTyreCondition().equalsIgnoreCase("")) {
                        tyredetails.setText(getString(R.string.not_mentioned));
                    } else {
                        tyredetails.setText(datum.getTyreCondition());
                    }

                    if (datum.getBusType() == null || datum.getBusType().equalsIgnoreCase("")) {
                        bustypedetails.setText(getString(R.string.not_mentioned));
                    } else {
                        bustypedetails.setText(datum.getBusType());
                    }

                    if (datum.getImplements() == null || datum.getImplements().equalsIgnoreCase("")) {
                        impldetails.setText(getString(R.string.not_mentioned));
                    } else {
                        impldetails.setText(datum.getImplements());
                    }

                    if (datum.getAirCondition() == null || datum.getAirCondition().equalsIgnoreCase("")) {
                        airdetails.setText(getString(R.string.not_mentioned));
                    } else {
                        airdetails.setText(datum.getAirCondition());
                    }

                    if (datum.getInvoice() == null || datum.getInvoice().equalsIgnoreCase("")) {
                        invoicedetails.setText(getString(R.string.not_mentioned));
                    } else {
                        invoicedetails.setText(datum.getInvoice());
                    }

                    if (datum.getRegistrationNumber() == null || datum.getRegistrationNumber().equalsIgnoreCase("")) {
                        registrationdetails.setText(getString(R.string.not_mentioned));
                    } else {
                        registrationdetails.setText(datum.getRegistrationNumber());
                    }

                    if (datum.getEngineNo() == null || datum.getEngineNo().equalsIgnoreCase("")) {
                        enginedetails.setText(getString(R.string.not_mentioned));
                    } else {
                        enginedetails.setText(datum.getEngineNo());
                    }

                    if (datum.getChassisNo() == null || datum.getChassisNo().equalsIgnoreCase("")) {
                        chassicdetails.setText(getString(R.string.not_mentioned));
                    } else {
                        chassicdetails.setText(datum.getChassisNo());
                    }

                    if (datum.getBodyManufacturer() == null || datum.getBodyManufacturer().equalsIgnoreCase("")) {
                        bodymfgdetails.setText(getString(R.string.not_mentioned));
                    } else {
                        bodymfgdetails.setText(datum.getBodyManufacturer());
                    }

                    if (datum.getSeatManufacturer() == null || datum.getSeatManufacturer().equalsIgnoreCase("")) {
                        seatmfgdetails.setText(getString(R.string.not_mentioned));
                    } else {
                        seatmfgdetails.setText(datum.getSeatManufacturer());
                    }

                    if (datum.getCategory().endsWith("car") || (datum.getCategory().equalsIgnoreCase("car") ||
                            datum.getCategory().endsWith("Car"))) {
                        permittext.setVisibility(View.VISIBLE);
                        permitdetails.setVisibility(View.VISIBLE);
                        if (datum.getPermit().equalsIgnoreCase("Private")) {
                            permitvaldetails.setVisibility(View.GONE);
                            fitnessdetails.setVisibility(View.GONE);
                            taxdetails.setVisibility(View.GONE);
                            permitvaltext.setVisibility(View.GONE);
                            fitnesstext.setVisibility(View.GONE);
                            taxtext.setVisibility(View.GONE);
                        }
                        rtodetails.setVisibility(View.VISIBLE);
                        rcdetails.setVisibility(View.VISIBLE);
                        hypodetails.setVisibility(View.VISIBLE);
                        colordetails.setVisibility(View.VISIBLE);
                        insvaldetails.setVisibility(View.VISIBLE);
                        insurencedetails.setVisibility(View.VISIBLE);
                        fueldetails.setVisibility(View.VISIBLE);
                        rtotext.setVisibility(View.VISIBLE);
                        rctext.setVisibility(View.VISIBLE);
                        hypotext.setVisibility(View.VISIBLE);
                        colortext.setVisibility(View.VISIBLE);
                        insvaltext.setVisibility(View.VISIBLE);
                        insurencetext.setVisibility(View.VISIBLE);
                        fueltext.setVisibility(View.VISIBLE);
                        drivedetails.setVisibility(View.VISIBLE);
                        transmissiondetails.setVisibility(View.VISIBLE);
                        seatingdetails.setVisibility(View.VISIBLE);
                        drivetext.setVisibility(View.VISIBLE);
                        transmissiontext.setVisibility(View.VISIBLE);
                        seatingtext.setVisibility(View.VISIBLE);
                        registrationdetails.setVisibility(View.VISIBLE);
                        registrationtext.setVisibility(View.VISIBLE);
                        enginedetails.setVisibility(View.VISIBLE);
                        enginetext.setVisibility(View.VISIBLE);
                        chassicdetails.setVisibility(View.VISIBLE);
                        chassictext.setVisibility(View.VISIBLE);
                    }

                    if (datum.getCategory().endsWith("Bus")) {
                        seatingdetails.setVisibility(View.VISIBLE);
                        tyredetails.setVisibility(View.VISIBLE);
                        bustypedetails.setVisibility(View.VISIBLE);
                        airdetails.setVisibility(View.VISIBLE);
                        seatingtext.setVisibility(View.VISIBLE);
                        tyretext.setVisibility(View.VISIBLE);
                        bustypetext.setVisibility(View.VISIBLE);
                        airtext.setVisibility(View.VISIBLE);
                        rtodetails.setVisibility(View.VISIBLE);
                        rcdetails.setVisibility(View.VISIBLE);
                        hypodetails.setVisibility(View.VISIBLE);
                        insvaldetails.setVisibility(View.VISIBLE);
                        insurencedetails.setVisibility(View.VISIBLE);
                        permitvaldetails.setVisibility(View.VISIBLE);
                        fitnessdetails.setVisibility(View.VISIBLE);
                        fueldetails.setVisibility(View.VISIBLE);
                        taxdetails.setVisibility(View.VISIBLE);
                        rtotext.setVisibility(View.VISIBLE);
                        rctext.setVisibility(View.VISIBLE);
                        hypotext.setVisibility(View.VISIBLE);
                        insvaltext.setVisibility(View.VISIBLE);
                        insurencetext.setVisibility(View.VISIBLE);
                        permitvaltext.setVisibility(View.VISIBLE);
                        fitnesstext.setVisibility(View.VISIBLE);
                        fueltext.setVisibility(View.VISIBLE);
                        taxtext.setVisibility(View.VISIBLE);
                        registrationdetails.setVisibility(View.VISIBLE);
                        registrationtext.setVisibility(View.VISIBLE);
                        enginedetails.setVisibility(View.VISIBLE);
                        enginetext.setVisibility(View.VISIBLE);
                        chassicdetails.setVisibility(View.VISIBLE);
                        chassictext.setVisibility(View.VISIBLE);
                        bodymfgdetails.setVisibility(View.VISIBLE);
                        bodymfgtext.setVisibility(View.VISIBLE);
                        seatmfgdetails.setVisibility(View.VISIBLE);
                        seatmfgtext.setVisibility(View.VISIBLE);
                    }

                    if (datum.getCategory().endsWith("Commercial Vehicle")) {
                        tyredetails.setVisibility(View.VISIBLE);
                        bodydetails.setVisibility(View.VISIBLE);
                        tyretext.setVisibility(View.VISIBLE);
                        bodytext.setVisibility(View.VISIBLE);
                        rtodetails.setVisibility(View.VISIBLE);
                        rcdetails.setVisibility(View.VISIBLE);
                        hypodetails.setVisibility(View.VISIBLE);
                        colordetails.setVisibility(View.VISIBLE);
                        insvaldetails.setVisibility(View.VISIBLE);
                        insurencedetails.setVisibility(View.VISIBLE);
                        permitvaldetails.setVisibility(View.VISIBLE);
                        fitnessdetails.setVisibility(View.VISIBLE);
                        fueldetails.setVisibility(View.VISIBLE);
                        taxdetails.setVisibility(View.VISIBLE);
                        rtotext.setVisibility(View.VISIBLE);
                        rctext.setVisibility(View.VISIBLE);
                        hypotext.setVisibility(View.VISIBLE);
                        colortext.setVisibility(View.VISIBLE);
                        insvaltext.setVisibility(View.VISIBLE);
                        insurencetext.setVisibility(View.VISIBLE);
                        permitvaltext.setVisibility(View.VISIBLE);
                        fitnesstext.setVisibility(View.VISIBLE);
                        fueltext.setVisibility(View.VISIBLE);
                        taxtext.setVisibility(View.VISIBLE);
                        registrationdetails.setVisibility(View.VISIBLE);
                        registrationtext.setVisibility(View.VISIBLE);
                        enginedetails.setVisibility(View.VISIBLE);
                        enginetext.setVisibility(View.VISIBLE);
                        chassicdetails.setVisibility(View.VISIBLE);
                        chassictext.setVisibility(View.VISIBLE);
                    }

                    if (datum.getCategory().endsWith("Construction Equipement ")) {
                        invoicedetails.setVisibility(View.VISIBLE);
                        invoicetext.setVisibility(View.VISIBLE);
                        rtodetails.setVisibility(View.VISIBLE);
                        hypodetails.setVisibility(View.VISIBLE);
                        insvaldetails.setVisibility(View.VISIBLE);
                        insurencedetails.setVisibility(View.VISIBLE);
                        permitvaldetails.setVisibility(View.VISIBLE);
                        getVehicleData(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                                        .getString("loginContact", ""),
                                getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                                        .getInt("vehicle_id", 0));
                        fitnessdetails.setVisibility(View.VISIBLE);
                        taxdetails.setVisibility(View.VISIBLE);
                        rtotext.setVisibility(View.VISIBLE);
                        hypotext.setVisibility(View.VISIBLE);
                        insvaltext.setVisibility(View.VISIBLE);
                        insurencetext.setVisibility(View.VISIBLE);
                        permitvaltext.setVisibility(View.VISIBLE);
                        fitnesstext.setVisibility(View.VISIBLE);
                        taxtext.setVisibility(View.VISIBLE);
                        registrationdetails.setVisibility(View.VISIBLE);
                        registrationtext.setVisibility(View.VISIBLE);
                        enginedetails.setVisibility(View.VISIBLE);
                        enginetext.setVisibility(View.VISIBLE);
                        chassicdetails.setVisibility(View.VISIBLE);
                        chassictext.setVisibility(View.VISIBLE);
                    }

                    if (datum.getCategory().startsWith("Tractor")) {
                        applicationdetails.setVisibility(View.VISIBLE);
                        impldetails.setVisibility(View.VISIBLE);
                        tyredetails.setVisibility(View.VISIBLE);
                        applicationtext.setVisibility(View.VISIBLE);
                        impltext.setVisibility(View.VISIBLE);
                        tyretext.setVisibility(View.VISIBLE);
                        rtodetails.setVisibility(View.VISIBLE);
                        rcdetails.setVisibility(View.VISIBLE);
                        insvaldetails.setVisibility(View.VISIBLE);
                        insurencedetails.setVisibility(View.VISIBLE);
                        permitvaldetails.setVisibility(View.VISIBLE);
                        fitnessdetails.setVisibility(View.VISIBLE);
                        taxdetails.setVisibility(View.VISIBLE);
                        rtotext.setVisibility(View.VISIBLE);
                        rctext.setVisibility(View.VISIBLE);
                        insvaltext.setVisibility(View.VISIBLE);
                        insurencetext.setVisibility(View.VISIBLE);
                        permitvaltext.setVisibility(View.VISIBLE);
                        fitnesstext.setVisibility(View.VISIBLE);
                        taxtext.setVisibility(View.VISIBLE);
                        registrationdetails.setVisibility(View.VISIBLE);
                        registrationtext.setVisibility(View.VISIBLE);
                        enginedetails.setVisibility(View.VISIBLE);
                        enginetext.setVisibility(View.VISIBLE);
                        chassicdetails.setVisibility(View.VISIBLE);
                        chassictext.setVisibility(View.VISIBLE);
                    }

                    if (datum.getCategory().endsWith("2 Wheeler") || (datum.getCategory().endsWith("Bikes"))) {
                        rtodetails.setVisibility(View.VISIBLE);
                        rcdetails.setVisibility(View.VISIBLE);
                        hypodetails.setVisibility(View.VISIBLE);
                        colordetails.setVisibility(View.VISIBLE);
                        insvaldetails.setVisibility(View.VISIBLE);
                        insurencedetails.setVisibility(View.VISIBLE);
                        fueldetails.setVisibility(View.VISIBLE);
                        rtotext.setVisibility(View.VISIBLE);
                        rctext.setVisibility(View.VISIBLE);
                        hypotext.setVisibility(View.VISIBLE);
                        colortext.setVisibility(View.VISIBLE);
                        insvaltext.setVisibility(View.VISIBLE);
                        insurencetext.setVisibility(View.VISIBLE);
                        fueltext.setVisibility(View.VISIBLE);
                        registrationdetails.setVisibility(View.VISIBLE);
                        registrationtext.setVisibility(View.VISIBLE);
                        enginedetails.setVisibility(View.VISIBLE);
                        enginetext.setVisibility(View.VISIBLE);
                        chassicdetails.setVisibility(View.VISIBLE);
                        chassictext.setVisibility(View.VISIBLE);
                    }

                    if (datum.getCategory().equalsIgnoreCase("3 Wheeler")) {
                        seatingdetails.setVisibility(View.VISIBLE);
                        tyredetails.setVisibility(View.VISIBLE);
                        seatingtext.setVisibility(View.VISIBLE);
                        tyretext.setVisibility(View.VISIBLE);
                        rtodetails.setVisibility(View.VISIBLE);
                        rcdetails.setVisibility(View.VISIBLE);
                        hypodetails.setVisibility(View.VISIBLE);
                        colordetails.setVisibility(View.VISIBLE);
                        insvaldetails.setVisibility(View.VISIBLE);
                        insurencedetails.setVisibility(View.VISIBLE);
                        permitvaldetails.setVisibility(View.VISIBLE);
                        fitnessdetails.setVisibility(View.VISIBLE);
                        fueldetails.setVisibility(View.VISIBLE);
                        taxdetails.setVisibility(View.VISIBLE);
                        rtotext.setVisibility(View.VISIBLE);
                        rctext.setVisibility(View.VISIBLE);
                        hypotext.setVisibility(View.VISIBLE);
                        colortext.setVisibility(View.VISIBLE);
                        insvaltext.setVisibility(View.VISIBLE);
                        insurencetext.setVisibility(View.VISIBLE);
                        permitvaltext.setVisibility(View.VISIBLE);
                        fitnesstext.setVisibility(View.VISIBLE);
                        fueltext.setVisibility(View.VISIBLE);
                        taxtext.setVisibility(View.VISIBLE);
                        registrationdetails.setVisibility(View.VISIBLE);
                        registrationtext.setVisibility(View.VISIBLE);
                        enginedetails.setVisibility(View.VISIBLE);
                        enginetext.setVisibility(View.VISIBLE);
                        chassicdetails.setVisibility(View.VISIBLE);
                        chassictext.setVisibility(View.VISIBLE);
                    }

                    if (datum.getCategory().equalsIgnoreCase(" Main")) {
                        rtodetails.setVisibility(View.VISIBLE);
                        rcdetails.setVisibility(View.VISIBLE);
                        hypodetails.setVisibility(View.VISIBLE);
                        colordetails.setVisibility(View.VISIBLE);
                        insvaldetails.setVisibility(View.VISIBLE);
                        insurencedetails.setVisibility(View.VISIBLE);
                        permitvaldetails.setVisibility(View.VISIBLE);
                        fitnessdetails.setVisibility(View.VISIBLE);
                        fueldetails.setVisibility(View.VISIBLE);
                        taxdetails.setVisibility(View.VISIBLE);
                        rtotext.setVisibility(View.VISIBLE);
                        rctext.setVisibility(View.VISIBLE);
                        hypotext.setVisibility(View.VISIBLE);
                        colortext.setVisibility(View.VISIBLE);
                        insvaltext.setVisibility(View.VISIBLE);
                        insurencetext.setVisibility(View.VISIBLE);
                        permitvaltext.setVisibility(View.VISIBLE);
                        fitnesstext.setVisibility(View.VISIBLE);
                        fueltext.setVisibility(View.VISIBLE);
                        taxtext.setVisibility(View.VISIBLE);
                        registrationdetails.setVisibility(View.VISIBLE);
                        registrationtext.setVisibility(View.VISIBLE);
                        enginedetails.setVisibility(View.VISIBLE);
                        enginetext.setVisibility(View.VISIBLE);
                        chassicdetails.setVisibility(View.VISIBLE);
                        chassictext.setVisibility(View.VISIBLE);
                    }

                    if (datum.getCategory().equalsIgnoreCase("MUV")) {
                        rtodetails.setVisibility(View.VISIBLE);
                        rcdetails.setVisibility(View.VISIBLE);
                        hypodetails.setVisibility(View.VISIBLE);
                        colordetails.setVisibility(View.VISIBLE);
                        insvaldetails.setVisibility(View.VISIBLE);
                        insurencedetails.setVisibility(View.VISIBLE);
                        permitvaldetails.setVisibility(View.VISIBLE);
                        fitnessdetails.setVisibility(View.VISIBLE);
                        fueldetails.setVisibility(View.VISIBLE);
                        taxdetails.setVisibility(View.VISIBLE);
                        rtotext.setVisibility(View.VISIBLE);
                        rctext.setVisibility(View.VISIBLE);
                        hypotext.setVisibility(View.VISIBLE);
                        colortext.setVisibility(View.VISIBLE);
                        insvaltext.setVisibility(View.VISIBLE);
                        insurencetext.setVisibility(View.VISIBLE);
                        permitvaltext.setVisibility(View.VISIBLE);
                        fitnesstext.setVisibility(View.VISIBLE);
                        fueltext.setVisibility(View.VISIBLE);
                        taxtext.setVisibility(View.VISIBLE);
                        registrationdetails.setVisibility(View.VISIBLE);
                        registrationtext.setVisibility(View.VISIBLE);
                        enginedetails.setVisibility(View.VISIBLE);
                        enginetext.setVisibility(View.VISIBLE);
                        chassicdetails.setVisibility(View.VISIBLE);
                        chassictext.setVisibility(View.VISIBLE);
                    }

                    if (datum.getCategory().equalsIgnoreCase("SUV")) {
                        rtodetails.setVisibility(View.VISIBLE);
                        rcdetails.setVisibility(View.VISIBLE);
                        hypodetails.setVisibility(View.VISIBLE);
                        colordetails.setVisibility(View.VISIBLE);
                        insvaldetails.setVisibility(View.VISIBLE);
                        insurencedetails.setVisibility(View.VISIBLE);
                        permitvaldetails.setVisibility(View.VISIBLE);
                        fitnessdetails.setVisibility(View.VISIBLE);
                        fueldetails.setVisibility(View.VISIBLE);
                        taxdetails.setVisibility(View.VISIBLE);
                        rtotext.setVisibility(View.VISIBLE);
                        rctext.setVisibility(View.VISIBLE);
                        hypotext.setVisibility(View.VISIBLE);
                        colortext.setVisibility(View.VISIBLE);
                        insvaltext.setVisibility(View.VISIBLE);
                        insurencetext.setVisibility(View.VISIBLE);
                        permitvaltext.setVisibility(View.VISIBLE);
                        fitnesstext.setVisibility(View.VISIBLE);
                        fueltext.setVisibility(View.VISIBLE);
                        taxtext.setVisibility(View.VISIBLE);
                        registrationdetails.setVisibility(View.VISIBLE);
                        registrationtext.setVisibility(View.VISIBLE);
                        enginedetails.setVisibility(View.VISIBLE);
                        enginetext.setVisibility(View.VISIBLE);
                        chassicdetails.setVisibility(View.VISIBLE);
                        chassictext.setVisibility(View.VISIBLE);
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
            CustomToast.customToast(getActivity(), getString(R.string._404_));
            //   showMessage(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
            // showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
            //   showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "VehicleDetails_Details");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    /*public void showMessage(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG);
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    public void errorMessage(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getVehicleData(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                                        .getString("loginContact", ""),
                                getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                                        .getString("vehicle_id", ""));
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.BLUE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }*/
}
