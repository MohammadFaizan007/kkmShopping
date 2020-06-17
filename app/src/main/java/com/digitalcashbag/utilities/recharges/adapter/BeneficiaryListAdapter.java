package com.digitalcashbag.utilities.recharges.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcashbag.R;
import com.digitalcashbag.shopping.Cons;
import com.digitalcashbag.utilities.dmt.DmtActivity;
import com.digitalcashbag.utilities.recharges.activities.AddMoney;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.DecimalFormat;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.CheckErrorCode;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.model.request.utility.RequestBeneDelete;
import kkm.com.core.model.request.utility.RequestFundTransfer;
import kkm.com.core.model.request.utility.response.ResponseBalanceAmount;
import kkm.com.core.model.response.utility.ResponseBenficiarylist;
import kkm.com.core.retrofit.ApiServices;
import kkm.com.core.retrofit.MvpView;
import kkm.com.core.retrofit.ServiceGenerator;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kkm.com.core.app.AppConfig.PAYLOAD_BUNDLE;
import static kkm.com.core.app.AppConfig.PAYLOAD_TYPE_SIX_DMT_BENEFICIARY_DELETE;
import static kkm.com.core.app.AppConfig.PAYLOAD_TYPE_THREE_DMT_FUND_TRANSFER;

public class BeneficiaryListAdapter extends RecyclerView.Adapter<BeneficiaryListAdapter.ViewHolder> {

    private static DecimalFormat format = new DecimalFormat("0.00");
    private final Context context;
    private List<ResponseBenficiarylist> list;
    private MvpView mvpView;
    private Dialog transferDialog;

    public BeneficiaryListAdapter(Context context, List<ResponseBenficiarylist> body, MvpView mvpView) {
        this.context = context;
        this.list = body;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.ben_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtName.setText(String.format("%s %s", list.get(position).getBeneficiaryFirstName(), list.get(position).getBeneficiaryLastName()));
        holder.txtMobileno.setText(list.get(position).getBeneficiaryMobileNo());
        holder.txtAccountNo.setText(list.get(position).getBeneficiaryAccountNo());
        holder.txtBankName.setText(list.get(position).getBeneficiaryBankName());
        holder.txtIfscCode.setText(list.get(position).getBeneficiaryIFSC());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void transferDialog(String mobileNumber, String benId, String benName) {
        transferDialog = new Dialog(context);
        transferDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        transferDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        transferDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        transferDialog.setContentView(R.layout.dialog_fund_transfer);

        EditText et_amount = transferDialog.findViewById(R.id.et_amount);
        EditText et_Remark = transferDialog.findViewById(R.id.et_Remark);
        Button btn_send_money = transferDialog.findViewById(R.id.btn_send_money);
        Button btn_cancel = transferDialog.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(v -> transferDialog.dismiss());
        btn_send_money.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(et_amount.getText().toString()) && Float.parseFloat(et_amount.getText().toString().trim()) >= 500.00) {
                getWalletBalance(benId, benName, mobileNumber, et_amount.getText().toString().trim(),
                        et_Remark.getText().toString());
            } else
                Toast.makeText(context, "Minimum amount should be 500.", Toast.LENGTH_SHORT).show();
        });

        transferDialog.setCancelable(false);
        transferDialog.show();
    }

    private void getFundTransfer(String benId, String benName, String mobile, String amount, String remark) {


//        {
//            "Type":"3", "NUMBER":"9918703130", "AMOUNT":"11.00", "AMOUNT_ALL":"11.00", "routingType":
//            "IMPS", "benId":"4681757", "FK_MemId":"150"
//        }
        amount = format.format(Double.parseDouble(amount));
        ((DmtActivity) context).showLoading();
        ApiServices apiServices = ServiceGenerator.createServiceUtility(ApiServices.class);
        RequestFundTransfer transfer = new RequestFundTransfer();
        transfer.setAMOUNT(amount);
        transfer.setAMOUNTALL(amount);
        transfer.setBeneficiaryName(benName);
        transfer.setBenId(benId);
        transfer.setNUMBER(PreferencesManager.getInstance(context).getMOBILE());
        transfer.setRoutingType("IMPS");
        transfer.setFK_MemId(PreferencesManager.getInstance(context).getUSERID());
        transfer.setType(PAYLOAD_TYPE_THREE_DMT_FUND_TRANSFER);
        LoggerUtil.logItem(transfer);

        Call<JsonArray> call = apiServices.getFundTransfer(transfer);
        String finalAmount = amount;
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {
                ((DmtActivity) context).hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body().size() > 0) {
                    transferDialog.dismiss();
                    try {
                        JsonObject jobj = response.body().get(0).getAsJsonObject();
                        if ((jobj.get("error").getAsString()).equalsIgnoreCase("0") && (jobj.get("result").getAsString()).equalsIgnoreCase("0")) {
                            ((DmtActivity) context).showMessage("Transaction Successful");
//                        getAddWallet(jobj, benId, benName, remark, finalAmount);
                        } else {
                            CheckErrorCode code = new CheckErrorCode();
                            code.isValidTransaction(context, jobj.get("result").getAsString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    transferDialog.dismiss();
                    Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {
                ((DmtActivity) context).hideLoading();
            }
        });
    }

//    private void getAddWallet(JsonObject param, String benId, String benName, String remark, String amount) {
//
//        try {
//
//            ((DmtActivity) context).showLoading();
//            ApiServices apiServices = ServiceGenerator.createServiceUtility(ApiServices.class);
//            RequestAddWallet wallet = new RequestAddWallet();
//            wallet.setBeneficiaryid(benId);
//            wallet.setBeneficiaryName(benName);
//            wallet.setCreditAmount("");
//            wallet.setDATE(param.get("date").getAsString());
//            wallet.setDebitAmount(amount);
//            wallet.setERROR(param.get("error").getAsString());
//            wallet.setFkMemberId(PreferencesManager.getInstance(context).getUSERID());
//            wallet.setNarration(remark);
//            wallet.setRemarks(remark);
//            wallet.setSESSION(param.get("session").getAsString());
//            wallet.setTransAmount(amount);
//            wallet.setTransDate(param.get("date").getAsString());
//            wallet.setTransNo(param.get("transid").getAsString());
//            wallet.setTransStatus(param.get("trnxstatus").getAsString());
//            wallet.setRemittersMobileNo(PreferencesManager.getInstance(context).getMOBILE());
//            wallet.setRESULT(param.get("error").getAsString());
//
//            LoggerUtil.logItem(wallet);
//
//            Call<JsonObject> call = apiServices.getAddWallet(wallet);
//            call.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
//                    ((DmtActivity) context).hideLoading();
//                    LoggerUtil.logItem(response.body());
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                    ((DmtActivity) context).hideLoading();
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    [
//    {
//              "date": "20.02.2019 11:02:30",
//              "session": "WedFeb202019110223",
//              "error": "0",
//              "result": "0",
//              "addinfo": {
//                      "status": "Transaction Successful",
//                      "statuscode": "TXN",
//                      "data": {
//                              "charged_amt": 9.32,
//                              "ref_no": "905111444298",
//                              "name": "SAVITA RAMASHANKAR M",
//                              "amount": "5.00",
//                              "ccf_bank": 10,
//                              "bank_alias": "",
//                              "locked_amt": 0,
//                              "opr_id": "905111444298",
//                              "ipay_id": "1190220110228NQMMZ"
//                              }
//                         },
//              "errmsg": "",
//              "transid": "1001692393080",
//              "authcode": "1190220110228NQMMZ",
//              "trnxstatus": "7"
//    }
//]

    private void getBeneficiaryDelete(String mobileNumber, String benId) {
        ApiServices apiServices = ServiceGenerator.createServiceUtility(ApiServices.class);
        ((DmtActivity) context).showLoading();
//        {
//                "Type": "6",
//                "NUMBER": "9918703130",
//                "AMOUNT": "5.00",
//                "AMOUNT_ALL": "5.00",
//                "remId": "2441357",
//                "benId": "3154441"
//        }
        RequestBeneDelete beneDelete = new RequestBeneDelete();
        beneDelete.setType(PAYLOAD_TYPE_SIX_DMT_BENEFICIARY_DELETE);
        beneDelete.setNUMBER(mobileNumber);
        beneDelete.setAMOUNT("1.0");
        beneDelete.setAMOUNTALL("1.0");
        beneDelete.setBenId(benId);
        beneDelete.setRemId(PreferencesManager.getInstance(context).getREMITTER_ID());
        LoggerUtil.logItem(beneDelete);

        Call<JsonObject> objectCall = apiServices.beneficiarydelete(beneDelete);
        objectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                ((DmtActivity) context).hideLoading();
                LoggerUtil.logItem(response.body());
                mvpView.getClickPosition(0, "delete");
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                ((DmtActivity) context).hideLoading();
            }
        });
    }

    private void getWalletBalance(String benId, String benName, String mobileNumber, String amountPackage, String remark) {
        try {

            String keys = BuildConfig.CASHBAG_KEY;
            SecretKey easypay_key = new SecretKeySpec(keys.getBytes(), "AES");

            ApiServices apiServices = ServiceGenerator.createServiceUtilityV2(ApiServices.class);
            ((DmtActivity) context).showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("MemberId", PreferencesManager.getInstance(context).getUSERID());
            LoggerUtil.logItem(jsonObject);

            JsonObject body = new JsonObject();
            try {
                body.addProperty("body", Cons.encryptMsg(jsonObject.toString(), easypay_key));
                LoggerUtil.logItem(body);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Call<JsonObject> walletBalanceCall = apiServices.getbalanceAmount(body);
            walletBalanceCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    ((DmtActivity) context).hideLoading();
                    LoggerUtil.logItem(response.body());

//
                    try {

                        String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easypay_key);
                        ResponseBalanceAmount convertedObject = new Gson().fromJson(paramResponse, ResponseBalanceAmount.class);

                        if (convertedObject.getStatus().equalsIgnoreCase("Success")) {
//                        PreferencesManager.getInstance(context).setWALLET_BALANCE((float) response.body().getBalanceAmount());
                            if (convertedObject.getBalanceAmount() >= Float.parseFloat(amountPackage)) {
                                getFundTransfer(benId, benName, mobileNumber, amountPackage, remark);
                            } else
                                createAddBalanceDialog(context, "Insufficient bag balance", "You have insufficient balance in your bag, add money before making transactions.", amountPackage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    ((DmtActivity) context).hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createAddBalanceDialog(Context context, String title, String msg, String amountPackage) {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setNegativeButton("Add Balance", (dialog, id) -> {
            dialog.dismiss();
//            AddBalancePayment addBalancePayment = new AddBalancePayment();
            Bundle b = new Bundle();
            b.putString("total", amountPackage + "");
            b.putString("from", "dmt");
//            addBalancePayment.setArguments(b);
//            FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
//            addBalancePayment.show(ft, AddBalancePayment.TAG);

            goToActivity((Activity) context, AddMoney.class, b);
        });

        android.support.v7.app.AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void goToActivity(Activity activity, Class<?> classActivity, Bundle bundle) {
        Utils.hideSoftKeyboard(activity);
        Intent intent = new Intent(activity, classActivity);
        if (bundle != null) intent.putExtra(PAYLOAD_BUNDLE, bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(kkm.com.core.R.anim.slide_from_right, kkm.com.core.R.anim.slide_to_left);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtMobileno)
        TextView txtMobileno;
        @BindView(R.id.txtAccountNo)
        TextView txtAccountNo;
        @BindView(R.id.txtBankName)
        TextView txtBankName;
        @BindView(R.id.txtIfscCode)
        TextView txtIfscCode;
        @BindView(R.id.imgDelete)
        ImageView imgDelete;
        @BindView(R.id.btn_send_money)
        Button btn_send_money;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            btn_send_money.setOnClickListener(v -> transferDialog(list.get(getAdapterPosition()).getBeneficiaryMobileNo(),
                    list.get(getAdapterPosition()).getBeneficiaryId(),
                    list.get(getAdapterPosition()).getBeneficiaryFirstName() + " " +
                            list.get(getAdapterPosition()).getBeneficiaryLastName()));

            imgDelete.setOnClickListener(v -> getBeneficiaryDelete(list.get(getAdapterPosition()).getBeneficiaryMobileNo(),
                    list.get(getAdapterPosition()).getBeneficiaryId()));
        }
    }
}