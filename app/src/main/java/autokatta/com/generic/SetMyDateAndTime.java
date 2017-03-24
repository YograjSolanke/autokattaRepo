package autokatta.com.generic;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ak-004 on 24/3/17.
 */


public class SetMyDateAndTime implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText editText;
    private Calendar myCalendar;
    GenericFunctions valid;
    Context ctx;

    public SetMyDateAndTime(String type, EditText editText, Context ctx) {
        this.editText = editText;
        myCalendar = Calendar.getInstance();
        this.ctx = ctx;
        valid = new GenericFunctions();

        if (type.equalsIgnoreCase("date")) {
            new DatePickerDialog(ctx, this, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        } else if (type.equalsIgnoreCase("time")) {
            new TimePickerDialog(ctx, this, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true)
                    .show();

        }


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


    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String startTime = valid.getTimeFormat(i, i1, myCalendar.get(Calendar.SECOND));

        editText.setText(startTime);

    }
}
