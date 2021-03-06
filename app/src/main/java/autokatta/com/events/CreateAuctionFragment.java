package autokatta.com.events;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import autokatta.com.R;
import autokatta.com.Registration.Multispinner;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.GenericFunctions;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.AuctionCreateResponse;
import autokatta.com.response.GetStatesResponse;
import autokatta.com.response.SpecialClauseAddResponse;
import autokatta.com.response.SpecialClauseGetResponse;
import autokatta.com.view.MySavedAuctionEventActivity;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 23/3/17.
 */

public class CreateAuctionFragment extends Fragment implements View.OnClickListener, RequestNotifier, View.OnTouchListener, RadioGroup.OnCheckedChangeListener, Multispinner.MultiSpinnerListener {

    EditText auctioname, startdate, starttime, enddate, endtime;
    AutoCompleteTextView address;
    RadioGroup rgauctiontype;
    RadioButton physical, online, banquet;
    Button addmore, create, cancel;
    String myContact;
    SpecialCluasesAdapter adapter;
    List positionArrayList = new ArrayList<>();
    String recieve = null;
    List<Integer> checkedids = new ArrayList<>();
    List<String> checkedspecialclauses = new ArrayList<>();
    View createAuctionView;
    ListView clauseList;
    Spinner auctionCategorySpinner;
    Multispinner stockLocationSpinner;
    ApiCall apiCall;
    String Radiobtn_click = "";
    GenericFunctions validObj;
    String ids = "", cluases = "", name, stdate, sttime, eddate, edtime, type, location, auctionCategory, stockLocation;
    private ConnectionDetector mConnectionDetector;
    HashMap<String, Integer> mStatelist1 = new HashMap<>();
    List<String> stateLst = new ArrayList<>();

    public CreateAuctionFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createAuctionView = inflater.inflate(R.layout.fragment_create_auction, container, false);
        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
        mConnectionDetector = new ConnectionDetector(getActivity());

        auctioname = (EditText) createAuctionView.findViewById(R.id.editauctionname);
        startdate = (EditText) createAuctionView.findViewById(R.id.auctionstartdate);
        starttime = (EditText) createAuctionView.findViewById(R.id.auctionstarttime);
        enddate = (EditText) createAuctionView.findViewById(R.id.auctionenddate);
        endtime = (EditText) createAuctionView.findViewById(R.id.auctionendtime);
        rgauctiontype = (RadioGroup) createAuctionView.findViewById(R.id.radiogroup);
        physical = (RadioButton) createAuctionView.findViewById(R.id.physical);
        online = (RadioButton) createAuctionView.findViewById(R.id.online);
        banquet = (RadioButton) createAuctionView.findViewById(R.id.banquet);
        address = (AutoCompleteTextView) createAuctionView.findViewById(R.id.address);
        addmore = (Button) createAuctionView.findViewById(R.id.btnaddmore);
        create = (Button) createAuctionView.findViewById(R.id.btncreate);
        clauseList = (ListView) createAuctionView.findViewById(R.id.list_view);
        auctionCategorySpinner = (Spinner) createAuctionView.findViewById(R.id.auctionCategory);
        stockLocationSpinner = (Multispinner) createAuctionView.findViewById(R.id.stockLocation);

        apiCall = new ApiCall(getActivity(), this);
        validObj = new GenericFunctions();

        auctioname.requestFocus();
        rgauctiontype.setOnCheckedChangeListener(this);
        startdate.setOnTouchListener(this);
        starttime.setOnTouchListener(this);
        enddate.setOnTouchListener(this);
        endtime.setOnTouchListener(this);
        create.setOnClickListener(this);
        addmore.setOnClickListener(this);
        clauseList.setOnTouchListener(this);

        address.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.simple));
        apiCall.getSpecialClauses("getClause", "");
        apiCall.getStates();

        return createAuctionView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case (R.id.btnaddmore):

                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Add Clauses");
                alertDialog.setMessage("Enter Clause");

                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                recieve = input.getText().toString();
                                if (recieve.equals("") || recieve.startsWith(" ") && recieve.endsWith(" ")) {
                                    if (isAdded())
                                        CustomToast.customToast(getActivity(), "Please enter clause");
                                } else {
                                    apiCall.addSpecialClauses("setClause", recieve);
                                }


                            }
                        });
                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();

                break;

            case (R.id.btncreate):
                if (!mConnectionDetector.isConnectedToInternet()) {
                    if (isAdded())
                        CustomToast.customToast(getActivity(), getString(R.string.no_internet));

                } else {
                    Boolean flag = false;

                    name = auctioname.getText().toString();
                    stdate = startdate.getText().toString();
                    sttime = starttime.getText().toString();
                    eddate = enddate.getText().toString();
                    edtime = endtime.getText().toString();
                    location = address.getText().toString();


                    //current date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date now = new Date();
                    String dateString = sdf.format(now);
                    SimpleDateFormat tm = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                    String time = tm.format(Calendar.getInstance().getTime());

                    auctionCategory = auctionCategorySpinner.getSelectedItem().toString();
                    stockLocation = stockLocationSpinner.getSelectedItem().toString();

                    if (!location.isEmpty()) {

                        List<String> resultList = GooglePlacesAdapter.getResultList();
                        for (int i = 0; i < resultList.size(); i++) {

                            if (location.equalsIgnoreCase(resultList.get(i))) {
                                flag = true;
                                break;

                            } else {

                                flag = false;
                            }
                        }
                    }

                    type = ((RadioButton) createAuctionView.findViewById(rgauctiontype.getCheckedRadioButtonId())).getText().toString();

                    if (name.equals("") || name.startsWith(" ") && name.endsWith(" ")) {
                        auctioname.setError("Enter auction title");
                        auctioname.requestFocus();

                    } else if (stdate.equals("")) {
                        startdate.requestFocus();
                        startdate.setError("Enter start date");

                    } else if (sttime.equals("")) {
                        starttime.requestFocus();
                        starttime.setError("Enter start time");

                    } else if (stdate.equals(dateString) && !validObj.startTimeEndTimeValidation(time, sttime)) {
                        starttime.setError("time is invalid");
                        starttime.requestFocus();

                    } else if (eddate.equals("")) {
                        enddate.requestFocus();
                        enddate.setError("Enter end date");

                    } else if (edtime.equals("")) {
                        endtime.requestFocus();
                        endtime.setError("Enter end time");

                    } else if (!validObj.startDateValidatioon(stdate)) {
                        startdate.setError("Enter valid Date");
                        startdate.requestFocus();

                    } else if (!validObj.startDateEndDateValidation(eddate, stdate)) {
                        enddate.requestFocus();
                        enddate.setError("Enter valid Date");

                    } else if (stdate.equals(eddate) && !validObj.startTimeEndTimeValidation(sttime, edtime)) {
                        endtime.setError("End time should be greater than start time");
                        endtime.requestFocus();
                    } else if (address.getVisibility() == View.VISIBLE && location.isEmpty()) {
                        address.setError("Enter Location");
                        address.requestFocus();

                    } else if (address.getVisibility() == View.VISIBLE && !flag) {
                        address.setError("Please Select Location From Dropdown Only");
                        address.requestFocus();

                    } else if (auctionCategory.equalsIgnoreCase("-Select Stock Type-")) {
                        if (isAdded())
                            CustomToast.customToast(getActivity(), "Please select stock type for auction");
                        auctionCategorySpinner.requestFocus();
                    } else if (stockLocation.equalsIgnoreCase("-Select State-") || stockLocation.equals("")) {
                        if (isAdded())
                            CustomToast.customToast(getActivity(), "Please select states ");
                        stockLocationSpinner.requestFocus();

                    } else {

                        checkedids = adapter.checkedids();
                        checkedspecialclauses = adapter.checkedspecialclauses();
                        positionArrayList = adapter.positionArray();

                        for (int i = 0; i < checkedids.size(); i++) {
                            int idint = checkedids.get(i);
                            if (idint != 0) {
                                if (ids.equals(""))
                                    ids = String.valueOf(idint);
                                else
                                    ids = ids + "," + String.valueOf(idint);
                            }
                        }

                        for (int i = 0; i < checkedspecialclauses.size(); i++) {
                            if (!checkedspecialclauses.get(i).equals("0")) {
                                if (cluases.equals(""))
                                    cluases = checkedspecialclauses.get(i);
                                else
                                    cluases = cluases + "," + checkedspecialclauses.get(i);
                            }
                        }

                        System.out.println(checkedids + "positionArray " + positionArrayList.size());

                        if (ids.equals("")) {
                            if (isAdded())
                                CustomToast.customToast(getActivity(), "Please select atleast single clause");
                        } else {
                            final Dialog dialog = new Dialog(getActivity());
                            dialog.setTitle("Auction");
                            dialog.setContentView(R.layout.dailogbox);

                            final RadioGroup radiogroup = (RadioGroup) dialog.findViewById(R.id.radiogroup);
                            Button okbtn = (Button) dialog.findViewById(R.id.okbtn);
                            Button canclebtn = (Button) dialog.findViewById(R.id.canclebtn);

                            okbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Radiobtn_click = ((RadioButton) dialog.findViewById(radiogroup.getCheckedRadioButtonId())).getText().toString();
                                    dialog.dismiss();

                                    apiCall.createAuction(name, stdate, sttime, eddate, edtime, type, myContact, location, auctionCategory, ids,
                                            Radiobtn_click, stockLocation);
                                }
                            });

                            canclebtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    }
                }
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        switch (view.getId()) {

            case (R.id.auctionstartdate):

                if (action == MotionEvent.ACTION_DOWN) {
                    startdate.setInputType(InputType.TYPE_NULL);
                    startdate.setError(null);
                    new SetMyDateAndTime("date", startdate, getActivity());
                }
                break;
            case (R.id.auctionstarttime):
                if (action == MotionEvent.ACTION_DOWN) {
                    starttime.setInputType(InputType.TYPE_NULL);
                    starttime.setError(null);
                    new SetMyDateAndTime("time", starttime, getActivity());
                }
                break;
            case (R.id.auctionenddate):

                if (action == MotionEvent.ACTION_DOWN) {
                    enddate.setInputType(InputType.TYPE_NULL);
                    enddate.setError(null);
                    new SetMyDateAndTime("date", enddate, getActivity());
                }

                break;
            case (R.id.auctionendtime):

                if (action == MotionEvent.ACTION_DOWN) {
                    endtime.setInputType(InputType.TYPE_NULL);
                    endtime.setError(null);
                    new SetMyDateAndTime("time", endtime, getActivity());
                }
                break;

            case (R.id.list_view):
                view.getParent().requestDisallowInterceptTouchEvent(true);
                break;

        }
        return false;
    }

    @Override
    public void notifySuccess(final Response<?> response) {

        if (response != null) {
            if (response.isSuccessful()) {
                /*
                        AuctionAllVehicleData to get category
                 */
                if (response.body() instanceof SpecialClauseGetResponse) {
                    SpecialClauseGetResponse moduleResponse = (SpecialClauseGetResponse) response.body();
                    final ArrayList<String> id = new ArrayList<>();
                    final ArrayList<String> clause = new ArrayList<>();

                    if (!moduleResponse.getSuccess().isEmpty()) {

                        for (SpecialClauseGetResponse.Success message : moduleResponse.getSuccess()) {
                            id.add(message.getClauseId());
                            clause.add(message.getClause());

                        }
                        adapter = new SpecialCluasesAdapter(getActivity(), id, clause);
                        clauseList.setAdapter(adapter);
                    }
                } else if (response.body() instanceof SpecialClauseAddResponse) {

                    SpecialClauseAddResponse moduleResponse = (SpecialClauseAddResponse) response.body();
                    if (moduleResponse.getSuccess() != null) {

                        String id = moduleResponse.getSuccess().getClauseID().toString();
                        Log.i("ClauseId", "->" + id);
                        if (isAdded())
                            CustomToast.customToast(getActivity(), "Clause Added Successfully");
                        apiCall.getSpecialClauses("getClause", "");
                    }

                } else if (response.body() instanceof AuctionCreateResponse) {
                    AuctionCreateResponse createResponse = (AuctionCreateResponse) response.body();
                    if (createResponse.getSuccess() != null) {
                        final int Aucid = createResponse.getSuccess().getAuctionID();
                        Log.i("AuctId", "->" + Aucid);
                        if (isAdded())
                            CustomToast.customToast(getActivity(), "Auction Created Successfully");

                        if (Aucid != 0) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    getActivity());

                            alertDialogBuilder
                                    .setMessage("Do you want to upload vehicle(s)?")
                                    .setCancelable(false)
                                    .setPositiveButton("Now", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            Bundle b = new Bundle();

                                            b.putInt("auction_id", Aucid);
                                            b.putString("title", name);
                                            b.putString("startdate", stdate);
                                            b.putString("starttime", sttime);
                                            b.putString("enddate", eddate);
                                            b.putString("endtime", edtime);
                                            b.putString("cluase", cluases);
                                            b.putString("ids", ids);
                                            b.putString("cluases", cluases);
                                            b.putString("className", "CreateAuction");
                                            b.putString("category", auctionCategory);
                                            b.putString("location", stockLocation);
                                            b.putString("location", location);
                                            b.putSerializable("positionArray", (Serializable) positionArrayList);
                                            b.putString("noofvehicles", String.valueOf(0));

                                            AddVehiclesForAuctionFragment frag = new AddVehiclesForAuctionFragment();

                                            frag.setArguments(b);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.createEventFrame, frag);
                                            fragmentTransaction.addToBackStack("AddVehiclesForAuction");
                                            fragmentTransaction.commit();

                                        }
                                    })
                                    .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            if (isAdded())
                                                CustomToast.customToast(getActivity(), "Auction saved in my saved event");
                                            Intent intent = new Intent(getActivity(), MySavedAuctionEventActivity.class);
                                            getActivity().startActivity(intent);
                                            getActivity().finish();

                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            alertDialog.show();
                        }


                    }

                } else if (response.body() instanceof GetStatesResponse) {
                    stateLst.clear();
                    GetStatesResponse mGetState = (GetStatesResponse) response.body();
                    if (!mGetState.getSuccess().isEmpty()) {
                        for (GetStatesResponse.Success StateResponse : mGetState.getSuccess()) {
                            StateResponse.setStateId(StateResponse.getStateId());
                            StateResponse.setStateName(StateResponse.getStateName());
                            stateLst.add(StateResponse.getStateName());
                            mStatelist1.put(StateResponse.getStateName(), StateResponse.getStateId());
                        }
                        if (!stateLst.isEmpty()) {
                            stockLocationSpinner.setItems(stateLst, "-Select State-", this);
                        }
                    }
                }

            } else {
                //  CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }


    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof NullPointerException) {
            //   CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            // CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check class", "Create Auction Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {

        if (checkedId == R.id.physical || checkedId == R.id.banquet) {
            address.setVisibility(View.VISIBLE);
        } else {
            address.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemsSelected(boolean[] selected) {
    }

    private class SpecialCluasesAdapter extends BaseAdapter {

        Activity activity;
        List<String> ids, clauses;
        List<Boolean> positionArrayList;
        private LayoutInflater inflater = null;
        List<Integer> checked_ids;
        List<String> checked_clauses;

        SpecialCluasesAdapter(Activity activity, List<String> ids, List<String> clauses) {

            this.activity = activity;
            this.ids = ids;
            this.clauses = clauses;

            positionArrayList = new ArrayList<>(ids.size());
            checked_ids = new ArrayList<>(ids.size());
            checked_clauses = new ArrayList<>(ids.size());

            for (int i = 0; i < ids.size(); i++) {
                checked_ids.add(0);
                checked_clauses.add("0");
                positionArrayList.add(false);
            }

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return ids.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            TextView clauses;
            CheckBox checkbox;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View vi = convertView;
            final ViewHolder holder;

            if (convertView == null) {
                vi = inflater.inflate(R.layout.clauses_list, null);
                holder = new ViewHolder();
                holder.clauses = (TextView) vi.findViewById(R.id.txtname);
                holder.checkbox = (CheckBox) vi.findViewById(R.id.check);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
                holder.checkbox.setOnCheckedChangeListener(null);
            }

            holder.clauses.setText(clauses.get(position));

            holder.checkbox.setFocusable(false);
            holder.checkbox.setChecked(positionArrayList.get(position));

            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        checked_ids.set(position, Integer.parseInt(ids.get(position)));
                        checked_clauses.set(position, clauses.get(position));
                        positionArrayList.set(position, true);

                    } else if (checked_ids.contains(Integer.parseInt(ids.get(position)))) {
                        checked_ids.set(position, 0);
                        checked_clauses.set(position, "0");
                        positionArrayList.set(position, false);
                    }
                }

            });

            return vi;
        }


        private List<Integer> checkedids() {
            return checked_ids;
        }

        private List<String> checkedspecialclauses() {
            return checked_clauses;
        }

        public List positionArray() {
            return positionArrayList;
        }
    }
}
