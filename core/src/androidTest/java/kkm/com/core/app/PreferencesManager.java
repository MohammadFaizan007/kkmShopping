package kkm.com.core.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Vivek on 17/9/18.
 */

public class PreferencesManager {
    //app login variables
    private static final String PREF_NAME = "com.cashbag";
    private static final String USERID = "user_id";
    private static final String LoginID = "login_id";

    private static final String REMITTER_ID = "remitter_id";
    private static final String PINCODE = "pincode";

    private static final String STATE = "state";
    private static final String CITY = "city";
    private static final String ADDRESS = "address";


    private static final String USERTYPE = "user_type";
    private static final String NAME = "name";
    private static final String DOB = "dob";
    private static final String DOB_IMAGE = "dob_image";
    private static final String LNAME = "lname";
    private static final String EMAIL = "email";
    private static final String MOBILE = "mobile";
    private static final String PROFILEPIC = "pic";
    private static final String CARTCOUNT = "cart_count";
    private static final String ADDRESSMODE = "addressmode";
    private static final String CATEGORY_NAME = "category_name";
    private static final String TRANSACTION_PASS = "transaction_password";
    private static final String FIRST_VISIT = "first_visit";
    private static final String FIRST_LOGIN_SKIP = "first_login_skip";
    private static final String WALLET_BALANCE = "wallet_balance";
    private static final String UNCLEARED_BALANCE = "uncleared_balance";
    private static final String isKycVerified = "isKycVerified";
    private static final String loginPin = "loginPin";
    private static final String lastLogin = "lastLogin";
    private static final String birthdayCounter = "birthdayCounter";

    private static final String InviteCode = "InviteCode";

    private static final String savedLOGINID = "savedLOGINID";
    private static final String savedPASSWORD = "savedPASSWORD";
    private static final String ANDROIDID = "android_id";
    private static final String DELIVERY_CHARGE = "delivery_charge";

    private static final String CLIENT_ID = "clientId";
    private static final String ENTITY_ID = "entityId";
    private static final String KIT_NO = "kitno";

    private static PreferencesManager sInstance;
    private final SharedPreferences mPref;

    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    //for fragment
    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    //for getting instance
    public static synchronized PreferencesManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
        return sInstance;
    }

    //    saved login
    public String getSavedLOGINID() {
        return mPref.getString(savedLOGINID, "");
    }

    public void setSavedLOGINID(String value) {
        mPref.edit().putString(savedLOGINID, value).apply();
    }

    //    invide code
    public String getInviteCode() {
        return mPref.getString(InviteCode, "");
    }

    public void setInviteCode(String value) {
        mPref.edit().putString(InviteCode, value).apply();
    }

    //    saved password
    public String getSavedPASSWORD() {
        return mPref.getString(savedPASSWORD, "");
    }

    public void setSavedPASSWORD(String value) {
        mPref.edit().putString(savedPASSWORD, value).apply();
    }

    //    Login PIN
    public String getLoginPin() {
        return mPref.getString(loginPin, "");
    }

    public void setLoginPin(String value) {
        mPref.edit().putString(loginPin, value).apply();
    }


    //  Last Login Date
    public String getLastLogin() {
        return mPref.getString(lastLogin, "");
    }

    public void setLastLogin(String value) {
        mPref.edit().putString(lastLogin, value).apply();
    }


    //    KYC VERIFIED
    public String getIsKycVerified() {
        return mPref.getString(isKycVerified, "");
    }

    public void setIsKycVerified(String value) {
        mPref.edit().putString(isKycVerified, value).apply();
    }


    public boolean clear() {
        return mPref.edit().clear().commit();
    }

    public String getTransactionPass() {
        return mPref.getString(TRANSACTION_PASS, "");
    }

    public void setTransactionPass(String value) {
        mPref.edit().putString(TRANSACTION_PASS, value).apply();
    }

    public boolean getfirst_visit() {
        return mPref.getBoolean(FIRST_VISIT, true);
    }

    public void setfirst_visit(boolean value) {
        mPref.edit().putBoolean(FIRST_VISIT, value).apply();
    }

    public boolean getloginSkip() {
        return mPref.getBoolean(FIRST_LOGIN_SKIP, true);
    }

    public void setloginSkip(boolean value) {
        mPref.edit().putBoolean(FIRST_LOGIN_SKIP, value).apply();
    }


    public String getREMITTER_ID() {
        return mPref.getString(REMITTER_ID, "");
    }

    public void setREMITTER_ID(String value) {
        mPref.edit().putString(REMITTER_ID, value).apply();
    }


    public void setAddress(String value) {
        mPref.edit().putString(ADDRESS, value).apply();
    }

    public String getAddress() {
        return mPref.getString(ADDRESS, "");
    }


    public void setCity(String value) {
        mPref.edit().putString(CITY, value).apply();
    }

    public String getCity() {
        return mPref.getString(CITY, "");
    }


    public String getSate() {
        return mPref.getString(STATE, "");
    }

    public void setState(String value) {
        mPref.edit().putString(STATE, value).apply();
    }

    public String getPINCODE() {
        return mPref.getString(PINCODE, "");
    }

    public void setPINCODE(String value) {
        mPref.edit().putString(PINCODE, value).apply();
    }

    public Float getWALLET_BALANCE() {
        return mPref.getFloat(WALLET_BALANCE, 0f);
    }

    public void setWALLET_BALANCE(Float value) {
        mPref.edit().putFloat(WALLET_BALANCE, value).apply();
    }


    public String getUNCLEARED_BALANCE() {
        return mPref.getString(UNCLEARED_BALANCE, "");
    }

    public void setUNCLEARED_BALANCE(String value) {
        mPref.edit().putString(UNCLEARED_BALANCE, value).apply();
    }


    public String getLoginID() {
        return mPref.getString(LoginID, "");
    }

    public void setLoginID(String value) {
        mPref.edit().putString(LoginID, value).apply();
    }

    public String getUSERID() {
        return mPref.getString(USERID, "");
    }

    public void setUSERID(String value) {
        mPref.edit().putString(USERID, value).apply();
    }

    public String getUSERTYPE() {
        return mPref.getString(USERTYPE, "");
    }

    public void setUSERTYPE(String value) {
        mPref.edit().putString(USERTYPE, value).apply();
    }

    public String getNAME() {
        return mPref.getString(NAME, "");
    }

    public void setNAME(String value) {
        mPref.edit().putString(NAME, value).apply();
    }

    public String getDOB() {
        return mPref.getString(DOB, "");
    }

    public void setDOB(String value) {
        mPref.edit().putString(DOB, value).apply();
    }

    public String getANDROIDID() {
        return mPref.getString(ANDROIDID, "");
    }

    public void setANDROIDID(String value) {
        mPref.edit().putString(ANDROIDID, value).apply();
    }


    public int getCounter() {
        return mPref.getInt(birthdayCounter, 0);
    }

    public void setcounter(int value) {
        mPref.edit().putInt(birthdayCounter, value).apply();
    }

    public String getDOB_IMAGE() {
        return mPref.getString(DOB_IMAGE, "");
    }

    public void setDOB_IMAGE(String value) {
        mPref.edit().putString(DOB_IMAGE, value).apply();
    }

    public String getENTITY_ID() {
        return mPref.getString(ENTITY_ID, "");
    }

    public void setENTITY_ID(String value) {
        mPref.edit().putString(ENTITY_ID, value).apply();
    }

    public String getKIT_NO() {
        return mPref.getString(KIT_NO, "");
    }

    public void setKIT_NO(String value) {
        mPref.edit().putString(KIT_NO, value).apply();
    }

    public String getCLIENT_ID() {
        return mPref.getString(CLIENT_ID, "");
    }

    public void setCLIENT_ID(String value) {
        mPref.edit().putString(CLIENT_ID, value).apply();
    }

    public String getLNAME() {
        return mPref.getString(LNAME, "");
    }

    public void setLNAME(String value) {
        mPref.edit().putString(LNAME, value).apply();
    }

    public String getEMAIL() {
        return mPref.getString(EMAIL, "");
    }

    public void setEMAIL(String value) {
        mPref.edit().putString(EMAIL, value).apply();
    }

    public String getMOBILE() {
        return mPref.getString(MOBILE, "");
    }

    public void setMOBILE(String value) {
        mPref.edit().putString(MOBILE, value).apply();
    }

    public String getPROFILEPIC() {
        return mPref.getString(PROFILEPIC, "");
    }

    public void setPROFILEPIC(String value) {
        mPref.edit().putString(PROFILEPIC, value).apply();
    }

    public int getCartCount() {
        return mPref.getInt(CARTCOUNT, 0);
    }

    public void setCartCount(int value) {
        mPref.edit().putInt(CARTCOUNT, value).apply();
    }

    public String getAddressMode() {
        return mPref.getString(ADDRESSMODE, "");
    }

    public void setAddressMode(String value) {
        mPref.edit().putString(ADDRESSMODE, value).apply();
    }

    public String getCategoryName() {
        return mPref.getString(CATEGORY_NAME, "");
    }

    public void setCategoryName(String value) {
        mPref.edit().putString(CATEGORY_NAME, value).apply();
    }

    public String getDeliveryCharge() {
        return mPref.getString(DELIVERY_CHARGE, "");
    }

    public void setDeliveryCharge(String value) {
        mPref.edit().putString(DELIVERY_CHARGE, value).apply();
    }

}
