package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 13/7/17.
 */

public class LoanMelaAnalyticsResponse {

    @SerializedName("Success")
    @Expose
    private List<Success> success = null;
    @SerializedName("Error")
    @Expose
    private Object error;

    public List<Success> getSuccess() {
        return success;
    }

    public void setSuccess(List<Success> success) {
        this.success = success;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }


public class Success {

    @SerializedName("ReachedCount")
    @Expose
    private int reachedCount;
    @SerializedName("GoingCount")
    @Expose
    private int goingCount;
    @SerializedName("GoingStudent")
    @Expose
    private int goingStudent;
    @SerializedName("GoingSelfStudent")
    @Expose
    private int goingSelfStudent;
    @SerializedName("GoingEmployee")
    @Expose
    private int goingEmployee;
    @SerializedName("IgnoreCount")
    @Expose
    private int ignoreCount;
    @SerializedName("IgnoreStudent")
    @Expose
    private int ignoreStudent;
    @SerializedName("IgnoreSelfStudent")
    @Expose
    private int ignoreSelfStudent;
    @SerializedName("IgnoreEmployee")
    @Expose
    private int ignoreEmployee;

    public int getReachedCount() {
        return reachedCount;
    }

    public void setReachedCount(int reachedCount) {
        this.reachedCount = reachedCount;
    }

    public int getGoingCount() {
        return goingCount;
    }

    public void setGoingCount(int goingCount) {
        this.goingCount = goingCount;
    }

    public int getGoingStudent() {
        return goingStudent;
    }

    public void setGoingStudent(int goingStudent) {
        this.goingStudent = goingStudent;
    }

    public int getGoingSelfStudent() {
        return goingSelfStudent;
    }

    public void setGoingSelfStudent(int goingSelfStudent) {
        this.goingSelfStudent = goingSelfStudent;
    }

    public int getGoingEmployee() {
        return goingEmployee;
    }

    public void setGoingEmployee(int goingEmployee) {
        this.goingEmployee = goingEmployee;
    }

    public int getIgnoreCount() {
        return ignoreCount;
    }

    public void setIgnoreCount(int ignoreCount) {
        this.ignoreCount = ignoreCount;
    }

    public int getIgnoreStudent() {
        return ignoreStudent;
    }

    public void setIgnoreStudent(int ignoreStudent) {
        this.ignoreStudent = ignoreStudent;
    }

    public int getIgnoreSelfStudent() {
        return ignoreSelfStudent;
    }

    public void setIgnoreSelfStudent(int ignoreSelfStudent) {
        this.ignoreSelfStudent = ignoreSelfStudent;
    }

    public int getIgnoreEmployee() {
        return ignoreEmployee;
    }

    public void setIgnoreEmployee(int ignoreEmployee) {
        this.ignoreEmployee = ignoreEmployee;
    }

}

}
