package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import autokatta.com.R;
import autokatta.com.response.SoldVehicleResponse;

/**
 * Created by ak-001 on 20/9/17.
 */

public class MySoldAdapter extends RecyclerView.Adapter<MySoldAdapter.VehicleHolder> {

    Activity activity;
    List<SoldVehicleResponse.Success.SoldVehicle> mMainList;

    public MySoldAdapter(Activity activity, List<SoldVehicleResponse.Success.SoldVehicle> mMainList) {
        this.activity = activity;
        this.mMainList = mMainList;
    }

    static class VehicleHolder extends RecyclerView.ViewHolder {
        ImageView vehicleimage;
        TextView edittitles, editprices, editcategorys, editbrands, editmodels, editleads, edituploadedon, editmfgyr,
                editkms, editrto, editlocation, editregNo;
        Button vehidetails, btnnotify, delete, mEnquiry, mQuotation, mUploadGroup, mUploadStore, mTransferStock,
                mViewQuote;
        CardView mcardView;
        RelativeLayout mBroadcast;
        LinearLayout mLinear;

        TextView mCustName, mSoldDate, mCustAddress, mCustContact;

        VehicleHolder(View itemView) {
            super(itemView);
            /*edittitles = (TextView) itemView.findViewById(R.id.edittitle);
            editprices = (TextView) itemView.findViewById(R.id.editprice);
            editcategorys = (TextView) itemView.findViewById(R.id.editcategory);
            editbrands = (TextView) itemView.findViewById(R.id.editbrand);
            editmodels = (TextView) itemView.findViewById(R.id.editmodel);
            editleads = (TextView) itemView.findViewById(R.id.editleads);
            edituploadedon = (TextView) itemView.findViewById(R.id.edituploadedon);
            delete = (Button) itemView.findViewById(R.id.delete);
            btnnotify = (Button) itemView.findViewById(R.id.btnnotify);
            mEnquiry = (Button) itemView.findViewById(R.id.Enquiry);
            mUploadGroup = (Button) itemView.findViewById(R.id.upload_group);
            mUploadStore = (Button) itemView.findViewById(R.id.upload_store);
            editmfgyr = (TextView) itemView.findViewById(R.id.year);
            editkms = (TextView) itemView.findViewById(R.id.km_hrs);
            //edithrs=(TextView)itemView.findViewById(R.id.km_hrs);
            editrto = (TextView) itemView.findViewById(R.id.RTO);
            editlocation = (TextView) itemView.findViewById(R.id.location);
            editregNo = (TextView) itemView.findViewById(R.id.registrationNo);
            mBroadcast = (RelativeLayout) itemView.findViewById(R.id.relativebroadcast);
            mLinear = (LinearLayout) itemView.findViewById(R.id.linearbtns);
            mQuotation = (Button) itemView.findViewById(R.id.quotation);
            mTransferStock = (Button) itemView.findViewById(R.id.transfer_stock);
            mViewQuote = (Button) itemView.findViewById(R.id.view_quotation);
            vehidetails = (Button) itemView.findViewById(R.id.vehibtndetails);*/

            vehicleimage = (ImageView) itemView.findViewById(R.id.vehiprofile);
            mcardView = (CardView) itemView.findViewById(R.id.card_view);
            mCustName = (TextView) itemView.findViewById(R.id.customer_name);
            mSoldDate = (TextView) itemView.findViewById(R.id.sold_date);
            mCustAddress = (TextView) itemView.findViewById(R.id.address);
            mCustContact = (TextView) itemView.findViewById(R.id.editcontact);
        }
    }

    @Override
    public MySoldAdapter.VehicleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_sold_vehicle_adapter, parent, false);
        return new VehicleHolder(view);
    }

    @Override
    public void onBindViewHolder(MySoldAdapter.VehicleHolder holder, int position) {

        holder.mCustName.setText(mMainList.get(position).getCustName());
        holder.mSoldDate.setText(mMainList.get(position).getSoldDate());
        holder.mCustAddress.setText(mMainList.get(position).getAddress());
        holder.mCustContact.setText(mMainList.get(position).getSoldToContact());
    }

    @Override
    public int getItemCount() {
        return mMainList.size();
    }
}
