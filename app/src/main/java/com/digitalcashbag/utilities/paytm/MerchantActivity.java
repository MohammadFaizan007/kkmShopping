//package com.digitalcashbag.utilities.paytm;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.digitalcashbag.R;
//import com.paytm.pgsdk.PaytmMerchant;
//import com.paytm.pgsdk.PaytmOrder;
//import com.paytm.pgsdk.PaytmPGService;
//import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//import java.util.TreeMap;
//
//public class MerchantActivity extends Activity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.merchantapp);
//        initOrderId();
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//    }
//
//    //This is to refresh the order id: Only for the Sample App's purpose.
//    @Override
//    protected void onStart() {
//        super.onStart();
//        initOrderId();
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//    }
//
//
//    private void initOrderId() {
//        Random r = new Random(System.currentTimeMillis());
//        String orderId = "ORDER" + (1 + r.nextInt(2)) * 10000
//                + r.nextInt(10000);
//        EditText orderIdEditText = (EditText) findViewById(R.id.order_id);
//        orderIdEditText.setText(orderId);
//    }
//
//    public void onStartTransaction(View view) {
//
//
////        CheckSum Generation
//
//        String merchantMid = "eoTQdf57993634271162";
//// Key in your staging and production MID available in your dashboard
//        String merchantKey = "swDNf2Hj!Tr8R&iG";
//// Key in your staging and production MID available in your dashboard
//        String orderId = "order1";
//        String channelId = "WAP";
//        String custId = "cust123";
//        String mobileNo = "8076574489";
//        String email = "kkmdmpl@gmail.com";
//        String txnAmount = "100.12";
//        String website = "WEBSTAGING";
//// This is the staging value. Production value is available in your dashboard
//        String industryTypeId = "Retail";
//// This is the staging value. Production value is available in your dashboard
//        String callbackUrl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=order1";
//        TreeMap<String, String> paytmParams = new TreeMap<String, String>();
//        paytmParams.put("MID",merchantMid);
//        paytmParams.put("ORDER_ID",orderId);
//        paytmParams.put("CHANNEL_ID",channelId);
//        paytmParams.put("CUST_ID",custId);
//        paytmParams.put("MOBILE_NO",mobileNo);
//        paytmParams.put("EMAIL",email);
//        paytmParams.put("TXN_AMOUNT",txnAmount);
//        paytmParams.put("WEBSITE",website);
//        paytmParams.put("INDUSTRY_TYPE_ID",industryTypeId);
//        paytmParams.put("CALLBACK_URL", callbackUrl);
////        String paytmChecksum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(merchantKey, paytmParams);
//
////        Order Creation
//
//
//        PaytmPGService Service = PaytmPGService.getStagingService();
//        HashMap<String, String> paramMap = new HashMap<String, String>();
//        paramMap.put("MID", "eoTQdf57993634271162");
//// Key in your staging and production MID available in your dashboard
//        paramMap.put("ORDER_ID", "order1");
//        paramMap.put("CUST_ID", "cust123");
//        paramMap.put("MOBILE_NO", "8076574489");
//        paramMap.put("EMAIL", "kkmdmpl@gmail.com");
//        paramMap.put("CHANNEL_ID", "WAP");
//        paramMap.put("TXN_AMOUNT", "100.12");
//        paramMap.put("WEBSITE", "WEBSTAGING");
//// This is the staging value. Production value is available in your dashboard
//        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
//// This is the staging value. Production value is available in your dashboard
//        paramMap.put("CALLBACK_URL", "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=order1");
////        paramMap.put("CHECKSUMHASH", paytmChecksum);
//        PaytmOrder Order = new PaytmOrder(paramMap);
//
//        PaytmMerchant Merchant = new PaytmMerchant(
//                "https://pguat.paytm.com/paytmchecksum/paytmCheckSumGenerator.jsp",
//                "https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp");
//
//        Service.initialize(Order, null);
//
//        Service.startPaymentTransaction(this, true, true,
//                new PaytmPaymentTransactionCallback() {
//                    @Override
//                    public void someUIErrorOccurred(String inErrorMessage) {
//                        // Some UI Error Occurred in Payment Gateway Activity.
//                        // // This may be due to initialization of views in
//                        // Payment Gateway Activity or may be due to //
//                        // initialization of webview. // Error Message details
//                        // the error occurred.
//                    }
//
//
//                    @Override
//                    public void onTransactionResponse(Bundle inResponse) {
//                        // After successful transaction this method gets called.
//                        // // Response bundle contains the merchant response
//                        // parameters.
//                        Log.d("LOG", "Payment Transaction is successful " + inResponse);
//                        Toast.makeText(getApplicationContext(), "Payment Transaction is successful ", Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void networkNotAvailable() { // If network is not
//                        // available, then this
//                        // method gets called.
//                    }
//
//                    @Override
//                    public void clientAuthenticationFailed(String inErrorMessage) {
//                        // This method gets called if client authentication
//                        // failed. // Failure may be due to following reasons //
//                        // 1. Server error or downtime.
//                        // 2. Server unable to
//                        // generate checksum or checksum response is not in
//                        // proper format.
//                        // 3. Server failed to authenticate
//                        // that client. That is value of payt_STATUS is 2. //
//                        // Error Message describes the reason for failure.
//                    }
//
//                    @Override
//                    public void onErrorLoadingWebPage(int iniErrorCode,
//                                                      String inErrorMessage, String inFailingUrl) {
//
//                    }
//
//                    // had to be added: NOTE
//                    @Override
//                    public void onBackPressedCancelTransaction() {
//                        // TODO Auto-generated method stub
//                    }
//
//                    @Override
//                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
//                        // This method gets called if transaction failed. //
//                        // Here in this case transaction is completed, but with
//                        // a failure. // Error Message describes the reason for
//                        // failure. // Response bundle contains the merchant
//                        // response parameters.
//                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
//                        Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
//                    }
//
//                });
//    }
//}