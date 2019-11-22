package pe.upc.edu.easyFinance.view.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_dashboard.*
import pe.upc.edu.easyFinance.R
import pe.upc.edu.easyFinance.model.persistence.AppDatabase
import pe.upc.edu.easyFinance.model.persistence.model.User
import pe.upc.edu.easyFinance.model.response.MovementResponse
import pe.upc.edu.easyFinance.service.RetrofitClient
import pe.upc.edu.easyFinance.view.adapters.MovementAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {

    lateinit var movementAdapter: MovementAdapter
    var movements: List<MovementResponse> = ArrayList()
    var users: MutableList<User> = ArrayList()
    var token: String = ""

    companion object {
        private const val ERROR_TAG = "Error"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        users = AppDatabase.getInstance(view!!.context).getDao().getAll()
        token = users[0].token

        val temp = StringBuilder()
        temp.append("bearer ").append(token)
        token = temp.toString()

        rvMovements.layoutManager = LinearLayoutManager(context)
        searchMovements()
    }

    private fun searchMovements(){
        val retrofit = RetrofitClient.instance().getMovements(token)

        retrofit.enqueue(object : Callback<List<MovementResponse>> {
            override fun onFailure(call: Call<List<MovementResponse>>, t: Throwable) {
                Log.d(ERROR_TAG, t.toString())
            }

            override fun onResponse(
                call: Call<List<MovementResponse>>,
                response: Response<List<MovementResponse>>
            ) {
                if(response.isSuccessful){
                    movements = response.body()!!
                    movementAdapter = MovementAdapter(movements)
                    rvMovements.adapter = movementAdapter
                } else{
                    Log.d(ERROR_TAG, "Error")
                }
            }
        })
    }
}
