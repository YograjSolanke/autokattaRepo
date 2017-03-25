package autokatta.com.events;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
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

import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.RequestNotifier;
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

        rgauctiontype.setOnCheckedChangeListener(this);
        startdate.setOnTouchListener(this);
        starttime.setOnTouchListener(this);
        enddate.setOnTouchListener(this);
        endtime.setOnTouchListener(this);
        create.setOnClickListener(this);
        addmore.setOnClickListener(this);


        address.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.simple));


        
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

                                    //addClause();
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

        }
        return false;
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

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

        public SpecialCluasesAdapter(Activity activity, FragmentActivity fragmentActivity, ArrayList<String> ids, ArrayList<String> clauses) {

            this.activity = activity;
            this.fragmentActivity = fragmentActivity;
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

            inflater = (LayoutInflater) fragmentActivity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);

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
