package dut.t2.travelhelper.service

import dut.t2.travelhelper.service.model.User
import dut.t2.travelhelper.service.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("ApplicationUser/Login")
    fun login(@Body user: User): Call<LoginResponse>

}