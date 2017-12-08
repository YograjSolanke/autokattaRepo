package autokatta.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import autokatta.com.R;

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
        TextView mTitle, mLayoutType, mDateTime;
        ImageView mImageView;

        RecentViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.name);
            mLayoutType = (TextView) itemView.findViewById(R.id.layout);
            mDateTime = (TextView) itemView.findViewById(R.id.dateTime);
            mImageView = (ImageView) itemView.findViewById(R.id.Pic);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recent_visits, parent, false);
        return new RecentViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {

        final RecentViewHolder holder = (RecentViewHolder) holder1;
    }


}
