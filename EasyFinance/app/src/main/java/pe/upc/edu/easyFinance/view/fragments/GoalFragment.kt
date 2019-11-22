package pe.upc.edu.easyFinance.view.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_goal.*
import pe.upc.edu.easyFinance.R
import pe.upc.edu.easyFinance.model.persistence.AppDatabase
import pe.upc.edu.easyFinance.model.persistence.model.User
import pe.upc.edu.easyFinance.model.response.GoalResponse
import pe.upc.edu.easyFinance.service.RetrofitClient
import pe.upc.edu.easyFinance.view.adapters.GoalAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

/**
 * A simple [Fragment] subclass.
 */
class GoalFragment : Fragment() {

    lateinit var goalAdapter: GoalAdapter
    var goals: List<GoalResponse> = ArrayList()
    var users: MutableList<User> = ArrayList()
    var token: String = ""

    companion object{
        private const val ERROR_TAG = "Error"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        users = AppDatabase.getInstance(view!!.context).getDao().getAll()
        token = users[0].token

        val temp = StringBuilder()
        temp.append("bearer ").append(token)
        token = temp.toString()

        rvGoal.layoutManager = LinearLayoutManager(context)
        searchGoals()
    }

    private fun searchGoals(){
        val retrofit = RetrofitClient.instance().getGoals(token)

        retrofit.enqueue(object : Callback<List<GoalResponse>> {
            override fun onFailure(call: Call<List<GoalResponse>>, t: Throwable) {
                Log.d(ERROR_TAG, t.toString())
            }

            override fun onResponse(call: Call<List<GoalResponse>>, response: Response<List<GoalResponse>>) {
                if(response.isSuccessful){
                    goals = response.body()!!
                    goalAdapter = GoalAdapter(goals)
                    rvGoal.adapter = goalAdapter
                } else {
                    Log.d(ERROR_TAG, "Error")
                }
            }
        })
    }
}
