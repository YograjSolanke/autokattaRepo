package autokatta.com.events;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.GenericFunctions;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.AuctionCreateResponse;
import autokatta.com.response.SpecialClauseAddResponse;
import autokatta.com.response.SpecialClauseGetResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 23/3/17.
 */

public class CreateAuctionFragment extends Fragment
        implements View.OnClickListener, RequestNotifier, View.OnTouchListener, RadioGroup.OnCheckedChangeListener {

    EditText auctioname, startdate, starttime, enddate, endtime;
    AutoCompleteTextView address;
    RadioGroup rgauctiontype;
    RadioButton physical, online, banquet;
    Button addmore, create, cancel;
    String myContact;
    SpecialCluasesAdapter adapter;
    boolean positionArray[];
    String recieve = null;
    ArrayList<Integer> checkedids = new ArrayList<>();
    ArrayList<String> checkedspecialclauses = new ArrayList<>();
    View createAuctionView;
    ListView clauseList;
    ApiCall apiCall;
    String Radiobtn_click = "";
    GenericFunctions validObj;
    String ids = "", cluases = "", name, stdate, sttime, eddate, edtime, type, location;
    private List<String> resultList = new ArrayList<>();

    public CreateAuctionFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createAuctionView = inflater.inflate(R.layout.fragment_create_auction, container, false);
        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");


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
        apiCall = new ApiCall(getActivity(), this);
        validObj = new GenericFunctions();

        rgauctiontype.setOnCheckedChangeListener(this);
        startdate.setOnTouchListener(this);
        starttime.setOnTouchListener(this);
        enddate.setOnTouchListener(this);
        endtime.setOnTouchListener(this);
        create.setOnClickListener(this);
        addmore.setOnClickListener(this);
        clauseList.setOnTouchListener(this);


        address.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.simple));
        apiCall.getSpecialClauses("getClause");


        
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
                // alertDialog.setIcon(R.drawable.key);

                alertDialog.setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                recieve = input.getText().toString();
                                if (recieve.equals(""))
                                    Toast.makeText(getActivity(), "Please enter clause", Toast.LENGTH_LONG).show();
                                else {
                                    //new AddClauseTask().execute();

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

                Boolean flag = false;

                name = auctioname.getText().toString();
                stdate = startdate.getText().toString();
                sttime = starttime.getText().toString();
                eddate = enddate.getText().toString();
                edtime = endtime.getText().toString();
                location = address.getText().toString();


                if (!location.isEmpty()) {

                    resultList = GooglePlacesAdapter.getResultList();
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

                if (name.equals("")) {
                    auctioname.setError("Enter auction title");
                    auctioname.requestFocus();
                    Toast.makeText(getActivity(), "Enter auction title", Toast.LENGTH_LONG).show();
//                    auctioname.setFocusable(true);
                } else if (stdate.equals("")) {
                    startdate.requestFocus();
//                    startdate.setError("Enter start date");
                    Toast.makeText(getActivity(), "Enter start date", Toast.LENGTH_LONG).show();
                } else if (sttime.equals("")) {
                    starttime.requestFocus();
//                    starttime.setError("Enter start time");
                    Toast.makeText(getActivity(), "Enter start time", Toast.LENGTH_LONG).show();
                } else if (eddate.equals("")) {
                    enddate.requestFocus();
//                    enddate.setError("Enter end date");
                    Toast.makeText(getActivity(), "Enter end date", Toast.LENGTH_LONG).show();
                } else if (edtime.equals("")) {
                    endtime.requestFocus();
//                    endtime.setError("Enter end time");
                    Toast.makeText(getActivity(), "Enter end time", Toast.LENGTH_LONG).show();
                } else if (!validObj.startDateValidatioon(stdate)) {
                    startdate.setError("Enter valid Date");
                    startdate.requestFocus();
                } else if (!validObj.startDateEndDateValidation(eddate, stdate)) {
                    enddate.requestFocus();
                    enddate.setError("Enter valid Date");
                } else if (stdate.equals(eddate)) {
                    if (!validObj.startTimeEndTimeValidation(sttime, edtime)) {
                        endtime.setError("Enter valid time");
                        endtime.requestFocus();
                    }
                } else if (address.getVisibility() == View.VISIBLE && location.isEmpty()) {
                    address.setError("Enter Location");
                    address.requestFocus();

                } else if (address.getVisibility() == View.VISIBLE && !flag) {
                    address.setError("Please Select Location From Dropdown Only");
                    address.requestFocus();
                } else {

                    checkedids = adapter.checkedids();
                    checkedspecialclauses = adapter.checkedspecialclauses();
                    positionArray = adapter.positionArray();

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

                    System.out.println(checkedids + "positionArray " + positionArray.length);

                    if (ids.equals(""))
                        Toast.makeText(getActivity(), "Please select atleast single clause", Toast.LENGTH_LONG).show();
                    else {
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
                                apiCall.createAuction(name, stdate, sttime, eddate, edtime, type, myContact, location, "", ids, Radiobtn_click);
                            }
                        });

                        canclebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                    Radiobtn_click=((RadioButton)dialog.findViewById(radiogroup.getCheckedRadioButtonId())).getText().toString();
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

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
                    //whichclick = "enddate";
                    startdate.setInputType(InputType.TYPE_NULL);
                    startdate.setError(null);
                    new SetMyDateAndTime("date", startdate, getActivity());
                }
                break;
            case (R.id.auctionstarttime):


                if (action == MotionEvent.ACTION_DOWN) {
                    //whichclick = "enddate";
                    starttime.setInputType(InputType.TYPE_NULL);
                    starttime.setError(null);
                    new SetMyDateAndTime("time", starttime, getActivity());
                }
                break;
            case (R.id.auctionenddate):

                if (action == MotionEvent.ACTION_DOWN) {
                    //whichclick = "enddate";
                    enddate.setInputType(InputType.TYPE_NULL);
                    enddate.setError(null);
                    new SetMyDateAndTime("date", enddate, getActivity());
                }

                break;
            case (R.id.auctionendtime):

                if (action == MotionEvent.ACTION_DOWN) {
                    //whichclick = "enddate";
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
    public void notifySuccess(Response<?> response) {

        if (response != null) {
            if (response.isSuccessful()) {

                /*
                        Response to get category
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
                        CustomToast.customToast(getActivity(), "Clause Added Successfully");
                        apiCall.getSpecialClauses("getClause");
                    }

                } else if (response.body() instanceof AuctionCreateResponse) {
                    AuctionCreateResponse createResponse = (AuctionCreateResponse) response.body();
                    if (createResponse.getSuccess() != null) {
                        String id = createResponse.getSuccess().getAuctionID().toString();
                        Log.i("AuctId", "->" + id);
                        CustomToast.customToast(getActivity(), "Auction Created Successfully");

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


    public class SpecialCluasesAdapter extends BaseAdapter {

        Activity activity;
        FragmentActivity fragmentActivity;
        ArrayList<String> ids, clauses;
        boolean positionArray[];

        private LayoutInflater inflater = null;

        ArrayList<Integer> checked_ids;
        ArrayList<String> checked_clauses;

        public SpecialCluasesAdapter(Activity activity, ArrayList<String> ids, ArrayList<String> clauses) {

            this.activity = activity;
            this.ids = ids;
            this.clauses = clauses;

            positionArray = new boolean[ids.size()];
            checked_ids = new ArrayList<Integer>(ids.size());
            checked_clauses = new ArrayList<String>(ids.size());

            for (int i = 0; i < ids.size(); i++) {
                checked_ids.add(0);
                checked_clauses.add("0");
                positionArray[i] = false;
            }

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            System.out.println("checked_ids size " + checked_ids.size());
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
            holder.checkbox.setChecked(positionArray[position]);

            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        checked_ids.set(position, Integer.parseInt(ids.get(position)));
                        checked_clauses.set(position, clauses.get(position));
                        positionArray[position] = true;
                    } else if (checked_ids.contains(Integer.parseInt(ids.get(position)))) {
                        checked_ids.set(position, 0);
                        checked_clauses.set(position, "0");
                        positionArray[position] = false;
                    }
                }

            });

            return vi;
        }


        public ArrayList<Integer> checkedids() {
            return checked_ids;
        }

        public ArrayList<String> checkedspecialclauses() {
            return checked_clauses;
        }

        public boolean[] positionArray() {
            return positionArray;
        }


    }
}
