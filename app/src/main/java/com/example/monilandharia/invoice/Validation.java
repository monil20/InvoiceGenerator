package com.example.monilandharia.invoice;

import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by Monil Andharia on 30-Apr-16.
 */
public class Validation {

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{3}-\\d{7}";
    private static final String WEBSITE_REGEX = "^(?:(ftp|http|https)?:\\/\\/)?(?:[\\w-]+\\.)+([a-z]|[A-Z]|[0-9]){2,6}$";

    private static final String REQUIRED_MSG = "Mandatory Field";
    private static final String EMAIL_MSG = "Invalid E mail";
    private static final String WEBSITE_MSG = "Invalid Website";
    private static final String PHONE_MSG = "###-#######";

    public static boolean isEmailAddress(EditText editText, boolean required) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
    }

    public static boolean isWebsite(EditText editText, boolean required) {
        return isValid(editText, WEBSITE_REGEX, WEBSITE_MSG, required);
    }

    public static boolean isPhoneNumber(EditText editText, boolean required) {
        return isValid(editText, PHONE_REGEX, PHONE_MSG, required);
    }

    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }

    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if (required && !hasText(editText)) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        }
        ;

        // if the field is non mandatory but requires validation
        if (!required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        }

        return true;
    }

}
