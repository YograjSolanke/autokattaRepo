package autokatta.com.response;

/**
 * Created by ak-003 on 4/11/17.
 */

public class ModelSuggestionsResponse {

    private int groupId;
    private int storeId;
    private int productId, serviceId;
    private int uploadVehicleID;
    private int layoutId;
    private String userContact;
    private String Location;
    private String Name;
    private String Image;
    private String VehicleBrand;
    private String VehicleModel;
    private String VehicleMfgYear;
    private String VehicleCategory;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getVehicleId() {
        return uploadVehicleID;
    }

    public void setVehicleId(int uploadVehicleID) {
        this.uploadVehicleID = uploadVehicleID;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int product_id) {
        this.productId = product_id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int service_id) {
        this.serviceId = service_id;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getVehicleBrand() {
        return VehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        VehicleBrand = vehicleBrand;
    }

    public String getVehicleModel() {
        return VehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        VehicleModel = vehicleModel;
    }

    public String getVehicleMfgYear() {
        return VehicleMfgYear;
    }

    public void setVehicleMfgYear(String vehicleMfgYear) {
        VehicleMfgYear = vehicleMfgYear;
    }

    public String getVehicleCategory() {
        return VehicleCategory;
    }

    public void setVehicleCategory(String vehicleCategory) {
        VehicleCategory = vehicleCategory;
    }
}
