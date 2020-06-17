package kkm.com.core.app;

import android.content.Context;
import android.widget.Toast;

public class CheckErrorCode {

    public String isValidTransaction(Context context, String code) {
        switch (code) {
            case "0": {
                return showMessage(context, "Successfully completed.");
            }
            case "1": {
                return showMessage(context, "Session with this number already exists.");
            }
            case "2": {
                return showMessage(context, "Invalid Dealer code.");
            }
            case "3": {
                return showMessage(context, "Invalid acceptance outlet code.");

            }
            case "4": {
                return showMessage(context, "Invalid Operator code.");

            }
            case "5": {
                return showMessage(context, "Invalid session code format.");

            }
            case "6": {
                return showMessage(context, "Invalid EDS.");

            }
            case "7": {
                return showMessage(context, "Invalid amount format or amount value is out of admissible range.");

            }
            case "8": {
                return showMessage(context, "Invalid phone number format.");

            }
            case "9": {
                return showMessage(context, "Invalid format of personal account number.");

            }
            case "10": {
                return showMessage(context, "Invalid request message format.");

            }
            case "11": {
                return showMessage(context, "Session with such a number does not exist.");

            }
            case "12": {
                return showMessage(context, "The request is made from an unregistered IP.");

            }
            case "13": {
                return showMessage(context, "The outlet is not registered with the Service Provider.");

            }
            case "15": {
                return showMessage(context, "Payments to the benefit of this operator are not supported by the system.");

            }
            case "17": {
                return showMessage(context, "The phone number does not match the previously entered number.");

            }
            case "18": {
                return showMessage(context, "The payment amount does not match the previously entered amount.");

            }
            case "19": {
                return showMessage(context, "The account number does not match the previously entered number.");

            }
            case "20": {
                return showMessage(context, "The payment is being completed.");

            }
            case "21": {
                return showMessage(context, "Not enough funds for effecting the payment.");

            }
            case "22": {
                return showMessage(context, "The payment has not been accepted. Funds transfer error.");

            }
            case "23": {
                return showMessage(context, "Invalid Mobile Number. Make sure your number belongs to this provider.");

            }
            case "24": {
                return showMessage(context, "Error of connection with the provider’s server or a routine break in CyberPlat®.");

            }
            case "25": {
                return showMessage(context, "Effecting of this type of payments is suspended.");

            }
            case "26": {
                return showMessage(context, "Payments of this Dealer are temporarily blocked");

            }
            case "27": {
                return showMessage(context, "Operations with this account are suspended");

            }
            case "30": {
                return showMessage(context, "General system failure.");

            }
            case "31": {
                return showMessage(context, "Exceeded number of simultaneously processed requests.");

            }
            case "32": {
                return showMessage(context, "Repeated payment within 60 minutes from the end of payment effecting process");

            }
            case "33": {
                return showMessage(context, "This denomination is applicable in <Flexi OR Special> category");

            }
            case "34": {
                return showMessage(context, "Transaction with such number could not be found.");

            }
            case "35": {
                return showMessage(context, "Payment status alteration error.");

            }
            case "36": {
                return showMessage(context, "Invalid payment status.");

            }
            case "37": {
                return showMessage(context, "An attempt of referring to the gateway that is different from the gateway at the previous");

            }
            case "38": {
                return showMessage(context, "Invalid date. The effective period of the payment may have expired.");

            }
            case "39": {
                return showMessage(context, "Invalid account number.");

            }
            case "40": {
                return showMessage(context, "The card of the specified value is not registered in the system");

            }
            case "41": {
                return showMessage(context, "Error in saving the payment in the system.");

            }
            case "42": {
                return showMessage(context, "Error in saving the receipt to the database.");

            }
            case "43": {
                return showMessage(context, "Your working session in the system is invalid (the duration of the session may have expired), try to re-enter.");

            }
            case "44": {
                return showMessage(context, "The Client cannot operate on this trading server.");

            }
            case "45": {
                return showMessage(context, "No license is available for accepting payments to the benefit of this operator.");

            }
            case "46": {
                return showMessage(context, "Could not complete the erroneous payment.");

            }
            case "47": {
                return showMessage(context, "Time limitation of access rights has been activated.");

            }
            case "48": {
                return showMessage(context, "Error in saving the session data to the database.");

            }
            case "50": {
                return showMessage(context, "Effecting payments in the system is temporarily impossible.");

            }
            case "51": {
                return showMessage(context, "Data are not found in the system.");

            }
            case "52": {
                return showMessage(context, "The Dealer may be blocked. The Dealer’s current status does not allow effecting payments");

            }
            case "53": {
                return showMessage(context, "The Acceptance Outlet may be blocked. The Acceptance Outlet’s current status does not allow effecting payments.");

            }
            case "54": {
                return showMessage(context, "The Operator may be blocked. The Operator’s current status does not allow effecting payments");

            }
            case "55": {
                return showMessage(context, "The Dealer Type does not allow effecting payments.");

            }
            case "56": {
                return showMessage(context, "An Acceptance Outlet of another type was expected. This Acceptance Outlet type does not allow effecting payments.");

            }
            case "57": {
                return showMessage(context, "An Operator of another type was expected. This Operator type does not allow effecting payments");

            }
            case "81": {
                return showMessage(context, "Exceeded the maximum payment amount.");

            }
            case "82": {
                return showMessage(context, "Daily debit amount has been exceeded.");

            }
            case "83": {
                return showMessage(context, "Maximum payment amount for the outlet has been exceeded.");

            }
            case "84": {
                return showMessage(context, "Daily total amount for the outlet has been exceeded.");

            }
            case "85": {
                return showMessage(context, "AMOUNT ALL is invalid");

            }
            case "86": {
                return showMessage(context, "Invalid rate value");

            }
            case "87": {
                return showMessage(context, "Beneficiary is blocked");

            }
            case "88": {
                return showMessage(context, "Duplicate Transaction (Same Mobile Number)");

            }
            case "89": {
                return showMessage(context, "A limit by a beneficiary is reached");

            }
            case "134": {
                return showMessage(context, "Wrong Key");

            }
            case "137": {
                return showMessage(context, "Wrong key or passphrase");

            }
            case "224": {
                return showMessage(context, "Operator system is down. Please try again later.");

            }
            case "333": {
                return showMessage(context, "Unknown error");

            }
            case "171": {
                return showMessage(context, "Manually Cancelled");

            }
        }
        return "";
    }

    private String showMessage(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        return msg;
    }
}
