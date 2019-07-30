package com.example.EverBank.service


import android.app.*
import android.bluetooth.BluetoothAdapter
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.media.RingtoneManager.getDefaultUri
import android.os.Build
import android.os.IBinder
import android.os.RemoteException
import android.support.annotation.Nullable
import android.util.Log
import android.widget.Toast
import com.example.EverBank.utils.ServiceCallback
import com.example.EverBank.utils.Toaster
import com.example.EverBank.utils.Utils
import com.example.EverBank.view.TarefasActivity
import org.altbeacon.beacon.*
import org.altbeacon.beacon.service.ArmaRssiFilter


@Suppress("DEPRECATION")
open class BeaconService : Service(), BeaconConsumer {

    private var beaconManager: BeaconManager? = null
    private var notificationManager: NotificationManager? = null
    private var myRegion: Region? = null
    private var visitedMinors: ArrayList<Beacon>? = null
    private var intent: Intent = Intent()
    private var bluetoothCurrentlyState: Int = -1
    private var closestBeacon: Beacon? = null


    override fun onCreate() {
        super.onCreate()

        bluetoothCurrentlyState = BluetoothAdapter.getDefaultAdapter().state

        if (bluetoothCurrentlyState == BluetoothAdapter.STATE_TURNING_OFF ||
            bluetoothCurrentlyState == BluetoothAdapter.STATE_OFF
        ) {

            intent = Intent(applicationContext, BluetoothReceiver::class.java)
            intent.putExtra(Utils.blueToothStateConstant, BluetoothAdapter.getDefaultAdapter().state)
            sendBroadcast(intent)
        }

        BeaconManager.setRssiFilterImplClass(ArmaRssiFilter::class.java)
        this.beaconManager = BeaconManager.getInstanceForApplication(this)
        this.beaconManager!!.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))

        beaconManager!!.foregroundScanPeriod = 10000
        beaconManager!!.foregroundBetweenScanPeriod = 10000
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        visitedMinors = ArrayList()
        myRegion = Region("region", null, null, null)
        beaconManager?.bind(this)

        super.onStartCommand(intent, flags, startId)
        return START_REDELIVER_INTENT
    }

    private fun sendNotification(notificationText: String, notificationTitle: String) {

        val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, "NOTIFY")
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setSmallIcon(com.example.poceveris_beacon.R.drawable.logo)
                .setSound(getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        } else {

            Notification.Builder(this)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setSmallIcon(com.example.poceveris_beacon.R.drawable.logo)
                .setSound(getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

        }

        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addNextIntent(Intent(this, TarefasActivity::class.java))

        val resultPendingIntent = stackBuilder.getPendingIntent(
            0,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        builder.setContentIntent(resultPendingIntent)

        builder.setAutoCancel(true)
        notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager!!.notify(1, builder.build())

    }

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onBeaconServiceConnect() {

        try {
            beaconManager!!.startMonitoringBeaconsInRegion(myRegion!!)
            beaconManager!!.stopMonitoringBeaconsInRegion(myRegion!!)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

        beaconManager!!.addMonitorNotifier(object : MonitorNotifier {

            override fun didDetermineStateForRegion(state: Int, region: Region?) {
                Log.i(TAG, "SERVICE : I have just switched from seeing/not seeing beacons: $state")
            }

            override fun didEnterRegion(region: Region?) {

                try {
                    beaconManager!!.startRangingBeaconsInRegion(region!!)
                    Toaster().showToastOnMainThread(this@BeaconService, "Estou na zona de pareamento",Toast.LENGTH_SHORT)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }

            override fun didExitRegion(region: Region?) {

                try {
                    beaconManager!!.stopRangingBeaconsInRegion(region!!)
                    Toaster().showToastOnMainThread(this@BeaconService, "Saiu da zona de pareamento", Toast.LENGTH_SHORT)
                    startProgress()
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
                Log.i(TAG, "SERVICE : I no longer see a beacon")
            }
        })

        beaconManager!!.addRangeNotifier { collection, _ ->

            for (beacon in collection) {

                if (closestBeacon == null) {

                    closestBeacon = beacon

                    visitedMinors?.add(beacon)

                    startBeaconNotification(visitedMinors!!)

                } else if (beacon == closestBeacon) {

                    visitedMinors?.add(beacon)

                    startBeaconNotification(visitedMinors!!)

                    if (closestBeacon!!.distance > beacon.distance) {
                        closestBeacon = beacon
                        startBeaconNotification(visitedMinors!!)
                    } else {
                        beaconManager?.startMonitoringBeaconsInRegion(myRegion!!)
                    }
                }
            }
        }
    }

    private fun startBeaconNotification(list: ArrayList<Beacon>) {
        sendNotification("Que tipo de atendimento gostaria..", "Bem-vindo à Agencia Phygital 2019 !")
        val callback = ServiceCallback.instance
        callback.onFinished(list)
    }

    private fun startProgress() {
        val callback = ServiceCallback.instance
        callback.onSuccess()
    }

    override fun onDestroy() {

        try {
            beaconManager!!.stopRangingBeaconsInRegion(myRegion!!)
            beaconManager!!.removeAllMonitorNotifiers()
            beaconManager!!.unbind(this)
        } catch (e: Exception) {
            Toaster().showToastOnMainThread(this, "service destroi", Toast.LENGTH_LONG)
        }

        cleanNotifications()
        sendBroadcast(intent)
        super.onDestroy()
    }

    fun cleanNotifications() {
        if (notificationManager != null)
            notificationManager!!.cancelAll()
    }
}