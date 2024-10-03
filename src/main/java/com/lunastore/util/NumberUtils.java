package com.lunastore.util;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Component
public class NumberUtils {


    public String formatCustomDecimal(Double number, Integer fractionDigits, Boolean useGrouping) {
        if (number == null) {
            return "";
        }

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.KOREA);
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');

        StringBuilder pattern = new StringBuilder("#,##0");
        if (fractionDigits != null && fractionDigits > 0) {
            pattern.append(".");
            for (int i = 0; i < fractionDigits; i++) {
                pattern.append("0");
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat(pattern.toString(), symbols);
        decimalFormat.setGroupingUsed(useGrouping != null ? useGrouping : true);
        return decimalFormat.format(number);
    }
}