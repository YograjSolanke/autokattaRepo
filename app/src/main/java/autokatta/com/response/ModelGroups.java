package autokatta.com.response;

/**
 * Created by ak-001 on 20/3/17.
 */

public class ModelGroups {

    private String id, title, image, adminVehicleCount;
    private Integer vehicleCount, groupCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
