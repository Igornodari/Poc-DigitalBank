package com.example.EverBank.viewModels

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity

import com.example.EverBank.helper.LoginValidate

@RequiresApi(Build.VERSION_CODES.O)

class Autentication : AppCompatActivity() {

    private var loginValidate: LoginValidate? = null

    init {
        loginValidate = LoginValidate()
    }

    fun autFormat(user: String, pass: String, context: Context): Boolean {
        var stringUser = user.trim()
        var stringPass = pass.trim()

        return if (loginValidate!!.checkUserField(stringUser) || loginValidate!!.checkUserField(stringPass)) {
            loginValidate!!.showToast(context, "Erro:User or Pass Empty Field")
            true

        } else if (!loginValidate!!.isValid(stringUser)) {
            loginValidate!!.showToast(context, "Erro: User Formate Error ")
            true

        } else if (!loginValidate!!.checkPassword(stringPass)) {
            loginValidate!!.showToast(context, "Erro: Password Formate Error")
            true

        } else {
            loginValidate!!.showToast(context, "Login Succes")
            false
        }

    }


}