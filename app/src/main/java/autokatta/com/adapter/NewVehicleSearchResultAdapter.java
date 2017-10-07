package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;

/**
 * Created by ak-004 on 7/10/17.
 */

public class NewVehicleSearchResultAdapter extends RecyclerView.Adapter<NewVehicleSearchResultAdapter.VehicleHolder> implements RequestNotifier {

    Activity activity;

    public NewVehicleSearchResultAdapter() {

    }

    @Override
    public NewVehicleSearchResultAdapter.VehicleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_vehicle_search_adapter, parent, false);
        return new VehicleHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewVehicleSearchResultAdapter.VehicleHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(activity, activity.getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "StoreVehicleAdapter");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

    }

    class VehicleHolder extends RecyclerView.ViewHolder {
        VehicleHolder(View itemView) {
            super(itemView);


        }

        /***
         * Retrofit Logs
         ***/
        private OkHttpClient.Builder initLog() {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging).readTimeout(90, TimeUnit.SECONDS);
            return httpClient;
        }
    }
}
