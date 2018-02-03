package tutorial.kotlin.udemy.kotlinchat.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import tutorial.kotlin.udemy.kotlinchat.BASE_DATA_URL
import tutorial.kotlin.udemy.kotlinchat.URL_EXTENSION
import tutorial.kotlin.udemy.kotlinchat.network.interfaces.ApiMethods


/**
 * Created by Md. Sifat-Ul Haque on 2/3/2018.
 */
object ApiClient {
    private var retrofitDataClient: Retrofit? = null
    private val gson: Gson

    init {
        gson = setGson()
        retrofitDataClient = setDataClient()
    }

    private fun setGson(): Gson {
        return GsonBuilder()
                .setLenient()
                .create()
    }


    private fun setDataClient(): Retrofit {
        if (retrofitDataClient == null) {
            retrofitDataClient = Retrofit.Builder()
                    .baseUrl(BASE_DATA_URL + URL_EXTENSION)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
        return retrofitDataClient as Retrofit
    }

    fun getApiService(): ApiMethods {
        return retrofitDataClient!!.create(ApiMethods::class.java)
    }
}