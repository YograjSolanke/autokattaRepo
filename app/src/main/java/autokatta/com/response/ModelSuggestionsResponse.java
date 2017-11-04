package autokatta.com.response;

/**
 * Created by ak-003 on 4/11/17.
 */

public class ModelSuggestionsResponse {

    private int groupId;
    private int storeId;
    private int uploadVehicleID;
    private int layoutId;
    private String userContact;
    private String Location;
    private String Name;
    private String Image;

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
}
