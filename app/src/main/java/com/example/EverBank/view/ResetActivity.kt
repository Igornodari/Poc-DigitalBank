package com.example.EverBank.view

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.example.poceveris_beacon.R
import com.google.firebase.auth.FirebaseAuth


class ResetActivity : AppCompatActivity() {

    private val email: AutoCompleteTextView? = null
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_reset)

        firebaseAuth = FirebaseAuth.getInstance();
    }

    fun reset() {

        firebaseAuth!!.sendPasswordResetEmail(email!!.text.toString())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    email.setText("")
                    Toast.makeText(
                        this,
                        "Recuperação de acesso iniciada. Email enviado.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "Falhou! Tente novamente",
                        Toast.LENGTH_SHORT
                    ).show();
                }
            }
    }
}