package tutorial.kotlin.udemy.kotlinchat.network.interfaces

import retrofit2.Call
import retrofit2.http.*
import tutorial.kotlin.udemy.kotlinchat.network.models.*

/**
 * Created by Md. Sifat-Ul Haque on 2/3/2018.
 */
interface ApiMethods {

    @POST("account/register")
    fun regUser(@Body userReg: UserRegistration): Call<String>

    @POST("account/login")
    fun loginUser(@Body userReg: UserRegistration): Call<UserLoginModel>

    @POST("user/add")
    fun addUser(@Header("Authorization") auth: String ,@Body addUser: AddUserRequestModel): Call<AddUserRequestModel>

    @GET("user/byEmail/{email}")
    fun findUser(@Header("Authorization") auth: String, @Path("email") email: String): Call<AddUserRequestModel>

    @GET("channel")
    fun getAllChannels(@Header("Authorization") auth: String): Call<ArrayList<Channel>>

    @GET("message/byChannel/{channelId}")
    fun getAllMessageFromChannel(@Header("Authorization") auth: String, @Path("channelId") channelId: String): Call<ArrayList<Message>>
}