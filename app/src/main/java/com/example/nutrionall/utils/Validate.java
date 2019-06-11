package com.example.nutrionall.utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class Validate {

    public static Boolean validateNotExistFieldOrError(EditText field, String msg, Context context) {
        if (field.getText().toString().equals("")) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
}
