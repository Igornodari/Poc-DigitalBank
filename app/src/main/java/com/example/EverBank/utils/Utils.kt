package com.example.EverBank.utils

import android.bluetooth.BluetoothAdapter
import android.util.Base64


object Utils {

    private var bluetoothCurrentlYsTATE: Int = -1
    const val blueToothStateConstant = "BLUETOOTH_CONSTANT"

    fun encoderBase64(text: String): String {
        return Base64.encodeToString(text.toByteArray(), Base64.DEFAULT).toString()
    }

    fun getBlueToothState(): Int {
        return bluetoothCurrentlYsTATE
    }

    fun setBlueToothState(currentlyState: Int) {
        bluetoothCurrentlYsTATE = currentlyState
    }

    fun decoderBase64(textDecoded: String): String {
        return Base64.decode(textDecoded, Base64.DEFAULT).toString()
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
}