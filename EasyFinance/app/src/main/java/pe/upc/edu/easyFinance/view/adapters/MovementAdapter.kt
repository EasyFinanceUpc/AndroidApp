package pe.upc.edu.easyFinance.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.prototype_movements.view.*
import pe.upc.edu.easyFinance.R
import pe.upc.edu.easyFinance.model.response.MovementResponse

class MovementAdapter(val movements: List<MovementResponse>):
    RecyclerView.Adapter<MovementAdapter.PrototypeMovement>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PrototypeMovement {
        return PrototypeMovement(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.prototype_movements, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return movements.size
    }

    override fun onBindViewHolder(holder: MovementAdapter.PrototypeMovement, position: Int) {
        holder.bindTo(movements[position])
    }

    inner class PrototypeMovement(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvCategory = itemView.tvCategory
        private val tvDate = itemView.tvDate
        private val tvAmount = itemView.tvAmount
        private val tvType = itemView.tvType

        fun bindTo(movement: MovementResponse){
            tvCategory.text = movement.categoryName
            tvDate.text = movement.createAt.substring(startIndex = 0, endIndex = 10) + " - " + movement.createAt.substring(startIndex = 11, endIndex = 16)
            tvAmount.text = movement.amount.toString()
            tvType.text = movement.typeName
        }
    }
}