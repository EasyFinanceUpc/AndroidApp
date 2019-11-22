package pe.upc.edu.easyFinance.activities

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.spinner_item.*
import pe.upc.edu.easyFinance.R
import java.util.*

class SignUpActivity : AppCompatActivity() {

    private var backpress = 0
    lateinit var toolbar: Toolbar
    val c = Calendar.getInstance()
    val y = c.get(Calendar.YEAR)
    val m = c.get(Calendar.MONTH)
    val d = c.get(Calendar.DAY_OF_MONTH)
    var isValid = true
    var genderValid = true
    var gender = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.title_sign_up)

        //region Gender
        spinnerGender()
        spGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                    1-> {
                        genderValid = true
                        gender = 1
                    }
                    2->{
                        genderValid = true
                        gender = 2
                    }
                    else -> {
                        item_spinner.setTextColor(ContextCompat.getColor(applicationContext, R.color.secondaryText))
                        genderValid = false
                        gender = 0
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        //endregion
    }

    fun showSignIn(@Suppress("UNUSED_PARAMETER") view: View){
        startActivity(Intent(applicationContext, SignInActivity::class.java))
    }

    fun showRegistry(view: View){

        isValid = true

        val email = tiEmail.text.toString().trim()
        val password = tiPassword.text.toString().trim()
        val confirm = tiConfirm.text.toString().trim()
        val birthday = tiBirthday.text.toString().trim()

        if(email.isEmpty()){
            tilEmail.error = resources.getString(R.string.email_error)
            isValid = false
        }else
            tilEmail.isErrorEnabled = false

        if(password.isEmpty()){
            tilPassword.error = resources.getString(R.string.password_error)
            isValid = false
        }else
            tilPassword.isErrorEnabled = false

        if(confirm.isEmpty()){
            tilConfirm.error = resources.getString(R.string.confirm_error)
            isValid = false
        }else
            tilConfirm.isErrorEnabled = false

        if(birthday.isEmpty()){
            tilBirthday.error = resources.getString(R.string.birthday_error)
            isValid = false
        }else
            tilBirthday.isErrorEnabled = false

        if(!genderValid)
            isValid = false

        if(password != confirm){
            tilConfirm.error = resources.getString(R.string.confirm_error_2)
            isValid = false
        }else
            tilConfirm.isErrorEnabled = false

        if(isValid){
            Toast.makeText(applicationContext,R.string.registry, Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, SignInActivity::class.java))
        }
    }

    fun showBirthDay(view: View){
        val dp = DatePickerDialog(
            this@SignUpActivity,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                var date: String = String.format(
                    Locale.getDefault(),
                    "%02d-%02d-%02d",
                    year,
                    monthOfYear + 1,
                    dayOfMonth
                )
                tiBirthday.setText(date)
            }, y, m, d
        )
        dp.show()
    }

    override fun onBackPressed() {
        backpress += 1
        if(backpress > 1){
            this.finishAffinity()
        }else{
            Toast.makeText(applicationContext, resources.getText(R.string.exit_text), Toast.LENGTH_SHORT).show()
        }
    }

    private fun spinnerGender(){
        var genders = arrayOf(resources.getString(R.string.hint_gender), resources.getString(R.string.hint_men), resources.getString(R.string.hint_woman))
        val arrayAdapter = ArrayAdapter(applicationContext, R.layout.spinner_item, genders)
        spGender!!.adapter = arrayAdapter
    }
}
