package dut.t2.travelhelper.service

import com.google.gson.JsonObject
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhelper.service.model.User
import dut.t2.travelhelper.service.response.LoginResponse
import dut.t2.travelhepler.service.model.Home
import dut.t2.travelhepler.service.model.Photo
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.service.model.Reference
import dut.t2.travelhepler.service.response.SignUpResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.ArrayList

interface ApiService {

    @POST("ApplicationUser/Login")
    fun login(@Body user: User): Call<LoginResponse>

    @POST("ApplicationUser/Register")
    fun signUp(@Body jsonObject: JsonObject): Call<SignUpResponse>

    @GET("Users/Publictrips")
    fun getPublicTrips(@Header("Authorization") authorization: String): Call<ArrayList<PublicTrip>>

    @GET("address")
    fun getSuggestAddress(@Header("Authorization") authorization: String): Call<ArrayList<String>>

    @POST("PublicTrips")
    fun createPublicTrip(
        @Header("Authorization") authorization: String,
        @Body trip: JsonObject
    ): Call<PublicTrip>

    @PUT("PublicTrips/{id}")
    fun updatePublicTrip(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int,
        @Body trip: JsonObject
    ): Call<PublicTrip>

    @DELETE("PublicTrips/{id}")
    fun deletePublicTrip(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ): Call<PublicTrip>

    @Multipart
    @POST("Users/Avatar")
    fun updateAvatar(
        @Header("Authorization") authorization: String,
        @Part avatar: MultipartBody.Part
    ): Call<Profile>

    @PUT("Users")
    fun updateUserProfile(
        @Header("Authorization") authorization: String,
        @Body jsonObjects: JsonObject
    ): Call<Profile>

    @GET("users/images")
    fun getPhotos(@Header("Authorization") authorization: String): Call<ArrayList<Photo>>

    @DELETE("Images/{id}")
    fun deletePhoto(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ): Call<Photo>

    @Multipart
    @POST("Images")
    fun uploadImage(
        @Header("Authorization") authorization: String,
        @Part photo: MultipartBody.Part
    ): Call<Photo>

    @GET("users/References")
    fun getReferences(@Header("Authorization") authorization: String): Call<ArrayList<Reference>>

    @GET("Users/Homes")
    fun getHomeInfo(@Header("Authorization") authorization: String): Call<ArrayList<Home>>

    @GET("Users/Search")
    fun getHosts(
        @Header("Authorization") authorization: String,
        @Query("address") address: String
    ): Call<ArrayList<Profile>>


    @GET("Users/{id}/Homes")
    fun getHomeInfoOfOtherUser(@Path("id") userId: String): Call<ArrayList<Home>>
}