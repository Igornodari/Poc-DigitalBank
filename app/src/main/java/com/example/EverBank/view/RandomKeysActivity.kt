package com.example.EverBank.view

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.EverBank.utils.Keys
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class RandomKeysActivity : AppCompatActivity() {

    private var position: Int? = null
    private var positionExtra: Int? = null
    private var count: Int? = null
    val database = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        position = getExtra("position")
        count = getExtra("count")
        init(this, position!!, count)

    }

    private fun getExtra(keyExtra: String): Int? {
        val extras = intent.extras
        if (extras != null) {
            positionExtra = extras.getInt(keyExtra)
        }
        return positionExtra
    }


    fun init(activity: Activity, position: Int, count: Int?) {

        when (position) {

            0 -> {

                val ref = database.getReference("Keys/senhaSaque")
                activity.setContentView(com.example.poceveris_beacon.R.layout.card_tarefa_saque)
                val textView: TextView? = activity.findViewById(com.example.poceveris_beacon.R.id.tv_phy_senha_saque)

                ref.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var value: Long = p0.value as Long

                        if (value == null) {
                            value++
                            textView?.text = "PHY${value}"
                        }else{
                            textView?.text = "PHY${value}"
                        }

                    }

                })
            }


            1 -> {
                activity.setContentView(com.example.poceveris_beacon.R.layout.card_tarefa_deposito)
                val textView: TextView? = activity.findViewById(com.example.poceveris_beacon.R.id.tv_phy_senha_deposito)

                val ref = database.getReference("Keys/senhaDeposito")
                ref.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var value: Long = p0.value as Long

                        if (value == null) {
                            value++
                            textView?.text = "PHY${value}"
                        } else {
                            textView?.text = "PHY${value}"
                        }

                    }

                })
            }


            2 -> {
                activity.setContentView(com.example.poceveris_beacon.R.layout.card_tarefa_pagar_contas)
                val textView: TextView? = activity.findViewById(com.example.poceveris_beacon.R.id.tv_phy_senha_pcontas)
                val ref = database.getReference("Keys/senhaPagarContas")
                ref.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var value: Long = p0.value as Long

                        if (value == null) {
                            value++
                            textView?.text = "PHY${value}"
                        } else {
                            textView?.text = "PHY${value}"
                        }

                    }

                })

            }

            3 -> {

                activity.setContentView(com.example.poceveris_beacon.R.layout.card_tarefa_gerente)
                val textView: TextView? = activity.findViewById(com.example.poceveris_beacon.R.id.tv_phy_senha_gerente)
                val ref = database.getReference("Keys/senhaGerente")
                ref.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var value: Long = p0.value as Long

                        if (value == null) {
                            value++
                            textView?.text = "PHY${value}"
                        } else {
                            textView?.text = "PHY${value}"
                        }

                    }

                })


            }

            4 -> {

                activity.setContentView(com.example.poceveris_beacon.R.layout.card_tarefa_info)
                val textView: TextView? = activity.findViewById(com.example.poceveris_beacon.R.id.tv_phy_senha_info)
                val ref = database.getReference("Keys/senhaInfo")
                ref.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var value: Long = p0.value as Long

                        if (value == null) {
                            value++
                            textView?.text = "PHY${value}"
                        } else {
                            textView?.text = "PHY${value}"
                        }

                    }

                })


            }

            5 -> {
                activity.setContentView(com.example.poceveris_beacon.R.layout.card_tarefa_outros)
                val textView: TextView? = activity.findViewById(com.example.poceveris_beacon.R.id.tv_phy_senha_outros)
                val ref = database.getReference("Keys/senhaOutros")
                ref.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var value: Long = p0.value as Long

                        if (value == null) {
                            value++
                            textView?.text = "PHY${value}"
                        } else {
                            textView?.text = "PHY${value}"
                        }

                    }

                })


            }

        }
    }

}

