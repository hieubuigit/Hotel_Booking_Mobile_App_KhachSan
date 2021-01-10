package com.chuyende.hotelbookingappofhotel.validate;

import android.app.Activity;
import android.widget.EditText;

public class CheckTextInput {
    Activity activity;

    public CheckTextInput(Activity activity) {
        this.activity = activity;
    }

    public void checkEmpty(EditText check, String warning) {
        if (check.getText().toString().isEmpty()) {
            check.setError(warning);
            check.isFocused();
        }
    }
}
