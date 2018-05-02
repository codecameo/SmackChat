package tutorial.kotlin.udemy.kotlinchat

import android.app.Application

/**
 * Created by Oshan on 5/2/18.
 */
class ChatApp : Application() {

    companion object {
        lateinit var sharedPref: SharedPrefs
    }

    override fun onCreate() {
        super.onCreate()
        sharedPref = SharedPrefs(applicationContext)
    }

}