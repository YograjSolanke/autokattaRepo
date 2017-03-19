package autokatta.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import autokatta.com.R;

/**
 * Created by ak-003 on 19/3/17.
 */

public class AutokattaContactFragment extends Fragment {

    View mAutoContact;
    RecyclerView mRecyclerView;
    EditText edtSearchContact;

    public AutokattaContactFragment() {
        //Empty Constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAutoContact = inflater.inflate(R.layout.fragment_autokatta_contacts, container, false);

        edtSearchContact = (EditText) mAutoContact.findViewById(R.id.inputSearch);
        mRecyclerView = (RecyclerView) mAutoContact.findViewById(R.id.rv_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        edtSearchContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //adapter.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return mAutoContact;
    }
}
