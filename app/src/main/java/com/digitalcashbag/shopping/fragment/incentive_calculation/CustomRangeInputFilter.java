package com.digitalcashbag.shopping.fragment.incentive_calculation;

import android.text.InputFilter;
import android.text.Spanned;

public class CustomRangeInputFilter implements InputFilter {
    private double minValue;
    private double maxValue;

    public CustomRangeInputFilter(double minVal, double maxVal) {
        this.minValue = minVal;
        this.maxValue = maxVal;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
        try {
            // Remove the string out of destination that is to be replaced
            String newVal = dest.toString().substring(0, dStart) + dest.toString().substring(dEnd);
            newVal = newVal.substring(0, dStart) + source.toString() + newVal.substring(dStart);
            double input;

            if (newVal.equalsIgnoreCase("")) {
                input = 0;
            } else {
                input = Double.parseDouble(newVal);
            }

            if (isInRange(minValue, maxValue, input)) {
                return null;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean isInRange(double a, double b, double c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}