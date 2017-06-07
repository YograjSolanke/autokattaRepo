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

import autokatta.com.R;
import autokatta.com.adapter.GetCategoryListAdapter;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 7/6/17.
 */

public class CategoryList extends Fragment {
    View mCategory;
    ArrayList mList=new ArrayList();
    ListView mListView;
    GetCategoryListAdapter getCategoryListadapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCategory=inflater.inflate(R.layout.upload_vehicle_categries_list,null);
        mListView= (ListView) mCategory.findViewById(R.id.upload_category_list);
        mList.clear();
        getActivity().setTitle("Select Auction Category");
        mList.add("Finance/Repo");
        mList.add("Insurance Salvage");
        mList.add("Scrapped");
        mList.add("Dealer Stock");
        mList.add("Market Place");


        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(adapter);
/*
        GetCategoryListAdapter getCategoryListadapter = new GetCategoryListAdapter(getActivity(), mList);
        mListView.setAdapter(getCategoryListadapter);
        getCategoryListadapter.notifyDataSetChanged();*/
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = mList.get(position).toString();
               // String subTypeId = mList.get(position).getId();
                if (s != null) {
                    getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_auction_categoryName", s).apply();
                  //  getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_categoryId", subTypeId).apply();
                       getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.vehicle_upload_container, new Title(), "Category_list")
                            .addToBackStack("Category_list")
                            .commit();
                }
            }
        });
        return mCategory;
    }
}
