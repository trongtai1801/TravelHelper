package dut.t2.travelhelper.service

import com.google.gson.JsonObject
import dut.t2.travelhelper.service.model.User
import dut.t2.travelhelper.service.response.LoginResponse
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.service.response.PublicTripsResponse
import dut.t2.travelhepler.service.response.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("ApplicationUser/Login")
    fun login(@Body user: User): Call<LoginResponse>

    @POST("ApplicationUser/Register")
    fun signUp(@Body jsonObject: JsonObject): Call<SignUpResponse>

    @GET("Users/Publictrips")
    fun getPublicTrips(@Header("Authorization") authorization: String): Call<ArrayList<PublicTrip>>

}