package com.example.EverBank.utils

import android.content.Context
import android.os.Handler
import android.widget.Toast


class Toaster {

    fun showToastOnMainThread(context: Context, message: String, duration: Int) {
        val handler = Handler(context.applicationContext.mainLooper)
        handler.post { Toast.makeText(context.applicationContext, message, duration).show() }
    }
}