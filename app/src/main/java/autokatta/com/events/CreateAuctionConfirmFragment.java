package autokatta.com.events;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.AuctionConfirmAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.AuctionAllVehicleData;
import autokatta.com.view.Create_Event;
import retrofit2.Response;

/**
 * Created by ak-003 on 4/4/17.
 */

@SuppressWarnings("unchecked")
public class CreateAuctionConfirmFragment extends Fragment implements RequestNotifier {

    public CreateAuctionConfirmFragment() {
        //empty constructor
    }

    ListView vehiclelistview;
    Button btnconfirm, btnspecial_clauses;
    RadioGroup rgshowhide;

    String contactnumber;
    String bundleAuctionTitle, bundleAuctionStartDate, bundleAuctionStartTime, bundleAuctionEndDate, bundleAuctionEndTime, bundleSpecialClauses;
    private int bundleAuctionId = 0;
    private EditText editvehicle;

    List<AuctionAllVehicleData> finalVehiclesData = new ArrayList<>();
    AuctionConfirmAdapter adapter;
    String stringVehicleIds = "", SaveActivate = "", ShowHide, stringNoofVehicle;
    RadioButton showprice, hideprice;
    private List<Boolean> isSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View root = inflater.inflate(R.layout.fragment_create_auction_confirm, null);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        contactnumber = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "7841023392");

        EditText auctionTitle = (EditText) root.findViewById(R.id.typeofauction2);
        EditText startDate = (EditText) root.findViewById(R.id.startdate);
        EditText startTime = (EditText) root.findViewById(R.id.stattime);
        EditText endDate = (EditText) root.findViewById(R.id.enddate);
        EditText endTime = (EditText) root.findViewById(R.id.endtime);
        editvehicle = (EditText) root.findViewById(R.id.editvehicle);
        vehiclelistview = (ListView) root.findViewById(R.id.vehiclelistview);

        rgshowhide = (RadioGroup) root.findViewById(R.id.rgshowhide);
        showprice = (RadioButton) root.findViewById(R.id.showprice);
        hideprice = (RadioButton) root.findViewById(R.id.hideprice);
        btnconfirm = (Button) root.findViewById(R.id.btnconfirm);
        btnspecial_clauses = (Button) root.findViewById(R.id.btnspecial_clauses);


        Bundle b = getArguments();

        bundleAuctionId = b.getInt("auction_id");
        bundleAuctionTitle = b.getString("title");
        bundleAuctionStartDate = b.getString("startdate");
        bundleAuctionStartTime = b.getString("starttime");
        bundleAuctionEndDate = b.getString("enddate");
        bundleAuctionEndTime = b.getString("endtime");
        bundleSpecialClauses = b.getString("specialClauses");
        Integer noOfVehicles = Integer.valueOf(b.getString("vehiclecount"));

        finalVehiclesData = (ArrayList<AuctionAllVehicleData>) b.getSerializable("finalVehiclesData");

        auctionTitle.setText(bundleAuctionTitle);
        startDate.setText(bundleAuctionStartDate);
        startTime.setText(bundleAuctionStartTime);
        endDate.setText(bundleAuctionEndDate);
        endTime.setText(bundleAuctionEndTime);
        editvehicle.setText(String.valueOf(noOfVehicles));

        btnspecial_clauses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String clauses[] = new String[0];
                try {
                    clauses = bundleSpecialClauses.split(",");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Special Clauses");
                builder.setCancelable(true);
                builder.setItems(clauses, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        dialog.dismiss();

                    }
                }).show();

            }
        });

        adapter = new AuctionConfirmAdapter(getActivity(), bundleAuctionId, finalVehiclesData, editvehicle, noOfVehicles);
        vehiclelistview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                int count = 0;

                isSave = adapter.returnIsSave();
                for (int i = 0; i < isSave.size(); i++) {
                    if (isSave.get(i).equals(true)) {
                        count++;
                    }
                }

                stringVehicleIds = "";
                stringNoofVehicle = editvehicle.getText().toString();
                ShowHide = ((RadioButton) root.findViewById(rgshowhide.getCheckedRadioButtonId())).getText().toString();

                List<AuctionAllVehicleData> vehicleIdsCame = adapter.checkboxVehicleIds();

                if (vehicleIdsCame.size() == 0 || ShowHide == null || ShowHide.isEmpty() || ShowHide.equals("")) {
                    if (isAdded())
                        CustomToast.customToast(getActivity(), "Please select vehicle to confirm");
                } else if (vehicleIdsCame.size() != count) {
                    if (isAdded())
                        CustomToast.customToast(getActivity(), "Please save start and reserved price");

                } else {

                    for (int i = 0; i < vehicleIdsCame.size(); i++) {
                        AuctionAllVehicleData obj = vehicleIdsCame.get(i);

                        if (stringVehicleIds.equals(""))
                            stringVehicleIds = String.valueOf(obj.vehicleId);
                        else
                            stringVehicleIds = stringVehicleIds + "," + obj.vehicleId;
                    }

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder
                            .setMessage("Do you want to ACTIVATE or SAVE an auction ?")
                            .setCancelable(true)
                            .setPositiveButton("ACTIVATE", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    SaveActivate = "ACTIVE";
                                    UpdateAuction();
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("SAVE", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    SaveActivate = "SAVE";
                                    UpdateAuction();
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

        System.out.println("finalVehiclesData " + finalVehiclesData.size());
        return root;
    }

    private void UpdateAuction() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);

        mApiCall.UpdateAuction(bundleAuctionId, bundleAuctionTitle, bundleAuctionStartDate, bundleAuctionStartTime,
                bundleAuctionEndDate, bundleAuctionEndTime, bundleSpecialClauses, stringVehicleIds, SaveActivate, ShowHide, stringNoofVehicle);
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            // CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            //CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "Create Auction Confirm Fragment");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.startsWith("Success")) {
                if (isAdded())
                    CustomToast.customToast(getActivity(), "Auction Created Successfully");

                startActivity(new Intent(getActivity(), Create_Event.class));
                getActivity().finish();
            }
        }
    }
}
