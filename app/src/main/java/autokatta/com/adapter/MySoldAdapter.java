package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

        VehicleHolder(View itemView) {
            super(itemView);
            edittitles = (TextView) itemView.findViewById(R.id.edittitle);
            editprices = (TextView) itemView.findViewById(R.id.editprice);
            editcategorys = (TextView) itemView.findViewById(R.id.editcategory);
            editbrands = (TextView) itemView.findViewById(R.id.editbrand);
            editmodels = (TextView) itemView.findViewById(R.id.editmodel);
            editleads = (TextView) itemView.findViewById(R.id.editleads);
            edituploadedon = (TextView) itemView.findViewById(R.id.edituploadedon);
            vehicleimage = (ImageView) itemView.findViewById(R.id.vehiprofile);
            delete = (Button) itemView.findViewById(R.id.delete);
            vehidetails = (Button) itemView.findViewById(R.id.vehibtndetails);
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
            mcardView = (CardView) itemView.findViewById(R.id.card_view);
            mBroadcast = (RelativeLayout) itemView.findViewById(R.id.relativebroadcast);
            mLinear = (LinearLayout) itemView.findViewById(R.id.linearbtns);
            mQuotation = (Button) itemView.findViewById(R.id.quotation);
            mTransferStock = (Button) itemView.findViewById(R.id.transfer_stock);
            mViewQuote = (Button) itemView.findViewById(R.id.view_quotation);
        }
    }

    @Override
    public MySoldAdapter.VehicleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MySoldAdapter.VehicleHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
