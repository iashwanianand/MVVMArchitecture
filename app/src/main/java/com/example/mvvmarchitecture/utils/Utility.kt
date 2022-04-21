package com.example.mvvmarchitecture.utils

import android.content.Context
import android.widget.Toast

class Utility {

    fun customToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}