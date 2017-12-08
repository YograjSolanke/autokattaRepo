package autokatta.com.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.broadcastreceiver.RssReader;
import autokatta.com.interfaces.ListListener;
import autokatta.com.model.RssItem;

/**
 * Created by ak-001 on 7/12/17
 */

public class RssFeeds extends Fragment {
    View mRssFeed;
    boolean hasViewCreated = false;
    private int count;
    List<RssItem> newList = new ArrayList<>();
    String URL_ONE = "https://auto.economictimes.indiatimes.com/rss/passenger-vehicle";
    String URL_TWO = "https://auto.economictimes.indiatimes.com/rss/commercial-vehicle";
    String URL_THREE = "https://auto.economictimes.indiatimes.com/rss/two-wheelers";
    String URL_FOUR = "https://auto.economictimes.indiatimes.com/rss/aftermarket";
    String URL_FIVE = "https://auto.economictimes.indiatimes.com/rss/auto-components";
    String URL_SIX = "https://auto.economictimes.indiatimes.com/rss/tyres";
    String URL_SEVEN = "https://auto.economictimes.indiatimes.com/rss/auto-technology";
    String URL_EIGHT = "https://auto.economictimes.indiatimes.com/rss/auto-finance";
    String URL_NINE = "https://auto.economictimes.indiatimes.com/rss/automotive";
    String URL_TEN = "https://auto.economictimes.indiatimes.com/rss/oil-and-lubes";

    public RssFeeds() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRssFeed = inflater.inflate(R.layout.activity_rss_channel, container, false);
        return mRssFeed;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                count = 0;
                GetRSSDataTask task = new GetRSSDataTask();
                // Start process RSS task
                task.execute(URL_ONE);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {
                hasViewCreated = true;
            }
        }
    }

    /**
     * This class downloads and parses RSS Channel feed.
     *
     * @author itcuties
     */
    private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem>> {
        @Override
        protected List<RssItem> doInBackground(String... urls) {
            try {
                // Create RSS reader
                RssReader rssReader = new RssReader(urls[0]);
                // Parse RSS, get items
                return rssReader.getItems();
            } catch (Exception e) {
                Log.e("RssFeeds", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<RssItem> result) {
            Log.i("result", "->" + result.get(0));

            newList.addAll(result);
            // Get a ListView from the RSS Channel view
            ListView itcItems = (ListView) mRssFeed.findViewById(R.id.rssChannelListView);
            // Create a list adapter
            ArrayAdapter<RssItem> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, newList);
            // Set list adapter for the ListView
            itcItems.setAdapter(adapter);
            // Set list view item click listener
            itcItems.setOnItemClickListener(new ListListener(result, getActivity()));

            count++;
            if (count == 1) {
                new GetRSSDataTask().execute(URL_TWO);
            } else if (count == 2) {
                new GetRSSDataTask().execute(URL_THREE);
            } else if (count == 3) {
                new GetRSSDataTask().execute(URL_FOUR);
            } else if (count == 4) {
                new GetRSSDataTask().execute(URL_FIVE);
            } else if (count == 5) {
                new GetRSSDataTask().execute(URL_SIX);
            } else if (count == 6) {
                new GetRSSDataTask().execute(URL_SEVEN);
            } else if (count == 7) {
                new GetRSSDataTask().execute(URL_EIGHT);
            } else if (count == 8) {
                new GetRSSDataTask().execute(URL_NINE);
            } else if (count == 9) {
                new GetRSSDataTask().execute(URL_TEN);
            }
        }
    }
}
