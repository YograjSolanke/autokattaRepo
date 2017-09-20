package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by ak-003 on 21/3/17.
 */

public class MySearchResponse {
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

        private Date searchDateNew;
        private Date stopDateNew;

        public Date getSearchDateNew() {
            return searchDateNew;
        }

        public void setSearchDateNew(Date searchDateNew) {
            this.searchDateNew = searchDateNew;
        }

        public Date getStopDateNew() {
            return stopDateNew;
        }

        public void setStopDateNew(Date stopDateNew) {
            this.stopDateNew = stopDateNew;
        }

        @SerializedName("search_id")

        @Expose
        private int searchId;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("Brand")
        @Expose
        private String brand;
        @SerializedName("model")
        @Expose
        private String model;
        @SerializedName("rto_city")
        @Expose
        private String rtoCity;
        @SerializedName("location_city")
        @Expose
        private String locationCity;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("year_of_manufactur")
        @Expose
        private String yearOfManufactur;
        @SerializedName("searchdate")
        @Expose
        private String searchdate;
        @SerializedName("mysearchstatus")
        @Expose
        private String mysearchstatus;
        @SerializedName("stop")
        @Expose
        private String stop;
        @SerializedName("stopdate")
        @Expose
        private String stopdate;
        @SerializedName("searchstatus")
        @Expose
        private String searchstatus;
        @SerializedName("BuyerLeads")
        @Expose
        private String buyerLeads;

        public String getStocktype() {
            return stocktype;
        }

        public void setStocktype(String stocktype) {
            this.stocktype = stocktype;
        }

        @SerializedName("StockType")

        @Expose
        private String stocktype;

        public int getSearchId() {
            return searchId;
        }

        public void setSearchId(int searchId) {
            this.searchId = searchId;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getRtoCity() {
            return rtoCity;
        }

        public void setRtoCity(String rtoCity) {
            this.rtoCity = rtoCity;
        }

        public String getLocationCity() {
            return locationCity;
        }

        public void setLocationCity(String locationCity) {
            this.locationCity = locationCity;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getYearOfManufactur() {
            return yearOfManufactur;
        }

        public void setYearOfManufactur(String yearOfManufactur) {
            this.yearOfManufactur = yearOfManufactur;
        }

        public String getSearchdate() {
            return searchdate;
        }

        public void setSearchdate(String searchdate) {
            this.searchdate = searchdate;
        }

        public String getMysearchstatus() {
            return mysearchstatus;
        }

        public void setMysearchstatus(String mysearchstatus) {
            this.mysearchstatus = mysearchstatus;
        }

        public String getStop() {
            return stop;
        }

        public void setStop(String stop) {
            this.stop = stop;
        }

        public String getStopdate() {
            return stopdate;
        }

        public void setStopdate(String stopdate) {
            this.stopdate = stopdate;
        }

        public String getSearchstatus() {
            return searchstatus;
        }

        public void setSearchstatus(String searchstatus) {
            this.searchstatus = searchstatus;
        }

        public String getBuyerLeads() {
            return buyerLeads;
        }

        public void setBuyerLeads(String buyerLeads) {
            this.buyerLeads = buyerLeads;
        }
    }
}
