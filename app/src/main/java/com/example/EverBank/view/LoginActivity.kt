package com.example.EverBank.view

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.EverBank.utils.UserInput
import com.example.EverBank.utils.Utils
import com.example.EverBank.utils.Utils.setBluetooth
import com.example.EverBank.viewModels.Autentication
import com.example.poceveris_beacon.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@RequiresApi(Build.VERSION_CODES.O)
open class LoginActivity : AppCompatActivity() {

    private var login: String? = null
    private var pass: String? = null

    private lateinit var userInput: UserInput
    private var autentication: Autentication? = null
    val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    var fbData: FirebaseDatabase? = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        iniciarAcao()
    }

    private fun iniciarAcao() {

        getLogineSenha()
        goCadastro()
        goReset()

        // Desativar bluetooth
        if (!mBluetoothAdapter.isEnabled) {
            mBluetoothAdapter.enable()
        }
    }

    private fun getLogineSenha() {
        val btn_login: Button = findViewById(R.id.btn_login)
        btn_login.setOnClickListener {

            val ed_login: EditText = findViewById(R.id.ed_login)
            login = ed_login.text.toString()

            val ed_senha: EditText = findViewById(R.id.ed_senha)
            pass = ed_senha.text.toString()

            userInput = UserInput(login!!, pass!!)
            checklogin(login.toString(), pass.toString())
        }

    }

    fun goCadastro() {

        val tv_cadastroButton: TextView = findViewById(R.id.tv_cadastroButton)
        tv_cadastroButton.setOnClickListener {
            val intent = Intent(applicationContext, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

    fun goReset() {
        val tv_reset: TextView = findViewById(R.id.tv_reset)
        tv_reset.setOnClickListener {
            val intent = Intent(applicationContext, ResetActivity::class.java)
            startActivity(intent)
        }
    }

    fun checklogin(email: String, pass: String) {
        autentication = Autentication()
        if (!autentication!!.autFormat(email, pass, this)) {
            val intent = Intent(this, BeaconActivity::class.java)
            verifyUserInDatabase(email, object : Callback {
                override fun onVerifiedUserData(isVerified: Boolean) {
                    if (isVerified) {

                        if (!mBluetoothAdapter.isEnabled) {
                            mBluetoothAdapter.enable()
                        }

                        intent.putExtra("email", email)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)

                    } else showMessage(applicationContext, "Login Fail")

                }
            })

        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun verifyUserInDatabase(mail: String, callback: Callback) {
        val mailEncoded = Utils.encoderBase64(mail.trim()).replace("\n", "")
        val databaseReference = fbData!!.reference.child("Usuarios").child(mailEncoded)

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                showMessage(applicationContext, p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
//                val user = p0.getValue(User::class.java)
                callback.onVerifiedUserData(true)
            }
        })

    }

    fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    interface Callback {
        fun onVerifiedUserData(isVerified: Boolean)
    }
}

