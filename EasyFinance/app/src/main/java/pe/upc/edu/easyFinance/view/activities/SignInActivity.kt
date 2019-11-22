package pe.upc.edu.easyFinance.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_in.*
import pe.upc.edu.easyFinance.R
import pe.upc.edu.easyFinance.model.persistence.AppDatabase
import pe.upc.edu.easyFinance.model.persistence.model.User
import pe.upc.edu.easyFinance.model.request.LoginRequest
import pe.upc.edu.easyFinance.model.response.LoginResponse
import pe.upc.edu.easyFinance.service.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    private var backpress = 0
    var token: String = ""

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

        if(isValid){
            val request = LoginRequest(email,password)

            RetrofitClient.instance().userLogin(request)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, "Check Internet Connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if(response.body()?.role!! > 0 && response.body()?.role!! <= 2){
                            token = response.body()!!.token

                            val users: MutableList<User> = AppDatabase.getInstance(view.context).getDao().getAll()

                            if(users.isEmpty()){
                                val user = User(1, token, true)
                                AppDatabase.getInstance(view.context).getDao().insert(user)
                            }else{
                                val user = User(1, token, true)
                                AppDatabase.getInstance(view.context).getDao().update(user)
                            }

                            /*val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.putExtra("token", token)
                            startActivity(intent)*/
                            Toast.makeText(applicationContext, "token -> " + token, Toast.LENGTH_LONG).show()
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                        }else{
                            Toast.makeText(applicationContext, "Check your email or password", Toast.LENGTH_LONG).show()
                        }
                    }
                })
        }

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
