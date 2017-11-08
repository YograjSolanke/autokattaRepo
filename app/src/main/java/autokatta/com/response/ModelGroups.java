package autokatta.com.response;

/**
 * Created by ak-001 on 20/3/17.
 */

public class ModelGroups {

    private String title, image, adminVehicleCount, groupPrivacyStatus;
    private int vehicleCount;
    private int groupCount;
    private int id;
    private int productcount;

    public int getProductcount() {
        return productcount;
    }

    public void setProductcount(int productcount) {
        this.productcount = productcount;
    }

    public int getServicecount() {
        return servicecount;
    }

    public void setServicecount(int servicecount) {
        this.servicecount = servicecount;
    }

    private int servicecount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }

    public String getAdminVehicleCount() {
        return adminVehicleCount;
    }

    public void setAdminVehicleCount(String adminVehicleCount) {
        this.adminVehicleCount = adminVehicleCount;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public String getGroupPrivacyStatus() {
        return groupPrivacyStatus;
    }

    public void setGroupPrivacyStatus(String groupPrivacyStatus) {
        this.groupPrivacyStatus = groupPrivacyStatus;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }
}
