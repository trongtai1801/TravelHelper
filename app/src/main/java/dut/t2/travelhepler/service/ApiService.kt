package dut.t2.travelhelper.service

import com.google.gson.JsonObject
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhelper.service.model.User
import dut.t2.travelhelper.service.response.LoginResponse
import dut.t2.travelhepler.service.model.*
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

    @GET("users/{id}/images")
    fun getPhotos(
        @Header("Authorization") authorization: String,
        @Path("id") userId: String
    ): Call<ArrayList<Photo>>

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

    @GET("users/{id}/References")
    fun getReferences(
        @Header("Authorization") authorization: String,
        @Path("id") userId: String
    ): Call<ArrayList<Reference>>

    @GET("Users/Homes")
    fun getHomeInfo(@Header("Authorization") authorization: String): Call<ArrayList<Home>>

    @GET("Users/Search")
    fun getHosts(
        @Header("Authorization") authorization: String,
        @Query("address") address: String
    ): Call<ArrayList<Profile>>


    @GET("Users/{id}/Homes")
    fun getHomeInfoOfOtherUser(
        @Header("Authorization") authorization: String,
        @Path("id") userId: String
    ): Call<ArrayList<Home>>

    @POST("TravelRequests/{id}")
    fun createRequestToStay(
        @Header("Authorization") authorization: String,
        @Path("id") hostId: String,
        @Body trip: JsonObject
    ): Call<Void>

    @GET("Users/SentTravelRequests")
    fun getMyRequests(@Header("Authorization") authorization: String): Call<ArrayList<Request>>

    @POST("References/{id}")
    fun writeReference(
        @Header("Authorization") authorization: String,
        @Path("id") userId: String,
        @Body reference: JsonObject
    ): Call<Reference>

    @GET("Users/TravelRequests")
    fun getRequestsToStay(@Header("Authorization") authorization: String): Call<ArrayList<Request>>

    @GET("Publictrips/Search")
    fun getTravelers(
        @Header("Authorization") authorization: String,
        @Query("destination") destination: String
    ): Call<ArrayList<PublicTrip>>

    @POST("HostOffers/{id}")
    fun createOffer(
        @Header("Authorization") authorization: String,
        @Path("id") userId: String,
        @Body trip: JsonObject
    ): Call<Void>

    @GET("Users/SentHostOffers")
    fun getMyOffers(@Header("Authorization") authorization: String): Call<ArrayList<Offer>>

    @GET("Users/HostOffers")
    fun getOfferToHost(@Header("Authorization") authorization: String): Call<ArrayList<Offer>>

    @GET("Users/Friends")
    fun getFriends(@Header("Authorization") authorization: String): Call<ArrayList<Profile>>

    @PUT("HostOffers/{id}")
    fun acceptHostOffer(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ): Call<Offer>

    @DELETE("HostOffers/{id}")
    fun ignoreHostOffer(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ): Call<Void>

    @PUT("TravelRequests/{id}")
    fun acceptTravelRequest(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ): Call<Request>

    @DELETE("TravelRequests/{id}")
    fun ignoreTravelRequest(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ): Call<Void>

    @GET("Users/FriendRequests")
    fun getFriendRequests(@Header("Authorization") authorization: String): Call<ArrayList<FriendRequest>>

    @PUT("FriendRequests/{id}")
    fun acceptFriendRequest(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ): Call<FriendRequest>

    @DELETE("FriendRequests/{id}")
    fun ignoreFriendRequest(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ): Call<Void>

    @GET("Friends/CheckIsFriend/{id}")
    fun chekFriend(@Header("Authorization") authorization: String, @Path("id") userId: String): Call<CheckFriend>

    @POST("FriendRequests/{id}")
    fun addFriend(
        @Header("Authorization") authorization: String,
        @Path("id") userId: String,
        @Body message: JsonObject
    ): Call<Void>

    @GET("Users/SentFriendRequests")
    fun getMyFriendRequests(@Header("Authorization") authorization: String): Call<ArrayList<FriendRequest>>

    @GET("Users/SearchByName")
    fun searchUser(
        @Header("Authorization") authorization: String,
        @Query("name") seachString: String
    ): Call<ArrayList<Profile>>

    @PUT("FriendRequests/CancelRequest/{id}")
    fun cancelFriendRequest(
        @Header("Authorization") authorization: String,
        @Path("id") requestId: Int
    ): Call<Void>

    @PUT("TravelRequests/CancelRequest/{id}")
    fun cancelTravelRequest(
        @Header("Authorization") authorization: String,
        @Path("id") requestId: Int
    ): Call<Void>

    @PUT("HostOffers/CancelOffer/{id}")
    fun cancelHostOfferRequest(
        @Header("Authorization") authorization: String,
        @Path("id") requestId: Int
    ): Call<Void>
}