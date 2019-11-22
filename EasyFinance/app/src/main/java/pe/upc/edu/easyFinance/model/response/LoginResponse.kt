package pe.upc.edu.easyFinance.model.response

import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("token")
    var token = ""

    @SerializedName("role")
    var role = 0
}