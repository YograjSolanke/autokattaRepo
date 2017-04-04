package autokatta.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.interfaces.RequestNotifier;
import retrofit2.Response;

/**
 * Created by ak-004 on 3/4/17.
 */

public class MySearchAdapter extends RecyclerView.Adapter<MySearchAdapter.SearchHolder> implements RequestNotifier {
    @Override
    public MySearchAdapter.SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MySearchAdapter.SearchHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class SearchHolder extends RecyclerView.ViewHolder {

        public SearchHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
