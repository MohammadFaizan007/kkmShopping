package kkm.com.core.retrofit;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import kkm.com.core.model.request.RequestAddCart;
import kkm.com.core.model.request.RequestChangePassword;
import kkm.com.core.model.request.RequestCreateLoginPIN;
import kkm.com.core.model.request.RequestCustomerVerification;
import kkm.com.core.model.request.RequestForgotPass;
import kkm.com.core.model.request.m2p.RequestKit;
import kkm.com.core.model.request.RequestProducList;
import kkm.com.core.model.request.RequestSearch;
import kkm.com.core.model.request.RequestSendQuery;
import kkm.com.core.model.request.RequestTSLevelView;
import kkm.com.core.model.request.RequestTeamStatus;
import kkm.com.core.model.request.RequestTicket;
import kkm.com.core.model.request.RequestUnclearedBalance;
import kkm.com.core.model.request.RequestWallet;
import kkm.com.core.model.request.billpayment.RequestBillPayment;
import kkm.com.core.model.request.billpayment.RequestBroadBandVerification;
import kkm.com.core.model.request.billpayment.RequestCollectPay;
import kkm.com.core.model.request.billpayment.RequestElectricityVerification;
import kkm.com.core.model.request.billpayment.RequestGasVerification;
import kkm.com.core.model.request.billpayment.RequestInsuranceVerify;
import kkm.com.core.model.request.billpayment.RequestWaterVerification;
import kkm.com.core.model.request.giftCardRequest.RequestCouponsDetails;
import kkm.com.core.model.request.giftCardRequest.RequestCreateGiftCard;
import kkm.com.core.model.request.giftCardRequest.RequestGiftCardCategories;
import kkm.com.core.model.request.giftCardRequest.RequestGiftCoupons;
import kkm.com.core.model.request.m2p.RequestM2PRegistration;
import kkm.com.core.model.request.profilemlm.RequestBankUpdate;
import kkm.com.core.model.request.profilemlm.RequestInsuranceUpdate;
import kkm.com.core.model.request.profilemlm.RequestPersonalUpdate;
import kkm.com.core.model.request.themeParkRequests.RequestProductAvailability;
import kkm.com.core.model.request.themeParkRequests.RequestThemePark;
import kkm.com.core.model.request.themeParkRequests.RequestThemeParkBook;
import kkm.com.core.model.request.themeParkRequests.RequestThemeParkLists;
import kkm.com.core.model.request.utility.RequestAddWallet;
import kkm.com.core.model.request.utility.RequestBalanceAmount;
import kkm.com.core.model.request.utility.RequestBeneDelete;
import kkm.com.core.model.request.utility.RequestBeneficiaryRegistration;
import kkm.com.core.model.request.utility.RequestFundTransfer;
import kkm.com.core.model.request.utility.RequestReitterOtpValidate;
import kkm.com.core.model.request.utility.RequestRemitterRegister;
import kkm.com.core.model.request.utility.ResponseAddBeneficiaryDetails;
import kkm.com.core.model.request.utility.response.ResponseBalanceAmount;
import kkm.com.core.model.response.AddNewAddressResponse;
import kkm.com.core.model.response.CreateOrderResponse;
import kkm.com.core.model.response.DeleteAddressResponse;
import kkm.com.core.model.response.GetAddressListResponse;
import kkm.com.core.model.response.ResponseCategory;
import kkm.com.core.model.response.ResponseForgotPass;
import kkm.com.core.model.response.m2p.requestkit.ResponseKit;
import kkm.com.core.model.response.ResponseLogin;
import kkm.com.core.model.response.ResponseOrderList;
import kkm.com.core.model.response.ResponsePaymentStatus;
import kkm.com.core.model.response.ResponsePincodeDetail;
import kkm.com.core.model.response.ResponseProductCancel;
import kkm.com.core.model.response.ResponseProductDetails;
import kkm.com.core.model.response.ResponseProductExchange;
import kkm.com.core.model.response.ResponseProductList;
import kkm.com.core.model.response.ResponseProductReturn;
import kkm.com.core.model.response.ResponseReferalName;
import kkm.com.core.model.response.ResponseRegistration;
import kkm.com.core.model.response.ResponseSendQuery;
import kkm.com.core.model.response.ResponseTicket;
import kkm.com.core.model.response.ResponseTrackOrder;
import kkm.com.core.model.response.ResponseUnclearedBalance;
import kkm.com.core.model.response.SetDefaultAddressResponse;
import kkm.com.core.model.response.bill.ResponseBillPayment;
import kkm.com.core.model.response.bill.ResponseBroadBandVerification;
import kkm.com.core.model.response.bill.ResponseCollectUpi;
import kkm.com.core.model.response.bill.ResponseElectricityVerification;
import kkm.com.core.model.response.bill.ResponseGasVerification;
import kkm.com.core.model.response.bill.ResponseInsuranceVerify;
import kkm.com.core.model.response.bill.ResponseProviderList;
import kkm.com.core.model.response.bill.ResponseWaterVerification;
import kkm.com.core.model.response.cart.ResponseCartList;
import kkm.com.core.model.response.cart.ResponseShippingCharge;
import kkm.com.core.model.response.giftCardResponse.ResponseGiftCardcategory;
import kkm.com.core.model.response.incentive.ResponseGetLevelWiseIncome;
import kkm.com.core.model.response.incentive.ResponseLevelIncome;
import kkm.com.core.model.response.jioPrepaid.ResponseJioPrepaidRecharge;
import kkm.com.core.model.response.m2p.registration.ResponseM2PRegistration;
import kkm.com.core.model.response.mlmDashboard.ResponseMlmDashboard;
import kkm.com.core.model.response.notification.ResponseNotification;
import kkm.com.core.model.response.profile.ResponseProfileUpdate;
import kkm.com.core.model.response.profile.ResponseViewProfile;
import kkm.com.core.model.response.referal.ResponseReferalCode;
import kkm.com.core.model.response.register.RequestRegister;
import kkm.com.core.model.response.register.ResponseDistrict;
import kkm.com.core.model.response.register.ResponsePincode;
import kkm.com.core.model.response.register.ResponseRegister;
import kkm.com.core.model.response.register.ResponseSponsorName;
import kkm.com.core.model.response.register.ResponseState;
import kkm.com.core.model.response.register.ResponseTehsil;
import kkm.com.core.model.response.searchResponse.ResponseSearchItems;
import kkm.com.core.model.response.shopping.ResponseINRDeailsOrders;
import kkm.com.core.model.response.shopping.ResponseMainCategory;
import kkm.com.core.model.response.shopping.ResponseShoppingOffers;
import kkm.com.core.model.response.tatasky.productlisttwo.RequestProductListTwo;
import kkm.com.core.model.response.tatasky.request.RequestRegion;
import kkm.com.core.model.response.tatasky.response.ResponseTataskyBooking;
import kkm.com.core.model.response.team.ResponseDirect;
import kkm.com.core.model.response.team.ResponseDownline;
import kkm.com.core.model.response.team.ResponseTSLevelView;
import kkm.com.core.model.response.team.ResponseTeamStatus;
import kkm.com.core.model.response.themeParkResponse.ResponseBookTemePark;
import kkm.com.core.model.response.themeParkResponse.themeParkDetails.ResponseThemeParkDetails;
import kkm.com.core.model.response.themeParkResponse.theme_park_maincategories.ResponseThemeParkMainCategories;
import kkm.com.core.model.response.trasactions.ResponseTransactions;
import kkm.com.core.model.response.unclearLedger.ResponseUnclearBalance;
import kkm.com.core.model.response.utility.ResponseBenficiarylist;
import kkm.com.core.model.response.utility.ResponseDatacardRecharge;
import kkm.com.core.model.response.utility.ResponseDthRecharge;
import kkm.com.core.model.response.utility.ResponsePostpaidRecharge;
import kkm.com.core.model.response.utility.ResponsePrepaidRecharge;
import kkm.com.core.model.response.utility.ResponseRecentRecharges;
import kkm.com.core.model.response.wallet.ResponseBankDetails;
import kkm.com.core.model.response.wallet.ResponseCompanyName;
import kkm.com.core.model.response.wallet.ResponseFundLog;
import kkm.com.core.model.response.wallet.ResponseIncentiveLedger;
import kkm.com.core.model.response.wallet.ResponseNewWalletRequest;
import kkm.com.core.model.response.wallet.ResponseWalletLedger;
import kkm.com.core.model.response.wallet.ResponseWalletRequest;
import kkm.com.core.model.response.wallet.ResponseWithdrawalDetails;
import kkm.com.core.model.response.wallet.ResponseWithdrawalRequest;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiServices {


    @POST("CategoryList")
    Call<ResponseMainCategory> getShoppingMainCategory(@Body JsonObject data);

    @POST("HomePageAPI ")
    Call<ResponseCategory> getHomePageAPI(@Body JsonObject data);

    @POST("ProductList")
    Call<ResponseProductList> getProductList(@Body RequestProducList data);

    @POST("GetProducts")
    Call<ResponseProductDetails> getProductDetails(@Body JsonObject data);

    @POST("CartItemList")
    Call<ResponseCartList> getCartItems(@Body JsonObject object);

    @POST("LoginMember")
    Call<ResponseLogin> getLogin(@Body JsonObject object);

    @POST("OtpVerifiedLogin")
    Call<ResponseLogin> getOtpVerifiedLogin(@Body JsonObject object);

    @POST("PayoutRequest")
    Call<ResponseWithdrawalRequest> sendWithdrawalRequest(@Body JsonObject object);

    @POST("PayoutRequestDetails")
    Call<ResponseWithdrawalDetails> getWithdrawalList(@Body JsonObject object);

    @POST("ForgotPassword")
    Call<ResponseForgotPass> getForgotPassword(@Body RequestForgotPass requestForgotPass);

    @POST("Registration")
    Call<ResponseRegistration> getRegistration(@Body JsonObject object);

    @POST("CartItemInsert")
    Call<JsonObject> addCartItem(@Body RequestAddCart object);

    @POST("CartItemDelete")
    Call<JsonObject> addCartItemDelete(@Body JsonObject object);

    @POST("CartItemDeleteAll")
    Call<JsonObject> addCartItemDeleteAll(@Body JsonObject object);

    @POST("ShippingCharges")
    Call<ResponseShippingCharge> getShippingCharge(@Body JsonObject object);

    @POST("GetAddressList")
    Call<GetAddressListResponse> getAddressList(@Body JsonObject object);

    @POST("AddNewAddress")
    Call<AddNewAddressResponse> addNewAddress(@Body JsonObject object);

    @POST("DeleteAddress")
    Call<DeleteAddressResponse> getDeleteAddress(@Body JsonObject object);

    @POST("CreateOrder")
    Call<CreateOrderResponse> getCreateOrder(@Body JsonObject object);

    @POST("GetDebitAmount")
    Call<JsonObject> getDebitWalletBalance(@Body JsonObject object);

    @POST("SetDefaultAddress")
    Call<SetDefaultAddressResponse> getSetDefaultAddress(@Body JsonObject object);

    @POST("OrderList")
    Call<ResponseOrderList> getOrderList(@Body JsonObject object);

    @POST("GetAllTransactions")
    Call<ResponseTransactions> getAllTrasactions(@Body JsonObject object);

    @POST("Directs")
    Call<ResponseDirect> getDirects(@Body JsonObject object);

    @POST("EWalletLedger")
    Call<ResponseWalletLedger> getEWalletLedger(@Body JsonObject object);

    @POST("EWalletRequest")
    Call<ResponseWalletRequest> getEWalletRequest(@Body JsonObject object);

    @POST("PayoutRequest")
    Call<ResponseIncentiveLedger> getIncentiveLedger(@Body JsonObject object);

    @POST("NewWalletRequest")
    Call<ResponseNewWalletRequest> getNewWalletRequest(@Body RequestWallet object);

    @POST("GetSponserName")
    Call<ResponseSponsorName> getGetSponserName(@Body JsonObject object);

    @POST("Downline")
    Call<ResponseDownline> getDownline(@Body JsonObject object);

    @POST("GetTahsilName")
    Call<ResponseTehsil> getTehsil(@Body JsonObject object);

    @POST("GetStateName")
    Call<ResponseState> getstate(@Body JsonObject object);

    @POST("District")
    Call<ResponseDistrict> getDistrict(@Body JsonObject object);

    @POST("Pincode")
    Call<ResponsePincode> getPincode(@Body JsonObject object);

    @POST("Register")
    Call<ResponseRegister> getRegister(@Body RequestRegister object);

    @POST("ChangePassword")
    Call<JsonObject> ChangePassword(@Body RequestChangePassword object);

    @GET
    Call<String> getOtp(@Url String url);

    @GET
    Call<ResponseINRDeailsOrders> getOtherOrders(@Url String url);

    @POST("GetCustomerReferalmobile")
    Call<ResponseReferalName> getReferalName(@Body JsonObject object);

    @POST("GetTeamStatusDetails")
    Call<ResponseTSLevelView> responseTsLevelViewCall(@Body RequestTSLevelView requestTSLevelView);

    @POST("TeamStatus")
    Call<ResponseTeamStatus> responseTeamStatusCall(@Body RequestTeamStatus requestTeamStatus);

    @POST("GetTransactionsAMT")
    Call<ResponseUnclearedBalance> responseUnclearedBalanceCall(@Body RequestUnclearedBalance requestUnclearedBalance);

    @POST("KitRequest")
    Call<ResponseKit> responseKitCall(@Body RequestKit requestKit);

    @POST("TicketsSupport")
    Call<ResponseTicket> getTicketCall(@Body RequestTicket requestTicket);

    @POST("SupportSend")
    Call<ResponseSendQuery> getResponseSendQueryCall(@Body RequestSendQuery requestSendQuery);

    @POST("UserProfile")
    Call<ResponseViewProfile> getUserProfileMlm(@Body JsonObject object);

    @Multipart
    @POST("MediaUpload/{customer_id}")
    Call<JsonObject> uploadImage(@Path(value = "customer_id", encoded = true) String customer_id, @Part("ClientsDocument") RequestBody key, @Part("Action") RequestBody imgType, @Part("Type") RequestBody type, @Part("UniqueNo") RequestBody uniqueNo, @Part() MultipartBody.Part file);

    @POST("BankDetailsUpdate")
    Call<ResponseProfileUpdate> getBankUpdate(@Body RequestBankUpdate update);

    @POST("UpdateMemberPersonalDetails")
    Call<ResponseProfileUpdate> getPersonalUpdate(@Body RequestPersonalUpdate update);

    @POST("UpdateInsuranceDetails")
    Call<ResponseProfileUpdate> getInsuranceUpdate(@Body RequestInsuranceUpdate update);

    @GET
    @Headers({"Content-Type: application/json"})
    Call<JsonObject> getBankName(@Url String url);

    @POST("Dashboard")
    Call<ResponseMlmDashboard> getMlmDashboardData(@Body JsonObject object);

    //  UTILITY API
    @POST("RemitterRegistration")
    Call<JsonArray> getRemitterRegistration(@Body RequestRemitterRegister object);

    @POST("RemitterRegistrationValidationOTP")
    Call<JsonArray> getRemitterRegistrationValidationOTP(@Body RequestReitterOtpValidate object);

    @POST("BeneficiaryRegistration")
    Call<JsonArray> getBeneficiaryRegistration(@Body RequestBeneficiaryRegistration object);


    @POST("FundTransfer")
    Call<JsonArray> getFundTransfer(@Body RequestFundTransfer transfer);

    @POST("AddWallet")
    Call<JsonObject> getAddWallet(@Body RequestAddWallet wallet);


    @POST("CustomerVerification")
    Call<JsonArray> getCustomerVerification(@Body RequestCustomerVerification requestCustomerVerification);

    @POST("GetBalanceAmount")
    Call<ResponseBalanceAmount> getbalanceAmount(@Body RequestBalanceAmount amount);


    @POST("GetBeneficiaryDetils")
    Call<List<ResponseBenficiarylist>> getBeneficiaryDetils(@Body JsonObject amount);

    @POST("FundTransferLog")
    Call<ResponseFundLog> getFundTransferLog(@Body JsonObject amount);


    @POST("AddBeneficiaryDetils")
    Call<JsonObject> getAddBeneficiaryDetils(@Body ResponseAddBeneficiaryDetails detils);

    @POST("ShoppingOffer")
    Call<ResponseShoppingOffers> getShoppingOffer(@Body JsonObject param);

    // Gift card API
    @POST("GiftCardCategoryAPI")
    Call<List<ResponseGiftCardcategory>> getGiftCardCategories(@Body RequestGiftCardCategories categories);

    @POST("GiftCardCategoryID")
    Call<JsonArray> getGiftCoupons(@Body RequestGiftCoupons coupons);

    @POST("ProductorBrandAPI")
    Call<JsonArray> getCouponsDetails(@Body RequestCouponsDetails coupons);

    @POST("SpendAPI")
    Call<JsonArray> createGiftCard(@Body RequestCreateGiftCard card);

    // Theme Park API
    @POST("CategoryAPI")
    Call<List<ResponseThemeParkMainCategories>> getThemeParkCategories(@Body RequestThemePark requestThemePark);

    @POST("CategoryIDAPI")
    Call<List<ResponseThemeParkDetails>> getThemeParkLists(@Body RequestThemeParkLists categories);

    @POST("ProductAvailability")
    Call<JsonArray> getThemeParkAvailability(@Body RequestProductAvailability availability);

    @POST("BookingAPI")
    Call<List<ResponseBookTemePark>> getThemeParkBooking(@Body RequestThemeParkBook book);


    @POST("BeneficiaryDelete")
    Call<JsonObject> beneficiarydelete(@Body RequestBeneDelete categories);

    //    Mobile Recharge Prepaid, Postpaid, Datacard
    @POST("PrepaidRecharge")
    Call<List<ResponsePrepaidRecharge>> getPrepaidRecharge(@Body JsonObject object);


    @POST("PostpaidRecharge")
    Call<List<ResponsePostpaidRecharge>> getPostpaidRecharge(@Body JsonObject object);


    @POST("DataCard")
    Call<List<ResponseDatacardRecharge>> getDatacardRecharge(@Body JsonObject object);


    //    Jio Prepaid
    @POST("ForFetchPlanPrepaid")
    Call<JsonArray> getJioPlan(@Body JsonObject jsonObject);

    @POST("ForRechargeMobilePrepaid")
    Call<List<ResponseJioPrepaidRecharge>> getJioPrepaidRecharge(@Body JsonObject object);

    //Dth Recharge
    @POST("DTH")
    Call<List<ResponseDthRecharge>> getDthRecharge(@Body JsonObject dth);


    // Recent Recharges
    @POST("GetRecentUsers")
    Call<ResponseRecentRecharges> getRecentRecharges(@Body JsonObject jsonObject);


    @POST("ProductExchange")
    Call<ResponseProductExchange> getProductExchange(@Body JsonObject object);

    @POST("ProductReturn")
    Call<ResponseProductReturn> getProductReturn(@Body JsonObject object);

    @POST("OrderTracking")
    Call<ResponseTrackOrder> getOrderTracking(@Body JsonObject object);

    @POST("ProductCancel")
    Call<ResponseProductCancel> getProductCancel(@Body JsonObject object);

    @POST("ElectricityBillPayment")
    Call<List<ResponseBillPayment>> billPayment(@Body RequestBillPayment object);

    @POST("GasBillPayment")
    Call<List<ResponseBillPayment>> gasbillPayment(@Body RequestBillPayment object);

    @POST("Insurance")
    Call<List<ResponseBillPayment>> insurancebillPayment(@Body RequestBillPayment object);

    @POST("BroadBandProvider")
    Call<List<ResponseBillPayment>> broadBandbillPayment(@Body RequestBillPayment object);

    @POST("WaterBillPayment")
    Call<List<ResponseBillPayment>> waterbillPayment(@Body RequestBillPayment object);

    @POST("GetAllProvider")
    Call<ResponseProviderList> getAllProvider(@Body JsonObject object);

    @POST("CreatePin")
    Call<ResponseProfileUpdate> createLoginPIN(@Body RequestCreateLoginPIN pin);

    @POST("Search")
    Call<ResponseSearchItems> getSearchList(@Body RequestSearch search);

    @POST("GetReferalDetails")
    Call<ResponseReferalCode> getReferalNameFronCode(@Body JsonObject object);

    @POST("CollectPay")
    Call<List<ResponseCollectUpi>> getCollectPay(@Body RequestCollectPay object);

    @GET
    @Headers({"Content-Type: application/json"})
    Call<ResponsePincodeDetail> getStateCity(@Url String url);

    @POST("GetRegionlist")
    Call<JsonArray> getRegion(@Body RequestRegion region);

    @POST("GetProductListRegion2")
    Call<JsonArray> getProductListRegion(@Body JsonObject categories);

    @POST("GetProductList")
    Call<JsonArray> getGetProductList(@Body RequestProductListTwo categories);

    @POST("Booking")
    Call<List<ResponseTataskyBooking>> getTataskyBooking(@Body JsonObject object);

    @POST("CompanyBankMasterSelect")
    Call<ResponseCompanyName> getCompanyList(@Body JsonObject object);

    @POST("PayoutLedgerSelect")
    Call<ResponseUnclearBalance> getUnclearLedger(@Body JsonObject object);

    @POST("GetNotificationsList")
    Call<ResponseNotification> getGetNotificationsList(@Body JsonObject object);

    @POST("ElectricityVerification")
    Call<List<ResponseElectricityVerification>> getElectricityVerification(@Body RequestElectricityVerification verification);

    @POST("WaterBillVerification")
    Call<List<ResponseWaterVerification>> getWaterBillVerification(@Body RequestWaterVerification verification);

    @POST("GasPaymentVerification")
    Call<List<ResponseGasVerification>> getGasPaymentVerification(@Body RequestGasVerification verification);

    @POST("BroadBandVerify")
    Call<List<ResponseBroadBandVerification>> getBroadBandVerify(@Body RequestBroadBandVerification verification);

    @POST("InsuranceVerify")
    Call<List<ResponseInsuranceVerify>> getInsuranceVerify(@Body RequestInsuranceVerify verification);

    @POST("GetPaymentTransactionDetails")
    Call<ResponsePaymentStatus> getPaymentStatus(@Body JsonObject verification);

    @POST("PayoutRequestCancel")
    Call<JsonObject> getPayoutCancelled(@Body JsonObject object);

    @POST("GetBankDetails")
    Call<ResponseBankDetails> getBankDetails(@Body JsonObject object);

    @POST("SaveUPIAddress")
    Call<JsonObject> saveUpi(@Body JsonObject object);

    @POST("GetUPIAddress")
    Call<JsonObject> getUpi(@Body JsonObject object);

    @POST("SignOutAPI")
    Call<JsonObject> getUserSignout(@Body JsonObject object);

    @POST("GetLevelWiseIncome")
    Call<ResponseGetLevelWiseIncome> GetLevelWiseIncome(@Body JsonObject object);

    @POST("GetLevelIncomeDetails")
    Call<ResponseLevelIncome> getLevelIncomeDetails(@Body JsonObject object);

    @POST("VerifyKycPin")
    Call<JsonObject> verifyKycPin(@Body JsonObject object);

    //    M2P
    @POST("CustomerRegistration")
    Call<ResponseM2PRegistration> getM2pRegister(@Body RequestM2PRegistration registration);

//    @POST("")
}
