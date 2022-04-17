package com.example.fooddrink.utils;

import static android.content.Context.LOCATION_SERVICE;

import android.content.Context;
import android.location.LocationManager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {

    public static String formatDateToString(java.util.Date date, String formatOut) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatOut);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean validateNumberPhone(String number) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[0-9]{10,13}$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public static boolean isValidatePassword(String pass) {
        return pass.length() >= 6;
    }

    public static DecimalFormat formatNumber(String no) {
        DecimalFormat decimalFormat = null;
        decimalFormat = (DecimalFormat) NumberFormat.getInstance(new Locale("vi", "VN"));
        decimalFormat.setDecimalSeparatorAlwaysShown(true);
        no = no.toUpperCase();
        switch (no) {
            case "N0":
                decimalFormat.applyPattern("#,###,###,###,###");
                break;
            case "N1":
                decimalFormat.applyPattern("#,###,###,##0.#");
                break;
            case "N2":
                decimalFormat.applyPattern("#,###,###,##0.##");
                break;
            case "N3":
                decimalFormat.applyPattern("#,###,###,##0.###");
                break;
            case "N4":
                decimalFormat.applyPattern("#,###,###,##0.####");
                break;
            case "N5":
                decimalFormat.applyPattern("#,###,###,##0.#####");
                break;
            default:
                decimalFormat.applyPattern("#,###,###,###,###");
                break;
        }
        return decimalFormat;
    }

    public static boolean checkLocation(Context context) {
        final LocationManager manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
