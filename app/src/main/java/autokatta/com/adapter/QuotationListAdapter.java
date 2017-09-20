package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import autokatta.com.response.MyVehicleQuotationListResponse;

/**
 * Created by ak-004 on 20/9/17.
 */

public class QuotationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;
    private List<MyVehicleQuotationListResponse.Success> QuotationList;
    private String mLoginContact;


    public QuotationListAdapter(Activity activity1, List<MyVehicleQuotationListResponse.Success> QuotationList1,
                                String mLoginContact1) {
        mActivity = activity1;
        QuotationList = QuotationList1;
        mLoginContact = mLoginContact1;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
