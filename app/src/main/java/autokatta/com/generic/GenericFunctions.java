package autokatta.com.generic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ak-004 on 21/3/17.
 */

public class GenericFunctions {


    //email validation method defination

    public boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //function to valid birth date
    public Boolean getbirthdate(String dob) {
        Boolean flag = true;
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        String dateString = sdf.format(date);
        System.out.println("current date====" + dateString);

        String[] partc = dateString.split("-");
        String[] partu = dob.split("-");


        int currentyear = Integer.parseInt(partc[0]);
        int useryear = Integer.parseInt(partu[0]);

        if (currentyear - useryear > 8) {
            System.out.println("year checking");
            System.out.println("valid user ");

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
                //dobtext.setError("Minimum 8 year age required");
            } else if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) == 0) {

                System.out.println("Mothns checking");
                if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) > 0) {
                    System.out.println("day checking");
                    System.out.println("date checked valid user ");
                } else if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) <= 0) {
                    flag = false;
                    System.out.println("day checking");
                    System.out.println("date checked invalid user ");
                    //dobtext.setError("Minimum 8 year age required");
                }
            }

        }
        return flag;
    }

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


        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
        // Rotate Bitmap
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, o2.outWidth, o2.outHeight, matrix, true);


        return rotatedBitmap;

    }

    public Boolean startDateValidatioon(String startdate) {

        Boolean flag2 = true;
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String dateString = sdf.format(now);
        //String dateString = sdf.format(date);


        System.out.println("current date====" + dateString);
        // dobtext.getText();

        System.out.println("Result here==================================================");

        String[] partc = dateString.split("-");

        // dob = dobtext.getText().toString();

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

                //dobtext.setError("Minimum 8 year age required");
            } else if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) == 0) {

                System.out.println("Mothns checking");
                if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) > 0) {
                    flag2 = false;
                    System.out.println("day checking");
                    System.out.println("date checked invalid user ");
                } else if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) < 0) {

                    System.out.println("day checking");
                    System.out.println("date checked valid user ");
                    //dobtext.setError("Minimum 8 year age required");
                } else if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) == 0) {
                    // flag=false;
                    System.out.println("day checking");
                    System.out.println("date checked valid user ");
                    //dobtext.setError("Minimum 8 year age required");
                }
            }

        }
        return flag2;
    }

    public Boolean startDateEndDateValidation(String endDate, String startDate) {

        Boolean flag1 = true;

        String[] partc = endDate.split("-");
        String[] partu = startDate.split("-");


        int currentyear = Integer.parseInt(partc[0]);
        int useryear = Integer.parseInt(partu[0]);

        if (currentyear - useryear > 0) {
            System.out.println("year ");
//            System.out.println("valid date ");

        } else if (currentyear - useryear == 0) {

            System.out.println("year checking");
            if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) > 0) {
                System.out.println("valid date ");
            } else if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) < 0) {
                flag1 = false;
                System.out.println("Months checked invalid date ");
                //dobtext.setError("Minimum 8 year age required");
            } else if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) == 0) {

                if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) > 0) {
                    System.out.println("daty checked valid date ");
                } else if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) < 0) {
                    flag1 = false;
                    System.out.println("daty checked invalid date ");
                    //dobtext.setError("Minimum 8 year age required");
                }
            }

        }
        return flag1;
    }

    public Boolean startTimeEndTimeValidation(String startTime, String endTime) {

        Boolean flag = true;
        String[] startTime1 = startTime.split(":");
        String[] endTime1 = endTime.split(":");

        if (Integer.parseInt(startTime1[0]) > Integer.parseInt(endTime1[0])) {
            flag = false;
        } else if (Integer.parseInt(startTime1[0]) < Integer.parseInt(endTime1[0])) {
            flag = true;
        } else if (Integer.parseInt(startTime1[0]) == Integer.parseInt(endTime1[0])) {
            if ((Integer.parseInt(startTime1[1]) > Integer.parseInt(endTime1[1])) || (Integer.parseInt(startTime1[1]) == Integer.parseInt(endTime1[1]))) {
                flag = false;
            }
        }

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
        Time startTime = null, endTime = null;

        try {
            SimpleDateFormat ra = new SimpleDateFormat("hh:mm");
            Date yourDate = null;
            Date yourdate2 = null;
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
