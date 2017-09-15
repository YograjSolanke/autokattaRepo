package autokatta.com.request;

/**
 * Created by ak-001 on 15/9/17.
 */

public class ManualEnquiryRequestCount {
    public String vehicleId;
    public String serviceId;
    public String productId;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
