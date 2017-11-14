package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreEmployeeResponse;
import retrofit2.Response;

/**
 * Created by ak-004 on 14/11/17.
 */

public class MyStoreEmployeeAdapter extends RecyclerView.Adapter<MyStoreEmployeeAdapter.YoHolder> implements RequestNotifier {
    private Activity mActivity;
    private List<StoreEmployeeResponse.Success.Employee> mEmpList = new ArrayList<>();
    private ConnectionDetector mConnectionDetector;
    private String strDetailsShare = "", myContact;

    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    static class YoHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mEmpName, mEmpContact;
        ImageView mCall;
        Button mRemove, mInventory, mEnquiries;


        YoHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.adapter_mystoreListCard_view);
            mEmpName = (TextView) itemView.findViewById(R.id.textName);
            mEmpContact = (TextView) itemView.findViewById(R.id.textContact);
            mCall = (ImageView) itemView.findViewById(R.id.call);
            mRemove = (Button) itemView.findViewById(R.id.btnRemove);
            mInventory = (Button) itemView.findViewById(R.id.btnInventory);
            mEnquiries = (Button) itemView.findViewById(R.id.btnEnquiries);


        }
    }

    @Override
    public int getItemCount() {
        return mEmpList.size();
    }

    public MyStoreEmployeeAdapter(Activity mActivity1, List<StoreEmployeeResponse.Success.Employee> mItemList) {
        try {
            this.mActivity = mActivity1;
            this.mEmpList = mItemList;

            mConnectionDetector = new ConnectionDetector(mActivity);
        } catch (ClassCastException c) {
            c.printStackTrace();
        }
    }

    @Override
    public MyStoreEmployeeAdapter.YoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_store_employee_adapter, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new YoHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyStoreEmployeeAdapter.YoHolder holder, int position) {
        myContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).
                getString("loginContact", "");

        holder.mEmpName.setText(mEmpList.get(position).getName());
        holder.mEmpContact.setText(mEmpList.get(position).getContactNo());


//        holder.img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String image;
//                if (mStoreList.get(holder.getAdapterPosition()).getStoreImage().equals(""))
//                    image = mActivity.getString(R.string.base_image_url) + "logo48x48.png";
//                else
//                    image = mActivity.getString(R.string.base_image_url) + mStoreList.get(holder.getAdapterPosition()).getStoreImage();
//                Intent intent = new Intent(mActivity, FullImageActivity.class);
//                Bundle b = new Bundle();
//                b.putString("image", image);
//                intent.putExtras(b);
//                mActivity.startActivity(intent);
//            }
//        });
    }

    private void deleteStore(int storeId) {
        ApiCall apiCall = new ApiCall(mActivity, this);
        apiCall.DeleteStore(storeId, "delete");
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "MyStoreList Adapter");
        }
    }

    @Override
    public void notifyString(String str) {

        if (str != null) {
            if (str.startsWith("success")) {
                CustomToast.customToast(mActivity, "Store deleted");

                //mStoreList.remove(getAdapterPosition());
            }

        }
    }

}
