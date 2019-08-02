package com.example.EverBank.utils

import org.altbeacon.beacon.Beacon

open class ServiceCallback private constructor() {

    private var callback: ReceiverCallback? = null


    fun onSuccess() {
        callback?.onSuccess()}

    fun onFailure() {
        callback?.onFailure()
    }

    fun getCallback(receiverCallback: ReceiverCallback) {
        this.callback = receiverCallback
    }

    fun onFinished(list: ArrayList<Beacon>) {
        callback?.onFinished(list)
    }

    companion object {
        val instance by lazy { ServiceCallback() }
    }
}