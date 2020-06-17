package kkm.com.core.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import kkm.com.core.R;


public class DialogUtil {
    private static final String TAG = "DialogUtil";

    public static ProgressDialog progressDialog;

    public DialogUtil(Activity activity) {
        // This utility class is not publicly instantiable
//        progressDialog = new ProgressDialog(activity);
    }

    public static ProgressDialog showLoadingDialog(Activity activity, String callingPlace) {
        Log.d(TAG, "showLoadingDialog: " + callingPlace);
//        if (!progressDialog.isShowing())
        progressDialog = new ProgressDialog(activity);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog_layout);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(true);

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });

        return progressDialog;


    }

    public static void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }
}