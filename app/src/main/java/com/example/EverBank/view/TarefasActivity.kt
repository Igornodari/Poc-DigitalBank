package com.example.EverBank.view

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.example.EverBank.service.BluetoothReceiver
import com.example.EverBank.utils.Keys
import com.example.EverBank.utils.RecyclerItemClickListenr
import com.example.EverBank.utils.Tarefas
import com.example.EverBank.utils.Utils
import com.example.poceveris_beacon.R.*
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import java.util.*


class TarefasActivity : AppCompatActivity() {

    private var bluetoothReceiver: BluetoothReceiver? = null
    lateinit var listView: RecyclerView
    lateinit var context: Context
    var keys: Keys = Keys()
    private lateinit var adapter: MyListAdapter
    internal var TAG = TarefasActivity::class.java.simpleName
    private var firebaseDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference
    private var keysReference: DatabaseReference? = firebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_tarefas)
        FirebaseApp.initializeApp(this)

        try {
            bluetoothReceiver = BluetoothReceiver()
            context = this
            listView = findViewById(id.rv_tarefas)
            listView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)

            val versionList = listPopulate()
            adapter = MyListAdapter(versionList)
            listView.adapter = adapter

            listView.addOnItemTouchListener(
                RecyclerItemClickListenr(
                    this,
                    listView,
                    object : RecyclerItemClickListenr.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {

                            when (position) {

                                0 -> {
                                    keysReference = firebaseDatabase.child("Keys").child("senhaSaque")
                                    keysReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError) {}

                                        override fun onDataChange(p0: DataSnapshot) {
                                            if (p0.value == null) {
                                                keysReference!!.setValue(keys.senhaSaque + 1)
                                            }else{
                                                keys = Keys()
                                                var value: Long = p0.value as Long
                                                value += 1
                                                keys.senhaSaque += value
                                                keysReference!!.setValue(keys.senhaSaque)
                                            }

                                             nextAct(position, keys.senhaSaque)
                                        }
                                    })
                                }

                                1 -> {
                                    keysReference = firebaseDatabase.child("Keys").child("senhaDeposito")
                                    keysReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError) {
                                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                        }

                                        override fun onDataChange(p0: DataSnapshot) {

                                            if (p0.value == null) {
                                                keysReference!!.setValue(keys.senhaDeposito + 1)
                                            }
                                            keys = Keys()
                                            var value: Long = p0.value as Long
                                            value += 1
                                            keys.senhaDeposito += value
                                            keysReference!!.setValue(keys.senhaDeposito)

                                            nextAct(position, keys.senhaDeposito)
                                        }

                                    })
                                }

                                2 ->{

                                    keysReference = firebaseDatabase.child("Keys").child("senhaPagarContas")
                                    keysReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError) {
                                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                        }

                                        override fun onDataChange(p0: DataSnapshot) {

                                            if (p0.value == null) {
                                                keysReference!!.setValue(keys.senhaContas + 1)
                                            }

                                            keys = Keys()
                                            var value: Long = p0.value as Long
                                            value += 1
                                            keys.senhaContas+= value
                                            keysReference!!.setValue(keys.senhaContas)

                                            nextAct(position, keys.senhaContas)
                                        }
                                    })
                                }
                                3 -> {
                                    keysReference = firebaseDatabase.child("Keys").child("senhaGerente")
                                    keysReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError) {
                                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                        }

                                        override fun onDataChange(p0: DataSnapshot) {

                                            if (p0.value == null) {
                                                keysReference!!.setValue(keys.senhaGerente + 1)
                                            }else{
                                                keys = Keys()
                                                var value: Long = p0.value as Long
                                                value += 1
                                                keys.senhaGerente+= value
                                                keysReference!!.setValue(keys.senhaGerente)
                                            }

                                            nextAct(position, keys.senhaGerente)
                                        }
                                    })

                                }
                                4 -> {

                                    keysReference = firebaseDatabase.child("Keys").child("senhaInfo")
                                    keysReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError) {
                                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                        }

                                        override fun onDataChange(p0: DataSnapshot) {

                                            if (p0.value == null) {
                                                keysReference!!.setValue(keys.senhaInfo + 1)
                                            }else{
                                                keys = Keys()
                                                var value: Long = p0.value as Long
                                                value += 1
                                                keys.senhaInfo+= value
                                                keysReference!!.setValue(keys.senhaInfo)
                                            }
                                            nextAct(position, keys.senhaInfo)
                                        }
                                    })

                                }
                                5 -> {

                                    keysReference = firebaseDatabase.child("Keys").child("senhaOutros")
                                    keysReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError) {
                                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                        }

                                        override fun onDataChange(p0: DataSnapshot) {

                                            if (p0.value == null) {
                                                keysReference!!.setValue(keys.senhaOutros + 1)
                                            }else{
                                                keys = Keys()
                                                var value: Long = p0.value as Long
                                                value += 1
                                                keys.senhaOutros+= value
                                                keysReference!!.setValue(keys.senhaOutros)
                                            }

                                            nextAct(position, keys.senhaOutros)
                                        }
                                    })

                                }
                            }
                        }

                        override fun onItemLongClick(view: View?, position: Int) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }
                    })
            )

        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }

        if (getBluetoothState()) {
            setBluetooth(enable = false)
            bluetoothReceiver!!.stopService(this, Utils.getBlueToothState())
        }
    }

private fun listPopulate(): ArrayList<Tarefas> {
    val versionList = ArrayList<Tarefas>()
    versionList.add(Tarefas(drawable.iconos_cs5_10, "Saque"))
    versionList.add(Tarefas(drawable.iconos_cs5_16, "Deposito"))
    versionList.add(Tarefas(drawable.iconos_cs5_125, "Pagar Contas"))
    versionList.add(Tarefas(drawable.iconos_cs5_40_2, "Falar com Gerente"))
    versionList.add(Tarefas(drawable.iconos_cs5_51, "Informações"))
    versionList.add(Tarefas(drawable.iconos_cs5_40, "Outros"))

    return versionList
}

fun setBluetooth(enable: Boolean): Boolean {

    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    val isEnabled = bluetoothAdapter.isEnabled

    if (enable && !isEnabled) {
        return bluetoothAdapter.enable()
    } else if (!enable && isEnabled) {
        return bluetoothAdapter.disable()
    }
    return true
}

fun getBluetoothState(): Boolean {
    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    Utils.setBlueToothState(BluetoothAdapter.getDefaultAdapter().state)
    return bluetoothAdapter.isEnabled
}

fun nextAct(position: Int, senha: Long) {
    intent = Intent(this, RandomKeysActivity::class.java)
    intent.putExtra("position", position)
    intent.putExtra("count", senha)
    startActivity(intent)
}
}




