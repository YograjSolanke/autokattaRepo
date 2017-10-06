package autokatta.com.generic;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ak-004 on 24/3/17.
 */


public class SetMyDateAndTime implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText editText;
    private Calendar myCalendar;
    private GenericFunctions valid;
    private Context context;
    private String key;
    private String startTime;

    public SetMyDateAndTime(String type, EditText editText, Context ctx) {
        this.editText = editText;
        myCalendar = Calendar.getInstance();
        context = ctx;
        valid = new GenericFunctions();

        if (type.equalsIgnoreCase("date")) {
            new DatePickerDialog(context, this, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        } else if (type.equalsIgnoreCase("time")) {
            key = "time";
            new TimePickerDialog(context, this, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false)
                    .show();

        } else if (type.equalsIgnoreCase("timeEvent")) {
            key = "timeEvent";
            new TimePickerDialog(context, this, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true)
                    .show();

        }


    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // this.editText.setText();

        //  String myFormat = "MMM dd, yyyy"; //In which you need put here
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        editText.setText(sdformat.format(myCalendar.getTime()));

    }


    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

        if (key.equals("timeEvent")) {
            startTime = valid.getTimeFormat(i, i1, myCalendar.get(Calendar.SECOND));
            editText.setText(startTime);
        } else if (key.equals("time")) {
            final String AM_PM, hour, minute;

            if (i < 12) {
                AM_PM = "AM";
                if (i < 10) {
                    hour = "0" + String.valueOf(i);
                } else
                    hour = String.valueOf(i);

                if (i1 < 10)
                    minute = "0" + String.valueOf(i1);
                else
                    minute = String.valueOf(i1);

            } else {
                AM_PM = "PM";
                i = i - 12;
                if (i < 10) {
                    hour = "0" + String.valueOf(i);
                } else
                    hour = String.valueOf(i);

                if (i1 < 10)
                    minute = "0" + String.valueOf(i1);
                else
                    minute = String.valueOf(i1);
            }

            editText.setText(hour + ":" + minute + " " + AM_PM);
        }


    }
}
