package autokatta.com.other;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import autokatta.com.R;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.search_product, new SearchFragment()).commit();
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
        mSearchView.setIconifiedByDefault(false);

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
        SearchFragment searchTabFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString("searchText", query);
        searchTabFragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction xfragmentTransaction = manager.beginTransaction();
        xfragmentTransaction.replace(R.id.search_product, searchTabFragment).commit();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
