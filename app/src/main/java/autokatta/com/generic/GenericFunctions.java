package autokatta.com.generic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ak-004 on 21/3/17.
 */

public class GenericFunctions {


    //email validation method defination
    public boolean isValidEmail(String email) {
        /*String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();*/

        /*String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        String EMAIL_PATTERN1 = "/^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$/";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();*/
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //function to valid birth date
    public Boolean getbirthdate(String dob) {
        Boolean flag = true;
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());
        String dateString = sdf.format(date);

        Date userDate;

        try {
            if (!dob.contains("-")) {
                userDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).parse(dob);
                dob = sdf.format(userDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] partc = dateString.split("-");
        String[] partu = dob.split("-");


        int currentyear = Integer.parseInt(partc[0]);
        int useryear = Integer.parseInt(partu[0]);


        if (currentyear - useryear > 8) {
            System.out.println("year checking");
            System.out.println("valid user ");

        } else if (currentyear < useryear) {
            flag = false;

        } else if (currentyear - useryear < 8) {

            flag = false;

            System.out.println("year checking");
            System.out.println("invalid user ");
        } else if (currentyear - useryear == 8) {

            System.out.println("year checking");
            if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) > 0) {
                System.out.println("Mothns checking");
                System.out.println("valid user ");
            } else if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) < 0) {
                flag = false;
                System.out.println("Mothns checking");
                System.out.println("Months checked invalid user ");
            } else if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) == 0) {

                System.out.println("Mothns checking");
                if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) > 0) {
                    System.out.println("day checking");
                    System.out.println("date checked valid user ");
                } else if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) <= 0) {
                    flag = false;
                    System.out.println("day checking");
                    System.out.println("date checked invalid user ");
                }
            }

        }
        return flag;
    }

    /* function for  bitmap */
    public Bitmap decodeFile(String filePath) {

        System.out.println("filePath: " + filePath);

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);
        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;
        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, o2);


        ExifInterface exif;
        Matrix matrix = new Matrix();
        try {
            exif = new ExifInterface(filePath);

            String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
            int rotationAngle = 0;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
            // Rotate Bitmap

            matrix.setRotate(rotationAngle, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return Bitmap.createBitmap(bitmap, 0, 0, o2.outWidth, o2.outHeight, matrix, true);

    }

    /* function for start date validation */
    public Boolean startDateValidatioon(String startdate) {

        Boolean flag2 = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date now = new Date(); //current Date
        String dateString = sdf.format(now); // current date formatted with above format

        Date userDate;
        try {
            if (!startdate.contains("-")) {
                userDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).parse(startdate);
                startdate = sdf.format(userDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String[] partc = dateString.split("-");
        String[] partu = startdate.split("-");


        int currentyear = Integer.parseInt(partc[0]);
        int useryear = Integer.parseInt(partu[0]);

        if (currentyear - useryear > 0) {
            flag2 = false;
            System.out.println("year checking");
            System.out.println("invalid user ");

        } else if (currentyear - useryear < 0) {
            System.out.println("year checking");
            System.out.println("valid user ");

        } else if (currentyear - useryear == 0) {

            System.out.println("year checking");
            if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) > 0) {

                flag2 = false;
                System.out.println("Mothns checking");
                System.out.println("Months checked invalid user ");

            } else if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) < 0) {

                System.out.println("Mothns checking");
                System.out.println("valid user ");

            } else if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) == 0) {

                System.out.println("Mothns checking");
                if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) > 0) {
                    flag2 = false;
                    System.out.println("day checking");
                    System.out.println("date checked invalid user ");
                } else if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) < 0) {

                    System.out.println("day checking");
                    System.out.println("date checked valid user ");

                } else if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) == 0) {
                    System.out.println("day checking");
                    System.out.println("date checked valid user ");
                }
            }

        }
        return flag2;
    }

    /* function for start date and end date validation */
    public Boolean startDateEndDateValidation(String endDate, String startDate) {

        Boolean flag1 = true;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        /*Date userDateEnd = new Date();
        Date userDateStart = new Date();
        try {
            userDateEnd = sdf.parse(endDate);
            userDateStart = sdf.parse(startDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        endDate = sdf.format(userDateEnd);
        startDate = sdf.format(userDateStart);*/

        Date userDateEnd, userDateStart;
        try {

            if (!endDate.contains("-")) {
                userDateEnd = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).parse(endDate);
                endDate = sdf.format(userDateEnd);
            }

            if (!startDate.contains("-")) {
                userDateStart = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).parse(startDate);
                startDate = sdf.format(userDateStart);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] partc = endDate.split("-");
        String[] partu = startDate.split("-");


        int currentyear = Integer.parseInt(partc[0]);
        int useryear = Integer.parseInt(partu[0]);

        if (currentyear - useryear > 0) {
            System.out.println("year ");

        } else if (currentyear - useryear == 0) {

            System.out.println("year checking");
            if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) > 0) {
                System.out.println("valid date ");
            } else if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) < 0) {
                flag1 = false;
                System.out.println("Months checked invalid date ");

            } else if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) == 0) {

                if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) > 0) {
                    System.out.println("daty checked valid date ");
                } else if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) < 0) {
                    flag1 = false;
                    System.out.println("daty checked invalid date ");
                } else if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) == 0) {
                    Log.i("Date", "Equal");
                }
            }

        }
        return flag1;
    }

    /* function for start time and end time validation */
    public Boolean startTimeEndTimeValidation(String startTime, String endTime) {

        Boolean flag;

        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        Date starttm = null;
        Date endtm = null;
        try {
            starttm = parseFormat.parse(startTime);
            endtm = parseFormat.parse(endTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String sttime = displayFormat.format(starttm);
        String edTime = displayFormat.format(endtm);

        if (sttime.compareTo(edTime) > 0)
            flag = false;
        else if (sttime.compareTo(edTime) == 0)
            flag = false;
        else
            flag = true;

        return flag;
    }


    public String getTimeFormat(int selectedHour, int selectedMinute, int selectedSecond) {

        String hour = "", minute = "", second = "";
        if (selectedHour < 10)
            hour = "0" + String.valueOf(selectedHour);
        else
            hour = String.valueOf(selectedHour);

        if (selectedMinute < 10)
            minute = "0" + String.valueOf(selectedMinute);
        else
            minute = String.valueOf(selectedMinute);

        if (selectedSecond < 10)
            second = "0" + String.valueOf(selectedSecond);
        else
            second = String.valueOf(selectedSecond);

        return hour + ":" + minute + ":" + second;
    }

    public Boolean compareTime(String st, String end) {

        Boolean flagtime = false;
        Time startTime, endTime;

        try {
            SimpleDateFormat ra = new SimpleDateFormat("hh:mm", Locale.getDefault());
            Date yourDate;
            Date yourdate2;

            yourDate = ra.parse(st);
            yourdate2 = ra.parse(end);


            startTime = new Time(yourDate.getTime());
            endTime = new Time(yourdate2.getTime());


            if (endTime.before(startTime)) {
                System.out.println("wrng time!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                flagtime = true;

            } else if (startTime.equals(endTime)) {
                flagtime = true;
            } else {
                flagtime = false;
                System.out.println("right time!!!!!!!!!!!!!!!!!!!!!");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flagtime;
    }

    public boolean isValidUrl(String url) {

        Pattern regex = Pattern.compile("^(WWW|www)\\.+[a-zA-Z0-9\\-\\.]+\\.(com|org|net|mil|edu|in|IN|COM|ORG|NET|MIL|EDU)$");
        Matcher matcher = regex.matcher(url);
        return matcher.matches();

    }

}
