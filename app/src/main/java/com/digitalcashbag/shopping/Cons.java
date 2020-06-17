package com.digitalcashbag.shopping;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import kkm.com.core.model.response.GetAddressListItem;
import kkm.com.core.model.response.bill.AllProviderlistItem;
import kkm.com.core.model.response.bill.GetAllProviderStateWiseListItem;
import kkm.com.core.model.response.cart.CartItemListItem;
import kkm.com.core.model.response.jioPrepaid.ResponseJioBrowsePlanPrepaid;
import kkm.com.core.model.response.shopping.ShoppingOffersItem;
import kkm.com.core.model.response.tatasky.response.AddinfoItem;
import kkm.com.core.model.response.themeParkResponse.themeParkAvailabilityArray.ResponseAvailabilityArray;

public class Cons {
    public static List<GetAddressListItem> GetAddressList_arrylst=new ArrayList<>();
    public static List<CartItemListItem> cartItemList;

    public static List<ResponseAvailabilityArray> responseAvailabilityArrays;
    public static List<AllProviderlistItem> allProviderlistItemList;
    public static List<GetAllProviderStateWiseListItem> allElectricityProviderlist;
    public static List<ShoppingOffersItem> shoppingoffersitem;
    public static List<ResponseJioBrowsePlanPrepaid> responseJioBrowsePlanArray;
    public static List<AddinfoItem> addinfoItems;

    // TODO BILL PAYMENT TYPE.......
    public static final String ELECTRICITY_BILL_PAYMENT = "Electricity Bill Payment";
    public static final String GAS_BILL_PAYMENT = "Gas Bill Payment";
    public static final String WATER_BILL_PAYMENT = "Water Bill Payment";
    public static final String INSURANCE_BILL_PAYMENT = "Insurance";
    public static final String BROADBAND_BILL_PAYMENT = "Broad Band Provider";

    public static String calculateHmac(String payload, String secretKey) {
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(keySpec);
            byte[] result = mac.doFinal(payload.getBytes());
            return Base64.encodeToString(result, 0);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Exception hashing payload", e);
        }
    }

    public static String encryptMsg(String message, SecretKey secret)
            throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] cipherText = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeToString(cipherText, Base64.NO_WRAP);
    }

    public static String decryptMsg(String cipherText, SecretKey secret)
            throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        byte[] decode = Base64.decode(cipherText, Base64.NO_WRAP);
        String decryptString = new String(cipher.doFinal(decode), StandardCharsets.UTF_8);
        return decryptString;
    }
}

