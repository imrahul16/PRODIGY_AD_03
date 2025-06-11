package com.example.prostopwatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Timer

class MainActivity : AppCompatActivity(){
    private lateinit var tvTimer: TextView
    private lateinit var btnStart: Button
    private lateinit var btnPause: Button
    private lateinit var btnReset: Button

    private var isRunning = false
    private var startTime = 0L
    private var timeBuff = 0L
    private var updateTime = 0L

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            val time = System.currentTimeMillis()-startTime+timeBuff
            updateTime=time
            val minutes = (time/1000)/60
            val seconds = (time/1000)%60
            val milliseconds = time % 1000
            tvTimer.text = String.format("%02d:%02d:%03d",minutes,seconds,milliseconds)
            handler.postDelayed(this,10)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTimer=findViewById<TextView>(R.id.tvTimer)
        btnStart=findViewById<Button>(R.id.btnstart)
        btnPause=findViewById<Button>(R.id.btnPause)
        btnReset=findViewById<Button>(R.id.btnReset)

        btnStart.setOnClickListener {
            if(!isRunning){
                startTime= System.currentTimeMillis()
                handler.post(runnable)
                isRunning=true
            }
        }
        btnPause.setOnClickListener {
            if (isRunning){
                timeBuff += System.currentTimeMillis() - startTime
                handler.removeCallbacks(runnable)
                isRunning=false
            }
        }
        btnReset.setOnClickListener {
            handler.removeCallbacks(runnable)
            isRunning=false
            startTime=0L
            timeBuff=0L
            updateTime=0L
            tvTimer.text = "00:00:000"
        }
    }
}