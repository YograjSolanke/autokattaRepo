package autokatta.com.browseStore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;

/**
 * Created by ak-003 on 20/3/17.
 */

public class ProductBasedStore extends Fragment {

    View mProductBased;

    public ProductBasedStore() {
        //Empty Constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProductBased = inflater.inflate(R.layout.fragment_product_based_store, container, false);

        return mProductBased;
    }
}
