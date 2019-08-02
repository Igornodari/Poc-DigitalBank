package com.example.EverBank.view

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.example.EverBank.service.BluetoothReceiver
import com.example.EverBank.utils.ReceiverCallback
import com.example.EverBank.utils.ServiceCallback
import com.example.EverBank.utils.Toaster
import com.example.EverBank.utils.Utils
import com.example.EverBank.utils.Utils.setBluetooth
import com.example.poceveris_beacon.R
import kotlinx.android.synthetic.main.activity_beacon_detector.*
import org.altbeacon.beacon.Beacon


open class BeaconActivity : AppCompatActivity(), ReceiverCallback {

    private var beaconMonitoring: Intent? = null
    private var isServiceStopped: Boolean = false
    var lottieAnimationView: LottieAnimationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beacon_detector)

        lottieAnimationView = findViewById(R.id.lav_android_wave_json)

        setBluetooth(true)
        activeLocation()
        onSuccess()

        isServiceStopped = false
        beaconMonitoring = Intent(this, BluetoothReceiver::class.java)
        beaconMonitoring!!.putExtra(Utils.blueToothStateConstant, Utils.getBlueToothState())
        startService(beaconMonitoring)
        sendBroadcast(beaconMonitoring)

        initCallback()

    }

    private fun initCallback() {
        ServiceCallback.instance.getCallback(this)
    }


    override fun onFinished(list: ArrayList<Beacon>) {
        if (lottieAnimationView!!.isAnimating) {
            lottieAnimationView!!.cancelAnimation()
            lottieAnimationView!!.visibility = View.GONE
            tv_beaconlist!!.text = "Encontramos o ponto de acesso : " + list.iterator().next().bluetoothName +
                    "\n Clique na notificação para ver nossos serviços !!"
        }
    }

    override fun onFailure() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccess() {
        if (!lottieAnimationView!!.isAnimating) {
            lottieAnimationView!!.playAnimation()
            lottieAnimationView!!.visibility = View.VISIBLE
        }
    }

    fun activeLocation() {
        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Esse aplicativo precisa de permissão para ativar a localização")
            builder.setMessage("Aceite para continuar")
            builder.setPositiveButton(android.R.string.ok, null)

            builder.setOnDismissListener {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    1
                )
            }
            builder.show()
        }
    }

        override fun onDestroy() {
            if (!isServiceStopped)
                isServiceStopped = false
            beaconMonitoring = Intent(this, BluetoothReceiver::class.java)
            beaconMonitoring!!.putExtra(Utils.blueToothStateConstant, Utils.getBlueToothState())
            sendBroadcast(beaconMonitoring!!)

            super.onDestroy()
        }


    }


