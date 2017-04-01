package autokatta.com;

import android.app.Application;

import java.util.List;

/**
 * Created by ak-003 on 31/3/17.
 */

public class AutokattaApplication extends Application {
    private List<String> name;

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getNumber() {
        return number;
    }

    public void setNumber(List<String> number) {
        this.number = number;
    }

    private List<String> number;


}
