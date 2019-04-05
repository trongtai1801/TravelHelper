package dut.t2.travelhelper.service

import com.google.gson.JsonObject
import dut.t2.travelhelper.service.model.User
import dut.t2.travelhelper.service.response.LoginResponse
import dut.t2.travelhepler.service.response.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("ApplicationUser/Login")
    fun login(@Body user: User): Call<LoginResponse>

    @POST("ApplicationUser/Register")
    fun signUp(@Body jsonObject: JsonObject): Call<SignUpResponse>

}