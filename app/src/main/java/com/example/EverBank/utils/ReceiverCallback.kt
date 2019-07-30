package com.example.EverBank.utils

import org.altbeacon.beacon.Beacon

interface ReceiverCallback {
    fun onSuccess()
    fun onFailure()
    fun onFinished(list: ArrayList<Beacon>)
}