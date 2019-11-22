package pe.upc.edu.easyFinance.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_in.*
import pe.upc.edu.easyFinance.R

class SignInActivity : AppCompatActivity() {

    private var backpress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    fun showSignUp(view: View){
        startActivity(Intent(applicationContext, SignUpActivity::class.java))
    }

    fun showForgot(view: View){
        startActivity(Intent(applicationContext, ForgotActivity::class.java))
    }

    fun showHome(view: View){
        var isValid = true
        val email = tiEmail.text.toString().trim()
        val password = tiPassword.text.toString().trim()

        if(email.isEmpty()){
            tilEmail.error = resources.getString(R.string.email_error)
            isValid = false
        } else
            tilEmail.isErrorEnabled = false

        if (password.isEmpty()){
            tilPassword.error = resources.getString(R.string.password_error)
            isValid = false
        } else
            tilPassword.isErrorEnabled = false

        if(isValid)
            startActivity(Intent(applicationContext, MainActivity::class.java))
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
