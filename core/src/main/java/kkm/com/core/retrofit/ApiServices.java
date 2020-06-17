package kkm.com.core.retrofit;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import kkm.com.core.model.request.RequestAddCart;
import kkm.com.core.model.request.RequestAmazonAdd;
import kkm.com.core.model.request.RequestChangePassword;
import kkm.com.core.model.request.RequestCreateLoginPIN;
import kkm.com.core.model.request.RequestCustomerVerification;
import kkm.com.core.model.request.RequestForgotPass;
import kkm.com.core.model.request.RequestProducList;
import kkm.com.core.model.request.RequestSearch;
import kkm.com.core.model.request.RequestSendQuery;
import kkm.com.core.model.request.RequestTSLevelView;
import kkm.com.core.model.request.RequestTeamStatus;
import kkm.com.core.model.request.RequestTicket;
import kkm.com.core.model.request.RequestTrainingBookings;
import kkm.com.core.model.request.RequestUnclearedBalance;
import kkm.com.core.model.request.RequestWallet;
import kkm.com.core.model.request.ResponseTsLevelSearch;
import kkm.com.core.model.request.bookTrainingTicket.RequestBookTrainingTicket;
import kkm.com.core.model.request.m2p.RequestPayAmount;
import kkm.com.core.model.request.policybazar.RequestSPORegistration;
import kkm.com.core.model.request.profilemlm.RequestBankUpdate;
import kkm.com.core.model.request.profilemlm.RequestInsuranceUpdate;
import kkm.com.core.model.request.profilemlm.RequestPersonalUpdate;
import kkm.com.core.model.request.profilemlm.RequestProfileUpdate;
import kkm.com.core.model.request.savePolicyBazarData.RequestSavePolicyBazarData;
import kkm.com.core.model.request.ticketAllotment.RequestTrainingAllotment;
import kkm.com.core.model.request.ticketAllotment.ResponseTicketAllotment;
import kkm.com.core.model.request.utility.RequestAddWallet;
import kkm.com.core.model.request.utility.RequestBeneDelete;
import kkm.com.core.model.request.utility.RequestBeneficiaryRegistration;
import kkm.com.core.model.request.utility.RequestFundTransfer;
import kkm.com.core.model.request.utility.RequestReitterOtpValidate;
import kkm.com.core.model.request.utility.RequestRemitterRegister;
import kkm.com.core.model.request.utility.ResponseAddBeneficiaryDetails;
import kkm.com.core.model.response.AddNewAddressResponse;
import kkm.com.core.model.response.CreateOrderResponse;
import kkm.com.core.model.response.DeleteAddressResponse;
import kkm.com.core.model.response.GetAddressListResponse;
import kkm.com.core.model.response.ResponseAmazonAdd;
import kkm.com.core.model.response.ResponseCategory;
import kkm.com.core.model.response.ResponseForgotPass;
import kkm.com.core.model.response.ResponseLogin;
import kkm.com.core.model.response.ResponseOrderList;
import kkm.com.core.model.response.ResponsePaymentStatus;
import kkm.com.core.model.response.ResponsePincodeDetail;
import kkm.com.core.model.response.ResponsePolicyBazarData;
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
import kkm.com.core.model.response.bookTrainingTicket.ResponseBookTrainingTicket;
import kkm.com.core.model.response.cart.ResponseCartList;
import kkm.com.core.model.response.getMyTraining.ResponseMyTrainings;
import kkm.com.core.model.response.incentive.ResponseGetLevelWiseIncome;
import kkm.com.core.model.response.incentive.ResponseLevelIncome;
import kkm.com.core.model.response.memberNameFromMobile.ResponseName;
import kkm.com.core.model.response.mlmDashboardNew.ResponseNewMLMDashboard;
import kkm.com.core.model.response.notification.ResponseNotification;
import kkm.com.core.model.response.profile.ResponseProfileUpdate;
import kkm.com.core.model.response.profile.ResponseViewProfile;
import kkm.com.core.model.response.referal.ResponseReferalCode;
import kkm.com.core.model.response.register.ResponseDistrict;
import kkm.com.core.model.response.register.ResponsePincode;
import kkm.com.core.model.response.register.ResponseState;
import kkm.com.core.model.response.searchResponse.ResponseSearchItems;
import kkm.com.core.model.response.shopping.ResponseINRDeailsOrders;
import kkm.com.core.model.response.shopping.ResponseMainCategory;
import kkm.com.core.model.response.shopping.ResponseShoppingOffers;
import kkm.com.core.model.response.team.ResponseDirect;
import kkm.com.core.model.response.team.ResponseDownline;
import kkm.com.core.model.response.team.ResponseTSLevelView;
import kkm.com.core.model.response.team.ResponseTeamStatus;
import kkm.com.core.model.response.trainingBookings.ResponseTrainingBookings;
import kkm.com.core.model.response.transportMode.ResponseTransportModes;
import kkm.com.core.model.response.unclearLedger.ResponseUnclearBalance;
import kkm.com.core.model.response.updateProfile.ResponseGetProfile;
import kkm.com.core.model.response.utility.ResponseBenficiarylist;
import kkm.com.core.model.response.wallet.ResponseBankDetails;
import kkm.com.core.model.response.wallet.ResponseCompanyName;
import kkm.com.core.model.response.wallet.ResponseFundLog;
import kkm.com.core.model.response.wallet.ResponseHoldWallet;
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
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiServices {

    @POST("PolicyBazaarInsert")
    Call<ResponsePolicyBazarData> savePolicyBazarData(@Body RequestSavePolicyBazarData requestSavePolicyBazarData);

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

    @POST("LoginMember")
    Call<JsonObject> getLoginEnc(@Body JsonObject object);

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
    Call<JsonObject> getAllTrasactions(@Body JsonObject object);

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


    @POST("Downline")
    Call<ResponseDownline> getDownline(@Body JsonObject object);


    @POST("GetStateName")
    Call<ResponseState> getstate(@Body JsonObject object);

    @POST("District")
    Call<ResponseDistrict> getDistrict(@Body JsonObject object);

    @POST("Pincode")
    Call<ResponsePincode> getPincode(@Body JsonObject object);


    @POST("ChangePassword")
    Call<JsonObject> ChangePassword(@Body RequestChangePassword object);

    @GET
    Call<String> getOtp(@Url String url);

    @GET
    Call<ResponseINRDeailsOrders> getOtherOrders(@Url String url);

    @GET
    Call<JsonObject> getCardBalance(@Url String url);

    @GET
    Call<JsonObject> checkCustomerData(@Url String url);

    @GET
    Call<ResponseMyTrainings> getMyTrainingsCall(@Url String url);

    @POST("EventTransaction")
    Call<ResponseBookTrainingTicket> responseBookTrainingTicketCall(@Body RequestBookTrainingTicket requestBookTrainingTicket);


    @POST("GetCustomerReferalmobile")
    Call<ResponseReferalName> getReferalName(@Body JsonObject object);

    @POST("GetTeamStatusDetails")
    Call<ResponseTSLevelView> responseTsLevelViewCall(@Body RequestTSLevelView requestTSLevelView);

    @POST("GetTeamStatusDetailsWithOutPaging")
    Call<ResponseTSLevelView> GetTeamStatusDetailsWithOutPaging(@Body ResponseTsLevelSearch requestTSLevelView);

    @POST("TeamStatus")
    Call<ResponseTeamStatus> responseTeamStatusCall(@Body RequestTeamStatus requestTeamStatus);

    @POST("GetTransactionsAMT")
    Call<ResponseUnclearedBalance> responseUnclearedBalanceCall(@Body RequestUnclearedBalance requestUnclearedBalance);


    @POST("KitRequest")
    Call<JsonObject> responseKitCall(@Body JsonObject requestKit);

    @POST("TicketsSupport")
    Call<ResponseTicket> getTicketCall(@Body RequestTicket requestTicket);

    @POST("SupportSend")
    Call<ResponseSendQuery> getResponseSendQueryCall(@Body RequestSendQuery requestSendQuery);

    @POST("UserProfile")
    Call<ResponseViewProfile> getUserProfileMlm(@Body JsonObject object);

    @POST("register-user")
    Call<JsonObject> getRequestSpoRegistrationCall(@Body RequestSPORegistration object, @Header("Authorization") String userkey);

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
    Call<JsonObject> getbalanceAmount(@Body JsonObject amount);


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
    Call<JsonObject> getGiftCardCategories(@Body JsonObject categories);

    @POST("GiftCardCategoryID")
    Call<JsonObject> getGiftCoupons(@Body JsonObject coupons);

    @POST("ProductorBrandAPI")
    Call<JsonObject> getCouponsDetails(@Body JsonObject coupons);

    @POST("SpendAPI")
    Call<JsonObject> createGiftCard(@Body JsonObject card);

    // Theme Park API
    @POST("CategoryAPI")
    Call<JsonObject> getThemeParkCategories(@Body JsonObject requestThemePark);

    @POST("CategoryIDAPI")
    Call<JsonObject> getThemeParkLists(@Body JsonObject categories);

    @POST("ProductAvailability")
    Call<JsonObject> getThemeParkAvailability(@Body JsonObject availability);

    @POST("BookingAPI")
    Call<JsonObject> getThemeParkBooking(@Body JsonObject book);


    @POST("BeneficiaryDelete")
    Call<JsonObject> beneficiarydelete(@Body RequestBeneDelete categories);

    //    Mobile Recharge Prepaid, Postpaid, Datacard
    @POST("PrepaidRecharge")
    Call<JsonObject> getPrepaidRecharge(@Body JsonObject object);


    @POST("PostpaidRecharge")
    Call<JsonObject> getPostpaidRecharge(@Body JsonObject object);


    @POST("DataCard")
    Call<JsonObject> getDatacardRecharge(@Body JsonObject object);


    //    Jio Prepaid
    @POST("ForFetchPlanPrepaid")
    Call<JsonObject> getJioPlan(@Body JsonObject jsonObject);

    @POST("ForRechargeMobilePrepaid")
    Call<JsonObject> getJioPrepaidRecharge(@Body JsonObject object);

    //Dth Recharge
    @POST("DTH")
    Call<JsonObject> getDthRecharge(@Body JsonObject dth);


    // Recent Recharges
    @POST("GetRecentUsers")
    Call<JsonObject> getRecentRecharges(@Body JsonObject jsonObject);


    @POST("ProductExchange")
    Call<ResponseProductExchange> getProductExchange(@Body JsonObject object);

    @POST("ProductReturn")
    Call<ResponseProductReturn> getProductReturn(@Body JsonObject object);

    @POST("OrderTracking")
    Call<ResponseTrackOrder> getOrderTracking(@Body JsonObject object);

    @POST("ProductCancel")
    Call<ResponseProductCancel> getProductCancel(@Body JsonObject object);

    @POST("ElectricityBillPayment")
    Call<JsonObject> billPayment(@Body JsonObject object);

    @POST("GasBillPayment")
    Call<JsonObject> gasbillPayment(@Body JsonObject object);

    @POST("Insurance")
    Call<JsonObject> insurancebillPayment(@Body JsonObject object);

    @POST("BroadBandProvider")
    Call<JsonObject> broadBandBillPayment(@Body JsonObject object);

    @POST("WaterBillPayment")
    Call<JsonObject> waterBillPayment(@Body JsonObject object);

    @POST("GetAllProvider")
    Call<JsonObject> getAllProvider(@Body JsonObject object);

    @POST("GetAllProviderStateWise")
    Call<JsonObject> getElectricityProvider(@Body JsonObject object);


    @POST("CreatePin")
    Call<ResponseProfileUpdate> createLoginPIN(@Body RequestCreateLoginPIN pin);

    @POST("Search")
    Call<ResponseSearchItems> getSearchList(@Body RequestSearch search);

    @POST("GetReferalDetails")
    Call<ResponseReferalCode> getReferalNameFronCode(@Body JsonObject object);

    @POST("CollectPay")
    Call<JsonObject> getCollectPay(@Body JsonObject object);

    @GET
    @Headers({"Content-Type: application/json"})
    Call<ResponsePincodeDetail> getStateCity(@Url String url);

    @POST("GetRegionlist")
    Call<JsonObject> getRegion(@Body JsonObject region);

    @POST("GetProductListRegion2")
    Call<JsonObject> getProductListRegion(@Body JsonObject categories);

    @POST("GetProductList")
    Call<JsonObject> getGetProductList(@Body JsonObject categories);

    @POST("Booking")
    Call<JsonObject> getTataskyBooking(@Body JsonObject object);

    @POST("CompanyBankMasterSelect")
    Call<ResponseCompanyName> getCompanyList(@Body JsonObject object);

    @POST("PayoutLedgerSelect")
    Call<ResponseUnclearBalance> getUnclearLedger(@Body JsonObject object);

    @POST("GetNotificationsList")
    Call<ResponseNotification> getGetNotificationsList(@Body JsonObject object);

    @POST("ElectricityVerification")
    Call<JsonObject> getElectricityVerification(@Body JsonObject verification);

    @POST("WaterBillVerification")
    Call<JsonObject> getWaterBillVerification(@Body JsonObject verification);

    @POST("GasPaymentVerification")
    Call<JsonObject> getGasPaymentVerification(@Body JsonObject verification);

    @POST("BroadBandVerify")
    Call<JsonObject> getBroadBandVerify(@Body JsonObject verification);

    @POST("InsuranceVerify")
    Call<JsonObject> getInsuranceVerify(@Body JsonObject verification);

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
    Call<JsonObject> getM2pRegister(@Body JsonObject registration);

    @POST("GetCardDetails")
    Call<JsonObject> getCardDetails(@Body JsonObject details);

    @POST("PayAmount")
    Call<JsonObject> getAmountPaid(@Body RequestPayAmount amount);

//    @POST("CheckWalletFlag")
//    Call<JsonObject> getWalletFlag(@Body JsonObject object);

    @POST("CancelWalletRequest")
    Call<JsonObject> getCancelWalletRequest(@Body JsonObject amount);

    @GET
    Call<ResponseGetProfile> getProfile(@Url String url);

    @GET
    Call<ResponseName> getNameFromMobile(@Url String url);

    @POST("UpdatePersonalDetails")
    Call<JsonObject> getProfileUpdate(@Body RequestProfileUpdate update);

    @POST("PaymentAEPS")
    Call<JsonObject> paymentAeps(@Body JsonObject update);

    @POST("InsertAmazonlDetails")
    Call<ResponseAmazonAdd> addAmazonProduct(@Body RequestAmazonAdd add);

    @GET("GetTransactions")
    Call<JsonObject> gettransactions(@Query("EntityId") String id);

    @POST("MemberEventDetails")
    Call<ResponseTrainingBookings> getTrainingBookings(@Body RequestTrainingBookings bookings);

    @GET("GetTransportMode")
    Call<ResponseTransportModes> getTransportModes();

    @POST("CheckPincode")
    Call<JsonObject> checkPinCode(@Body JsonObject object);

    @POST("AssignTraining")
    Call<ResponseTicketAllotment> alotTickets(@Body RequestTrainingAllotment allotment);

    @GET
    Call<ResponseHoldWallet> getholdWalletDetail(@Url String url);

    @GET
    Call<JsonObject> getNotificationCount(@Url String url);

    @POST("SetCardPIN")
    Call<JsonObject> createCardPIN(@Body JsonObject create);

    @POST("GetCardCVV")
    Call<JsonObject> getCVVDetails(@Body JsonObject create);

    @POST("AddFund")
    Call<JsonObject> getAddFund(@Body JsonObject create);

    @GET
    Call<JsonObject> getSupportData(@Url String url);

    @GET
    Call<JsonObject> checkM2pUser(@Url String url);

    @GET
    Call<ResponseNewMLMDashboard> getDashboardDataNew(@Url String s);
}
