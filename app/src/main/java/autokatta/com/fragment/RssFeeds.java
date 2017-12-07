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
                GetRSSDataTask task = new GetRSSDataTask();
                // Start process RSS task
                task.execute("https://economictimes.indiatimes.com/rssfeeds/13359675.cms");
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
            // Get a ListView from the RSS Channel view
            ListView itcItems = (ListView) mRssFeed.findViewById(R.id.rssChannelListView);
            // Create a list adapter
            ArrayAdapter<RssItem> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, result);
            // Set list adapter for the ListView
            itcItems.setAdapter(adapter);
            // Set list view item click listener
            itcItems.setOnItemClickListener(new ListListener(result, getActivity()));
        }
    }
}
