package edu.icesi.monage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //startActivity(Intent(this,RegistrationActivity::class.java))
        startTimer()
        //finish()
    }
    fun startTimer(){
        object : CountDownTimer(3000,1000){
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                val intent = Intent(applicationContext, LoginActivity :: class.java).apply{}
                startActivity(intent)
            }
        }.start()
    }
}