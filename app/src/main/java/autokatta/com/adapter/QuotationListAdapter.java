package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import autokatta.com.R;
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
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_qoutation_list, parent, false);
        return new QuotationHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        final QuotationHolder holder = (QuotationHolder) holder1;

        holder.name.setText(QuotationList.get(position).getCustomerName());
        holder.contact.setText(QuotationList.get(position).getCustContact());
        holder.reservedPrice.setText(String.valueOf(QuotationList.get(position).getReservePrice()));
        holder.date.setText(QuotationList.get(position).getCreatedDate());

    }

    @Override
    public int getItemCount() {
        return QuotationList.size();
    }


    private static class QuotationHolder extends RecyclerView.ViewHolder {

        TextView name, contact, reservedPrice, date;


        QuotationHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.setName);
            contact = (TextView) itemView.findViewById(R.id.setContact);
            reservedPrice = (TextView) itemView.findViewById(R.id.setPrice);
            date = (TextView) itemView.findViewById(R.id.setDate);
        }
    }
}
