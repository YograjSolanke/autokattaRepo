package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 26/3/17.
 */

public class GetVehicleModelResponse {

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

        @SerializedName("model_id")
        @Expose
        private int modelId;
        @SerializedName("model_title")
        @Expose
        private String modelTitle;

        public int getModelId() {
            return modelId;
        }

        public void setModelId(int modelId) {
            this.modelId = modelId;
        }

        public String getModelTitle() {
            return modelTitle;
        }

        public void setModelTitle(String modelTitle) {
            this.modelTitle = modelTitle;
        }

    }
}
