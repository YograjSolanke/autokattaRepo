package autokatta.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ak-003 on 7/12/17.
 */

public class RecentVisitsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public RecentVisitsAdapter() {
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private class RecentViewHolder extends RecyclerView.ViewHolder {

        public RecentViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


}
