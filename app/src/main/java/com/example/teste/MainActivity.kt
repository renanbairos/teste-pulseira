package com.example.teste

import android.Manifest.permission.*
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.betomaluje.miband.ActionCallback
import com.betomaluje.miband.MiBand
import com.betomaluje.miband.MiBand.disconnect
import com.betomaluje.miband.bluetooth.NotificationConstants
import com.betomaluje.miband.model.VibrationMode
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    var miBand = MiBand.getInstance(this)
    private var isConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        connectToMiBand()



    }

    private fun connectToMiBand() {
        if (!miBand.isConnected) {
            miBand.connect(myConnectionCallback)
        }

        Toast.makeText(applicationContext, "Conectando...", Toast.LENGTH_SHORT).show()
    }

    private val myConnectionCallback: ActionCallback = object : ActionCallback {
        override fun onSuccess(data: Any) {
            Toast.makeText(applicationContext, "Conectado!!!!", Toast.LENGTH_SHORT).show()
            isConnected = true
            connectToMiBand();
        }

        override fun onFail(errorCode: Int, msg: String) {
            Toast.makeText(applicationContext, "FALHA", Toast.LENGTH_SHORT).show()
            isConnected = false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (!miBand.isConnected) {
            teste()
            //to vibrate without the led
            miBand.startVibration(VibrationMode.VIBRATION_WITHOUT_LED);

            //to stop vibration
            miBand.stopVibration();
        } else {

            //to vibrate without the led
            miBand.startVibration(VibrationMode.VIBRATION_WITHOUT_LED);

            //to stop vibration
            miBand.stopVibration();

            disconnect()
        }
    }

    fun teste() {
        miBand.connect(object : ActionCallback {
            override fun onSuccess(data: Any) {
                Log.d("","Connected with Mi Band!")
                Toast.makeText(applicationContext, "Conectou", Toast.LENGTH_SHORT).show()

                //to vibrate without the led
                miBand.startVibration(VibrationMode.VIBRATION_WITHOUT_LED);

                //to stop vibration
                miBand.stopVibration();
                //show SnackBar/Toast or something
            }

            override fun onFail(errorCode: Int, msg: String) {
                Toast.makeText(applicationContext, "Falhou", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        this.connectToMiBand();
    }
}