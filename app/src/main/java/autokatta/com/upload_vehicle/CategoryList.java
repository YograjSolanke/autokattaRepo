package autokatta.com.upload_vehicle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 7/6/17.
 */

public class CategoryList extends Fragment {
    View mCategory;
    List<String> mList = new ArrayList<>();
    ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCategory = inflater.inflate(R.layout.upload_vehicle_categries_list, null);
        mListView = (ListView) mCategory.findViewById(R.id.upload_category_list);
        mList.clear();
        getActivity().setTitle("Select Stock Type");
        mList.add("Finance/Repo");
        mList.add("Insurance");
        mList.add("Scrap");
        mList.add("Inventory");
        mList.add("Market Place");

        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = mList.get(position);
                // String subTypeId = mList.get(position).getId();
                if (s != null) {
                    getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_auction_categoryName", s).apply();

                    if (s.equalsIgnoreCase("Scrap") || s.equalsIgnoreCase("Inventory")) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.vehicle_upload_container, new InventoryScrap(), "InventoryScrap")
                                .addToBackStack("InventoryScrap")
                                .commit();
                    } else if (s.equalsIgnoreCase("Finance/Repo") || s.equalsIgnoreCase("Insurance")) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.vehicle_upload_container, new InsuranceFinanceRepo(), "InsuranceFinanceRepo")
                                .addToBackStack("InsuranceFinanceRepo")
                                .commit();
                    } else {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.vehicle_upload_container, new Title(), "Category_list")
                                .addToBackStack("Category_list")
                                .commit();
                    }
                }
            }
        });
        return mCategory;
    }
}
