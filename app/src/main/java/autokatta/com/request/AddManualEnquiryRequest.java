package autokatta.com.request;

/**
 * Created by ak-003 on 5/5/17.
 */

public class AddManualEnquiryRequest {

    private String myContact;
    private String custName;
    private String custContact;
    private String custAddress;
    private String custFullAddress;
    private String custInventoryType;
    private String custEnquiryStatus;
    private String discussion;
    private String nextFollowupDate;

    public AddManualEnquiryRequest(String myContact, String custName, String custContact, String custAddress, String custFullAddress,
                                   String custInventoryType, String custEnquiryStatus, String discussion,
                                   String nextFollowupDate) {
        this.myContact = myContact;
        this.custName = custName;
        this.custContact = custContact;
        this.custAddress = custAddress;
        this.custFullAddress = custFullAddress;
        this.custInventoryType = custInventoryType;
        this.custEnquiryStatus = custEnquiryStatus;
        this.discussion = discussion;
        this.nextFollowupDate = nextFollowupDate;
    }
}
