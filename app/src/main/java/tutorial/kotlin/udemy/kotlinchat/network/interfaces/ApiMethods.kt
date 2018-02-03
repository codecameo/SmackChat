package tutorial.kotlin.udemy.kotlinchat.network.interfaces

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import tutorial.kotlin.udemy.kotlinchat.network.models.UserRegistration

/**
 * Created by Md. Sifat-Ul Haque on 2/3/2018.
 */
interface ApiMethods {

    @POST("account/register")
    fun regUser(@Body userReg: UserRegistration): Call<String>
}