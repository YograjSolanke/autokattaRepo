package autokatta.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import autokatta.com.R;

/**
 * Created by ak-001 on 27/3/17.
 */

public class VehicleDetails_Details extends Fragment {

    View mVehicleDetails;
    TextView mTitleStr, mPriceStr, mViewsStr, mCallStr;
    TextView mAddressDetails, mCategoryDetails, mSubCatDetails, mBrandDetails, mModelDetails, mVersionDetails, mYearDetails;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mVehicleDetails = inflater.inflate(R.layout.fragment_vehicle_details_details, container, false);

        mTitleStr = (TextView) mVehicleDetails.findViewById(R.id.title_str);
        mPriceStr = (TextView) mVehicleDetails.findViewById(R.id.price_str);
        mViewsStr = (TextView) mVehicleDetails.findViewById(R.id.views_str);
        mCallStr = (TextView) mVehicleDetails.findViewById(R.id.call_str);

        mAddressDetails = (TextView) mVehicleDetails.findViewById(R.id.addressdetails);
        mCategoryDetails = (TextView) mVehicleDetails.findViewById(R.id.categorydetails);
        mSubCatDetails = (TextView) mVehicleDetails.findViewById(R.id.subcategorydetails);
        mBrandDetails = (TextView) mVehicleDetails.findViewById(R.id.branddetails);
        mModelDetails = (TextView) mVehicleDetails.findViewById(R.id.modeldetails);
        mVersionDetails = (TextView) mVehicleDetails.findViewById(R.id.versiondetails);
        mYearDetails = (TextView) mVehicleDetails.findViewById(R.id.yeardetails);

        return mVehicleDetails;
    }
}
