package autokatta.com.upload_vehicle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.net.SocketTimeoutException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 22/3/17.
 */

public class Upload_Group_Create_Fragment extends Fragment implements View.OnClickListener, RequestNotifier {
    View mGroupCreate;
    Button mCreateGroup;
    EditText mGroupTitle;
    ImageView mGroupImage;
    String lastWord = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGroupCreate = inflater.inflate(R.layout.fragment_group_create, container, false);
        mGroupTitle = (EditText) mGroupCreate.findViewById(R.id.group_title);
        mCreateGroup = (Button) mGroupCreate.findViewById(R.id.BtnCreateGroup);
        mGroupImage = (ImageView) mGroupCreate.findViewById(R.id.group_profile_pic);
        return mGroupCreate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.BtnCreateGroup:
                if (mGroupTitle.getText().toString().equalsIgnoreCase("") || mGroupTitle.getText().toString().startsWith(" ")) {
                    Snackbar.make(v, "Please provide group name and optional group icon", Snackbar.LENGTH_LONG).show();
                } else {
                    createGroups(mGroupTitle.getText().toString(), lastWord,
                            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null));
                }
                break;
        }
    }

    /*
    Create group
     */
    private void createGroups(String s, String lastWord, String contact) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.createGroup(s,lastWord, contact);
    }

    /*
    Retrofit Response...
     */
    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "Upload Group Create Fragment");
        }
    }

    @Override
    public void notifyString(String str) {
        if (str!=null){
            Log.i("String","->"+str);
        }
    }
}
