package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import autokatta.com.R;
import autokatta.com.response.GetContactByCompanyResponse;

/**
 * Created by ak-005 on 29/3/17.
 */

public class CompanyBasedInvitationAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private Activity mContext;
    private List<GetContactByCompanyResponse.Success> mList;


   // private ItemFilter mFilter = new ItemFilter();


    public CompanyBasedInvitationAdapter(Activity mContext,  List<GetContactByCompanyResponse.Success> mList) {

        this.mContext = mContext;
        this.mList = mList;
       // mInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    private class ContactListHolder{
        TextView mName;
        TextView mContact;
        ImageView mPro_pic;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactListHolder contactListHolder = null;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.companyinvitation_adapter, null);

            contactListHolder = new ContactListHolder();
            contactListHolder.mName = (TextView) convertView.findViewById(R.id.names);
            contactListHolder.mContact = (TextView) convertView.findViewById(R.id.contact);
            contactListHolder.mPro_pic = (ImageView) convertView.findViewById(R.id.pro_pic);
            convertView.setTag(contactListHolder);
        }else {
            contactListHolder = (ContactListHolder) convertView.getTag();
        }
        Log.i("aaaaaaaaaaaaaaaaa","->"+mList);
        GetContactByCompanyResponse.Success success = mList.get(position);
        contactListHolder.mName.setText(success.getUsername());
        contactListHolder.mContact.setText(success.getContact());


        //Set Profile Photo
        if (contactListHolder.mPro_pic.equals("") || contactListHolder.mPro_pic.equals(null) || contactListHolder.mPro_pic.equals("null"))
        {
            contactListHolder.mPro_pic.setBackgroundResource(R.drawable.profile);
        }
       else
        {
            contactListHolder.mPro_pic.setImageURI(Uri.parse(success.getProfilePic()));

        }
        return convertView;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position)  {
        return position;
    }

    @Override
    public long getItemId(int position)  {
        return mList.get(position).hashCode();
    }


}
