package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 24/3/17.
 */

public class PriceSuggestionResponse {
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

        @SerializedName("priceSuggestion")
        @Expose
        private String priceSuggestion;

        public String getPriceSuggestion() {
            return priceSuggestion;
        }

        public void setPriceSuggestion(String priceSuggestion) {
            this.priceSuggestion = priceSuggestion;
        }

    }
}
