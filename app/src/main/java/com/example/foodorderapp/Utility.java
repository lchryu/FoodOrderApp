package com.example.foodorderapp;

import android.content.Context;
import android.widget.Toast;

public class Utility {
    static void ShowToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
