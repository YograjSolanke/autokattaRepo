package autokatta.com.other;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import autokatta.com.R;

/**
 * Created by ak-003 on 9/6/17.
 */

public class CustomSnackBar {
    private View mView;

    /*
    Sub Category Url
     */
    String id, typeId;
    int origin;
    Activity mContext;
    String method;
    String msg;

    public CustomSnackBar(View view, Activity context, String id, String typeId, String method, String msg) {
        this.mView = view;
        this.mContext = context;
        this.id = id;
        this.typeId = typeId;
        this.method = method;
        this.msg = msg;
    }

    public CustomSnackBar(View view, Activity context, String id, int origin, String method, String msg) {
        this.mView = view;
        this.mContext = context;
        this.id = id;
        this.origin = origin;
        this.method = method;
        this.msg = msg;
    }

    public void customSnackBar() {
        try {
            Snackbar snackbar = Snackbar
                    .make(mView, msg, Snackbar.LENGTH_INDEFINITE)
                    .setAction(mContext.getString(R.string.no_internet), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });

// Changing message text color
            snackbar.setActionTextColor(Color.RED);

// Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
