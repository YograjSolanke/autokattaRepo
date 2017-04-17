package autokatta.com.other;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.fragment.MyGroupsFragment;
import autokatta.com.search.SearchProduct;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {

    private SearchView mSearchView;
    SearchProduct mSearchProduct;
    String searchString;
    Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mBundle = new Bundle();
        mBundle.putString("searchText", searchString);

        mSearchProduct = new SearchProduct();
        mSearchProduct.setArguments(mBundle);

        ViewPager mviewPager = (ViewPager) mSearchView.findViewById(R.id.search_viewpager);
        if (mviewPager != null) {
            setupViewPager(mviewPager);
        }
        TabLayout tabLayout = (TabLayout) mSearchView.findViewById(R.id.search_tabs);
        tabLayout.setupWithViewPager(mviewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getSupportFragmentManager());
        tabAdapterName.addFragment(new MyGroupsFragment(), "Promotional");
        tabAdapterName.addFragment(new SearchProduct(), "Products");
        viewPager.setAdapter(tabAdapterName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchMenuItem.getActionView();
        searchMenuItem.expandActionView();
        setupSearchView();
        return true;
    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(true);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
            // Try to use the "applications" global search provider
            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            mSearchView.setSearchableInfo(info);
        }
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchString = query;
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
