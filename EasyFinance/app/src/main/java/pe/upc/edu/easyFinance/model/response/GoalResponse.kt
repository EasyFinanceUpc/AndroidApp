package pe.upc.edu.easyFinance.model.response

import com.google.gson.annotations.SerializedName

class GoalResponse (
    @SerializedName("id")
    var id: Int,

    @SerializedName("createAt")
    var createAt: String,

    @SerializedName("amount")
    var amount: Float,

    @SerializedName("categoryId")
    var categoryId: Int,

    @SerializedName("categoryName")
    var categoryName: String,

    @SerializedName("color")
    var color: String,

    @SerializedName("reachAt")
    var reachAt: String
)