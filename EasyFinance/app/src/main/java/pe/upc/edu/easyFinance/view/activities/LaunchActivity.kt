package pe.upc.edu.easyFinance.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import pe.upc.edu.easyFinance.R
import pe.upc.edu.easyFinance.model.persistence.AppDatabase
import pe.upc.edu.easyFinance.model.persistence.model.User

class LaunchActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private var LAUNCH_DELAY: Long = 2500
    var users: MutableList<User> = ArrayList()

    private val mRunnable: Runnable = Runnable {
        if(!isFinishing){
            users = AppDatabase.getInstance(applicationContext).getDao().getAll()
            if(users.isNotEmpty()){
                users[0].flag
                if(users[0].flag){
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(applicationContext, SignInActivity::class.java))
                    finish()
                }
            }else{
                startActivity(Intent(applicationContext, SignInActivity::class.java))
                finish()
            }
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
