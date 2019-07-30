package com.example.EverBank.service

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.EverBank.utils.Utils

class BluetoothReceiver : BroadcastReceiver() {

    private var const: Context? = null


    fun BluetoothReceiver(context: Context?) {
        this.const = context!!.applicationContext
    }


    override fun onReceive(context: Context?, intent: Intent?) {

        val extras: Bundle? = intent!!.extras

        when (val bluetoothState: Int? = extras?.getInt(Utils.blueToothStateConstant, Utils.getBlueToothState())) {

            BluetoothAdapter.STATE_OFF -> {
                stopService(context, bluetoothState)
            }
            BluetoothAdapter.STATE_TURNING_OFF -> {
                stopService(context, bluetoothState)
            }
            BluetoothAdapter.STATE_ON -> {
                initService(context, bluetoothState)
            }
            BluetoothAdapter.STATE_TURNING_ON -> {
                initService(context, bluetoothState)
            }
        }
    }


    fun initService(context: Context?, bluetoothState: Int) {
        val intent = Intent(context, BeaconService::class.java)
        intent.putExtra(Utils.blueToothStateConstant, bluetoothState)
        context?.startService(intent)
    }

    fun stopService(context: Context?, bluetoothState: Int) {
        val intent = Intent(context, BeaconService::class.java)
        intent.putExtra(Utils.blueToothStateConstant, bluetoothState)
        context?.stopService(intent)
    }


}