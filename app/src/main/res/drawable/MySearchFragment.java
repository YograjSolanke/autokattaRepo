package com.autokatta.search_vehicle;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.autokatta.R;
import com.autokatta.adapter.MysearchAdapter;
import com.autokatta.classfiles.SearchData;
import com.autokatta.classfiles.TimeStampConstants;
import com.autokatta.utils.NetworkUtils;
import com.autokatta.view.LoginActivity;
import com.autokatta.view.MainActivity;
import com.autokatta.view.SQlitewallDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ak-003 on 4/4/16.
 */

@SuppressLint("SimpleDateFormat")
public class MySearchFragment extends Fragment
{
    FragmentActivity ctx;
    String contactnumber;
    ListView list;
    MysearchAdapter adapter;
    SQlitewallDB sqlite_obj;
    ArrayList<SearchData>searchlist=new ArrayList<>();
    Boolean flag_loading =false;
    FloatingActionButton getHelp;
    String refSearchId="";
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    public static final String MyContactPREFERENCES = "contact No" ;
    RequestQueue requestQueue;


     @Override
    public void onAttach(Context activity)
    {
        super.onAttach(activity);
        ctx=(FragmentActivity)activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
         View root = inflater.inflate(R.layout.store_search_result_activity, container, false);

        MainActivity.toolbar.setTitle("My Search");

        list=(ListView)root.findViewById(R.id.store_list);
        TextView headingtext=(TextView)root.findViewById(R.id.headingtext);
        headingtext.setText("My Search List");

        getHelp = (FloatingActionButton) root.findViewById(R.id.fabHelp);

        prefs = getActivity().getSharedPreferences(MyContactPREFERENCES, Context.MODE_PRIVATE);
        contactnumber = prefs.getString("contact", "");

        sqlite_obj= SQlitewallDB.getInstance(getActivity());
        requestQueue= Volley.newRequestQueue(getActivity());
        if (!NetworkUtils.isNetworkAvailable()) {
//
            refSearchId="";
            DisplayContent(refSearchId);
            adapter = new MysearchAdapter(getActivity(), ctx,searchlist);
            list.setAdapter(adapter);

            Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();

        }
        else{

            try {
                getMySearchData();
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

        getHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog openDialog = new Dialog(getActivity());
                openDialog.setContentView(R.layout.customdialog_help);

                TextView dialogText = (TextView)openDialog.findViewById(R.id.dialog_text);
                ImageView dialogImage = (ImageView) openDialog.findViewById(R.id.dialog_image);
                Button dialogClose= (Button) openDialog.findViewById(R.id.dialog_button);

                openDialog.setTitle("Help");
                dialogText.setText(Html.fromHtml("<HTML><HEAD></HEAD><BODY>"+"My Search "+"</BODY></HTML>"));

                dialogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog.dismiss();
                    }
                });
                openDialog.show();
            }
        });



        list.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
                {
                    if(!flag_loading)
                    {
                        flag_loading = true;
                        DisplayContent(refSearchId);

                        adapter.notifyDataSetChanged();
                        flag_loading=false;
                    }
                }
            }
        });

        return root;
    }



    private void getMySearchData()throws JSONException{

        //object.open();
        sqlite_obj.open();

        if (LoginActivity.mysearchflag == false)
            sqlite_obj.deleteAllfromMySearchList();

        sqlite_obj.close();


        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://autokatta.com/mobile/getMyVehicleSearch.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONArray new_array = null;
                        try {
                            new_array = new JSONArray(response);
                            System.out.println("+++++++++new Array Length"+new_array.length());

                            sqlite_obj.open();
                            for (int i = new_array.length()-1;i>=0; i--) {

                                JSONObject jsonObject = new_array.getJSONObject(i);

                                String dates = jsonObject.optString("searchdate");
                                 DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                Date d = null;
                                try {
                                    d = f.parse(dates);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
//
                                String dates1 = jsonObject.optString("stopdate");
                                DateFormat f1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                try {
                                    Date d1 = f1.parse(dates1);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                sqlite_obj.insertintoMysearchlist(jsonObject.optString("search_id"),
                                        jsonObject.optString("category"),
                                        jsonObject.optString("Brand"),
                                        jsonObject.optString("model"),
                                        jsonObject.optString("price"),
                                        jsonObject.optString("year_of_manufactur"),
                                        jsonObject.optString("searchstatus"),
                                        jsonObject.optString("BuyerLeads"),
                                        jsonObject.optString("mysearchstatus"),
                                        dates,
                                        dates1

                                );



                                if(i==0)
                                    TimeStampConstants.mysearchTimeId=jsonObject.optString("search_id");




                            }

                            sqlite_obj.close();
                            DisplayContent(refSearchId);
                            adapter = new MysearchAdapter(getActivity(), ctx,searchlist);
                            list.setAdapter(adapter);
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("contact",contactnumber);
                params.put("timestamp", TimeStampConstants.mysearchTimeId);

                return  params;
            }
        };
        requestQueue.add(stringRequest);
    }
    // Function To Handle Back Event
    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.containerView);

        if(fm.getBackStackEntryCount() > 0){
            fm.popBackStack();
        }

    }

    private void DisplayContent(String id) {
        // TODO Auto-generated method stub

        sqlite_obj.open();
        Cursor c = sqlite_obj.getAllDatafromMySearchlist(id);


        if (c.moveToFirst())
        {
            do {
        SearchData obj=new SearchData();

        obj.searchId=c.getString(0);
                refSearchId=obj.searchId;
        obj.searchCategory=c.getString(1);
        obj.searchBrand=c.getString(2);
        obj.searchModel=c.getString(3);
        obj.searchPrice=c.getString(4);
        obj.searchYear=c.getString(5);
        obj.searchFavouritestatus=c.getString(6);
        obj.searchBuyerLead=c.getString(7);
        obj.searchStatus=c.getString(8);

        DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String datess = c.getString(9).toString();

        Date d = null;
        try {
            d = f.parse(datess);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        obj.searchDate=d;


        String dates = c.getString(10).toString();

        Date d1 = null;
        try {
            d1 = f.parse(dates);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        obj.searchStopDate=d1;

        searchlist.add(obj);
            } while (c.moveToNext());
        }

        sqlite_obj.close();



        System.out.println("++++++++++++++++++++++++++++++=both count="+list.getCount()+"-"+searchlist);


    }


    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    onBackPressed();

                    return true;

                }

                return false;
            }
        });

    }


}
