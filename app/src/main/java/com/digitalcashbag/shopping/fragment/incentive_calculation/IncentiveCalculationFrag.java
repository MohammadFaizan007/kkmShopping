package com.digitalcashbag.shopping.fragment.incentive_calculation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.digitalcashbag.shopping.activities.MainContainer;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.BuildConfig;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.utils.LoggerUtil;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class IncentiveCalculationFrag extends BaseFragment {
    public BigDecimal totalIncentive = BigDecimal.valueOf(0);
    public BigDecimal totalAmount = BigDecimal.valueOf(0);
    @BindView(R.id.edLevel1)
    EditText edLevel1;
    @BindView(R.id.edLevel1_cal)
    TextView edLevel1Cal;
    @BindView(R.id.edLevel2)
    EditText edLevel2;
    @BindView(R.id.edLevel2_cal)
    TextView edLevel2Cal;
    @BindView(R.id.edLevel3)
    EditText edLevel3;
    @BindView(R.id.edLevel3_cal)
    TextView edLevel3Cal;
    @BindView(R.id.edLevel4)
    EditText edLevel4;
    @BindView(R.id.edLevel4_cal)
    TextView edLevel4Cal;
    @BindView(R.id.edLevel5)
    EditText edLevel5;
    @BindView(R.id.edLevel5_cal)
    TextView edLevel5Cal;
    @BindView(R.id.edLevel6)
    EditText edLevel6;
    @BindView(R.id.edLevel6_cal)
    TextView edLevel6Cal;
    @BindView(R.id.txtTotalMonthly)
    TextView txtTotalMonthly;
    @BindView(R.id.yearlyExpense1)
    EditText yearlyExpense1;
    @BindView(R.id.yearlyExpense2)
    EditText yearlyExpense2;
    @BindView(R.id.yearlyExpense3)
    EditText yearlyExpense3;
    @BindView(R.id.yearlyExpense4)
    EditText yearlyExpense4;
    @BindView(R.id.yearlyExpense5)
    EditText yearlyExpense5;
    @BindView(R.id.yearlyExpense6)
    EditText yearlyExpense6;
    @BindView(R.id.txtTotalYearly)
    TextView txtTotalYearly;
    @BindView(R.id.total_calculated_value)
    TextView totalCalculatedValue;
    @BindView(R.id.button_next_calculate)
    Button buttonNextCalculate;
    @BindView(R.id.imageView8)
    ImageView imageView8;
    @BindView(R.id.step1)
    TextView step1;
    @BindView(R.id.step2)
    TextView step2;
    @BindView(R.id.step3)
    TextView step3;
    @BindView(R.id.monthly_incentive_include)
    View monthly_incentive_include;
    @BindView(R.id.yearly_incentive_include)
    View yearly_incentive_include;
    @BindView(R.id.calculation_page_include)
    View calculation_page_include;
    Unbinder unbinder;
    int page = 1;
    @BindView(R.id.view12)
    View view12;
    @BindView(R.id.view23)
    View view23;
    @BindView(R.id.title)
    TextView title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incentive_calculation_frag, container, false);
        unbinder = ButterKnife.bind(this, view);
        resetToStepOne();
        initializeStepOne();


        Glide.with(context).load(BuildConfig.BASE_URL_ICONS + "congratulationsbg.png")
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC))
                .into(imageView8);

        step1.setOnClickListener(v -> resetToStepOne());
        step2.setOnClickListener(v -> {
            if (page == 1) {
                if (validation()) {
                    if (totalIncentive.compareTo(BigDecimal.ZERO) > 0) {
                        LoggerUtil.logItem("Total " + totalIncentive);
                        initializeStepTwo();
                        moveToStepTwo();
                    } else {
                        showAlert("Invalid referral.", R.color.red, R.drawable.error);
                    }
                } else {
                    showAlert("Please enter all the values.", R.color.red, R.drawable.error);
                }
            } else if (page == 3) {
                moveToStepTwo();
            }
        });

        buttonNextCalculate.setOnClickListener(v -> {
            if (page == 1) {
                if (validation()) {
                    if (totalIncentive.compareTo(BigDecimal.ZERO) > 0) {
                        LoggerUtil.logItem("Total " + totalIncentive);
                        initializeStepTwo();
                        moveToStepTwo();
                    } else {
                        showAlert("Invalid referral.", R.color.red, R.drawable.error);
                    }
                } else {
                    showAlert("Please enter all the values.", R.color.red, R.drawable.error);
                }
            } else if (page == 2) {
                if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
                    LoggerUtil.logItem("TotalExpe " + totalAmount);
                    moveToStepThree();
                } else {
                    showAlert("Please enter any one expenses.", R.color.red, R.drawable.error);
                }
            } else if (page == 3) {
                ((MainContainer) getActivity()).ReplaceFragment(new IncentiveCalculationFrag(), "SAI");
            }
        });


        return view;
    }

    private boolean validation() {
        if (TextUtils.isEmpty(edLevel1.getText().toString())) {
            return false;
        } else if (TextUtils.isEmpty(edLevel2.getText().toString())) {
            return false;
        } else if (TextUtils.isEmpty(edLevel3.getText().toString())) {
            return false;
        } else if (TextUtils.isEmpty(edLevel4.getText().toString())) {
            return false;
        } else if (TextUtils.isEmpty(edLevel5.getText().toString())) {
            return false;
        } else return !TextUtils.isEmpty(edLevel6.getText().toString());
    }

    private void initializeStepOne() {
        try {
            page = 1;
            txtTotalMonthly.setText(String.valueOf(totalIncentive));

            edLevel1.setFilters(new InputFilter[]{new CustomRangeInputFilter(1f, 10.0f)});
            edLevel2.setFilters(new InputFilter[]{new CustomRangeInputFilter(1f, 10.0f)});
            edLevel3.setFilters(new InputFilter[]{new CustomRangeInputFilter(1f, 10.0f)});
            edLevel4.setFilters(new InputFilter[]{new CustomRangeInputFilter(1f, 10.0f)});
            edLevel5.setFilters(new InputFilter[]{new CustomRangeInputFilter(1f, 10.0f)});
            edLevel6.setFilters(new InputFilter[]{new CustomRangeInputFilter(1f, 10.0f)});

            edLevel1.addTextChangedListener(new CustomTextWatcher(edLevel1));
            edLevel2.addTextChangedListener(new CustomTextWatcher(edLevel2));
            edLevel3.addTextChangedListener(new CustomTextWatcher(edLevel3));
            edLevel4.addTextChangedListener(new CustomTextWatcher(edLevel4));
            edLevel5.addTextChangedListener(new CustomTextWatcher(edLevel5));
            edLevel6.addTextChangedListener(new CustomTextWatcher(edLevel6));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeStepTwo() {
        page = 2;
        txtTotalYearly.setText(String.format("₹ %s", String.valueOf(totalAmount)));

        yearlyExpense1.addTextChangedListener(new CustomTextWatcher(yearlyExpense1));
        yearlyExpense2.addTextChangedListener(new CustomTextWatcher(yearlyExpense2));
        yearlyExpense3.addTextChangedListener(new CustomTextWatcher(yearlyExpense3));
        yearlyExpense4.addTextChangedListener(new CustomTextWatcher(yearlyExpense4));
        yearlyExpense5.addTextChangedListener(new CustomTextWatcher(yearlyExpense5));
        yearlyExpense6.addTextChangedListener(new CustomTextWatcher(yearlyExpense6));
    }

    private void moveToStepTwo() {
        page = 2;
        buttonNextCalculate.setText("Next");
        title.setText("Yearly Expenses");
        step1.setBackground(getResources().getDrawable(R.drawable.incentive_steps_success));
        view12.setBackgroundResource(R.color.colorPrimaryDark);
        step2.setBackground(getResources().getDrawable(R.drawable.incentive_steps_success));
        view23.setBackgroundResource(R.color.yellow);
        step3.setBackground(getResources().getDrawable(R.drawable.incentive_steps_progress));
        monthly_incentive_include.setVisibility(View.GONE);
        yearly_incentive_include.setVisibility(View.VISIBLE);
        calculation_page_include.setVisibility(View.GONE);
    }

    private void moveToStepThree() {
        finalCalculations();
        buttonNextCalculate.setText("RESET");
        title.setVisibility(View.GONE);
        page = 3;
        step1.setBackground(getResources().getDrawable(R.drawable.incentive_steps_success));
        view12.setBackgroundResource(R.color.colorPrimaryDark);
        step2.setBackground(getResources().getDrawable(R.drawable.incentive_steps_success));
        view23.setBackgroundResource(R.color.colorPrimaryDark);
        step3.setBackground(getResources().getDrawable(R.drawable.incentive_steps_success));
        monthly_incentive_include.setVisibility(View.GONE);
        yearly_incentive_include.setVisibility(View.GONE);
        calculation_page_include.setVisibility(View.VISIBLE);
    }

    private void resetToStepOne() {
        page = 1;
        title.setText("Referral");
        buttonNextCalculate.setText("Next");
        step1.setBackground(getResources().getDrawable(R.drawable.incentive_steps_success));
        view12.setBackgroundResource(R.color.yellow);
        step2.setBackground(getResources().getDrawable(R.drawable.incentive_steps_progress));
        view23.setBackgroundResource(R.color.yellow);
        step3.setBackground(getResources().getDrawable(R.drawable.incentive_steps_progress));
        monthly_incentive_include.setVisibility(View.VISIBLE);
        yearly_incentive_include.setVisibility(View.GONE);
        calculation_page_include.setVisibility(View.GONE);
    }

    private void finalCalculations() {
        BigDecimal final_calculated_value = (totalIncentive.multiply(totalAmount).multiply(BigDecimal.valueOf(0.002))).divideToIntegralValue(BigDecimal.valueOf(12));
        totalCalculatedValue.setText(String.format("₹ %s", String.valueOf(final_calculated_value.setScale(2, BigDecimal.ROUND_HALF_EVEN))));
        LoggerUtil.logItem(final_calculated_value);
    }

    private String multiplyTwoEditText(TextView edLevel1Cal, EditText edLevel2) {

        BigDecimal calculatedValue = BigDecimal.ZERO;
        try {
            calculatedValue = BigDecimal.valueOf(Double.valueOf(edLevel1Cal.getText().toString())).multiply(
                    BigDecimal.valueOf(Double.valueOf(edLevel2.getText().toString())));
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
        return calculatedValue.toBigInteger().toString();
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class CustomTextWatcher implements TextWatcher {
        private EditText mEditText;

        CustomTextWatcher(EditText e) {
            mEditText = e;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (page == 1) {
                totalIncentive = BigDecimal.valueOf(0);
                if (!TextUtils.isEmpty(edLevel1.getText().toString())) {
                    totalIncentive = totalIncentive.add(BigDecimal.valueOf(Double.valueOf(edLevel1.getText().toString())));
                    edLevel1Cal.setText(totalIncentive.toBigInteger().toString());
                } else {
                    edLevel1Cal.setText("");
                }
                if (!TextUtils.isEmpty(edLevel2.getText().toString())) {
                    edLevel2Cal.setText(multiplyTwoEditText(edLevel1Cal, edLevel2));
                    totalIncentive = totalIncentive.add(BigDecimal.valueOf(Double.valueOf(edLevel2Cal.getText().toString())));
                } else {
                    edLevel2Cal.setText("");
                }
                if (!TextUtils.isEmpty(edLevel3.getText().toString())) {
                    edLevel3Cal.setText(multiplyTwoEditText(edLevel2Cal, edLevel3));
                    totalIncentive = totalIncentive.add(BigDecimal.valueOf(Double.valueOf(edLevel3Cal.getText().toString())));
                } else {
                    edLevel3Cal.setText("");
                }
                if (!TextUtils.isEmpty(edLevel4.getText().toString())) {
                    edLevel4Cal.setText(multiplyTwoEditText(edLevel3Cal, edLevel4));
                    totalIncentive = totalIncentive.add(BigDecimal.valueOf(Double.valueOf(edLevel4Cal.getText().toString())));
                } else {
                    edLevel4Cal.setText("");
                }
                if (!TextUtils.isEmpty(edLevel5.getText().toString())) {
                    edLevel5Cal.setText(multiplyTwoEditText(edLevel4Cal, edLevel5));
                    totalIncentive = totalIncentive.add(BigDecimal.valueOf(Double.valueOf(edLevel5Cal.getText().toString())));
                } else {
                    edLevel5Cal.setText("");
                }
                if (!TextUtils.isEmpty(edLevel6.getText().toString())) {
                    edLevel6Cal.setText(multiplyTwoEditText(edLevel5Cal, edLevel6));
                    totalIncentive = totalIncentive.add(BigDecimal.valueOf(Double.valueOf(edLevel6Cal.getText().toString())));
                } else {
                    edLevel6Cal.setText("");
                }
                txtTotalMonthly.setText(totalIncentive.toBigInteger().toString());
            } else if (page == 2) {
                totalAmount = BigDecimal.valueOf(0);
                if (!TextUtils.isEmpty(yearlyExpense1.getText().toString())) {
                    totalAmount = totalAmount.add(BigDecimal.valueOf(Double.valueOf(yearlyExpense1.getText().toString())));
                }
                if (!TextUtils.isEmpty(yearlyExpense2.getText().toString())) {
                    totalAmount = totalAmount.add(BigDecimal.valueOf(Double.valueOf(yearlyExpense2.getText().toString())));
                }
                if (!TextUtils.isEmpty(yearlyExpense3.getText().toString())) {
                    totalAmount = totalAmount.add(BigDecimal.valueOf(Double.valueOf(yearlyExpense3.getText().toString())));
                }
                if (!TextUtils.isEmpty(yearlyExpense4.getText().toString())) {
                    totalAmount = totalAmount.add(BigDecimal.valueOf(Double.valueOf(yearlyExpense4.getText().toString())));
                }
                if (!TextUtils.isEmpty(yearlyExpense5.getText().toString())) {
                    totalAmount = totalAmount.add(BigDecimal.valueOf(Double.valueOf(yearlyExpense5.getText().toString())));
                }
                if (!TextUtils.isEmpty(yearlyExpense6.getText().toString())) {
                    totalAmount = totalAmount.add(BigDecimal.valueOf(Double.valueOf(yearlyExpense6.getText().toString())));
                }

                txtTotalYearly.setText(String.format("₹ %s", String.valueOf(totalAmount)));
            }
        }
    }
}
