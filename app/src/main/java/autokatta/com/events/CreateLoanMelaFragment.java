package autokatta.com.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import retrofit2.Response;

/**
 * Created by ak-004 on 23/3/17.
 */

public class CreateLoanMelaFragment extends Fragment implements RequestNotifier {

    View createLoanView;
    ApiCall apiCall;


    public CreateLoanMelaFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createLoanView = inflater.inflate(R.layout.fragment_create_loanmela, container, false);

        apiCall = new ApiCall(getActivity(), this);
        return createLoanView;


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
