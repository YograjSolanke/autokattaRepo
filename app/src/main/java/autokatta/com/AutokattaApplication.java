package autokatta.com;

import android.app.Application;

import java.util.List;

/**
 * Created by ak-003 on 31/3/17.
 */

public class AutokattaApplication extends Application {
    private static List<String> name;
    private static List<String> number;

    public static List<String> getName() {
        return name;
    }

    public static void setName(List<String> name) {
        AutokattaApplication.name = name;
    }

    public static List<String> getNumber() {
        return number;
    }

    public static void setNumber(List<String> number) {
        AutokattaApplication.number = number;
    }
}
