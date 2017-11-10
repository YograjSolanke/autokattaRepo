package autokatta.com.generic;

import android.content.Context;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ak-003 on 10/11/17.
 */

public class Hashtag extends ClickableSpan {
    private Context context;

    public Hashtag(Context ctx) {
        super();
        context = ctx;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ds.linkColor);
        ds.setARGB(255, 30, 144, 255);
    }

    @Override
    public void onClick(View widget) {
        TextView tv = (TextView) widget;
        Spanned s = (Spanned) tv.getText();
        int start = s.getSpanStart(this);
        int end = s.getSpanEnd(this);
        String theWord = s.subSequence(start + 1, end).toString();
        // you can start another activity here
        //Toast.makeText(context, String.format("Tag : %s", theWord), Toast.LENGTH_SHORT ).show();

    }
    //String str  =  "@People , You've gotta #dance like there's nobody watching,#Love like you'll never be #hurt,#Sing like there's @nobody listening,And live like it's #heaven on #earth.";
}
