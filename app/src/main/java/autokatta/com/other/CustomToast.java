package autokatta.com.other;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import autokatta.com.R;

/**
 * Created by ak-001 on 16/3/17.
 */

public class CustomToast {
    public static void customToast(Context context, String msg)
    {
        try {
            //Creating the LayoutInflater instance
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Getting the View object as defined in the customtoast.xml file
            View layout = li.inflate(R.layout.toast_layout,null);

            TextView message = (TextView) layout.findViewById(R.id.msg);
            message.setText(msg);
            //Creating the Toast object
            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
            toast.setView(layout);//setting the view of custom toast layout
            toast.show();
            //Toast.makeText(getApplicationContext(), "Using this you will Save the data!", Toast.LENGTH_LONG).show();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
