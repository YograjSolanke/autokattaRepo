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
        SimpleDateFormat sdformat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        editText.setText(sdformat.format(myCalendar.getTime()));

    }


    @Override
    public void onTimeSet(TimePicker timePicker, int currentHour, int currentMinute) {

        if (key.equals("timeEvent")) {
            startTime = valid.getTimeFormat(currentHour, currentMinute, myCalendar.get(Calendar.SECOND));
            editText.setText(startTime);
        } else if (key.equals("time")) {
            final String AM_PM, hour, minute;

            if (currentHour < 12) {
                AM_PM = "AM";
                if (currentHour < 10) {
                    hour = "0" + String.valueOf(currentHour);
                } else
                    hour = String.valueOf(currentHour);

                if (currentMinute < 10)
                    minute = "0" + String.valueOf(currentMinute);
                else
                    minute = String.valueOf(currentMinute);

            } else {
                AM_PM = "PM";
                currentHour = currentHour - 12;
                if (currentHour < 10)
                    hour = "0" + String.valueOf(currentHour);
                else if (currentHour == 0)
                    hour = "12";
                else
                    hour = String.valueOf(currentHour);


                if (currentMinute < 10)
                    minute = "0" + String.valueOf(currentMinute);
                else
                    minute = String.valueOf(currentMinute);
            }

            editText.setText(hour + ":" + minute + " " + AM_PM);
        }


    }
}
