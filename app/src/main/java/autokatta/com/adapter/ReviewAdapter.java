package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import autokatta.com.R;
import autokatta.com.response.ReviewAndReplyResponse;

/**
 * Created by ak-004 on 26/9/17.
 */

public class ReviewAdapter extends BaseAdapter implements View.OnClickListener {


    private Activity activity;
    private LayoutInflater inflater = null;
    private String contactnumber;
    DateFormat date, time;
    private List<String> hashMapDate;
    private List<String> hashMapposition;


    private List<ReviewAndReplyResponse.Success.ReviewMessage> locallist = new ArrayList<>();

    public ReviewAdapter(Activity activity, List<ReviewAndReplyResponse.Success.ReviewMessage> locallist, String contactnumber) {

        date = new SimpleDateFormat(" MMM dd yyyy", Locale.getDefault());
        time = new SimpleDateFormat(" hh:mm aa", Locale.getDefault());


        this.activity = activity;
        this.contactnumber = contactnumber;
        this.locallist = locallist;
        hashMapDate = new ArrayList<>();
        hashMapposition = new ArrayList<>();


        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return locallist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    /*********
     * Create a holder Class to contain inflated xml file elements
     *********/
    public static class ViewHolder {

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ReviewAndReplyResponse.Success.ReviewMessage MessageObj = locallist.get(position);
        View row = convertView;
        String keyword = "";
        if (keyword.equals("Review"))
        row = inflater.inflate(R.layout.left, parent, false);
        else
            row = inflater.inflate(R.layout.right, parent, false);

        TextView chattext = (TextView) row.findViewById(R.id.msgr);
        TextView dateNtime = (TextView) row.findViewById(R.id.dateNtime);

        chattext.setText(locallist.get(position).getReviewString());


        return row;
    }

    @Override
    public void onClick(View v) {

    }


}

