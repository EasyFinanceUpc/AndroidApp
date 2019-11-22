package pe.upc.edu.easyFinance.service

import pe.upc.edu.easyFinance.model.request.LoginRequest
import pe.upc.edu.easyFinance.model.request.SignUpRequest
import pe.upc.edu.easyFinance.model.response.GoalResponse
import pe.upc.edu.easyFinance.model.response.LoginResponse
import pe.upc.edu.easyFinance.model.response.MovementResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {

    @POST("authentications")
    fun userLogin(@Body request: LoginRequest): Call<LoginResponse>

    @GET("goals")
    fun getGoals(@Header("Authorization") token: String): Call<List<GoalResponse>>

    @POST("authentications/signup/customers")
    fun userSignUp(@Body request: SignUpRequest) : Call<LoginResponse>

    @GET("movements")
    fun getMovements(@Header("Authorization") token: String) : Call<List<MovementResponse>>
}