package tutorial.kotlin.udemy.kotlinchat

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import tutorial.kotlin.udemy.kotlinchat.network.ApiClient
import tutorial.kotlin.udemy.kotlinchat.network.models.UserRegistration
import java.util.*

/**
 * Created by Md. Sifat-Ul Haque on 2/2/2018.
 */

class SignupActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = "SignupActivity"

    lateinit var random: Random
    lateinit var userAvatar: String
    lateinit var avatarColor: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initVariable()
        initListeners()
    }

    private fun initVariable() {
        random = Random()
        userAvatar = "profiledefault"
        avatarColor = "[0.5, 0.5, 0.5, 1]"
    }

    private fun initListeners() {
        btn_create_account.setOnClickListener(this)
        btn_generate_color.setOnClickListener(this)
        iv_user_avatar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {
            R.id.btn_create_account ->
                createAccount()
            R.id.iv_user_avatar ->
                changeUserAvatar()
            R.id.btn_generate_color ->
                generateBackgroundColor()
        }
    }

    private fun generateBackgroundColor() {
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)
        iv_user_avatar.setBackgroundColor(Color.rgb(r, g, b))
        avatarColor = "[${r / 255.0f}, ${g / 255.0f}, ${b / 255.0f}, 1]"
        println(avatarColor)
    }

    private fun changeUserAvatar() {
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)
        if (color == 1) {
            userAvatar = "light$avatar"
        } else {
            userAvatar = "dark$avatar"
        }

        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        iv_user_avatar.setImageResource(resourceId)
    }

    private fun createAccount() {
        val apiService = ApiClient.getApiService()
        val email = et_signup_email.text.toString()
        val password = et_signup_password.text.toString()
        if (isValid(email, password)) {
            apiService.regUser(UserRegistration(email, password)).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: retrofit2.Response<String>?) {
                    if (response?.isSuccessful!!) {
                        Log.d(TAG, response.body().toString())
                    } else {
                        Log.d(TAG, "Error Occurred ${response.errorBody()!!.string()}")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d(TAG, t.message)
                }
            })
        }
    }

    private fun isValid(email: String, password: String): Boolean {
        return !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
    }

}
