package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 24/3/17.
 */

public class GetBrandModelVersionResponse {

    public class Brand {

        @SerializedName("brandid")
        @Expose
        private String brandid;
        @SerializedName("brandtitle")
        @Expose
        private String brandtitle;

        public String getBrandid() {
            return brandid;
        }

        public void setBrandid(String brandid) {
            this.brandid = brandid;
        }

        public String getBrandtitle() {
            return brandtitle;
        }

        public void setBrandtitle(String brandtitle) {
            this.brandtitle = brandtitle;
        }

    }


    public class Example {

        @SerializedName("Brand")
        @Expose
        private List<Brand> brand = null;
        @SerializedName("Model")
        @Expose
        private List<Object> model = null;
        @SerializedName("Version")
        @Expose
        private List<Object> version = null;

        public List<Brand> getBrand() {
            return brand;
        }

        public void setBrand(List<Brand> brand) {
            this.brand = brand;
        }

        public List<Object> getModel() {
            return model;
        }

        public void setModel(List<Object> model) {
            this.model = model;
        }

        public List<Object> getVersion() {
            return version;
        }

        public void setVersion(List<Object> version) {
            this.version = version;
        }

    }
}
