package autokatta.com.response;

/**
 * Created by ak-001 on 20/3/17.
 */

public class ModelGroups {

    private String id, title, image, groupCount, adminVehicleCount;
    private int vehicleCount;

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

    public String getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(String groupCount) {
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
