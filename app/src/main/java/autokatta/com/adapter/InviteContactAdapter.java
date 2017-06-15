package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;

/**
 * Created by ak-003 on 1/4/17.
 */

public class InviteContactAdapter extends RecyclerView.Adapter<InviteContactAdapter.YoHolder> implements Filterable {

    private Activity mActivity;
    private List<String> contactdata = new ArrayList<>();
    private List<String> contactdata_copy = new ArrayList<>();
    private ItemFilter mFilter = new ItemFilter();
    private String myContact = "";
    private ApiCall apicall;
    private String Rcontact = "";

    public InviteContactAdapter(Activity mActivity, List<String> contactdata) {
        try {
            this.mActivity = mActivity;
            this.contactdata = contactdata;
            contactdata_copy = contactdata;
            myContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "");
            //apicall = new ApiCall(this.mActivity, this);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public YoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_invite_contact, parent, false);
        YoHolder yoHolder = new YoHolder(v);
        return yoHolder;
    }

    @Override
    public void onBindViewHolder(final YoHolder holder, final int position) {

        String contact_name = contactdata.get(position);
        String arr[] = contact_name.split("=");
        System.out.println("Name:=" + arr[0]);
        System.out.println("Number:=" + arr[1]);


        holder.mTextName.setText(arr[0]);
        holder.mTextNumber.setText(arr[1]);
        final String namep = arr[0];
        final String con = arr[1];

        holder.btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("***********" + con);

                sendSMSMessage(con, namep);
            }
        });
        holder.imgCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               call(con);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactdata.size();
    }


    class YoHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ImageView imgCall;
        TextView mTextName, mTextNumber;
        Button btnInvite;

        private YoHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.adapter_invite_contactCard_view);
            imgCall = (ImageView) itemView.findViewById(R.id.callImg);
            mTextName = (TextView) itemView.findViewById(R.id.txtname);
            mTextNumber = (TextView) itemView.findViewById(R.id.txtnumber);
            btnInvite = (Button) itemView.findViewById(R.id.btninvite);
        }
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<String> list = contactdata_copy;

            int count = list.size();

            if (filterString != null && filterString.length() > 0) {


                final ArrayList<String> nlist = new ArrayList<String>(count);

                String filterableString;
                for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                    if (filterableString.contains(" ")) {
                        String[] arr = filterableString.split(" ");
                        String fname = arr[0];
                        String lname = arr[1];

                        if (fname.toLowerCase().startsWith(filterString) || lname.toLowerCase().startsWith(filterString)) {
                            nlist.add(filterableString);
                        }
                    } else if (filterableString.toLowerCase().startsWith(filterString)) {
                        nlist.add(filterableString);
                    }
            }

            results.values = nlist;
            results.count = nlist.size();
            } else {
                results.values = list;
                results.count = list.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactdata = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }

    }

    private void sendSMSMessage(String con, String msg) {
        Log.i("Send SMS", "");

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(con, null, "hi..." + msg, null, null);
            Toast.makeText(mActivity, "SMS sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(mActivity, "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    //Calling Functionality
    private void call(String contact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact));
        try {
            mActivity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Car Details Fragment\n");
        }
    }

}
