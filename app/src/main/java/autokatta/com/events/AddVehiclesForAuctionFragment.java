package autokatta.com.events;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.AuctionAdminVehiclesAdapter;
import autokatta.com.adapter.AuctionBySelfVehiclesAdapter;
import autokatta.com.adapter.AuctionReauctionVehiclesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.GenericFunctions;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.AdminExcelSheetResponse;
import autokatta.com.response.AdminVehiclesResponse;
import autokatta.com.response.AuctionAllVehicleData;
import autokatta.com.response.AuctionAllVehicleResponse;
import autokatta.com.response.AuctionReauctionVehicleResponse;
import autokatta.com.response.SpecialClauseGetResponse;
import retrofit2.Response;

/**
 * Created by ak-003 on 1/4/17.
 */

public class AddVehiclesForAuctionFragment extends Fragment implements RequestNotifier, View.OnTouchListener, View.OnClickListener {

    public AddVehiclesForAuctionFragment() {
        //empty constructor
    }

    String contactnumber, str, singleAuctionId, UserId, sheet = "", ExcelsheetName = "";
    public static EditText editNoOfVehicles;
    public static int IntVehicleNo;
    EditText auctionTitle, startDate, startTime, endDate, endTime;
    Button btnspecial_clauses;
    ImageView editpencil, donecheck;
    Button btnbyteam, btnbyself, btnbyadmin, btnbyreauction, buttonnext;
    ListView byteam_listview, byself_listview, byadmin_listview, byreauction_listview;
    Spinner selectAuctionsSpinner;
    String className, auction_id, bundleAuctionTitle, bundleAuctionStartDate, bundleAuctionStartTime, bundleAuctionEndDate,
            bundleAuctionEndTime, bundleIds, bundleClause, bundleCategory, bundleLocation;
    boolean bundlepositionArray[];
    String auctionTitleUpdate, startDateUpdate, startTimeUpdate, endDateUpdate, endTimeUpdate, specialClausesUpdate, specialClausesIDUpdate;

    GenericFunctions genericFunctions = new GenericFunctions();
    List<Integer> auctionIds = new ArrayList<>();
    List<String> auctionTitles = new ArrayList<>();
    List<String> getIds = new ArrayList<>();
    List<String> getClauses = new ArrayList<>();

    AuctionReauctionVehiclesAdapter reauctionadapter;
    AuctionBySelfVehiclesAdapter selfadapter;
    AuctionAdminVehiclesAdapter adminadapter;

    TextView txtSheets;
    ApiCall mApiCall;
    CollapsingToolbarLayout collapsingToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final View root = inflater.inflate(R.layout.fragment_addvehicles_auction, container, false);

        contactnumber = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "7841023392");
        mApiCall = new ApiCall(getActivity(), this);
        Bundle b = getArguments();

        auction_id = b.getString("auction_id");
        className = b.getString("className");
        bundleAuctionTitle = b.getString("title");
        bundleAuctionStartDate = b.getString("startdate");
        bundleAuctionStartTime = b.getString("starttime");
        bundleAuctionEndDate = b.getString("enddate");
        bundleAuctionEndTime = b.getString("endtime");
        bundleIds = b.getString("ids");
        bundleClause = b.getString("cluase");
        bundleCategory = b.getString("category");
        bundleLocation = b.getString("location");
        bundlepositionArray = b.getBooleanArray("positionArray");

        specialClausesIDUpdate = bundleIds;
        specialClausesUpdate = bundleClause;

        auctionTitle = (EditText) root.findViewById(R.id.editauctiontitle);
        editNoOfVehicles = (EditText) root.findViewById(R.id.editNoOfVehicles);
        startDate = (EditText) root.findViewById(R.id.startdate);
        startTime = (EditText) root.findViewById(R.id.starttime);
        endDate = (EditText) root.findViewById(R.id.enddate);
         IntVehicleNo= Integer.parseInt(b.getString("noofvehicles"));
        endTime = (EditText) root.findViewById(R.id.endtime);
        btnspecial_clauses = (Button) root.findViewById(R.id.btnspecial_clauses);

        auctionTitle.setText(bundleAuctionTitle);
        startDate.setText(bundleAuctionStartDate);
        startTime.setText(bundleAuctionStartTime);
        endDate.setText(bundleAuctionEndDate);
        endTime.setText(bundleAuctionEndTime);
        //IntVehicleNo = 0;
        editNoOfVehicles.setText(String.valueOf(IntVehicleNo));

        editpencil = (ImageView) root.findViewById(R.id.editpencil);
        donecheck = (ImageView) root.findViewById(R.id.donecheck);

        btnbyteam = (Button) root.findViewById(R.id.btnbyteam);
        btnbyself = (Button) root.findViewById(R.id.btnbyself);
        btnbyadmin = (Button) root.findViewById(R.id.btnbyadmin);
        btnbyreauction = (Button) root.findViewById(R.id.btnbyreauction);
        buttonnext = (Button) root.findViewById(R.id.buttonnext);

        byteam_listview = (ListView) root.findViewById(R.id.byteam_listview);
        byself_listview = (ListView) root.findViewById(R.id.byself_listview);
        byadmin_listview = (ListView) root.findViewById(R.id.byadmin_listview);
        byreauction_listview = (ListView) root.findViewById(R.id.reauction_listview);
        selectAuctionsSpinner = (Spinner) root.findViewById(R.id.selectAuctionsSpinner);

        txtSheets = (TextView) root.findViewById(R.id.txtSheets);
        collapsingToolbar = (CollapsingToolbarLayout) root.findViewById(R.id.collapsing_toolbar);

        startDate.setOnTouchListener(this);
        startTime.setOnTouchListener(this);
        endDate.setOnTouchListener(this);
        endTime.setOnTouchListener(this);

        btnspecial_clauses.setOnClickListener(this);
        editpencil.setOnClickListener(this);
        donecheck.setOnClickListener(this);
        btnbyteam.setOnClickListener(this);
        btnbyself.setOnClickListener(this);
        btnbyadmin.setOnClickListener(this);
        btnbyreauction.setOnClickListener(this);
        txtSheets.setOnClickListener(this);
        buttonnext.setOnClickListener(this);


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getAllVehicles();
                    getClause();
                    getexcelFilesName();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        selectAuctionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            int count = 0;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                singleAuctionId = String.valueOf(auctionIds.get(position));
                if (count != 0) {
                    if (singleAuctionId.equals("0"))
                        singleAuctionId = "";
                    getReauctionedData(singleAuctionId);
                }
                count++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return root;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (v.getId()) {
            case (R.id.startdate):
                if (action == MotionEvent.ACTION_DOWN) {
                    //whichclick = "enddate";
                    startDate.setInputType(InputType.TYPE_NULL);
                    startDate.setError(null);
                    new SetMyDateAndTime("date", startDate, getActivity());
                }
                break;
            case (R.id.enddate):
                if (action == MotionEvent.ACTION_DOWN) {
                    //whichclick = "enddate";
                    endDate.setInputType(InputType.TYPE_NULL);
                    endDate.setError(null);
                    new SetMyDateAndTime("date", endDate, getActivity());
                }
                break;
            case (R.id.starttime):
                if (action == MotionEvent.ACTION_DOWN) {
                    //whichclick = "enddate";
                    startTime.setInputType(InputType.TYPE_NULL);
                    startTime.setError(null);
                    new SetMyDateAndTime("time", startTime, getActivity());
                }
                break;

            case (R.id.endtime):
                if (action == MotionEvent.ACTION_DOWN) {
                    //whichclick = "enddate";
                    endTime.setInputType(InputType.TYPE_NULL);
                    endTime.setError(null);
                    new SetMyDateAndTime("time", endTime, getActivity());
                }
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnspecial_clauses:
                String[] getIdsArray = new String[getClauses.size()];
                getIdsArray = getClauses.toArray(getIdsArray);
                specialClausesIDUpdate = "";
                specialClausesUpdate = "";
                final ArrayList seletedItems = new ArrayList();
                final ArrayList<String> selectedIds = new ArrayList<String>();
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Select Special clauses")
                        .setCancelable(true)
                        .setMultiChoiceItems(getIdsArray, bundlepositionArray, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                                if (isChecked) {
                                    selectedIds.add(getIds.get(indexSelected));
                                    seletedItems.add(indexSelected);
                                    bundlepositionArray[indexSelected] = true;
                                } else if (seletedItems.contains(indexSelected)) {
                                    selectedIds.remove(getIds.get(indexSelected));
                                    seletedItems.remove(Integer.valueOf(indexSelected));
                                    bundlepositionArray[indexSelected] = false;
                                }
                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getActivity(), "Selected clauses" + selectedIds, Toast.LENGTH_LONG).show();
                                for (int i = 0; i < bundlepositionArray.length; i++) {
                                    if (bundlepositionArray[i]) {
                                        if (specialClausesIDUpdate.equals("")) {
                                            specialClausesIDUpdate = getIds.get(i);
                                            specialClausesUpdate = getClauses.get(i);
                                        } else {
                                            specialClausesUpdate = specialClausesUpdate + "," + getClauses.get(i);
                                            specialClausesIDUpdate = specialClausesIDUpdate + "," + getIds.get(i);
                                        }
                                    }
                                }
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        }).create();
                dialog.show();

                break;

            case R.id.editpencil:
                auctionTitle.setEnabled(true);
                startDate.setEnabled(true);
                startTime.setEnabled(true);
                endDate.setEnabled(true);
                endTime.setEnabled(true);
                btnspecial_clauses.setEnabled(true);
                btnspecial_clauses.setVisibility(View.VISIBLE);
                editpencil.setVisibility(View.GONE);
                donecheck.setVisibility(View.VISIBLE);
                break;

            case R.id.btnbyteam:
                btnbyteam.setTextColor(getResources().getColor(R.color.orange));
                btnbyself.setTextColor(getResources().getColor(R.color.white));
                btnbyadmin.setTextColor(getResources().getColor(R.color.white));
                btnbyreauction.setTextColor(getResources().getColor(R.color.white));
                byteam_listview.setVisibility(View.VISIBLE);
                byself_listview.setVisibility(View.GONE);
                byadmin_listview.setVisibility(View.GONE);
                byreauction_listview.setVisibility(View.GONE);
                selectAuctionsSpinner.setVisibility(View.GONE);
                txtSheets.setVisibility(View.GONE);
                break;

            case R.id.btnbyself:
                btnbyteam.setTextColor(getResources().getColor(R.color.white));
                btnbyself.setTextColor(getResources().getColor(R.color.orange));
                btnbyadmin.setTextColor(getResources().getColor(R.color.white));
                btnbyreauction.setTextColor(getResources().getColor(R.color.white));
                byteam_listview.setVisibility(View.GONE);
                byself_listview.setVisibility(View.VISIBLE);
                byadmin_listview.setVisibility(View.GONE);
                byreauction_listview.setVisibility(View.GONE);
                selectAuctionsSpinner.setVisibility(View.GONE);
                txtSheets.setVisibility(View.GONE);
                break;

            case R.id.btnbyadmin:
                btnbyteam.setTextColor(getResources().getColor(R.color.white));
                btnbyself.setTextColor(getResources().getColor(R.color.white));
                btnbyadmin.setTextColor(getResources().getColor(R.color.orange));
                btnbyreauction.setTextColor(getResources().getColor(R.color.white));
                byteam_listview.setVisibility(View.GONE);
                byself_listview.setVisibility(View.GONE);
                byadmin_listview.setVisibility(View.VISIBLE);
                byreauction_listview.setVisibility(View.GONE);
                selectAuctionsSpinner.setVisibility(View.GONE);
                txtSheets.setVisibility(View.VISIBLE);
                break;

            case R.id.btnbyreauction:
                btnbyteam.setTextColor(getResources().getColor(R.color.white));
                btnbyself.setTextColor(getResources().getColor(R.color.white));
                btnbyadmin.setTextColor(getResources().getColor(R.color.white));
                btnbyreauction.setTextColor(getResources().getColor(R.color.orange));
                byteam_listview.setVisibility(View.GONE);
                byself_listview.setVisibility(View.GONE);
                byadmin_listview.setVisibility(View.GONE);
                byreauction_listview.setVisibility(View.VISIBLE);
                selectAuctionsSpinner.setVisibility(View.VISIBLE);
                txtSheets.setVisibility(View.GONE);
                break;

            case R.id.txtSheets:
                String sheetarr[] = sheet.split(",");
                alertBoxToSelectExcelSheet(sheetarr);
                break;

            case R.id.donecheck:


                //date comparision
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date now = new Date();
                String dateString = sdf.format(now);
                SimpleDateFormat tm = new SimpleDateFormat("hh:mm a");
                String time = tm.format(Calendar.getInstance().getTime());

                System.out.println("current date=" + dateString);
                System.out.println("current time=" + time);


                auctionTitleUpdate = auctionTitle.getText().toString();
                startDateUpdate = startDate.getText().toString();
                startTimeUpdate = startTime.getText().toString();
                endDateUpdate = endDate.getText().toString();
                endTimeUpdate = endTime.getText().toString();
                if (auctionTitleUpdate.equals("")) {
                    auctionTitle.setError("Enter auction title");
                    CustomToast.customToast(getActivity(), "Enter auction title");
//                    auctioname.setFocusable(true);
                } else if (startDateUpdate.equals("")) {
//                    startdate.setError("Enter start date");
                    CustomToast.customToast(getActivity(), "Enter start date");
                } else if (startTimeUpdate.equals("")) {
//                    starttime.setError("Enter start time");
                    CustomToast.customToast(getActivity(), "Enter start time");
                } else if (startDateUpdate.equals(dateString) && !genericFunctions.startTimeEndTimeValidation(time, startTimeUpdate)) {
                    CustomToast.customToast(getActivity(),  "time is invalid");

                } else if (endDateUpdate.equals("")) {
                    CustomToast.customToast(getActivity(), "Enter end date");
//                    enddate.setError("Enter end date");
                } else if (endTimeUpdate.equals("")) {
                    CustomToast.customToast(getActivity(),"Enter end time");
//                    endtime.setError("Enter end time");
                } else if (!genericFunctions.startDateValidatioon(startDateUpdate)) {
                    startDate.setError("Enter valid Date");
                } else if (!genericFunctions.startDateEndDateValidation(endDateUpdate, startDateUpdate)) {
                    endDate.setError("Enter valid Date");
                } else if (specialClausesIDUpdate.equals(""))
                    Toast.makeText(getActivity(), "Please select atleast one clause", Toast.LENGTH_LONG).show();
                else if (startDateUpdate.equals(endDateUpdate)) {
                    if (!genericFunctions.startTimeEndTimeValidation(startTimeUpdate, endTimeUpdate)) {
                        endTime.setError("Enter valid time");
                    } else {
                        //new UpdateAuctionCreation().execute();
                        UpdateAuctionData();
                    }
                } else {
                    //new UpdateAuctionCreation().execute();
                    UpdateAuctionData();
                }

                break;

            case R.id.buttonnext:

                List<AuctionAllVehicleData> finalVehiclesData = new ArrayList<>();
                List<AuctionAllVehicleData> byselfVehiclesData = new ArrayList<>();
                List<AuctionAllVehicleData> reauctionVehiclesData = new ArrayList<>();
                List<AuctionAllVehicleData> adminVehiclesData = new ArrayList<>();
                byselfVehiclesData = selfadapter.checkboxVehicleData();
                reauctionVehiclesData = reauctionadapter.checkboxVehicleData();
                adminVehiclesData = adminadapter.checkboxVehicleData();
                for (int i = 0; i < byselfVehiclesData.size(); i++) {
                    finalVehiclesData.add(byselfVehiclesData.get(i));
                }
                for (int i = 0; i < reauctionVehiclesData.size(); i++) {
                    finalVehiclesData.add(reauctionVehiclesData.get(i));
                }
                for (int i = 0; i < adminVehiclesData.size(); i++) {
                    finalVehiclesData.add(adminVehiclesData.get(i));
                }

                if (finalVehiclesData.size() == 0)
                    CustomToast.customToast(getActivity(),"Please select vehicle(s)");
                else {
                    CustomToast.customToast(getActivity(),"Please confirm vehicles that you selected now");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("finalVehiclesData", (Serializable) finalVehiclesData);
                    bundle.putString("auction_id", auction_id);
                    bundle.putString("title", auctionTitle.getText().toString());
                    bundle.putString("startdate", startDate.getText().toString());
                    bundle.putString("starttime", startTime.getText().toString());
                    bundle.putString("enddate", endDate.getText().toString());
                    bundle.putString("endtime", endTime.getText().toString());
                    bundle.putString("vehiclecount", editNoOfVehicles.getText().toString());
                    bundle.putString("specialClauses", specialClausesUpdate);

                    if (className.equals("SavedAuction")) {
                        CreateAuctionConfirmFragment frag = new CreateAuctionConfirmFragment();
                        frag.setArguments(bundle);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.saved_auctionFrame, frag);
                        fragmentTransaction.addToBackStack("AuctionCreateConfirm");
                        fragmentTransaction.commit();
                    } else {
                        CreateAuctionConfirmFragment frag = new CreateAuctionConfirmFragment();
                        frag.setArguments(bundle);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.createEventFrame, frag);
                        fragmentTransaction.addToBackStack("AuctionCreateConfirm");
                        fragmentTransaction.commit();
                    }
                }
                break;

        }
    }

    private void getexcelFilesName() {
        mApiCall.ExcelSheetName(contactnumber);
    }

    //alert box to get Excel sheet names
    public void alertBoxToSelectExcelSheet(final String[] choices) {
        final ArrayList<String> mSelectedItems = new ArrayList<>();
        mSelectedItems.clear();
        ExcelsheetName = "";
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        // set the dialog title
        builder.setTitle("Choose Excel sheet name from following")
                .setCancelable(true)
                .setMultiChoiceItems(choices, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            mSelectedItems.add(choices[which]);
                        } else if (mSelectedItems.contains(choices[which])) {
                            mSelectedItems.remove(choices[which]);
                        }
                    }

                })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        for (int i = 0; i < mSelectedItems.size(); i++) {
                            if (ExcelsheetName.equals("")) {
                                ExcelsheetName = mSelectedItems.get(i);
                            } else
                                ExcelsheetName = ExcelsheetName + "," + mSelectedItems.get(i);
                        }

                        ExcelsheetName = ExcelsheetName.replaceAll(" ", "");
                        if (!ExcelsheetName.equals("")) {
                            getVehiclesByExcelSheet(ExcelsheetName);
                            dialog.dismiss();
                            //Toast.makeText(getActivity(), "Web Service call to get vehicles", Toast.LENGTH_SHORT).show();
                        } else {
                            CustomToast.customToast(getActivity(),"Please select checkbox");
                        }

                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })

                .show();

    }


    private void getVehiclesByExcelSheet(String ExcelSheetName) {
        mApiCall.AdminVehicles(contactnumber, ExcelSheetName, UserId);
    }

    private void getAllVehicles() {
        mApiCall.AllAuctionVehicle(contactnumber);
    }


    private void UpdateAuctionData() {
        mApiCall.UpdateAuction(auction_id, auctionTitleUpdate, startDateUpdate, startTimeUpdate, endDateUpdate,
                endTimeUpdate, specialClausesIDUpdate, "", "", "", "");
    }


    private void getReauctionedData(final String singleAuctionId) {
        mApiCall.ReauctionedVehicles(contactnumber, singleAuctionId);
    }

    private void getClause() {
        mApiCall.getSpecialClauses("getClause");
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                /*
                        AuctionAllVehicleData to get All vehicles data
                 */
                if (response.body() instanceof AuctionAllVehicleResponse) {
                    ArrayList<AuctionAllVehicleData> uploadedVehicleData = new ArrayList<>();
                    ArrayList<AuctionAllVehicleData> reauctionAuctionAllVehicleData = new ArrayList<>();
                    ArrayList<AuctionAllVehicleData> adminVehicleData = new ArrayList<>();
                    AuctionAllVehicleResponse auctionAllVehicleResponse = (AuctionAllVehicleResponse) response.body();
                        /*
                        Uploaded vehicle data
                         */
                    if (!auctionAllVehicleResponse.getSuccess().getUploadedVehicles().isEmpty()) {
                        uploadedVehicleData.clear();
                        for (AuctionAllVehicleResponse.Success.UploadedVehicle uploadSuccess : auctionAllVehicleResponse.getSuccess().getUploadedVehicles()) {
                            AuctionAllVehicleData auctionAllVehicleData = new AuctionAllVehicleData();
                            auctionAllVehicleData.setVehicleId(uploadSuccess.getVehicleId());
                            auctionAllVehicleData.setVehicleTitle(uploadSuccess.getTitle());
                            auctionAllVehicleData.setVehicleCategory(uploadSuccess.getCategory());
                            auctionAllVehicleData.setVehicleBrand(uploadSuccess.getBrand());
                            auctionAllVehicleData.setVehicleModel(uploadSuccess.getModel());
                            auctionAllVehicleData.setVehicleVersion(uploadSuccess.getVersion());
                            auctionAllVehicleData.setVehicleMfgYear(uploadSuccess.getYear());
                            auctionAllVehicleData.setVehicleLocation_city(uploadSuccess.getLocationCity());
                            auctionAllVehicleData.setVehicleRTOCity(uploadSuccess.getRtoCity());
                            auctionAllVehicleData.setVehicleColor(uploadSuccess.getColor());
                            //auctionAllVehicleData.setVehicleImages(uploadSuccess.getImage());
                            auctionAllVehicleData.setVehicleRegistrationNo(uploadSuccess.getRegistrationNumber());
                            auctionAllVehicleData.setVehicleStartPrice(uploadSuccess.getStartPrice());
                            auctionAllVehicleData.setVehicleReservedPrice(uploadSuccess.getReservedPrice());

                            String img = uploadSuccess.getImage();
                            if (img.contains(",")) {
                                String arr[] = img.split(",", 2);
                                auctionAllVehicleData.setVehicleSingleImage(arr[0]);
                                String all = img.replace(",", "/ ");
                                auctionAllVehicleData.setVehicleImages(all);
                            } else {
                                auctionAllVehicleData.setVehicleSingleImage(img);
                                auctionAllVehicleData.setVehicleImages(img);
                            }
                            uploadedVehicleData.add(auctionAllVehicleData);
                        }
                    }
                        /*
                            Reauctioned vehicle data
                         */
                    if (!auctionAllVehicleResponse.getSuccess().getReauctionVehicles().isEmpty()) {
                        reauctionAuctionAllVehicleData.clear();
                        for (AuctionAllVehicleResponse.Success.ReauctionVehicle reauctionSuccess : auctionAllVehicleResponse.getSuccess().getReauctionVehicles()) {
                            AuctionAllVehicleData auctionAllVehicleData = new AuctionAllVehicleData();
                            auctionAllVehicleData.setVehicleId(reauctionSuccess.getVehicleId());
                            auctionAllVehicleData.setVehicleTitle(reauctionSuccess.getTitle());
                            auctionAllVehicleData.setVehicleCategory(reauctionSuccess.getCategory());
                            auctionAllVehicleData.setVehicleBrand(reauctionSuccess.getBrand());
                            auctionAllVehicleData.setVehicleModel(reauctionSuccess.getModel());
                            auctionAllVehicleData.setVehicleVersion(reauctionSuccess.getVersion());
                            auctionAllVehicleData.setVehicleMfgYear(reauctionSuccess.getYear());
                            auctionAllVehicleData.setVehicleLocation_city(reauctionSuccess.getLocationCity());
                            auctionAllVehicleData.setVehicleRTOCity(reauctionSuccess.getRtoCity());
                            auctionAllVehicleData.setVehicleColor(reauctionSuccess.getColor());
                            // auctionAllVehicleData.setVehicleImages(reauctionSuccess.getImage());
                            auctionAllVehicleData.setVehicleRegistrationNo(reauctionSuccess.getRegistrationNumber());
                            auctionAllVehicleData.setVehicleStartPrice(reauctionSuccess.getStartPrice());
                            auctionAllVehicleData.setVehicleReservedPrice(reauctionSuccess.getReservedPrice());

                            String img = reauctionSuccess.getImage();
                            if (img.contains(",")) {
                                String arr[] = img.split(",", 2);
                                auctionAllVehicleData.setVehicleSingleImage(arr[0]);
                                String all = img.replace(",", "/ ");
                                auctionAllVehicleData.setVehicleImages(all);
                            } else {
                                auctionAllVehicleData.setVehicleSingleImage(img);
                                auctionAllVehicleData.setVehicleImages(img);
                            }
                            reauctionAuctionAllVehicleData.add(auctionAllVehicleData);
                        }
                    }
                        /*
                            Admin vehicle data
                         */
                    if (!auctionAllVehicleResponse.getSuccess().getAdminVehicles().isEmpty()) {
                        adminVehicleData.clear();
                        for (AuctionAllVehicleResponse.Success.AdminVehicle adminSuccess : auctionAllVehicleResponse.getSuccess().getAdminVehicles()) {
                            AuctionAllVehicleData auctionAllVehicleData = new AuctionAllVehicleData();
                            auctionAllVehicleData.setVehicleId("A " + adminSuccess.getVehicleId());
                            auctionAllVehicleData.setVehicleContact(adminSuccess.getContactNo());
                            auctionAllVehicleData.setVehicleLotNo(adminSuccess.getLotNo());
                            auctionAllVehicleData.setVehicleRepodate(adminSuccess.getRepoDate());
                            auctionAllVehicleData.setVehicleRegistrationNo(adminSuccess.getRegistrationYear());
                            auctionAllVehicleData.setVehicleTitle("NA");
                            auctionAllVehicleData.setVehicleCategory("NA");
                            auctionAllVehicleData.setVehicleBrand(adminSuccess.getBrand());
                            auctionAllVehicleData.setVehicleModel(adminSuccess.getModel());
                            auctionAllVehicleData.setVehicleVersion(adminSuccess.getVersion());
                            auctionAllVehicleData.setVehicleMfgYear(adminSuccess.getYear());
                            auctionAllVehicleData.setVehicleLocation_city(adminSuccess.getLocationCity());
                            auctionAllVehicleData.setVehicleRTOCity(adminSuccess.getRtoCity());
                            auctionAllVehicleData.setVehicleColor(adminSuccess.getColor());
                            //auctionAllVehicleData.setVehicleImages(adminSuccess.getImage());
                            auctionAllVehicleData.setVehicleRegistrationNo(adminSuccess.getRegistrationNumber());
                            auctionAllVehicleData.setVehicleStartPrice(adminSuccess.getStartPrice());
                            auctionAllVehicleData.setVehicleReservedPrice(adminSuccess.getReservedPrice());

                            String img = adminSuccess.getImage();
                            if (img.contains(",")) {
                                String arr[] = img.split(",", 2);
                                auctionAllVehicleData.setVehicleSingleImage(arr[0]);
                                String all = img.replace(",", "/ ");
                                auctionAllVehicleData.setVehicleImages(all);
                            } else {
                                auctionAllVehicleData.setVehicleSingleImage(img);
                                auctionAllVehicleData.setVehicleImages(img);
                            }
                            adminVehicleData.add(auctionAllVehicleData);
                        }
                    }
                    if (!auctionAllVehicleResponse.getSuccess().getAuctions().isEmpty()) {
                        auctionIds.add(0);
                        auctionTitles.add("Select auction here");
                        for (AuctionAllVehicleResponse.Success.Auction auctionSuccess : auctionAllVehicleResponse.getSuccess().getAuctions()) {
                            auctionIds.add(Integer.valueOf(auctionSuccess.getAuctionId()));
                            auctionTitles.add(auctionSuccess.getActionTitle() + " " + "Vehicles:" + auctionSuccess.getReauctionvehiCount());

                        }
                    }

                    selfadapter = new AuctionBySelfVehiclesAdapter(getActivity(), uploadedVehicleData);
                    byself_listview.setAdapter(selfadapter);
                    btnbyself.setText("By self(" + String.valueOf(uploadedVehicleData.size()) + ")");

                    reauctionadapter = new AuctionReauctionVehiclesAdapter(getActivity(), reauctionAuctionAllVehicleData);
                    byreauction_listview.setAdapter(reauctionadapter);
                    btnbyreauction.setText("Reauction(" + String.valueOf(reauctionAuctionAllVehicleData.size()) + ")");

                    adminadapter = new AuctionAdminVehiclesAdapter(getActivity(), adminVehicleData);
                    byadmin_listview.setAdapter(adminadapter);
                    btnbyadmin.setText("By Admin(" + String.valueOf(adminVehicleData.size()) + ")");

                    if (adminVehicleData.size() > 0) {
                        //To set underlined string to textview
                        SpannableString spanString = new SpannableString("Click to select Excel Sheet");
                        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
                        txtSheets.setText(spanString);
                    }

                    if (getActivity() != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_list_item_1, auctionTitles);
                        selectAuctionsSpinner.setAdapter(adapter);
                    }
                }


                /*
                        AuctionAllVehicleData to get AdminExcel Sheet
                 */
                else if (response.body() instanceof AdminExcelSheetResponse) {
                    AdminExcelSheetResponse adminResponse = (AdminExcelSheetResponse) response.body();
                    sheet = "";
                    UserId = "";
                    if (!adminResponse.getSuccess().isEmpty()) {
                        for (AdminExcelSheetResponse.Success success : adminResponse.getSuccess()) {
                            sheet = success.getExcelSheetName();
                            UserId = success.getUserId();
                        }

                    } else
                        CustomToast.customToast(getActivity(), getString(R.string.no_response));
                }


                /*
                        AuctionAllVehicleData to get AdminVehicles data
                 */
                else if (response.body() instanceof AdminVehiclesResponse) {
                    AdminVehiclesResponse adminResponse = (AdminVehiclesResponse) response.body();
                    ArrayList<AuctionAllVehicleData> adminData = new ArrayList<>();
                    if (!adminResponse.getSuccess().isEmpty()) {
                        adminData.clear();
                        for (AdminVehiclesResponse.Success success : adminResponse.getSuccess()) {
                            AuctionAllVehicleData auctionAllVehicleData = new AuctionAllVehicleData();
                            auctionAllVehicleData.setVehicleId("A " + success.getId());
                            auctionAllVehicleData.setVehicleContact(success.getContactNo());
                            auctionAllVehicleData.setVehicleLotNo(success.getLotNo());
                            auctionAllVehicleData.setVehicleRegistrationNo(success.getRegistrationNumber());
                            auctionAllVehicleData.setVehicleRegYear(success.getRegistrationYear());
                            auctionAllVehicleData.setVehicleMfgYear(success.getYom());
                            auctionAllVehicleData.setVehicleBrand(success.getManufacturer());
                            auctionAllVehicleData.setVehicleModel(success.getProduct());
                            auctionAllVehicleData.setVehicleLocation_city(success.getYardName() + " " + success.getParkedAt());
                            auctionAllVehicleData.setVehicleColor(success.getColor());
                            auctionAllVehicleData.setVehicleRepodate(success.getRepoDate());
                            auctionAllVehicleData.setVehicleYardRent(success.getYardRentPerDay());
                            //auctionAllVehicleData.setUploadedImages(success.getUploadedImages());
                            auctionAllVehicleData.setVehicleStartPrice(success.getStartPrice());
                            auctionAllVehicleData.setVehicleReservedPrice(success.getReservedPrice());
                            auctionAllVehicleData.setVehicleTitle("NA");
                            auctionAllVehicleData.setVehicleCategory("NA");

                            String img = success.getUploadedImages();
                            if (img.contains(",")) {
                                String arr[] = img.split(",", 2);
                                auctionAllVehicleData.setVehicleSingleImage(arr[0]);
                                String all = img.replace(",", "/ ");
                                auctionAllVehicleData.setVehicleImages(all);
                            } else {
                                auctionAllVehicleData.setVehicleSingleImage(img);
                                auctionAllVehicleData.setVehicleImages(img);
                            }

                            adminData.add(auctionAllVehicleData);
                        }

                        adminadapter = new AuctionAdminVehiclesAdapter(getActivity(), adminData);
                        byadmin_listview.setAdapter(adminadapter);
                        btnbyadmin.setText("By admin(" + String.valueOf(adminData.size()) + ")");
                    } else
                        CustomToast.customToast(getActivity(), getString(R.string.no_response));
                }

                /*
                        AuctionAllVehicleData to get Reauction Data
                 */
                else if (response.body() instanceof AuctionReauctionVehicleResponse) {
                    AuctionReauctionVehicleResponse reauctionResponse = (AuctionReauctionVehicleResponse) response.body();
                    ArrayList<AuctionAllVehicleData> reaucionData = new ArrayList<>();
                    if (!reauctionResponse.getSuccess().isEmpty()) {
                        reaucionData.clear();
                        for (AuctionReauctionVehicleResponse.Success reauctionSuccess : reauctionResponse.getSuccess()) {
                            AuctionAllVehicleData auctionAllVehicleData = new AuctionAllVehicleData();
                            auctionAllVehicleData.setVehicleId(reauctionSuccess.getVehicleId());
                            auctionAllVehicleData.setVehicleTitle(reauctionSuccess.getTitle());
                            auctionAllVehicleData.setVehicleCategory(reauctionSuccess.getCategory());
                            auctionAllVehicleData.setVehicleBrand(reauctionSuccess.getBrand());
                            auctionAllVehicleData.setVehicleModel(reauctionSuccess.getModel());
                            auctionAllVehicleData.setVehicleVersion(reauctionSuccess.getVersion());
                            auctionAllVehicleData.setVehicleMfgYear(reauctionSuccess.getYear());
                            auctionAllVehicleData.setVehicleLocation_city(reauctionSuccess.getLocationCity());
                            auctionAllVehicleData.setVehicleRTOCity(reauctionSuccess.getRtoCity());
                            auctionAllVehicleData.setVehicleColor(reauctionSuccess.getColor());
                            //auctionAllVehicleData.setVehicleImages(reauctionSuccess.getImage());
                            auctionAllVehicleData.setVehicleRegistrationNo(reauctionSuccess.getRegistrationNumber());
                            auctionAllVehicleData.setVehicleStartPrice("");
                            auctionAllVehicleData.setVehicleReservedPrice("");

                            String img = reauctionSuccess.getImage();
                            if (img.contains(",")) {
                                String arr[] = img.split(",", 2);
                                auctionAllVehicleData.setVehicleSingleImage(arr[0]);
                                String all = img.replace(",", "/ ");
                                auctionAllVehicleData.setVehicleImages(all);

                            } else {
                                auctionAllVehicleData.setVehicleSingleImage(img);
                                auctionAllVehicleData.setVehicleImages(img);
                            }

                            reaucionData.add(auctionAllVehicleData);
                        }
                        reauctionadapter = new AuctionReauctionVehiclesAdapter(getActivity(), reaucionData);
                        byreauction_listview.setAdapter(reauctionadapter);
                        btnbyreauction.setText("Reauction(" + String.valueOf(reaucionData.size()) + ")");

                    } else
                        CustomToast.customToast(getActivity(), getString(R.string.no_response));
                }

                /*
                        AuctionAllVehicleData to get Special Clauses
                 */
                else if (response.body() instanceof SpecialClauseGetResponse) {
                    SpecialClauseGetResponse moduleResponse = (SpecialClauseGetResponse) response.body();
                    if (!moduleResponse.getSuccess().isEmpty()) {
                        getIds.clear();
                        getClauses.clear();
                        for (SpecialClauseGetResponse.Success message : moduleResponse.getSuccess()) {
                            getIds.add(message.getClauseId());
                            getClauses.add(message.getClause());
                        }
                    }
                }

            } else
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
            Log.i("Check Class-", "Add vehicles for Auction Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

        if (str != null) {
            if (str.startsWith("Success")) {
                CustomToast.customToast(getActivity(),  "Update Successfull");
                auctionTitle.setEnabled(false);
                startDate.setEnabled(false);
                startTime.setEnabled(false);
                endDate.setEnabled(false);
                endTime.setEnabled(false);
                btnspecial_clauses.setEnabled(false);
                btnspecial_clauses.setVisibility(View.GONE);
                editpencil.setVisibility(View.VISIBLE);
                donecheck.setVisibility(View.GONE);
                //Update data in SqLite
                /*sqlite_obj.open();
                SQlitewallDB.DatabaseHelper obg = new SQlitewallDB.DatabaseHelper(getActivity());

                obg.updateAuction(auction_id, auctionTitleUpdate, startDateUpdate, startTimeUpdate, endDateUpdate, endTimeUpdate, specialClausesIDUpdate, "0");

                sqlite_obj.close();*/
            }
        } else
            CustomToast.customToast(getActivity(), getString(R.string.no_response));

    }
}
