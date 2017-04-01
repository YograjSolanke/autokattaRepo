package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 1/4/17.
 */

public class AdminExcelSheetResponse {
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

        @SerializedName("UserId")
        @Expose
        private String userId;
        @SerializedName("excelSheetName")
        @Expose
        private String excelSheetName;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getExcelSheetName() {
            return excelSheetName;
        }

        public void setExcelSheetName(String excelSheetName) {
            this.excelSheetName = excelSheetName;
        }

    }
}
