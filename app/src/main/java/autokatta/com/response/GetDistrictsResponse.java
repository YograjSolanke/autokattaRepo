package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-005 on 27/3/17.
 */

public class GetDistrictsResponse {

        @SerializedName("distId")
        @Expose
        private String distId;
        @SerializedName("distName")
        @Expose
        private String distName;

        public String getDistId() {
            return distId;
        }

        public void setDistId(String distId) {
            this.distId = distId;
        }

        public String getDistName() {
            return distName;
        }

        public void setDistName(String distName) {
            this.distName = distName;
        }


}
