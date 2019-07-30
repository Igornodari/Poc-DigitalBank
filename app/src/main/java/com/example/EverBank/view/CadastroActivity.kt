package com.example.EverBank.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import android.view.Menu
import android.widget.Toast
import com.example.EverBank.utils.User
import com.example.EverBank.utils.Utils
import com.example.poceveris_beacon.R
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    private var firebaseDatabase: FirebaseDatabase? = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        FirebaseApp.initializeApp(this@CadastroActivity)
        databaseReference = firebaseDatabase!!.reference
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_formulario, menu)
        return super.onCreateOptionsMenu(menu)
    }


    fun onClick() {

        ed_loginCadastro.text.toString()
        ed_emailCadastro.text.toString()
        ed_senhaCadastro.text.toString()
        ed_confirmsenha.text.toString()

        btn_cadastro.setOnClickListener {

            val usuario = User()
            usuario.id = Utils.encoderBase64(ed_emailCadastro.toString())
            usuario.login = ed_loginCadastro.toString()
            usuario.email = ed_emailCadastro.toString()
            usuario.senha = Utils.encoderBase64(ed_senhaCadastro.toString()).replace("\n", "")
            usuario.cfsenha = Utils.encoderBase64(ed_confirmsenha.toString()).replace("\n", "")
            databaseReference!!.child("Usuarios").child(usuario.id.toString().replace("\n", "")).setValue(usuario)
            Toast.makeText(this, "Usuario :" + usuario.login.toString() + ", Adicionado", Toast.LENGTH_SHORT).show()

            goLogin()

        }
    }

    fun goLogin() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
    }
}
