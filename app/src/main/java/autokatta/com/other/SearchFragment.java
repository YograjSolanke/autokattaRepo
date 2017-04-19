package autokatta.com.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.fragment.MyGroupsFragment;
import autokatta.com.search.SearchPerson;
import autokatta.com.search.SearchProduct;
import autokatta.com.search.SearchService;
import autokatta.com.search.SearchStore;
import autokatta.com.search.SearchVehicle;

/**
 * Created by ak-001 on 17/4/17.
 */

public class SearchFragment extends Fragment {
    View mSearchView;
    SearchProduct mSearchProduct;
    SearchService mSearchService;
    SearchStore mSearchStore;
    SearchPerson mSearchPerson;
    SearchVehicle mSearchVehicle;
    Bundle mBundle;
    Bundle bundle = new Bundle();
    String searchString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchView = inflater.inflate(R.layout.fragment_search, container, false);

        mSearchProduct = new SearchProduct();
        mSearchService = new SearchService();
        mSearchStore = new SearchStore();
        mSearchPerson = new SearchPerson();
        mSearchVehicle = new SearchVehicle();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBundle = getArguments();
                if (mBundle != null) {
                    searchString = mBundle.getString("searchText");
                    mSearchProduct.setArguments(bundle);
                    mSearchService.setArguments(bundle);
                    mSearchStore.setArguments(bundle);
                    mSearchPerson.setArguments(bundle);
                    mSearchVehicle.setArguments(bundle);
                }
                bundle.putString("searchText1", searchString);
                Log.i("searchText", "->" + searchString);

                ViewPager mviewPager = (ViewPager) mSearchView.findViewById(R.id.search_viewpager);
                if (mviewPager != null) {
                    setupViewPager(mviewPager);
                }
                TabLayout tabLayout = (TabLayout) mSearchView.findViewById(R.id.search_tabs);
                tabLayout.setupWithViewPager(mviewPager);
            }
        });
        return mSearchView;
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getChildFragmentManager());
        tabAdapterName.addFragment(new MyGroupsFragment(), "Promotional");
        tabAdapterName.addFragment(mSearchProduct, "Products");
        tabAdapterName.addFragment(mSearchService, "Services");
        tabAdapterName.addFragment(mSearchStore, "Store");
        tabAdapterName.addFragment(mSearchPerson, "Person");
        tabAdapterName.addFragment(mSearchVehicle, "Vehicle");
        viewPager.setAdapter(tabAdapterName);
    }
}
