package autokatta.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ak-001 on 5/5/17.
 */

public class ManualEnquiryAdapter extends RecyclerView.Adapter<ManualEnquiryAdapter.ManualEnquiryViewHolder> {

    static class ManualEnquiryViewHolder extends RecyclerView.ViewHolder {
        public ManualEnquiryViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public ManualEnquiryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ManualEnquiryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
