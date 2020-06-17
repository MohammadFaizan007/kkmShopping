package kkm.com.core.app;

import static kkm.com.core.BuildConfig.BUNDLE;

public class AppConfig {
    public static final String PAYLOAD_BUNDLE = BUNDLE;
    public static final String PAYLOAD_DEBIT_TYPE_SHOPPING = "Shopping";
    public static final String PAYLOAD_DEBIT_TYPE_RECHARGE = "Recharge";
    public static final String PAYLOAD_DEBIT_TYPE_TRANSFER = "Transfer";
    public static final String PAYLOAD_PAYMENT_MODE_WALLET = "Wallet";
    public static final String PAYLOAD_PAYMENT_MODE_ONLINE = "Online";
    public static final String PAYLOAD_TRANSACTION_STATUS_SUCCESS = "Success";
    public static final String PAYLOAD_ACCOUNT_RECHARGE_TWO = "2";
    public static final String SHARED_PREF = "ah_firebase";

    //DMT Type
    public static final String PAYLOAD_TYPE_FIVE_DMT_CUSTOMER_DETAILS = "5";                        //Type=5: To fetch customer details.
    public static final String PAYLOAD_TYPE_ZERO_DMT_CUSTOMER_REGISTRATION = "0";                   //Type=0: To register new customer
    public static final String PAYLOAD_TYPE_FOUR_DMT_BENEFICIARY_REGISTRATION = "4";                //Type=4: To add new beneficiary
    public static final String PAYLOAD_TYPE_TWO_DMT_BENEFICIARY_REGISTRATION_VALIDATE_OTP = "2";    //Type=2: To verify OTP for beneficiary verification.
    public static final String PAYLOAD_TYPE_NINE_DMT_BENEFICIARY_REGISTRATION_RESEND_OTP = "9";     //Type=9: To generate OTP for add and delete beneficiary
    public static final String PAYLOAD_TYPE_TEN_DMT_BENEFICIARY_ACCOUNT_VERIFICATION = "10";        //Type=10: To account validation
    public static final String PAYLOAD_TYPE_THREE_DMT_FUND_TRANSFER = "3";                          //Type=3: To transfer funds
    public static final String PAYLOAD_TYPE_SIX_DMT_BENEFICIARY_DELETE = "6";                       //Type=6: To delete beneficiary
    public static final String PAYLOAD_TYPE_TWENTY_THREE_DMT_BENEFICIARY_DELETE_VALIDATE_OTP = "23";    //Type=23: To verify OTP for delete beneficiary verification.
    public static final String PAYLOAD_TYPE_FIFTEEN_DMT_BANK_DETAILS = "15";                        //Type=15: To get Bank Details
    public static final String PAYLOAD_TYPE_TWENTY_TWO_DMT_REFUND_TRANSACTION = "22";               //Type=22: To get refund transaction. Please refer the details provided before Refund API logs.
    public static final String PAYLOAD_TYPE_TWENTY_FOUR_DMT_BENEFICIARY_UPDATE = "24";               //Type=24: To Update beneficiary
    public static final String PAYLOAD_TYPE_FOURTEEN_DMT_TRANSACTION_HISTORY = "14";                //Type=14: To get transaction history of funds transfer.

    //Tata sky Type
    public static final String PAYLOAD_TYPE_ONE_TATA_REGION_LIST = "1";         //Type=1: To get the region list
    public static final String PAYLOAD_TYPE_TWO_TATA_GET_PRODUCTS = "2";        //Type=2: To get the products based on region list
    public static final String PAYLOAD_TYPE_THREE_TATA_PRODUCT_DETAIL = "3";    //Type=3: To get specific product details based on product id
    public static final String PAYLOAD_TYPE_FIVE_TATA_TRANSACTION = "5";        //Type=5: To book the product

    //UPI Type
    public static final String PAYLOAD_TYPE_ONE_UPI_COLLECT_PAY = "1";      //Type=1: To collect pay
    public static final String PAYLOAD_TYPE_TWO_UPI_REFUND = "2";           //Type=2: for refund

    //Theme Park Type
    public static final String PAYLOAD_TYPE_ONE_THEME_PARK_CATEGORY_LIST = "1";         //Type=1: To fetch the list of travel categories
    public static final String PAYLOAD_TYPE_TWO_THEME_PARK_GET_CATEGORY_ID = "2";       //Type=2: To fetch particular category details based on category id
    public static final String PAYLOAD_TYPE_THREE_THEME_PARK_GET_PRODUCT_ID = "3";      //Type=3: To fetch particular product details from the product based on category id
    public static final String PAYLOAD_TYPE_FOUR_THEME_PARK_GET_PRODUCT_DETAIL = "4";   //Type=4: To fetch particular product details from the product based on category id (date mandatory)
    public static final String PAYLOAD_TYPE_FIVE_THEME_PARK_BOOKING_TRANSACTION = "5";  //Type=5: To book the tour through this api
    public static final String PAYLOAD_TYPE_SIX_THEME_PARK_TRANSACTION_STATUS = "6";    //Type=6: To check the booking status (Order ID is required)

    //Gift Card Type
    public static final String PAYLOAD_TYPE_ONE_GIFT_CARD_CATEGORY_LIST = "1";      //Type=1: To fetch the list of brand / product categories
    public static final String PAYLOAD_TYPE_TWO_GIFT_CARD_GET_CATEGORY_ID = "2";    //Type=2: To fetch the specific details of category
    public static final String PAYLOAD_TYPE_THREE_GIFT_CARD_GET_PRODUCT_ID = "3";   //Type=3: To fetch the specific details of brand / product
    public static final String PAYLOAD_TYPE_FIVE_GIFT_CARD_TRANSACTION = "5";       //Type=5: To place order for digital brands / products
    public static final String PAYLOAD_TYPE_SIX_GIFT_CARD_TRANSACTION_STATUS = "6"; //Type=6: To recheck the actual status of specific placed order of brand (Order ID is required)
    public static final String PAYLOAD_TYPE_SIX_GIFT_CARD_RESEND_API = "8";         //Type=8: To resend the gift voucher details on email if haven’t received to receiver (Order ID is required)
}
