package autokatta.com.interfaces;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.List;

import autokatta.com.model.RssItem;
import autokatta.com.view.BrowserView;

/**
 * Class implements a list listener
 *
 * @author ITCuties
 */
public class ListListener implements OnItemClickListener {
    // List item's reference
    List<RssItem> listItems;
    // Calling activity reference
    Activity activity;

    public ListListener(List<RssItem> aListItems, Activity anActivity) {
        listItems = aListItems;
        activity = anActivity;
    }

    /**
     * Start a browser with url from the rss item.
     */
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        /*Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(listItems.get(pos).getLink()));
		activity.startActivity(i);*/
        Intent intent = new Intent(activity, BrowserView.class);
        intent.putExtra("url", listItems.get(pos).getLink());
        activity.startActivity(intent);

    }

}
