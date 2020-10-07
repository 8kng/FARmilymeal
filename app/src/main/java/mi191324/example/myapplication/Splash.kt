package mi191324.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity(){
    private val handler = Handler()
    private val runnable = Runnable {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.application_open)
        handler.postDelayed(runnable, 500)
    }
    override fun onStop(){
        super.onStop()
        handler.removeCallbacks(runnable)
    }
}