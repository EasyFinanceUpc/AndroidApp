package pe.upc.edu.easyFinance.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import pe.upc.edu.easyFinance.R

class ForgotActivity : AppCompatActivity() {

    private var backpress = 0
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.title_reset)
    }

    fun showSignUp(@Suppress("UNUSED_PARAMETER") view: View){
        startActivity(Intent(applicationContext, SignUpActivity::class.java))
    }

    override fun onBackPressed() {
        backpress += 1
        if(backpress > 1){
            this.finishAffinity()
        }else{
            Toast.makeText(applicationContext, resources.getText(R.string.exit_text), Toast.LENGTH_SHORT).show()
        }
    }
}
