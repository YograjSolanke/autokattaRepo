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
    private Integer reachedCount;
    @SerializedName("GoingCount")
    @Expose
    private Integer goingCount;
    @SerializedName("GoingStudent")
    @Expose
    private Integer goingStudent;
    @SerializedName("GoingSelfStudent")
    @Expose
    private Integer goingSelfStudent;
    @SerializedName("GoingEmployee")
    @Expose
    private Integer goingEmployee;
    @SerializedName("IgnoreCount")
    @Expose
    private Integer ignoreCount;
    @SerializedName("IgnoreStudent")
    @Expose
    private Integer ignoreStudent;
    @SerializedName("IgnoreSelfStudent")
    @Expose
    private Integer ignoreSelfStudent;
    @SerializedName("IgnoreEmployee")
    @Expose
    private Integer ignoreEmployee;

    public Integer getReachedCount() {
        return reachedCount;
    }

    public void setReachedCount(Integer reachedCount) {
        this.reachedCount = reachedCount;
    }

    public Integer getGoingCount() {
        return goingCount;
    }

    public void setGoingCount(Integer goingCount) {
        this.goingCount = goingCount;
    }

    public Integer getGoingStudent() {
        return goingStudent;
    }

    public void setGoingStudent(Integer goingStudent) {
        this.goingStudent = goingStudent;
    }

    public Integer getGoingSelfStudent() {
        return goingSelfStudent;
    }

    public void setGoingSelfStudent(Integer goingSelfStudent) {
        this.goingSelfStudent = goingSelfStudent;
    }

    public Integer getGoingEmployee() {
        return goingEmployee;
    }

    public void setGoingEmployee(Integer goingEmployee) {
        this.goingEmployee = goingEmployee;
    }

    public Integer getIgnoreCount() {
        return ignoreCount;
    }

    public void setIgnoreCount(Integer ignoreCount) {
        this.ignoreCount = ignoreCount;
    }

    public Integer getIgnoreStudent() {
        return ignoreStudent;
    }

    public void setIgnoreStudent(Integer ignoreStudent) {
        this.ignoreStudent = ignoreStudent;
    }

    public Integer getIgnoreSelfStudent() {
        return ignoreSelfStudent;
    }

    public void setIgnoreSelfStudent(Integer ignoreSelfStudent) {
        this.ignoreSelfStudent = ignoreSelfStudent;
    }

    public Integer getIgnoreEmployee() {
        return ignoreEmployee;
    }

    public void setIgnoreEmployee(Integer ignoreEmployee) {
        this.ignoreEmployee = ignoreEmployee;
    }

}

}
