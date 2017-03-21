package autokatta.com.generic;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ak-004 on 21/3/17.
 */

public class GenericFunctions {




    //email validation method defination

    public boolean isValidEmail(String email)
    {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //function to valid birth date
    public Boolean getbirthdate(String dob)
    {
        Boolean flag=true;
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        String dateString = sdf.format(date);
        System.out.println("current date====" + dateString);
        // dobtext.getText();


        System.out.println("Result here==================================================");

        String[] partc = dateString.split("-");

        // dob = dobtext.getText().toString();

        String[] partu = dob.split("-");


        int currentyear = Integer.parseInt(partc[0]);
        int useryear = Integer.parseInt(partu[0]);

        if (currentyear - useryear > 8) {
            System.out.println("year checking");
            System.out.println("valid user ");

        }
        else if (currentyear - useryear < 8) {

            flag=false;

            System.out.println("year checking");
            System.out.println("invalid user ");
            // dobtext.setError("Minimum 8 year age required");
        }
        else if (currentyear - useryear == 8) {

            System.out.println("year checking");
            if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) > 0) {
                System.out.println("Mothns checking");
                System.out.println("valid user ");
            }
            else if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) < 0) {
                flag=false;
                System.out.println("Mothns checking");
                System.out.println("Months checked invalid user ");
                //dobtext.setError("Minimum 8 year age required");
            }
            else if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) == 0) {

                System.out.println("Mothns checking");
                if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) > 0) {
                    System.out.println("day checking");
                    System.out.println("date checked valid user ");
                }
                else if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) <= 0) {
                    flag=false;
                    System.out.println("day checking");
                    System.out.println("date checked invalid user ");
                    //dobtext.setError("Minimum 8 year age required");
                }
            }

        }
        return  flag;
    }
}
