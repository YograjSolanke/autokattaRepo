package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.Registration.InviteFriends;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.CreateUserResponse;
import autokatta.com.response.GetRegisteredContactsResponse;
import autokatta.com.response.GetRegisteredContactsResponse.Success;
import retrofit2.Response;

/**
 * Created by ak-005 on 31/3/17.
 */

public class InviteFriendsAdapter extends BaseAdapter implements RequestNotifier{
    private Activity mContext;


    private List<String> originalData = null;
   // private List<Success> filteredData = null;
    private LayoutInflater mInflater;
    ArrayList<Boolean> positionArray;

    String nameToSend,contactToSend;
   ApiCall mApiCall;
    String mContact,mPassword;

    private List<GetRegisteredContactsResponse.Success> mList;
    private List<GetRegisteredContactsResponse.Success> mListCopy;
   private ItemFilter mFilter = new ItemFilter();

    public InviteFriendsAdapter(Activity mContext, List<GetRegisteredContactsResponse.Success> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mListCopy=mList;

    }


    private class ContactListHolder{
        TextView mName;
        TextView mContact;
        Button mbtnInvite;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactListHolder contactListHolder = null;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.invite_friend_adapter, null);
            mApiCall=new ApiCall(mContext,this);

            contactListHolder = new ContactListHolder();
            contactListHolder.mName = (TextView) convertView.findViewById(R.id.txtname);
            contactListHolder.mContact = (TextView) convertView.findViewById(R.id.txtnumber);
            contactListHolder.mbtnInvite = (Button) convertView.findViewById(R.id.btninvite);
            convertView.setTag(contactListHolder);
        }else {
            contactListHolder = (ContactListHolder) convertView.getTag();
        }
        Log.i("aaaaaaaaaaaaaaaaa","->"+mList);
        GetRegisteredContactsResponse.Success success = mList.get(position);
        contactListHolder.mName.setText(success.getUsername());
        contactListHolder.mContact.setText(success.getContact());

        final ContactListHolder ContactListHolder = contactListHolder;

      /*  // If weren't re-ordering this you could rely on what you set last time
        String contact_name= String.valueOf(mListCopy.get(position));
        String arr[]=contact_name.split("=");
        System.out.println("Name:="+arr[0]);
        System.out.println("Number:="+arr[1]);



        contactListHolder.mName.setText(arr[0]);
        contactListHolder.mContact.setText(arr[1]);
*/

        final InviteFriendsAdapter.ContactListHolder finalContactListHolder = contactListHolder;
        contactListHolder.mbtnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameToSend = ContactListHolder.mName.getText().toString();
                contactToSend = ContactListHolder.mContact.getText().toString();
              mApiCall.createUser(nameToSend,contactToSend);
              //  createUser(nameToSend,contactToSend);
                //sendSMSMessage(con, namep);
            }
        });



        return convertView;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        protected FilterResults performFiltering(CharSequence constraint)
        {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<GetRegisteredContactsResponse.Success> list = mListCopy;

            int count = list.size();

            final ArrayList<GetRegisteredContactsResponse.Success> nlist = new ArrayList<>(count);

            GetRegisteredContactsResponse.Success filterableString ;

            for (int i = 0; i < count; i++)
            {
                filterableString = list.get(i);
                if(filterString.equals(""))
                {
                    nlist.add(filterableString);
                }
                else if(filterableString.getUsername().toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mList = (ArrayList<Success>) results.values;
            notifyDataSetChanged();
        }
    }
    public int getCount() {
        return mList.size();
    }

    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response.isSuccessful()) {

            CreateUserResponse mCreateUserResponse = (CreateUserResponse) response.body();

            for (CreateUserResponse.Success mUserResponse : mCreateUserResponse.getSuccess()) {
                mUserResponse.setContact(mUserResponse.getContact());
                mUserResponse.setUsername(mUserResponse.getUsername());
                mUserResponse.setRegId(mUserResponse.getRegId());
                mUserResponse.setPassword(mUserResponse.getPassword());

                 mContact=mUserResponse.getContact().toString();
                 mPassword=mUserResponse.getPassword().toString();

            }
            sendSMSMessage(mContact,mPassword);

            System.out.println("*******Response"+mContact+"pass"+mPassword);
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
    protected void sendSMSMessage(String con, String msg) {
        Log.i("Send SMS", "");

        try
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(con, null, "hi..."+msg, null, null);
            Toast.makeText(mContext, "SMS sent.", Toast.LENGTH_LONG).show();
            Intent i=new Intent(mContext, InviteFriends.class);
            mContext.startActivity(i);
            mContext.finish();
          /*  Invitefriends fr = new Invitefriends();
            mFragmentManager = ctx.getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.containerView, fr).commit();*/
        }

        catch (Exception e)
        {
            Toast.makeText(mContext, "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}
