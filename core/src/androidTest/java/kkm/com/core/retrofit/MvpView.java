package kkm.com.core.retrofit;

import android.support.annotation.StringRes;

import java.util.Date;

/**
 * Created by Vivek on 1/8/18.
 */

public interface MvpView {
    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();

    void getDateDetails(Date date);

    void getClickPosition(int position, String tag);

    void getClickPositionDirectMember(int position, String tag, String memberId);

    void getGiftCardCategoryId(String id, String name);

    void checkAvailability(String id, String date, String name, String amount);

    void openSearchCategory(String searchItemId, String searchName);

    void getPayoutWithdrawalId(String requestId);

}
