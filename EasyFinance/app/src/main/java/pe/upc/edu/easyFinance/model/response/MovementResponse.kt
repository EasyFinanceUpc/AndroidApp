package pe.upc.edu.easyFinance.model.response

import com.google.gson.annotations.SerializedName

class MovementResponse (
    @SerializedName("id")
    var id: Int,

    @SerializedName("createAt")
    var createAt: String,

    @SerializedName("amount")
    var amount: Float,

    @SerializedName("note")
    var note: String,

    @SerializedName("categoryId")
    var categoryId: Int,

    @SerializedName("categoryName")
    var categoryName: String,

    @SerializedName("color")
    var color: String,

    @SerializedName("type")
    var type: Int,

    @SerializedName("typeName")
    var typeName: String
)