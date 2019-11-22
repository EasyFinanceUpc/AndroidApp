package pe.upc.edu.easyFinance.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import pe.upc.edu.easyFinance.R

class LaunchActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private var LAUNCH_DELAY: Long = 2500

    private val mRunnable: Runnable = Runnable {
        if(!isFinishing){
            startActivity(Intent(applicationContext, SignInActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, LAUNCH_DELAY)
    }

    override fun onDestroy() {
        if(mRunnable != null){
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }
}
