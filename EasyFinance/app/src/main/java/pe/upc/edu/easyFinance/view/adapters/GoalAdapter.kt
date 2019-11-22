package pe.upc.edu.easyFinance.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.prototype_goal.view.*
import pe.upc.edu.easyFinance.R
import pe.upc.edu.easyFinance.model.response.GoalResponse
import java.lang.StringBuilder

class GoalAdapter(val goals: List<GoalResponse>)
    : RecyclerView.Adapter<GoalAdapter.PrototypeGoal>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrototypeGoal {
        return PrototypeGoal(LayoutInflater.from(parent.context)
            .inflate(R.layout.prototype_goal, parent, false))
    }

    override fun getItemCount(): Int {
        return goals.size
    }

    override fun onBindViewHolder(holder: PrototypeGoal, position: Int) {
        holder.bindTo(goals[position])
    }

    inner class PrototypeGoal(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvCategory = itemView.tvCategory
        private val tvCreate = itemView.tvCreate
        private val tvReach = itemView.tvReach
        private val tvAmount = itemView.tvAmount

        fun bindTo(goal: GoalResponse){
            val temp = StringBuilder()
            tvCategory.text = goal.categoryName
            tvCreate.text = goal.createAt.substring(startIndex = 0, endIndex = 10) + " - " + goal.createAt.substring(startIndex = 11, endIndex = 16)
            tvAmount.text = goal.amount.toString()+"0"
            if(goal.reachAt == "0001-01-01T00:00:00")
                tvReach.text = "not yet"
            else
                tvReach.text = goal.reachAt.substring(startIndex = 0, endIndex = 10) + " - " + goal.reachAt.substring(startIndex = 11, endIndex = 16)
        }
    }
}