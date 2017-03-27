package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 27/3/17.
 */

public class GetCompaniesResponse {

    @SerializedName("Success")
    @Expose
    private List<Success> success = null;


    public List<Success> getSuccess() {
        return success;
    }

    public void setSuccess(List<Success> success) {
        this.success = success;
    }


public class Success {

    @SerializedName("compid")
    @Expose
    private String compid;
    @SerializedName("companyName")
    @Expose
    private String companyName;

    public String getCompid() {
        return compid;
    }

    public void setCompid(String compid) {
        this.compid = compid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
}
