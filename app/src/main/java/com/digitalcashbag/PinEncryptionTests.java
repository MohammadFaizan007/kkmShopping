package com.digitalcashbag;


import android.util.Base64;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class PinEncryptionTests {

    private static final String ZERO_PAD = "0000000000000000";

    private static final String PIN_PAD = "FFFFFFFFFFFFFF";

    public String encryptionPinBlock(String kitNumber, String cardpin) throws Exception {

        String encryptPinBlock = "";

        try {
//            EC2CEC2277BFD9F0102E8241B917C185
            encryptPinBlock = encryptPinBlock(kitNumber, cardpin, "89555EED0B07495D00A6C11789D40E1D");
            System.out.println("Encrypted PinBlock: " + encryptPinBlock);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptPinBlock;
    }

    public String encryptPinBlock(String cardNumber, String pin, String key)
            throws Exception {

        byte[] pinBlockByte = getPinBlock(cardNumber, pin);

        String pinBlock = getHexString(pinBlockByte);

        System.out.println("Clear PinBlock: " + pinBlock);

        Cipher cipher = Cipher.getInstance(("AES/ECB/PKCS5Padding"));
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedPinBlock = cipher.doFinal(pinBlock.getBytes(StandardCharsets.UTF_8));

        return Base64.encodeToString(encryptedPinBlock, Base64.NO_WRAP);


//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, secret);
//        byte[] cipherText = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
//        return Base64.encodeToString(cipherText, Base64.NO_WRAP);
    }

    /**
     * Takes a byte array as input and provides a Hex String representation
     *
     * @param input
     * @return
     */
    private static String getHexString(byte[] input) {
        StringBuilder strBuilder = new StringBuilder();
        for (byte hexByte : input) {
            int res = 0xFF & hexByte;
            String hexString = Integer.toHexString(res);
            if (hexString.length() == 1) {
                strBuilder.append(0);
            }
            strBuilder.append(hexString);
        }
        return strBuilder.toString().toUpperCase();
    }

    /**
     * Converts a Hex string representation to an int array
     *
     * @param input Every two character of the string is assumed to be
     * @return int array containing the Hex String input
     * @throws IllegalBlockSizeException
     */
    private static int[] getHexIntArray(String input) throws IllegalBlockSizeException {
        if (input.length() % 2 != 0) {
            throw new IllegalBlockSizeException("Invalid Hex String, Hex representation length is not a multiple of 2");
        }
        int[] resultHex = new int[input.length() / 2];
        for (int iCnt1 = 0; iCnt1 < input.length(); iCnt1++) {
            String byteString = input.substring(iCnt1, ++iCnt1 + 1);
            int hexOut = Integer.parseInt(byteString, 16);
            resultHex[iCnt1 / 2] = (hexOut & 0x000000ff);
        }
        return resultHex;
    }

    /**
     * Converts a Hex string representation to an byte array
     *
     * @param input Every two character of the string is assumed to be
     * @return byte array containing the Hex String input
     * @throws IllegalBlockSizeException
     */
    private static byte[] getHexByteArray(String input) throws IllegalBlockSizeException {

        int[] resultHex = getHexIntArray(input);
        byte[] returnBytes = new byte[resultHex.length];
        for (int cnt = 0; cnt < resultHex.length; cnt++) {
            returnBytes[cnt] = (byte) resultHex[cnt];
        }
        return returnBytes;
    }

    /**
     * Takes the Card number and Pin as input and generates the Pin Block Out of
     * it. First get the card padded (16 Char) which when converted to Hex gives
     * an array of 8 Get the Pin Padded (16 Char) which when converted to Hex
     * gives an array of 8 XOR the resulting arrays to get the pin block
     *
     * @param cardNumber
     * @param pin
     * @return
     * @throws IllegalBlockSizeException
     */
    private static byte[] getPinBlock(String cardNumber, String pin) throws IllegalBlockSizeException {
        int[] paddedPin = padPin(pin);
        int[] paddedCard = padCard(cardNumber);

        byte[] pinBlock = new byte[8];
        for (int cnt = 0; cnt < 8; cnt++) {
            pinBlock[cnt] = (byte) (paddedPin[cnt] ^ paddedCard[cnt]);
        }
        return pinBlock;
    }

    /**
     * Generates a 16 digit block, with following Components Two digit pin
     * length (left padded with zero if length less than 10) Pin Number Right
     * padded with F to make it 16 char long. FOr example for a 5 digit Pin
     * 12345 the outout would be 0512 345F FFFF FFFF
     *
     * @param pin
     * @return
     * @throws IllegalBlockSizeException
     */
    private static int[] padPin(String pin) throws IllegalBlockSizeException {
        String pinBlockString = "0" + pin.length() + pin + PIN_PAD;
        pinBlockString = pinBlockString.substring(0, 16);
        return getHexIntArray(pinBlockString);

    }

    /**
     * Using the Card Number it generates a 16-digit block with 4 zeroes and and
     * the 12 right most digits of the card number, excluding the check digit
     * (which is the last digit of the card number. For Example for a Card 5259
     * 5134 8115 5074 4 Will be the check digit Right most 12 digits would be
     * 951348115507 Hence the output would be 0000 9513 4811 5507
     *
     * @param cardNumber
     * @return
     * @throws IllegalBlockSizeException
     */
    private static int[] padCard(String cardNumber) throws IllegalBlockSizeException {
        cardNumber = ZERO_PAD + cardNumber;
        int cardNumberLength = cardNumber.length();
        int beginIndex = cardNumberLength - 16;
        String acctNumber = cardNumber.substring(beginIndex, cardNumberLength);
        return getHexIntArray(acctNumber);
    }

    public void main(String[] args) throws Exception {
        encryptionPinBlock("930000001", "9999");
    }
}
