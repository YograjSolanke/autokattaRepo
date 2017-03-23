package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-001 on 23/3/17.
 */

public class GetVehicleColor {
    @SerializedName("color_id")
    @Expose
    private String colorId;
    @SerializedName("color")
    @Expose
    private String color;

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
