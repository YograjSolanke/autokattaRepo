package autokatta.com.generic;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ak-004 on 24/3/17.
 */


public class SetMyDate implements DatePickerDialog.OnDateSetListener {

    private EditText editText;
    private Calendar myCalendar;
    Context ctx;

    public SetMyDate(EditText editText, Context ctx) {
        this.editText = editText;
        myCalendar = Calendar.getInstance();
        this.ctx = ctx;
        new DatePickerDialog(ctx, this, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // this.editText.setText();

        //  String myFormat = "MMM dd, yyyy"; //In which you need put here
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        editText.setText(sdformat.format(myCalendar.getTime()));

    }


}
